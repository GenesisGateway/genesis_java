package com.emerchantpay.gateway.mobile;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.mobile.AfricanMobilePayoutRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AfricanMobilePayoutRequestTest {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1.0);
    private static final String OPERATOR = "AIRTEL";
    private static final String TARGET = "315261";

    private String uniqueId;
    private GenesisClient client;
    private AfricanMobilePayoutRequest аfricanMobilePayoutRequest;

    @Before
    public void createObjects() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        аfricanMobilePayoutRequest = prepareObject();
    }

    private AfricanMobilePayoutRequest prepareObject() throws MalformedURLException {
        аfricanMobilePayoutRequest = new AfricanMobilePayoutRequest();
        аfricanMobilePayoutRequest.setCurrency(Currency.GHS.getCurrency());
        аfricanMobilePayoutRequest.setAmount(AMOUNT);
        аfricanMobilePayoutRequest.setTransactionId(uniqueId);
        аfricanMobilePayoutRequest.setRemoteIp("82.137.112.202");
        аfricanMobilePayoutRequest.setCustomerEmail("test@abv.bg");
        аfricanMobilePayoutRequest.setCustomerPhone("124411");
        аfricanMobilePayoutRequest.setUsage("TICKETS");
        аfricanMobilePayoutRequest.setTarget(TARGET);
        аfricanMobilePayoutRequest.setOperator(OPERATOR);
        аfricanMobilePayoutRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        аfricanMobilePayoutRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        аfricanMobilePayoutRequest.setBillingPrimaryAddress("First Avenue");
        аfricanMobilePayoutRequest.setBillingSecondaryAddress("Second Avenue");
        аfricanMobilePayoutRequest.setBillingFirstname("John");
        аfricanMobilePayoutRequest.setBillingLastname("Doe");
        аfricanMobilePayoutRequest.setBillingCity("Rosario");
        аfricanMobilePayoutRequest.setBillingCountry(Country.Ghana.getCountry());
        аfricanMobilePayoutRequest.setBillingZipCode("M4B1B3");
        аfricanMobilePayoutRequest.setBillingState("BE");

        return аfricanMobilePayoutRequest;
    }

    private void verifyExecute() {
        аfricanMobilePayoutRequest.toXML();

        when(client.execute()).thenReturn(аfricanMobilePayoutRequest);

        assertEquals(AMOUNT, аfricanMobilePayoutRequest.getAmount());
        assertEquals(Currency.GHS.getCurrency(), аfricanMobilePayoutRequest.getCurrency());
        assertEquals(TransactionTypes.AFRICAN_MOBILE_PAYOUT, аfricanMobilePayoutRequest.getTransactionType());
        assertEquals(uniqueId, аfricanMobilePayoutRequest.getTransactionId());
        assertEquals(OPERATOR, аfricanMobilePayoutRequest.getOperator());
        assertEquals(TARGET, аfricanMobilePayoutRequest.getTarget());

        assertEquals(аfricanMobilePayoutRequest, аfricanMobilePayoutRequest.setAmount(AMOUNT).setCurrency(Currency.GHS.getCurrency()));
        assertEquals(аfricanMobilePayoutRequest, аfricanMobilePayoutRequest.setTransactionId(uniqueId));
        assertEquals(аfricanMobilePayoutRequest, аfricanMobilePayoutRequest.setOperator(OPERATOR));
        assertEquals(аfricanMobilePayoutRequest, аfricanMobilePayoutRequest.setTarget(TARGET));

        assertEquals(аfricanMobilePayoutRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidArguments() {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() {
        аfricanMobilePayoutRequest.setAmount(null);
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCurrency() {
        аfricanMobilePayoutRequest.setCurrency(null);
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() {
        аfricanMobilePayoutRequest.setTransactionId(null);
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenMissingTarget() {
        аfricanMobilePayoutRequest.setTarget(null);
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenMissingOperator() {
        аfricanMobilePayoutRequest.setOperator(null);
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCountry() {
        аfricanMobilePayoutRequest.setBillingCountry(null);
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenMissingCustomerPhone() {
        аfricanMobilePayoutRequest.setCustomerPhone(null);
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenInvalidCountry() {
        аfricanMobilePayoutRequest.setBillingCountry(Country.Belgium.getCountry());
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenInvalidCurrency() {
        аfricanMobilePayoutRequest.setCurrency(Currency.EUR.getCurrency());
        аfricanMobilePayoutRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenInvalidOperator() {
        аfricanMobilePayoutRequest.setOperator("A1");
        аfricanMobilePayoutRequest.toXML();
    }
}