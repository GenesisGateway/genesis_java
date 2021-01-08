package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.BancontactRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BancontactRequestTest {

    private String uniqueId;

    private GenesisClient client;
    private BancontactRequest bancontactRequest;

    @Before
    public void createBancontact() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        bancontactRequest = new BancontactRequest();
    }

    public void prepareObject() throws MalformedURLException {
        bancontactRequest.setAmount(BigDecimal.valueOf(1.00));
        bancontactRequest.setCurrency(Currency.EUR.getCurrency());
        bancontactRequest.setTransactionId(UUID.randomUUID().toString());
        bancontactRequest.setCustomerEmail("test@abv.bg");
        bancontactRequest.setCustomerPhone("124411");
        bancontactRequest.setRemoteIp("82.137.112.202");
        bancontactRequest.setUsage("TICKETS");
        bancontactRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        bancontactRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        bancontactRequest.setBillingPrimaryAddress("First Avenue");
        bancontactRequest.setBillingSecondaryAddress("Second Avenue");
        bancontactRequest.setBillingFirstname("John");
        bancontactRequest.setBillingLastname("Doe");
        bancontactRequest.setBillingCity("Rosario");
        bancontactRequest.setBillingCountry(Country.Belgium.getCountry());
        bancontactRequest.setBillingZipCode("M4B1B3");
        bancontactRequest.setBillingState("BE");
    }

    public void verifyExecute() throws MalformedURLException {
        prepareObject();
        bancontactRequest.toXML();

        when(client.execute()).thenReturn(bancontactRequest);

        assertEquals(BigDecimal.valueOf(1.0), bancontactRequest.getAmount());
        assertEquals(Currency.EUR.getCurrency(), bancontactRequest.getCurrency());
        assertEquals("bcmc", bancontactRequest.getTransactionType());
        assertEquals("bcmc", bancontactRequest.getPaymentType());
        assertEquals(bancontactRequest, bancontactRequest.setAmount(BigDecimal.valueOf(1.0)).setCurrency(Currency.EUR.getCurrency()));
        assertEquals(bancontactRequest, bancontactRequest.setPaymentType("bcmc").setTransactionId(UUID.randomUUID().toString()));

        assertEquals(bancontactRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest() throws MalformedURLException {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testBancontactWithMissingParams() {
        bancontactRequest.setAmount(new BigDecimal("20.00"));
        bancontactRequest.setBillingCountry(Country.Belgium.getCode());
        bancontactRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testBancontactWithWrongCurrency() throws MalformedURLException {
        bancontactRequest.setTransactionId(uniqueId);
        bancontactRequest.setAmount(new BigDecimal("20.00"));
        bancontactRequest.setCurrency(Currency.GBP.getCurrency());
        bancontactRequest.setRemoteIp("82.137.112.202");
        bancontactRequest.setBillingCountry(Country.Belgium.getCode());
        bancontactRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        bancontactRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        bancontactRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testBancontactWithWrongCountry() throws MalformedURLException {
        bancontactRequest.setTransactionId(uniqueId);
        bancontactRequest.setAmount(new BigDecimal("20.00"));
        bancontactRequest.setCurrency(Currency.EUR.getCurrency());
        bancontactRequest.setRemoteIp("82.137.112.202");
        bancontactRequest.setBillingCountry(Country.Finland.getCode());
        bancontactRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        bancontactRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        bancontactRequest.toXML();
    }

}
