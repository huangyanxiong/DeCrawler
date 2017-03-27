package com.dataeye.crawler.proxypool;

import com.dataeye.crawler.proxypool.domain.IpProxy;
import com.dataeye.crawler.proxypool.service.IpProxyService;
import com.dataeye.crawler.util.ProfileManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by shelocks on 17/3/15.
 */
public class KuaiDaiLiProxyProcessor implements PageProcessor {
    //TODO 这里的cookie要动态更新
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setUserAgent(
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"
    ).setDomain("www.kuaidaili.com")
            .addCookie("_ydclearance", "a8b058319801558dd920c1f5-06cb-4ebe-9cfe-5ced10648a4e-1490586362");


    Pattern pattern = Pattern.compile("http://www.kuaidaili.com/free/inha/\\d*/$");

    private final static int MAX_PAGE_NUMBER = 5;

    private Map<String, Object> extras = null;

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

        System.out.println("get url ==> " + page.getRequest().getUrl());

        List<IpProxy> ipProxyList = new ArrayList<IpProxy>();

        Document document = Jsoup.parse(UrlUtils.fixAllRelativeHrefs(page.getRawText(), page.getRequest().getUrl()));

        Elements proxyList = document.select("table>tbody>tr");
        for (Element proxy : proxyList) {
            Object[] proxyFields = proxy.select("tr>td").toArray();
            IpProxy ipProxy = new IpProxy();
            //抽取代理信息
            for (int index = 0; index < proxyFields.length; index++) {
                String field = ((Element) proxyFields[index]).text();
                if (index == 0) {
                    ipProxy.setIp(field);
                } else if (index == 1) {
                    ipProxy.setPort(Integer.valueOf(field));
                } else if (index == 2) {
                    ipProxy.setPrivateLevel(field);
                } else if (index == 3) {
                    ipProxy.setType(field);
                } else if (index == 5) {
                    ipProxy.setArea(field);
                } else if (index == 6) {
                    ipProxy.setSpeed(1);
                }
            }
            ipProxy.setAlive(false);
            ipProxy.setLastCheck(0);

            page.putField(ipProxy.getIp(), ipProxy);

            System.out.println(ipProxy.toString());

            if (!IpProxyService.checkIfExist(ipProxy.getIp(), ipProxy.getPort())) {
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
        Elements urlList = page.select("div#listnav>ul>li>a");

        List<Request> nextUrls = new LinkedList<>();
        for (Element nextUrlEle : urlList) {
            if (!nextUrlEle.hasClass("active")) {
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
        Spider.create(new KuaiDaiLiProxyProcessor()).addUrl("http://www.kuaidaili.com/free/inha/").run();
    }
}
