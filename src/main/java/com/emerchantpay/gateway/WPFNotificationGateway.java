package com.emerchantpay.gateway;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;
import com.emerchantpay.gateway.util.SHA512Hasher;

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

public class WPFNotificationGateway extends Request {
	private Http http;
	protected Configuration configuration;

	private NodeWrapper response;

	private URL notificationUrl;
	private String uniqueId;
	private String wpfUniqueId;
	private String signature;
	private String transactionId;
	private String transactionType;
	private String terminalToken;
	private String status;
	private String partialApproval;
	private BigDecimal amount;
	private String eci;

	public WPFNotificationGateway() {
		super();
	}

	public WPFNotificationGateway(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public WPFNotificationGateway setNotificationURL(URL notificationUrl) {
		this.notificationUrl = notificationUrl;
		return this;
	}

	public WPFNotificationGateway setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		return this;
	}

	public WPFNotificationGateway setWPFUniqueId(String wpfUniqueId) {
		this.wpfUniqueId = wpfUniqueId;
		return this;
	}

	public WPFNotificationGateway setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public WPFNotificationGateway setSignature(String signature) {

		this.signature = signature;
		return this;
	}

	public WPFNotificationGateway setTransactionType(String transactionType) {
		this.transactionType = transactionType;
		return this;
	}

	public WPFNotificationGateway setTerminalToken(String terminalToken) {
		this.terminalToken = terminalToken;
		return this;
	}

	public WPFNotificationGateway setStatus(String status) {
		this.status = status;
		return this;
	}

	public WPFNotificationGateway setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public WPFNotificationGateway setPartialApproval(String partialApproval) {
		this.partialApproval = partialApproval;
		return this;
	}

	public WPFNotificationGateway setEci(String eci) {
		this.eci = eci;
		return this;
	}

	@Override
	public String toXML() {
		return buildRequest("").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		return new RequestBuilder(root).addElement("signature", signature)
				.addElement("payment_transaction_unique_id", uniqueId)
				.addElement("payment_transaction_transaction_type", transactionType)
				.addElement("payment_transaction_amount", amount)
				.addElement("payment_transaction_partial_approval", partialApproval)
				.addElement("payment_transaction_terminal_token", terminalToken)
				.addElement("wpf_unique_id", wpfUniqueId).addElement("wpf_transaction_id", transactionId)
				.addElement("wpf_status", status).addElement("eci", eci).addElement("notification_type", "wpf");
	}

	public Request execute(Configuration configuration) {

		http = new Http(configuration);

		try {

			if (signature.equals(SHA512Hasher.SHA512(wpfUniqueId + configuration.getPassword()))) {
				response = http.postQuery(notificationUrl.toString(), this);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	public NodeWrapper getResponse() {
		return response;
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("echo_notification").getElements();
	}
}
