package com.emerchantpay.gateway.mobile;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.mobile.AfricanMobileSaleRequest;
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

public class AfricanMobileSaleRequestTest {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1.0);
    private static final String OPERATOR = "VODACOM";
    private static final String TARGET = "315261";

    private String uniqueId;
    private GenesisClient client;
    private AfricanMobileSaleRequest africanMobileSaleRequest;

    @Before
    public void createObjects() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        africanMobileSaleRequest = prepareObject();
    }

    private AfricanMobileSaleRequest prepareObject() throws MalformedURLException {
        africanMobileSaleRequest = new AfricanMobileSaleRequest();
        africanMobileSaleRequest.setCurrency(Currency.GHS.getCurrency());
        africanMobileSaleRequest.setAmount(AMOUNT);
        africanMobileSaleRequest.setTransactionId(uniqueId);
        africanMobileSaleRequest.setRemoteIp("82.137.112.202");
        africanMobileSaleRequest.setCustomerEmail("test@abv.bg");
        africanMobileSaleRequest.setCustomerPhone("124411");
        africanMobileSaleRequest.setUsage("TICKETS");
        africanMobileSaleRequest.setTarget(TARGET);
        africanMobileSaleRequest.setOperator(OPERATOR);
        africanMobileSaleRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        africanMobileSaleRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        africanMobileSaleRequest.setBillingPrimaryAddress("First Avenue");
        africanMobileSaleRequest.setBillingSecondaryAddress("Second Avenue");
        africanMobileSaleRequest.setBillingFirstname("John");
        africanMobileSaleRequest.setBillingLastname("Doe");
        africanMobileSaleRequest.setBillingCity("Rosario");
        africanMobileSaleRequest.setBillingCountry(Country.Ghana.getCountry());
        africanMobileSaleRequest.setBillingZipCode("M4B1B3");
        africanMobileSaleRequest.setBillingState("BE");

        return africanMobileSaleRequest;
    }

    private void verifyExecute() {
        africanMobileSaleRequest.toXML();

        when(client.execute()).thenReturn(africanMobileSaleRequest);

        assertEquals(AMOUNT, africanMobileSaleRequest.getAmount());
        assertEquals(Currency.GHS.getCurrency(), africanMobileSaleRequest.getCurrency());
        assertEquals(TransactionTypes.AFRICAN_MOBILE_SALE, africanMobileSaleRequest.getTransactionType());
        assertEquals(uniqueId, africanMobileSaleRequest.getTransactionId());
        assertEquals(OPERATOR, africanMobileSaleRequest.getOperator());
        assertEquals(TARGET, africanMobileSaleRequest.getTarget());

        assertEquals(africanMobileSaleRequest, africanMobileSaleRequest.setAmount(AMOUNT).setCurrency(Currency.GHS.getCurrency()));
        assertEquals(africanMobileSaleRequest, africanMobileSaleRequest.setTransactionId(uniqueId));
        assertEquals(africanMobileSaleRequest, africanMobileSaleRequest.setOperator(OPERATOR));
        assertEquals(africanMobileSaleRequest, africanMobileSaleRequest.setTarget(TARGET));

        assertEquals(africanMobileSaleRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidArguments() {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() {
        africanMobileSaleRequest.setAmount(null);
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCurrency() {
        africanMobileSaleRequest.setCurrency(null);
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() {
        africanMobileSaleRequest.setTransactionId(null);
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenMissingTarget() {
        africanMobileSaleRequest.setTarget(null);
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenMissingOperator() {
        africanMobileSaleRequest.setOperator(null);
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCountry() {
        africanMobileSaleRequest.setBillingCountry(null);
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenMissingCustomerPhone() {
        africanMobileSaleRequest.setCustomerPhone(null);
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenInvalidCountry() {
        africanMobileSaleRequest.setBillingCountry(Country.Belgium.getCountry());
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenInvalidCurrency() {
        africanMobileSaleRequest.setCurrency(Currency.EUR.getCurrency());
        africanMobileSaleRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenInvalidOperator() {
        africanMobileSaleRequest.setOperator("A1");
        africanMobileSaleRequest.toXML();
    }
}