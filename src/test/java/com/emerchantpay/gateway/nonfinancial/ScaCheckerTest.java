package com.emerchantpay.gateway.nonfinancial;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.ScaCheckerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ScaCheckerTest {

    private ScaCheckerRequest scaCheckerRequest;
    private JsonNode node;

    @Before
    public void stubScaCheckerRequest() {
        node = mock(JsonNode.class);
        scaCheckerRequest = mock(ScaCheckerRequest.class);
        scaCheckerRequest.setCardNumber("420000000000");
        scaCheckerRequest.setMoto(false);
        scaCheckerRequest.setMit(false);
        scaCheckerRequest.setRecurring(false);
        scaCheckerRequest.setTransactionCurrency("EUR");
        scaCheckerRequest.setTransactionAmount(new BigDecimal("2.00"));
    }

    public void verifyScaCheckerExecute() {
        when(scaCheckerRequest.execute()).thenReturn(node);
        assertEquals(scaCheckerRequest.execute(), node);
        verify(scaCheckerRequest).execute();
        verifyNoMoreInteractions(scaCheckerRequest.execute());
    }

    @Test
    public void testScaCheckerSuccess() {
        verifyScaCheckerExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testSearchRateFailure() {
        scaCheckerRequest = new ScaCheckerRequest();
        scaCheckerRequest.toJSON();
        scaCheckerRequest.execute();
        verifyScaCheckerExecute();
    }
}
