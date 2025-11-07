package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.constants.TransactionStates;
import com.emerchantpay.gateway.api.exceptions.BadRequestException;
import com.emerchantpay.gateway.api.exceptions.ResponseException;
import com.emerchantpay.gateway.api.requests.financial.VoidRequest;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.AuthorizeRequestFactory;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.StringUtils;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for void requests.<br/>
 * The tests execute against the Genesis Staging endpoint defined by
 * {@link Environments#STAGING} and {@link Endpoints#EMERCHANTPAY}<br/>
 * Note that authorization can be voided only once.
 *
 * <p><strong>Prerequisites:</strong>
 * <ol>
 *   <li>Ensure the file
 *       {@code src/testIntegration/resources/test-integration.properties}
 *       exists and contains valid keys:
 *     <ul>
 *       <li><code>merchant.ci.username</code></li>
 *       <li><code>merchant.ci.password</code></li>
 *       <li><code>terminal.ci-eur.token</code></li>
 *     </ul>
 *   </li>
 * </ol>jkm
 */
public class VoidIT {

    private static PropertiesConfiguration props;
    private Configuration configuration;

    private String voidTransactionId;

    @BeforeAll
    public static void loadProperties() {
        try {
            props = new Configurations().properties("test-integration.properties");
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
        voidTransactionId = new StringUtils().generateUID();
    }

    @Test
    public void testVoidNoSmartRouting() {
        configuration.setToken(props.getString("terminal.ci-eur.token"));
        String authUniqueId = issueAuthRequest(false);

        // Void previous Auth request
        VoidRequest voidRequest = new VoidRequest();
        voidRequest.setTransactionId(voidTransactionId);
        voidRequest.setReferenceId(authUniqueId);
        voidRequest.setUsage("Void transaction IT");
        voidRequest.setRemoteIp("127.0.0.1");

        TransactionResult<Transaction> voidResult = executeRequest(voidRequest);
        assertEquals(TransactionStates.APPROVED, voidResult.getTransaction().getStatus());
    }

    @Test
    public void testVoidGlobalSmartRouting() {
        configuration.setForceSmartRouting(true);
        String authUniqueId = issueAuthRequest(false);

        VoidRequest voidRequest = new VoidRequest();
        voidRequest.setTransactionId(voidTransactionId);
        voidRequest.setReferenceId(authUniqueId);
        voidRequest.setUsage("Void transaction IT");
        voidRequest.setRemoteIp("127.0.0.1");

        TransactionResult<Transaction> result = executeRequest(voidRequest);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testVoidPerRequestSmartRouting() {
        String authUniqueId = issueAuthRequest(true);

        VoidRequest voidRequest = new VoidRequest();
        voidRequest.setTransactionId(voidTransactionId);
        voidRequest.setReferenceId(authUniqueId);
        voidRequest.setUsage("Void transaction IT");
        voidRequest.setRemoteIp("127.0.0.1");
        voidRequest.setUseSmartRouting(true);

        TransactionResult<Transaction> result = executeRequest(voidRequest);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testVoidErrorSmartRoutingIncorrectReferenceId() {
        VoidRequest voidRequest = new VoidRequest();
        voidRequest.setTransactionId(voidTransactionId);
        voidRequest.setReferenceId("00000000000000000000000000000000");
        voidRequest.setUsage("Void transaction IT");
        voidRequest.setRemoteIp("127.0.0.1");
        voidRequest.setUseSmartRouting(true);

        assertThrows(BadRequestException.class, () -> {
            executeRequest(voidRequest);
        });
    }

    @Test
    public void testVoidErrorNoSmartRoutingNoToken() {
        VoidRequest voidRequest = new VoidRequest();
        voidRequest.setTransactionId(voidTransactionId);
        voidRequest.setReferenceId("00000000000000000000000000000000");
        voidRequest.setUsage("Void transaction IT");
        voidRequest.setRemoteIp("127.0.0.1");

        ResponseException exception = assertThrows(ResponseException.class, () -> {
            executeRequest(voidRequest);
        });
        assertTrue(exception.getMessage().startsWith("Code: 220"));
    }

    /** Issue Auth request and get its uniqueId from response*/
    private String issueAuthRequest(boolean useSmartRouting) {
        AuthorizeRequest authorize = AuthorizeRequestFactory.create();
        authorize.setUseSmartRouting(useSmartRouting);
        TransactionResult<Transaction> authResult = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, authResult.getTransaction().getStatus());
        return authResult.getTransaction().getUniqueId();
    }

    private TransactionResult<Transaction> executeRequest(Request request) {
        GenesisClient client = new GenesisClient(configuration, request);
        client.debugMode(true);
        client.execute();
        return client.getTransaction().getRequest();
    }
}
