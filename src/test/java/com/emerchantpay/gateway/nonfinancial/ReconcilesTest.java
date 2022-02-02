package com.emerchantpay.gateway.nonfinancial;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileRequest;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ReconcilesTest {

    private GenesisClient client;
    private ReconcileRequest reconcileRequest;
    private ReconcileByDateRequest reconcileByDate = new ReconcileByDateRequest();

    private String uniqueId;

    @Before
    public void createSingle() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        reconcileRequest = mock(ReconcileRequest.class);
    }

    @Before
    public void createChargebackByDate() {
        client = mock(GenesisClient.class);
        reconcileByDate = mock(ReconcileByDateRequest.class);
    }

    public void verifySingleExecute() {
        when(client.execute()).thenReturn(reconcileRequest);
        assertEquals(client.execute(), reconcileRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyByDateExecute() {
        when(client.execute()).thenReturn(reconcileByDate);
        assertEquals(client.execute(), reconcileByDate);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSingle() {

        // Single
        when(reconcileRequest.setArn(isA(String.class))).thenReturn(reconcileRequest);

        assertEquals(reconcileRequest.setArn("74537604221431003881865"), reconcileRequest);

        verify(reconcileRequest).setArn("74537604221431003881865");
        verifyNoMoreInteractions(reconcileRequest);

        verifySingleExecute();
    }

    @Test
    public void testSingleReconcileTransactionId() {
        ReconcileRequest reconcileRequest = new ReconcileRequest();
        reconcileRequest.setTransactionId(uniqueId);
        assertEquals(uniqueId, reconcileRequest.getTransactionId());
        verifySingleExecute();
    }

    @Test
    public void testSingleReconcileUniqueId() {
        ReconcileRequest reconcileRequest = new ReconcileRequest();
        reconcileRequest.setUniqueId(uniqueId);
        assertEquals(uniqueId, reconcileRequest.getUniqueId());
        verifySingleExecute();
    }

    @Test
    public void testByDate() {
        // By Date
        when(reconcileByDate.setStartDate(isA(String.class))).thenReturn(reconcileByDate);
        when(reconcileByDate.setEndDate(isA(String.class))).thenReturn(reconcileByDate);
        when(reconcileByDate.setPage(isA(Integer.class))).thenReturn(reconcileByDate);

        assertEquals(reconcileByDate.setStartDate("20-04-2016"), reconcileByDate);
        assertEquals(reconcileByDate.setEndDate("20-04-2017"), reconcileByDate);
        assertEquals(reconcileByDate.setPage(2), reconcileByDate);

        verify(reconcileByDate).setStartDate("20-04-2016");
        verify(reconcileByDate).setEndDate("20-04-2017");
        verify(reconcileByDate).setPage(2);
        verifyNoMoreInteractions(reconcileByDate);

        verifyByDateExecute();
    }
}
