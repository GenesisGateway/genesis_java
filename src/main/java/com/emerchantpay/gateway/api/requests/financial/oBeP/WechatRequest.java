package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.NotificationAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.net.URL;
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

public class WechatRequest extends FinancialRequest implements CustomerInfoAttributes,
        NotificationAttributes, AsyncAttributes {

    private String transactionType = TransactionTypes.WECHAT;

    private String productCode;
    private Integer productNumber;
    private String productDescription;
    private URL returnUrl;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public WechatRequest() {
        super();
    }

    public WechatRequest setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public WechatRequest setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    public WechatRequest setProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public WechatRequest setReturnUrl(URL returnUrl) {
        this.returnUrl = returnUrl;
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
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.usage, getUsage());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildNotificationParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement("product_code", productCode)
                .addElement("product_num", productNumber)
                .addElement("product_desc", productDescription)
                .addElement("return_url", returnUrl)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildBillingAddress());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}