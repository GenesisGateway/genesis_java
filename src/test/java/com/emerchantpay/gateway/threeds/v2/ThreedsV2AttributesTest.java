package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThreedsV2AttributesTest {

    private Sale3DRequest sale3DRequest;
    private Random random;
    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND_NINETEEN = 19;

    @Before
    public void createThreedsV2Attributes() {
        sale3DRequest = new Sale3DRequest();
        random = new Random();
    }

    @Test
    public void buildThreedsV2AttributesBrowserDevice() {
        String deviceType = "browser";
        sale3DRequest.set3dsV2ControlDeviceType(deviceType);
        assertEquals(deviceType, sale3DRequest.get3dsV2ControlDeviceType());

        String challengeWindowSize = "390x400";
        sale3DRequest.set3dsV2ControlChallengeWindowSize(challengeWindowSize);
        assertEquals(challengeWindowSize, sale3DRequest.get3dsV2ControlChallengeWindowSize());


        String acceptHeader = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3dsV2BrowserAcceptHeader(acceptHeader);
        assertEquals(acceptHeader, sale3DRequest.get3dsV2BrowserAcceptHeader());

        sale3DRequest.set3dsV2BrowserJavaEnabled(true);
        assertEquals(true, sale3DRequest.get3dsV2BrowserJavaEnabled());

        String language = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3dsV2BrowserLanguage(language);
        assertEquals(language, sale3DRequest.get3dsV2BrowserLanguage());

        String timeZoneOffset = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3dsV2BrowserTimeZoneOffset(timeZoneOffset);
        assertEquals(timeZoneOffset, sale3DRequest.get3dsV2BrowserTimeZoneOffset());

        String userAgent = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3DSUserAgent(userAgent);
        assertEquals(userAgent, sale3DRequest.get3dsV2BrowserUserAgent());

        Integer colorDepth = 16;
        sale3DRequest.set3dsV2BrowserColorDepth(colorDepth);
        assertEquals(colorDepth, sale3DRequest.get3dsV2BrowserColorDepth());

        Integer screenHeight = random.nextInt(MAX_BOUND_NINETEEN);
        sale3DRequest.set3dsV2BrowserScreenHeight(screenHeight);
        assertEquals(screenHeight, sale3DRequest.get3dsV2BrowserScreenHeight());

        Integer screenWidth = random.nextInt(MAX_BOUND_NINETEEN);
        sale3DRequest.set3dsV2BrowserScreenWidth(screenWidth);
        assertEquals(screenWidth, sale3DRequest.get3dsV2BrowserScreenWidth());

        assertTrue(sale3DRequest.buildThreedsV2Params() instanceof RequestBuilder);
    }

    @Test
    public void buildThreedsV2AttributesApplicationDevice() {
        String deviceType = "application";
        sale3DRequest.set3dsV2ControlDeviceType(deviceType);
        assertEquals(deviceType, sale3DRequest.get3dsV2ControlDeviceType());

        String sdkInterface = "both";
        sale3DRequest.set3dsV2SDKInterface(sdkInterface);
        assertEquals(sdkInterface, sale3DRequest.get3dsV2SDKInterface());

        ArrayList<String> uiTypes = new  ArrayList<String>();
        uiTypes.add("single_select");
        sale3DRequest.set3dsV2SDKUITypes(uiTypes);
        assertEquals(uiTypes, sale3DRequest.get3dsV2SDKUITypes());

        String applicationId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3dsV2SDKApplicationId(applicationId);
        assertEquals(applicationId, sale3DRequest.get3dsV2SDKApplicationId());

        String encryptedDate = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3dsV2SDKEncryptedData(encryptedDate);
        assertEquals(encryptedDate, sale3DRequest.get3dsV2SDKEncryptedData());

        String ephemeralPublicKeyPair = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3dsV2SDKEphemeralPublicKeyPair(ephemeralPublicKeyPair);
        assertEquals(ephemeralPublicKeyPair, sale3DRequest.get3dsV2SDKEphemeralPublicKeyPair());

        String referenceNumber = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        sale3DRequest.set3dsV2SDKReferenceNumber(referenceNumber);
        assertEquals(referenceNumber, sale3DRequest.get3dsV2SDKReferenceNumber());

        Integer maxTimeout = random.nextInt(MAX_BOUND_NINETEEN) + 5;//should be greater than 5
        sale3DRequest.set3dsV2SDKMaxTimeout(maxTimeout);
        assertEquals(maxTimeout, sale3DRequest.get3dsV2SDKMaxTimeout());

        assertTrue(sale3DRequest.buildThreedsV2Params() instanceof RequestBuilder);
    }

    @Test (expected = RequiredParamsException.class)
    public void missingBrowserParams(){
        String deviceType = "browser";
        sale3DRequest.set3dsV2ControlDeviceType(deviceType);
        assertEquals(deviceType, sale3DRequest.get3dsV2ControlDeviceType());
        sale3DRequest.buildThreedsV2Params();
    }

    @Test (expected = RequiredParamsException.class)
    public void missingSdkParams(){
        String deviceType = "application";
        sale3DRequest.set3dsV2ControlDeviceType(deviceType);
        assertEquals(deviceType, sale3DRequest.get3dsV2ControlDeviceType());
        sale3DRequest.buildThreedsV2Params();
    }
}
