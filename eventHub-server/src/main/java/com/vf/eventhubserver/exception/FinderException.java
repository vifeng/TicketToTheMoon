package com.vf.eventhubserver.exception;

/** This exception is thrown when an object cannot be found. */
public class FinderException extends ApplicationException {

  public FinderException() {
    super();
  }

  public FinderException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public FinderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public FinderException(final String message) {
    super(message);
  }

  public FinderException(final Throwable cause) {
    super(cause);
  }
}
