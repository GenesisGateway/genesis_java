package com.emerchantpay.gateway.api.exceptions;

import com.emerchantpay.gateway.api.constants.ErrorCodes;

public class DeprecatedMethodException extends GenesisException {

	private static final long serialVersionUID = 1L;

	public DeprecatedMethodException(String message) {
		super("Deprecated method",
				new GenesisException("Method: " + message + " is deprecated"));
	}

	public DeprecatedMethodException() {
		super();
	}
}
