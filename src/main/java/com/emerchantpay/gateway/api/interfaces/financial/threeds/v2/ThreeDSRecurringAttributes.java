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
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.ArrayList;
import java.util.HashMap;

public interface ThreeDSRecurringAttributes {

    default ThreeDSRecurringAttributes set3dsV2RecurringExpirationDate(String expirationDate) {
        if(getValidator().isValidDate(expirationDate, "expiration_date")) {
            get3DSv2RecurringAttrParamsMap().put("expiration_date ", expirationDate);
            get3DSv2RecurringAttrRequestBuilder().addElement("expiration_date ", expirationDate);
        }
        return this;
    }

    default String get3dsV2RecurringExpirationDate() {
        return get3DSv2RecurringAttrParamsMap().get("expiration_date ");
    }

    default ThreeDSRecurringAttributes set3dsV2RecurringFrequency(Integer frequency) {

        get3DSv2RecurringAttrParamsMap().put("frequency", String.valueOf(frequency));
        get3DSv2RecurringAttrRequestBuilder().addElement("frequency", frequency);
        return this;
    }

    default Integer get3dsV2RecurringFrequency() {
        String frequencyStr = get3DSv2RecurringAttrParamsMap().get("frequency");
        if(frequencyStr != null){
            return Integer.parseInt(frequencyStr);
        } else{
            return null;
        }
    }

    default RequestBuilder build3DSv2RecurringParams() {

        if (get3dsV2RecurringFrequency() != null && (get3dsV2RecurringFrequency() < 1 || get3dsV2RecurringFrequency() > 9999)) {
            throw new InvalidParamException("Invalid value for 3DS frequency. Values between 1 and 9999 are accepted");
        }

        ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
        if (invalidParams.isEmpty()) {
            return get3DSv2RecurringAttrRequestBuilder();
        } else {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }
    }

    RequestBuilder get3DSv2RecurringAttrRequestBuilder();

    HashMap<String, String> get3DSv2RecurringAttrParamsMap();

    GenesisValidator getValidator();
}