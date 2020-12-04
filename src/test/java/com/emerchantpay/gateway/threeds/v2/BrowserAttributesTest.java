package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BrowserAttributesTest {

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
    public void buildBrowserAttributes() {

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

        assertTrue(sale3DRequest.build3DSv2BrowserParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void colorDepthError(){
        sale3DRequest.set3dsV2BrowserColorDepth(13);
        sale3DRequest.build3DSv2BrowserParams();
    }
}