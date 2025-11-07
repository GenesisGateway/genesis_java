package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.constants.TransactionStates;
import com.emerchantpay.gateway.api.exceptions.NotFoundException;
import com.emerchantpay.gateway.api.exceptions.ResponseException;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileRequest;
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
 * Integration tests for reconcile requests.<br/>
 * The tests execute against the Genesis Staging endpoint defined by
 * {@link Environments#STAGING} and {@link Endpoints#EMERCHANTPAY}
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
 * </ol>
 */
public class ReconcileIT {

    private static PropertiesConfiguration props;
    private Configuration configuration;

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
    }

    @Test
    public void testReconcileNoSmartRouting() {
        configuration.setToken(props.getString("terminal.ci-eur.token"));
        String authTransactionId = issueAuthRequest(false);

        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId(authTransactionId);

        TransactionResult<Transaction> result = executeRequest(reconcile);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testReconcileGlobalSmartRouting() {
        configuration.setForceSmartRouting(true);
        String authTransactionId = issueAuthRequest(false);

        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId(authTransactionId);

        TransactionResult<Transaction> result = executeRequest(reconcile);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testReconcilePerRequestSmartRouting() {
        String authTransactionId = issueAuthRequest(true);

        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId(authTransactionId);
        reconcile.setUseSmartRouting(true);

        TransactionResult<Transaction> result = executeRequest(reconcile);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testReconcileErrorIncorrectTransactionId() {
        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId("dummy-id");
        reconcile.setUseSmartRouting(true);

        assertThrows(NotFoundException.class, () -> {
            executeRequest(reconcile);
        });
    }

    @Test
    public void testReconcileErrorNoSmartRoutingNoToken() {
        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId("dummy-id");

        ResponseException exception = assertThrows(ResponseException.class, () -> {
            executeRequest(reconcile);
        });
        assertTrue(exception.getMessage().startsWith("Code: 220"));
    }

    /** Issue Auth request and get its uniqueId from response*/
    private String issueAuthRequest(boolean useSmartRouting) {
        AuthorizeRequest authorize = AuthorizeRequestFactory.create();
        authorize.setUseSmartRouting(useSmartRouting);
        TransactionResult<Transaction> authResult = executeRequest(authorize);
        assertEquals(TransactionStates.APPROVED, authResult.getTransaction().getStatus());
        return authResult.getTransaction().getTransactionId();
    }

    private TransactionResult<Transaction> executeRequest(Request request) {
        GenesisClient client = new GenesisClient(configuration, request);
        client.debugMode(true);
        client.execute();
        return client.getTransaction().getRequest();
    }
}
