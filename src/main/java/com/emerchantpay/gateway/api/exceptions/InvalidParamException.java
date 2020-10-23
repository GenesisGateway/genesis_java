package com.emerchantpay.gateway.api.exceptions;

import java.util.List;

public class InvalidParamException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidParamException() {
        super();
    }

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(String paramName, List<String> allowedValues) {
        super("Invalid value for " + paramName + ".  Allowed values are: " + allowedValues.toString());
    }
}