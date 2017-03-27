package com.dataeye.crawler.proxypool.dao;

import com.dataeye.crawler.proxypool.domain.IpProxy;
import com.dataeye.crawler.thrift.Job;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by shelocks on 17/3/17.
 */
@RegisterMapper(IpProxyMapper.class)
public interface IpProxyDAO {

  /**
   * @return
   */
  @SqlQuery(" select * from ip_proxy")
  List<IpProxy> listAllProxies ();

  @SqlUpdate(" update ip_proxy set is_alive = :alive where id = :id ")
  Integer updateIpProxy(@BindBean IpProxy ipProxy);

  @SqlUpdate(" delete from ip_proxy where id = :id ")
  Integer delIpProxy(@BindBean IpProxy ipProxy);

  @SqlBatch(" delete from ip_proxy where id = :id ")
  @BatchChunkSize(10000)
  void delIpProxyList(@BindBean List<IpProxy> ipProxyList);

  @SqlUpdate(" insert into ip_proxy (ip,port,type,area,private_level,speed,is_alive,last_check) values (:ip,:port,:type,:area,:privateLevel,:speed,:alive,:lastCheck) ")
  Integer addIpProxy(@BindBean IpProxy ipProxy);

  @SqlBatch(" insert into ip_proxy (ip,port,type,area,private_level,speed,is_alive,last_check) values (:ip,:port,:type,:area,:privateLevel,:speed,:alive,:lastCheck) ")
  @BatchChunkSize(10000)
  void addIpProxyList(@BindBean List<IpProxy> ipProxyList);

  @SqlQuery(" select * from ip_proxy where ip = :ip and port = :port ")
  IpProxy findProxyByIp(@Bind("ip") String ip, @Bind("port") Integer port);
}
