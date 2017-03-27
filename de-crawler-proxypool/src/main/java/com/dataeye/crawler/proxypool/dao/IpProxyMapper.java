package com.dataeye.crawler.proxypool.dao;

import com.dataeye.crawler.proxypool.domain.IpProxy;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shelocks on 17/3/17.
 */
public class IpProxyMapper implements ResultSetMapper<IpProxy> {
  @Override
  public IpProxy map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    IpProxy ipProxy = new IpProxy();
    ipProxy.setId(r.getInt("id"));
    ipProxy.setIp(r.getString("ip"));
    ipProxy.setPort(r.getInt("port"));
    ipProxy.setArea(r.getString("area"));
    ipProxy.setAlive(r.getBoolean("is_alive"));
    ipProxy.setType(r.getString("type"));
    ipProxy.setSpeed(r.getInt("speed"));
    ipProxy.setLastCheck(r.getLong("last_check"));
    ipProxy.setPrivateLevel(r.getString("private_level"));

    return ipProxy;
  }
}
