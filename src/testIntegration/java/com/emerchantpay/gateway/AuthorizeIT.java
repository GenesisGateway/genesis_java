package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.constants.TransactionStates;
import com.emerchantpay.gateway.api.exceptions.ResponseException;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.AuthorizeRequestFactory;
import com.emerchantpay.gateway.util.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for authorization transactions.<br/>
 * The tests execute against Genesis Staging endpoint defined by
 * {@link Environments#STAGING} and {@link Endpoints#EMERCHANTPAY}
 *
 * <p><strong>Prerequisites:</strong>
 * Ensure the file {@code src/testIntegration/resources/test-integration.properties}
 * exists and contains valid keys:
 * <ul>
 *   <li><code>merchant.ci.username</code></li>
 *   <li><code>merchant.ci.password</code></li>
 *   <li><code>terminal.ci-eur.token</code></li>
 * </ul>
 */
public class AuthorizeIT {

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
        assertNotNull(props.getString("terminal.ci-eur.token"), "Terminal token is missing");
    }

    @BeforeEach
    public void setUp() {
        configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
        configuration.setUsername(props.getString("merchant.ci.username"));
        configuration.setPassword(props.getString("merchant.ci.password"));
    }

    @Test
    public void testAuthorizeNoSmartRouting() {
        configuration.setToken(props.getString("terminal.ci-eur.token"));
        AuthorizeRequest authorize = AuthorizeRequestFactory.create();
        TransactionResult<Transaction> result = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testAuthorizeGlobalSmartRouting() {
        configuration.setForceSmartRouting(true);
        AuthorizeRequest authorize = AuthorizeRequestFactory.create();
        TransactionResult<Transaction> result = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testAuthorizePerRequestSmartRouting() {
        AuthorizeRequest authorize = AuthorizeRequestFactory.create();
        authorize.setUseSmartRouting(true);
        TransactionResult<Transaction> result = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testAuthorizeErrorNoSmartRoutingNoToken() {
        AuthorizeRequest authorize = AuthorizeRequestFactory.create();

        ResponseException exception = assertThrows(ResponseException.class, () -> {
            executeRequest(authorize);
        });
        assertTrue(exception.getMessage().contains("Code: 220"),
                "Expected error code 220 in the exception message.");
    }

    @Test
    public void testAuthorizeDynamicDescriptorProps() {
        configuration.setToken(props.getString("terminal.ci-eur.token"));
        AuthorizeRequest authorize = AuthorizeRequestFactory.createWithDynamicDescriptorProps();
        TransactionResult<Transaction> result = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
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