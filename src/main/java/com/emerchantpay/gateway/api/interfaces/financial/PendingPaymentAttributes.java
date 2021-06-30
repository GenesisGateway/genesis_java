package com.emerchantpay.gateway.api.interfaces.financial;

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

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.ArrayList;
import java.util.HashMap;

public interface PendingPaymentAttributes {

    default PendingPaymentAttributes setReturnPendingUrl(String pendingUrl) {
        //Validate only if URL is not blank
        if (pendingUrl != null && !pendingUrl.isEmpty()) {
            getValidator().isValidUrl(RequiredParameters.returnPendingUrl, pendingUrl);
        }
        getPendingPaymentAttrParamsMap().put(RequiredParameters.returnPendingUrl, pendingUrl);
        getPendingPaymentAttrRequestBuilder().addElement(RequiredParameters.returnPendingUrl, pendingUrl);
        return this;
    }

    default String getReturnPendingUrl() {
        return getPendingPaymentAttrParamsMap().get(RequiredParameters.returnPendingUrl);
    }

    default RequestBuilder buildPendingPaymentParams() {
        ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
        if (!invalidParams.isEmpty()) {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        } else {
            return getPendingPaymentAttrRequestBuilder();
        }
    }

    RequestBuilder getPendingPaymentAttrRequestBuilder();

    HashMap<String, String> getPendingPaymentAttrParamsMap();

    GenesisValidator getValidator();
}
