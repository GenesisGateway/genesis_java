package com.emerchantpay.gateway.api.requests.nonfinancial.consumer;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateConsumerRequest extends Request {

    private String consumerId;
    private String email;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public UpdateConsumerRequest() {
        super();
    }

    public UpdateConsumerRequest setConsumerId(String consumerId) {
        this.consumerId = consumerId;
        return this;
    }

    public UpdateConsumerRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toXML() {
        return buildRequest("update_consumer_request").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.consumerId, consumerId);
        requiredParams.put(RequiredParameters.email, getEmail());

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("consumer_id", consumerId)
                .addElement("email", email)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("update_consumer_request").getElements();
    }

    @Override
    public Boolean isConsumer() {
        return true;
    }
}