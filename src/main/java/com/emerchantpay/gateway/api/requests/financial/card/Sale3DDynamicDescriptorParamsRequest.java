package com.emerchantpay.gateway.api.requests.financial.card;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;

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

public class Sale3DDynamicDescriptorParamsRequest extends Request {

	private Sale3DRequest parent;

	private String merchantname;
	private String merchantcity;

	Sale3DDynamicDescriptorParamsRequest(Sale3DRequest parent) {
		this.parent = parent;
	}

	public Sale3DDynamicDescriptorParamsRequest setMerchantName(String merchantname) {
		this.merchantname = merchantname;
		return this;
	}

	public Sale3DDynamicDescriptorParamsRequest setMerchantCity(String merchantcity) {
		this.merchantcity = merchantcity;
		return this;
	}

	@Override
	public String toXML() {
		return buildRequest("dynamic_descriptor_params").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		return new RequestBuilder(root).addElement("merchant_name", merchantname).addElement("merchant_city",
				merchantcity);
	}

	public Sale3DRequest done() {
		return parent;
	}
}
