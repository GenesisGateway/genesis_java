package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.MyBankRequest;
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

public class MyBankRequestTest {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1.0);
    private String uniqueId;
    private GenesisClient client;
    private MyBankRequest myBankRequest;

    @Before
    public void createObjects() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        myBankRequest = prepareRequest();
    }

    private MyBankRequest prepareRequest() throws MalformedURLException {
        MyBankRequest request = new MyBankRequest();
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
        request.setBillingCountry(Country.Spain.getCountry());
        request.setBillingZipCode("M4B1B3");
        request.setBillingState("BE");

        return request;
    }

    private void verifyExecute() throws MalformedURLException {
        myBankRequest.toXML();

        when(client.execute()).thenReturn(myBankRequest);

        assertEquals(AMOUNT, myBankRequest.getAmount());
        assertEquals(Currency.EUR.getCurrency(), myBankRequest.getCurrency());
        assertEquals(TransactionTypes.MY_BANK, myBankRequest.getTransactionType());
        assertEquals(uniqueId, myBankRequest.getTransactionId());
        assertEquals(myBankRequest, myBankRequest.setAmount(AMOUNT).setCurrency(Currency.EUR.getCurrency()));
        assertEquals(myBankRequest, myBankRequest.setTransactionId(uniqueId));

        assertEquals(myBankRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidArguments() throws MalformedURLException {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() throws MalformedURLException {
        myBankRequest.setAmount(null);
        myBankRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCurrency() throws MalformedURLException {
        myBankRequest.setCurrency(null);
        myBankRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCurrencyIsNotEUR() throws MalformedURLException {
        myBankRequest.setCurrency(Currency.GBP.getCurrency());
        myBankRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() throws MalformedURLException {
        myBankRequest.setTransactionId(null);
        myBankRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenCountryItaly() throws MalformedURLException {
        myBankRequest.setBillingCountry(Country.Italy.getCode());
        myBankRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenCountrySpain() throws MalformedURLException {
        myBankRequest.setBillingCountry(Country.Spain.getCode());
        myBankRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCountryIsNotItalyNorSpain() throws MalformedURLException {
        myBankRequest.setBillingCountry(Country.Finland.getCode());
        myBankRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCountry() throws MalformedURLException {
        myBankRequest.setBillingCountry(null);
        myBankRequest.toXML();
    }

}