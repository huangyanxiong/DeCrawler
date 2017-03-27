package com.dataeye.crawler.proxypool;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by shelocks on 17/3/15.
 */
public class ProxyPool {
  public static void main(String[] args) throws IOException {

    Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("119.179.141.85",1118));

    OkHttpClient okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();

    Request request = new Request.Builder().url("http://www.baidu.com").head().build();
    Response response = okHttpClient.newCall(request).execute();

    System.out.println(response);
  }
}
