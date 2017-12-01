package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.PaySecPayoutRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.PaySecRequest;
import com.emerchantpay.gateway.util.Country;
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

public class PaySecRequestsTest {

    private GenesisClient client;
    private PaySecRequest paysec;
    private PaySecPayoutRequest paysecPayout;

    private String uid;
    private String uidPayout;

    @Before
    public void createPaySec() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        paysec = mock(PaySecRequest.class);
    }

    @Before
    public void createPaySecPayout() {
        uidPayout = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        paysecPayout = mock(PaySecPayoutRequest.class);
    }



    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(paysec.setBillingCountry(null)).thenThrow(exception);
        when(paysecPayout.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyPaysecExecute() {
        when(client.execute()).thenReturn(paysec);
        assertEquals(client.execute(), paysec);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyPaysecPayoutExecute() {
        when(client.execute()).thenReturn(paysecPayout);
        assertEquals(client.execute(), paysecPayout);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testPaySec() throws MalformedURLException {

        // PaySec
        when(paysec.setTransactionId(isA(String.class))).thenReturn(paysec);
        when(paysec.setRemoteIp(isA(String.class))).thenReturn(paysec);
        when(paysec.setUsage(isA(String.class))).thenReturn(paysec);
        when(paysec.setAmount(isA(BigDecimal.class))).thenReturn(paysec);
        when(paysec.setCurrency(isA(String.class))).thenReturn(paysec);
        when(paysec.setCustomerEmail(isA(String.class))).thenReturn(paysec);
        when(paysec.setCustomerPhone(isA(String.class))).thenReturn(paysec);
        when(paysec.setNotificationUrl(isA(URL.class))).thenReturn(paysec);
        when(paysec.setReturnSuccessUrl(isA(URL.class))).thenReturn(paysec);
        when(paysec.setReturnFailureUrl(isA(URL.class))).thenReturn(paysec);
        when(paysec.setBillingPrimaryAddress(isA(String.class))).thenReturn(paysec);
        when(paysec.setBillingSecondaryAddress(isA(String.class))).thenReturn(paysec);
        when(paysec.setBillingFirstname(isA(String.class))).thenReturn(paysec);
        when(paysec.setBillingLastname(isA(String.class))).thenReturn(paysec);
        when(paysec.setBillingCity(isA(String.class))).thenReturn(paysec);
        when(paysec.setBillingCountry(isA(String.class))).thenReturn(paysec);
        when(paysec.setBillingZipCode(isA(String.class))).thenReturn(paysec);
        when(paysec.setBillingState(isA(String.class))).thenReturn(paysec);

        assertEquals(paysec.setTransactionId(uid).setRemoteIp("94.26.28.135").setUsage("TICKETS"), paysec);
        assertEquals(paysec.setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("10.00")), paysec);
        assertEquals(paysec.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"), paysec);
        assertEquals(paysec.setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure")), paysec);
        assertEquals(paysec.setNotificationUrl(new URL("https://example.com/notification")), paysec);
        assertEquals(paysec.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("Beijing").setBillingCountry(Country.China.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BJ"), paysec);


        verify(paysec).setTransactionId(uid);
        verify(paysec).setRemoteIp("94.26.28.135");
        verify(paysec).setUsage("TICKETS");
        verify(paysec).setCurrency(Currency.CNY.getCurrency());
        verify(paysec).setAmount(new BigDecimal("10.00"));
        verify(paysec).setCustomerEmail("john.doe@emerchantpay.com");
        verify(paysec).setCustomerPhone("+55555555");
        verify(paysec).setReturnSuccessUrl(new URL("https://example.com/return_success"));
        verify(paysec).setReturnFailureUrl(new URL("https://example.com/return_failure"));
        verify(paysec).setNotificationUrl(new URL("https://example.com/notification"));
        verify(paysec).setBillingPrimaryAddress("First Avenue");
        verify(paysec).setBillingSecondaryAddress("Second Avenue");
        verify(paysec).setBillingFirstname("John");
        verify(paysec).setBillingLastname("Doe");
        verify(paysec).setBillingCity("Beijing");
        verify(paysec).setBillingCountry(Country.China.getCode());
        verify(paysec).setBillingZipCode("M4B1B3");
        verify(paysec).setBillingState("BJ");
        verifyNoMoreInteractions(paysec);

        verifyPaysecExecute();
    }

    @Test(expected = ApiException.class)
    public void testPaySecWithMissingParams() {

        clearRequiredParams();
        assertNull(paysec.setBillingCountry(null));
        verify(paysec).setBillingCountry(null);
        verifyNoMoreInteractions(paysec);

        verifyPaysecExecute();
    }

    @Test
    public void testPaySecPayout() throws MalformedURLException {

        // PaySec Payout
        when(paysecPayout.setTransactionId(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setRemoteIp(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setUsage(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setAmount(isA(BigDecimal.class))).thenReturn(paysecPayout);
        when(paysecPayout.setCurrency(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setCustomerEmail(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setCustomerPhone(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBankCode(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBankName(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBankBranch(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBankAccountName(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBankAccountNumber(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setNotificationUrl(isA(URL.class))).thenReturn(paysecPayout);
        when(paysecPayout.setReturnSuccessUrl(isA(URL.class))).thenReturn(paysecPayout);
        when(paysecPayout.setReturnFailureUrl(isA(URL.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingPrimaryAddress(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingSecondaryAddress(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingFirstname(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingLastname(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingCity(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingCountry(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingZipCode(isA(String.class))).thenReturn(paysecPayout);
        when(paysecPayout.setBillingState(isA(String.class))).thenReturn(paysecPayout);

        assertEquals(paysecPayout.setTransactionId(uidPayout).setRemoteIp("94.26.28.135").setUsage("TICKETS"),
                paysecPayout);
        assertEquals(paysecPayout.setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("600.00")),
                paysecPayout);
        assertEquals(paysecPayout.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"),
                paysecPayout);
        assertEquals(paysecPayout.setBankCode("BOC").setBankName("深圳发展银行").setBankBranch("Test Bank Branch")
                .setBankAccountName("Test Bank Account Name").setBankAccountNumber("1234123412341234")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure")), paysecPayout);
        assertEquals(paysecPayout.setNotificationUrl(new URL("https://example.com/notification")), paysecPayout);
        assertEquals(paysecPayout.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity(Country.China.getCode()).setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BJ"), paysecPayout);

        verify(paysecPayout).setTransactionId(uidPayout);
        verify(paysecPayout).setRemoteIp("94.26.28.135");
        verify(paysecPayout).setUsage("TICKETS");
        verify(paysecPayout).setCurrency(Currency.CNY.getCurrency());
        verify(paysecPayout).setAmount(new BigDecimal("600.00"));
        verify(paysecPayout).setCustomerEmail("john.doe@emerchantpay.com");
        verify(paysecPayout).setCustomerPhone("+55555555");
        verify(paysecPayout).setCustomerEmail("john.doe@emerchantpay.com");
        verify(paysecPayout).setCustomerPhone("+55555555");
        verify(paysecPayout).setBankCode("BOC");
        verify(paysecPayout).setBankName("深圳发展银行");
        verify(paysecPayout).setBankBranch("Test Bank Branch");
        verify(paysecPayout).setBankAccountName("Test Bank Account Name");
        verify(paysecPayout).setBankAccountNumber("1234123412341234");
        verify(paysecPayout).setReturnSuccessUrl(new URL("https://example.com/return_success"));
        verify(paysecPayout).setReturnFailureUrl(new URL("https://example.com/return_failure"));
        verify(paysecPayout).setNotificationUrl(new URL("https://example.com/notification"));
        verify(paysecPayout).setBillingPrimaryAddress("First Avenue");
        verify(paysecPayout).setBillingSecondaryAddress("Berlin");
        verify(paysecPayout).setBillingFirstname("John");
        verify(paysecPayout).setBillingLastname("Doe");
        verify(paysecPayout).setBillingCity(Country.China.getCode());
        verify(paysecPayout).setBillingCountry("DE");
        verify(paysecPayout).setBillingZipCode("M4B1B3");
        verify(paysecPayout).setBillingState("BJ");
        verifyNoMoreInteractions(paysecPayout);

        verifyPaysecPayoutExecute();
    }

    @Test(expected = ApiException.class)
    public void testPaySecPayoutWithMissingParams() {

        clearRequiredParams();
        assertNull(paysecPayout.setBillingCountry(null));
        verify(paysecPayout).setBillingCountry(null);
        verifyNoMoreInteractions(paysecPayout);

        verifyPaysecPayoutExecute();
    }
}