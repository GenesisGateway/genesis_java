package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.TrustlySaleRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.TrustlyWithdrawalRequest;
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

public class TrustlyTest {

    private GenesisClient client;
    private TrustlySaleRequest trustlySale;
    private TrustlyWithdrawalRequest trustlyWithdrawal;

    private String uidSale;
    private String uidWithdrawal;

    @Before
    public void createSale() {
        uidSale = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        trustlySale = mock(TrustlySaleRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(trustlySale.setBillingCountry(null)).thenThrow(exception);
        when(trustlyWithdrawal.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifySaleExecute() {
        when(client.execute()).thenReturn(trustlySale);
        assertEquals(client.execute(), trustlySale);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyWithdrawalExecute() {
        when(client.execute()).thenReturn(trustlyWithdrawal);
        assertEquals(client.execute(), trustlyWithdrawal);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Before
    public void createWithdrawal() {
        uidWithdrawal = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        trustlyWithdrawal = mock(TrustlyWithdrawalRequest.class);
    }

    @Test
    public void testSale() throws MalformedURLException {
        // Sale
        when(trustlySale.setTransactionId(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setRemoteIp(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setUsage(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setCurrency(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setAmount(isA(BigDecimal.class))).thenReturn(trustlySale);
        when(trustlySale.setCustomerEmail(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setCustomerPhone(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setReturnSuccessUrl(isA(URL.class))).thenReturn(trustlySale);
        when(trustlySale.setReturnFailureUrl(isA(URL.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingPrimaryAddress(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingSecondaryAddress(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingZipCode(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingFirstname(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingLastname(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingCity(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingCountry(isA(String.class))).thenReturn(trustlySale);
        when(trustlySale.setBillingState(isA(String.class))).thenReturn(trustlySale);

        assertEquals(trustlySale.setTransactionId(uidSale).setRemoteIp("82.137.112.202").setUsage("TICKETS"), trustlySale);
        assertEquals(trustlySale.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")), trustlySale);
        assertEquals(trustlySale.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), trustlySale);
        assertEquals(trustlySale.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), trustlySale);
        assertEquals(trustlySale.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE"), trustlySale);

        verify(trustlySale).setTransactionId(uidSale);
        verify(trustlySale).setRemoteIp("82.137.112.202");
        verify(trustlySale).setUsage("TICKETS");
        verify(trustlySale).setCurrency(Currency.EUR.getCurrency());
        verify(trustlySale).setAmount(new BigDecimal("2.00"));
        verify(trustlySale).setCustomerEmail("john@example.com");
        verify(trustlySale).setCustomerPhone("+55555555");
        verify(trustlySale).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(trustlySale).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(trustlySale).setBillingPrimaryAddress("Berlin");
        verify(trustlySale).setBillingSecondaryAddress("Berlin");
        verify(trustlySale).setBillingFirstname("Plamen");
        verify(trustlySale).setBillingLastname("Petrov");
        verify(trustlySale).setBillingCity("Berlin");
        verify(trustlySale).setBillingCountry("DE");
        verify(trustlySale).setBillingZipCode("M4B1B3");
        verify(trustlySale).setBillingState("BE");
        verifyNoMoreInteractions(trustlySale);

        verifySaleExecute();
    }

    @Test
    public void testWithdrawal() throws MalformedURLException {
        // Withdrawal
        when(trustlyWithdrawal.setTransactionId(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setRemoteIp(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setUsage(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setCurrency(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setAmount(isA(BigDecimal.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setCustomerEmail(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setCustomerPhone(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setReturnSuccessUrl(isA(URL.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setReturnFailureUrl(isA(URL.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBirthDate(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingPrimaryAddress(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingSecondaryAddress(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingZipCode(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingFirstname(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingLastname(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingCity(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingCountry(isA(String.class))).thenReturn(trustlyWithdrawal);
        when(trustlyWithdrawal.setBillingState(isA(String.class))).thenReturn(trustlyWithdrawal);

        assertEquals(trustlyWithdrawal.setTransactionId(uidWithdrawal).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                trustlyWithdrawal);
        assertEquals(trustlyWithdrawal.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")),
                trustlyWithdrawal);
        assertEquals(trustlyWithdrawal.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), trustlyWithdrawal);
        assertEquals(trustlyWithdrawal.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), trustlyWithdrawal);
        assertEquals(trustlyWithdrawal.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE"), trustlyWithdrawal);
        assertEquals(trustlyWithdrawal.setBirthDate("24-04-1988"), trustlyWithdrawal);

        verify(trustlyWithdrawal).setTransactionId(uidWithdrawal);
        verify(trustlyWithdrawal).setRemoteIp("82.137.112.202");
        verify(trustlyWithdrawal).setUsage("TICKETS");
        verify(trustlyWithdrawal).setCurrency(Currency.EUR.getCurrency());
        verify(trustlyWithdrawal).setAmount(new BigDecimal("2.00"));
        verify(trustlyWithdrawal).setCustomerEmail("john@example.com");
        verify(trustlyWithdrawal).setCustomerPhone("+55555555");
        verify(trustlyWithdrawal).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(trustlyWithdrawal).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(trustlyWithdrawal).setBirthDate("24-04-1988");
        verify(trustlyWithdrawal).setBillingPrimaryAddress("Berlin");
        verify(trustlyWithdrawal).setBillingSecondaryAddress("Berlin");
        verify(trustlyWithdrawal).setBillingFirstname("Plamen");
        verify(trustlyWithdrawal).setBillingLastname("Petrov");
        verify(trustlyWithdrawal).setBillingCity("Berlin");
        verify(trustlyWithdrawal).setBillingCountry("DE");
        verify(trustlyWithdrawal).setBillingZipCode("M4B1B3");
        verify(trustlyWithdrawal).setBillingState("BE");
        verifyNoMoreInteractions(trustlyWithdrawal);

        verifyWithdrawalExecute();
    }

    @Test(expected = ApiException.class)
    public void testSaleWithMissingParams() {
        clearRequiredParams();

        assertNull(trustlySale.setBillingCountry(null));
        verify(trustlySale).setBillingCountry(null);
        verifyNoMoreInteractions(trustlySale);

        verifySaleExecute();
    }

    @Test(expected = ApiException.class)
    public void testWithdrawalWithMissingParams() {
        clearRequiredParams();

        assertNull(trustlyWithdrawal.setBillingCountry(null));
        verify(trustlyWithdrawal).setBillingCountry(null);
        verifyNoMoreInteractions(trustlyWithdrawal);

        verifyWithdrawalExecute();
    }
}