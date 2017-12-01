package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.sct.SCTPayoutRequest;
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

public class SCTPayoutTest {

    private String uidPayout;

    private GenesisClient client;
    private SCTPayoutRequest sctPayout;

    @Before
    public void createSCTPayout() {
        uidPayout = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        sctPayout = mock(SCTPayoutRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(sctPayout.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(sctPayout);
        assertEquals(client.execute(), sctPayout);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testSCTPayout() {

        // SCT Payout
        when(sctPayout.setTransactionId(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setRemoteIp(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setUsage(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setAmount(isA(BigDecimal.class))).thenReturn(sctPayout);
        when(sctPayout.setCurrency(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setCustomerEmail(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setCustomerPhone(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setIban(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBic(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingPrimaryAddress(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingSecondaryAddress(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingFirstname(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingLastname(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingCity(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingCountry(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingZipCode(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setBillingState(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingPrimaryAddress(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingSecondaryAddress(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingFirstname(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingLastname(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingCity(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingCountry(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingZipCode(isA(String.class))).thenReturn(sctPayout);
        when(sctPayout.setShippingState(isA(String.class))).thenReturn(sctPayout);

        assertEquals(sctPayout.setTransactionId(uidPayout).setRemoteIp("192.168.0.1").setUsage("TICKETS"), sctPayout);
        assertEquals(sctPayout.setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency()),
                sctPayout);
        assertEquals(sctPayout.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), sctPayout);
        assertEquals(sctPayout.setIban("DE09100100101234567894").setBic("PBNKDEFFXXX"), sctPayout);

        // Billing Address
        assertEquals(sctPayout.setBillingPrimaryAddress("New York").setBillingSecondaryAddress("Dallas")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("1000").setBillingState("NY"), sctPayout);

        // Shipping Address
        assertEquals(sctPayout.setShippingPrimaryAddress("New York").setShippingSecondaryAddress("Dallas")
                .setShippingFirstname("Plamen").setShippingLastname("Petrov")
                .setShippingCity("Berlin").setShippingCountry(Country.Germany.getCode())
                .setShippingZipCode("1000").setShippingState("NY"), sctPayout);

        verify(sctPayout).setTransactionId(uidPayout);
        verify(sctPayout).setRemoteIp("192.168.0.1");
        verify(sctPayout).setUsage("TICKETS");
        verify(sctPayout).setAmount(new BigDecimal("2.00"));
        verify(sctPayout).setCurrency(Currency.EUR.getCurrency());
        verify(sctPayout).setCustomerEmail("john@example.com");
        verify(sctPayout).setCustomerPhone("+55555555");
        verify(sctPayout).setIban("DE09100100101234567894");
        verify(sctPayout).setBic("PBNKDEFFXXX");

        // Billing Address
        verify(sctPayout).setBillingPrimaryAddress("New York");
        verify(sctPayout).setBillingSecondaryAddress("Dallas");
        verify(sctPayout).setBillingFirstname("Plamen");
        verify(sctPayout).setBillingLastname("Petrov");
        verify(sctPayout).setBillingCity("Berlin");
        verify(sctPayout).setBillingCountry(Country.Germany.getCode());
        verify(sctPayout).setBillingZipCode("1000");
        verify(sctPayout).setBillingState("NY");

        // Shipping Address
        verify(sctPayout).setShippingPrimaryAddress("New York");
        verify(sctPayout).setShippingSecondaryAddress("Dallas");
        verify(sctPayout).setShippingFirstname("Plamen");
        verify(sctPayout).setShippingLastname("Petrov");
        verify(sctPayout).setShippingCity("Berlin");
        verify(sctPayout).setShippingCountry(Country.Germany.getCode());
        verify(sctPayout).setShippingZipCode("1000");
        verify(sctPayout).setShippingState("NY");

        verifyNoMoreInteractions(sctPayout);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testSCTWithMissingParams() {

        clearRequiredParams();
        assertNull(sctPayout.setBillingCountry(null));
        verify(sctPayout).setBillingCountry(null);
        verifyNoMoreInteractions(sctPayout);

        verifyExecute();
    }
}