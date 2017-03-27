package com.dataeye.crawler.client;

/**
 * Created by shelocks on 17/3/14.
 */
public class DownloaderNotFoundException extends RuntimeException {
  public DownloaderNotFoundException() {
  }

  public DownloaderNotFoundException(String message) {
    super(message);
  }

  public DownloaderNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public DownloaderNotFoundException(Throwable cause) {
    super(cause);
  }

  public DownloaderNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
