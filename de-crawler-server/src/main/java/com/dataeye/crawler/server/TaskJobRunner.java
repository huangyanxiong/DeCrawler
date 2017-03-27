package com.dataeye.crawler.server;


import com.dataeye.crawler.thrift.Task;
import com.dataeye.crawler.thrift.Worker;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by yann on 2017/3/8.
 */
public class TaskJobRunner implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TaskJobRunner.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Map<String, BlockingQueue<Task>> bizQueues = (Map<String, BlockingQueue<Task>>) context.getJobDetail().getJobDataMap().get("queue");

        com.dataeye.crawler.thrift.Job bean = (com.dataeye.crawler.thrift.Job) context.getJobDetail().getJobDataMap().get("bean");

        String jobId = bean.getJobId();
        String jobName = bean.getJobName();

        Worker worker = new Worker(jobId, jobName);

        Task task = new Task(jobId, bean.getLevel(), bean.getUrl(), worker);

        // 平台信息放入 extras 属性
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("platform", bean.getPlatform());
        extras.put("account", bean.getAccount());
        task.setExtras(extras);

        BlockingQueue<Task> taskQueue = null;

        try {
            if (bizQueues.containsKey(jobName)) {
                taskQueue = bizQueues.get(jobName);
                taskQueue.offer(task);
            } else {
                taskQueue = new PriorityBlockingQueue<Task>(1024, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o1.getLevel() - o2.getLevel();
                    }
                });
                taskQueue.offer(task);
                bizQueues.put(jobName, taskQueue);
            }
        }catch (Exception ex){
            logger.error(" Put task into queue error !");
        }

        logger.info("JobId: " + jobId + ", JobName: " + jobName + ", Queue size is : " + taskQueue.size());

    }
}
