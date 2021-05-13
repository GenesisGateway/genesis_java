package com.emerchantpay.gateway.preauthorization;

import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.ReferenceRequest;
import com.emerchantpay.gateway.api.requests.financial.preauthorization.IncrementalAuthorizeRequest;
import com.emerchantpay.gateway.api.requests.financial.preauthorization.PartialReversalRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ReferenceAttributesTest {

    private String uid;
    private String referenceId;
    private Random random;
    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND_NINETEEN = 19;

    @Before
    public void init() {
        uid = new StringUtils().generateUID();
        referenceId = new StringUtils().generateUID();
        random = new Random();
    }

    public void stubAndVerify(ReferenceRequest request) {

        request.setTransactionId(uid);
        assertEquals(uid, request.getTransactionId());

        String usage = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        request.setUsage(usage);
        assertEquals(usage, request.getUsage());

        request.setRemoteIp("82.137.112.202");
        assertEquals("82.137.112.202", request.getRemoteIp());

        BigDecimal amount =  new BigDecimal(10);
        request.setAmount(amount);
        assertEquals(amount, request.getAmount());

        request.setCurrency(Currency.USD.getCurrency());
        assertEquals(Currency.USD.getCurrency(), request.getCurrency());

        request.setReferenceId(referenceId);
        assertEquals(referenceId, request.getReferenceId());

        assertTrue(request.toXML() instanceof String);
        assertTrue(request.toQueryString("") instanceof String);
    }

    @Test
    public void testIncrementalAuthorizeValid(){
        IncrementalAuthorizeRequest incrAuthRequest = new IncrementalAuthorizeRequest();
        stubAndVerify(incrAuthRequest);
    }

    @Test
    public void testIncrementalAuthorizeZeroAmount(){
        IncrementalAuthorizeRequest incrAuthRequest = new IncrementalAuthorizeRequest();
        stubAndVerify(incrAuthRequest);
        incrAuthRequest.setAmount(new BigDecimal(0));
        incrAuthRequest.toXML();
    }

    @Test
    public void testPartialReversalValid(){
        PartialReversalRequest partialReversalRequest = new PartialReversalRequest();
        stubAndVerify(partialReversalRequest);
    }

    @Test(expected = RequiredParamsException.class)
    public void testMissingReferenceId(){
        IncrementalAuthorizeRequest incrAuthRequest = new IncrementalAuthorizeRequest();
        stubAndVerify(incrAuthRequest);
        incrAuthRequest.setReferenceId("");
        incrAuthRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testMissingTransactionId(){
        IncrementalAuthorizeRequest incrAuthRequest = new IncrementalAuthorizeRequest();
        stubAndVerify(incrAuthRequest);
        incrAuthRequest.setTransactionId("");
        incrAuthRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testWrongAmount(){
        PartialReversalRequest partialReversalRequest = new PartialReversalRequest();
        stubAndVerify(partialReversalRequest);
        partialReversalRequest.setAmount(new BigDecimal(0));
        partialReversalRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testMissingCurrency(){
        PartialReversalRequest partialReversalRequest = new PartialReversalRequest();
        stubAndVerify(partialReversalRequest);
        partialReversalRequest.setCurrency("");
        partialReversalRequest.toXML();
    }
}