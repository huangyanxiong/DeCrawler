package com.dataeye.crawler.client;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * 一个组合模式的Scheduler.一个Scheduler的优先级比较高,这样我们可以组合
 * 不同优先级的Scheduler.例如,一个本地的Scheduler和一个分布式Scheduler.
 * <p>
 * Created by shelocks on 17/3/15.
 */
public class OrElseScheduler implements Scheduler {
  private Scheduler firstScheduler;
  private Scheduler secondScheuler;

  public OrElseScheduler(Scheduler firstScheduler, Scheduler secondScheuler) {
    this.firstScheduler = firstScheduler;
    this.secondScheuler = secondScheuler;
  }

  @Override
  public void push(Request request, Task task) {
    try {
      firstScheduler.push(request, task);
    } catch (Exception ex) {
      //表明第一个queue已满,放入第二个queue中
      secondScheuler.push(request, task);
    }
  }

  @Override
  public Request poll(Task task) {
    Request request = firstScheduler.poll(task);
    if (request == null) {
      request = secondScheuler.poll(task);
    }

    return request;
  }
}
