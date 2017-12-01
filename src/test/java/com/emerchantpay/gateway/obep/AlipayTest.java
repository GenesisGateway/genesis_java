package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.AlipayRequest;
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

public class AlipayTest {

    private GenesisClient client;
    private AlipayRequest alipay;

    private String uid;

    @Before
    public void createAliPay() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        alipay = mock(AlipayRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(alipay.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(alipay);
        assertEquals(client.execute(), alipay);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testAliPay() throws MalformedURLException {

        // AliPay
        when(alipay.setTransactionId(isA(String.class))).thenReturn(alipay);
        when(alipay.setRemoteIp(isA(String.class))).thenReturn(alipay);
        when(alipay.setUsage(isA(String.class))).thenReturn(alipay);
        when(alipay.setAmount(isA(BigDecimal.class))).thenReturn(alipay);
        when(alipay.setCurrency(isA(String.class))).thenReturn(alipay);
        when(alipay.setCustomerEmail(isA(String.class))).thenReturn(alipay);
        when(alipay.setCustomerPhone(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingPrimaryAddress(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingSecondaryAddress(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingFirstname(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingLastname(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingCity(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingCountry(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingZipCode(isA(String.class))).thenReturn(alipay);
        when(alipay.setBillingState(isA(String.class))).thenReturn(alipay);
        when(alipay.setNotificationUrl(isA(URL.class))).thenReturn(alipay);
        when(alipay.setReturnSuccessUrl(isA(URL.class))).thenReturn(alipay);
        when(alipay.setReturnFailureUrl(isA(URL.class))).thenReturn(alipay);

        assertEquals(alipay.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS"), alipay);
        assertEquals(alipay.setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("1000.00")), alipay);
        assertEquals(alipay.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"), alipay);
        assertEquals(alipay.setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure")), alipay);
        assertEquals(alipay.setNotificationUrl(new URL("https://example.com/notification")), alipay);
        assertEquals(alipay.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe").setBillingCity("Beijing")
                .setBillingCountry(Country.China.getCode()).setBillingZipCode("M4B1B3")
                .setBillingState("BJ"), alipay);

        verify(alipay).setTransactionId(uid);
        verify(alipay).setRemoteIp("82.137.112.202");
        verify(alipay).setUsage("TICKETS");
        verify(alipay).setCurrency(Currency.CNY.getCurrency());
        verify(alipay).setAmount(new BigDecimal("1000.00"));
        verify(alipay).setCustomerEmail("john.doe@emerchantpay.com");
        verify(alipay).setCustomerPhone("+55555555");
        verify(alipay).setReturnSuccessUrl(new URL("https://example.com/return_success"));
        verify(alipay).setReturnFailureUrl(new URL("https://example.com/return_failure"));
        verify(alipay).setNotificationUrl(new URL("https://example.com/notification"));
        verify(alipay).setBillingPrimaryAddress("First Avenue");
        verify(alipay).setBillingSecondaryAddress("Second Avenue");
        verify(alipay).setBillingFirstname("John");
        verify(alipay).setBillingLastname("Doe");
        verify(alipay).setBillingCity("Beijing");
        verify(alipay).setBillingCountry(Country.China.getCode());
        verify(alipay).setBillingZipCode("M4B1B3");
        verify(alipay).setBillingState("BJ");
        verifyNoMoreInteractions(alipay);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testAliPayWithMissingParams() {
        clearRequiredParams();
        assertNull(alipay.setBillingCountry(null));
        verify(alipay).setBillingCountry(null);
        verifyNoMoreInteractions(alipay);

        verifyExecute();
    }
}