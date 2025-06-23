package com.emerchantpay.gateway.api.requests.financial;

import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;

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

public class RefundRequest extends ReferenceRequest {

    public RefundRequest() {
		super();
	}

	/**
	 * Sets the reference identifier for this request.
	 *
	 * @param referencialId the reference identifier to set
	 * @return this instance of {@link <ClassName>}
	 * @deprecated and scheduled for removal since {@code 1.18.8}, use {@link #setReferenceId(String)} instead.
	 */
	@Deprecated
	public RefundRequest setReferencialId(String referencialId) {
		setReferenceId(referencialId);
		return this;
	}

	@Override
	public String getTransactionType() {
        return TransactionTypes.REFUND;
	}

	@Override
	protected HashMap<String, String> getRequiredParams(){
		super.getRequiredParams();
		requiredParams.put(RequiredParameters.currency, getCurrency());
		return requiredParams;
	}
}
