package com.emerchantpay.gateway.card.recurring;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.Authorize3DRequest;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSale3DRequest;
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

public class InitRecurringSale3DTest {

    private String uniqueId;

    private GenesisClient client;
    private InitRecurringSale3DRequest initrecsale3d;

    @Before
    public void createSale() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        initrecsale3d = mock(InitRecurringSale3DRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(initrecsale3d.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(initrecsale3d);
        assertEquals(client.execute(), initrecsale3d);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSale3D() throws MalformedURLException {
        // Sale3D
        when(initrecsale3d.setTransactionId(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setRemoteIp(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setUsage(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setMoto(isA(Boolean.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setCurrency(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setAmount(isA(BigDecimal.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setRecurringCategory(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setCardNumber(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setCardHolder(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setCvv(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setExpirationMonth(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setExpirationYear(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingPrimaryAddress(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingSecondaryAddress(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingFirstname(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingLastname(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingCity(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingCountry(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingZipCode(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingState(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setBillingState(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setCustomerEmail(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setCustomerPhone(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setReturnSuccessUrl(isA(URL.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setReturnFailureUrl(isA(URL.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setNotificationUrl(isA(URL.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setFXRateId(isA(String.class))).thenReturn(initrecsale3d);
        when(initrecsale3d.setSchemeTokenized(isA(Boolean.class))).thenReturn(initrecsale3d);

        assertEquals(initrecsale3d.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS"),
                initrecsale3d);
        assertEquals(initrecsale3d.setMoto(true), initrecsale3d);
        assertEquals(initrecsale3d.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency()),
                initrecsale3d);
        assertEquals(initrecsale3d.setRecurringCategory("subscription"), initrecsale3d);
        assertEquals(initrecsale3d.setCardHolder("PLAMEN PETROV").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123"), initrecsale3d);
        assertEquals(initrecsale3d.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555"), initrecsale3d);
        assertEquals(initrecsale3d.setNotificationUrl(new URL("http://www.example.com/notification")),
                initrecsale3d);
        assertEquals(initrecsale3d.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), initrecsale3d);
        assertEquals(initrecsale3d.setBillingPrimaryAddress("Berlin")
                .setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen")
                .setBillingLastname("Petrov")
                .setBillingCity("New York")
                .setBillingCountry("US")
                .setBillingZipCode("M4B1B3")
                .setBillingState("CA"), initrecsale3d);
        assertEquals(initrecsale3d.setFXRateId("123"), initrecsale3d);
        assertEquals(initrecsale3d.setSchemeTokenized(Boolean.TRUE), initrecsale3d);

        verify(initrecsale3d).setTransactionId(uniqueId.toString());
        verify(initrecsale3d).setRemoteIp("192.168.0.1");
        verify(initrecsale3d).setUsage("TICKETS");
        verify(initrecsale3d).setMoto(true);
        verify(initrecsale3d).setAmount(new BigDecimal("22.00"));
        verify(initrecsale3d).setRecurringCategory("subscription");
        verify(initrecsale3d).setCurrency(Currency.USD.getCurrency());
        verify(initrecsale3d).setCardNumber("4200000000000000");
        verify(initrecsale3d).setCardHolder("PLAMEN PETROV");
        verify(initrecsale3d).setCvv("123");
        verify(initrecsale3d).setExpirationMonth("02");
        verify(initrecsale3d).setExpirationYear("2020");
        verify(initrecsale3d).setCustomerEmail("john@example.com");
        verify(initrecsale3d).setCustomerPhone("+5555555555");
        verify(initrecsale3d).setBillingPrimaryAddress("Berlin");
        verify(initrecsale3d).setBillingSecondaryAddress("Berlin");
        verify(initrecsale3d).setBillingFirstname("Plamen");
        verify(initrecsale3d).setBillingLastname("Petrov");
        verify(initrecsale3d).setBillingCity("New York");
        verify(initrecsale3d).setBillingCountry("US");
        verify(initrecsale3d).setBillingZipCode("M4B1B3");
        verify(initrecsale3d).setBillingState("CA");
        verify(initrecsale3d).setNotificationUrl(new URL("http://www.example.com/notification"));
        verify(initrecsale3d).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(initrecsale3d).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(initrecsale3d).setFXRateId("123");
        verify(initrecsale3d).setSchemeTokenized(Boolean.TRUE);
        verifyNoMoreInteractions(initrecsale3d);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testSale3DWithMissingParams() {
        clearRequiredParams();
        assertNull(initrecsale3d.setBillingCountry(null));
        verify(initrecsale3d).setBillingCountry(null);
        verifyNoMoreInteractions(initrecsale3d);

        verifyExecute();
    }

    @Test(expected = RegexException.class)
    public void testNegativeAmountError(){
        InitRecurringSale3DRequest initRecurringSale3D = new InitRecurringSale3DRequest();
        initRecurringSale3D.setCurrency(Currency.USD.getCurrency());
        initRecurringSale3D.setAmount(new BigDecimal("-22.00"));
        initRecurringSale3D.buildPaymentParams();
    }

    @Test
    public void testZeroAmount(){
        InitRecurringSale3DRequest initRecurringSale3D = new InitRecurringSale3DRequest();
        initRecurringSale3D.setCurrency(Currency.USD.getCurrency());
        BigDecimal amount = new BigDecimal("0.00");
        initRecurringSale3D.setAmount(new BigDecimal("0.00"));
        assertEquals(amount, initRecurringSale3D.getAmount());
        assertTrue(initRecurringSale3D.buildPaymentParams() instanceof RequestBuilder);
    }

    @Test
    public void testMissingSchemeTokenizedParam(){
        InitRecurringSale3DRequest sale3D = new InitRecurringSale3DRequest();
        setRequiredParams(sale3D);
        sale3D.setSchemeTokenized(null);
        assertThrows("scheme_tokenized Required param(s) are missing", RequiredParamsException.class, sale3D::toXML);
    }

    @Test
    public void testValidRequiredParam() {
        InitRecurringSale3DRequest sale3D = new InitRecurringSale3DRequest();
        setRequiredParams(sale3D);
        String xml = sale3D.toXML();
        assertTrue(xml.contains("<scheme_tokenized>true</scheme_tokenized>"));
    }

    private void setRequiredParams(InitRecurringSale3DRequest sale) {
        sale.setTransactionId(RandomStringUtils.randomAlphanumeric(16));
        sale.setCustomerEmail("john.doe@gmail.com");
        sale.setCurrency(Currency.EUR.getCurrency()).setAmount(BigDecimal.valueOf(50));
        sale.setCardNumber("4200000000000000")
                .setCardHolder("Test Holder")
                .setExpirationMonth("04")
                .setExpirationYear("2026");
        sale.setConsumerId(RandomStringUtils.randomNumeric(10));
        sale.setSchemeTokenized(Boolean.TRUE);
    }
}