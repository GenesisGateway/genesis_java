package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.EarthportRequest;
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

public class EarthportTest {

    private GenesisClient client;
    private EarthportRequest earthport;

    private String uid;

    @Before
    public void createEarthport() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        earthport = mock(EarthportRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(earthport.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(earthport);
        assertEquals(client.execute(), earthport);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testEarthport() {

        // Earthport
        when(earthport.setTransactionId(isA(String.class))).thenReturn(earthport);
        when(earthport.setRemoteIp(isA(String.class))).thenReturn(earthport);
        when(earthport.setUsage(isA(String.class))).thenReturn(earthport);
        when(earthport.setCurrency(isA(String.class))).thenReturn(earthport);
        when(earthport.setAmount(isA(BigDecimal.class))).thenReturn(earthport);
        when(earthport.setCustomerEmail(isA(String.class))).thenReturn(earthport);
        when(earthport.setCustomerPhone(isA(String.class))).thenReturn(earthport);
        when(earthport.setAccountName(isA(String.class))).thenReturn(earthport);
        when(earthport.setBankName(isA(String.class))).thenReturn(earthport);
        when(earthport.setIBAN(isA(String.class))).thenReturn(earthport);
        when(earthport.setBIC(isA(String.class))).thenReturn(earthport);
        when(earthport.setAccountNumber(isA(String.class))).thenReturn(earthport);
        when(earthport.setBankCode(isA(String.class))).thenReturn(earthport);
        when(earthport.setBranchCode(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingPrimaryAddress(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingSecondaryAddress(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingFirstname(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingLastname(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingCity(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingCountry(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingZipCode(isA(String.class))).thenReturn(earthport);
        when(earthport.setBillingState(isA(String.class))).thenReturn(earthport);

        assertEquals(earthport.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS"), earthport);
        assertEquals(earthport.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")),
                earthport);
        assertEquals(earthport.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"), earthport);
        assertEquals(earthport.setAccountName("John Doe").setBankName("Barclays Bank PLC").setIBAN("DK5000400440116243")
                .setBIC("APMMDKKK").setAccountNumber("10352719").setBankCode("063").setBranchCode("169"), earthport);
        assertEquals(earthport.setBillingPrimaryAddress("14, Copenhagen").setBillingSecondaryAddress("24, Copenhagen")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov").setBillingCity("Copenhagen")
                .setBillingCountry(Country.Denmark.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("CH"), earthport);

        verify(earthport).setTransactionId(uid);
        verify(earthport).setRemoteIp("82.137.112.202");
        verify(earthport).setUsage("TICKETS");
        verify(earthport).setCurrency(Currency.EUR.getCurrency());
        verify(earthport).setAmount(new BigDecimal("2.00"));
        verify(earthport).setCustomerEmail("john.doe@emerchantpay.com");
        verify(earthport).setCustomerPhone("+55555555");
        verify(earthport).setAccountName("John Doe");
        verify(earthport).setBankName("Barclays Bank PLC");
        verify(earthport).setIBAN("DK5000400440116243");
        verify(earthport).setBIC("APMMDKKK");
        verify(earthport).setAccountNumber("10352719");
        verify(earthport).setBankCode("063");
        verify(earthport).setBranchCode("169");
        verify(earthport).setBillingPrimaryAddress("14, Copenhagen");
        verify(earthport).setBillingSecondaryAddress("24, Copenhagen");
        verify(earthport).setBillingFirstname("Plamen");
        verify(earthport).setBillingLastname("Petrov");
        verify(earthport).setBillingCity("Copenhagen");
        verify(earthport) .setBillingCountry(Country.Denmark.getCode());
        verify(earthport).setBillingZipCode("M4B1B3");
        verify(earthport).setBillingState("CH");
        verifyNoMoreInteractions(earthport);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testEarthportWithMissingParams() {

        clearRequiredParams();

        assertNull(earthport.setBillingCountry(null));
        verify(earthport).setBillingCountry(null);
        verifyNoMoreInteractions(earthport);

        verifyExecute();
    }
}