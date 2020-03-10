package com.emerchantpay.gateway.card.recurring;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSale3DRequest;
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
        when(initrecsale3d.setFXRateId(isA(String.class))).thenReturn(initrecsale3d);;

        assertEquals(initrecsale3d.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS"),
                initrecsale3d);
        assertEquals(initrecsale3d.setMoto(true), initrecsale3d);
        assertEquals(initrecsale3d.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency()),
                initrecsale3d);
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

        verify(initrecsale3d).setTransactionId(uniqueId.toString());
        verify(initrecsale3d).setRemoteIp("192.168.0.1");
        verify(initrecsale3d).setUsage("TICKETS");
        verify(initrecsale3d).setMoto(true);
        verify(initrecsale3d).setAmount(new BigDecimal("22.00"));
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
}