package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.BankAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.NotificationAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankPayoutRequest extends FinancialRequest implements CustomerInfoAttributes, AsyncAttributes,
        NotificationAttributes, BankAttributes, BillingAddressAttributes {

    private static final String transactionType = TransactionTypes.BANK_PAYOUT;

    @Getter
    @Setter
    private String idCardNumber;
    @Getter
    @Setter
    private String documentType;
    @Getter
    @Setter
    private String accountId;
    @Getter
    @Setter
    private String userId;
    @Getter
    @Setter
    private String birthDate;

    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    @Override
    public BankPayoutRequest setCurrency(String currency) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setCurrency(currency);
        return this;
    }

    @Override
    public BankPayoutRequest setAmount(BigDecimal amount) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setAmount(amount);
        return this;
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
        requiredParams.put(RequiredParameters.notificationUrl, getNotificationUrl());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.billingAddress, getBillingPrimaryAddress());
        requiredParams.put(RequiredParameters.firstName, getBillingFirstName());
        requiredParams.put(RequiredParameters.lastName, getBillingLastName());
        requiredParams.put(RequiredParameters.state, getBillingState());
        requiredParams.put(RequiredParameters.country, getBillingCountryCode());

        validateCurrency();

        // Validate request
        validator.isValidRequest(requiredParams);
        validateBirthDate();

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildNotificationParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildBankParams().toXML())
                .addElement("id_card_number", idCardNumber)
                .addElement("document_type", documentType)
                .addElement("account_id", accountId)
                .addElement("user_id", userId)
                .addElement("birth_date", birthDate)
                .addElement("billing_address", buildBillingAddress().toXML());
    }

    protected void validateCurrency() {
        // Allowed currencies
        ArrayList<String> requiredCurrencies = new ArrayList<String>();

        requiredCurrencies.add(Currency.ARS.getCurrency());
        requiredCurrencies.add(Currency.BRL.getCurrency());
        requiredCurrencies.add(Currency.CLP.getCurrency());
        requiredCurrencies.add(Currency.CNY.getCurrency());
        requiredCurrencies.add(Currency.COP.getCurrency());
        requiredCurrencies.add(Currency.IDR.getCurrency());
        requiredCurrencies.add(Currency.INR.getCurrency());
        requiredCurrencies.add(Currency.MYR.getCurrency());
        requiredCurrencies.add(Currency.MXN.getCurrency());
        requiredCurrencies.add(Currency.PEN.getCurrency());
        requiredCurrencies.add(Currency.THB.getCurrency());
        requiredCurrencies.add(Currency.UYU.getCurrency());

        if (!requiredCurrencies.contains(getCurrency())) {
            throw new RequiredParamsException("Invalid currency. Allowed currencies are: "
                    + requiredCurrencies.toString());
        }
    }

    private void validateBirthDate() {
        if(!validator.isValidDate(birthDate, "birth_date")) {
            throw new RegexException("Invalid birth_date. Allowed format is: dd-mm-yyyy");
        }
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

}
