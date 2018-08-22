package com.emerchantpay.gateway.api.exceptions;

public class TransactionTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TransactionTypeException(String message) {
        super(message);
    }

    public TransactionTypeException() {
        super();
    }
}
