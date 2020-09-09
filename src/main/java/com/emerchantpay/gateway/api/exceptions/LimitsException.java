package com.emerchantpay.gateway.api.exceptions;

public class LimitsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LimitsException(String message) {
        super(message);
    }

    public LimitsException() {
        super();
    }
}

