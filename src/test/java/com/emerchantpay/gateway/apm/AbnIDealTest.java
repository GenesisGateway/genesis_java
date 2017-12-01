package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.AbnIDealRequest;
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

public class AbnIDealTest {

    private String uniqueId;

    private GenesisClient client;
    private AbnIDealRequest abnIDeal;

    @Before
    public void createAbnIDeal()  {
        uniqueId = new StringUtils().generateUID();

        client =  mock(GenesisClient.class);
        abnIDeal = mock(AbnIDealRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(abnIDeal.setCustomerBankId(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(abnIDeal);
        assertEquals(client.execute(), abnIDeal);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testAbnIDeal() throws MalformedURLException {

        // AbnIDeal
        when(abnIDeal.setTransactionId(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setRemoteIp(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setUsage(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setCurrency(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setAmount(isA(BigDecimal.class))).thenReturn(abnIDeal);
        when(abnIDeal.setCustomerEmail(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setCustomerPhone(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setReturnSuccessUrl(isA(URL.class))).thenReturn(abnIDeal);
        when(abnIDeal.setReturnFailureUrl(isA(URL.class))).thenReturn(abnIDeal);
        when(abnIDeal.setCustomerBankId(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingPrimaryAddress(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingSecondaryAddress(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingFirstname(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingLastname(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingCity(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingCountry(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingZipCode(isA(String.class))).thenReturn(abnIDeal);
        when(abnIDeal.setBillingState(isA(String.class))).thenReturn(abnIDeal);


        assertEquals(abnIDeal.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), abnIDeal);
        assertEquals(abnIDeal.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("20.00")),
                abnIDeal);
        assertEquals(abnIDeal.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), abnIDeal);
        assertEquals(abnIDeal.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), abnIDeal);
        assertEquals(abnIDeal.setCustomerBankId("INGBNL2A"), abnIDeal);
        assertEquals(abnIDeal.setBillingPrimaryAddress("Amsterdam Street 1")
                .setBillingSecondaryAddress("Amsterdam Street 2").setBillingFirstname("Plamen")
                .setBillingLastname("Petrov").setBillingCity("Amsterdam")
                .setBillingCountry("NL").setBillingZipCode("NLB1B3")
                .setBillingState("NL"), abnIDeal);


        verify(abnIDeal).setTransactionId(uniqueId);
        verify(abnIDeal).setRemoteIp("82.137.112.202");
        verify(abnIDeal).setUsage("TICKETS");
        verify(abnIDeal).setCurrency(Currency.EUR.getCurrency());
        verify(abnIDeal).setAmount(new BigDecimal("20.00"));
        verify(abnIDeal).setCustomerEmail("john@example.com");
        verify(abnIDeal).setCustomerPhone("+55555555");
        verify(abnIDeal).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(abnIDeal).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(abnIDeal).setCustomerBankId("INGBNL2A");
        verify(abnIDeal).setBillingPrimaryAddress("Amsterdam Street 1");
        verify(abnIDeal).setBillingSecondaryAddress("Amsterdam Street 2");
        verify(abnIDeal).setBillingFirstname("Plamen");
        verify(abnIDeal).setBillingLastname("Petrov");
        verify(abnIDeal).setBillingCity("Amsterdam");
        verify(abnIDeal).setBillingCountry("NL");
        verify(abnIDeal).setBillingZipCode("NLB1B3");
        verify(abnIDeal).setBillingState("NL");
        verifyNoMoreInteractions(abnIDeal);

        verifyExecute();

    }

    @Test(expected = ApiException.class)
    public void testAbnIDealWithMissingParams() {

        clearRequiredParams();

        assertNull(abnIDeal.setCustomerBankId(null));
        verify(abnIDeal).setCustomerBankId(null);
        verifyNoMoreInteractions(abnIDeal);

        verifyExecute();
    }
}