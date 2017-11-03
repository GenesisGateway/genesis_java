package com.emerchantpay.gateway.api;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @license     http://opensource.org/licenses/MIT The MIT License
 */

import com.emerchantpay.gateway.api.interfaces.AddressAttributes;
import com.emerchantpay.gateway.api.interfaces.BaseAttributes;

import java.io.Serializable;

/**
 * Abstract class for fluent interface request builders.
 */
public abstract class Request implements BaseAttributes, AddressAttributes, Serializable {

	public String toXML() {
		throw new UnsupportedOperationException();
	}

	public String toQueryString(String parent) {
		throw new UnsupportedOperationException();
	}

	public String toQueryString() {
		throw new UnsupportedOperationException();
	}

	public String getKind() {
		return null;
	}

	protected String buildXMLElement(Object element) {
		return RequestBuilder.buildXMLElement(element);
	}

	protected String buildXMLElement(String name, Object element) {
		return RequestBuilder.buildXMLElement(name, element);
	}

	public Request getRequest() {
		return this;
	}

	public String getTransactionType() {
		return null;
	}
}
