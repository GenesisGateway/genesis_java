package com.emerchantpay.gateway.api.exceptions;

import com.emerchantpay.gateway.api.constants.ErrorCodes;

public class ResponseException extends GenesisException {

	private static final long serialVersionUID = 1L;

	public ResponseException(Integer code, String message, Throwable cause) {
		super(code, message, cause);
		message = "Invalid/Unexpected format!";
	}

	public ResponseException(Integer code, String message) {
		super("Code: " + code + " Description: " + ErrorCodes.getErrorDescription(code),
				new GenesisException("Technical Message: " + message));
	}

	public ResponseException() {
		super();
	}
}
