package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.card.PayoutRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


public class PayoutTest {

    private String uniqueId;

    private GenesisClient client;
    private PayoutRequest payout;

    @Before
    public void createPayout() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        payout = mock(PayoutRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(payout.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(payout);
        assertEquals(client.execute(), payout);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testPayout()  {
        // Payout
        when(payout.setTransactionId(isA(String.class))).thenReturn(payout);
        when(payout.setRemoteIp(isA(String.class))).thenReturn(payout);
        when(payout.setUsage(isA(String.class))).thenReturn(payout);
        when(payout.setAmount(isA(BigDecimal.class))).thenReturn(payout);
        when(payout.setCurrency(isA(String.class))).thenReturn(payout);
        when(payout.setCardHolder(isA(String.class))).thenReturn(payout);
        when(payout.setCardNumber(isA(String.class))).thenReturn(payout);
        when(payout.setExpirationMonth(isA(String.class))).thenReturn(payout);
        when(payout.setExpirationYear(isA(String.class))).thenReturn(payout);
        when(payout.setCvv(isA(String.class))).thenReturn(payout);
        when(payout.setCustomerEmail(isA(String.class))).thenReturn(payout);
        when(payout.setCustomerPhone(isA(String.class))).thenReturn(payout);
        when(payout.setBillingPrimaryAddress(isA(String.class))).thenReturn(payout);
        when(payout.setBillingSecondaryAddress(isA(String.class))).thenReturn(payout);
        when(payout.setBillingFirstname(isA(String.class))).thenReturn(payout);
        when(payout.setBillingLastname(isA(String.class))).thenReturn(payout);
        when(payout.setBillingCity(isA(String.class))).thenReturn(payout);
        when(payout.setBillingCountry(isA(String.class))).thenReturn(payout);
        when(payout.setBillingZipCode(isA(String.class))).thenReturn(payout);
        when(payout.setBillingState(isA(String.class))).thenReturn(payout);
        when(payout.setFXRateId(isA(String.class))).thenReturn(payout);

        assertEquals(payout.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS"),
                payout);
        assertEquals(payout.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency()), payout);
        assertEquals(payout.setCardHolder("PLAMEN PETROV").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123"), payout);
        assertEquals(payout.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555"), payout);
        assertEquals(payout.setBillingPrimaryAddress("Address1")
                .setBillingSecondaryAddress("Address2")
                .setBillingFirstname("John")
                .setBillingLastname("Doe")
                .setBillingCity("New York")
                .setBillingCountry("US")
                .setBillingZipCode("1000")
                .setBillingState("NY"), payout);
        assertEquals(payout.setFXRateId("123"), payout);

        verify(payout).setTransactionId(uniqueId.toString());
        verify(payout).setRemoteIp("192.168.0.1");
        verify(payout).setUsage("TICKETS");
        verify(payout).setAmount(new BigDecimal("22.00"));
        verify(payout).setCurrency(Currency.USD.getCurrency());
        verify(payout).setCurrency(Currency.USD.getCurrency());
        verify(payout).setCardHolder("PLAMEN PETROV");
        verify(payout).setCardNumber("4200000000000000");
        verify(payout).setExpirationMonth("02");
        verify(payout).setExpirationYear("2020");
        verify(payout).setCvv("123");
        verify(payout).setCustomerEmail("john@example.com");
        verify(payout).setCustomerPhone("+5555555555");
        verify(payout).setBillingPrimaryAddress("Address1");
        verify(payout).setBillingSecondaryAddress("Address2");
        verify(payout).setBillingFirstname("John");
        verify(payout).setBillingLastname("Doe");
        verify(payout).setBillingCity("New York");
        verify(payout).setBillingCountry("US");
        verify(payout).setBillingZipCode("1000");
        verify(payout).setBillingState("NY");
        verify(payout).setFXRateId("123");

        verifyNoMoreInteractions(payout);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testPayoutWithMissingParams() {

        clearRequiredParams();
        assertNull(payout.setBillingCountry(null));
        verify(payout).setBillingCountry(null);
        verifyNoMoreInteractions(payout);

        verifyExecute();
    }
}