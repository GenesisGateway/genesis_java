package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.constants.TransactionStates;
import com.emerchantpay.gateway.api.exceptions.ResponseException;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.StringUtils;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.configuration2.ex.ConfigurationException;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

// Before running the test, make sure src/testIntegration/resources/test-integration.properties exists
public class SmartRoutingIT {

    private static PropertiesConfiguration props;
    private Configuration configuration;

    @BeforeAll
    public static void loadProperties() {
        Configurations configs = new Configurations();
        try {
            props = configs.properties("test-integration.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("Failed to load integration test configuration", e);
        }
        assertNotNull(props.getString("merchant.ci.username"), "Username property is missing");
    }

    @BeforeEach
    public void setUp() {
        configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
        configuration.setUsername(props.getString("merchant.ci.username"));
        configuration.setPassword(props.getString("merchant.ci.password"));
    }

    @Test
    public void testAuthorizeTransactionNoSmartRouting() {
        configuration.setToken(props.getString("terminal.ci-eur.token"));
        AuthorizeRequest authorize = createAuthorizeRequest();
        TransactionResult<Transaction> result = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testAuthorizeTransactionGlobalSmartRouting() {
        configuration.setForceSmartRouting(true);
        AuthorizeRequest authorize = createAuthorizeRequest();
        TransactionResult<Transaction> result = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testAuthorizeTransactionPerRequestSmartRouting() {
        AuthorizeRequest authorize = createAuthorizeRequest();
        authorize.setUseSmartRouting(true);
        TransactionResult<Transaction> result = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testAuthorizeTransactionErrorNoSmartRoutingNoToken() {
        AuthorizeRequest authorize = createAuthorizeRequest();

        ResponseException exception = assertThrows(ResponseException.class, () -> {
            executeRequest(authorize);
        });
        assertTrue(exception.getMessage().contains("Code: 220"),
                "Expected error code 220 in the exception message.");
    }

    private AuthorizeRequest createAuthorizeRequest() {
        // Set up Authorize request
        String uniqueId = new StringUtils().generateUID();

        AuthorizeRequest authorize = new AuthorizeRequest();

        authorize.setTransactionId(uniqueId)
                .setUsage("40208 concert tickets")
                .setRemoteIp("245.253.2.12");
        authorize.setGaming(true);

        authorize.setCurrency("EUR").setAmount(new BigDecimal("50.00"));

        authorize.setCardHolder("Emil Example")
                .setCardNumber("4200000000000000")
                .setExpirationMonth("01")
                .setExpirationYear("2020")
                .setCvv("123");

        authorize.setCustomerEmail("emil.example@abc.com");

        authorize.setBillingFirstname("Travis")
                .setBillingLastname("Pastrana")
                .setBillingPrimaryAddress("Muster Str. 12")
                .setBillingZipCode("10178")
                .setBillingCity("Los Angeles")
                .setBillingState("CA").setBillingCountry("US");

        return authorize;
    }

    private TransactionResult<Transaction> executeRequest(FinancialRequest request) {
        GenesisClient client = new GenesisClient(configuration, request);
        client.debugMode(true);
        client.execute();

        // Parse Payment result
        TransactionResult<Transaction> result = client.getTransaction().getRequest();
        System.out.println("Transaction Id: " + result.getTransaction().getTransactionId());
        return result;
    }
}