package com.dataeye.crawler.proxypool;

import com.dataeye.crawler.client.CompositeDownloader;
import com.dataeye.crawler.client.CompositePageProcessor;
import com.dataeye.crawler.client.DataeyeSpider;
import com.dataeye.crawler.client.DefaultListener;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static us.codecraft.webmagic.selector.Selectors.xpath;

public class IqiyiPageProcesscor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        try {

            Map<String, Object> extraMap = page.getRequest().getExtras();
            String platform = extraMap.get("platform").toString();
            System.out.println(" platform ==> " + platform);
            String account = extraMap.get("account").toString();
            System.out.println(" account ==> " + account);

            getMainPage(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMainPage(Page page) {
        List<String> recomendList = page.getHtml().xpath("//div[@class='focus-index_listwrap']/ul/li").all();
        for (String s : recomendList) {
            String title = xpath("//li/a").selectElement(s).attr("alt");
            title = title.replace(":", "：");
            System.out.println(title);
        }
    }

    public Site getSite() {
        return site;
    }


    public static void main(String[] args) throws Exception {

        /*
        Spider.create(new IqiyiPageProcesscor())
                .addUrl("http://www.iqiyi.com/")
                .thread(1)
                .run();
        Thread.sleep(2 * 1000);
        System.exit(0);
        */

//        CompositePageProcessor processor = new CompositePageProcessor();
//        processor.addProcessor("iqiyi",new IqiyiPageProcesscor());
//
//        CompositeDownloader downloader = new CompositeDownloader();
//        downloader.addDownloader("iqiyi",new HttpClientDownloader());
//
//        List<SpiderListener> listenerList = new LinkedList<SpiderListener>();
//        listenerList.add(new DefaultListener());
//
//        DataeyeSpider
//                .create(processor, args)
//                .setDownloader(downloader)
//                .setSpiderListeners(listenerList)
//                .setExitWhenComplete(false)
//                .thread(1)
//                .runAsync();

        System.out.println("start spider");
    }
}
