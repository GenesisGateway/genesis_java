package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ChargebackByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ChargebackRequest;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CharbacksTest {

    private GenesisClient client;
    private ChargebackRequest chargeback;
    private ChargebackByDateRequest chargeByDate;

    @Before
    public void createSingle() {
        client = mock(GenesisClient.class);
        chargeback = mock(ChargebackRequest.class);
    }

    @Before
    public void createChargebackByDate() {
        client = mock(GenesisClient.class);
        chargeByDate = mock(ChargebackByDateRequest.class);
    }

    public void verifySingleExecute() {
        when(client.execute()).thenReturn(chargeback);
        assertEquals(client.execute(), chargeback);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyByDateExecute() {
        when(client.execute()).thenReturn(chargeback);
        assertEquals(client.execute(), chargeback);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSingle() {

        // Single
        when(chargeback.setArn(isA(String.class))).thenReturn(chargeback);

        assertEquals(chargeback.setArn("74537604221431003881865"), chargeback);

        verify(chargeback).setArn("74537604221431003881865");
        verifyNoMoreInteractions(chargeback);

        verifySingleExecute();
    }

    @Test
    public void testByDate() {
        // By Date
        when(chargeByDate.setStartDate(isA(String.class))).thenReturn(chargeByDate);
        when(chargeByDate.setEndDate(isA(String.class))).thenReturn(chargeByDate);
        when(chargeByDate.setPage(isA(Integer.class))).thenReturn(chargeByDate);

        assertEquals(chargeByDate.setStartDate("20-04-2016"), chargeByDate);
        assertEquals(chargeByDate.setEndDate("20-04-2017"), chargeByDate);
        assertEquals(chargeByDate.setPage(2), chargeByDate);

        verify(chargeByDate).setStartDate("20-04-2016");
        verify(chargeByDate).setEndDate("20-04-2017");
        verify(chargeByDate).setPage(2);
        verifyNoMoreInteractions(chargeByDate);

        verifyByDateExecute();
    }
}
