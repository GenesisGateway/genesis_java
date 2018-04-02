package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.exceptions.DeprecatedMethodException;
import com.emerchantpay.gateway.util.Configuration;
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

public class GenesisClient extends Request {

	private Configuration configuration;
	private Request request;

	// Execute
	private Http http;
	private NodeWrapper response;

	public GenesisClient(Configuration configuration, Request request) {

		super();
		this.configuration = configuration;
		this.request = request;
	}

	public GenesisClient debugMode(Boolean enabled) {
		configuration.setDebugMode(enabled);
		return this;
	}

	public GenesisClient changeRequest(Request request) {
		this.request = request;
		return this;
	}

	public TransactionGateway getTransaction() {

		return new TransactionGateway(configuration, getResponse());
	}

	public Request execute() {
		switch (request.getTransactionType()) {
			case "wpf_payment":
				configuration.setWpfEnabled(true);
				configuration.setTokenEnabled(false);

				if (configuration.getLanguage() != null) {
					configuration.setAction(configuration.getLanguage() + "/wpf");
				} else {
					configuration.setAction("wpf");
				}
				break;
			case "wpf_reconcile":
				configuration.setWpfEnabled(true);
				configuration.setTokenEnabled(false);
				configuration.setAction("wpf/reconcile");
				break;
			case "reconcile":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(true);
				configuration.setAction("reconcile");
				break;
			case "reconcile_by_date":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(true);
				configuration.setAction("reconcile/by_date");
				break;
			case "blacklist":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(false);
				configuration.setAction("blacklists");
				break;
			case "chargeback":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(false);
				configuration.setAction("chargebacks");
				break;
			case "chargeback_by_date":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(false);
				configuration.setAction("chargebacks/by_date");
				break;
			case "reports_fraud":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(false);
				configuration.setAction("fraud_reports");
				break;
			case "reports_fraud_by_date":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(false);
				configuration.setAction("fraud_reports/by_date");
				break;
			case "retrieval_requests":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(false);
				configuration.setAction("retrieval_requests");
				break;
			case "retrieval_requests_by_date":
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(false);
				configuration.setAction("retrieval_requests/by_date");
				break;
			case "avs":
				throw new DeprecatedMethodException("AVS");
			case "abn_ideal":
				throw  new DeprecatedMethodException("ABNiDEAL");
			case "inpay":
				throw new DeprecatedMethodException("InPay");
			default:
				configuration.setWpfEnabled(false);
				configuration.setTokenEnabled(true);
				configuration.setAction("process");
				break;
		}

		http = new Http(configuration);
		response = http.post(configuration.getBaseUrl(), request);

		return this;
	}

	public NodeWrapper getResponse() {
		return response;
	}
}
