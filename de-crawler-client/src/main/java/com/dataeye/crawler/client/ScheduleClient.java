package com.dataeye.crawler.client;

import com.dataeye.crawler.thrift.Job;
import com.dataeye.crawler.thrift.ScheduleService;
import com.dataeye.crawler.thrift.Task;
import com.dataeye.crawler.thrift.Worker;
import com.dataeye.crawler.util.ProfileManager;
import com.linecorp.armeria.client.Clients;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shelocks on 17/3/1.
 */
public class ScheduleClient implements ScheduleService.Iface {

    private final static String RPCConn = ProfileManager.get().getProperty("rpcConn");

    private final static ScheduleService.Iface SCHEDULE_SERVICE = Clients.newClient(
            "tbinary+http://" + RPCConn + "/scheduler",
            ScheduleService.Iface.class); // or AsyncIface.class

    private final static ScheduleClient CRAWLER_CLIENT = new ScheduleClient();

    private ScheduleClient() {
    }

    public static final ScheduleClient getInstance() {
        return CRAWLER_CLIENT;
    }

    @Override
    public Task poll(Worker worker) throws TException {
        return SCHEDULE_SERVICE.poll(worker);
    }

    @Override
    public boolean push(com.dataeye.crawler.thrift.Task task) throws TException {
        return SCHEDULE_SERVICE.push(task);
    }

    @Override
    public Map<String, Set<Worker>> listWorks() throws TException {
        return SCHEDULE_SERVICE.listWorks();
    }

    @Override
    public boolean addJob(Job job) throws TException {
        return SCHEDULE_SERVICE.addJob(job);
    }

    @Override
    public boolean updateJob(Job job) throws TException {
        return SCHEDULE_SERVICE.updateJob(job);
    }

    @Override
    public boolean delJob(String jobId, String jobName) throws TException {
        return SCHEDULE_SERVICE.delJob(jobId, jobName);
    }

    @Override
    public List<Job> listJobByName(String jobName) throws TException {
        return SCHEDULE_SERVICE.listJobByName(jobName);
    }

    @Override
    public Job getJobByIdAndName(String jobId, String jobName) throws TException {
        return SCHEDULE_SERVICE.getJobByIdAndName(jobId, jobName);
    }

    @Override
    public String hello() throws TException {
        return SCHEDULE_SERVICE.hello();
    }

}
