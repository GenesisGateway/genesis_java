package com.emerchantpay.gateway.nonfinancial;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ReportsByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ReportsRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ReportsTest {

    private GenesisClient client;
    private ReportsRequest reportRequest;
    private ReportsByDateRequest reportByDate;

    @Before
    public void createSingle() {
        client = mock(GenesisClient.class);
        reportRequest = mock(ReportsRequest.class);
    }

    @Before
    public void createReportsByDate() {
        client = mock(GenesisClient.class);
        reportByDate = mock(ReportsByDateRequest.class);
    }

    public void verifySingleExecute() {
        when(client.execute()).thenReturn(reportRequest);
        assertEquals(client.execute(), reportRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyByDateExecute() {
        when(client.execute()).thenReturn(reportByDate);
        assertEquals(client.execute(), reportByDate);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSingle() {
        // Single
        when(reportRequest.setArn(isA(String.class))).thenReturn(reportRequest);

        assertEquals(reportRequest.setArn("74537604221431003881865"), reportRequest);

        verify(reportRequest).setArn("74537604221431003881865");
        verifyNoMoreInteractions(reportRequest);

        verifySingleExecute();
    }

    @Test
    public void testByDate() {
        // By Date
        when(reportByDate.setStartDate(isA(String.class))).thenReturn(reportByDate);
        when(reportByDate.setEndDate(isA(String.class))).thenReturn(reportByDate);
        when(reportByDate.setPage(isA(Integer.class))).thenReturn(reportByDate);

        assertEquals(reportByDate.setStartDate("20-04-2016"), reportByDate);
        assertEquals(reportByDate.setEndDate("20-04-2017"), reportByDate);
        assertEquals(reportByDate.setPage(2), reportByDate);

        verify(reportByDate).setStartDate("20-04-2016");
        verify(reportByDate).setEndDate("20-04-2017");
        verify(reportByDate).setPage(2);
        verifyNoMoreInteractions(reportByDate);

        verifyByDateExecute();
    }
}
