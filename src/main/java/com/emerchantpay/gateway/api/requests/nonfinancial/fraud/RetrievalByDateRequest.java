package com.emerchantpay.gateway.api.requests.nonfinancial.fraud;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
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

public class RetrievalByDateRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String startdate;
	private String enddate;
	private Integer page;

	public RetrievalByDateRequest() {
		super();
	}

	public RetrievalByDateRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public RetrievalByDateRequest setStartDate(String startdate) {
		this.startdate = startdate;
		return this;
	}

	public RetrievalByDateRequest setEndDate(String enddate) {
		this.enddate = enddate;
		return this;
	}

	public RetrievalByDateRequest setPage(Integer page) {
		this.page = page;
		return this;
	}

	@Override
	public String toXML() {
		return buildRequest("retrieval_request_request").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		return new RequestBuilder(root).addElement("start_date", startdate).addElement("end_date", enddate)
				.addElement("page", page);
	}

	public Request execute(Configuration configuration) {

		configuration.setTokenEnabled(false);
		configuration.setAction("retrieval_requests/by_date");
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
