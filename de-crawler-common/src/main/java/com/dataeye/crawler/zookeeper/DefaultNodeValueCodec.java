package com.dataeye.crawler.zookeeper;

import com.dataeye.crawler.thrift.Worker;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public final class DefaultNodeValueCodec implements NodeValueCodec<Worker> {
  public static final DefaultNodeValueCodec INSTANCE = new DefaultNodeValueCodec();
  private static final String segmentDelimiter = ",";
  private static final String fieldDelimiter = ":";
  private static final Pattern SEGMENT_DELIMITER = Pattern.compile("\\s*" + segmentDelimiter + "\\s*");

  @Override
  public Worker decode(String segment) {
    final String[] tokens = segment.split(fieldDelimiter);

    String workerId = tokens[0];
    String biz = tokens[1];
    final Worker worker = new Worker(workerId, biz);

    return worker;
  }

  @Override
  public Set<Worker> decodeAll(String valueString) {
    Set<Worker> workers = new HashSet<>();
    try {
      for (String segment : SEGMENT_DELIMITER.split(valueString)) {
        workers.add(decode(segment));
      }
    } catch (Exception e) {
      throw new RuntimeException("invalid worker list: " + valueString, e);
    }
    if (workers.isEmpty()) {
      throw new RuntimeException("ZNode does not contain any workers.");
    }
    return workers;
  }

  @Override
  public byte[] encodeAll(Iterable<Worker> workers) {
    requireNonNull(workers, "workers");
    StringBuilder nodeValue = new StringBuilder();
    workers.forEach(worker -> nodeValue.append(worker.getId()).append(fieldDelimiter).append(
            worker.getBiz()).append(segmentDelimiter));
    //delete the last unused segment delimiter
    if (nodeValue.length() > 0) {
      nodeValue.deleteCharAt(nodeValue.length() - 1);
    }
    return nodeValue.toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public byte[] encode(Worker worker) {
    return (worker.getId() + fieldDelimiter + worker.getBiz()).getBytes(StandardCharsets.UTF_8);
  }
}