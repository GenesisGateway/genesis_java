package com.emerchantpay.gateway.model;

import java.util.Map;

public class Notification {
	private String rawDocument;
	private String eci;

	// API Notification
	private String uniqueId;
	private String signature;
	private String transactionId;
	private String transactionType;
	private String terminalToken;
	private String status;
	private String amount;
	private String partialApproval;
	private String event;
	private String avsResponseCode;
	private String avsReponseText;

	//WPF Notification
	private String wpfUniqueId;
	private String wpfTransactionId;
	private String wpfStatus;
	private String paymentTransactionType;
	private String paymentTransactionTerminalToken;
	private String paymentTransactionUniqueId;
	private String paymentTransactionAmount;
	private String paymentTransactionPartialApproval;
	private String notificationType;
	private String paymentTransactionAvsCode;
	private String paymentTransactionAvsText;

	public Notification(Map<String, String> notificationParams) {
		this.rawDocument = notificationParams.toString();
		this.eci = notificationParams.get("eci");

		// API Notification
		this.uniqueId = notificationParams.get("unique_id");
		this.signature = notificationParams.get("signature");
		this.transactionId = notificationParams.get("transaction_id");
		this.transactionType = notificationParams.get("transaction_type");
		this.terminalToken = notificationParams.get("terminal_token");
		this.status = notificationParams.get("notification_status");
		this.amount = notificationParams.get("amount");
		this.partialApproval = notificationParams.get("partial_approval");
		this.event = notificationParams.get("event");
		this.avsResponseCode = notificationParams.get("avs_response_code");
		this.avsReponseText = notificationParams.get("avs_response_text");

		// WPF Notification
		this.wpfUniqueId = notificationParams.get("wpf_unique_id");
		this.wpfTransactionId = notificationParams.get("wpf_transaction_id");
		this.wpfStatus = notificationParams.get("wpf_status");
		this.paymentTransactionType = notificationParams.get("payment_transaction_transaction_type");
		this.paymentTransactionTerminalToken = notificationParams.get("payment_transaction_terminal_token");
		this.paymentTransactionUniqueId = notificationParams.get("payment_transaction_unique_id");
		this.paymentTransactionAmount = notificationParams.get("payment_transaction_amount");
		this.paymentTransactionPartialApproval = notificationParams.get("payment_transaction_partial_approval");
		this.notificationType = notificationParams.get("notification_type");
		this.paymentTransactionAvsCode = notificationParams.get("payment_transaction_avs_response_code");
		this.paymentTransactionAvsText = notificationParams.get("payment_transaction_avs_response_text");
	}

	// API Notification
	public String getUniqueId() {
		return uniqueId;
	}

	public String getSignature() {
		return signature;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getTerminalToken() {
		return terminalToken;
	}

	public String getNotificationStatus() {
		return status;
	}

	public String getAmount() {
		return amount;
	}

	public String getPartialApproval() {
		return partialApproval;
	}

	public String getEvent() {
		return event;
	}

	public String getAvsResponseCode() {
		return avsResponseCode;
	}

	public String  getAvsReponseText() {
		return avsReponseText;
	}

	// WPF Notification
	public String getWpfUniqueId() {
		return wpfUniqueId;
	}

	public String getWpfTransactionId() {
		return wpfTransactionId;
	}

	public String getWpfStatus() {
		return wpfStatus;
	}

	public String getPaymentTransactionType() {
		return paymentTransactionType;
	}

	public String getPaymentTransactionTerminalToken() {
		return paymentTransactionTerminalToken;
	}

	public String getPaymentTransactionUniqueId() {
		return paymentTransactionUniqueId;
	}

	public String getPaymentTransactionAmount() {
		return paymentTransactionAmount;
	}

	public String getPaymentTransactionPartialApproval() {
		return paymentTransactionPartialApproval;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public String getPaymentTransactionAvsCode() {
		return paymentTransactionAvsCode;
	}

	public String getPaymentTransactionAvsText() {
		return paymentTransactionAvsText;
	}

	public String getEci() {
		return eci;
	}

	public String getDocument() {
		return rawDocument;
	}
}
