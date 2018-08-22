package com.emerchantpay.gateway.api.requests.financial.apm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

public class InPayRequest extends Request implements PaymentAttributes, CustomerInfoAttributes, AsyncAttributes {

	private String transactionType = TransactionTypes.INPAY;
	private BigDecimal amount;
	private String currency;
	private Boolean isPayout;
	private Integer customerBankId;
	private String orderDescription;
	private String orderId;
	private String bankCountry;
	private String bankName;
	private String swift;
	private String accNumber;
	private String bankAddress;
	private String ownerName;
	private String ownerAddress;

	// Required params
	private HashMap<String, String> requiredParams = new HashMap<String, String>();

	// GenesisValidator
	private GenesisValidator validator = new GenesisValidator();

	public InPayRequest() {
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

	public InPayRequest setIsPayout(Boolean isPayout) {
		this.isPayout = isPayout;
		return this;
	}

	public InPayRequest setCustomerBankId(Integer customerBankId) {
		this.customerBankId = customerBankId;
		return this;
	}

	public InPayRequest setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
		return this;
	}

	public InPayRequest setOrderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public InPayRequest setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
		return this;
	}

	public InPayRequest setBankName(String bankName) {
		this.bankName = bankName;
		return this;
	}

	public InPayRequest setSwift(String swift) {
		this.swift = swift;
		return this;
	}

	public InPayRequest setAccNumber(String accNumber) {
		this.accNumber = accNumber;
		return this;
	}

	public InPayRequest setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
		return this;
	}

	public InPayRequest setOwnerName(String ownerName) {
		this.ownerName = ownerName;
		return this;
	}

	public InPayRequest setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
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
		requiredParams.put(RequiredParameters.remoteIp, getRemoteIp());
		requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
		requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
		requiredParams.put(RequiredParameters.customerEmail, getCustomerEmail());

		// Validate request
		validator.isValidRequest(requiredParams);

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement(buildBaseParams().toXML())
				.addElement(buildPaymentParams().toXML())
				.addElement(buildCustomerInfoParams().toXML())
				.addElement(buildAsyncParams().toXML()).addElement("is_payout", isPayout)
				.addElement("customer_bank_id", customerBankId).addElement("order_description", orderDescription)
				.addElement("payout_order_id", orderId).addElement("payout_bank_country", bankCountry)
				.addElement("payout_bank_name", bankName).addElement("payout_swift", swift)
				.addElement("payout_acc_number", accNumber).addElement("payout_bank_address", bankAddress)
				.addElement("payout_owner_name", ownerName).addElement("payout_owner_address", ownerAddress)
				.addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML());
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}