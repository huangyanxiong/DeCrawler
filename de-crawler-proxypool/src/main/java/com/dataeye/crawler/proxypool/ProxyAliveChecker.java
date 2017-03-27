package com.dataeye.crawler.proxypool;

import com.dataeye.crawler.db.DaoManager;
import com.dataeye.crawler.proxypool.dao.IpProxyDAO;
import com.dataeye.crawler.proxypool.domain.IpProxy;
import com.dataeye.crawler.proxypool.service.IpProxyService;
import com.dataeye.crawler.util.ProfileManager;
import com.google.common.util.concurrent.*;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * 检查Ip代理池中ip的可用性
 * <p>
 * Created by shelocks on 17/3/15.
 */
public class ProxyAliveChecker implements Downloader, PageProcessor {
  //进行任务执行的线程池
  private ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
          Executors.newFixedThreadPool(10));

  private CountDownLatch countDownLatch;

  private static final Logger logger = LoggerFactory.getLogger(ProxyAliveChecker.class);

  @Override
  public Page download(Request request, Task task) {
    Page page = new Page();
    page.setRequest(request);
    return page;
  }

  @Override
  public void setThread(int threadNum) {
  }

  @Override
  public void process(Page page) {
    //TODO need query all proxies ?
    List<IpProxy> proxies = IpProxyService.listAllProxies();

    countDownLatch = new CountDownLatch(proxies.size());

    for (IpProxy proxy : proxies) {
      this.checkProxy(proxy);
    }

    //等待所有的任务结束
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      logger.error("ProxyAliveChecker process error raise.", e);
    }

    logger.info("ProxyAliveChecker process done." + (proxies.size() - countDownLatch.getCount()) + " ip checked");
    countDownLatch = null;
  }

  @Override
  public Site getSite() {
    return Site.me();
  }

  private void checkProxy(IpProxy ipProxy) {
    //check 代理是否存活
    ListenableFuture<IpProxy> proxyFuture = executorService.submit(new Callable<IpProxy>() {
      @Override
      public IpProxy call() throws Exception {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipProxy.getIp(), ipProxy.getPort()));

        OkHttpClient okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();

        okhttp3.Request request = new okhttp3.Request.Builder().url("http://www.baidu.com").head().build();
        try {
          Response response = okHttpClient.newCall(request).execute();
          ipProxy.setAlive(true);
        } catch (IOException e) {
          ipProxy.setAlive(false);
        }
        ipProxy.setLastCheck(System.currentTimeMillis());

        return ipProxy;
      }
    });

    // check 之后进行数据更新工作
    Futures.addCallback(proxyFuture, new FutureCallback<IpProxy>() {
      @Override
      public void onSuccess(IpProxy ipProxy) {
        countDownLatch.countDown();
        if (ipProxy.isAlive()){
          IpProxyService.updateIpProxy(ipProxy);
        } else {
          IpProxyService.delIpProxy(ipProxy);
        }

      }

      @Override
      public void onFailure(Throwable throwable) {
        countDownLatch.countDown();
      }
    });
  }

  public static void main(String[] args) {
    ProfileManager.init(args);
    Spider.create(new ProxyAliveChecker()).addUrl("https://www.baidu.com").run();
  }

}
