package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ScaExemptions;
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

public interface ScaAttributes {

    // SCA Params
    default ScaAttributes setScaExemption(String scaExemption) {
        getScaAttrParamsMap().put("exemption", scaExemption);
        getScaAttrRequestBuilder().addElement("exemption", scaExemption);
        return this;
    }

    default ScaAttributes setScaVisaMerchantId(String scaVisaMerchantId) {
        getScaAttrParamsMap().put("visa_merchant_id", scaVisaMerchantId);
        getScaAttrRequestBuilder().addElement("visa_merchant_id", scaVisaMerchantId);
        return this;
    }

    default RequestBuilder buildScaParams() {
        return getScaAttrRequestBuilder();
    }

    default HashMap<String, String> getScaConditionalRequiredFields() {
        if (getScaAttrParamsMap().get("exemption") == ScaExemptions.EXEMPTION_TRUSTED_MERCHANT) {
            return new HashMap<String, String>() {
                {
                    put(RequiredParameters.scaVisaMerchantId, getScaAttrParamsMap().get("visa_merchant_id"));
                }
            };
        } else {
            return new HashMap<>();
        }
    }

    RequestBuilder getScaAttrRequestBuilder();

    HashMap<String, String> getScaAttrParamsMap();
}