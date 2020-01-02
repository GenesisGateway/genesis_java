package com.emerchantpay.gateway.api.requests.nonfinancial.consumer;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.GenesisException;

import java.util.List;
import java.util.Map;

public class RetrieveConsumerRequest extends Request {

    private String consumerId;
    private String email;

    public RetrieveConsumerRequest() {
        super();
    }

    public RetrieveConsumerRequest setConsumerId(String consumerId) {
        this.consumerId = consumerId;
        return this;
    }

    public RetrieveConsumerRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toXML() {
        return buildRequest("retrieve_consumer_request").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {
        if ((consumerId == null || consumerId.isEmpty()) && (email == null || email.isEmpty())) {
            throw new GenesisException("Either email or consumer_id field has to be set.");
        }

        return new RequestBuilder(root).addElement("consumer_id", consumerId)
                .addElement("email", email);
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("retrieve_consumer_request").getElements();
    }

    @Override
    public Boolean isConsumer() {
        return true;
    }
}