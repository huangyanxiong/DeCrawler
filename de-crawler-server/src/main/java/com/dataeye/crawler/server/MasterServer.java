package com.dataeye.crawler.server;

import com.linecorp.armeria.common.http.HttpSessionProtocols;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.thrift.THttpService;

/**
 * Created by shelocks on 17/3/1.
 */
public class MasterServer {
  public static void main(String[] args) {
    ServerBuilder sb = new ServerBuilder();
    sb.port(8080, HttpSessionProtocols.HTTP); // or just port(8080, "http")
    sb.serviceAt("/scheduler", THttpService.of(new DefaultScheduleService(args))).build();

    Server server = sb.build();
    server.start().join();
  }
}
