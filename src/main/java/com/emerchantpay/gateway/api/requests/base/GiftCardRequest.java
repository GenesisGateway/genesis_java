package com.emerchantpay.gateway.api.requests.base;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;

import java.math.BigDecimal;
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

public abstract class GiftCardRequest extends Request implements PaymentAttributes, CustomerInfoAttributes,
		DescriptorAttributes {

	private BigDecimal amount;
	private String currency;
	private String cardnumber;
	private String cvv;
	private String referenceId;

	@Override
	public PaymentAttributes setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public PaymentAttributes setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	@Override
	public String getCurrency() {
		return currency;
	}

	public GiftCardRequest setCardNumber(String cardnumber) {
		this.cardnumber = cardnumber;
		return this;
	}

	public GiftCardRequest setCvv(String cvv) {
		this.cvv = cvv;
		return this;
	}

	public GiftCardRequest setReferenceId(String referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	@Override
	public String toXML() {
		return buildGiftcardParams().toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildGiftcardParams().toQueryString();
	}

	protected RequestBuilder buildGiftcardParams() {

		return new RequestBuilder("").addElement(buildBaseParams().toXML())
				.addElement("reference_id", referenceId)
				.addElement(buildPaymentParams().toXML())
				.addElement("card_number", cardnumber)
				.addElement("cvv", cvv)
				.addElement(buildCustomerInfoParams().toXML())
				.addElement("dynamic_descriptor_params", buildDescriptorParams().toXML())
				.addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML());
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildGiftcardParams().getElements();
	}
}
