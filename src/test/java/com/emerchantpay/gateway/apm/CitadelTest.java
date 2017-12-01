package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.CitadelPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.CitadelPayOutRequest;
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

public class CitadelTest {


    private String uidPayIn;
    private String uidPayOut;

    private GenesisClient client;
    private CitadelPayInRequest citadelPayIn;
    private CitadelPayOutRequest citadelPayOut;

    @Before
    public void createCitadelPayin() {
        uidPayIn = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        citadelPayIn = mock(CitadelPayInRequest.class);
    }

    @Before
    public void createCitadelPayout() {
        uidPayOut = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        citadelPayOut = mock(CitadelPayOutRequest.class);
    }

    public void clearRequiredParams() {

        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(citadelPayIn.setBillingCountry(null)).thenThrow(exception);
        when(citadelPayOut.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyPayinExecute() {
        when(client.execute()).thenReturn(citadelPayIn);
        assertEquals(client.execute(), citadelPayIn);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyPayoutExecute() {
        when(client.execute()).thenReturn(citadelPayOut);
        assertEquals(client.execute(), citadelPayOut);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testCitadelPayin() throws MalformedURLException {

        // PayIn
        when(citadelPayIn.setTransactionId(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setRemoteIp(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setUsage(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setCurrency(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setAmount(isA(BigDecimal.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setCustomerEmail(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setCustomerPhone(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setReturnSuccessUrl(isA(URL.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setReturnFailureUrl(isA(URL.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setNotificationUrl(isA(URL.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setMerchantCustomerId(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingPrimaryAddress(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingSecondaryAddress(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingFirstname(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingLastname(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingCity(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingCountry(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingZipCode(isA(String.class))).thenReturn(citadelPayIn);
        when(citadelPayIn.setBillingState(isA(String.class))).thenReturn(citadelPayIn);

        assertEquals(citadelPayIn.setTransactionId(uidPayIn).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                citadelPayIn);
        assertEquals(citadelPayIn.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")),
                citadelPayIn);
        assertEquals( citadelPayIn.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"),
                citadelPayIn);
        assertEquals(citadelPayIn.setReturnSuccessUrl(new URL("https://www.gmail.com"))
                .setReturnFailureUrl(new URL("https://www.google.com")), citadelPayIn);
        assertEquals(citadelPayIn.setNotificationUrl(new URL("http://www.example.com/notification"))
                .setMerchantCustomerId("1534537"), citadelPayIn);
        assertEquals(citadelPayIn.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov").setBillingCity("Berlin")
                .setBillingCountry("DE").setBillingZipCode("M4B1B3").setBillingState("BE"), citadelPayIn);

        verify(citadelPayIn).setTransactionId(uidPayIn);
        verify(citadelPayIn).setRemoteIp("82.137.112.202");
        verify(citadelPayIn).setUsage("TICKETS");
        verify(citadelPayIn).setCurrency(Currency.EUR.getCurrency());
        verify(citadelPayIn).setAmount(new BigDecimal("2.00"));
        verify(citadelPayIn).setCustomerEmail("john.doe@emerchantpay.com");
        verify(citadelPayIn).setCustomerPhone("+55555555");
        verify(citadelPayIn).setReturnSuccessUrl(new URL("https://www.gmail.com"));
        verify(citadelPayIn).setReturnFailureUrl(new URL("https://www.google.com"));
        verify(citadelPayIn).setNotificationUrl(new URL("http://www.example.com/notification"));
        verify(citadelPayIn).setMerchantCustomerId("1534537");
        verify(citadelPayIn).setBillingPrimaryAddress("Berlin");
        verify(citadelPayIn).setBillingSecondaryAddress("Berlin");
        verify(citadelPayIn).setBillingFirstname("Plamen");
        verify(citadelPayIn).setBillingLastname("Petrov");
        verify(citadelPayIn).setBillingCity("Berlin");
        verify(citadelPayIn).setBillingCountry("DE");
        verify(citadelPayIn).setBillingZipCode("M4B1B3");
        verify(citadelPayIn).setBillingState("BE");
        verifyNoMoreInteractions(citadelPayIn);

        verifyPayinExecute();
    }

    @Test(expected = ApiException.class)
    public void testCitadelPayinWithMissingParams() {
        clearRequiredParams();

        assertNull(citadelPayIn.setBillingCountry(null));
        verify(citadelPayIn).setBillingCountry(null);
        verifyNoMoreInteractions(citadelPayIn);

        verifyPayinExecute();
    }

    @Test
    public void testCitadelPayout() {

        // PayOut
        when(citadelPayOut.setTransactionId(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setRemoteIp(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setUsage(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setCurrency(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setAmount(isA(BigDecimal.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setCustomerEmail(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setCustomerPhone(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setHolderName(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setAccountNumber(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setIBAN(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setSwiftCode(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBankName(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBankCity(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBankCode(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBranchCode(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBranchCheckDigit(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingPrimaryAddress(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingSecondaryAddress(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingFirstname(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingLastname(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingCity(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingCountry(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingZipCode(isA(String.class))).thenReturn(citadelPayOut);
        when(citadelPayOut.setBillingState(isA(String.class))).thenReturn(citadelPayOut);

        assertEquals(citadelPayOut.setTransactionId(uidPayOut).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                citadelPayOut);
        assertEquals(citadelPayOut.setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency()),
                citadelPayOut);
        assertEquals(citadelPayOut.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"),
                citadelPayOut);
        assertEquals(citadelPayOut.setHolderName("John Doe").setAccountNumber("1534537").setIBAN("DE89370400440532013000")
                .setSwiftCode("DEUTDEDB899").setBankName("Royal Bank").setBankCity("Berlin")
                .setBankCode("123567").setBranchCode("123567").setBranchCheckDigit("02"), citadelPayOut);
        assertEquals(citadelPayOut.setBillingPrimaryAddress("Toronto").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE"), citadelPayOut);


        verify(citadelPayOut).setTransactionId(uidPayOut);
        verify(citadelPayOut).setRemoteIp("82.137.112.202");
        verify(citadelPayOut).setUsage("TICKETS");
        verify(citadelPayOut).setAmount(new BigDecimal("2.00"));
        verify(citadelPayOut).setCurrency(Currency.EUR.getCurrency());
        verify(citadelPayOut).setCustomerEmail("john.doe@emerchantpay.com");
        verify(citadelPayOut).setCustomerPhone("+55555555");
        verify(citadelPayOut).setHolderName("John Doe");
        verify(citadelPayOut).setAccountNumber("1534537");
        verify(citadelPayOut).setIBAN("DE89370400440532013000");
        verify(citadelPayOut).setSwiftCode("DEUTDEDB899");
        verify(citadelPayOut).setBankName("Royal Bank");
        verify(citadelPayOut).setBankCity("Berlin");
        verify(citadelPayOut).setBankCode("123567");
        verify(citadelPayOut).setBranchCode("123567");
        verify(citadelPayOut).setBranchCheckDigit("02");
        verify(citadelPayOut).setBillingPrimaryAddress("Toronto");
        verify(citadelPayOut).setBillingSecondaryAddress("Berlin");
        verify(citadelPayOut).setBillingFirstname("Plamen");
        verify(citadelPayOut).setBillingLastname("Petrov");
        verify(citadelPayOut).setBillingCity("Berlin");
        verify(citadelPayOut).setBillingCountry("DE");
        verify(citadelPayOut).setBillingZipCode("M4B1B3");
        verify(citadelPayOut).setBillingState("BE");
        verifyNoMoreInteractions(citadelPayOut);

        verifyPayoutExecute();
    }


    @Test(expected = ApiException.class)
    public void testCitadelPayoutWithMissingParams() {
        clearRequiredParams();

        assertNull(citadelPayOut.setBillingCountry(null));
        verify(citadelPayOut).setBillingCountry(null);
        verifyNoMoreInteractions(citadelPayOut);

        verifyPayoutExecute();
    }
}