package com.dataeye.crawler.client;

import com.dataeye.crawler.thrift.Worker;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yann on 2017/3/8.
 */
public class DefaultScheduler implements Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultScheduler.class);

    private Worker worker;

    public DefaultScheduler(Worker worker){
        this.worker = worker;
    }

    @Override
    public void push(Request request, Task task) {
        try {
            com.dataeye.crawler.thrift.Task bizTask=
                    (com.dataeye.crawler.thrift.Task) request.getExtra("bizTask");
            ScheduleClient.getInstance().push(bizTask);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Request poll(Task task) {
        Request request = new Request();
        String message = "client id:" + worker.getId() + ", biz:" + worker.getBiz();
        try {
            com.dataeye.crawler.thrift.Task bizTask = ScheduleClient.getInstance().poll(worker);
            if(!bizTask.getId().equals("")){
                //Map<String, Object> extrasKV = clone(bizTask.getExtras());
                //request.setExtras(extrasKV);
                request.putExtra("bizTask",bizTask);
                request.setUrl(bizTask.getUrl());
                logger.info(message + " get task, url is: " + bizTask.getUrl());
            }else {
                logger.info(message + " cannot get task, sleep for a while!");
                return null;
            }
        } catch (TException e) {
            logger.error(message + "throw Exception, message is: " + e.getMessage());
            e.printStackTrace();
        }
        return request;
    }

    private Map<String, Object> clone(Map<String, String> extras){
        String platform = extras.get("platform");
        String account = extras.get("account");
        Map<String, Object> extrasKV = new HashMap<String, Object>();
        extrasKV.put("platform", platform);
        extrasKV.put("account", account);
        return extrasKV;
    }
}
