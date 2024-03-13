package com.vf.eventhubserver.exception;

/**
 * This exception is throw when a general problem occurs in the persistent layer.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException() {
        super();
    }

    public DataAccessException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(final String message) {
        super(message);
    }

    public DataAccessException(final Throwable cause) {
        super(cause);
    }
}
