package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.CashURequest;
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


public class CashUTest {

    private String uniqueId;

    private GenesisClient client;
    private CashURequest  cashU;

    @Before
    public void createCashU() {

        uniqueId = new StringUtils().generateUID();

        client =  mock(GenesisClient.class);
        cashU = mock(CashURequest.class);
    }

    public void clearRequiredParams() {

        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(cashU.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(cashU);
        assertEquals(client.execute(), cashU);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testCashU() throws MalformedURLException {

        // CashU
        when(cashU.setTransactionId(isA(String.class))).thenReturn(cashU);
        when(cashU.setRemoteIp(isA(String.class))).thenReturn(cashU);
        when(cashU.setUsage(isA(String.class))).thenReturn(cashU);
        when(cashU.setCurrency(isA(String.class))).thenReturn(cashU);
        when(cashU.setAmount(isA(BigDecimal.class))).thenReturn(cashU);
        when(cashU.setCustomerEmail(isA(String.class))).thenReturn(cashU);
        when(cashU.setCustomerPhone(isA(String.class))).thenReturn(cashU);
        when(cashU.setReturnSuccessUrl(isA(URL.class))).thenReturn(cashU);
        when(cashU.setReturnFailureUrl(isA(URL.class))).thenReturn(cashU);
        when(cashU.setBillingPrimaryAddress(isA(String.class))).thenReturn(cashU);
        when(cashU.setBillingSecondaryAddress(isA(String.class))).thenReturn(cashU);
        when(cashU.setBillingFirstname(isA(String.class))).thenReturn(cashU);
        when(cashU.setBillingLastname(isA(String.class))).thenReturn(cashU);
        when(cashU.setBillingCity(isA(String.class))).thenReturn(cashU);
        when(cashU.setBillingCountry(isA(String.class))).thenReturn(cashU);
        when(cashU.setBillingZipCode(isA(String.class))).thenReturn(cashU);
        when(cashU.setBillingState(isA(String.class))).thenReturn(cashU);

        assertEquals(cashU.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), cashU);
        assertEquals(cashU.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("15.00")), cashU);
        assertEquals(cashU.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), cashU);
        assertEquals(cashU.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), cashU);
        assertEquals(cashU.setBillingPrimaryAddress("Fifth avenue 1").setBillingSecondaryAddress("Fifth avenue 2")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov").setBillingCity("New York")
                .setBillingCountry("US").setBillingZipCode("M4B1B3").setBillingState("WA"), cashU);

        verify(cashU).setTransactionId(uniqueId);
        verify(cashU).setRemoteIp("82.137.112.202");
        verify(cashU).setUsage("TICKETS");
        verify(cashU).setCurrency(Currency.EUR.getCurrency());
        verify(cashU).setAmount(new BigDecimal("15.00"));
        verify(cashU).setCustomerEmail("john@example.com");
        verify(cashU).setCustomerPhone("+55555555");
        verify(cashU).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(cashU).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(cashU).setBillingPrimaryAddress("Fifth avenue 1");
        verify(cashU).setBillingSecondaryAddress("Fifth avenue 2");
        verify(cashU).setBillingFirstname("Plamen");
        verify(cashU).setBillingLastname("Petrov");
        verify(cashU).setBillingCity("New York");
        verify(cashU).setBillingCountry("US");
        verify(cashU).setBillingZipCode("M4B1B3");
        verify(cashU).setBillingState("WA");
        verifyNoMoreInteractions(cashU);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testCashUWithMissingParams() {

        clearRequiredParams();

        assertNull(cashU.setBillingCountry(null));
        verify(cashU).setBillingCountry(null);
        verifyNoMoreInteractions(cashU);

        verifyExecute();
    }
}