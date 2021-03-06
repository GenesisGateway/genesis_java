package com.emerchantpay.gateway.api.requests.financial.oBeP.southamerican;

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

import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.requests.financial.SouthAmericanPaymentRequest;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Country;

import java.util.ArrayList;
import java.util.HashMap;

public class PSERequest extends SouthAmericanPaymentRequest {

    private String transactionType = TransactionTypes.PSE;

    public ArrayList<String> getAllowedBillingCountries() {
        ArrayList<String> allowedBillingCountries = new ArrayList<String>();
        allowedBillingCountries.add(Country.Colombia.getCode());
        return allowedBillingCountries;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    @Override
    protected HashMap<String, String> getRequiredParams() {
        HashMap<String, String> requiredParams = super.getRequiredParams();
        requiredParams.remove(RequiredParameters.customerEmail);
        return requiredParams;
    }
}