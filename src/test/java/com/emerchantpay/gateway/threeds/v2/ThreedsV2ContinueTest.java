package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.constants.EndpointApiTypes;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.threeds.v2.ThreedsV2ContinueRequest;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ThreedsV2ContinueTest {

    private ThreedsV2ContinueRequest threedsV2ContinueRequest;
    private Random random;
    private LocalDateTime now;
    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND_NINETEEN = 19;
    private Configuration configuration;

    @Before
    public void createThreedsV2Continue() {

        threedsV2ContinueRequest = new ThreedsV2ContinueRequest();
        random = new Random();
        now = LocalDateTime.now();

        configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
        configuration.setUsername("test");
        configuration.setPassword("test");
        configuration.setToken("test");
    }

    @Test
    public void loadRequestFromResponse() throws NoSuchFieldException {
        //Mock transaction response
        Transaction trx = mock(Transaction.class);
        String uniqueId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        when(trx.getUniqueId()).thenReturn(uniqueId);
        BigDecimal amount = new BigDecimal(random.nextInt(4) + MIN_BOUND);
        when(trx.getAmount()).thenReturn(amount);
        String currency = Currency.EUR.getCurrency();
        when(trx.getCurrency()).thenReturn(currency);
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.from(ZoneOffset.UTC));
        String timeStamp = isoFormatter.format(Instant.now());
        when(trx.getTimestamp()).thenReturn(timeStamp);

        threedsV2ContinueRequest = new ThreedsV2ContinueRequest(trx, configuration);
        assertEquals(uniqueId, threedsV2ContinueRequest.getUniqueId());
        assertEquals(amount, threedsV2ContinueRequest.getAmount());
        assertEquals(currency, threedsV2ContinueRequest.getCurrency());
        assertEquals(timeStamp, threedsV2ContinueRequest.getThreedsTimestamp());
        assertNotNull(threedsV2ContinueRequest.getThreedsSignature());
        assertTrue(threedsV2ContinueRequest.getThreedsMethodContinueUrl().contains(Endpoints.THREEDS_METHOD.getEndpointName() + "/" + uniqueId));
        assertEquals(configuration, threedsV2ContinueRequest.getThreedsConfiguration());
        assertTrue(threedsV2ContinueRequest.getThreedsConfiguration().getAction().contains(Endpoints.THREEDS_METHOD.getEndpointName() + "/" + uniqueId));

        //Call toXML so request is built and it's verified there are no exceptions
        threedsV2ContinueRequest.toXML();
        //Query param added in build request
        assertTrue(threedsV2ContinueRequest.getThreedsConfiguration().getBaseUrl().contains("?signature="));
    }

    @Test
    public void testContinueUrlSmartRoutingEnabled() {
        //Mock transaction response
        Transaction trx = mock(Transaction.class);
        String uniqueId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        when(trx.getUniqueId()).thenReturn(uniqueId);
        BigDecimal amount = new BigDecimal(random.nextInt(4) + MIN_BOUND);
        when(trx.getAmount()).thenReturn(amount);
        String currency = Currency.EUR.getCurrency();
        when(trx.getCurrency()).thenReturn(currency);
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.from(ZoneOffset.UTC));
        String timeStamp = isoFormatter.format(Instant.now());
        when(trx.getTimestamp()).thenReturn(timeStamp);

        configuration.setForceSmartRouting(true);
        threedsV2ContinueRequest = new ThreedsV2ContinueRequest(trx, configuration);
        assertThrows(IllegalArgumentException.class, () -> threedsV2ContinueRequest.setUseSmartRouting(true));
        assertTrue(threedsV2ContinueRequest.getThreedsMethodContinueUrl().equals(
                "https://"
                + Environments.STAGING + "."
                + EndpointApiTypes.GATE + "."
                + Endpoints.EMERCHANTPAY + "/"
                + Endpoints.THREEDS_METHOD.getEndpointName() + "/"
                + uniqueId));
    }

    @Test
    public void manualRequestConstruct() {
        String uniqueId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        threedsV2ContinueRequest.setUniqueId(uniqueId);
        assertEquals(uniqueId, threedsV2ContinueRequest.getUniqueId());

        configuration.setTokenEnabled(false);
        threedsV2ContinueRequest.setThreedsConfiguration(configuration);
        assertEquals(configuration, threedsV2ContinueRequest.getThreedsConfiguration());
        assertTrue(threedsV2ContinueRequest.getThreedsConfiguration().getAction().contains(Endpoints.THREEDS_METHOD.getEndpointName() + "/" + uniqueId));
        assertTrue(threedsV2ContinueRequest.getThreedsMethodContinueUrl().contains(Endpoints.THREEDS_METHOD.getEndpointName() + "/" + uniqueId));

        //Cover unique Id retrieval from Continue URL
        String continueURL = threedsV2ContinueRequest.getThreedsMethodContinueUrl();
        threedsV2ContinueRequest.setUniqueId(null);
        threedsV2ContinueRequest.setThreedsMethodContinueUrl(null);
        assertNull(threedsV2ContinueRequest.getUniqueId());
        assertNull(threedsV2ContinueRequest.getThreedsMethodContinueUrl());

        threedsV2ContinueRequest.setThreedsMethodContinueUrl(continueURL);
        assertEquals(continueURL, threedsV2ContinueRequest.getThreedsMethodContinueUrl());
        assertEquals(uniqueId, threedsV2ContinueRequest.getUniqueId());

        BigDecimal amount = new BigDecimal(random.nextInt(4) + MIN_BOUND);
        threedsV2ContinueRequest.setAmount(amount);
        assertEquals(amount, threedsV2ContinueRequest.getAmount());

        String currency = Currency.EUR.getCurrency();
        threedsV2ContinueRequest.setCurrency(currency);
        assertEquals(currency, threedsV2ContinueRequest.getCurrency());

        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.from(ZoneOffset.UTC));
        String timeStamp = isoFormatter.format(Instant.now());
        threedsV2ContinueRequest.setThreedsTimestamp(timeStamp);
        assertEquals(timeStamp, threedsV2ContinueRequest.getThreedsTimestamp());

        assertNotNull(threedsV2ContinueRequest.getThreedsSignature());

        //Call toQueryString so request is built and it's verified there are no exceptions
        threedsV2ContinueRequest.toQueryString("");
        //Query param added in build request
        assertTrue(threedsV2ContinueRequest.getThreedsConfiguration().getBaseUrl().contains("?signature="));
        assertNull(threedsV2ContinueRequest.getResponse());
        assertNull(threedsV2ContinueRequest.getTransactionResponse());
    }

    @Test(expected = InvalidParamException.class)
    public void invalidResponseError() {
        Transaction trx = mock(Transaction.class);
        when(trx.getUniqueId()).thenReturn(null);
        threedsV2ContinueRequest = new ThreedsV2ContinueRequest(trx, configuration);
    }

    @Test(expected = RegexException.class)
    public void invalidURLError() {
        String uniqueId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        threedsV2ContinueRequest.setUniqueId(uniqueId);
        assertEquals(uniqueId, threedsV2ContinueRequest.getUniqueId());
        BigDecimal amount = new BigDecimal(random.nextInt(4) + MIN_BOUND);
        threedsV2ContinueRequest.setAmount(amount);
        assertEquals(amount, threedsV2ContinueRequest.getAmount());
        String currency = Currency.EUR.getCurrency();
        threedsV2ContinueRequest.setCurrency(currency);
        assertEquals(currency, threedsV2ContinueRequest.getCurrency());

        threedsV2ContinueRequest.setThreedsMethodContinueUrl(null);
        threedsV2ContinueRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void invalidAmountError() {
        String uniqueId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        threedsV2ContinueRequest.setUniqueId(uniqueId);
        assertEquals(uniqueId, threedsV2ContinueRequest.getUniqueId());
        BigDecimal amount = new BigDecimal(0);
        threedsV2ContinueRequest.setAmount(amount);
        assertEquals(amount, threedsV2ContinueRequest.getAmount());
        String currency = Currency.EUR.getCurrency();
        threedsV2ContinueRequest.setCurrency(currency);
        assertEquals(currency, threedsV2ContinueRequest.getCurrency());

        threedsV2ContinueRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void invalidTimestampError() {
        String uniqueId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        threedsV2ContinueRequest.setUniqueId(uniqueId);
        assertEquals(uniqueId, threedsV2ContinueRequest.getUniqueId());
        BigDecimal amount = new BigDecimal(random.nextInt(4) + MIN_BOUND);
        threedsV2ContinueRequest.setAmount(amount);
        assertEquals(amount, threedsV2ContinueRequest.getAmount());
        String currency = Currency.EUR.getCurrency();
        threedsV2ContinueRequest.setCurrency(currency);
        assertEquals(currency, threedsV2ContinueRequest.getCurrency());

        threedsV2ContinueRequest.setThreedsTimestamp("abc");
        threedsV2ContinueRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void requiredTimeStampError() {
        String uniqueId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        threedsV2ContinueRequest.setUniqueId(uniqueId);
        assertEquals(uniqueId, threedsV2ContinueRequest.getUniqueId());
        BigDecimal amount = new BigDecimal(random.nextInt(4) + MIN_BOUND);
        threedsV2ContinueRequest.setAmount(amount);
        assertEquals(amount, threedsV2ContinueRequest.getAmount());
        String currency = Currency.EUR.getCurrency();
        threedsV2ContinueRequest.setCurrency(currency);
        assertEquals(currency, threedsV2ContinueRequest.getCurrency());

        threedsV2ContinueRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void requiredUniqueIdOrURLError() {
        BigDecimal amount = new BigDecimal(random.nextInt(4) + MIN_BOUND);
        threedsV2ContinueRequest.setAmount(amount);
        assertEquals(amount, threedsV2ContinueRequest.getAmount());
        String currency = Currency.EUR.getCurrency();
        threedsV2ContinueRequest.setCurrency(currency);
        assertEquals(currency, threedsV2ContinueRequest.getCurrency());

        threedsV2ContinueRequest.toXML();
    }
}
