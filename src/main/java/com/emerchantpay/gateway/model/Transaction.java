package com.emerchantpay.gateway.model;

import java.math.BigDecimal;
import java.util.List;

import com.emerchantpay.gateway.api.exceptions.ResponseException;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.NodeWrapper;

public class Transaction {

	private String rawDocument;
	private String status;
	private String transactionType;
	private String uniqueId;
	private String transactionId;
	private Integer code;
	private String message;
	private String technicalMessage;
	private String descriptor;
	private BigDecimal amount;
	private String currency;
	private String redirectUrl;
	private String mode;
	private String timestamp;
	private Boolean sentToAcquirer;
	private String authorizationCode;
	private String responseCode;
	private String gaming;
	private String moto;
	private String partialAproval;
	private String avsResponseCode;
	private String avsResponseText;
	private List<String> dynamicDescriptorParams;
	private String customParam;

	public Transaction(NodeWrapper node) {

		this.rawDocument = node.toString();
		this.status = node.findString("status");
		this.transactionType = node.findString("transaction_type");
		this.uniqueId = node.findString("unique_id");
		this.transactionId = node.findString("transaction_id");
		this.code = node.findInteger("code");
		this.message = node.findString("message");
		this.technicalMessage = node.findString("technical_message");
		this.descriptor = node.findString("descriptor");
		this.amount = node.findBigDecimal("amount");
		this.currency = node.findString("currency");
		this.redirectUrl = node.findString("redirect_url");
		this.mode = node.findString("mode");
		this.timestamp = node.findString("timestamp");
		this.sentToAcquirer = node.findBoolean("sent_to_acquirer");
		this.authorizationCode = node.findString("authorization_code");
		this.responseCode = node.findString("response_code");
		this.gaming = node.findString("gaming");
		this.moto = node.findString("moto");
		this.partialAproval = node.findString("partial_approval");
		this.avsResponseCode = node.findString("avs_response_code");
		this.avsResponseText = node.findString("avs_response_text");
		this.dynamicDescriptorParams = node.findAllStrings("dynamic_descriptor_params");

		if (this.amount != null && this.currency != null) {

			Currency curr = new Currency();

			curr.setExponentToAmount(this.amount, this.currency);
			this.amount = curr.getAmount();
		}

		if(getStatus().equals("error")) {
			throw new ResponseException(getCode());
		}
	}

	public String getStatus() {
		return status;
	}

	public String getTranscationType() {
		return transactionType;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getTechnicalMessage() {
		return technicalMessage;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public BigDecimal getAmount() {

		return amount;
	}

	public String getCurrency() {

		return currency;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public String getMode() {
		return mode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public Boolean getSentToAcquirer() {
		return sentToAcquirer;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getGaming() {
		return gaming;
	}

	public String getMoto() {
		return moto;
	}

	public String getPartialAproval() {
		return partialAproval;
	}

	public String getAvsResponseCode() {
		return avsResponseCode;
	}

	public String getAvsResponseText() {
		return avsResponseText;
	}

	public List<String> getDynamicDescriptorParams() {
		return dynamicDescriptorParams;
	}

	public String getCustomParam(NodeWrapper node, String key) {
		this.customParam = node.findString(key);
		return customParam;
	}

	public String getDocument() {
		return rawDocument;
	}
}
