package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayOutRequest;
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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class IDebitTest {

    private GenesisClient client;
    private IDebitPayInRequest idebitPayIn;
    private IDebitPayOutRequest idebitPayOut;

    private String uidPayIn;
    private String uidPayOut;

    @Before
    public void createIDebitPayIn() {
        uidPayIn = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        idebitPayIn = mock(IDebitPayInRequest.class);
    }

    @Before
    public void createIDebitPayOut() {
        uidPayOut = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        idebitPayOut = mock(IDebitPayOutRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(idebitPayIn.setBillingCountry(null)).thenThrow(exception);
        when(idebitPayOut.setTransactionId(null)).thenThrow(exception);
    }

    public void verifyPayinExecute() {
        when(client.execute()).thenReturn(idebitPayIn);
        assertEquals(client.execute(), idebitPayIn);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyPayoutExecute() {
        when(client.execute()).thenReturn(idebitPayOut);
        assertEquals(client.execute(), idebitPayOut);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testIDebitPayIn() throws MalformedURLException {
        // PayIn
        when(idebitPayIn.setTransactionId(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setRemoteIp(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setUsage(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setAmount(isA(BigDecimal.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setCurrency(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setCustomerEmail(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setCustomerPhone(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setCustomerAccountId(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setNotificationUrl(isA(URL.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setReturnUrl(isA(URL.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingPrimaryAddress(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingSecondaryAddress(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingFirstname(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingLastname(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingCity(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingCountry(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingZipCode(isA(String.class))).thenReturn(idebitPayIn);
        when(idebitPayIn.setBillingState(isA(String.class))).thenReturn(idebitPayIn);

        assertEquals(idebitPayIn.setTransactionId(uidPayIn).setRemoteIp("192.168.0.1").setUsage("TICKETS"),
                idebitPayIn);
        assertEquals(idebitPayIn.setCurrency(Currency.CAD.getCurrency())
                .setAmount(new BigDecimal("100.00")), idebitPayIn);
        assertEquals(idebitPayIn.setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555"), idebitPayIn);
        assertEquals(idebitPayIn.setCustomerAccountId("1534537")
                .setReturnUrl(new URL("https://example.com/return_success"))
                .setNotificationUrl(new URL("https://example.com/return_notification")), idebitPayIn);
        assertEquals(idebitPayIn.setBillingPrimaryAddress("Toronto").setBillingSecondaryAddress("Toronto")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Toronto").setBillingCountry(Country.Canada.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("ON"), idebitPayIn);

        verify(idebitPayIn).setTransactionId(uidPayIn);
        verify(idebitPayIn).setRemoteIp("192.168.0.1");
        verify(idebitPayIn).setUsage("TICKETS");
        verify(idebitPayIn).setCurrency(Currency.CAD.getCurrency());
        verify(idebitPayIn).setAmount(new BigDecimal("100.00"));
        verify(idebitPayIn).setCustomerEmail("john@example.com");
        verify(idebitPayIn).setCustomerPhone("+55555555");
        verify(idebitPayIn).setCustomerAccountId("1534537");
        verify(idebitPayIn).setReturnUrl(new URL("https://example.com/return_success"));
        verify(idebitPayIn).setNotificationUrl(new URL("https://example.com/return_notification"));
        verify(idebitPayIn).setBillingPrimaryAddress("Toronto");
        verify(idebitPayIn).setBillingSecondaryAddress("Toronto");
        verify(idebitPayIn).setBillingFirstname("Plamen");
        verify(idebitPayIn).setBillingLastname("Petrov");
        verify(idebitPayIn).setBillingCity("Toronto");
        verify(idebitPayIn).setBillingCountry(Country.Canada.getCode());
        verify(idebitPayIn).setBillingZipCode("M4B1B3");
        verify(idebitPayIn).setBillingState("ON");
        verifyNoMoreInteractions(idebitPayIn);

        verifyPayinExecute();
    }

    @Test(expected = ApiException.class)
    public void testIDebitPayInWithMissingParams() {
        clearRequiredParams();
        assertNull(idebitPayIn.setBillingCountry(null));
        verify(idebitPayIn).setBillingCountry(null);
        verifyNoMoreInteractions(idebitPayIn);

        verifyPayinExecute();
    }

    @Test
    public void testIDebitPayOut() {

        // PayOut
        when(idebitPayOut.setTransactionId(isA(String.class))).thenReturn(idebitPayOut);
        when(idebitPayOut.setReferenceId(isA(String.class))).thenReturn(idebitPayOut);
        when(idebitPayOut.setCurrency(isA(String.class))).thenReturn(idebitPayOut);
        when(idebitPayOut.setAmount(isA(BigDecimal.class))).thenReturn(idebitPayOut);

        assertEquals(idebitPayOut.setTransactionId(uidPayOut).setReferenceId("2bf62f87d232590a115530b0a0154505")
                .setCurrency(Currency.CAD.getCurrency()).setAmount(new BigDecimal("1.00")), idebitPayOut);

        verify(idebitPayOut).setTransactionId(uidPayOut);
        verify(idebitPayOut).setReferenceId("2bf62f87d232590a115530b0a0154505");
        verify(idebitPayOut).setCurrency(Currency.CAD.getCurrency());
        verify(idebitPayOut).setAmount(new BigDecimal("1.00"));
        verifyNoMoreInteractions(idebitPayOut);

        verifyPayoutExecute();
    }

    @Test(expected = ApiException.class)
    public void testIDebitPayOutWithMissingParams() {
        clearRequiredParams();
        assertNull(idebitPayOut.setTransactionId(null));
        verify(idebitPayOut).setTransactionId(null);
        verifyNoMoreInteractions(idebitPayOut);

        verifyPayoutExecute();
    }
}