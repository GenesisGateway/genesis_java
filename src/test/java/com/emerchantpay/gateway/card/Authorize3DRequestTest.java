package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.card.Authorize3DRequest;
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


public class Authorize3DRequestTest {

    private String uniqueId;

    private GenesisClient client;
    private Authorize3DRequest authorize3d;

    private Authorize3DRequest prepareObject() {
        Authorize3DRequest authorize3d = new Authorize3DRequest();
        authorize3d.setCurrency(Currency.USD.getCurrency());
        return authorize3d;
    }

    private void setRequiredParams(Authorize3DRequest sale) {
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
    public void createAuthorize3D() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        authorize3d = mock(Authorize3DRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(authorize3d.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(authorize3d);
        assertEquals(client.execute(), authorize3d);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testAuthorize3D() throws MalformedURLException {

        // Authorize 3D
        when(authorize3d.setTransactionId(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setRemoteIp(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setUsage(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setGaming(isA(Boolean.class))).thenReturn(authorize3d);
        when(authorize3d.setMoto(isA(Boolean.class))).thenReturn(authorize3d);
        when(authorize3d.setAmount(isA(BigDecimal.class))).thenReturn(authorize3d);
        when(authorize3d.setCurrency(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setCardNumber(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setCardHolder(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setCvv(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setExpirationMonth(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setExpirationYear(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingPrimaryAddress(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingSecondaryAddress(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingFirstname(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingLastname(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingCity(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingCountry(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingZipCode(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setBillingState(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setCustomerEmail(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setCustomerPhone(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setNotificationUrl(isA(URL.class))).thenReturn(authorize3d);
        when(authorize3d.setReturnSuccessUrl(isA(URL.class))).thenReturn(authorize3d);
        when(authorize3d.setReturnFailureUrl(isA(URL.class))).thenReturn(authorize3d);
        when(authorize3d.setFXRateId(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setCrypto(isA(Boolean.class))).thenReturn(authorize3d);
        when(authorize3d.setRecurringType(isA(String.class))).thenReturn(authorize3d);
        when(authorize3d.setSchemeTokenized(isA(Boolean.class))).thenReturn(authorize3d);

        assertEquals(authorize3d.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), authorize3d);
        assertEquals(authorize3d.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00")), authorize3d);
        assertEquals(authorize3d.setCardNumber("4200000000000000").setCardHolder("PLAMEN PETROV").setCvv("123")
                .setExpirationMonth("02").setExpirationYear("2020"), authorize3d);
        assertEquals(authorize3d.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("New York").setBillingCountry("US")
                .setBillingZipCode("M4B1B3").setBillingState("CA"), authorize3d);
        assertEquals(authorize3d.setNotificationUrl(new URL("http://www.example.com/notification")), authorize3d);
        assertEquals(authorize3d.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), authorize3d);
        assertEquals(authorize3d.setFXRateId("123"), authorize3d);
        assertEquals(authorize3d.setCrypto(true), authorize3d);
        assertEquals(authorize3d.setRecurringType("initial"), authorize3d);
        assertEquals(authorize3d.setSchemeTokenized(Boolean.TRUE), authorize3d);

        verify(authorize3d).setTransactionId(uniqueId);
        verify(authorize3d).setRemoteIp("82.137.112.202");
        verify(authorize3d).setUsage("TICKETS");
        verify(authorize3d).setCurrency(Currency.USD.getCurrency());
        verify(authorize3d).setAmount(new BigDecimal("10.00"));
        verify(authorize3d).setCardNumber("4200000000000000");
        verify(authorize3d).setCardHolder("PLAMEN PETROV");
        verify(authorize3d).setCvv("123");
        verify(authorize3d).setExpirationMonth("02");
        verify(authorize3d).setExpirationYear("2020");
        verify(authorize3d).setBillingPrimaryAddress("Berlin");
        verify(authorize3d).setBillingSecondaryAddress("Berlin");
        verify(authorize3d).setBillingFirstname("Plamen");
        verify(authorize3d).setBillingLastname("Petrov");
        verify(authorize3d).setBillingCity("New York");
        verify(authorize3d).setBillingCountry("US");
        verify(authorize3d).setBillingZipCode("M4B1B3");
        verify(authorize3d).setBillingState("CA");
        verify(authorize3d).setNotificationUrl(new URL("http://www.example.com/notification"));
        verify(authorize3d).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(authorize3d).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(authorize3d).setFXRateId("123");
        verify(authorize3d).setCrypto(true);
        verify(authorize3d).setRecurringType("initial");
        verify(authorize3d).setSchemeTokenized(Boolean.TRUE);

        verifyNoMoreInteractions(authorize3d);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testAuthorize3DWithMissingParams() {

        clearRequiredParams();
        assertNull(authorize3d.setBillingCountry(null));
        verify(authorize3d).setBillingCountry(null);
        verifyNoMoreInteractions(authorize3d);

        verifyExecute();
    }

    @Test(expected = RegexException.class)
    public void testNegativeAmountError(){
        Authorize3DRequest authorize3D = prepareObject();
        authorize3D.setAmount(new BigDecimal("-22.00"));
        authorize3D.buildPaymentParams();
    }

    @Test
    public void testZeroAmount(){
        Authorize3DRequest authorize3D = prepareObject();
        BigDecimal amount = new BigDecimal("0.00");
        authorize3D.setAmount(new BigDecimal("0.00"));
        assertEquals(amount, authorize3D.getAmount());
        assertTrue(authorize3D.buildPaymentParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void testRecurrencyError(){
        Authorize3DRequest authorize3D = prepareObject();
        authorize3D.setRecurringType("ANY_TYPE");
        authorize3D.buildRecurringAttrParams();
    }

    @Test
    public void testMissingSchemeTokenizedParam(){
        Authorize3DRequest sale3D = prepareObject();
        setRequiredParams(sale3D);
        sale3D.setSchemeTokenized(null);
        assertThrows("scheme_tokenized Required param(s) are missing", RequiredParamsException.class, sale3D::toXML);
    }

    @Test
    public void testValidRequiredParam() {
        Authorize3DRequest sale3D = prepareObject();
        setRequiredParams(sale3D);
        String xml = sale3D.toXML();
        assertTrue(xml.contains("<scheme_tokenized>true</scheme_tokenized>"));
    }
}