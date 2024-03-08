package com.vf.eventhubserver.exception;

public class PatchFieldException extends ApplicationException {

    public PatchFieldException() {
        super();
    }

    public PatchFieldException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PatchFieldException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public PatchFieldException(final String message) {
        super(message);
    }


    public PatchFieldException(final Throwable cause) {
        super(cause);
    }
}
