package com.emerchantpay.gateway.api.requests.wpf;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;

public class WPFCreateRequest extends Request implements PaymentAttributes, CustomerInfoAttributes,
        DescriptorAttributes, AsyncAttributes, RiskParamsAttributes {

    private String description;
    private URL notificationUrl;
    private URL cancelUrl;
    private BigDecimal amount;
    private String currency;
    private Integer lifetime;
    
    private TransactionTypesRequest transactionTypes = new TransactionTypesRequest(this);
    
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
    
    public WPFCreateRequest setNotificationUrl(URL notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }
    
    public WPFCreateRequest setReturnCancelUrl(URL cancelUrl) {
        this.cancelUrl = cancelUrl;
        return this;
    }

    public WPFCreateRequest setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public TransactionTypesRequest addTransactionType(String transactionType) {
        transactionTypes.addTransaction(transactionType);
        return transactionTypes;
    }

    public TransactionTypesRequest addTransactionType(String transactionType, ArrayList<HashMap<String, String>> params) {
        transactionTypes.addTransaction(transactionType);

        for (HashMap<String, String> map : params) {
            for (Map.Entry<String, String> mapEntry : map.entrySet())
            {
                String key = mapEntry.getKey();
                String value = mapEntry.getValue();

                transactionTypes.addParam(key, value);
            }
        }

        return transactionTypes;
    }

    public TransactionTypesRequest addTransactionTypes(ArrayList<String> transactions) {

        for (String t: transactions) {
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

        return new RequestBuilder(root)
            .addElement(buildBaseParams().toXML())
            .addElement(buildPaymentParams().toXML())
            .addElement(buildCustomerInfoParams().toXML())
            .addElement("description", description)
            .addElement("notification_url", notificationUrl)
            .addElement(buildAsyncParams().toXML())
            .addElement("return_cancel_url", cancelUrl)
            .addElement("lifetime",  lifetime)
            .addElement("billing_address", buildBillingAddress().toXML())
            .addElement("shipping_address", buildShippingAddress().toXML())
            .addElement("transaction_types", transactionTypes)
            .addElement("risk_params", buildRiskParams().toXML())
            .addElement("dynamic_descriptor_params", buildDescriptorParams().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("wpf_payment").getElements();
    }
}
