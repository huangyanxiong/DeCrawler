package com.dataeye.crawler.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yann on 2017/3/8.
 */
public class CronUtil {

    private static final Logger logger = LoggerFactory.getLogger(CronUtil.class);

    public static Scheduler run() {
        Scheduler scheduler = null;
        try {
            // 取得一个scheduler的引用
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            // 开始执行，start()方法被调用后，计时器就开始工作
            scheduler.start();
        }catch (Exception ex){
            logger.error("create scheduler error !");
        }
        return scheduler;
    }
}
