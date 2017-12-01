package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.Banks;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVYeePayRequest;
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

public class PBVTest {

    private GenesisClient client;
    private PBVSaleRequest pbvSale;
    private PBVYeePayRequest pbvYeepay;

    private String uidSale;
    private String uidYeepay;

    @Before
    public void createSale() throws MalformedURLException {
        uidSale = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        pbvSale = mock(PBVSaleRequest.class);
    }

    @Before
    public void createYeePay() {
        uidYeepay = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        pbvYeepay = mock(PBVYeePayRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(pbvSale.setBillingCountry(null)).thenThrow(exception);
        when(pbvYeepay.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifySaleExecute() {
        when(client.execute()).thenReturn(pbvSale);
        assertEquals(client.execute(), pbvSale);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyYeepayExecute() {
        when(client.execute()).thenReturn(pbvYeepay);
        assertEquals(client.execute(), pbvYeepay);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSale() throws MalformedURLException {
        // Sale
        when(pbvSale.setTransactionId(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setRemoteIp(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setUsage(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setCurrency(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setAmount(isA(BigDecimal.class))).thenReturn(pbvSale);
        when(pbvSale.setCustomerEmail(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setCustomerPhone(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setCardNumber(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setCardHolder(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setCvv(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setExpirationMonth(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setExpirationYear(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBirthDate(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingPrimaryAddress(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingSecondaryAddress(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingZipCode(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingFirstname(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingLastname(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingCity(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingCountry(isA(String.class))).thenReturn(pbvSale);
        when(pbvSale.setBillingState(isA(String.class))).thenReturn(pbvSale);

        assertEquals(pbvSale.setTransactionId(uidSale).setRemoteIp("82.137.112.202").setUsage("TICKETS"), pbvSale);
        assertEquals(pbvSale.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")), pbvSale);
        assertEquals(pbvSale.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), pbvSale);
        assertEquals(pbvSale.setCardHolder("PLAMEN PETROV").setExpirationMonth("05").setExpirationYear("2020")
                .setCardNumber("4200000000000000").setCvv("123").setBirthDate("24-04-1988"), pbvSale);
        assertEquals(pbvSale.setBillingPrimaryAddress("New York").setBillingSecondaryAddress("New York")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("New York").setBillingCountry("US")
                .setBillingZipCode("N4C1C3").setBillingState("NY"), pbvSale);

        verify(pbvSale).setTransactionId(uidSale);
        verify(pbvSale).setTransactionId(uidSale);
        verify(pbvSale).setRemoteIp("82.137.112.202");
        verify(pbvSale).setUsage("TICKETS");
        verify(pbvSale).setCurrency(Currency.USD.getCurrency());
        verify(pbvSale).setAmount(new BigDecimal("2.00"));
        verify(pbvSale).setCustomerEmail("john@example.com");
        verify(pbvSale).setCustomerPhone("+55555555");
        verify(pbvSale).setCardHolder("PLAMEN PETROV");
        verify(pbvSale).setExpirationMonth("05");
        verify(pbvSale).setExpirationYear("2020");
        verify(pbvSale).setCardNumber("4200000000000000");
        verify(pbvSale).setCvv("123");
        verify(pbvSale).setBirthDate("24-04-1988");
        verify(pbvSale).setBillingPrimaryAddress("New York");
        verify(pbvSale).setBillingSecondaryAddress("New York");
        verify(pbvSale).setBillingFirstname("Plamen");
        verify(pbvSale).setBillingLastname("Petrov");
        verify(pbvSale).setBillingCity("New York");
        verify(pbvSale).setBillingCountry("US");
        verify(pbvSale).setBillingZipCode("N4C1C3");
        verify(pbvSale).setBillingState("NY");
        verifyNoMoreInteractions(pbvSale);

        verifySaleExecute();
    }

    @Test(expected = ApiException.class)
    public void testSaleWithMissingParams() {

        clearRequiredParams();

        assertNull(pbvSale.setBillingCountry(null));
        verify(pbvSale).setBillingCountry(null);
        verifyNoMoreInteractions(pbvSale);

        verifySaleExecute();
    }

    @Test
    public void testYeePay() throws MalformedURLException {

        // YeePay
        when(pbvYeepay.setTransactionId(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setRemoteIp(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setUsage(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setCurrency(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setAmount(isA(BigDecimal.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setCustomerEmail(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setCustomerPhone(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setProductName(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setProductCategory(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setCustomerId(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setCustomerBankId(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setCustomerName(isA(String.class))).thenReturn(pbvYeepay);
        when(pbvYeepay.setBankAccountNumber(isA(String.class))).thenReturn(pbvYeepay);

        assertEquals(pbvYeepay.setTransactionId(uidYeepay).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                pbvYeepay);
        assertEquals(pbvYeepay.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")),
                pbvYeepay);
        assertEquals(pbvYeepay.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), pbvYeepay);
        assertEquals(pbvYeepay.setProductName("Interstellar").setProductCategory("Movie")
                .setCustomerId("120104198302030841").setCustomerBankId(Banks.ICBC)
                .setCustomerName("李珊珊").setBankAccountNumber("6222020302063077036"), pbvYeepay);

        verify(pbvYeepay).setTransactionId(uidYeepay);
        verify(pbvYeepay).setRemoteIp("82.137.112.202");
        verify(pbvYeepay).setUsage("TICKETS");
        verify(pbvYeepay).setCurrency(Currency.USD.getCurrency());
        verify(pbvYeepay).setAmount(new BigDecimal("2.00"));
        verify(pbvYeepay).setCustomerEmail("john@example.com");
        verify(pbvYeepay).setCustomerPhone("+55555555");
        verify(pbvYeepay).setProductName("Interstellar");
        verify(pbvYeepay).setProductCategory("Movie");
        verify(pbvYeepay).setCustomerId("120104198302030841");
        verify(pbvYeepay).setCustomerBankId(Banks.ICBC);
        verify(pbvYeepay).setCustomerName("李珊珊");
        verify(pbvYeepay).setBankAccountNumber("6222020302063077036");
        verifyNoMoreInteractions(pbvYeepay);

        verifyYeepayExecute();
    }

    @Test(expected = ApiException.class)
    public void testYeepayWithMissingParams() {

        clearRequiredParams();

        assertNull(pbvYeepay.setBillingCountry(null));
        verify(pbvYeepay).setBillingCountry(null);
        verifyNoMoreInteractions(pbvYeepay);

        verifyYeepayExecute();
    }
}