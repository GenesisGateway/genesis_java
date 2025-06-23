package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.constants.TransactionStates;
import com.emerchantpay.gateway.api.exceptions.NotFoundException;
import com.emerchantpay.gateway.api.exceptions.ResponseException;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileRequest;
import com.emerchantpay.gateway.model.Transaction;
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
 *   <li>Run authorization test (e.g., in {@code SmartRoutingAuthorizeIT}) to obtain a valid
 *       RECONCILE_TRANSACTION_ID from authorization response</li>
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
public class SmartRoutingReconcileIT {

    private static PropertiesConfiguration props;
    private Configuration configuration;

    private final String RECONCILE_TRANSACTION_ID = "f78ab55b07ce4922ab79f49ebaa4d1";

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

        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId(RECONCILE_TRANSACTION_ID);

        TransactionResult<Transaction> result = executeRequest(reconcile);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testReconcileGlobalSmartRouting() {
        configuration.setForceSmartRouting(true);

        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId(RECONCILE_TRANSACTION_ID);

        TransactionResult<Transaction> result = executeRequest(reconcile);
        assertEquals(TransactionStates.APPROVED, result.getTransaction().getStatus());
    }

    @Test
    public void testReconcilePerRequestSmartRouting() {
        ReconcileRequest reconcile = new ReconcileRequest();
        reconcile.setTransactionId(RECONCILE_TRANSACTION_ID);
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
        reconcile.setTransactionId(RECONCILE_TRANSACTION_ID);

        ResponseException exception = assertThrows(ResponseException.class, () -> {
            executeRequest(reconcile);
        });
        String msg = exception.getMessage();
        assertTrue(exception.getMessage().startsWith("Code: 220"));
    }

    private TransactionResult<Transaction> executeRequest(ReconcileRequest request) {
        GenesisClient client = new GenesisClient(configuration, request);
        client.debugMode(true);
        client.execute();
        return client.getTransaction().getRequest();
    }
}
