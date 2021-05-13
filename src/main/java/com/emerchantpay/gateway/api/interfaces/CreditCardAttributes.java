package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.HashMap;

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

public interface CreditCardAttributes extends CredentialOnFileAttributes {

    // Credit Card params
    default CreditCardAttributes setCardHolder(String cardholder) {
        if (getValidator().isValidCardHolder(cardholder)) {
            getCreditCardAttrParamsMap().put("card_holder", cardholder);
            getCreditCardAttrRequestBuilder().addElement("card_holder", cardholder);
        }

        return this;
    }

    default CreditCardAttributes setCardNumber(String cardnumber) {
        if (getValidator().isValidCardNumber(cardnumber)) {
            getCreditCardAttrParamsMap().put("card_number", cardnumber);
            getCreditCardAttrRequestBuilder().addElement("card_number", cardnumber);
        }

        return this;
    }

    default CreditCardAttributes setCvv(String cvv) {
        if (getValidator().isValidCvv(cvv)) {
            getCreditCardAttrParamsMap().put("cvv", cvv);
            getCreditCardAttrRequestBuilder().addElement("cvv", cvv);
        }

        return this;
    }

    default CreditCardAttributes setExpirationMonth(String expirationMonth) {
        if (getValidator().isValidExpMonth(expirationMonth)) {
            getCreditCardAttrParamsMap().put("expiration_month", expirationMonth);
            getCreditCardAttrRequestBuilder().addElement("expiration_month", expirationMonth);
        }

        return this;
    }

    default CreditCardAttributes setExpirationYear(String expirationYear) {
        if (getValidator().isValidExpYear(expirationYear)) {
            getCreditCardAttrParamsMap().put("expiration_year", expirationYear);
            getCreditCardAttrRequestBuilder().addElement("expiration_year", expirationYear);
        }

        return this;
    }

    default CreditCardAttributes setBirthDate(String birthDate) {
        getCreditCardAttrParamsMap().put("birth_date", birthDate);
        getCreditCardAttrRequestBuilder().addElement("birth_date", birthDate);
        return this;
    }

    default String getCardHolder() {
        return getCreditCardAttrParamsMap().get("card_holder");
    }

    default String getCardNumber() {
        return getCreditCardAttrParamsMap().get("card_number");
    }

    default String getExpirationMonth() {
        return getCreditCardAttrParamsMap().get("expiration_month");
    }

    default String getExpirationYear() {
        return getCreditCardAttrParamsMap().get("expiration_year");
    }


    default RequestBuilder buildCreditCardParams() {
        RequestBuilder requestBuilder = getCreditCardAttrRequestBuilder();
        requestBuilder.addElement(buildCredentialOnFileParam().toXML());
        return requestBuilder;
    }

    RequestBuilder getCreditCardAttrRequestBuilder();

    HashMap<String, String> getCreditCardAttrParamsMap();

    GenesisValidator getValidator();
}