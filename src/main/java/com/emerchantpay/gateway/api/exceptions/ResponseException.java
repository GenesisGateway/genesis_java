package com.emerchantpay.gateway.api.exceptions;

import com.emerchantpay.gateway.api.constants.ErrorCodes;

public class ResponseException extends GenesisException {

	private static final long serialVersionUID = 1L;

	public ResponseException(Integer code, String message, Throwable cause) {
		super(code, message, cause);
		message = "Invalid/Unexpected format!";
	}

	public ResponseException(Integer code) {
		super("code: "+ code + " message: " + ErrorCodes.getErrorDescription(code), null);
	}

	public ResponseException() {
		super();
	}
}
