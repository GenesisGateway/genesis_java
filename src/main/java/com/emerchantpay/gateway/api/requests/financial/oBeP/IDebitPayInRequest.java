package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.NotificationAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;

import java.math.BigDecimal;
import java.net.URL;
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

public class IDebitPayInRequest extends Request implements PaymentAttributes, CustomerInfoAttributes,
        NotificationAttributes {

    private String transactionType = TransactionTypes.IDEBIT_PAYIN;
    private String customerAccountId;
    private URL returnUrl;
    private BigDecimal amount;
    private String currency;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public IDebitPayInRequest() {
        super();
    }

    public IDebitPayInRequest setCustomerAccountId(String customerAccountId) {
        this.customerAccountId = customerAccountId;
        return this;
    }

    public IDebitPayInRequest setReturnUrl(URL returnUrl) {
        this.returnUrl = returnUrl;
        return this;
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

        setRequiredParams();

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML()).addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML()).addElement("return_url", returnUrl)
                .addElement(buildNotificationParams().toXML())
                .addElement("customer_account_id", customerAccountId)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

    protected void setRequiredParams() {
        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.usage, getUsage());
        requiredParams.put(RequiredParameters.returnUrl, returnUrl.toString());
        requiredParams.put(RequiredParameters.customerAccountId, customerAccountId);

        // Allowed Currencies
        ArrayList<String> requiredCurrencies = new ArrayList<String>();

        requiredCurrencies.add(Currency.CAD.getCurrency());
        requiredCurrencies.add(Currency.EUR.getCurrency());
        requiredCurrencies.add(Currency.GBP.getCurrency());
        requiredCurrencies.add(Currency.AUD.getCurrency());

        // Allowed Countries
        ArrayList<String> requiredCountries = new ArrayList<String>();
        requiredCountries.add(Country.Canada.getCode());

        if (!requiredCurrencies.contains(getCurrency()) || !requiredCountries.contains(getBillingCountryCode())) {
            throw new RequiredParamsException("Invalid currency. Allowed countries are: "
                    + requiredCountries.toString());
        }
        // Validate request
        validator.isValidRequest(requiredParams);
    }
}
