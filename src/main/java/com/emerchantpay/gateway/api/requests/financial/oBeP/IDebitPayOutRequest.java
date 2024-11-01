package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class IDebitPayOutRequest extends FinancialRequest {

    private String transactionType = TransactionTypes.IDEBIT_PAYOUT;
    private String referenceId;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public IDebitPayOutRequest() {
        super();
    }

    public IDebitPayOutRequest setTransactionId(String transactionId) {
        super.setTransactionId(transactionId);
        return this;
    }

    public IDebitPayOutRequest setReferenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    @Override
    public String toXML() {
        return buildRequest("payment_transaction").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.referenceId, referenceId);
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());

        setRequiredCurrencies();

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root)
                .addElement("transaction_type", transactionType)
                .addElement("reference_id", referenceId)
                .addElement(buildPaymentParams().toXML())
                .addElement(buildBaseParams().toXML());
    }

    protected void setRequiredCurrencies() {
        // Allowed Currencies
        ArrayList<String> requiredCurrencies = new ArrayList<String>();

        requiredCurrencies.add(Currency.CAD.getCurrency());
        requiredCurrencies.add(Currency.EUR.getCurrency());
        requiredCurrencies.add(Currency.GBP.getCurrency());
        requiredCurrencies.add(Currency.AUD.getCurrency());

        if (!requiredCurrencies.contains(getCurrency())) {
            throw new RequiredParamsException("Invalid currency. Allowed currencies are: "
                    + requiredCurrencies.toString());
        }
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
