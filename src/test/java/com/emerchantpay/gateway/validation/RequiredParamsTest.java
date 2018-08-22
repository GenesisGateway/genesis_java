package com.emerchantpay.gateway.validation;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.api.validation.RequiredParametersValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequiredParamsTest {
    // Required params validator
    private RequiredParametersValidator validator;

    // Parameters
    private HashMap<String, String> requiredParamsMap;

    // Required params util
    private RequiredParameters requiredParams = new RequiredParameters();

    @Before
    public void setup() {
        validator = new RequiredParametersValidator();

        // Intitial params
        requiredParamsMap = new HashMap<String, String>();

        requiredParamsMap.put(requiredParams.transactionId, String.valueOf(UUID.randomUUID()));
        requiredParamsMap.put(requiredParams.amount, String.valueOf(new BigDecimal("2.00")));
        requiredParamsMap.put(requiredParams.currency, "USD");
        requiredParamsMap.put(requiredParams.cardHolder, "Emil Example");
        requiredParamsMap.put(requiredParams.cardNumber, "4200000000000000");
        requiredParamsMap.put(requiredParams.expirationMonth, "02");
        requiredParamsMap.put(requiredParams.expirationYear, "2020");

        // Address Required params
        requiredParamsMap.put(RequiredParameters.country, "US");
    }

    @Test
    public void testSuccessRequiredParams() {
        assertTrue(validator.isValidRequiredParams(requiredParamsMap));
    }

    @Test(expected = RequiredParamsException.class)
    public void testFailureRequiredParam() {
        // Intitial params
        requiredParamsMap = new HashMap<String, String>();

        requiredParamsMap.put(requiredParams.transactionId, String.valueOf(UUID.randomUUID()));
        requiredParamsMap.put(requiredParams.amount, String.valueOf(new BigDecimal("2.00")));
        requiredParamsMap.put(requiredParams.currency, "USD");
        requiredParamsMap.put(requiredParams.cardNumber, null);
        requiredParamsMap.put(requiredParams.cardHolder, "Emil Example");
        requiredParamsMap.put(requiredParams.expirationMonth, "02");
        requiredParamsMap.put(requiredParams.expirationYear, "2020");

        assertFalse(validator.isValidRequiredParams(requiredParamsMap));
    }

    @Test(expected = RequiredParamsException.class)
    public void testFailureRequiredParams() {
        // Intitial params
        requiredParamsMap = new HashMap<String, String>();

        requiredParamsMap.put(requiredParams.transactionId, String.valueOf(UUID.randomUUID()));
        requiredParamsMap.put(requiredParams.amount, String.valueOf(new BigDecimal("2.00")));
        requiredParamsMap.put(requiredParams.currency, "USD");
        requiredParamsMap.put(requiredParams.cardNumber, null);
        requiredParamsMap.put(requiredParams.cardHolder, null);
        requiredParamsMap.put(requiredParams.expirationMonth, "02");
        requiredParamsMap.put(requiredParams.expirationYear, "2020");

        assertFalse(validator.isValidRequiredParams(requiredParamsMap));
    }

    @Test
    public void testSuccessRequiredAddressParams() {
        // Init Required Params validator
        validator = new RequiredParametersValidator(requiredParamsMap);

        assertTrue(validator.isValidRequiredParams());
    }

    @Test(expected = RequiredParamsException.class)
    public void testFailireRequiredAddressParams() {
        // Reset params
        requiredParamsMap = new HashMap<String, String>();
        requiredParamsMap.put(requiredParams.country, null);

        // Init Required Params validator
        validator = new RequiredParametersValidator(requiredParamsMap);

        assertFalse(validator.isValidRequiredParams());
    }
}
