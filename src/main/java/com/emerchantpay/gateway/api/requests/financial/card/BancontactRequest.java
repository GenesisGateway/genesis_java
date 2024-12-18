package com.emerchantpay.gateway.api.requests.financial.card;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancontactRequest extends FinancialRequest implements CustomerInfoAttributes, BillingAddressAttributes, ShippingAddressAttributes, AsyncAttributes {

    private String transactionType = TransactionTypes.BANCONTACT;
    @Getter
    private String paymentType = "bcmc";

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public BancontactRequest() {
        super();
        // TODO: Do we really need to set EUR currency as default?
        setCurrency(Currency.EUR.getCurrency());
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    public BancontactRequest setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    protected void checkRequiredCountryAndCurrency() {
        // Validate country Belgium and currency EUR
        if (!Country.Belgium.getCode().equals(getBillingCountryCode())) {
            throw new InvalidParamException("Invalid country. Allowed country is: ["
                    + Country.Belgium.getCode() + "]");
        }

        if (!Currency.EUR.getCurrency().equals(getCurrency())) {
            throw new InvalidParamException("Invalid currency. Allowed currency is EUR.");
        }
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
        requiredParams.put(RequiredParameters.country, getBillingCountryCode());

        checkRequiredCountryAndCurrency();

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement("payment_type", paymentType)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
