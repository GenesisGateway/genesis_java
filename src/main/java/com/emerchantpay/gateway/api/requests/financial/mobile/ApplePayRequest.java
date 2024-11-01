package com.emerchantpay.gateway.api.requests.financial.mobile;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.AddressAttributes;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.CryptoAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.ArrayList;
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

public class ApplePayRequest extends FinancialRequest implements CustomerInfoAttributes,
        AddressAttributes, BusinessParamsAttributes, CryptoAttributes {

    private String transactionType = TransactionTypes.APPLE_PAY;

    private String paymentType;
    private String documentId;
    private String birthDate;
    private ApplePayPaymentTokenRequest applePayPaymentTokenRequest = new ApplePayPaymentTokenRequest();

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public ApplePayRequest() {
        super();
    }

    public ApplePayPaymentTokenRequest getPaymentTokenRequest() {
        return applePayPaymentTokenRequest;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    public ApplePayRequest setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getPaymentType(){ return paymentType; }

    public ApplePayRequest setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getDocumentId() {
        return documentId;
    }

    public ApplePayRequest setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String toXML() {

        String xmlRequest = buildRequest("payment_transaction").toXML();
        int paymentTokenOpenTagPos = xmlRequest.indexOf("<payment_token>");
        int paymentTokenTagPos = paymentTokenOpenTagPos + "<payment_token>".length();
        //Add unescaped payment token
        if(paymentTokenOpenTagPos > 0){
            String paymentTokenJson = applePayPaymentTokenRequest.toJSON();
            xmlRequest = xmlRequest.substring(0, paymentTokenTagPos) + paymentTokenJson + xmlRequest.substring(paymentTokenTagPos);
        }
        return xmlRequest;
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {
        // Set required params
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.paymentSubType, paymentType);

        // Validate request
        validator.isValidRequest(requiredParams);
        validator.isValidDate(birthDate, "birth_date");

        // Allowed Payment types
        ArrayList<String> requiredPaymentTypes = new ArrayList<>();
        requiredPaymentTypes.add("authorize");
        requiredPaymentTypes.add("init_recurring_sale");
        requiredPaymentTypes.add("sale");

        if (!requiredPaymentTypes.contains(paymentType)) {
            throw new RequiredParamsException("Invalid payment type. Allowed payment types are: "
                    + requiredPaymentTypes);
        }

        ArrayList<String> invalidParams = new ArrayList<String>(validator.getInvalidParams());
        if (!invalidParams.isEmpty()) {
            validator.clearInvalidParams();
            throw new RegexException(invalidParams);
        }

        String paymentToken;
        if (getPaymentTokenRequest().getPaymentToken() != null)
            paymentToken = getPaymentTokenRequest().getPaymentToken();
        else paymentToken = getPaymentTokenRequest().toJSON();

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement("payment_subtype", paymentType)
                .addElement("payment_token", paymentToken)
                .addElement("document_id", documentId)
                .addElement("birth_date", birthDate)
                .addElement(buildCryptoParams().toXML())
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement("business_attributes", buildBusinessParams().toXML())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }
}
