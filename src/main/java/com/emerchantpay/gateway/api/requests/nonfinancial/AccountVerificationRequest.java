package com.emerchantpay.gateway.api.requests.nonfinancial;

import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

public class AccountVerificationRequest extends Request implements BillingAddressAttributes, ShippingAddressAttributes, RiskParamsAttributes {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.ACCOUNT_VERIFICATION;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private Boolean moto;
	private String cardholder;
	private String cardnumber;
	private String expirationMonth;
	private String expirationYear;
	private String cvv;
	private String customerEmail;
	private String customerPhone;

	public AccountVerificationRequest() {
		super();
	}

	public AccountVerificationRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public AccountVerificationRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public AccountVerificationRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public AccountVerificationRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public AccountVerificationRequest setMoto(Boolean moto) {
		this.moto = moto;
		return this;
	}

	public AccountVerificationRequest setCardNumber(String cardnumber) {
		this.cardnumber = cardnumber;
		return this;
	}

	public AccountVerificationRequest setCardHolder(String cardholder) {
		this.cardholder = cardholder;
		return this;
	}

	public AccountVerificationRequest setCvv(String cvv) {
		this.cvv = cvv;
		return this;
	}

	public AccountVerificationRequest setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
		return this;
	}

	public AccountVerificationRequest setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
		return this;
	}

	public AccountVerificationRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public AccountVerificationRequest setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
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

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement("transaction_id", transactionId).addElement("usage", usage)
				.addElement("remote_ip", remoteIP).addElement("moto", moto).addElement("card_holder", cardholder)
				.addElement("card_number", cardnumber).addElement("expiration_month", expirationMonth)
				.addElement("expiration_year", expirationYear).addElement("cvv", cvv)
				.addElement("customer_email", customerEmail).addElement("customer_phone", customerPhone)
				.addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML())
				.addElement("risk_params", buildRiskParams());
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