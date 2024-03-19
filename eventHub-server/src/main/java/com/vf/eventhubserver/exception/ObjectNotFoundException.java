package com.vf.eventhubserver.exception;

/** This exception is thrown when an object is not found in the persistent layer. */
public class ObjectNotFoundException extends FinderException {

  public ObjectNotFoundException() {
    super();
  }

  public ObjectNotFoundException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ObjectNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ObjectNotFoundException(final String message) {
    super(message);
  }

  public ObjectNotFoundException(final Throwable cause) {
    super(cause);
  }
}
