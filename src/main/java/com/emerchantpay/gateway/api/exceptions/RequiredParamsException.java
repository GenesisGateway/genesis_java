package com.emerchantpay.gateway.api.exceptions;

public class RequiredParamsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RequiredParamsException(String message) {
		super(message);
	}

	public RequiredParamsException() {
		super();
	}
}
