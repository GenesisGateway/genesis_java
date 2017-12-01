package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.POLiRequest;
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

public class POLiTest {

    private GenesisClient client;
    private POLiRequest poli;

    private String uniqueId;

    @Before
    public void createPoli() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        poli = mock(POLiRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(poli.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(poli);
        assertEquals(client.execute(), poli);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testPoli() throws MalformedURLException {

        // POLi
        when(poli.setTransactionId(isA(String.class))).thenReturn(poli);
        when(poli.setRemoteIp(isA(String.class))).thenReturn(poli);
        when(poli.setUsage(isA(String.class))).thenReturn(poli);
        when(poli.setCurrency(isA(String.class))).thenReturn(poli);
        when(poli.setAmount(isA(BigDecimal.class))).thenReturn(poli);
        when(poli.setCustomerEmail(isA(String.class))).thenReturn(poli);
        when(poli.setCustomerPhone(isA(String.class))).thenReturn(poli);
        when(poli.setReturnSuccessUrl(isA(URL.class))).thenReturn(poli);
        when(poli.setReturnFailureUrl(isA(URL.class))).thenReturn(poli);
        when(poli.setBillingPrimaryAddress(isA(String.class))).thenReturn(poli);
        when(poli.setBillingSecondaryAddress(isA(String.class))).thenReturn(poli);
        when(poli.setBillingZipCode(isA(String.class))).thenReturn(poli);
        when(poli.setBillingFirstname(isA(String.class))).thenReturn(poli);
        when(poli.setBillingLastname(isA(String.class))).thenReturn(poli);
        when(poli.setBillingCity(isA(String.class))).thenReturn(poli);
        when(poli.setBillingCountry(isA(String.class))).thenReturn(poli);
        when(poli.setBillingState(isA(String.class))).thenReturn(poli);

        assertEquals(poli.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), poli);
        assertEquals(poli.setCurrency(Currency.AUD.getCurrency()).setAmount(new BigDecimal("2.00")), poli);
        assertEquals(poli.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), poli);
        assertEquals(poli.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), poli);
        assertEquals(poli.setBillingPrimaryAddress("Sydney 1").setBillingSecondaryAddress("Sydney 2")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Sydney").setBillingCountry(Country.Australia.getCode())
                .setBillingZipCode("S4C1C3").setBillingState("SY"), poli);

        verify(poli).setTransactionId(uniqueId);
        verify(poli).setRemoteIp("82.137.112.202");
        verify(poli).setUsage("TICKETS");
        verify(poli).setCurrency(Currency.AUD.getCurrency());
        verify(poli).setAmount(new BigDecimal("2.00"));
        verify(poli).setCustomerEmail("john@example.com");
        verify(poli).setCustomerPhone("+55555555");
        verify(poli).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(poli).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(poli).setBillingPrimaryAddress("Sydney 1");
        verify(poli).setBillingSecondaryAddress("Sydney 2");
        verify(poli).setBillingFirstname("Plamen");
        verify(poli).setBillingLastname("Petrov");
        verify(poli).setBillingCity("Sydney");
        verify(poli).setBillingCountry(Country.Australia.getCode());
        verify(poli).setBillingZipCode("S4C1C3");
        verify(poli).setBillingState("SY");
        verifyNoMoreInteractions(poli);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testPoliWithMissingParams() {

        clearRequiredParams();

        assertNull(poli.setBillingCountry(null));
        verify(poli).setBillingCountry(null);
        verifyNoMoreInteractions(poli);

        verifyExecute();
    }
}