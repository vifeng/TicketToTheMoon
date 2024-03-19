package com.vf.eventhubserver.exception;

public abstract class ApplicationException extends RuntimeException {

  protected ApplicationException() {}

  protected ApplicationException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  protected ApplicationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  protected ApplicationException(final String message) {
    super(message);
  }

  protected ApplicationException(final Throwable cause) {
    super(cause);
  }
}
