package com.vf.eventhubserver.exception;

/** This exception is thrown when an object cannot be updated. */
public class UpdateException extends ApplicationException {

  public UpdateException() {
    super();
  }

  public UpdateException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public UpdateException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UpdateException(final String message) {
    super(message);
  }

  public UpdateException(final Throwable cause) {
    super(cause);
  }
}
