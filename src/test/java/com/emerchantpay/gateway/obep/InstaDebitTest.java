package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.InstaDebitPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.InstaDebitPayOutRequest;
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

public class InstaDebitTest {

    GenesisClient client;

    private InstaDebitPayInRequest instadebitPayIn;
    private InstaDebitPayOutRequest instadebitPayOut;

    private String uidPayIn;
    private String uidPayOut;

    @Before
    public void createIDebitPayIn() {
        uidPayIn = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        instadebitPayIn = mock(InstaDebitPayInRequest.class);
    }

    @Before
    public void createInstaDebitPayOut() {
        uidPayOut = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        instadebitPayOut = mock(InstaDebitPayOutRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(instadebitPayIn.setBillingCountry(null)).thenThrow(exception);
        when(instadebitPayOut.setTransactionId(null)).thenThrow(exception);
    }

    public void verifyPayinExecute() {
        when(client.execute()).thenReturn(instadebitPayIn);
        assertEquals(client.execute(), instadebitPayIn);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyPayoutExecute() {
        when(client.execute()).thenReturn(instadebitPayIn);
        assertEquals(client.execute(), instadebitPayIn);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testInstaDebitPayIn() throws MalformedURLException {
        // PayIn
        when(instadebitPayIn.setTransactionId(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setRemoteIp(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setUsage(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setAmount(isA(BigDecimal.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setCurrency(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setCustomerEmail(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setCustomerPhone(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setCustomerAccountId(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setNotificationUrl(isA(URL.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setReturnUrl(isA(URL.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingPrimaryAddress(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingSecondaryAddress(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingFirstname(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingLastname(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingCity(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingCountry(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingZipCode(isA(String.class))).thenReturn(instadebitPayIn);
        when(instadebitPayIn.setBillingState(isA(String.class))).thenReturn(instadebitPayIn);

        assertEquals(instadebitPayIn.setTransactionId(uidPayIn).setRemoteIp("192.168.0.1").setUsage("TICKETS"),
                instadebitPayIn);
        assertEquals(instadebitPayIn.setCurrency(Currency.CAD.getCurrency())
                .setAmount(new BigDecimal("100.00")), instadebitPayIn);
        assertEquals(instadebitPayIn.setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555"), instadebitPayIn);
        assertEquals(instadebitPayIn.setCustomerAccountId("1534537")
                .setReturnUrl(new URL("https://example.com/return_success"))
                .setNotificationUrl(new URL("https://example.com/return_notification")), instadebitPayIn);
        assertEquals(instadebitPayIn.setBillingPrimaryAddress("Toronto").setBillingSecondaryAddress("Toronto")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Toronto").setBillingCountry(Country.Canada.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("ON"), instadebitPayIn);

        verify(instadebitPayIn).setTransactionId(uidPayIn);
        verify(instadebitPayIn).setRemoteIp("192.168.0.1");
        verify(instadebitPayIn).setUsage("TICKETS");
        verify(instadebitPayIn).setCurrency(Currency.CAD.getCurrency());
        verify(instadebitPayIn).setAmount(new BigDecimal("100.00"));
        verify(instadebitPayIn).setCustomerEmail("john@example.com");
        verify(instadebitPayIn).setCustomerPhone("+55555555");
        verify(instadebitPayIn).setCustomerAccountId("1534537");
        verify(instadebitPayIn).setReturnUrl(new URL("https://example.com/return_success"));
        verify(instadebitPayIn).setNotificationUrl(new URL("https://example.com/return_notification"));
        verify(instadebitPayIn).setBillingPrimaryAddress("Toronto");
        verify(instadebitPayIn).setBillingSecondaryAddress("Toronto");
        verify(instadebitPayIn).setBillingFirstname("Plamen");
        verify(instadebitPayIn).setBillingLastname("Petrov");
        verify(instadebitPayIn).setBillingCity("Toronto");
        verify(instadebitPayIn).setBillingCountry(Country.Canada.getCode());
        verify(instadebitPayIn).setBillingZipCode("M4B1B3");
        verify(instadebitPayIn).setBillingState("ON");
        verifyNoMoreInteractions(instadebitPayIn);

        verifyPayinExecute();
    }

    @Test(expected = ApiException.class)
    public void testIDebitPayInWithMissingParams() {
        clearRequiredParams();
        assertNull(instadebitPayIn.setBillingCountry(null));
        verify(instadebitPayIn).setBillingCountry(null);
        verifyNoMoreInteractions(instadebitPayIn);

        verifyPayinExecute();
    }

    @Test
    public void testInstaDebitPayOut() {
        // PayOut
        when(instadebitPayOut.setTransactionId(isA(String.class))).thenReturn(instadebitPayOut);
        when(instadebitPayOut.setReferenceId(isA(String.class))).thenReturn(instadebitPayOut);
        when(instadebitPayOut.setCurrency(isA(String.class))).thenReturn(instadebitPayOut);
        when(instadebitPayOut.setAmount(isA(BigDecimal.class))).thenReturn(instadebitPayOut);

        assertEquals(instadebitPayOut.setTransactionId(uidPayOut).setReferenceId("2bf62f87d232590a115530b0a0154505")
                .setCurrency(Currency.CAD.getCurrency()).setAmount(new BigDecimal("1.00")), instadebitPayOut);

        verify(instadebitPayOut).setTransactionId(uidPayOut);
        verify(instadebitPayOut).setReferenceId("2bf62f87d232590a115530b0a0154505");
        verify(instadebitPayOut).setCurrency(Currency.CAD.getCurrency());
        verify(instadebitPayOut).setAmount(new BigDecimal("1.00"));
        verifyNoMoreInteractions(instadebitPayOut);

        verifyPayoutExecute();
    }

    @Test(expected = ApiException.class)
    public void testIDebitPayOutWithMissingParams() {
        clearRequiredParams();
        assertNull(instadebitPayOut.setTransactionId(null));
        verify(instadebitPayOut).setTransactionId(null);
        verifyNoMoreInteractions(instadebitPayOut);

        verifyPayinExecute();
    }
}