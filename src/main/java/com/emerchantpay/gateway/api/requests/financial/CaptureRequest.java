package com.emerchantpay.gateway.api.requests.financial;

import java.math.BigDecimal;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

public class CaptureRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.CAPTURE;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String referenceId;
	private String currency;

	public CaptureRequest() {
		super();
	}

	public CaptureRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public CaptureRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public CaptureRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public CaptureRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public CaptureRequest setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public CaptureRequest setReferencialId(String referencialId) {
		this.referenceId = referencialId;
		return this;
	}

	public CaptureRequest setCurrency(String currency) {
		this.currency = currency;
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
				.addElement("reference_id", referenceId).addElement("currency", currency);
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
}
