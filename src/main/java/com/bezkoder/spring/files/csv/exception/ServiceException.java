package com.bezkoder.spring.files.csv.exception;

public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 5201535108215156084L;

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(String message) {
    super(message);
  }
}
