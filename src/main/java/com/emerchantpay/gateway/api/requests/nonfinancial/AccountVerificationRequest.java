package com.emerchantpay.gateway.api.requests.nonfinancial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.CreditCardAttributes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

public class AccountVerificationRequest extends Request implements CreditCardAttributes, CustomerInfoAttributes,
        RiskParamsAttributes {

    private String transactionType = TransactionTypes.ACCOUNT_VERIFICATION;
    private Boolean moto;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public AccountVerificationRequest() {
        super();
    }

    public AccountVerificationRequest setMoto(Boolean moto) {
        this.moto = moto;
        return this;
    }

    @Override
    public String toXML() {
        return buildRequest("payment_transaction").toXML();
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.cardHolder, getCardHolder());
        requiredParams.put(RequiredParameters.cardNumber, getCardNumber());
        requiredParams.put(RequiredParameters.expirationMonth, getExpirationMonth());
        requiredParams.put(RequiredParameters.expirationYear, getExpirationYear());
        requiredParams.put(RequiredParameters.address1, getBillingPrimaryAddress());
        requiredParams.put(RequiredParameters.zipCode, getBillingZipCode());
        requiredParams.put(RequiredParameters.city, getBillingCity());

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML()).addElement("moto", moto)
                .addElement(buildCreditCardParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML())
                .addElement("risk_params", buildRiskParams().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}