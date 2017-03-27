package com.dataeye.crawler.proxypool;

import com.dataeye.crawler.client.CompositeDownloader;
import com.dataeye.crawler.client.CompositePageProcessor;
import com.dataeye.crawler.client.DataeyeSpider;
import com.dataeye.crawler.client.DefaultListener;
import com.dataeye.crawler.proxypool.domain.IpProxy;
import com.dataeye.crawler.proxypool.service.IpProxyService;
import com.dataeye.crawler.util.ProfileManager;
import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shelocks on 17/3/15.
 */
public class XiCiProxyProcessor implements PageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(XiCiProxyProcessor.class);

    private SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm");

    Pattern pattern = Pattern.compile("http://www.xicidaili.com/nn/\\d*$");

    private final static int MAX_PAGE_NUMBER = 5;

    private Map<String, Object> extras = null;

    private Site site = Site.me()
//            .setHttpProxy(new HttpHost("117.90.7.213", 9000))
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"
    );
            //.addCookie("CNZZDATA1256960793", "1691733321-1490076008-|1490076008")
            //.addCookie("UM_distinctid", "15aef8a28d89-00ef97d83b7c6-47534130-100200-15aef8a28dfbb")
            //.addCookie("_free_proxy_session", "BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJWEyYTlmMTE3MDIwNGI5OGE2YTBmNTAwNDc3MzVhYjA0BjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMVFiYkhncGlmaktiVkRoUllvcHRtNnE5dFQ1MURJNmNacEV3OWlYQ09nOVk9BjsARg==--c29d3ddbb63846c872917a1ad5e034233e09433a");

    @Override
    public void process(Page page) {

        if (extras == null) {
            extras = page.getRequest().getExtras();
        }

        if (pattern.matcher(page.getRequest().getUrl()).find()) {
            parseMainUrl(page);
        }

        Document document = Jsoup.parse(UrlUtils.fixAllRelativeHrefs(page.getRawText(), page.getRequest().getUrl()));
        //获取接着要抓取的url
        List<Request> nextUrls = extractNextUrls(document);
        for (Request req : nextUrls) {
            page.addTargetRequest(req);
        }

    }

    private void parseMainUrl(Page page) {

        Document document = Jsoup.parse(UrlUtils.fixAllRelativeHrefs(page.getRawText(), page.getRequest().getUrl()));

        List<IpProxy> ipProxyList = new ArrayList<IpProxy>();

        Elements proxyList = document.select("table[id='ip_list']>tbody>tr[class]");
        for (Element proxy : proxyList) {
            Object[] proxyFields = proxy.select("tr>td").toArray();
            String ip = null;
            Integer port = null;
            IpProxy ipProxy = new IpProxy();
            //抽取代理信息
            for (int index = 0; index < proxyFields.length; index++) {
                String field = ((Element) proxyFields[index]).text();
                if (index == 1) {
                    ip = field;
                } else if (index == 2) {
                    port = Integer.valueOf(field);
                } else if (index == 3) {
                    ipProxy.setArea(field);
                } else if (index == 4) {
                    ipProxy.setPrivateLevel(field);
                } else if (index == 5) {
                    ipProxy.setType(field);
                } else if (index == 9) {
                    try {
                        ipProxy.setLastCheck(formatter.parse(field).getTime());
                    } catch (ParseException ex) {
                        logger.error(" date format error", ex);
                    }
                }
            }
            ipProxy.setIp(ip);
            ipProxy.setPort(port);
            ipProxy.setSpeed(1);
            ipProxy.setAlive(false);

            System.out.println(ipProxy);

            if (!IpProxyService.checkIfExist(ip, port)) {
                ipProxyList.add(ipProxy);
            }
        }

        if (ipProxyList.size() > 0) {
            IpProxyService.addIpProxyList(ipProxyList);
        }
    }

    /**
     * 用页面中抽取接下来要抓的url
     *
     * @param page
     * @return
     */
    private List<Request> extractNextUrls(Document page) {
        Elements urlList = page.select("div[class='pagination']>a");

        List<Request> nextUrls = new LinkedList<>();
        for (Element nextUrlEle : urlList) {
            if (nextUrlEle.hasAttr("href")) {
                String url = nextUrlEle.attr("href");
                int pageNumber = this.extractPageNumber(url);
                if (pageNumber > 0 && pageNumber <= MAX_PAGE_NUMBER) {
                    Request request = new Request();
                    request.setUrl(url);
                    request.setExtras(extras);
                    nextUrls.add(request);
                }
            }
        }

        return nextUrls;
    }

    /**
     * 抽取出分页号
     *
     * @param url
     * @return
     */
    private int extractPageNumber(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        int lastIndex = url.lastIndexOf("/");
        if (lastIndex == -1) {
            return lastIndex;
        }
        try {
            return Integer.valueOf(url.substring(lastIndex + 1, url.length()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        ProfileManager.init(args);
        Spider.create(new XiCiProxyProcessor()).addUrl("http://www.xicidaili.com/nn/").run();

//        CompositePageProcessor processor = new CompositePageProcessor();
//        processor.addProcessor("ipProxyFetch-xici",new XiCiProxyProcessor());
//
//        CompositeDownloader downloader = new CompositeDownloader();
//        downloader.addDownloader("ipProxyFetch-xici",new HttpClientDownloader());
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
