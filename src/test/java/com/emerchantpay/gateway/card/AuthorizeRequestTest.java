package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AuthorizeRequestTest {

    private String uniqueId;

    private GenesisClient client;
    private AuthorizeRequest authorize;

    @Before
    public void createAuthorize() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        authorize = mock(AuthorizeRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(authorize.setCardNumber(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(authorize);
        assertEquals(client.execute(), authorize);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testAuthorize() throws MalformedURLException {

        // Authorize
        when(authorize.setTransactionId(isA(String.class))).thenReturn(authorize);
        when(authorize.setRemoteIp((isA(String.class)))).thenReturn(authorize);
        when(authorize.setUsage(isA(String.class))).thenReturn(authorize);
        when(authorize.setCurrency(isA(String.class))).thenReturn(authorize);
        when(authorize.setAmount(isA(BigDecimal.class))).thenReturn(authorize);
        when(authorize.setCardNumber(isA(String.class))).thenReturn(authorize);
        when(authorize.setCardHolder(isA(String.class))).thenReturn(authorize);
        when(authorize.setCvv(isA(String.class))).thenReturn(authorize);
        when(authorize.setExpirationMonth(isA(String.class))).thenReturn(authorize);
        when(authorize.setExpirationYear(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingPrimaryAddress(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingSecondaryAddress(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingFirstname(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingLastname(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingCity(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingCountry(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingZipCode(isA(String.class))).thenReturn(authorize);
        when(authorize.setBillingState(isA(String.class))).thenReturn(authorize);
        when(authorize.setCustomerEmail(isA(String.class))).thenReturn(authorize);
        when(authorize.setCustomerPhone(isA(String.class))).thenReturn(authorize);
        when(authorize.setBirthDate(isA(String.class))).thenReturn(authorize);
        when(authorize.setFXRateId(isA(String.class))).thenReturn(authorize);
        when(authorize.setCrypto(isA(Boolean.class))).thenReturn(authorize);

        assertEquals(authorize.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), authorize);
        assertEquals(authorize.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00")), authorize);
        assertEquals(authorize.setCardNumber("4200000000000000").setCardHolder("PLAMEN PETROV").setCvv("123")
                .setExpirationMonth("02").setExpirationYear("2020"), authorize);
        assertEquals(authorize.setBirthDate("24-04-1988"), authorize);
        assertEquals(authorize.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("New York").setBillingCountry("US")
                .setBillingZipCode("M4B1B3").setBillingState("CA"), authorize);
        assertEquals(authorize.setFXRateId("123"), authorize);
        assertEquals(authorize.setCrypto(true), authorize);

        verify(authorize).setTransactionId(uniqueId);
        verify(authorize).setRemoteIp("82.137.112.202");
        verify(authorize).setUsage("TICKETS");
        verify(authorize).setCurrency(Currency.USD.getCurrency());
        verify(authorize).setAmount(new BigDecimal("10.00"));
        verify(authorize).setCardNumber("4200000000000000");
        verify(authorize).setCardHolder("PLAMEN PETROV");
        verify(authorize).setCvv("123");
        verify(authorize).setExpirationMonth("02");
        verify(authorize).setExpirationYear("2020");
        verify(authorize).setBirthDate("24-04-1988");
        verify(authorize).setBillingPrimaryAddress("Berlin");
        verify(authorize).setBillingSecondaryAddress("Berlin");
        verify(authorize).setBillingFirstname("Plamen");
        verify(authorize).setBillingLastname("Petrov");
        verify(authorize).setBillingCity("New York");
        verify(authorize).setBillingCountry("US");
        verify(authorize).setBillingZipCode("M4B1B3");
        verify(authorize).setBillingState("CA");
        verify(authorize).setFXRateId("123");
        verify(authorize).setCrypto(true);
        verifyNoMoreInteractions(authorize);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testAuthorizeWithMissingParams() {
        clearRequiredParams();

        assertNull(authorize.setCardNumber(null));
        verify(authorize).setCardNumber(null);
        verifyNoMoreInteractions(authorize);

        verifyExecute();
    }
}