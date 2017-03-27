package com.dataeye.crawler.example;

import com.dataeye.crawler.thrift.Job;
import com.dataeye.crawler.thrift.ScheduleService;
import com.dataeye.crawler.thrift.Worker;
import com.linecorp.armeria.client.Clients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shelocks on 17/3/3.
 */
public class TestCase {

    public static void main(String[] args) throws Exception {

        ScheduleService.Iface scheduleService = Clients.newClient(
                "tbinary+http://127.0.0.1:8080/scheduler",
                ScheduleService.Iface.class); // or AsyncIface.class

        //服务端 hello 测试方法使用实例
        String greeting = scheduleService.hello();
        System.out.println(greeting);

        //服务端 listWorks 方法使用实例
        Map<String, Set<Worker>> map = scheduleService.listWorks();
        Set<Worker> set = map.get("myname");
        for (Worker worker : set) {
            System.out.println(worker.getBiz());
        }

        //服务端 push 方法使用实例
        com.dataeye.crawler.thrift.Task task = new com.dataeye.crawler.thrift.Task();
        task.setId("myid");
        task.setLevel(1);
        task.setUrl("http://www.iqiyi.com/");
        Worker worker = new Worker("myid1", "myname");
        task.setWork(worker);
        Map<String, String> mapTask = new HashMap<String, String>();
        mapTask.put("platform", "iqiyi");
        mapTask.put("account", "myAccount1");
        task.setExtras(mapTask);
        scheduleService.push(task);


        //服务端 addJob 方法使用实例
        Job job1 = new Job();
        job1.setJobId("myid2");
        job1.setJobName("myname");
        job1.setLevel(1);
        job1.setCron("0/40 * * * * ?");
        job1.setUrl("http://www.iqiyi.com/");
        job1.setPlatform("youku");
        job1.setAccount("aaa");
        System.out.println(scheduleService.addJob(job1));

        //服务端 updateJob 方法使用实例
        Job job2 = new Job();
        job2.setJobId("myid2");
        job2.setJobName("myname");
        job2.setLevel(1);
        job2.setCron("0/30 * * * * ?");
        job2.setUrl("http://www.iqiyi.com/");
        job2.setPlatform("baidu");
        job2.setAccount("aaa");
        System.out.println(scheduleService.updateJob(job2));

        //服务端 delJob 方法使用实例
        System.out.println(scheduleService.delJob("myid2", "myname"));

        //服务端 listJobByName 方法使用实例
        List<Job> jobList = scheduleService.listJobByName("myname");
        for (Job job : jobList){
            System.out.println(job.getCron());
        }

        //服务端 getJobByIdAndName 方法使用实例
        System.out.println(scheduleService.getJobByIdAndName("myid2","myname").getJobName());
    }

}
