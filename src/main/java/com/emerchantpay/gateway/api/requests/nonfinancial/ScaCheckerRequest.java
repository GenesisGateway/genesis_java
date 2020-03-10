package com.emerchantpay.gateway.api.requests.nonfinancial;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.model.ScaChecker;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.*;
import static com.fasterxml.jackson.annotation.JsonInclude.*;

public class ScaCheckerRequest extends Request {

    private String cardNumber;
    private Boolean moto;
    private Boolean mit;
    private Boolean recurring;
    private BigDecimal transactionAmount;
    private String transactionCurrency;
    private String transactionExemption;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    protected Configuration configuration;
    private Http http;

    private JsonNode node;

    public ScaCheckerRequest() {
        super();
    }

    public ScaCheckerRequest(Configuration configuration) {
        super();
        this.configuration = configuration;
    }

    public ScaCheckerRequest setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public ScaCheckerRequest setMoto(Boolean moto) {
        this.moto = moto;
        return this;
    }

    public ScaCheckerRequest setMit(Boolean mit) {
        this.mit = mit;
        return this;
    }

    public ScaCheckerRequest setRecurring(Boolean recurring) {
        this.recurring = recurring;
        return this;
    }

    public ScaCheckerRequest setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public ScaCheckerRequest setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
        return this;
    }

    public void setTransactionExemption(String transactionExemption) {
        this.transactionExemption = transactionExemption;
    }

    protected String buildRequest(String root) {
        BigDecimal convertedAmount = null;
        if (transactionAmount != null && transactionCurrency != null) {
            Currency curr = new Currency();
            curr.setAmountToExponent(transactionAmount, transactionCurrency);
            convertedAmount = curr.getAmount();
        }

        requiredParams.put(RequiredParameters.cardNumber, cardNumber);
        requiredParams.put(RequiredParameters.transactionAmount, String.valueOf(transactionAmount));
        requiredParams.put(RequiredParameters.transactionCurrency, transactionCurrency);

        // Validate request
        validator.isValidCardNumber(cardNumber);
        validator.isValidRequest(requiredParams);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        // Create SCA Checker object
        ScaChecker scaChecker = new ScaChecker();
        if (convertedAmount != null && validator.isValidAmount(convertedAmount)) {
            scaChecker = new ScaChecker(cardNumber,
                    moto, mit, recurring,
                    transactionExemption,
                    convertedAmount.intValue(),
                    transactionCurrency);
        } else {
            throw new RegexException(validator.getInvalidParams());
        }


        String requestBody = "";
        try {
            requestBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scaChecker);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return requestBody;
    }

    public JsonNode execute() {
        configuration.setTokenEnabled(true);
        configuration.setAction(Endpoints.SCA_CHECKER.getEndpointName());
        http = new Http(configuration);
        node = http.postJson(configuration.getBaseUrl(), toJSON());

        return node;
    }

    @Override
    public String toJSON() {
        return buildRequest("sca_checker");
    }

    public ScaChecker getResponse() {
        return new ScaChecker(node);
    }
}