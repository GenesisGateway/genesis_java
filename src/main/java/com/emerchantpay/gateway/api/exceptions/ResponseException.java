package com.emerchantpay.gateway.api.exceptions;

public class ResponseException extends GenesisException {

	private static final long serialVersionUID = 1L;

	public ResponseException(Integer code, String message, Throwable cause) {
		super(code, message, cause);
		message = "Invalid/Unexpected format!";
	}

	public ResponseException() {
		super();
	}
}
