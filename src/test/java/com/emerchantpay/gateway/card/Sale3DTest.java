package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class Sale3DTest {

    private String uniqueId;

    private GenesisClient client;
    private Sale3DRequest sale3d;

    private Sale3DRequest prepareObject() {
        Sale3DRequest sale = new Sale3DRequest();
        sale.setCurrency(Currency.USD.getCurrency());
        return sale;
    }

    private void setRequiredParams(Sale3DRequest sale) {
        sale.setTransactionId(RandomStringUtils.randomAlphanumeric(16));
        sale.setCustomerEmail("john.doe@gmail.com");
        sale.setAmount(BigDecimal.valueOf(50));
        sale.setCardNumber("4200000000000000")
                .setCardHolder("Test Holder")
                .setExpirationMonth("04")
                .setExpirationYear("2026");
        sale.setConsumerId(RandomStringUtils.randomNumeric(10));
        sale.setSchemeTokenized(Boolean.TRUE);
    }

    @Before
    public void createSale() throws MalformedURLException {

        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sale3d = mock(Sale3DRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(sale3d.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(sale3d);
        assertEquals(client.execute(), sale3d);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSale3D() throws MalformedURLException {

        // Sale3D
        when(sale3d.setTransactionId(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setRemoteIp(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setUsage(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setAmount(isA(BigDecimal.class))).thenReturn(sale3d);
        when(sale3d.setCurrency(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setCardHolder(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setCardNumber(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setExpirationMonth(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setExpirationYear(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setCvv(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setCustomerEmail(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setCustomerPhone(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingPrimaryAddress(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingSecondaryAddress(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingFirstname(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingLastname(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingCity(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingCountry(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingZipCode(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setBillingState(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setNotificationUrl(isA(URL.class))).thenReturn(sale3d);
        when(sale3d.setReturnSuccessUrl(isA(URL.class))).thenReturn(sale3d);
        when(sale3d.setReturnFailureUrl(isA(URL.class))).thenReturn(sale3d);
        when(sale3d.setFXRateId(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setCrypto(isA(Boolean.class))).thenReturn(sale3d);
        when(sale3d.setRecurringType(isA(String.class))).thenReturn(sale3d);
        when(sale3d.setSchemeTokenized(isA(Boolean.class))).thenReturn(sale3d);

        assertEquals(sale3d.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS"),
                sale3d);
        assertEquals(sale3d.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency()), sale3d);
        assertEquals(sale3d.setCardHolder("JOHN DOE").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123"), sale3d);
        assertEquals(sale3d.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555"), sale3d);
        assertEquals(sale3d.setNotificationUrl(new URL("http://www.example.com/notification")), sale3d);
        assertEquals(sale3d.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), sale3d);
        assertEquals(sale3d.setBillingPrimaryAddress("Address1")
                .setBillingSecondaryAddress("Address2")
                .setBillingFirstname("John")
                .setBillingLastname("Doe")
                .setBillingCity("New York")
                .setBillingCountry("US")
                .setBillingZipCode("1000")
                .setBillingState("NY"), sale3d);
        assertEquals(sale3d.setFXRateId("123"), sale3d);
        assertEquals(sale3d.setCrypto(true), sale3d);
        assertEquals(sale3d.setRecurringType("initial"), sale3d);
        assertEquals(sale3d.setSchemeTokenized(true), sale3d);

        verify(sale3d).setTransactionId(uniqueId);
        verify(sale3d).setRemoteIp("192.168.0.1");
        verify(sale3d).setUsage("TICKETS");
        verify(sale3d).setAmount(new BigDecimal("22.00"));
        verify(sale3d).setCurrency(Currency.USD.getCurrency());
        verify(sale3d).setCardHolder("JOHN DOE");
        verify(sale3d).setCardNumber("4200000000000000");
        verify(sale3d).setExpirationMonth("02");
        verify(sale3d).setExpirationYear("2020");
        verify(sale3d).setCvv("123");
        verify(sale3d).setCustomerEmail("john@example.com");
        verify(sale3d).setCustomerPhone("+5555555555");
        verify(sale3d).setNotificationUrl(new URL("http://www.example.com/notification"));
        verify(sale3d).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(sale3d).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(sale3d).setBillingPrimaryAddress("Address1");
        verify(sale3d).setBillingSecondaryAddress("Address2");
        verify(sale3d).setBillingFirstname("John");
        verify(sale3d).setBillingLastname("Doe");
        verify(sale3d).setBillingCity("New York");
        verify(sale3d).setBillingCountry("US");
        verify(sale3d).setBillingZipCode("1000");
        verify(sale3d).setBillingState("NY");
        verify(sale3d).setFXRateId("123");
        verify(sale3d).setCrypto(true);
        verify(sale3d).setRecurringType("initial");
        verify(sale3d).setSchemeTokenized(true);

        verifyNoMoreInteractions(sale3d);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testSale3DWithMissingParams() {
        clearRequiredParams();
        assertNull(sale3d.setBillingCountry(null));
        verify(sale3d).setBillingCountry(null);
        verifyNoMoreInteractions(sale3d);

        verifyExecute();
    }

    @Test(expected = RegexException.class)
    public void testNegativeAmountError(){
        Sale3DRequest sale3D = prepareObject();
        sale3D.setAmount(new BigDecimal("-22.00"));
        sale3D.buildPaymentParams();
    }

    @Test
    public void testZeroAmount(){
        Sale3DRequest sale3D = prepareObject();
        BigDecimal amount = new BigDecimal("0.00");
        sale3D.setAmount(new BigDecimal("0.00"));
        assertEquals(amount, sale3D.getAmount());
        assertTrue(sale3D.buildPaymentParams() instanceof RequestBuilder);
    }

    @Test
    public void testRecurrency_ShouldSuccess_WhenProvidedInitial(){
        Sale3DRequest sale3D = new Sale3DRequest();
        sale3D.setRecurringType("initial");
        sale3D.buildRecurringAttrParams();
    }

    @Test
    public void testRecurrencShouldSuccess_WhenProvidedManaged(){
        Sale3DRequest sale3D = new Sale3DRequest();
        sale3D.setRecurringType("managed");
        sale3D.buildRecurringAttrParams();
    }

    @Test(expected = InvalidParamException.class)
    public void testRecurrencyError(){
        Sale3DRequest sale3D = new Sale3DRequest();
        sale3D.setRecurringType("subsequent");
        sale3D.buildRecurringAttrParams();
    }

    @Test
    public void testMissingSchemeTokenizedParam(){
        Sale3DRequest sale3D = prepareObject();
        setRequiredParams(sale3D);
        sale3D.setSchemeTokenized(null);
        assertThrows("scheme_tokenized Required param(s) are missing", RequiredParamsException.class, sale3D::toXML);
    }

    @Test
    public void testValidRequiredParam() {
        Sale3DRequest sale3D = prepareObject();
        setRequiredParams(sale3D);
        String xml = sale3D.toXML();
        assertTrue(xml.contains("<scheme_tokenized>true</scheme_tokenized>"));
    }

}