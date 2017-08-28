package com.emerchantpay.gateway.api.requests.financial.apm;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

public class P24Request extends Request implements BillingAddressAttributes, ShippingAddressAttributes {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.P24;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private String customerEmail;
	private String customerPhone;
	private URL successUrl;
	private URL failureUrl;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;

	public P24Request() {
		super();
	}

	public P24Request(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public P24Request setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public P24Request setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public P24Request setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public P24Request setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public P24Request setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public P24Request setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public P24Request setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
		return this;
	}

	public P24Request setReturnSuccessUrl(URL successUrl) {
		this.successUrl = successUrl;
		return this;
	}

	public P24Request setReturnFailureUrl(URL failureUrl) {
		this.failureUrl = failureUrl;
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

		return new RequestBuilder(root).addElement("transaction_id", transactionId)
				.addElement("transaction_type", transactionType).addElement("usage", usage)
				.addElement("remote_ip", remoteIP).addElement("customer_email", customerEmail)
				.addElement("customer_phone", customerPhone).addElement("return_success_url", successUrl)
				.addElement("return_failure_url", failureUrl).addElement("amount", convertedAmount)
				.addElement("currency", currency).addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML());
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
