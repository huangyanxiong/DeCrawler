package com.dataeye.crawler.proxypool.domain;

/**
 * Created by shelocks on 17/3/15.
 */
public class IpProxy {

  private int id;

  private String ip;

  private int port;

  //proxy type: http https sock
  private String type;

  //地区
  private String area;

  //匿名度
  private String privateLevel;

  //相应速度
  private int speed;

  //使用存活
  private boolean isAlive;

  //最后check时间
  private long lastCheck;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  public String getType() {
    return type;
  }

  public String getArea() {
    return area;
  }

  public String getPrivateLevel() {
    return privateLevel;
  }

  public int getSpeed() {
    return speed;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public long getLastCheck() {
    return lastCheck;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public void setPrivateLevel(String privateLevel) {
    this.privateLevel = privateLevel;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public void setAlive(boolean alive) {
    isAlive = alive;
  }

  public void setLastCheck(long lastCheck) {
    this.lastCheck = lastCheck;
  }

  @Override
  public String toString() {
    return "IpProxy{" +
            "id='" + id + '\'' +
            "ip='" + ip + '\'' +
            ", port=" + port +
            ", type='" + type + '\'' +
            ", area='" + area + '\'' +
            ", privateLevel='" + privateLevel + '\'' +
            ", speed=" + speed +
            ", isAlive=" + isAlive +
            ", lastCheck=" + lastCheck +
            '}';
  }
}
