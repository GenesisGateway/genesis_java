package com.emerchantpay.gateway.nonfinancial.fx;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.fx.FXGetRateRequest;
import com.emerchantpay.gateway.model.fx.Rate;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FXGetRateTest {

    private FXGetRateRequest fxGetRateRequest;
    private JsonNode node;
    private Rate rate;

    @Before
    public void stubFXGetRate() {
        node = mock(JsonNode.class);
        fxGetRateRequest = mock(FXGetRateRequest.class);
        fxGetRateRequest.setTierId("1");
        fxGetRateRequest.setRateId("1");
        rate = mock(Rate.class);
    }

    public void verifyFXGetRateExecute() {
        when(fxGetRateRequest.execute()).thenReturn(node);
        when(fxGetRateRequest.getRate()).thenReturn(rate);
        assertEquals(fxGetRateRequest.execute(), node);
        assertEquals(fxGetRateRequest.getRate(), rate);
        verify(fxGetRateRequest).execute();
        verifyNoMoreInteractions(fxGetRateRequest.execute());
    }

    @Test
    public void testGetRateSuccess() {
        verifyFXGetRateExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testFXGetRateFailure() {
        fxGetRateRequest = new FXGetRateRequest();
        fxGetRateRequest.execute();
        verifyFXGetRateExecute();
    }
}
