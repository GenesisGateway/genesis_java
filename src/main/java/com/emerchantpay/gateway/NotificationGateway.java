package com.emerchantpay.gateway;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFReconcileRequest;
import com.emerchantpay.gateway.model.Notification;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.SHA1Hasher;
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

public class NotificationGateway implements Serializable {
	protected Configuration configuration;

	private ReconcileRequest reconcilationRequest;
	private WPFReconcileRequest wpfReconcileRequest;
	private GenesisClient client;
	private RequestBuilder response;

	private String uniqueId;
	private String signature;
	private Map<String, String> notificationParams;

	private Notification notification;

	public NotificationGateway(Configuration configuration, Map<String, String> notificationParams)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		super();
		this.configuration = configuration;
		this.notificationParams = notificationParams;

		parseNotification(notificationParams);
	}

	public void parseNotification(Map<String, String> notificationParams) throws UnsupportedEncodingException,
			NoSuchAlgorithmException {

		notification = new Notification(notificationParams);

		signature = notification.getSignature();

		if (isApiNotification() == true) {
			uniqueId = notification.getUniqueId();
		}

		if (isWPFNotification() == true) {
			uniqueId = notification.getWpfUniqueId();
		}

		if (isAuthentic() == false) {
			Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
			throw new GenesisException(errorCode, "Invalid Genesis Notification!", new Throwable());
		}
	}

	public void initReconciliation() {
		// Init Reconciliation request
		if (isApiNotification() == true) {
			reconcilationRequest = new ReconcileRequest();
			reconcilationRequest.setUniqueId(notificationParams.get("unique_id"));
			reconcilationRequest.setTransactionId(notificationParams.get("transaction_id"));
			
			client = new GenesisClient(configuration, reconcilationRequest);
			client.execute();
		}

		if (isWPFNotification() == true) {
			wpfReconcileRequest = new WPFReconcileRequest();
			wpfReconcileRequest.setUniqueId(uniqueId);
			wpfReconcileRequest.setTransactionId(notificationParams.get("wpf_transaction_id"));
			
			client = new GenesisClient(configuration, wpfReconcileRequest);
			client.execute();
		}
	}

	public Boolean isAuthentic() throws UnsupportedEncodingException, NoSuchAlgorithmException {

		if (uniqueId == null || signature == null) {
			Integer errorCode = ErrorCodes.INPUT_DATA_MISSING_ERROR.getCode();
			throw new GenesisException(errorCode, ErrorCodes.getErrorDescription(errorCode), new Throwable());
		}

		String password = configuration.getPassword();
		String hash;

		switch (signature.length()) {
			case 40:
				hash = SHA1Hasher.SHA1(uniqueId + password);
				break;
			case 128:
				hash = SHA512Hasher.SHA512(uniqueId + password);
				break;
			default:
				hash = "Empty SHA";
				break;
		}

		if (signature.equals(hash)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isApiNotification() {

		if (notification.getUniqueId() != null && !notification.getUniqueId().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isWPFNotification() {
		if (notification.getWpfUniqueId() != null && !notification.getWpfUniqueId().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Notification getNotification() {
		return notification;
	}

	public Transaction getReconcilation() {
		TransactionResult<? extends Transaction> result = client.getTransaction().getRequest();

		return result.getTransaction();
	}

	public void generateResponse() {
		response = new RequestBuilder("notification_echo");

		if (isApiNotification() == true) {
			response.addElement("unique_id", uniqueId);
		}

		if (isWPFNotification() == true) {
			response.addElement("wpf_unique_id", uniqueId);
		}
	}

	public RequestBuilder getResponse() {
		return response;
	}

	public List<Map.Entry<String,Object>> getResponseParams() {
		return response.getElements();
	}

	public String getResponseUniqueId() {
		return response.getElements().get(0).toString();
	}
}
