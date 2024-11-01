package com.emerchantpay.gateway.api.requests.financial.apm.klarna;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.PaymentCategories;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Country;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KlarnaAuthorizeRequest extends KlarnaItemsRequest implements CustomerInfoAttributes,
        DescriptorAttributes, AsyncAttributes, RiskParamsAttributes {

    // Request Builder
    private RequestBuilder requestBuilder;

    private final String transactionType = TransactionTypes.KLARNA_AUTHORIZE;

    private String description;
    private String customerGender;
    @Getter
    private BigDecimal orderTaxAmount;
    private String paymentMethodCategory;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<String, String>();

    // Genesis validator
    private final GenesisValidator validator = new GenesisValidator();

    // Klarna items
    private final KlarnaItemsRequest klarnaItemsRequest = new KlarnaItemsRequest();

    public KlarnaAuthorizeRequest() {
        super();
    }

    public KlarnaAuthorizeRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public KlarnaAuthorizeRequest setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
        return this;
    }

    public KlarnaAuthorizeRequest setOrderTaxAmount(BigDecimal orderTaxAmount) {
        this.orderTaxAmount = orderTaxAmount;
        return this;
    }

    public KlarnaAuthorizeRequest setPaymentMethodCategory(String paymentMethodCategory) {
        if (paymentMethodCategory.equals(PaymentCategories.PAYLATER) || paymentMethodCategory.equals(PaymentCategories.PAYOVERTIME)) {
            this.paymentMethodCategory = paymentMethodCategory;
        } else {
            throw new GenesisException("Please populate valid Payment category");
        }

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
        if (validator.isValidKlarnaAuthorizeRequest(transactionType, this, getAmount(), orderTaxAmount)) {
            requestBuilder = new RequestBuilder(root).addElement("transaction_type", transactionType)
                    .addElement(buildBaseParams().toXML())
                    .addElement(buildPaymentParams().toXML())
                    .addElement(buildCustomerInfoParams().toXML())
                    .addElement(buildAsyncParams().toXML())
                    .addElement("description", description)
                    .addElement("payment_method_category", paymentMethodCategory)
                    .addElement("billing_address", buildBillingAddress().toXML())
                    .addElement("shipping_address", buildShippingAddress().toXML())
                    .addElement("customer_gender", customerGender)
                    .addElement("order_tax_amount", orderTaxAmount)
                    .addElement(klarnaItemsRequest.toXML());
        }

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.remoteIp, getRemoteIp());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.paymentMethodCategory, paymentMethodCategory);
        requiredParams.put(RequiredParameters.country, getBillingCountryCode());
        requiredParams.put(RequiredParameters.country, getShippingCountryCode());

        setRequiredCountries();

        // Validate request
        validator.isValidRequest(requiredParams);

        return requestBuilder;
    }

    protected void setRequiredCountries() {
        // Allowed Countries
        ArrayList<String> requiredCountries = new ArrayList<String>();

        requiredCountries.add(Country.Austria.getCode());
        requiredCountries.add(Country.Denmark.getCode());
        requiredCountries.add(Country.Finland.getCode());
        requiredCountries.add(Country.Germany.getCode());
        requiredCountries.add(Country.Netherlands.getCode());
        requiredCountries.add(Country.Norway.getCode());
        requiredCountries.add(Country.Sweden.getCode());
        
        if (!requiredCountries.contains(getBillingCountryCode())) {
            throw new RequiredParamsException("Invalid country. Allowed countries are: "
                    + requiredCountries);
        }
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
