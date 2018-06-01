package com.emerchantpay.gateway.api.requests.financial.apm.klarna;

import com.emerchantpay.gateway.api.GenesisValidator;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class KlarnaCaptureRequest extends KlarnaItemsRequest implements PaymentAttributes, CustomerInfoAttributes,
        DescriptorAttributes, AsyncAttributes, RiskParamsAttributes {

    // Request Builder
    private RequestBuilder requestBuilder;

    private String transactionType = TransactionTypes.KLARNA_CAPTURE;
    private BigDecimal amount;
    private String currency;

    // Genesis validator
    private GenesisValidator validator = new GenesisValidator();

    // Klarna items
    private KlarnaItemsRequest klarnaItemsRequest = new KlarnaItemsRequest();

    public KlarnaCaptureRequest() {
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
        if (isValidData(transactionType, amount)) {
            requestBuilder = new RequestBuilder(root).addElement("transaction_type", transactionType)
                    .addElement(buildBaseParams().toXML())
                    .addElement(buildPaymentParams().toXML())
                    .addElement(buildCustomerInfoParams().toXML())
                    .addElement(buildAsyncParams().toXML())
                    .addElement("billing_address", buildBillingAddress().toXML())
                    .addElement("shipping_address", buildShippingAddress().toXML())
                    .addElement(klarnaItemsRequest.toXML());
        }

        return requestBuilder;
    }

    protected Boolean isValidData(String transactionType, BigDecimal amount) {
        // Validate
        validator.isValidRequest(transactionType, this, amount);

        if (validator.isValidData()) {
            return true;
        } else {
            return false;
        }
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
