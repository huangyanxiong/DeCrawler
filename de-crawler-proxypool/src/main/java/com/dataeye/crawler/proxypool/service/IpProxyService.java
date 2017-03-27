package com.dataeye.crawler.proxypool.service;

import com.dataeye.crawler.dao.JobDAO;
import com.dataeye.crawler.db.DaoManager;
import com.dataeye.crawler.proxypool.dao.IpProxyDAO;
import com.dataeye.crawler.proxypool.domain.IpProxy;
import com.dataeye.crawler.util.ProfileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yann on 2017/3/20.
 */
public class IpProxyService {

    private static final Logger logger = LoggerFactory.getLogger(IpProxyService.class);

    private static IpProxyDAO ipProxyDAO = DaoManager.getInstance().getDao(IpProxyDAO.class);

    public static List<IpProxy> listAllProxies(){
        return ipProxyDAO.listAllProxies();
    }

    public static boolean updateIpProxy(IpProxy ipProxy){
        return ipProxyDAO.updateIpProxy(ipProxy) > 0;
    }

    public static void delIpProxyList(List<IpProxy> ipProxyList){
        ipProxyDAO.delIpProxyList(ipProxyList);
    }

    public static void delIpProxy(IpProxy ipProxy){
        ipProxyDAO.delIpProxy(ipProxy);
    }

    public static boolean addIpProxy(IpProxy ipProxy){
        return ipProxyDAO.addIpProxy(ipProxy) > 0;
    }

    public static void addIpProxyList(List<IpProxy> ipProxyList){
        ipProxyDAO.addIpProxyList(ipProxyList);
    }

    public static boolean checkIfExist(String ip, Integer port){
        IpProxy ipProxy = ipProxyDAO.findProxyByIp(ip, port);
        if (ipProxy != null && ipProxy.getId() > 0) {
            return true;
        }
        return false;
    }


    public static void main(String[] args){
        ProfileManager.init(args);
        IpProxyDAO ipProxyDAO = DaoManager.getInstance().getDao(IpProxyDAO.class);

//        System.out.println(ipProxyDAO.findProxyByIp("192.168.1.103", 7777));

//        List<IpProxy> proxyList = ipProxyDAO.listAllProxies(new Date().getTime() - 3 * 24 * 3600 * 1000);;
//        for (IpProxy ipProxy : proxyList){
//            System.out.println(ipProxy.getIp());
//        }

        /*
        IpProxy ipProxy1 = new IpProxy();
        ipProxy1.setIp("192.168.1.102");
        ipProxy1.setPort(8888);
        ipProxy1.setArea("广东深圳");
        ipProxy1.setSpeed(1);
        ipProxy1.setAlive(true);
        ipProxy1.setType("HTTP");
        ipProxy1.setPrivateLevel("透明");
        ipProxy1.setLastCheck(1489983480000L);

        IpProxy ipProxy2 = new IpProxy();
        ipProxy2.setIp("192.168.1.103");
        ipProxy2.setPort(7777);
        ipProxy2.setArea("广东深圳");
        ipProxy2.setSpeed(1);
        ipProxy2.setAlive(true);
        ipProxy2.setType("HTTP");
        ipProxy2.setPrivateLevel("透明");
        ipProxy2.setLastCheck(1489983480000L);

        List<IpProxy> ipProxyList = new ArrayList<IpProxy>();
        ipProxyList.add(ipProxy1);
        ipProxyList.add(ipProxy2);

        ipProxyDAO.addIpProxyList(ipProxyList);
        */
        /*
        IpProxy ipProxy2 = new IpProxy();
        ipProxy2.setId(985);
        ipProxy2.setIp("175.155.247.231");
        ipProxy2.setPort(7777);
        ipProxy2.setArea("广东深圳");
        ipProxy2.setSpeed(1);
        ipProxy2.setAlive(true);
        ipProxy2.setType("HTTP");
        ipProxy2.setPrivateLevel("透明");
        ipProxy2.setLastCheck(1489983480000L);

        System.out.println(ipProxyDAO.updateIpProxy(ipProxy2));
        */
    }

}
