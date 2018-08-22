package com.emerchantpay.gateway.api.requests.financial.wallets;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.NotificationAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

public class EzeewalletRequest extends Request implements PaymentAttributes, NotificationAttributes, AsyncAttributes {

	private String transactionType = TransactionTypes.EZEEWALLET;
	private BigDecimal amount;
	private String currency;
	private String sourceWalletId;
	private String sourceWalletPwd;

	// Required params
	private HashMap<String, String> requiredParams = new HashMap<String, String>();

	// GenesisValidator
	private GenesisValidator validator = new GenesisValidator();

	public EzeewalletRequest() {
		super();
	}

	@Override
	public PaymentAttributes setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public PaymentAttributes setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	@Override
	public String getCurrency() {
		return currency;
	}

	public EzeewalletRequest setSourceWalletId(String sourceWalletId) {
		this.sourceWalletId = sourceWalletId;
		return this;
	}

	public EzeewalletRequest setSourceWalletPwd(String sourceWalletPwd) {
		this.sourceWalletPwd = sourceWalletPwd;
		return this;
	}

	@Override
	public String getTransactionType() {
		return transactionType;
	}

	@Override
	public String toXML() {
		return buildRequest("payment_transaction").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		// Set required params
		requiredParams.put(RequiredParameters.transactionId, getTransactionId());
		requiredParams.put(RequiredParameters.amount, getAmount().toString());
		requiredParams.put(RequiredParameters.currency, getCurrency());
		requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
		requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
		requiredParams.put(RequiredParameters.notificationUrl, getNotificationUrl());
		requiredParams.put(RequiredParameters.sourceWalletId, sourceWalletId);
		requiredParams.put(RequiredParameters.sourceWalletPwd, sourceWalletPwd);

		// Validate request
		validator.isValidRequest(requiredParams);

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement(buildBaseParams().toXML())
				.addElement(buildPaymentParams().toXML())
				.addElement(buildNotificationParams().toXML())
				.addElement(buildAsyncParams().toXML())
				.addElement("source_wallet_id", sourceWalletId)
				.addElement("source_wallet_pwd", sourceWalletPwd);
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}
