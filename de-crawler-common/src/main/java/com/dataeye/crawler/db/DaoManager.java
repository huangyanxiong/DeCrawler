package com.dataeye.crawler.db;

import com.dataeye.crawler.util.ProfileManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.skife.jdbi.v2.DBI;

import java.beans.PropertyVetoException;

/**
 * Created by shelocks on 17/3/16.
 */
public class DaoManager {
  private final ComboPooledDataSource ds = new ComboPooledDataSource();
  private DBI dbi;

  private final static DaoManager instance = new DaoManager();

  private DaoManager() {
    ds.setJdbcUrl(ProfileManager.get().getProperty("url"));
    try {
      ds.setDriverClass(ProfileManager.get().getProperty("driver"));
    } catch (PropertyVetoException e) {
      throw new RuntimeException(e.getCause());
    }
    ds.setUser(ProfileManager.get().getProperty("username"));
    ds.setPassword(ProfileManager.get().getProperty("password"));

    dbi = new DBI(ds);

  }

  public static DaoManager getInstance() {
    return instance;
  }

  public <DaoType> DaoType getDao(Class<DaoType> daoClazz) {
    return dbi.onDemand(daoClazz);
  }

}
