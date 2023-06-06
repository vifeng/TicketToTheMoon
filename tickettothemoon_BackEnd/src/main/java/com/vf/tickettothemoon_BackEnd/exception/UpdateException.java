package com.vf.tickettothemoon_BackEnd.exception;

/**
 * This exception is thrown when an object cannot be updated.
 */
@SuppressWarnings("serial")
public class UpdateException extends ApplicationException {

    public UpdateException(final String message) {
        super(message);
    }
}
