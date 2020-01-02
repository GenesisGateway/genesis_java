package com.emerchantpay.gateway.api.requests.nonfinancial.consumer;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetConsumerCardsRequest extends Request {

    private String consumerId;
    private String email;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public GetConsumerCardsRequest() {
        super();
    }

    public GetConsumerCardsRequest setConsumerId(String consumerId) {
        this.consumerId = consumerId;
        return this;
    }

    public GetConsumerCardsRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toXML() {
        return buildRequest("get_consumer_cards_request").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {
        // Set required params
        requiredParams.put(RequiredParameters.consumerId, consumerId);
        requiredParams.put(RequiredParameters.email, email);

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("consumer_id", consumerId)
                .addElement("email", email);
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("get_consumer_cards_request").getElements();
    }

    @Override
    public Boolean isConsumer() {
        return true;
    }
}