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

import java.util.ArrayList;
import java.util.HashMap;

public interface PproAttributes {

    default PproAttributes setNationalId(String nationalId) {
        getPproAttrParamsMap().put("national_id", nationalId);
        getPproAttrRequestBuilder().addElement("national_id", nationalId);
        return this;
    }

    default String getNationalId() {
        return getPproAttrParamsMap().get("national_id");
    }

    default PproAttributes setConsumerReference(String consumerReference) {
        getPproAttrParamsMap().put("consumer_reference", consumerReference);
        getPproAttrRequestBuilder().addElement("consumer_reference", consumerReference);
        return this;
    }

    default String getConsumerReference() {
        return getPproAttrParamsMap().get("consumer_reference");
    }

    default PproAttributes setBirthDate(String birthDate) {
        if(getValidator().isValidDate(birthDate, "birth_date")){
            getPproAttrParamsMap().put("birth_date", birthDate);
            getPproAttrRequestBuilder().addElement("birth_date", birthDate);
        }
        return this;
    }

    default String getBirthDate() {
        return getPproAttrParamsMap().get("birth_date");
    }

    default RequestBuilder buildPproParams() {
        ArrayList<String> invalidParams = new ArrayList<>(getValidator().getInvalidParams());
        if (invalidParams.isEmpty()) {
            return getPproAttrRequestBuilder();
        } else {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }
    }

    RequestBuilder getPproAttrRequestBuilder();

    HashMap<String, String> getPproAttrParamsMap();

    GenesisValidator getValidator();
}