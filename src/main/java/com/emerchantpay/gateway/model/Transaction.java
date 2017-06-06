package com.emerchantpay.gateway.model;

import java.math.BigDecimal;

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

		if (this.amount != null && this.currency != null) {

			Currency curr = new Currency();

			curr.setExponentToAmount(this.amount, this.currency);
			this.amount = curr.getAmount();
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

	public String getDocument() {
		return rawDocument;
	}
}
