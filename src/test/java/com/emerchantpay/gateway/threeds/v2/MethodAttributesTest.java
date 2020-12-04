package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MethodAttributesTest {

    private Sale3DRequest sale3DRequest;

    @Before
    public void createThreedsV2Attributes() {
        sale3DRequest = new Sale3DRequest();
    }

    @Test
    public void buildMethodAttributes() throws MalformedURLException {
        String url = "http://www.test.com";
        sale3DRequest.set3dsV2MethodCallbackUrl(new URL(url));
        assertEquals(url, sale3DRequest.get3dsV2MethodCallbackUrl());
        assertTrue(sale3DRequest.build3DSv2MethodParams() instanceof RequestBuilder);
    }
}