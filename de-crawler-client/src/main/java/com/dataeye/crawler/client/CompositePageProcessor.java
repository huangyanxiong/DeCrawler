package com.dataeye.crawler.client;

import org.apache.commons.collections.map.HashedMap;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Created by shelocks on 17/3/14.
 */
public class CompositePageProcessor implements PageProcessor {

  private Map<String, PageProcessor> processorMap = new HashedMap();

  private Site dumpSite = new Site();

  public void addProcessor(String type, PageProcessor processor) {
    requireNonNull(type, "type");
    requireNonNull(processor, "processor");

    processorMap.put(type, processor);
  }

  @Override
  public void process(Page page) {
    requireNonNull(page, "page");

    com.dataeye.crawler.thrift.Task bizTask = (com.dataeye.crawler.thrift.Task)
            page.getRequest().getExtra("bizTask");
    Object processorType = bizTask.getExtras().get("platform");

    if (processorType == null) {
      throw new ProcessorNotFoundException("When you use CompositePageProcessor processor," +
              "you should set platform value to find target processor.");
    }

    PageProcessor processor = processorMap.get(processorType);
    if(processor==null){
      throw new ProcessorNotFoundException("Can't find process for platformType:"+processorType);
    }

    processor.process(page);
  }

  @Override
  public Site getSite() {
    return dumpSite;
  }
}
