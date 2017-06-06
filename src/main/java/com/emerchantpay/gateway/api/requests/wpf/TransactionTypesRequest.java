package com.emerchantpay.gateway.api.requests.wpf;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;

import java.util.ArrayList;

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

public class TransactionTypesRequest extends Request {

	private WPFCreateRequest parent;
	private CustomAttributesRequest customAttributes;
	private ArrayList<CustomAttributesRequest> customAttributesList = new ArrayList<CustomAttributesRequest>();

	public TransactionTypesRequest() {
		super();
	}

	TransactionTypesRequest(WPFCreateRequest parent) {
		this.parent = parent;
	}

	public TransactionTypesRequest addTransactionType(String transactionType) {
		this.customAttributes = new CustomAttributesRequest(this, transactionType);
		this.customAttributesList.add(customAttributes);
		return this;
	}

	public TransactionTypesRequest addCustomParam(String key, String value) {
		this.customAttributes.addAttributeKey(key).addAttributeValue(value);
		return this;
	}

	@Override
	public String toXML() {
		return buildRequest("transaction_types").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		RequestBuilder builder = new RequestBuilder(root);

		for (Integer i = 0; i < customAttributesList.size(); i++) {
			builder.addElement(null, customAttributesList.get(i));
		}

		return builder;
	}

	public WPFCreateRequest done() {
		return parent;
	}
}
