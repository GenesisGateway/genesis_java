package com.emerchantpay.gateway.api.requests.financial.apm;

import java.math.BigDecimal;
import java.net.URL;

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

public class PaySafeCardRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionId;
	private String transactionType = TransactionTypes.PAYSAFECARD;
	private String usage;
	private String remoteIp;
	private URL successUrl;
	private URL failureUrl;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;
	private String customerEmail;
	private String customerPhone;

	private PaySafeCardAddressRequest billingAddress;
	private PaySafeCardAddressRequest shippingAddress;

	public PaySafeCardRequest() {
		super();
	}

	public PaySafeCardRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public PaySafeCardRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public PaySafeCardRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public PaySafeCardRequest setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
		return this;
	}

	public PaySafeCardRequest setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public PaySafeCardRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public PaySafeCardRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public PaySafeCardRequest setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
		return this;
	}

	public PaySafeCardRequest setReturnSuccessUrl(URL successUrl) {
		this.successUrl = successUrl;
		return this;
	}

	public PaySafeCardRequest setReturnFailureUrl(URL failureUrl) {
		this.failureUrl = failureUrl;
		return this;
	}

	public PaySafeCardAddressRequest billingAddress() {
		billingAddress = new PaySafeCardAddressRequest(this, "billing_address");
		return billingAddress;
	}

	public PaySafeCardAddressRequest shippingAddress() {
		shippingAddress = new PaySafeCardAddressRequest(this, "shipping_address");
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
				.addElement("currency", currency).addElement("billing_address", billingAddress)
				.addElement("shippingAddress", shippingAddress);
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
