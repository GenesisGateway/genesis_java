package com.emerchantpay.gateway.api.requests.financial.pbv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.CreditCardAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PBVAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

public class PBVSaleRequest extends Request implements PaymentAttributes, CreditCardAttributes, CustomerInfoAttributes,
		DescriptorAttributes, PBVAttributes {

	private String transactionType = TransactionTypes.PAYBYVOUCHER_SALE;
	private BigDecimal amount;
	private String currency;

	// Required params
	private HashMap<String, String> requiredParams = new HashMap<String, String>();

	// GenesisValidator
	private GenesisValidator validator = new GenesisValidator();

	public PBVSaleRequest() {
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
		requiredParams.put(RequiredParameters.redeemType, getRedeemType());
		requiredParams.put(RequiredParameters.cardType, getCardType());
		requiredParams.put(RequiredParameters.amount, getAmount().toString());
		requiredParams.put(RequiredParameters.currency, getCurrency());
		requiredParams.put(RequiredParameters.cardHolder, getCardHolder());
		requiredParams.put(RequiredParameters.cardNumber, getCardNumber());
		requiredParams.put(RequiredParameters.expirationMonth, getExpirationMonth());
		requiredParams.put(RequiredParameters.expirationYear, getExpirationYear());

		// Validate request
		validator.isValidRequest(requiredParams);

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement(buildBaseParams().toXML())
				.addElement(buildPaymentParams().toXML())
				.addElement(buildCreditCardParams().toXML())
				.addElement(buildCustomerInfoParams().toXML())
				.addElement(buildPBVParams().toXML())
				.addElement("dynamic_descriptor_params", buildDescriptorParams().toXML())
				.addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML());
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}