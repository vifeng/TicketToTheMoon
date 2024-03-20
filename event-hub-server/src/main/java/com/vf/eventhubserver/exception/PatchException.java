package com.vf.eventhubserver.exception;

/** This exception is thrown when an object cannot be updated. */
public class PatchException extends ApplicationException {

  public PatchException() {
    super();
  }

  public PatchException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public PatchException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public PatchException(final String message) {
    super(message);
  }

  public PatchException(final Throwable cause) {
    super(cause);
  }
}
