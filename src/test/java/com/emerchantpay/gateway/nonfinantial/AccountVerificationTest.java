package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.nonfinancial.AccountVerificationRequest;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


public class AccountVerificationTest {

    private String uniqueId;

    private GenesisClient client;
    private AccountVerificationRequest accountVerification;

    @Before
    public void createAuthorize3D()  {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        accountVerification = mock(AccountVerificationRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(accountVerification.setCardNumber(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(accountVerification);
        assertEquals(client.execute(), accountVerification);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testAccountVerification() {

        // Account Verification
        when(accountVerification.setTransactionId(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setRemoteIp(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setUsage(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setMoto(isA(Boolean.class))).thenReturn(accountVerification);
        when(accountVerification.setCardNumber(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setCardHolder(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setCvv(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setExpirationMonth(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setExpirationYear(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setCustomerEmail(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setCustomerPhone(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingPrimaryAddress(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingSecondaryAddress(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingFirstname(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingLastname(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingCity(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingCountry(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingZipCode(isA(String.class))).thenReturn(accountVerification);
        when(accountVerification.setBillingState(isA(String.class))).thenReturn(accountVerification);

        assertEquals(accountVerification.setTransactionId(uniqueId.toString())
                .setRemoteIp("192.168.0.1").setUsage("TICKETS"), accountVerification);
        assertEquals(accountVerification.setMoto(true).setCardHolder("JOHN DOE").setCardNumber("4200000000000000")
                .setExpirationMonth("02").setExpirationYear("2020").setCvv("123"), accountVerification);
        assertEquals(accountVerification.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555"),
                accountVerification);
        assertEquals(accountVerification.setBillingPrimaryAddress("Address1").setBillingSecondaryAddress("Address2")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("New York").setBillingCountry("US")
                .setBillingZipCode("1000").setBillingState("NY"), accountVerification);

        verify(accountVerification).setTransactionId(uniqueId.toString());
        verify(accountVerification).setRemoteIp("192.168.0.1");
        verify(accountVerification).setUsage("TICKETS");
        verify(accountVerification).setMoto(true);
        verify(accountVerification).setCardHolder("JOHN DOE");
        verify(accountVerification).setCardNumber("4200000000000000");
        verify(accountVerification).setExpirationMonth("02");
        verify(accountVerification).setExpirationYear("2020");
        verify(accountVerification).setCvv("123");
        verify(accountVerification).setCustomerEmail("john@example.com");
        verify(accountVerification).setCustomerPhone("+5555555555");
        verify(accountVerification).setBillingPrimaryAddress("Address1");
        verify(accountVerification).setBillingSecondaryAddress("Address2");
        verify(accountVerification).setBillingFirstname("John");
        verify(accountVerification).setBillingLastname("Doe");
        verify(accountVerification).setBillingCity("New York");
        verify(accountVerification).setBillingCountry("US");
        verify(accountVerification).setBillingZipCode("1000");
        verify(accountVerification).setBillingState("NY");
        verifyNoMoreInteractions(accountVerification);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testAccountVerificationWithMissingParams() {

        clearRequiredParams();
        assertNull(accountVerification.setCardNumber(null));
        verify(accountVerification).setCardNumber(null);
        verifyNoMoreInteractions(accountVerification);

        verifyExecute();
    }
}