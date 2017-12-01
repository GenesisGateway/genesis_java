package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.sdd.*;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class SDDRequestsTest {

    private String uidSale;
    private String uidRecurring;
    private String uidinitRecurring;
    private String uidRefund;

    private GenesisClient client;

    private SDDSaleRequest sddSale;
    private SDDInitRecurringSaleRequest sddInitRecurring;
    private SDDRecurringSaleRequest sddRecurring;
    private SDDRefundRequest sddRefund;

    @Before
    public void createSSDSale() {
        uidSale = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sddSale = mock(SDDSaleRequest.class);
    }

    @Before
    public void createInitRecurring() {
        uidinitRecurring = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sddInitRecurring = mock(SDDInitRecurringSaleRequest.class);
    }

    @Before
    public void createRecurringSale() {
        uidRecurring = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sddRecurring = mock(SDDRecurringSaleRequest.class);
    }

    @Before
    public void createSDDRefund() {
        uidRefund = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sddRefund = mock(SDDRefundRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(sddSale.setBillingCountry(null)).thenThrow(exception);
        when(sddInitRecurring.setBillingCountry(null)).thenThrow(exception);
        when(sddRecurring.setTransactionId(null)).thenThrow(exception);
        when(sddRefund.setTransactionId(null)).thenThrow(exception);
    }

    public void verifySaleExecute() {
        when(client.execute()).thenReturn(sddSale);
        assertEquals(client.execute(), sddSale);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyInitRecurringExecute() {
        when(client.execute()).thenReturn(sddInitRecurring);
        assertEquals(client.execute(), sddInitRecurring);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyRecurringExecute() {
        when(client.execute()).thenReturn(sddRecurring);
        assertEquals(client.execute(), sddRecurring);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyRefundExecute() {
        when(client.execute()).thenReturn(sddRefund);
        assertEquals(client.execute(), sddRefund);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSale() {
        // SDD Sale
        when(sddSale.setTransactionId(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setRemoteIp(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setUsage(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setAmount(isA(BigDecimal.class))).thenReturn(sddSale);
        when(sddSale.setCurrency(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setIban(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setBic(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setBillingFirstname(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setBillingLastname(isA(String.class))).thenReturn(sddSale);
        when(sddSale.setBillingCountry(isA(String.class))).thenReturn(sddSale);

        assertEquals(sddSale.setTransactionId(uidSale).setRemoteIp("94.26.28.135").setUsage("TICKETS"), sddSale);
        assertEquals(sddSale.setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency()), sddSale);
        assertEquals(sddSale.setIban("DE09100100101234567894").setBic("PBNKDEFFXXX"), sddSale);
        assertEquals(sddSale.setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCountry(Country.Germany.getCode()), sddSale);

        verify(sddSale).setTransactionId(uidSale);
        verify(sddSale).setRemoteIp("94.26.28.135");
        verify(sddSale).setUsage("TICKETS");
        verify(sddSale).setAmount(new BigDecimal("2.00"));
        verify(sddSale).setCurrency(Currency.EUR.getCurrency());
        verify(sddSale).setIban("DE09100100101234567894");
        verify(sddSale).setBic("PBNKDEFFXXX");
        verify(sddSale).setBillingFirstname("Plamen");
        verify(sddSale).setBillingLastname("Petrov");
        verify(sddSale).setBillingCountry(Country.Germany.getCode());
        verifyNoMoreInteractions(sddSale);

        verifySaleExecute();
    }

    @Test
    public void testInitRecurring() {
        // SDD Init Recurring Sale
        when(sddInitRecurring.setTransactionId(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setRemoteIp(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setUsage(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setAmount(isA(BigDecimal.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setCurrency(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setIban(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setBic(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setBillingFirstname(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setBillingLastname(isA(String.class))).thenReturn(sddInitRecurring);
        when(sddInitRecurring.setBillingCountry(isA(String.class))).thenReturn(sddInitRecurring);

        assertEquals(sddInitRecurring.setTransactionId(uidinitRecurring).setRemoteIp("192.168.0.1")
                .setUsage("TICKETS"), sddInitRecurring);
        assertEquals(sddInitRecurring.setAmount(new BigDecimal("2.00"))
                        .setCurrency(Currency.EUR.getCurrency()), sddInitRecurring);
        assertEquals(sddInitRecurring.setIban("DE09100100101234567894")
                .setBic("PBNKDEFFXXX"), sddInitRecurring);
        assertEquals(sddInitRecurring.setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCountry(Country.Germany.getCode()), sddInitRecurring);

        verify(sddInitRecurring).setTransactionId(uidinitRecurring);
        verify(sddInitRecurring).setRemoteIp("192.168.0.1");
        verify(sddInitRecurring).setUsage("TICKETS");
        verify(sddInitRecurring).setAmount(new BigDecimal("2.00"));
        verify(sddInitRecurring).setCurrency(Currency.EUR.getCurrency());
        verify(sddInitRecurring).setIban("DE09100100101234567894");
        verify(sddInitRecurring).setBic("PBNKDEFFXXX");
        verify(sddInitRecurring).setBillingFirstname("Plamen");
        verify(sddInitRecurring).setBillingLastname("Petrov");
        verify(sddInitRecurring).setBillingCountry(Country.Germany.getCode());
        verifyNoMoreInteractions(sddInitRecurring);

        verifyInitRecurringExecute();
    }

    @Test
    public void testSDDRecurringSale() {
        // SDD Recurring Sale
        when(sddRecurring.setTransactionId(isA(String.class))).thenReturn(sddRecurring);
        when(sddRecurring.setUsage(isA(String.class))).thenReturn(sddRecurring);
        when(sddRecurring.setAmount(isA(BigDecimal.class))).thenReturn(sddRecurring);
        when(sddRecurring.setCurrency(isA(String.class))).thenReturn(sddRecurring);
        when(sddRecurring.setReferenceId(isA(String.class))).thenReturn(sddRecurring);

        assertEquals(sddRecurring.setTransactionId(uidRecurring), sddRecurring);
        assertEquals(sddRecurring.setUsage("TICKETS"), sddRecurring);
        assertEquals(sddRecurring.setAmount(new BigDecimal("1.00"))
                .setCurrency(Currency.EUR.getCurrency()), sddRecurring);
        assertEquals(sddRecurring.setReferenceId("c3fe2428802a2d3a0b6a17a64ab9bc06"), sddRecurring);

        verify(sddRecurring).setTransactionId(uidRecurring);
        verify(sddRecurring).setUsage("TICKETS");
        verify(sddRecurring).setAmount(new BigDecimal("1.00"));
        verify(sddRecurring).setCurrency(Currency.EUR.getCurrency());
        verify(sddRecurring).setReferenceId("c3fe2428802a2d3a0b6a17a64ab9bc06");
        verifyNoMoreInteractions(sddRecurring);

        verifyRecurringExecute();
    }

    @Test
    public void testSDDRefund() {
        // SDD Refund
        when(sddRefund.setTransactionId(isA(String.class))).thenReturn(sddRefund);
        when(sddRefund.setUsage(isA(String.class))).thenReturn(sddRefund);
        when(sddRefund.setAmount(isA(BigDecimal.class))).thenReturn(sddRefund);
        when(sddRefund.setCurrency(isA(String.class))).thenReturn(sddRefund);
        when(sddRefund.setReferenceId(isA(String.class))).thenReturn(sddRefund);;

        assertEquals(sddRefund.setTransactionId(uidRefund), sddRefund);
        assertEquals(sddRefund.setUsage("TICKETS"), sddRefund);
        assertEquals(sddRefund.setAmount(new BigDecimal("1.00"))
                .setCurrency(Currency.EUR.getCurrency()), sddRefund);
        assertEquals(sddRefund.setReferenceId("c3fe2428802a2d3a0b6a17a64ab9bc06"), sddRefund);

        verify(sddRefund).setTransactionId(uidRefund);
        verify(sddRefund).setUsage("TICKETS");
        verify(sddRefund).setAmount(new BigDecimal("1.00"));
        verify(sddRefund).setCurrency(Currency.EUR.getCurrency());
        verify(sddRefund).setReferenceId("c3fe2428802a2d3a0b6a17a64ab9bc06");
        verifyNoMoreInteractions(sddRefund);
    }

    @Test(expected = ApiException.class)
    public void testSDDSaleWithMissingParams() {

        clearRequiredParams();
        assertNull(sddSale.setBillingCountry(null));
        verify(sddSale).setBillingCountry(null);
        verifyNoMoreInteractions(sddSale);

        verifySaleExecute();
    }

    @Test(expected = ApiException.class)
    public void testSDDInitRecurringWithMissingParams() {

        clearRequiredParams();
        assertNull(sddInitRecurring.setBillingCountry(null));
        verify(sddInitRecurring).setBillingCountry(null);
        verifyNoMoreInteractions(sddInitRecurring);

        verifyInitRecurringExecute();
    }

    @Test(expected = ApiException.class)
    public void testSDDRecurringWithMissingParams() {

        clearRequiredParams();
        assertNull(sddRecurring.setTransactionId(null));
        verify(sddRecurring).setTransactionId(null);
        verifyNoMoreInteractions(sddRecurring);

        verifyRecurringExecute();
    }

    @Test(expected = ApiException.class)
    public void testSDDRefundWithMissingParams() {

        clearRequiredParams();
        assertNull(sddRefund.setTransactionId(null));
        verify(sddRefund).setTransactionId(null);
        verifyNoMoreInteractions(sddRefund);

        verifyRefundExecute();
    }
}