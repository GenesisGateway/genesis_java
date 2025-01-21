package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


public class SaleRequestTest {

    private String uniqueId;

    private GenesisClient client;
    private SaleRequest sale;

    @Before
    public void createSale() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sale = mock(SaleRequest.class);
    }

    private SaleRequest prepareObjectWithoutCardData() {
        SaleRequest sale = new SaleRequest();
        sale.setTransactionId(uniqueId);
        sale.setCurrency(Currency.USD.getCurrency());
        sale.setAmount(new BigDecimal("12.00"));
        return sale;
    }

    private SaleRequest prepareObject() {
        SaleRequest sale = prepareObjectWithoutCardData();
        sale.setCardNumber("4200000000000000");
        sale.setCardHolder("PLAMEN PETROV");
        sale.setExpirationMonth("02");
        sale.setExpirationYear("2020");
        return sale;
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(sale.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(sale);
        assertEquals(client.execute(), sale);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSale() {

        // Sale
        when(sale.setTransactionId(isA(String.class))).thenReturn(sale);
        when(sale.setRemoteIp(isA(String.class))).thenReturn(sale);
        when(sale.setUsage(isA(String.class))).thenReturn(sale);
        when(sale.setAmount(isA(BigDecimal.class))).thenReturn(sale);
        when(sale.setCurrency(isA(String.class))).thenReturn(sale);
        when(sale.setCardHolder(isA(String.class))).thenReturn(sale);
        when(sale.setCardNumber(isA(String.class))).thenReturn(sale);
        when(sale.setExpirationMonth(isA(String.class))).thenReturn(sale);
        when(sale.setExpirationYear(isA(String.class))).thenReturn(sale);
        when(sale.setCvv(isA(String.class))).thenReturn(sale);
        when(sale.setCustomerEmail(isA(String.class))).thenReturn(sale);
        when(sale.setCustomerPhone(isA(String.class))).thenReturn(sale);
        when(sale.setBillingPrimaryAddress(isA(String.class))).thenReturn(sale);
        when(sale.setBillingSecondaryAddress(isA(String.class))).thenReturn(sale);
        when(sale.setBillingFirstname(isA(String.class))).thenReturn(sale);
        when(sale.setBillingLastname(isA(String.class))).thenReturn(sale);
        when(sale.setBillingCity(isA(String.class))).thenReturn(sale);
        when(sale.setBillingCountry(isA(String.class))).thenReturn(sale);
        when(sale.setBillingZipCode(isA(String.class))).thenReturn(sale);
        when(sale.setBillingState(isA(String.class))).thenReturn(sale);
        when(sale.setFXRateId(isA(String.class))).thenReturn(sale);
        when(sale.setCrypto(isA(Boolean.class))).thenReturn(sale);
        when(sale.setRecurringType(isA(String.class))).thenReturn(sale);
        when(sale.setSchemeTokenized(isA(Boolean.class))).thenReturn(sale);

        assertEquals(sale.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS"), sale);
        assertEquals(sale.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency()), sale);
        assertEquals(sale.setCardHolder("PLAMEN PETROV").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123"), sale);
        assertEquals(sale.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555"), sale);
        assertEquals( sale.setBillingPrimaryAddress("Address1")
                .setBillingSecondaryAddress("Address2")
                .setBillingFirstname("John")
                .setBillingLastname("Doe").setBillingCity("New York")
                .setBillingCountry("US")
                .setBillingZipCode("1000")
                .setBillingState("NY"), sale);
        assertEquals(sale.setFXRateId("123"), sale);
        assertEquals(sale.setCrypto(true), sale);
        assertEquals(sale.setRecurringType("initial"), sale);
        assertEquals(sale.setSchemeTokenized(Boolean.TRUE), sale);

        verify(sale).setTransactionId(uniqueId);
        verify(sale).setRemoteIp("192.168.0.1");
        verify(sale).setUsage("TICKETS");
        verify(sale).setAmount(new BigDecimal("22.00"));
        verify(sale).setCurrency(Currency.USD.getCurrency());
        verify(sale).setCardHolder("PLAMEN PETROV");
        verify(sale).setCardNumber("4200000000000000");
        verify(sale).setExpirationMonth("02");
        verify(sale).setExpirationYear("2020");
        verify(sale).setCvv("123");
        verify(sale).setCustomerEmail("john@example.com");
        verify(sale).setCustomerPhone("+5555555555");
        verify(sale).setBillingPrimaryAddress("Address1");
        verify(sale).setBillingSecondaryAddress("Address2");
        verify(sale).setBillingFirstname("John");
        verify(sale).setBillingLastname("Doe");
        verify(sale).setBillingCity("New York");
        verify(sale).setBillingCountry("US");
        verify(sale).setBillingZipCode("1000");
        verify(sale).setBillingState("NY");
        verify(sale).setFXRateId("123");
        verify(sale).setCrypto(true);
        verify(sale).setRecurringType("initial");
        verify(sale).setSchemeTokenized(Boolean.TRUE);
        verifyNoMoreInteractions(sale);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testSaleWithMissingParams() {

        clearRequiredParams();
        assertNull(sale.setBillingCountry(null));
        verify(sale).setBillingCountry(null);
        verifyNoMoreInteractions(sale);

        verifyExecute();
    }

    @Test(expected = RegexException.class)
    public void testNegativeAmountError(){
        SaleRequest sale = new SaleRequest();
        sale.setCurrency(Currency.USD.getCurrency());
        sale.setAmount(new BigDecimal("-22.00"));
        sale.buildPaymentParams();
    }

    @Test
    public void testZeroAmount(){
        SaleRequest sale = new SaleRequest();
        sale.setCurrency(Currency.USD.getCurrency());
        BigDecimal amount = new BigDecimal("0.00");
        sale.setAmount(new BigDecimal("0.00"));
        assertEquals(amount, sale.getAmount());
        assertTrue(sale.buildPaymentParams() instanceof RequestBuilder);
    }

    @Test
    public void testRecurrency_ShouldSuccess_WhenProvidedReferenceId(){
        SaleRequest sale = prepareObject();
        sale.setRecurringType("subsequent");
        sale.setReferenceId("1234");
        sale.toXML();
    }

    @Test
    public void testRecurrency_ShouldSuccess_WhenMissedCardData(){
        SaleRequest sale = prepareObjectWithoutCardData();
        sale.setRecurringType("subsequent");
        sale.setReferenceId("1234");
        sale.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRecurrency_ThrowException_WhenMissedReferenceId() {
        SaleRequest sale = prepareObject();
        sale.setRecurringType("subsequent");
        sale.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRecurrency_ThrowException_WhenMissedAmount() {
        SaleRequest authorize = prepareObject();
        authorize.setRecurringType("subsequent");
        authorize.setAmount(null);
        authorize.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRecurrencyError(){
        SaleRequest sale = new SaleRequest();
        sale.setRecurringType("ANY_TYPE");
    }

}