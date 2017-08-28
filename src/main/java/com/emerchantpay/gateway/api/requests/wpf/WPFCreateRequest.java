package com.emerchantpay.gateway.api.requests.wpf;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

public class WPFCreateRequest extends Request implements BillingAddressAttributes, ShippingAddressAttributes, RiskParamsAttributes {
    
    protected Configuration configuration;
    private Http http;
    
    private NodeWrapper response;
    
    private String transactionId;
    private String usage;
    private String customerEmail;
    private String customerPhone;
    private String description;
    private URL notificationUrl;
    private URL successUrl;
    private URL failureUrl;
    private URL cancelUrl;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private String currency;
    private Locale language;
    
    private TransactionTypesRequest transactionTypes;
    private WPFDynamicDescriptorParamsRequest dynamicDescriptorParams;
    
    public WPFCreateRequest() {
        super();
    }
    
    public WPFCreateRequest(Configuration configuration) {
        
        super();
        this.configuration = configuration;
    }
    
    public WPFCreateRequest setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }
    
    public WPFCreateRequest setUsage(String usage) {
        this.usage = usage;
        return this;
    }
    
    public WPFCreateRequest setAmount(BigDecimal amount) {
        
        this.amount = amount;
        return this;
    }
    
    public WPFCreateRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
    
    public WPFCreateRequest setDescription(String description) {
        this.description = description;
        return this;
    }
    
    public WPFCreateRequest setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }
    
    public WPFCreateRequest setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
        return this;
    }
    
    public WPFCreateRequest setNotificationUrl(URL notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }
    
    public WPFCreateRequest setReturnSuccessUrl(URL successUrl) {
        this.successUrl = successUrl;
        return this;
    }
    
    public WPFCreateRequest setReturnFailureUrl(URL failureUrl) {
        this.failureUrl = failureUrl;
        return this;
    }
    
    public WPFCreateRequest setReturnCancelUrl(URL cancelUrl) {
        this.cancelUrl = cancelUrl;
        return this;
    }
    
    public TransactionTypesRequest transactionTypes() {
        transactionTypes = new TransactionTypesRequest(this);
        return transactionTypes;
    }
    
    public WPFDynamicDescriptorParamsRequest dynamicDescriptors() {
        dynamicDescriptorParams = new WPFDynamicDescriptorParamsRequest(this);
        return dynamicDescriptorParams;
    }
    
    public WPFCreateRequest setLanguage(Locale language) {
        this.language = language;
        return this;
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
        
        if (amount != null && currency != null) {
            
            Currency curr = new Currency();
            
            curr.setAmountToExponent(amount, currency);
            convertedAmount = curr.getAmount();
        }
        
        return new RequestBuilder(root).addElement("transaction_id", transactionId).addElement("usage", usage)
        .addElement("description", description).addElement("customer_email", customerEmail)
        .addElement("customer_phone", customerPhone).addElement("notification_url", notificationUrl)
        .addElement("return_success_url", successUrl).addElement("return_failure_url", failureUrl)
        .addElement("return_cancel_url", cancelUrl).addElement("amount", convertedAmount)
        .addElement("currency", currency).addElement("billing_address", buildBillingAddress().toXML())
        .addElement("shipping_address", buildShippingAddress().toXML()).addElement("transaction_types", transactionTypes)
        .addElement("risk_params", buildRiskParams())
        .addElement("dynamic_descriptor_params", dynamicDescriptorParams);
    }
    
    public Request execute(Configuration configuration) {
        
        configuration.setWpfEnabled(true);
        configuration.setTokenEnabled(false);
        
        if (language != null) {
            configuration.setAction(language + "/wpf");
        } else{
            configuration.setAction("wpf");
        }
        
        http = new Http(configuration);
        response = http.post(configuration.getBaseUrl(), this);
        
        return this;
    }
    
    public NodeWrapper getResponse() {
        return response;
    }
    
    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("wpf_payment").getElements();
    }
}
