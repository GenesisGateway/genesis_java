package com.emerchantpay.gateway.api.requests.financial;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.MobileAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

public abstract class AfricanMobileRequest extends Request implements PaymentAttributes, CustomerInfoAttributes, AsyncAttributes, MobileAttributes, BillingAddressAttributes {

    private BigDecimal amount;
    private String currency;

    //Mobile Attributes
    private RequestBuilder mobileAttrRequestBuilder;
    private HashMap<String, String> mobileAttrParamsMap;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public AfricanMobileRequest() {
        super();
    }

    @Override
    public PaymentAttributes setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public PaymentAttributes setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    public RequestBuilder getMobileAttrRequestBuilder() {
        if (mobileAttrRequestBuilder == null) {
            mobileAttrRequestBuilder = new RequestBuilder("");
        }
        return mobileAttrRequestBuilder;
    }

    public HashMap<String, String> getMobileAttrParamsMap() {
        if (mobileAttrParamsMap == null) {
            mobileAttrParamsMap = new HashMap<String, String>();
        }
        return mobileAttrParamsMap;
    }

    public void validateCountryPaymentMethods() throws InvalidParamException {
        if (getCounties().contains(getBillingCountryCode())) {
            if (getCurrenciesMap().get(getBillingCountryCode()).equals(getCurrency())) {
                if (getOperatorsMap().get(getBillingCountryCode()).contains(getOperator())) {
                    return;
                }
                throw new InvalidParamException("Invalid operator code " + getOperator() + ". Allowed operator codes are: "
                        + getOperatorsMap().get(getBillingCountryCode()));
            }
            throw new InvalidParamException("Invalid currency code " + getCurrency() + ". Allowed currency code is: "
                    + getCurrenciesMap().get(getBillingCountryCode()));
        }
        throw new InvalidParamException("Invalid country code " + getBillingCountryCode() + ". Allowed country codes are: "
                + getCounties().stream().sorted().collect(Collectors.toList()));
    }

    protected abstract Set<String> getCounties();

    protected abstract Map<String, String> getCurrenciesMap();

    protected abstract Map<String, Set<String>> getOperatorsMap();

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
        requiredParams.put(RequiredParameters.amount, (getAmount() != null ? getAmount().toString() : null));
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.customerPhone, getCustomerPhone());
        requiredParams.put(RequiredParameters.operator, getOperator());
        requiredParams.put(RequiredParameters.target, getTarget());
        requiredParams.put(RequiredParameters.billingAddress, getBillingAddress().toXML());
        requiredParams.put(RequiredParameters.country, getBillingCountryCode());

        // Validate request
        validator.isValidRequest(requiredParams);
        validateCountryPaymentMethods();

        return new RequestBuilder(root).addElement("transaction_type", getTransactionType())
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildMobileAttributesParams().toXML())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}