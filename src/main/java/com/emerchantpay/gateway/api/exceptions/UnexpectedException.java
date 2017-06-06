package com.emerchantpay.gateway.api.exceptions;

public class UnexpectedException extends GenesisException {

	private static final long serialVersionUID = 1L;

	public UnexpectedException(String message) {
		super(message);
	}

	public UnexpectedException(String message, Throwable cause) {
		super(message, cause);
	}
}
