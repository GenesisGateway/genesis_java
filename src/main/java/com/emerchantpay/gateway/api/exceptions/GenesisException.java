package com.emerchantpay.gateway.api.exceptions;

public class GenesisException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GenesisException(Integer code, String message, Throwable cause) {
		super(message, cause);
	}

	public GenesisException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenesisException(String message) {
		super(message);
	}

	public GenesisException() {
		super();
	}
}
