package com.emerchantpay.gateway.api.interfaces.financial.threeds.v2;

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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public interface MethodAttributes {

    default MethodAttributes set3dsV2MethodCallbackUrl(URL callbackUrl) {
        if (getValidator().isValidUrl("callback_url", String.valueOf(callbackUrl))) {
            get3DSv2MethodAttrParamsMap().put("callback_url", String.valueOf(callbackUrl));
            get3DSv2MethodAttrRequestBuilder().addElement("callback_url", callbackUrl);
        }

        return this;
    }

    default String get3dsV2MethodCallbackUrl() {
        return get3DSv2MethodAttrParamsMap().get("callback_url");
    }

    default RequestBuilder build3DSv2MethodParams() {
        ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
        if (invalidParams.isEmpty()) {
            return get3DSv2MethodAttrRequestBuilder();
        } else {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }
    }

    RequestBuilder get3DSv2MethodAttrRequestBuilder();

    HashMap<String, String> get3DSv2MethodAttrParamsMap();

    GenesisValidator getValidator();
}
