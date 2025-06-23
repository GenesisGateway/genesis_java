package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

public interface RecurringTypeAttributes {

    String recurringTypeParamName = "recurring_type";

    default RecurringTypeAttributes setRecurringType(String recurringType) {
        if(Boolean.TRUE.equals(isValidRecurring(recurringType))) {
            getRecurringTypeAttrParamsMap().put(recurringTypeParamName, recurringType);
            getRecurringTypeAttrRequestBuilder().addElement(recurringTypeParamName, recurringType);
        }

        return this;
    }

    default String getRecurringType() {
        return getRecurringTypeAttrParamsMap().get(recurringTypeParamName);
    }

    default List<String> getAllowedRecurringTypes() {
        return Arrays.asList("initial", "managed");
    }

    default Boolean isValidRecurring(String recurringType) {
        List<String> allowedValues = getAllowedRecurringTypes();

        if(!allowedValues.contains(recurringType)) {
            throw new InvalidParamException("Invalid recurring type [" + recurringType + "]. Allowed recurring types are: "
                    + allowedValues);
        }

        return true;
    }

    default RequestBuilder buildRecurringAttrParams() {
        return getRecurringTypeAttrRequestBuilder();
    }

    RequestBuilder getRecurringTypeAttrRequestBuilder();

    HashMap<String, String> getRecurringTypeAttrParamsMap();
}
