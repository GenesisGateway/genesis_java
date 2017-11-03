package com.emerchantpay.gateway.api.requests.nonfinancial;

import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.CreditCardAttributes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;

public class AccountVerificationRequest extends Request implements CreditCardAttributes, CustomerInfoAttributes,
		RiskParamsAttributes {

	private String transactionType = TransactionTypes.ACCOUNT_VERIFICATION;
	private Boolean moto;

	public AccountVerificationRequest() {
		super();
	}

	public AccountVerificationRequest setMoto(Boolean moto) {
		this.moto = moto;
		return this;
	}

	@Override
	public String toXML() {
		return buildRequest("payment_transaction").toXML();
	}

	@Override
	public String getTransactionType() {
		return transactionType;
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement(buildBaseParams().toXML()).addElement("moto", moto)
				.addElement(buildCreditCardParams().toXML())
				.addElement(buildCustomerInfoParams().toXML())
				.addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML())
				.addElement("risk_params", buildRiskParams().toXML());
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}