package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.WechatRequest;
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


public class WechatTest {

    private GenesisClient client;
    private WechatRequest wechat;

    private String uid;

    @Before
    public void createWechat() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        wechat = mock(WechatRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(wechat.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(wechat);
        assertEquals(client.execute(), wechat);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void setMissingParams() {
        wechat.setBillingCountry(null);
    }

    @Test
    public void testWechat() throws MalformedURLException {

        // Wechat
        when(wechat.setTransactionId(isA(String.class))).thenReturn(wechat);
        when(wechat.setRemoteIp(isA(String.class))).thenReturn(wechat);
        when(wechat.setUsage(isA(String.class))).thenReturn(wechat);
        when(wechat.setAmount(isA(BigDecimal.class))).thenReturn(wechat);
        when(wechat.setCurrency(isA(String.class))).thenReturn(wechat);
        when(wechat.setCustomerEmail(isA(String.class))).thenReturn(wechat);
        when(wechat.setCustomerPhone(isA(String.class))).thenReturn(wechat);
        when(wechat.setProductCode(isA(String.class))).thenReturn(wechat);
        when(wechat.setProductDescription(isA(String.class))).thenReturn(wechat);
        when(wechat.setProductNumber(isA(Integer.class))).thenReturn(wechat);
        when(wechat.setReturnSuccessUrl(isA(URL.class))).thenReturn(wechat);
        when(wechat.setReturnFailureUrl(isA(URL.class))).thenReturn(wechat);
        when(wechat.setReturnUrl(isA(URL.class))).thenReturn(wechat);
        when(wechat.setNotificationUrl(isA(URL.class))).thenReturn(wechat);
        when(wechat.setBillingPrimaryAddress(isA(String.class))).thenReturn(wechat);
        when(wechat.setBillingSecondaryAddress(isA(String.class))).thenReturn(wechat);
        when(wechat.setBillingFirstname(isA(String.class))).thenReturn(wechat);
        when(wechat.setBillingLastname(isA(String.class))).thenReturn(wechat);
        when(wechat.setBillingCity(isA(String.class))).thenReturn(wechat);
        when(wechat.setBillingCountry(isA(String.class))).thenReturn(wechat);
        when(wechat.setBillingZipCode(isA(String.class))).thenReturn(wechat);
        when(wechat.setBillingState(isA(String.class))).thenReturn(wechat);

        assertEquals(wechat.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS"), wechat);
        assertEquals(wechat.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("20.00")), wechat);
        assertEquals(wechat.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"), wechat);
        assertEquals(wechat.setProductCode("Test product code").setProductNumber(1234)
                .setProductDescription("Test product description")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure")), wechat);
        assertEquals(wechat.setReturnUrl(new URL("https://example.com/return"))
                .setNotificationUrl(new URL("https://example.com/notification")), wechat);
        assertEquals(wechat.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE"), wechat);

        verify(wechat).setTransactionId(uid);
        verify(wechat).setRemoteIp("82.137.112.202");
        verify(wechat).setUsage("TICKETS");
        verify(wechat).setCurrency(Currency.EUR.getCurrency());
        verify(wechat).setAmount(new BigDecimal("20.00"));
        verify(wechat).setCustomerEmail("john.doe@emerchantpay.com");
        verify(wechat).setCustomerPhone("+55555555");
        verify(wechat).setProductCode("Test product code");
        verify(wechat).setProductNumber(1234);
        verify(wechat).setProductDescription("Test product description");
        verify(wechat).setReturnSuccessUrl(new URL("https://example.com/return_success"));
        verify(wechat).setReturnFailureUrl(new URL("https://example.com/return_failure"));
        verify(wechat).setReturnUrl(new URL("https://example.com/return"));
        verify(wechat).setNotificationUrl(new URL("https://example.com/notification"));
        verify(wechat).setBillingPrimaryAddress("First Avenue");
        verify(wechat).setBillingSecondaryAddress("Second Avenue");
        verify(wechat).setBillingFirstname("John");
        verify(wechat).setBillingLastname("Doe");
        verify(wechat).setBillingCity("Berlin");
        verify(wechat).setBillingCountry(Country.Germany.getCode());
        verify(wechat).setBillingZipCode("M4B1B3");
        verify(wechat).setBillingState("BE");
        verifyNoMoreInteractions(wechat);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testWechatWithMissingParams() {

        clearRequiredParams();
        assertNull(wechat.setBillingCountry(null));
        verify(wechat).setBillingCountry(null);
        verifyNoMoreInteractions(wechat);

        verifyExecute();
    }
}