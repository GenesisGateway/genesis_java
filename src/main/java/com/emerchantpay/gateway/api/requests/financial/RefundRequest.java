package com.emerchantpay.gateway.api.requests.financial;

import java.math.BigDecimal;

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

public class RefundRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.REFUND;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String referenceId;
	private String currency;

	public RefundRequest() {
		super();
	}

	public RefundRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public RefundRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public RefundRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public RefundRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public RefundRequest setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public RefundRequest setReferencialId(String referencialId) {
		this.referenceId = referencialId;
		return this;
	}

	public RefundRequest setCurrency(String currency) {
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
