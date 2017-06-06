package com.emerchantpay.gateway.api.requests.financial.apm;

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

public class InPayRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionId;
	private String transactionType = TransactionTypes.INPAY;
	private String usage;
	private String remoteIp;
	private URL successUrl;
	private URL failureUrl;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
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
	private String customerEmail;
	private String customerPhone;

	private InPayAddressRequest billingAddress;
	private InPayAddressRequest shippingAddress;

	public InPayRequest() {
		super();
	}

	public InPayRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public InPayRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public InPayRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public InPayRequest setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
		return this;
	}

	public InPayRequest setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public InPayRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
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

	public InPayRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public InPayRequest setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
		return this;
	}

	public InPayRequest setReturnSuccessUrl(URL successUrl) {
		this.successUrl = successUrl;
		return this;
	}

	public InPayRequest setReturnFailureUrl(URL failureUrl) {
		this.failureUrl = failureUrl;
		return this;
	}

	public InPayAddressRequest billingAddress() {
		billingAddress = new InPayAddressRequest(this, "billing_address");
		return billingAddress;
	}

	public InPayAddressRequest shippingAddress() {
		shippingAddress = new InPayAddressRequest(this, "shipping_address");
		return shippingAddress;
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
				.addElement("remote_ip", remoteIp).addElement("customer_email", customerEmail)
				.addElement("customer_phone", customerPhone).addElement("return_success_url", successUrl)
				.addElement("return_failure_url", failureUrl).addElement("amount", convertedAmount)
				.addElement("currency", currency).addElement("is_payout", isPayout)
				.addElement("customer_bank_id", customerBankId).addElement("order_description", orderDescription)
				.addElement("payout_order_id", orderId).addElement("payout_bank_country", bankCountry)
				.addElement("payout_bank_name", bankName).addElement("payout_swift", swift)
				.addElement("payout_acc_number", accNumber).addElement("payout_bank_address", bankAddress)
				.addElement("payout_owner_name", ownerName).addElement("payout_owner_address", ownerAddress)
				.addElement("billing_address", billingAddress).addElement("shippingAddress", shippingAddress);
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

	public InPayAddressRequest getBillingAddress() {
		return billingAddress;
	}
}
