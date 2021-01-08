package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.MultibancoRequest;
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

public class MultibancoRequestTest {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1.0);
    private String uniqueId;
    private GenesisClient client;
    private MultibancoRequest multibancoRequest;

    @Before
    public void createObjects() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        multibancoRequest = prepareRequest();
    }

    private MultibancoRequest prepareRequest() throws MalformedURLException {
        MultibancoRequest request = new MultibancoRequest();
        request.setCurrency(Currency.EUR.getCurrency());
        request.setAmount(AMOUNT);
        request.setTransactionId(uniqueId);
        request.setRemoteIp("82.137.112.202");
        request.setCustomerEmail("test@abv.bg");
        request.setCustomerPhone("124411");
        request.setUsage("TICKETS");
        request.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        request.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        request.setBillingPrimaryAddress("First Avenue");
        request.setBillingSecondaryAddress("Second Avenue");
        request.setBillingFirstname("John");
        request.setBillingLastname("Doe");
        request.setBillingCity("Rosario");
        request.setBillingCountry(Country.Portugal.getCountry());
        request.setBillingZipCode("M4B1B3");
        request.setBillingState("BE");

        return request;
    }

    private void verifyExecute() throws MalformedURLException {
        multibancoRequest.toXML();

        when(client.execute()).thenReturn(multibancoRequest);

        assertEquals(AMOUNT, multibancoRequest.getAmount());
        assertEquals(Currency.EUR.getCurrency(), multibancoRequest.getCurrency());
        assertEquals(TransactionTypes.MULTIBANCO, multibancoRequest.getTransactionType());
        assertEquals(uniqueId, multibancoRequest.getTransactionId());
        assertEquals(multibancoRequest, multibancoRequest.setAmount(AMOUNT).setCurrency(Currency.EUR.getCurrency()));
        assertEquals(multibancoRequest, multibancoRequest.setTransactionId(uniqueId));

        assertEquals(multibancoRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidArguments() throws MalformedURLException {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() throws MalformedURLException {
        multibancoRequest.setAmount(null);
        multibancoRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCurrency() throws MalformedURLException {
        multibancoRequest.setCurrency(null);
        multibancoRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() throws MalformedURLException {
        multibancoRequest.setTransactionId(null);
        multibancoRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenCountryPortugal() throws MalformedURLException {
        multibancoRequest.setBillingCountry(Country.Portugal.getCode());
        multibancoRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCountryIsNotPortugal() throws MalformedURLException {
        multibancoRequest.setBillingCountry(Country.Finland.getCode());
        multibancoRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCountry() throws MalformedURLException {
        multibancoRequest.setBillingCountry(null);
        multibancoRequest.toXML();
    }

}