package com.emerchantpay.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.emerchantpay.gateway.api.constants.Endpoints;
import org.junit.jupiter.api.Test;

public class EndpointsTest {

    @Test
    public void testEcomprocessingEndpoint() {
        assertEquals("e-comprocessing.net", Endpoints.ECOMPROCESSING.getEndpointName());
    }

    @Test
    public void testEmerchantpayEndpoint() {
        assertEquals("emerchantpay.net", Endpoints.EMERCHANTPAY.getEndpointName());
    }

    @Test
    public void testScaCheckerEndpoint() {
        assertEquals("sca/checker", Endpoints.SCA_CHECKER.getEndpointName());
    }

    @Test
    public void testThreedsMethodEndpoint() {
        assertEquals("threeds/threeds_method", Endpoints.THREEDS_METHOD.getEndpointName());
    }

    @Test
    public void testCustomEndpoint() {
        Endpoints customEndpoint = new Endpoints("custom-endpoint.net");
        assertEquals("custom-endpoint.net", customEndpoint.getEndpointName());
    }

    @Test
    public void testToString() {
        assertEquals("emerchantpay.net", Endpoints.EMERCHANTPAY.toString());
    }
}
