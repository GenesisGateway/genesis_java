package com.emerchantpay.gateway.nonfinancial.fx;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.fx.FXGetRatesRequest;
import com.emerchantpay.gateway.model.fx.Rate;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FXGetRatesTest {

    private FXGetRatesRequest fxGetRatesRequest;
    private JsonNode node;
    private Rate rate;
    private List<Rate> rateList;

    @Before
    public void stubFXGetRates() {
        node = mock(JsonNode.class);
        fxGetRatesRequest = mock(FXGetRatesRequest.class);
        rate = mock(Rate.class);
        rateList = new ArrayList<Rate>();
        rateList.add(rate);
    }

    public void verifyFXGetRatesExecute() {
        when(fxGetRatesRequest.execute()).thenReturn(node);
        when(fxGetRatesRequest.getRates()).thenReturn(rateList);
        assertEquals(fxGetRatesRequest.execute(), node);
        assertEquals(fxGetRatesRequest.getRates(), rateList);
        verify(fxGetRatesRequest).execute();
        verifyNoMoreInteractions(fxGetRatesRequest.execute());
    }

    @Test
    public void testGetRatesSuccess() {
        verifyFXGetRatesExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testGetRatesFailure() {
        fxGetRatesRequest = new FXGetRatesRequest();
        fxGetRatesRequest.execute();
        verifyFXGetRatesExecute();
    }
}
