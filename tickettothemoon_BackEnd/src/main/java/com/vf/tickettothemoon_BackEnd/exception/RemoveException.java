package com.vf.tickettothemoon_BackEnd.exception;

/**
 * This exception is thrown when an object cannot be deleted.
 */
public class RemoveException extends ApplicationException {

    public RemoveException() {
        super();
    }

    public RemoveException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RemoveException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public RemoveException(final String message) {
        super(message);
    }


    public RemoveException(final Throwable cause) {
        super(cause);
    }
}
