package com.emerchantpay.gateway.api.exceptions;

import java.util.ArrayList;

public class RegexException extends GenesisException {
    private static final long serialVersionUID = 1L;

    public RegexException(String message) {
        super();
    }

    public RegexException(ArrayList<String> messages) {
        super("Please check input data for errors. " + String.valueOf(messages) +  " has invalid format");
    }

    public RegexException() {
        super();
    }
}
