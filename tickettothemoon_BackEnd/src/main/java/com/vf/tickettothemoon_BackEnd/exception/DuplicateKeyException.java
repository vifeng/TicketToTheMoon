package com.vf.tickettothemoon_BackEnd.exception;

/**
 * This exception is throw when an object already exists in the persistent layer and we want to add
 * another one with the same identifier.
 */
@SuppressWarnings("serial")
public class DuplicateKeyException extends CreateException {
}
