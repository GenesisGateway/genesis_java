package com.emerchantpay.gateway.api.requests.financial.apm.crypto;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import lombok.Getter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitPaySaleRequest extends FinancialRequest implements CustomerInfoAttributes, BillingAddressAttributes, ShippingAddressAttributes {

    private static final String TRANSACTION_TYPE = TransactionTypes.BITPAY_SALE;

    @Getter
    private URL returnUrl;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    public BitPaySaleRequest() {
        super();
    }

    public BitPaySaleRequest setReturnUrl(URL returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    @Override
    public BitPaySaleRequest setAmount(BigDecimal amount) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setAmount(amount);
        return this;
    }

    @Override
    public BitPaySaleRequest setCurrency(String currency) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setCurrency(currency);
        return this;
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
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.customerEmail, getCustomerEmail());
        requiredParams.put(RequiredParameters.amount, (getAmount() != null ? getAmount().toString() : null));
        requiredParams.put(RequiredParameters.returnUrl, (getReturnUrl() != null ? getReturnUrl().toString() : null));

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("transaction_type", TRANSACTION_TYPE)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement("return_url", returnUrl)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
