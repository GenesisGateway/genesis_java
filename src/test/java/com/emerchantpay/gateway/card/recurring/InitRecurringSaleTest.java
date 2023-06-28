package com.emerchantpay.gateway.card.recurring;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSaleRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class InitRecurringSaleTest {

    private String uniqueId;

    private GenesisClient client;
    private InitRecurringSaleRequest initRecurring;

    @Before
    public void createInitRecurring() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        initRecurring = mock(InitRecurringSaleRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(initRecurring.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(initRecurring);
        assertEquals(client.execute(), initRecurring);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testInitRecurring() {
        // Init Recurring
        when(initRecurring.setTransactionId(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setRemoteIp(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setUsage(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setMoto(isA(Boolean.class))).thenReturn(initRecurring);
        when(initRecurring.setCurrency(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setAmount(isA(BigDecimal.class))).thenReturn(initRecurring);
        when(initRecurring.setRecurringCategory(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setCardNumber(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setCardHolder(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setCvv(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setExpirationMonth(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setExpirationYear(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingPrimaryAddress(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingSecondaryAddress(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingFirstname(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingLastname(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingCity(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingCountry(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingZipCode(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingState(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBillingState(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setCustomerEmail(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setCustomerPhone(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setBirthDate(isA(String.class))).thenReturn(initRecurring);
        when(initRecurring.setFXRateId(isA(String.class))).thenReturn(initRecurring);

        assertEquals(initRecurring.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                initRecurring);
        assertEquals(initRecurring.setMoto(true), initRecurring);
        assertEquals(initRecurring.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00")),
                initRecurring);
        assertEquals(initRecurring.setCardNumber("4200000000000000").setCardHolder("PLAMEN PETROV").setCvv("123")
                .setExpirationMonth("02").setExpirationYear("2020"), initRecurring);
        assertEquals(initRecurring.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), initRecurring);
        assertEquals(initRecurring.setBirthDate("24-04-1988"), initRecurring);
        assertEquals(initRecurring.setRecurringCategory("subscription"), initRecurring);
        assertEquals(initRecurring.setBillingPrimaryAddress("Berlin")
                .setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen")
                .setBillingLastname("Petrov")
                .setBillingCity("Berlin")
                .setBillingCountry("US")
                .setBillingZipCode("M4B1B3")
                .setBillingState("CA"), initRecurring);
        assertEquals(initRecurring.setFXRateId("123"), initRecurring);

        verify(initRecurring).setTransactionId(uniqueId);
        verify(initRecurring).setRemoteIp("82.137.112.202");
        verify(initRecurring).setUsage("TICKETS");
        verify(initRecurring).setMoto(true);
        verify(initRecurring).setCurrency(Currency.USD.getCurrency());
        verify(initRecurring).setAmount(new BigDecimal("10.00"));
        verify(initRecurring).setRecurringCategory("subscription");
        verify(initRecurring).setCardNumber("4200000000000000");
        verify(initRecurring).setCardHolder("PLAMEN PETROV");
        verify(initRecurring).setCvv("123");
        verify(initRecurring).setExpirationMonth("02");
        verify(initRecurring).setExpirationYear("2020");
        verify(initRecurring).setCustomerEmail("john@example.com");
        verify(initRecurring).setCustomerPhone("+55555555");
        verify(initRecurring).setBirthDate("24-04-1988");
        verify(initRecurring).setBillingPrimaryAddress("Berlin");
        verify(initRecurring).setBillingSecondaryAddress("Berlin");
        verify(initRecurring).setBillingFirstname("Plamen");
        verify(initRecurring).setBillingLastname("Petrov");
        verify(initRecurring).setBillingCity("Berlin");
        verify(initRecurring).setBillingCountry("US");
        verify(initRecurring).setBillingZipCode("M4B1B3");
        verify(initRecurring).setBillingState("CA");
        verify(initRecurring).setFXRateId("123");
        verifyNoMoreInteractions(initRecurring);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testInitRecurringWithMissingParams() {
        clearRequiredParams();
        assertNull(initRecurring.setBillingCountry(null));
        verify(initRecurring).setBillingCountry(null);
        verifyNoMoreInteractions(initRecurring);

        verifyExecute();
    }

    @Test(expected = RegexException.class)
    public void testNegativeAmountError(){
        InitRecurringSaleRequest initRecurringSale = new InitRecurringSaleRequest();
        initRecurringSale.setCurrency(Currency.USD.getCurrency());
        initRecurringSale.setAmount(new BigDecimal("-22.00"));
        initRecurringSale.buildPaymentParams();
    }

    @Test
    public void testZeroAmount(){
        InitRecurringSaleRequest initRecurringSale = new InitRecurringSaleRequest();
        initRecurringSale.setCurrency(Currency.USD.getCurrency());
        BigDecimal amount = new BigDecimal("0.00");
        initRecurringSale.setAmount(new BigDecimal("0.00"));
        assertEquals(amount, initRecurringSale.getAmount());
        assertTrue(initRecurringSale.buildPaymentParams() instanceof RequestBuilder);
    }

}