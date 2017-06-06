package com.emerchantpay.gateway.api.requests.financial.wallets;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

public class EzeewalletRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.EZEEWALLET;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;
	private String sourceWalletId;
	private String sourceWalletPwd;
	private URL successUrl;
	private URL failureUrl;
	private URL notificationUrl;

	public EzeewalletRequest() {
		super();
	}

	public EzeewalletRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public EzeewalletRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public EzeewalletRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public EzeewalletRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public EzeewalletRequest setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public EzeewalletRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public EzeewalletRequest setSourceWalletId(String sourceWalletId) {
		this.sourceWalletId = sourceWalletId;
		return this;
	}

	public EzeewalletRequest setSourceWalletPwd(String sourceWalletPwd) {
		this.sourceWalletPwd = sourceWalletPwd;
		return this;
	}

	public EzeewalletRequest setReturnSuccessUrl(URL successUrl) {
		this.successUrl = successUrl;
		return this;
	}

	public EzeewalletRequest setReturnFailureUrl(URL failureUrl) {
		this.failureUrl = failureUrl;
		return this;
	}

	public EzeewalletRequest setNotificationURL(URL notificationUrl) {
		this.notificationUrl = notificationUrl;
		return this;
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

		if (amount != null && currency != null) {

			Currency curr = new Currency();

			curr.setAmountToExponent(amount, currency);
			convertedAmount = curr.getAmount();
		}

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement("transaction_id", transactionId).addElement("usage", usage)
				.addElement("remote_ip", remoteIP).addElement("amount", convertedAmount)
				.addElement("currency", currency).addElement("source_wallet_id", sourceWalletId)
				.addElement("source_wallet_pwd", sourceWalletPwd).addElement("return_success_url", successUrl)
				.addElement("return_failure_url", failureUrl).addElement("notification_url", notificationUrl);
	}

	public Request execute(Configuration configuration) {

		configuration.setAction("process");
		http = new Http(configuration);
		response = http.post(configuration.getBaseUrl(), this);

		return this;
	}

	public NodeWrapper getResponse() {
		return response;
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}
