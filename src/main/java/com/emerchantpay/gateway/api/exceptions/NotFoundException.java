package com.emerchantpay.gateway.api.exceptions;

public class NotFoundException extends GenesisException {
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super();
    }
}