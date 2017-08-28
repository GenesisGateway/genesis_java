package com.emerchantpay.gateway.api.requests.financial.pbv;

import java.math.BigDecimal;
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

public class PBVSaleRequest extends Request implements BillingAddressAttributes, ShippingAddressAttributes {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.PAYBYVOUCHER_SALE;
	private String transactionId;
	private String cardType;
	private String redeemType;
	private String usage;
	private String remoteIP;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;
	private String cardholder;
	private String cardnumber;
	private String expirationMonth;
	private String expirationYear;
	private String cvv;
	private String customerEmail;
	private String customerPhone;
	private String birthDate;

	private PBVSaleDynamicDescriptorParamsRequest dynamicDescriptorParams;

	public PBVSaleRequest() {
		super();
	}

	public PBVSaleRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public PBVSaleRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public PBVSaleRequest setCardType(String cardType) {
		this.cardType = cardType;
		return this;
	}

	public PBVSaleRequest setRedeemType(String redeemType) {
		this.redeemType = redeemType;
		return this;
	}

	public PBVSaleRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public PBVSaleRequest setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public PBVSaleRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public PBVSaleRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public PBVSaleRequest setCardNumber(String cardnumber) {
		this.cardnumber = cardnumber;
		return this;
	}

	public PBVSaleRequest setCardholder(String cardholder) {
		this.cardholder = cardholder;
		return this;
	}

	public PBVSaleRequest setCvv(String cvv) {
		this.cvv = cvv;
		return this;
	}

	public PBVSaleRequest setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
		return this;
	}

	public PBVSaleRequest setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
		return this;
	}

	public PBVSaleRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public PBVSaleRequest setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
		return this;
	}

	public PBVSaleRequest setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public PBVSaleDynamicDescriptorParamsRequest dynamicDescriptionParams() {
		dynamicDescriptorParams = new PBVSaleDynamicDescriptorParamsRequest(this);
		return dynamicDescriptorParams;
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
				.addElement("transaction_id", transactionId).addElement("card_type", cardType)
				.addElement("redeem_type", redeemType).addElement("usage", usage).addElement("remote_ip", remoteIP)
				.addElement("amount", convertedAmount).addElement("currency", currency)
				.addElement("card_holder", cardholder).addElement("card_number", cardnumber)
				.addElement("expiration_month", expirationMonth).addElement("expiration_year", expirationYear)
				.addElement("cvv", cvv).addElement("customer_email", customerEmail)
				.addElement("customer_phone", customerPhone).addElement("birth_date", birthDate)
				.addElement("billing_address", buildBillingAddress().toXML())
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