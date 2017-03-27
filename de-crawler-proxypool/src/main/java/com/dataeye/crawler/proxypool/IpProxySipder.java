package com.dataeye.crawler.proxypool;

import com.dataeye.crawler.client.*;
import com.dataeye.crawler.util.ProfileManager;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.LinkedList;
import java.util.List;

/**
 * Ip代理的爬虫,主要功能:
 * <p>
 * 1) 从页面抓取代理ip地址,并存入数据库
 * 2) 定时check数据库中的代理ip是否有效
 * <p>
 * <p>
 * Created by shelocks on 17/3/16.
 */
public class IpProxySipder {
  public static void main(String[] args) {

    ProfileManager.init(args);

    ProxyAliveChecker proxyAliveChecker = new ProxyAliveChecker();

    XiCiProxyProcessor xici = new XiCiProxyProcessor();

    KuaiDaiLiProxyProcessor  kuaidaili = new KuaiDaiLiProxyProcessor();

    DefaultDownloader ipProxyFetchDownloader = new DefaultDownloader();
    ipProxyFetchDownloader.setThread(3);

    /**
     * 不同的业务使用不同的downloader,ipProxyCheck其实不需要下载逻辑.
     */
    CompositeDownloader downloader = new CompositeDownloader();
    downloader.addDownloader("ipProxyCheck", proxyAliveChecker, proxyAliveChecker.getSite());
    downloader.addDownloader("ipProxyFetch-xici", ipProxyFetchDownloader, xici.getSite());
    downloader.addDownloader("ipProxyFetch-kuaidaili", ipProxyFetchDownloader, kuaidaili.getSite());


    /**
     * 不同的业务使用不同的processor,组合在一起
     */
    CompositePageProcessor processor = new CompositePageProcessor();
    processor.addProcessor("ipProxyCheck", proxyAliveChecker);
    processor.addProcessor("ipProxyFetch-xici", xici);
    processor.addProcessor("ipProxyFetch-kuaidaili", kuaidaili);



    Spider spider = DataeyeSpider.create(processor, args);

    /**
     * 创建新的组合scheduler
     */
    Scheduler scheduler = new OrElseScheduler(new QueueScheduler(), spider.getScheduler());

    List<SpiderListener> listenerList = new LinkedList<SpiderListener>();
    listenerList.add(new DefaultListener(spider.getScheduler()));

    spider.setScheduler(scheduler)
            .setDownloader(downloader)
            .setSpiderListeners(listenerList)
            .setExitWhenComplete(false)
            .thread(1)
            .runAsync();

    System.out.println("start spider");
  }
}
