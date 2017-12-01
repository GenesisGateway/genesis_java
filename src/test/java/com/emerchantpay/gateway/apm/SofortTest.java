package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.SofortRequest;
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

public class SofortTest {

    private String uniqueId;

    private GenesisClient client;
    private SofortRequest sofort;

    @Before
    public void createSofort() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sofort = mock(SofortRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(sofort.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(sofort);
        assertEquals(client.execute(), sofort);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSofort() throws MalformedURLException {

        // Sofort
        when(sofort.setTransactionId(isA(String.class))).thenReturn(sofort);
        when(sofort.setRemoteIp(isA(String.class))).thenReturn(sofort);
        when(sofort.setUsage(isA(String.class))).thenReturn(sofort);
        when(sofort.setCurrency(isA(String.class))).thenReturn(sofort);
        when(sofort.setAmount(isA(BigDecimal.class))).thenReturn(sofort);
        when(sofort.setCustomerEmail(isA(String.class))).thenReturn(sofort);
        when(sofort.setCustomerPhone(isA(String.class))).thenReturn(sofort);
        when(sofort.setReturnSuccessUrl(isA(URL.class))).thenReturn(sofort);
        when(sofort.setReturnFailureUrl(isA(URL.class))).thenReturn(sofort);
        when(sofort.setBillingPrimaryAddress(isA(String.class))).thenReturn(sofort);
        when(sofort.setBillingSecondaryAddress(isA(String.class))).thenReturn(sofort);
        when(sofort.setBillingZipCode(isA(String.class))).thenReturn(sofort);
        when(sofort.setBillingFirstname(isA(String.class))).thenReturn(sofort);
        when(sofort.setBillingLastname(isA(String.class))).thenReturn(sofort);
        when(sofort.setBillingCity(isA(String.class))).thenReturn(sofort);
        when(sofort.setBillingCountry(isA(String.class))).thenReturn(sofort);
        when(sofort.setBillingState(isA(String.class))).thenReturn(sofort);

        assertEquals(sofort.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), sofort);
        assertEquals(sofort.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")), sofort);
        assertEquals(sofort.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), sofort);
        assertEquals(sofort.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), sofort);
        assertEquals(sofort.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE"), sofort);

        verify(sofort).setTransactionId(uniqueId);
        verify(sofort).setRemoteIp("82.137.112.202");
        verify(sofort).setUsage("TICKETS");
        verify(sofort).setCurrency(Currency.EUR.getCurrency());
        verify(sofort).setAmount(new BigDecimal("2.00"));
        verify(sofort).setCustomerEmail("john@example.com");
        verify(sofort).setCustomerPhone("+55555555");
        verify(sofort).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(sofort).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(sofort).setBillingPrimaryAddress("Berlin");
        verify(sofort).setBillingSecondaryAddress("Berlin");
        verify(sofort).setBillingFirstname("Plamen");
        verify(sofort).setBillingLastname("Petrov");
        verify(sofort).setBillingCity("Berlin");
        verify(sofort).setBillingCountry("DE");
        verify(sofort).setBillingZipCode("M4B1B3");
        verify(sofort).setBillingState("BE");
        verifyNoMoreInteractions(sofort);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testSofortWithMissingParams()  {
        clearRequiredParams();

        assertNull(sofort.setBillingCountry(null));
        verify(sofort).setBillingCountry(null);
        verifyNoMoreInteractions(sofort);

        verifyExecute();
    }
}