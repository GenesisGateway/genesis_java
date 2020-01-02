package com.emerchantpay.gateway.nonfinancial;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.RetrievalByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.RetrievalRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class RetrievalsTest {

    private GenesisClient client;
    private RetrievalRequest retrieval;
    private RetrievalByDateRequest retrievalByDate;

    @Before
    public void createSingle() {
        client = mock(GenesisClient.class);
        retrieval = mock(RetrievalRequest.class);
    }

    @Before
    public void createChargebackByDate() {
        client = mock(GenesisClient.class);
        retrievalByDate = mock(RetrievalByDateRequest.class);
    }

    public void verifySingleExecute() {
        when(client.execute()).thenReturn(retrieval);
        assertEquals(client.execute(), retrieval);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyByDateExecute() {
        when(client.execute()).thenReturn(retrievalByDate);
        assertEquals(client.execute(), retrievalByDate);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSingle() {
        // Single
        when(retrieval.setArn(isA(String.class))).thenReturn(retrieval);

        assertEquals(retrieval.setArn("74537604221431003881865"), retrieval);

        verify(retrieval).setArn("74537604221431003881865");
        verifyNoMoreInteractions(retrieval);

        verifySingleExecute();
    }

    @Test
    public void testByDate() {
        // By Date
        when(retrievalByDate.setStartDate(isA(String.class))).thenReturn(retrievalByDate);
        when(retrievalByDate.setEndDate(isA(String.class))).thenReturn(retrievalByDate);
        when(retrievalByDate.setPage(isA(Integer.class))).thenReturn(retrievalByDate);

        assertEquals(retrievalByDate.setStartDate("20-04-2016"), retrievalByDate);
        assertEquals(retrievalByDate.setEndDate("20-04-2017"), retrievalByDate);
        assertEquals(retrievalByDate.setPage(2), retrievalByDate);

        verify(retrievalByDate).setStartDate("20-04-2016");
        verify(retrievalByDate).setEndDate("20-04-2017");
        verify(retrievalByDate).setPage(2);
        verifyNoMoreInteractions(retrievalByDate);

        verifyByDateExecute();
    }
}
