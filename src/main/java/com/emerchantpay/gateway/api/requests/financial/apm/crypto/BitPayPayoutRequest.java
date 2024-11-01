package com.emerchantpay.gateway.api.requests.financial.apm.crypto;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.CryptoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.NotificationAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitPayPayoutRequest extends FinancialRequest implements CustomerInfoAttributes, CryptoAttributes, AsyncAttributes, NotificationAttributes, BillingAddressAttributes, ShippingAddressAttributes {

    private static final String TRANSACTION_TYPE = TransactionTypes.BITPAY_PAYOUT;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    public BitPayPayoutRequest() {
        super();
    }

    @Override
    public String getTransactionType() {
        return TRANSACTION_TYPE;
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
        requiredParams.put(RequiredParameters.amount, (getAmount() != null ? getAmount().toString() : null));
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.notificationUrl, getNotificationUrl());
        requiredParams.put(RequiredParameters.cryptoAddress, getCryptoAddress());
        requiredParams.put(RequiredParameters.cryptoWalletProvider, getCryptoWalletProvider());

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("transaction_type", TRANSACTION_TYPE)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildCryptoParams().toXML())
                .addElement(buildNotificationParams().toXML())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
