package com.example.back.common.exception;

/**
 * Exception thrown when validation fails on a request.
 */
public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }
}