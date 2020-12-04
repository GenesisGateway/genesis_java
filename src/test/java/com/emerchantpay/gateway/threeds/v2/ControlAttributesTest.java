package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ControlAttributesTest {

    private Sale3DRequest sale3DRequest;

    @Before
    public void createThreedsV2Attributes() {
        sale3DRequest = new Sale3DRequest();
    }

    @Test
    public void buildControlAttributes() {

        String deviceType = "browser";
        sale3DRequest.set3dsV2ControlDeviceType(deviceType);
        assertEquals(deviceType, sale3DRequest.get3dsV2ControlDeviceType());

        String challengeWindowSize = "390x400";
        sale3DRequest.set3dsV2ControlChallengeWindowSize(challengeWindowSize);
        assertEquals(challengeWindowSize, sale3DRequest.get3dsV2ControlChallengeWindowSize());

        String challengeIndicator = "preference";
        sale3DRequest.set3dsV2ControlChallengeIndicator(challengeIndicator);
        assertEquals(challengeIndicator, sale3DRequest.get3dsV2ControlChallengeIndicator());

        assertTrue(sale3DRequest.build3DSv2ControlParams() instanceof RequestBuilder);
    }

    @Test (expected = RequiredParamsException.class)
    public void missingControlParam(){
        //challengeWindowSize is required when device type is browser
        String deviceType = "browser";
        sale3DRequest.set3dsV2ControlDeviceType(deviceType);
        assertEquals(deviceType, sale3DRequest.get3dsV2ControlDeviceType());
        sale3DRequest.build3DSv2ControlParams();
    }

    @Test(expected = InvalidParamException.class)
    public void deviceTypeError(){
        sale3DRequest.set3dsV2ControlDeviceType("wrong value");
        sale3DRequest.build3DSv2ControlParams();
    }

    @Test(expected = InvalidParamException.class)
    public void challengeWindowSizeError(){
        sale3DRequest.set3dsV2ControlChallengeWindowSize("wrong value");
        sale3DRequest.build3DSv2ControlParams();
    }

    @Test(expected = InvalidParamException.class)
    public void challengeIndicatorError(){
        sale3DRequest.set3dsV2ControlChallengeIndicator("wrong value");
        sale3DRequest.build3DSv2ControlParams();
    }
}