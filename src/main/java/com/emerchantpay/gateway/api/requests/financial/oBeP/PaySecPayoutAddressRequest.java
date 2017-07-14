package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;

import java.util.List;
import java.util.Map;

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

public class PaySecPayoutAddressRequest extends Request {

	private PaySecPayoutRequest parent;

	private String firstname;
	private String lastname;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipCode;

	protected String tagName;

	PaySecPayoutAddressRequest(PaySecPayoutRequest parent, String tagName) {
		this.parent = parent;
		this.tagName = tagName;
	}

	// Billing Address
	public PaySecPayoutAddressRequest setFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public PaySecPayoutAddressRequest setLastname(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public PaySecPayoutAddressRequest setAddress1(String address1) {
		this.address1 = address1;
		return this;
	}

	public PaySecPayoutAddressRequest setAddress2(String address2) {
		this.address2 = address2;
		return this;
	}

	public PaySecPayoutAddressRequest setCity(String city) {
		this.city = city;
		return this;
	}

	public PaySecPayoutAddressRequest setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}

	public PaySecPayoutAddressRequest setState(String state) {
		this.state = state;
		return this;
	}

	public PaySecPayoutAddressRequest setCountry(String country) {
		this.country = country;
		return this;
	}

	@Override
	public String toXML() {
		return buildRequest(tagName).toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		return new RequestBuilder(root).addElement("first_name", firstname).addElement("last_name", lastname)
				.addElement("address1", address1).addElement("address2", address2).addElement("city", city)
				.addElement("zip_code", zipCode).addElement("state", state).addElement("country", country);
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest(tagName).getElements();
	}

	public PaySecPayoutRequest done() {
		return parent;
	}
}
