package com.emerchantpay.gateway.api.exceptions;

/**
 * Thrown to indicate that the request to the payment gateway failed
 * with a Bad Request response (HTTP status code 400).
 */
public class BadRequestException extends GenesisException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super();
    }
}