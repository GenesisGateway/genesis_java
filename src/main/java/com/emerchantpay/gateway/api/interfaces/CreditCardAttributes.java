package com.emerchantpay.gateway.api.interfaces;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @license     http://opensource.org/licenses/MIT The MIT License
 */

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.HashMap;

public interface CreditCardAttributes {

    RequestBuilder requestBuilder = new RequestBuilder("");
    GenesisValidator validator = new GenesisValidator();

    HashMap<String, String> paramsMap = new HashMap<String, String>();

    // Credit Card params
    default CreditCardAttributes setCardHolder(String cardholder) {
        if (validator.isValidCardHolder(cardholder)) {
            paramsMap.put("card_holder", cardholder);
            requestBuilder.addElement("card_holder", cardholder);
        }

        return this;
    }

    default CreditCardAttributes setCardNumber(String cardnumber) {
        if (validator.isValidCardNumber(cardnumber)) {
            paramsMap.put("card_number", cardnumber);
            requestBuilder.addElement("card_number", cardnumber);
        }

        return this;
    }

    default CreditCardAttributes setCvv(String cvv) {
        if (validator.isValidCvv(cvv)) {
            paramsMap.put("cvv", cvv);
            requestBuilder.addElement("cvv", cvv);
        }

        return this;
    }

    default CreditCardAttributes setExpirationMonth(String expirationMonth) {
        if (validator.isValidExpMonth(expirationMonth)) {
            paramsMap.put("expiration_month", expirationMonth);
            requestBuilder.addElement("expiration_month", expirationMonth);
        }

        return this;
    }

    default CreditCardAttributes setExpirationYear(String expirationYear) {
        if (validator.isValidExpYear(expirationYear)) {
            paramsMap.put("expiration_year", expirationYear);
            requestBuilder.addElement("expiration_year", expirationYear);
        }

        return this;
    }

    default CreditCardAttributes setBirthDate(String birthDate) {
        paramsMap.put("birth_date", birthDate);
        requestBuilder.addElement("birth_date", birthDate);
        return this;
    }

    default String getCardHolder() {
        return paramsMap.get("card_holder");
    }

    default String getCardNumber() {
        return paramsMap.get("card_number");
    }

    default String getExpirationMonth() {
        return paramsMap.get("expiration_month");
    }

    default String getExpirationYear() {
        return paramsMap.get("expiration_year");
    }


    default RequestBuilder buildCreditCardParams() {
        return requestBuilder;
    }
}