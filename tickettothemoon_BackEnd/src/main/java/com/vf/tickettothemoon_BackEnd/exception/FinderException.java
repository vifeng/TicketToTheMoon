package com.vf.tickettothemoon_BackEnd.exception;

/**
 * This exception is thrown when an object cannot be found.
 */
@SuppressWarnings("serial")
public class FinderException extends ApplicationException {

    public FinderException() {}

    public FinderException(final String message) {
        super(message);
    }
}
