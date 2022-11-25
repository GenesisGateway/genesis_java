package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TokenizationAttributesTest {

    private AuthorizeRequest authorizeRequest;

    private static final String TEST_CONSUMER_ID = "12345";
    private static final String TEST_TOKEN = "abcd1234";
    private static final String TEST_CARD_NUMBER = "4200000000000000";

    @Before
    public void createTravelDataAttributes() {
        authorizeRequest = new AuthorizeRequest();
        authorizeRequest.setAmount(BigDecimal.valueOf(1));
        authorizeRequest.setCurrency("EUR");
        authorizeRequest.setCardHolder("Test Holder");
        String uid = new StringUtils().generateUID();
        authorizeRequest.setTransactionId(uid);
    }

    @Test
    public void buildTokenizationAttributes() {
        assertEquals(false, authorizeRequest.getRememberCard());
        authorizeRequest.setRememberCard(true);
        assertEquals(true, authorizeRequest.getRememberCard());

        authorizeRequest.setConsumerId(TEST_CONSUMER_ID);
        assertEquals(TEST_CONSUMER_ID, authorizeRequest.getConsumerId());

        authorizeRequest.setToken(TEST_TOKEN);
        assertEquals(TEST_TOKEN, authorizeRequest.getToken());

        assertTrue(authorizeRequest.buildTokenizationParams() instanceof RequestBuilder);
    }

    @Test(expected = GenesisException.class)
    public void consumerIdError() {
        authorizeRequest.setConsumerId("a");
        authorizeRequest.buildTokenizationParams();
    }

    @Test(expected = RequiredParamsException.class)
    public void requiredConsumerIdError() {
        authorizeRequest.setToken(TEST_TOKEN);
        authorizeRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void requiredCustomerEmailError() {
        authorizeRequest.setRememberCard(true);
        authorizeRequest.setConsumerId(TEST_CONSUMER_ID);
        authorizeRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void requiredTokenOrCardNumberError() {
        authorizeRequest.setToken(TEST_TOKEN);
        authorizeRequest.setCardNumber(TEST_CARD_NUMBER);
        authorizeRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void requiredExpirationDateError() {
        authorizeRequest.setCardNumber(TEST_CARD_NUMBER);
        authorizeRequest.toXML();
    }
}
