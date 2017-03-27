package com.dataeye.crawler.client;


import com.dataeye.crawler.thrift.Worker;
import com.dataeye.crawler.util.ProfileManager;
import com.dataeye.crawler.zookeeper.NodeValueCodec;
import com.dataeye.crawler.zookeeper.ZooKeeperConnector;
import com.dataeye.crawler.zookeeper.ZooKeeperListener;
import com.dataeye.crawler.zookeeper.ZookeeperProperty;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.Map;

/**
 * Created by shelocks on 17/3/6.
 */
public class DataeyeSpider extends Spider {

    private static final Logger logger = LoggerFactory.getLogger(DataeyeSpider.class);

    private ZookeeperProperty zkBean;

    private ZooKeeperConnector zooKeeperConnector;

    private ZookeeperProperty zookeeperProperty;

    private NodeValueCodec<Worker> nodeValueCodec = NodeValueCodec.DEFAULT;

    private static final String ZKPATH = "/de-spider/works";

    private static final Integer ZKTIMEOUT = 60000;

    private Worker worker;

    /**
     * 加载配置文件、注册zk、并实例化 scheduler
     */
    public DataeyeSpider(PageProcessor pageProcessor, String[] args) {

        super(pageProcessor);

        ProfileManager.init(args);
        // 配置加载
        loadSpiderConf();
        //注册
        registrySelf();
        // 实例化scheduler
        this.scheduler = new DefaultScheduler(worker);
    }

    /**
     * 初始化SpiderWorker的基本信息
     */
    private void loadSpiderConf() {
        // init worker info
        String workerId = ProfileManager.get().getProperty("workerId");
        String workerBiz = ProfileManager.get().getProperty("workerBiz");
        worker = new Worker(workerId, workerBiz);
        logger.info("init worker info: workerId is " + workerId + " and workerBiz is " + workerBiz);

        // init zookeeper info
        String zkConn = ProfileManager.get().getProperty("zkConn");
        zookeeperProperty = new ZookeeperProperty(zkConn, ZKPATH, ZKTIMEOUT);
        logger.info("init zookeeper info: zkConn is " + zkConn);
    }

    /**
     * 向master注册自己,通过zookeeper实现
     */
    private void registrySelf() {
        zooKeeperConnector = new ZooKeeperConnector(
            zookeeperProperty.getZkConnnectionStr(),
            zookeeperProperty.getzNodePath(),
            zookeeperProperty.getSessionTimeout(),
            new ZooKeeperListener() {
                @Override
                public void nodeChildChange(Map<String, String> newChildrenValue) {

                }

                @Override
                public void nodeValueChange(String newValue) {

                }

                @Override
                public void connected() {
                    //将worker的自身信息写入zookeeper
                    zooKeeperConnector.createChild(worker.getId() + "_" + worker.getBiz(), nodeValueCodec.encode(worker), CreateMode.EPHEMERAL);
                }
            });
        zooKeeperConnector.connect();
    }

    public static Spider create(PageProcessor pageProcessor, String[] args) {
        return new DataeyeSpider(pageProcessor, args);
    }

    public Worker getWorker() {
        return this.worker;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

}
