package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

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

    private AuthorizeRequest prepareObjectWithoutCardData() {
        AuthorizeRequest authorize = new AuthorizeRequest();
        authorize.setTransactionId(uniqueId);
        authorize.setCurrency(Currency.USD.getCurrency());
        authorize.setAmount(new BigDecimal("12.00"));
        return authorize;
    }

    private AuthorizeRequest prepareObject() {
        AuthorizeRequest authorize = prepareObjectWithoutCardData();
        authorize.setCardNumber("4200000000000000");
        authorize.setCardHolder("PLAMEN PETROV");
        authorize.setExpirationMonth("02");
        authorize.setExpirationYear("2020");
        return authorize;
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
        when(authorize.setRecurringType(isA(String.class))).thenReturn(authorize);
        when(authorize.setSchemeTokenized(isA(Boolean.class))).thenReturn(authorize);

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
        assertEquals(authorize.setRecurringType("initial"), authorize);
        assertEquals(authorize.setSchemeTokenized(Boolean.TRUE), authorize);

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
        verify(authorize).setRecurringType("initial");
        verify(authorize).setSchemeTokenized(Boolean.TRUE);
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

    @Test(expected = RegexException.class)
    public void testNegativeAmountError(){
        AuthorizeRequest authorize = new AuthorizeRequest();
        authorize.setCurrency(Currency.USD.getCurrency());
        authorize.setAmount(new BigDecimal("-22.00"));
        authorize.buildPaymentParams();
    }

    @Test
    public void testZeroAmount(){
        AuthorizeRequest authorize = new AuthorizeRequest();
        authorize.setCurrency(Currency.USD.getCurrency());
        BigDecimal amount = new BigDecimal("0.00");
        authorize.setAmount(new BigDecimal("0.00"));
        assertEquals(amount, authorize.getAmount());
        assertTrue(authorize.buildPaymentParams() instanceof RequestBuilder);
    }

    @Test
    public void testRecurrency_ShouldSuccess_WhenProvidedReferenceId(){
        AuthorizeRequest authorize = prepareObject();
        authorize.setRecurringType("subsequent");
        authorize.setReferenceId("1234");
        authorize.toXML();
    }

    @Test
    public void testRecurrency_ShouldSuccess_WhenMissedCardData(){
        AuthorizeRequest authorize = prepareObjectWithoutCardData();
        authorize.setRecurringType("subsequent");
        authorize.setReferenceId("1234");
        authorize.toXML();
    }

    @Test (expected = InvalidParamException.class)
    public void testRecurrency_ThrowException_WhenMissedReferenceId(){
        AuthorizeRequest authorize = prepareObject();
        authorize.setRecurringType("subsequent");
        authorize.toXML();
    }

    @Test (expected = RequiredParamsException.class)
    public void testRecurrency_ThrowException_WhenMissedAmount(){
        AuthorizeRequest authorize = prepareObject();
        authorize.setRecurringType("subsequent");
        authorize.setAmount(null);
        authorize.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRecurrencyError(){
        AuthorizeRequest authorize = prepareObject();
        authorize.setRecurringType("ANY_TYPE");
    }

}