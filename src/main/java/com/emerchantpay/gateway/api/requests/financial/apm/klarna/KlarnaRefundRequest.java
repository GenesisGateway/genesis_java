package com.emerchantpay.gateway.api.requests.financial.apm.klarna;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KlarnaRefundRequest extends KlarnaItemsRequest {

    // Request builder
    private RequestBuilder requestBuilder;

    private final String transactionType = TransactionTypes.KLARNA_REFUND;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    // Klarna items
    private final KlarnaItemsRequest klarnaItemsRequest = new KlarnaItemsRequest();

    public KlarnaRefundRequest() {
        super();
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
        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.remoteIp, getRemoteIp());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());

        // Validate request
        validator.isValidRequest(requiredParams);

        requestBuilder = new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams())
                .addElement(klarnaItemsRequest.toXML());

        return requestBuilder;
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}