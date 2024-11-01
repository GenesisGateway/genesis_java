package com.emerchantpay.gateway.api.requests.financial.apm.klarna;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.DescriptorAttributes;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KlarnaCaptureRequest extends KlarnaItemsRequest implements CustomerInfoAttributes,
        DescriptorAttributes, AsyncAttributes, RiskParamsAttributes {

    // Request Builder
    private RequestBuilder requestBuilder;

    private final String transactionType = TransactionTypes.KLARNA_CAPTURE;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    // Klarna items
    private final KlarnaItemsRequest klarnaItemsRequest = new KlarnaItemsRequest();

    public KlarnaCaptureRequest() {
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
        if (validator.isValidKlarnaCaptureRequest(TransactionTypes.KLARNA_CAPTURE, this, getAmount())) {
            requestBuilder = new RequestBuilder(root).addElement("transaction_type", transactionType)
                    .addElement(buildBaseParams().toXML())
                    .addElement(buildPaymentParams().toXML())
                    .addElement(buildCustomerInfoParams().toXML())
                    .addElement(buildAsyncParams().toXML())
                    .addElement("billing_address", buildBillingAddress().toXML())
                    .addElement("shipping_address", buildShippingAddress().toXML())
                    .addElement(klarnaItemsRequest.toXML());
        }

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.remoteIp, getRemoteIp());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());

        // Validate request
        validator.isValidRequest(requiredParams);

        return requestBuilder;
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
