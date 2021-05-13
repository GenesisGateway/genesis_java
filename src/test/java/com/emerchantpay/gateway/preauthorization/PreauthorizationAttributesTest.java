package com.emerchantpay.gateway.preauthorization;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PreauthorizationAttributesTest {

    private AuthorizeRequest authorizeRequest;

    @Before
    public void createTravelDataAttributes() {
        authorizeRequest = new AuthorizeRequest();
    }

    @Test
    public void buildPreauthorizationAttributes() {

        assertEquals(false, authorizeRequest.getPreauthorization());
        authorizeRequest.setPreauthorization(true);
        assertEquals(true, authorizeRequest.getPreauthorization());

        assertTrue(authorizeRequest.buildPreauthorizationParams() instanceof RequestBuilder);
    }
}
