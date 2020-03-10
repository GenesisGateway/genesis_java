package com.emerchantpay.gateway.nonfinancial.fx;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.fx.FXSearchRateRequest;
import com.emerchantpay.gateway.model.fx.Rate;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FXSearchRateTest {

    private FXSearchRateRequest fxSearchRateRequest;
    private JsonNode node;
    private Rate rate;

    @Before
    public void stubFXSearchRate() {
        node = mock(JsonNode.class);
        fxSearchRateRequest = mock(FXSearchRateRequest.class);
        fxSearchRateRequest.setSourceCurrency("EUR");
        fxSearchRateRequest.setTargetCurrency("USD");
        rate = mock(Rate.class);
    }

    public void verifyFXSearchRateExecute() {
        when(fxSearchRateRequest.execute()).thenReturn(node);
        when(fxSearchRateRequest.getRate()).thenReturn(rate);
        assertEquals(fxSearchRateRequest.execute(), node);
        assertEquals(fxSearchRateRequest.getRate(), rate);
        verify(fxSearchRateRequest).execute();
        verifyNoMoreInteractions(fxSearchRateRequest.execute());
    }

    @Test
    public void testSearchRateSuccess() {
        verifyFXSearchRateExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testSearchRateFailure() {
        fxSearchRateRequest = new FXSearchRateRequest();
        fxSearchRateRequest.toJSON();
        fxSearchRateRequest.execute();
        verifyFXSearchRateExecute();
    }
}
