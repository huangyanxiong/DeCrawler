package com.dataeye.crawler.client;

import org.apache.commons.collections.map.HashedMap;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Created by shelocks on 17/3/14.
 */
public class CompositeDownloader implements Downloader {
  private Map<String, Downloader> downloaderMap = new HashedMap();
  private Map<String, Site> siteMap = new HashedMap();

  public void addDownloader(String type, Downloader downloader, Site site) {
    requireNonNull(type, "type");
    requireNonNull(downloader, "downloader");

    downloaderMap.put(type, downloader);
    siteMap.put(type, site);
  }

  @Override
  public Page download(Request request, Task task) {
    requireNonNull(request, "request");
    requireNonNull(task, "task");

    com.dataeye.crawler.thrift.Task bizTask = (com.dataeye.crawler.thrift.Task)
            request.getExtra("bizTask");
    Object type = bizTask.getExtras().get("platform");
    if (type == null) {
      throw new DownloaderNotFoundException("When you use CompositeDownloader downloader," +
              "you should set platform value to find target downloader.");
    }

    Downloader downloader = downloaderMap.get(type);
    if (downloader == null) {
      throw new DownloaderNotFoundException("Can't find downloader for platformType:" + type);
    }

    request.putExtra("site", siteMap.get(type));

    return downloader.download(request, task);
  }

  @Override
  public void setThread(int threadNum) {
  }
}
