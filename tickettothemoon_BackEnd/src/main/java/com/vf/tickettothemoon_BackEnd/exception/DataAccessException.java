package com.vf.tickettothemoon_BackEnd.exception;

/**
 * This exception is throw when a general problem occurs in the persistent layer.
 */
@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException {

    public DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
