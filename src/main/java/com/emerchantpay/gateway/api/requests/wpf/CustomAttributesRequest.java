package com.emerchantpay.gateway.api.requests.wpf;

import java.util.ArrayList;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilderWithAttribute;

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

public class CustomAttributesRequest extends Request {

	private TransactionTypesRequest parent;

	private String transactionType;

	private ArrayList<String> keyList = new ArrayList<String>();
	private ArrayList<String> valueList = new ArrayList<String>();

	public CustomAttributesRequest() {
		super();
	}

	CustomAttributesRequest(TransactionTypesRequest parent, String transactionType) {
		this.parent = parent;
		this.transactionType = transactionType;
	}

	public CustomAttributesRequest addAttributeKey(String key) {
		keyList.add(key);
		return this;
	}

	public CustomAttributesRequest addAttributeValue(String value) {
		valueList.add(value);
		return this;
	}

	@Override
	public String toXML() {
		return buildRequest("transaction_type").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilderWithAttribute buildRequest(String root) {

		RequestBuilderWithAttribute builder;

		builder = new RequestBuilderWithAttribute(root, transactionType);

		for (int i = 0; i < keyList.size(); i++) {
			builder.addElement(keyList.get(i), valueList.get(i));
		}

		return builder;
	}

	public TransactionTypesRequest done() {
		return parent;
	}
}
