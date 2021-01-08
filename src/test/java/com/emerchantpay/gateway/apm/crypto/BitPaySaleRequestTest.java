package com.emerchantpay.gateway.apm.crypto;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.apm.crypto.BitPaySaleRequest;
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

public class BitPaySaleRequestTest {

    private static final double AMOUNT = 1.0;
    private static final String RETURN_URL = "http://www.example.com/return";
    private static final String CUSTOMER_EMAIL = "test@abv.bg";

    private String uniqueId;
    private GenesisClient client;
    private BitPaySaleRequest bitPaySaleRequest;

    @Before
    public void createBitPaySale() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        bitPaySaleRequest = new BitPaySaleRequest();
    }

    private void prepareObject() throws MalformedURLException {
        bitPaySaleRequest.setRemoteIp("82.137.112.202");
        bitPaySaleRequest.setCustomerPhone("124411");
        bitPaySaleRequest.setUsage("TICKETS");
        bitPaySaleRequest.setBillingPrimaryAddress("First Avenue");
        bitPaySaleRequest.setBillingSecondaryAddress("Second Avenue");
        bitPaySaleRequest.setBillingFirstname("John");
        bitPaySaleRequest.setBillingLastname("Doe");
        bitPaySaleRequest.setBillingCity("Rosario");
        bitPaySaleRequest.setBillingCountry(Country.Belgium.getCountry());
        bitPaySaleRequest.setBillingZipCode("M4B1B3");
        bitPaySaleRequest.setBillingState("BE");
    }

    private void verifyExecute() throws MalformedURLException {
        prepareObject();
        bitPaySaleRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPaySaleRequest.setCurrency(Currency.EUR.getCurrency());
        bitPaySaleRequest.setTransactionId(uniqueId);
        bitPaySaleRequest.setCustomerEmail(CUSTOMER_EMAIL);
        bitPaySaleRequest.setReturnUrl(new URL(RETURN_URL));
        bitPaySaleRequest.toXML();

        when(client.execute()).thenReturn(bitPaySaleRequest);

        assertEquals(BigDecimal.valueOf(AMOUNT), bitPaySaleRequest.getAmount());
        assertEquals(Currency.EUR.getCurrency(), bitPaySaleRequest.getCurrency());
        assertEquals(TransactionTypes.BITPAY_SALE, bitPaySaleRequest.getTransactionType());
        assertEquals(RETURN_URL, bitPaySaleRequest.getReturnUrl().toString());
        assertEquals(bitPaySaleRequest, bitPaySaleRequest.setAmount(BigDecimal.valueOf(AMOUNT)).setCurrency(Currency.EUR.getCurrency()));
        assertEquals(bitPaySaleRequest, bitPaySaleRequest.setReturnUrl(new URL(RETURN_URL)).setTransactionId(uniqueId));

        assertEquals(bitPaySaleRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidArguments() throws MalformedURLException {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() throws MalformedURLException {
        prepareObject();
        bitPaySaleRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPaySaleRequest.setCurrency(Currency.EUR.getCurrency());
        bitPaySaleRequest.setCustomerEmail(CUSTOMER_EMAIL);
        bitPaySaleRequest.setReturnUrl(new URL(RETURN_URL));
        bitPaySaleRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() throws MalformedURLException {
        prepareObject();
        bitPaySaleRequest.setCurrency(Currency.EUR.getCurrency());
        bitPaySaleRequest.setTransactionId(uniqueId);
        bitPaySaleRequest.setCustomerEmail(CUSTOMER_EMAIL);
        bitPaySaleRequest.setReturnUrl(new URL(RETURN_URL));
        bitPaySaleRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCurrency() throws MalformedURLException {
        prepareObject();
        bitPaySaleRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPaySaleRequest.setTransactionId(uniqueId);
        bitPaySaleRequest.setCustomerEmail(CUSTOMER_EMAIL);
        bitPaySaleRequest.setReturnUrl(new URL(RETURN_URL));
        bitPaySaleRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCustomerEmail() throws MalformedURLException {
        prepareObject();
        bitPaySaleRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPaySaleRequest.setCurrency(Currency.EUR.getCurrency());
        bitPaySaleRequest.setTransactionId(uniqueId);
        bitPaySaleRequest.setReturnUrl(new URL(RETURN_URL));
        bitPaySaleRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingReturnUrl() throws MalformedURLException {
        prepareObject();
        bitPaySaleRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPaySaleRequest.setCurrency(Currency.EUR.getCurrency());
        bitPaySaleRequest.setTransactionId(uniqueId);
        bitPaySaleRequest.setCustomerEmail(CUSTOMER_EMAIL);
        bitPaySaleRequest.toXML();
    }

}
