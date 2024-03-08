package com.vf.eventhubserver.exception;

/**
 * This exception is thrown when an object cannot be created.
 */
public class CreateException extends ApplicationException {

    public CreateException() {
        super();
    }

    public CreateException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CreateException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public CreateException(final String message) {
        super(message);
    }


    public CreateException(final Throwable cause) {
        super(cause);
    }
}
