package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.RPNRequest;
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

public class RPNTest {

    GenesisClient client;
    private RPNRequest rpnRequest;

    private String uid;

    @Before
    public void createRPNPay() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        rpnRequest = mock(RPNRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(rpnRequest.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(rpnRequest);
        assertEquals(client.execute(), rpnRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRPNPay() throws MalformedURLException {

        // RPN
        when(rpnRequest.setTransactionId(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setRemoteIp(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setUsage(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setAmount(isA(BigDecimal.class))).thenReturn(rpnRequest);
        when(rpnRequest.setCurrency(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setCustomerEmail(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setCustomerPhone(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setNotificationUrl(isA(URL.class))).thenReturn(rpnRequest);
        when(rpnRequest.setReturnSuccessUrl(isA(URL.class))).thenReturn(rpnRequest);
        when(rpnRequest.setReturnFailureUrl(isA(URL.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingPrimaryAddress(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingSecondaryAddress(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingFirstname(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingLastname(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingCity(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingCountry(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingZipCode(isA(String.class))).thenReturn(rpnRequest);
        when(rpnRequest.setBillingState(isA(String.class))).thenReturn(rpnRequest);

        assertEquals(rpnRequest.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS"), rpnRequest);
        assertEquals(rpnRequest.setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("1000.00")),
                rpnRequest);
        assertEquals(rpnRequest.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"),
                rpnRequest);
        assertEquals(rpnRequest.setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure")), rpnRequest);
        assertEquals(rpnRequest.setNotificationUrl(new URL("https://example.com/notification")), rpnRequest);
        assertEquals(rpnRequest.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe").setBillingCity("Beijing")
                .setBillingCountry(Country.China.getCode()).setBillingZipCode("M4B1B3")
                .setBillingState("BJ"), rpnRequest);

        verify(rpnRequest).setTransactionId(uid);
        verify(rpnRequest).setRemoteIp("82.137.112.202");
        verify(rpnRequest).setUsage("TICKETS");
        verify(rpnRequest).setCurrency(Currency.CNY.getCurrency());
        verify(rpnRequest).setAmount(new BigDecimal("1000.00"));
        verify(rpnRequest).setCustomerEmail("john.doe@emerchantpay.com");
        verify(rpnRequest).setCustomerPhone("+55555555");
        verify(rpnRequest).setReturnSuccessUrl(new URL("https://example.com/return_success"));
        verify(rpnRequest).setReturnFailureUrl(new URL("https://example.com/return_failure"));
        verify(rpnRequest).setNotificationUrl(new URL("https://example.com/notification"));
        verify(rpnRequest).setBillingPrimaryAddress("First Avenue");
        verify(rpnRequest).setBillingSecondaryAddress("Second Avenue");
        verify(rpnRequest).setBillingFirstname("John");
        verify(rpnRequest).setBillingLastname("Doe");
        verify(rpnRequest).setBillingCity("Beijing");
        verify(rpnRequest).setBillingCountry(Country.China.getCode());
        verify(rpnRequest).setBillingZipCode("M4B1B3");
        verify(rpnRequest).setBillingState("BJ");
        verifyNoMoreInteractions(rpnRequest);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testRPNWithMissingParams() {

        clearRequiredParams();
        assertNull(rpnRequest.setBillingCountry(null));
        verify(rpnRequest).setBillingCountry(null);
        verifyNoMoreInteractions(rpnRequest);

        verifyExecute();
    }
}