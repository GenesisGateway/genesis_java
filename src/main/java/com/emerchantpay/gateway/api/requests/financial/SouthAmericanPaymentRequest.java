package com.emerchantpay.gateway.api.requests.financial;

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
import com.emerchantpay.gateway.api.interfaces.AddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PendingPaymentAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PproAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SouthAmericanPaymentRequest extends FinancialRequest implements AsyncAttributes,
        CustomerInfoAttributes, AddressAttributes, PproAttributes, PendingPaymentAttributes {

    //Ppro Attributes
    private RequestBuilder pproAttrRequestBuilder;
    private HashMap<String, String> pproAttrParamsMap;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public SouthAmericanPaymentRequest() {
        super();
    }

    abstract public ArrayList<String> getAllowedBillingCountries();

    //Could be overridden in sub-classes with different required parameters
    protected HashMap<String, String> getRequiredParams(){
        //Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.customerEmail, getCustomerEmail());
        requiredParams.put(RequiredParameters.country, getBillingCountryCode());
        requiredParams.put(RequiredParameters.consumerReference, getConsumerReference());
        requiredParams.put(RequiredParameters.nationalId, getNationalId());
        return requiredParams;
    }

    public RequestBuilder getPproAttrRequestBuilder(){
        if(pproAttrRequestBuilder == null){
            pproAttrRequestBuilder = new RequestBuilder("");
        }
        return pproAttrRequestBuilder;
    }

    public HashMap<String, String> getPproAttrParamsMap(){
        if(pproAttrParamsMap == null){
            pproAttrParamsMap = new HashMap<String, String>();
        }
        return pproAttrParamsMap;
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

        //Validate request
        validator.isValidRequest(getRequiredParams());

        // Allowed countries
        ArrayList<String> allowedBillingCountries = getAllowedBillingCountries();
        if (allowedBillingCountries != null && !allowedBillingCountries.isEmpty() &&
                !allowedBillingCountries.contains(getBillingCountryCode())) {
            throw new InvalidParamException(RequiredParameters.country, allowedBillingCountries);
        }

        return new RequestBuilder(root).addElement("transaction_type", getTransactionType())
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildPproParams().toXML())
                .addElement(buildPendingPaymentParams().toXML())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

}
