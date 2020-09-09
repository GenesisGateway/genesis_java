package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.UPIRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class UPITest {
    private GenesisClient client;
    private UPIRequest upiRequest;

    private String uid;

    @Before
    public void createUPI() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        upiRequest = mock(UPIRequest.class);
    }

    public UPIRequest stubUPI() throws MalformedURLException {
        UPIRequest upiRequest = new UPIRequest();

        upiRequest.setTransactionId(uid);
        upiRequest.setRemoteIp("82.137.112.202");
        upiRequest.setUsage("TICKETS");
        upiRequest.setCurrency(Currency.INR.getCurrency());
        upiRequest.setAmount(new BigDecimal("20.00"));
        upiRequest.setCustomerEmail("john.doe@emerchantpay.com");
        upiRequest.setCustomerPhone("+55555555");
        upiRequest.setDocumentId("12345");
        upiRequest.setVirtualPaymentAddress("john.doe@bank");
        upiRequest.setReturnSuccessUrl(new URL("https://example.com/return_success"));
        upiRequest.setReturnFailureUrl(new URL("https://example.com/return_failure"));

        upiRequest.setBillingPrimaryAddress("First Avenue");
        upiRequest.setBillingSecondaryAddress("Second Avenue");
        upiRequest.setBillingFirstname("John");
        upiRequest.setBillingLastname("Doe");
        upiRequest.setBillingCity("Berlin");
        upiRequest.setBillingCountry(Country.Germany.getCode());
        upiRequest.setBillingZipCode("M4B1B3");
        upiRequest.setBillingState("BE");

        upiRequest.setShippingPrimaryAddress("First Avenue");
        upiRequest.setShippingSecondaryAddress("Second Avenue");
        upiRequest.setShippingFirstname("John");
        upiRequest.setShippingLastname("Doe");
        upiRequest.setShippingCity("Berlin");
        upiRequest.setShippingCountry(Country.Germany.getCode());
        upiRequest.setShippingZipCode("M4B1B3");
        upiRequest.setShippingState("BE");

        return upiRequest;
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(upiRequest);
        assertEquals(client.execute(), upiRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testUPI() throws MalformedURLException {
        when(upiRequest.setTransactionId(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setRemoteIp(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setUsage(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setAmount(isA(BigDecimal.class))).thenReturn(upiRequest);
        when(upiRequest.setCurrency(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setCustomerEmail(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setCustomerPhone(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setDocumentId(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setVirtualPaymentAddress(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setReturnSuccessUrl(isA(URL.class))).thenReturn(upiRequest);
        when(upiRequest.setReturnFailureUrl(isA(URL.class))).thenReturn(upiRequest);

        when(upiRequest.setBillingPrimaryAddress(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setBillingSecondaryAddress(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setBillingFirstname(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setBillingLastname(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setBillingCity(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setBillingCountry(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setBillingZipCode(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setBillingState(isA(String.class))).thenReturn(upiRequest);

        when(upiRequest.setShippingPrimaryAddress(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setShippingSecondaryAddress(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setShippingFirstname(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setShippingLastname(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setShippingCity(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setShippingCountry(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setShippingZipCode(isA(String.class))).thenReturn(upiRequest);
        when(upiRequest.setShippingState(isA(String.class))).thenReturn(upiRequest);


        assertEquals(upiRequest, upiRequest.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS"));
        assertEquals(upiRequest, upiRequest.setCurrency(Currency.INR.getCurrency()).setAmount(new BigDecimal("20.00")));
        assertEquals(upiRequest, upiRequest.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"));
        assertEquals(upiRequest, upiRequest.setDocumentId("12345"));
        assertEquals(upiRequest, upiRequest.setVirtualPaymentAddress("john.doe@bank"));

        assertEquals(upiRequest, upiRequest.setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure")));

        assertEquals(upiRequest, upiRequest.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE"));

        assertEquals(upiRequest, upiRequest.setShippingPrimaryAddress("First Avenue").setShippingSecondaryAddress("Second Avenue")
                .setShippingFirstname("John").setShippingLastname("Doe")
                .setShippingCity("Berlin").setShippingCountry(Country.Germany.getCode())
                .setShippingZipCode("M4B1B3").setShippingState("BE"));


        verify(upiRequest).setTransactionId(uid);
        verify(upiRequest).setRemoteIp("82.137.112.202");
        verify(upiRequest).setUsage("TICKETS");
        verify(upiRequest).setCurrency(Currency.INR.getCurrency());
        verify(upiRequest).setAmount(new BigDecimal("20.00"));
        verify(upiRequest).setCustomerEmail("john.doe@emerchantpay.com");
        verify(upiRequest).setCustomerPhone("+55555555");
        verify(upiRequest).setDocumentId("12345");
        verify(upiRequest).setVirtualPaymentAddress("john.doe@bank");

        verify(upiRequest).setReturnSuccessUrl(new URL("https://example.com/return_success"));
        verify(upiRequest).setReturnFailureUrl(new URL("https://example.com/return_failure"));

        verify(upiRequest).setBillingPrimaryAddress("First Avenue");
        verify(upiRequest).setBillingSecondaryAddress("Second Avenue");
        verify(upiRequest).setBillingFirstname("John");
        verify(upiRequest).setBillingLastname("Doe");
        verify(upiRequest).setBillingCity("Berlin");
        verify(upiRequest).setBillingCountry(Country.Germany.getCode());
        verify(upiRequest).setBillingZipCode("M4B1B3");
        verify(upiRequest).setBillingState("BE");

        verify(upiRequest).setShippingPrimaryAddress("First Avenue");
        verify(upiRequest).setShippingSecondaryAddress("Second Avenue");
        verify(upiRequest).setShippingFirstname("John");
        verify(upiRequest).setShippingLastname("Doe");
        verify(upiRequest).setShippingCity("Berlin");
        verify(upiRequest).setShippingCountry(Country.Germany.getCode());
        verify(upiRequest).setShippingZipCode("M4B1B3");
        verify(upiRequest).setShippingState("BE");

        verifyNoMoreInteractions(upiRequest);

        verifyExecute();
    }

    @Test (expected = RequiredParamsException.class)
    public void testUPIWithMissingParams() throws MalformedURLException {
        upiRequest = stubUPI();
        upiRequest.setRemoteIp(null);
        upiRequest.toXML();
    }

    @Test (expected = RequiredParamsException.class)
    public void testUPIWithWrongCurrency() throws MalformedURLException {
        upiRequest = stubUPI();
        upiRequest.setCurrency(Currency.CAD.getCurrency());
        upiRequest.toXML();
    }

    @Test (expected = RegexException.class)
    public void testUPIWithWrongVPAFormat() throws MalformedURLException {
        upiRequest = stubUPI();
        upiRequest.setVirtualPaymentAddress("wrong.format");
        upiRequest.toXML();
    }
}
