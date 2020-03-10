package com.emerchantpay.gateway.nonfinancial.fx;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.fx.FXGetTierRequest;
import com.emerchantpay.gateway.model.fx.Tier;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FXGetTierTest {

    private FXGetTierRequest fxGetTierRequest;
    private JsonNode node;
    private Tier tier;

    @Before
    public void stubFXGetTier() {
        node = mock(JsonNode.class);
        fxGetTierRequest = mock(FXGetTierRequest.class);
        fxGetTierRequest.setId("1");
        tier = mock(Tier.class);
    }

    public void verifyFXGetTierExecute() {
        when(fxGetTierRequest.execute()).thenReturn(node);
        when(fxGetTierRequest.getTier()).thenReturn(tier);
        assertEquals(fxGetTierRequest.execute(), node);
        assertEquals(fxGetTierRequest.getTier(), tier);
        verify(fxGetTierRequest).execute();
        verifyNoMoreInteractions(fxGetTierRequest.execute());
    }

    @Test
    public void testGetTierSuccess() {
        verifyFXGetTierExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testFXGetTierFailure() {
        fxGetTierRequest = new FXGetTierRequest();
        fxGetTierRequest.execute();
        verifyFXGetTierExecute();
    }
}
