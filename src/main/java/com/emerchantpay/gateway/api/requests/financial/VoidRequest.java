package com.emerchantpay.gateway.api.requests.financial;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.financial.ReferenceAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

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

public class VoidRequest extends Request implements ReferenceAttributes {

    private final String transactionType = TransactionTypes.VOID;

    //Reference Attributes
    private RequestBuilder referenceAttrRequestBuilder;
    private HashMap<String, String> referenceAttrParamsMap;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    public VoidRequest() {
        super();
    }

    /**
     * Sets the reference identifier for this request.
     *
     * @param referencialId the reference identifier to set
     * @return this instance of {@link <ClassName>}
     * @deprecated and scheduled for removal since {@code 1.18.8}, use {@link #setReferenceId(String)} instead.
     */
    @Deprecated
    public VoidRequest setReferencialId(String referencialId) {
        setReferenceId(referencialId);
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

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.transactionType, getTransactionType());
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.referenceId, getReferenceId());

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildReferenceParams().toXML());
    }
}
