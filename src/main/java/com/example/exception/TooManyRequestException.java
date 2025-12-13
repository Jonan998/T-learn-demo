package com.example.exception;

public class TooManyRequestException extends RuntimeException {

  private final int retryAfter;

  public TooManyRequestException(String message, int retryAfter) {
    super(message);
    this.retryAfter = retryAfter;
  }

  public int getRetryAfter() {
    return retryAfter;
  }
}
