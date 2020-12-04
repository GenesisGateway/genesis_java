package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SdkAttributesTest {

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
    public void buildSdkAttributes() {

        String sdkInterface = "both";
        sale3DRequest.set3dsV2SDKInterface(sdkInterface);
        assertEquals(sdkInterface, sale3DRequest.get3dsV2SDKInterface());

        ArrayList<String> uiTypes = new  ArrayList<String>();
        uiTypes.add("single_select");
        uiTypes.add("multi_select");
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

        assertTrue(sale3DRequest.build3DSv2SdkParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void maxTimeoutError(){
        Integer maxTimeout = 2;
        sale3DRequest.set3dsV2SDKMaxTimeout(maxTimeout);
        sale3DRequest.build3DSv2SdkParams();
    }

    @Test(expected = InvalidParamException.class)
    public void interfaceError(){
        String sdkInterface = "wrong value";
        sale3DRequest.set3dsV2SDKInterface(sdkInterface);
        sale3DRequest.build3DSv2SdkParams();
    }

    @Test(expected = InvalidParamException.class)
    public void uiTypeError(){
        ArrayList<String> uiTypes = new ArrayList<String>();
        uiTypes.add("wrong value");
        sale3DRequest.set3dsV2SDKUITypes(uiTypes);
        sale3DRequest.build3DSv2SdkParams();
    }
}
