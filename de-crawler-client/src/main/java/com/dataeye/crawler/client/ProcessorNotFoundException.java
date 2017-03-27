package com.dataeye.crawler.client;

/**
 * Created by shelocks on 17/3/14.
 */
public class ProcessorNotFoundException extends RuntimeException {

  public ProcessorNotFoundException() {
  }

  public ProcessorNotFoundException(String message) {
    super(message);
  }

  public ProcessorNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProcessorNotFoundException(Throwable cause) {
    super(cause);
  }

  public ProcessorNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
