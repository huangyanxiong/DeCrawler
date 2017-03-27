package com.dataeye.crawler.server;

import com.dataeye.crawler.service.JobService;
import com.dataeye.crawler.thrift.Job;
import com.dataeye.crawler.thrift.ScheduleService;
import com.dataeye.crawler.thrift.Task;
import com.dataeye.crawler.thrift.Worker;
import com.dataeye.crawler.util.CronUtil;
import com.dataeye.crawler.util.ProfileManager;
import com.dataeye.crawler.zookeeper.DefaultNodeValueCodec;
import com.dataeye.crawler.zookeeper.ZooKeeperConnector;
import com.dataeye.crawler.zookeeper.ZooKeeperListener;
import com.dataeye.crawler.zookeeper.ZookeeperProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.thrift.TException;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Server端的{@link ScheduleService}实现
 *
 * @TODO 前期的简单实现就是quartz会生成每个爬取账号的定时任务, 然后塞入任务的优先级队列,
 * @TODO worker来获取任务时直接从队列中获取任务分给work.
 * <p>
 * <p>
 * Created by shelocks on 17/3/2.
 */
public class DefaultScheduleService implements ScheduleService.Iface {

    //保存任务的优先队列
    private PriorityBlockingQueue<Task> taskQueue = new PriorityBlockingQueue<Task>(1024, new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getLevel() - o2.getLevel();
        }
    });

    //每个业务有自己的任务队列
    private Map<String, BlockingQueue<Task>> bizQueues = new HashMap<>();

    private Scheduler scheduler;

    private ZooKeeperConnector zooKeeperConnector;

    private ZookeeperProperty zookeeperProperty;

    private DefaultNodeValueCodec nodeValueCodec = DefaultNodeValueCodec.INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(DefaultScheduleService.class);

    //每个业务有自己的workers
    private Map<String, Set<Worker>> bizWorkers = new HashMap<String, Set<Worker>>();

    public DefaultScheduleService(String[] configs) {

        ProfileManager.init(configs);

        scheduler = CronUtil.run();

        String zkConn = ProfileManager.get().getProperty("zkConn");

        this.zookeeperProperty = new ZookeeperProperty(zkConn, "/de-spider/works", 60000);

        this.zooKeeperConnector = new ZooKeeperConnector(this.zookeeperProperty.getZkConnnectionStr(),
                this.zookeeperProperty.getzNodePath(), this.zookeeperProperty.getSessionTimeout(),
                new ZooKeeperListener() {
                    @Override
                    public void nodeChildChange(Map<String, String> newChildrenValue) {
                        List<Worker> newWorkers = newChildrenValue.values().stream()
                                .map(nodeValueCodec::decode).filter(Objects::nonNull).collect(Collectors.toList());
                        Map<String, Set<Worker>> newBizWorkers = new HashMap<>();

                        if (newWorkers != null) {
                            for (Worker worker : newWorkers) {
                                String biz = worker.biz;

                                if (!newBizWorkers.containsKey(biz)) {
                                    newBizWorkers.put(biz, new HashSet<>());
                                }
                                newBizWorkers.get(biz).add(worker);
                            }
                        }

                        //更新之前打印对比日志,列出新增和减少了那些worker
                        logger.info("Previous biz workers:" + printBizWorkers());

                        //更新bizWorkers
                        bizWorkers = newBizWorkers;
                        logger.info("After biz workers:" + printBizWorkers());

                    }

                    @Override
                    public void nodeValueChange(String newValue) {

                    }

                    @Override
                    public void connected() {

                    }
                });
        this.zooKeeperConnector.connect();

        this.start();
    }

    /**
     *
     */
    private void start() {
        //quartz调度器做相应的工作
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    List<Job> jobList = JobService.getJobList();

                    for (Job bean : jobList) {

                        String jobName = "job-" + bean.getJobId() + "-" + bean.getJobName();
                        String groupName = "group-" + bean.getJobId() + "-" + bean.getJobName();
                        String triggerName = "trigger-" + bean.getJobId() + "-" + bean.getJobName();

                        String cron = bean.getCron();

                        JobKey jobKey = new JobKey(jobName, groupName);
                        if (scheduler.checkExists(jobKey)){
                            scheduler.deleteJob(jobKey);
                        }

                        JobDetail job = newJob(TaskJobRunner.class).withIdentity(jobName, groupName).build();
                        job.getJobDataMap().put("queue", bizQueues);
                        job.getJobDataMap().put("bean", bean);
                        CronTrigger trigger = newTrigger().withIdentity(triggerName, groupName).withSchedule(cronSchedule(cron)).build();
                        scheduler.scheduleJob(job, trigger);
                    }

                    JobService.updateJob2Load(jobList);

                } catch (Exception ex) {
                    logger.error("Quartz error!!! Job cannot run!", ex);
                    throw new RuntimeException(ex.getCause());
                }
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public Task poll(Worker worker) throws TException {
        Task task = null;
        if (bizQueues.containsKey(worker.biz)){
            task = bizQueues.get(worker.biz).poll();
        }
        if (task == null) {
            task = new Task("", 0, "", new Worker("", ""));
        }
        return task;
    }

    @Override
    public boolean push(Task task) throws TException {
        return bizQueues.get(task.getWork().biz).offer(task);
    }

    @Override
    public Map<String, Set<Worker>> listWorks() throws TException {
        return bizWorkers;
    }

    @Override
    public boolean addJob(Job job) throws TException {
        return JobService.addJobInfo(job);
    }

    @Override
    public boolean updateJob(Job job) throws TException {
        return JobService.updateJobInfo(job);
    }

    @Override
    public boolean delJob(String jobId, String jobName) throws TException {
        boolean result = false;
        String quarteName = "job-" + jobId + "-" + jobName;
        String quarteGroup = "group-" + jobId + "-" + jobName;
        try {
            JobKey jobKey = new JobKey(quarteName, quarteGroup);
            if (scheduler.checkExists(jobKey)){
                scheduler.deleteJob(jobKey);
            }
            result = JobService.delJobInfo(jobId, jobName);
        } catch (Exception ex) {
            logger.error(" delete job error!");
        }
        return result;
    }

    @Override
    public List<Job> listJobByName(String jobName) throws TException {
        return JobService.listJobByName(jobName);
    }

    @Override
    public Job getJobByIdAndName(String jobId, String jobName) throws TException {
        return JobService.getJobByIdAndName(jobId, jobName);
    }


    @Override
    public String hello() throws TException {
        return "hello worker";
    }

    private String printBizWorkers() {
        try {
            return new ObjectMapper().writeValueAsString(bizWorkers);
        } catch (JsonProcessingException e) {
            logger.error("failed print bizWorkers", e);
        }
        return "{failed print bizWorkers}";
    }

}
