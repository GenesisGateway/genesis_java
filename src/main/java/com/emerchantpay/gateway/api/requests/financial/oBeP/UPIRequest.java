package com.emerchantpay.gateway.api.requests.financial.oBeP;

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
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.AddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Currency;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UPIRequest extends FinancialRequest implements AsyncAttributes, CustomerInfoAttributes, AddressAttributes {

    private String transactionType = TransactionTypes.UPI;
    @Getter
    private String documentId;
    @Getter
    private String virtualPaymentAddress;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public UPIRequest() {
        super();
        setCurrency(Currency.INR.getCurrency());
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    public UPIRequest setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public UPIRequest setVirtualPaymentAddress(String virtualPaymentAddress) {
        this.virtualPaymentAddress = virtualPaymentAddress;
        return this;
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
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.remoteIp, getRemoteIp());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.customerEmail, getCustomerEmail());
        requiredParams.put(RequiredParameters.virtualPaymentAddress, getVirtualPaymentAddress());

        // Allowed Currencies
        ArrayList<String> requiredCurrencies = new ArrayList<String>();
        requiredCurrencies.add(Currency.INR.getCurrency());

        if (!requiredCurrencies.contains(getCurrency())) {
            throw new RequiredParamsException("Invalid currency. Allowed currencies are: "
                    + requiredCurrencies.toString());
        }

        // Validate request
        validator.isValidRequest(requiredParams);

        if (!validator.isValidVirtualPaymentAddress(RequiredParameters.virtualPaymentAddress, virtualPaymentAddress, true)) {
            ArrayList<String> invalidParams = new ArrayList<String>(validator.getInvalidParams());
            validator.clearInvalidParams();
            throw new RegexException(invalidParams);
        }

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement("document_id", getDocumentId())
                .addElement("virtual_payment_address", getVirtualPaymentAddress())
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
