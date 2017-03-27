package com.dataeye.crawler.zookeeper;

/**
 * Created by shelocks on 17/3/7.
 */
public class ZookeeperProperty {
  private String zkConnnectionStr;
  private String zNodePath;
  private int sessionTimeout;

  public ZookeeperProperty(String zkConnnectionStr, String zNodePath, int sessionTimeout) {
    this.zkConnnectionStr = zkConnnectionStr;
    this.zNodePath = zNodePath;
    this.sessionTimeout = sessionTimeout;
  }

  public String getZkConnnectionStr() {
    return zkConnnectionStr;
  }

  public void setZkConnnectionStr(String zkConnnectionStr) {
    this.zkConnnectionStr = zkConnnectionStr;
  }

  public String getzNodePath() {
    return zNodePath;
  }

  public void setzNodePath(String zNodePath) {
    this.zNodePath = zNodePath;
  }

  public int getSessionTimeout() {
    return sessionTimeout;
  }

  public void setSessionTimeout(int sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }
}
