package com.emerchantpay.gateway.api.exceptions;

public class NetworkException extends GenesisException {

	private static final long serialVersionUID = 1L;

	public NetworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public NetworkException(String message) {
		super(message);
	}

	public NetworkException() {
		super();
	}
}
