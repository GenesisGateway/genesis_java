package com.emerchantpay.gateway.nonfinancial.fx;

import com.emerchantpay.gateway.api.requests.nonfinancial.fx.FXGetTiersRequest;
import com.emerchantpay.gateway.model.fx.Tier;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FXGetTiersTest {

    private FXGetTiersRequest fxGetTiersRequest;
    private JsonNode node;
    private Tier tier;
    private List<Tier> tierList;

    @Before
    public void stubFXGetTiers() {
        node = mock(JsonNode.class);
        fxGetTiersRequest = mock(FXGetTiersRequest.class);
        tier = mock(Tier.class);
        tierList = new ArrayList<Tier>();
        tierList.add(tier);
    }

    public void verifyFXGetTiersExecute() {
        when(fxGetTiersRequest.execute()).thenReturn(node);
        when(fxGetTiersRequest.getTires()).thenReturn(tierList);
        assertEquals(fxGetTiersRequest.execute(), node);
        assertEquals(fxGetTiersRequest.getTires(), tierList);
        verify(fxGetTiersRequest).execute();
        verifyNoMoreInteractions(fxGetTiersRequest.execute());
    }

    @Test
    public void testGetTiresSuccess() {
        verifyFXGetTiersExecute();
    }
}
