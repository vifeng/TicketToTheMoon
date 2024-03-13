package com.vf.eventhubserver.exception;

public class NullException extends ApplicationException {

    public NullException() {
        super();
    }

    public NullException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NullException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NullException(final String message) {
        super(message);
    }

    public NullException(final Throwable cause) {
        super(cause);
    }
}
