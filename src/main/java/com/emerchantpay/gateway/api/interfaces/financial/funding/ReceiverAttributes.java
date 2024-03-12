package com.emerchantpay.gateway.api.interfaces.financial.funding;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ReceiverAccountTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

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

public interface ReceiverAttributes {

    default ReceiverAttributes setReceiverFirstname(String firstname) {
        getReceiverAttrParamsMap().put("first_name", firstname);
        getReceiverAttrRequestBuilder().addElement("first_name", firstname);
        return this;
    }

    default ReceiverAttributes setReceiverLastname(String lastname) {
        getReceiverAttrParamsMap().put("last_name", lastname);
        getReceiverAttrRequestBuilder().addElement("last_name", lastname);
        return this;
    }

    default ReceiverAttributes setReceiverCountry(String country) {
        getReceiverAttrParamsMap().put("country", country);
        getReceiverAttrRequestBuilder().addElement("country", country);
        return this;
    }

    default ReceiverAttributes setReceiverAccountNumber(String accountNumber) {
        getReceiverAttrParamsMap().put("account_number", accountNumber);
        getReceiverAttrRequestBuilder().addElement("account_number", accountNumber);
        return this;
    }

    default ReceiverAttributes setReceiverAccountNumberType(String accountNumberType) {
        getReceiverAttrParamsMap().put("account_number_type", ReceiverAccountTypes.validate(accountNumberType));
        getReceiverAttrRequestBuilder().addElement("account_number_type", ReceiverAccountTypes.validate(accountNumberType));
        return this;
    }

    default String getReceiverFirstname() {
        return getReceiverAttrParamsMap().get("first_name");
    }

    default String getReceiverLastname() {
        return getReceiverAttrParamsMap().get("last_name");
    }

    default String getReceiverCountry() {
        return getReceiverAttrParamsMap().get("country");
    }

    default String getReceiverAccountNumber() {
        return getReceiverAttrParamsMap().get("account_number");
    }

    default String getReceiverAccountNumberType() {
        return getReceiverAttrParamsMap().get("account_number_type");
    }

    default RequestBuilder buildReceiverParams() {
        if (getReceiverCountry() != null && getReceiverCountry().length() > 3) {
            throw new InvalidParamException("Invalid country code " + getReceiverCountry() + ". Allowed country codes are: with max 3 chars.");
        }

        if (getReceiverAccountNumberType() == null) {
            throw new InvalidParamException("Invalid Receiver Account type.");
        }

        return getReceiverAttrRequestBuilder();
    }

    RequestBuilder getReceiverAttrRequestBuilder();

    HashMap<String, String> getReceiverAttrParamsMap();

    GenesisValidator getValidator();
}