package com.emerchantpay.gateway.api.requests.financial.pbv;

import java.math.BigDecimal;
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

public class PBVYeePayRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.PAYBYVOUCHER_YEEPAY;
	private String transactionId;
	private String cardType;
	private String redeemType;
	private String usage;
	private String remoteIP;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;
	private String productName;
	private String productCategory;
	private String customerId;
	private String customerBankId;
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String bankAccountNumber;

	public PBVYeePayRequest() {
		super();
	}

	public PBVYeePayRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public PBVYeePayRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public PBVYeePayRequest setCardType(String cardType) {
		this.cardType = cardType;
		return this;
	}

	public PBVYeePayRequest setRedeemType(String redeemType) {
		this.redeemType = redeemType;
		return this;
	}

	public PBVYeePayRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public PBVYeePayRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public PBVYeePayRequest setAmount(BigDecimal amount) {

		this.amount = amount;

		return this;
	}

	public PBVYeePayRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public PBVYeePayRequest setCustomerId(String customerId) {
		this.customerId = customerId;
		return this;
	}

	public PBVYeePayRequest setCustomerBankId(String customerBankId) {
		this.customerBankId = customerBankId;
		return this;
	}

	public PBVYeePayRequest setCustomerName(String customerName) {
		this.customerName = customerName;
		return this;
	}

	public PBVYeePayRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public PBVYeePayRequest setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
		return this;
	}

	public PBVYeePayRequest setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public PBVYeePayRequest setProductCategory(String productCategory) {
		this.productCategory = productCategory;
		return this;
	}

	public PBVYeePayRequest setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
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
				.addElement("transaction_id", transactionId).addElement("card_type", cardType)
				.addElement("redeem_type", redeemType).addElement("usage", usage).addElement("remote_ip", remoteIP)
				.addElement("amount", convertedAmount).addElement("currency", currency)
				.addElement("customer_id_number", customerId).addElement("customer_bank_id", customerBankId)
				.addElement("customer_name", customerName).addElement("customer_email", customerEmail)
				.addElement("customer_phone", customerPhone).addElement("product_name", productName)
				.addElement("product_category", productCategory).addElement("bank_account_number", bankAccountNumber);
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