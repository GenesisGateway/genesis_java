package com.emerchantpay.gateway.api.requests.financial.wallets;

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

public class NetellerRequest extends Request implements BillingAddressAttributes, ShippingAddressAttributes {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.NETELLER;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;
	private String customerAccount;
	private String accountPassword;
	private URL successUrl;
	private URL failureUrl;
	private String customerEmail;
	private String customerPhone;

	public NetellerRequest() {
		super();
	}

	public NetellerRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public NetellerRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public NetellerRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public NetellerRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public NetellerRequest setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public NetellerRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public NetellerRequest setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
		return this;
	}

	public NetellerRequest setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
		return this;
	}

	public NetellerRequest setReturnSuccessUrl(URL successUrl) {
		this.successUrl = successUrl;
		return this;
	}

	public NetellerRequest setReturnFailureUrl(URL failureUrl) {
		this.failureUrl = failureUrl;
		return this;
	}

	public NetellerRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public NetellerRequest setCustomerPhone(String customerPhone) {
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

		if (amount != null && currency != null) {

			Currency curr = new Currency();

			curr.setAmountToExponent(amount, currency);
			convertedAmount = curr.getAmount();
		}

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement("transaction_id", transactionId).addElement("usage", usage)
				.addElement("remote_ip", remoteIP).addElement("amount", convertedAmount)
				.addElement("currency", currency).addElement("customer_account", customerAccount)
				.addElement("account_password", accountPassword).addElement("return_success_url", successUrl)
				.addElement("return_failure_url", failureUrl).addElement("customer_email", customerEmail)
				.addElement("customer_phone", customerPhone)
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