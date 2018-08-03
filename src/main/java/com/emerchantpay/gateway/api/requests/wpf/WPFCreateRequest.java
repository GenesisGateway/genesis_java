package com.emerchantpay.gateway.api.requests.wpf;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.NotificationAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.model.klarna.KlarnaItem;

public class WPFCreateRequest extends Request implements PaymentAttributes, CustomerInfoAttributes,
        DescriptorAttributes, NotificationAttributes, AsyncAttributes, RiskParamsAttributes {

    // Request Builder
    private RequestBuilder requestBuilder;

    private String description;
    private URL cancelUrl;
    private BigDecimal amount;
    private String currency;
    private Integer lifetime;
    private String customerGender;
    private BigDecimal orderTaxAmount;

    private TransactionTypesRequest transactionTypes = new TransactionTypesRequest(this);

    // Klarna items
    private KlarnaItemsRequest klarnaItemsRequest;

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public WPFCreateRequest() {
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

    public WPFCreateRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public WPFCreateRequest setReturnCancelUrl(URL cancelUrl) {
        if (validator.isValidUrl("return_cancel_url", String.valueOf(cancelUrl))) {
            this.cancelUrl = cancelUrl;
        }

        return this;
    }

    public WPFCreateRequest setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public WPFCreateRequest setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
        return this;
    }

    public WPFCreateRequest setOrderTaxAmount(BigDecimal orderTaxAmount) {
        this.orderTaxAmount = orderTaxAmount;
        return this;
    }

    public TransactionTypesRequest addTransactionType(String transactionType) {
        transactionTypes.addTransaction(transactionType);
        return transactionTypes;
    }

    public TransactionTypesRequest addTransactionType(String transactionType, ArrayList<HashMap<String, String>> params) {
        transactionTypes.addTransaction(transactionType);

        for (HashMap<String, String> map : params) {
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                String key = mapEntry.getKey();
                String value = mapEntry.getValue();

                transactionTypes.addParam(key, value);
            }
        }

        return transactionTypes;
    }

    public KlarnaItemsRequest addKlarnaItem(KlarnaItem klarnaItem) {
        return klarnaItemsRequest = new KlarnaItemsRequest(klarnaItem);
    }

    public KlarnaItemsRequest addKlarnaItems(ArrayList<KlarnaItem> klarnaItems) {
        return klarnaItemsRequest = new KlarnaItemsRequest(klarnaItems);
    }

    public TransactionTypesRequest addTransactionTypes(ArrayList<String> transactions) {

        for (String t : transactions) {
            transactionTypes.addTransaction(t);
        }

        return transactionTypes;
    }

    @Override
    public String getTransactionType() {
        return "wpf_payment";
    }

    @Override
    public String toXML() {
        return buildRequest("wpf_payment").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        requestBuilder = new RequestBuilder(root).addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement("description", description)
                .addElement(buildNotificationParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement("return_cancel_url", cancelUrl)
                .addElement("lifetime", lifetime)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML())
                .addElement("transaction_types", transactionTypes)
                .addElement("risk_params", buildRiskParams().toXML())
                .addElement("dynamic_descriptor_params", buildDescriptorParams().toXML());

        // Klarna payment method
        if (klarnaItemsRequest != null
                && transactionTypes.getTransactionTypes().contains(TransactionTypes.KLARNA_AUTHORIZE)) {
            requestBuilder.addElement("customer_gender", customerGender);
            requestBuilder.addElement("order_tax_amount", orderTaxAmount);
            requestBuilder.addElement(klarnaItemsRequest.toXML());

            validator.isValidKlarnaAuthorizeRequest(TransactionTypes.KLARNA_AUTHORIZE, klarnaItemsRequest,
                    amount, orderTaxAmount);
        }

        return requestBuilder;
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("wpf_payment").getElements();
    }
}
