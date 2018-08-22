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
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public interface AsyncAttributes {

    RequestBuilder requestBuilder = new RequestBuilder("");

    // Genesis validator
    GenesisValidator validator = new GenesisValidator();

    HashMap<String, String> paramsMap = new HashMap<String, String>();

    // Async Params
    default AsyncAttributes setReturnSuccessUrl(URL successUrl) {
        if (validator.isValidUrl("return_success_url", String.valueOf(successUrl))) {
            paramsMap.put("return_success_url", String.valueOf(successUrl));
            requestBuilder.addElement("return_success_url", successUrl);
        }

        return this;
    }

    default AsyncAttributes setReturnFailureUrl(URL failureUrl) {
        if (validator.isValidUrl("return_failure_url", String.valueOf(failureUrl))) {
            paramsMap.put("return_failure_url", String.valueOf(failureUrl));
            requestBuilder.addElement("return_failure_url", failureUrl);
        }

        return this;
    }

    default String getReturnSuccessUrl() {
        return paramsMap.get("return_success_url");
    }

    default String getReturnFailureUrl() {
        return paramsMap.get("return_failure_url");
    }

    default RequestBuilder buildAsyncParams() {
        return requestBuilder;
    }
}
