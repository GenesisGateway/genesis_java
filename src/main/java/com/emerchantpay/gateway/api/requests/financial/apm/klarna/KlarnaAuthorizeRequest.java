package com.emerchantpay.gateway.api.requests.financial.apm.klarna;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.PaymentCategories;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class KlarnaAuthorizeRequest extends KlarnaItemsRequest implements PaymentAttributes, CustomerInfoAttributes,
        DescriptorAttributes, AsyncAttributes, RiskParamsAttributes {

    // Request Builder
    private RequestBuilder requestBuilder;

    private String transactionType = TransactionTypes.KLARNA_AUTHORIZE;
    private BigDecimal amount;
    private String currency;
    private String description;
    private String customerGender;
    private BigDecimal orderTaxAmount;
    private String paymentMethodCategory;

    // Genesis validator
    private GenesisValidator validator = new GenesisValidator();

    // Klarna items
    private KlarnaItemsRequest klarnaItemsRequest = new KlarnaItemsRequest();

    public KlarnaAuthorizeRequest() {
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
    public String getCurrency() {
        return currency;
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
        if (validator.isValidKlarnaAuthorizeRequest(transactionType, this, amount, orderTaxAmount)) {
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

        return requestBuilder;
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

    public BigDecimal getOrderTaxAmount() {
        return orderTaxAmount;
    }
}
