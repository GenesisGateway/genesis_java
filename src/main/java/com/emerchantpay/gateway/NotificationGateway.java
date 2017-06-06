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
import com.emerchantpay.gateway.util.SHA1Hasher;

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

public class NotificationGateway extends Request {
	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private URL notificationUrl;
	private String uniqueId;
	private String signature;
	private String transactionId;
	private String transactionType;
	private String terminalToken;
	private String status;
	private BigDecimal amount;
	private String partialApproval;
	private String eci;
	private String event;

	public NotificationGateway(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public NotificationGateway setNotificationURL(URL notificationUrl) {
		this.notificationUrl = notificationUrl;
		return this;
	}

	public NotificationGateway setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		return this;
	}

	public NotificationGateway setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public NotificationGateway setSignature(String signature) {

		this.signature = signature;
		return this;
	}

	public NotificationGateway setTransactionType(String transactionType) {
		this.transactionType = transactionType;
		return this;
	}

	public NotificationGateway setTerminalToken(String terminalToken) {
		this.terminalToken = terminalToken;
		return this;
	}

	public NotificationGateway setStatus(String status) {
		this.status = status;
		return this;
	}

	public NotificationGateway setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public NotificationGateway setPartialApproval(String partialApproval) {
		this.partialApproval = partialApproval;
		return this;
	}

	public NotificationGateway setEci(String eci) {
		this.eci = eci;
		return this;
	}

	public NotificationGateway setEvent(String event) {
		this.event = event;
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

		return new RequestBuilder(root).addElement("unique_id", uniqueId).addElement("signature", signature)
				.addElement("transaction_id", transactionId).addElement("transaction_type", transactionType)
				.addElement("terminal_token", terminalToken).addElement("status", status).addElement("amount", amount)
				.addElement("partial_approval", partialApproval).addElement("eci", eci).addElement("event", event);
	}

	public Request execute(Configuration configuration) {

		http = new Http(configuration);

		try {

			if (signature.equals(SHA1Hasher.SHA1(uniqueId + configuration.getPassword()))) {
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
