package com.vf.eventhubserver.exception;

/**
 * This exception is throw when an object already exists in the persistent layer and we want to add
 * another one with the same identifier.
 */
public class DuplicateKeyException extends CreateException {

    public DuplicateKeyException() {
        super();
    }

    public DuplicateKeyException(final String message) {
        super(message);
    }


}
