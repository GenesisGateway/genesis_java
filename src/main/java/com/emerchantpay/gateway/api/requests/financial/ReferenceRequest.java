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
import com.emerchantpay.gateway.api.interfaces.financial.ReferenceAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ReferenceRequest extends FinancialRequest implements ReferenceAttributes {

    //Reference Attributes
    private RequestBuilder referenceAttrRequestBuilder;
    private HashMap<String, String> referenceAttrParamsMap;

    // Required params
    protected final HashMap<String, String> requiredParams = new HashMap<>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    public ReferenceRequest() {
        super();
    }

    //Could be overridden in subclasses with different required parameters
    protected HashMap<String, String> getRequiredParams(){
        //Set required params
        requiredParams.put(RequiredParameters.transactionType, getTransactionType());
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.referenceId, getReferenceId());
        return requiredParams;
    }

    public RequestBuilder getReferenceAttrRequestBuilder(){
        if(referenceAttrRequestBuilder == null){
            referenceAttrRequestBuilder = new RequestBuilder("");
        }
        return referenceAttrRequestBuilder;
    }

    public HashMap<String, String> getReferenceAttrParamsMap(){
        if(referenceAttrParamsMap == null){
            referenceAttrParamsMap = new HashMap<>();
        }
        return referenceAttrParamsMap;
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

        return new RequestBuilder(root).addElement("transaction_type", getTransactionType())
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildReferenceParams().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
