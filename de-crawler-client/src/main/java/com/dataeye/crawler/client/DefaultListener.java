package com.dataeye.crawler.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Created by shelocks on 17/3/1.
 */
public class DefaultListener implements SpiderListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultListener.class);

    private Scheduler defaultScheduler;

    public DefaultListener(Scheduler defaultScheduler){
        this.defaultScheduler = defaultScheduler;
    }

    public void onSuccess(Request request) {
        logger.info("success:" + request.getUrl());
    }

    public void onError(Request request) {
        defaultScheduler.push(request, new Task() {
            @Override
            public String getUUID() {
                return null;
            }

            @Override
            public Site getSite() {
                return null;
            }
        });
        logger.error("failed:" + request.getUrl());
    }

}
