package com.example.back.common.exception;

/**
 * Exception thrown when validation fails on a request.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}