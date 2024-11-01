package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PendingPaymentAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;

import java.math.BigDecimal;
import java.util.*;

public class MyBankRequest extends FinancialRequest implements CustomerInfoAttributes, AsyncAttributes,
        BillingAddressAttributes, ShippingAddressAttributes, PendingPaymentAttributes {

    private static final String transactionType = TransactionTypes.MY_BANK;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public MyBankRequest() {
        super();
        setCurrency(Currency.EUR.getCurrency());
    }

    @Override
    public MyBankRequest setAmount(BigDecimal amount) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setAmount(amount);
        return this;
    }

    @Override
    public MyBankRequest setCurrency(String currency) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setCurrency(currency);
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

    private void checkRequiredCountryAndCurrency() {
        // Validate country Italy, Spain
        ArrayList<String> allowedCountries = new ArrayList<>(Arrays.asList(Country.Italy.getCode(), Country.Spain.getCode()));

        if (!allowedCountries.contains(getBillingCountryCode())) {
            throw new InvalidParamException("Invalid country. Allowed countries are: "
                    + allowedCountries);
        }

        if (!Currency.EUR.getCurrency().equals(getCurrency())) {
            throw new InvalidParamException("Invalid currency. Allowed currency is EUR.");
        }
    }

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.amount, (getAmount() != null ? getAmount().toString() : null));
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.billingAddress, getBillingPrimaryAddress());
        requiredParams.put(RequiredParameters.country, getBillingCountryCode());

        // Validate request
        validator.isValidRequest(requiredParams);

        checkRequiredCountryAndCurrency();

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildPendingPaymentParams().toXML())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

}
