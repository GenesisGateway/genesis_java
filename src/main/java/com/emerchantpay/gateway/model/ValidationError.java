package com.emerchantpay.gateway.model;

import com.emerchantpay.gateway.api.constants.ErrorCodes;

/**
 * Represents an validation error from the gateway.
 */
public class ValidationError {
	private String attribute;
	private ErrorCodes code;
	private String message;

	public ValidationError(String attribute, ErrorCodes code, String message) {
		this.attribute = attribute;
		this.code = code;
		this.message = message;
	}

	/**
	 * Returns the attribute that this error references, e.g. amount or
	 * expirationDate.
	 * 
	 * @return the attribute.
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * Returns the {@link ValidationErrorCode} for the specific validation
	 * error.
	 * 
	 * @return a {@link ValidationErrorCode}.
	 */
	public ErrorCodes getCode() {
		return code;
	}

	/**
	 * Returns the message associated with the validation error. Messages may
	 * change over time; rely on {@link #getCode()} for comparisons.
	 * 
	 * @return a String for the message.
	 */
	public String getMessage() {
		return message;
	}
}
