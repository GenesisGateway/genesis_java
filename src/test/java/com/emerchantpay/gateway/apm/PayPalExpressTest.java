package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.PayPalExpressRequest;
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

public class PayPalExpressTest {

    private String uniqueId;

    private GenesisClient client;
    private PayPalExpressRequest paypalExpress;

    @Before
    public void createPayPal() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        paypalExpress = mock(PayPalExpressRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(paypalExpress.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(paypalExpress);
        assertEquals(client.execute(), paypalExpress);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testPayPalExpress() throws MalformedURLException {

        // Checkout
        when(paypalExpress.setTransactionId(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setRemoteIp((isA(String.class)))).thenReturn(paypalExpress);
        when(paypalExpress.setUsage(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setCurrency(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setAmount(isA(BigDecimal.class))).thenReturn(paypalExpress);
        when(paypalExpress.setReturnSuccessUrl(isA(URL.class))).thenReturn(paypalExpress);
        when(paypalExpress.setReturnFailureUrl(isA(URL.class))).thenReturn(paypalExpress);
        when(paypalExpress.setCustomerEmail(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setCustomerPhone(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingPrimaryAddress(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingSecondaryAddress(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingFirstname(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingLastname(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingCity(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingCountry(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingZipCode(isA(String.class))).thenReturn(paypalExpress);
        when(paypalExpress.setBillingState(isA(String.class))).thenReturn(paypalExpress);

        assertEquals(paypalExpress.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                paypalExpress);
        assertEquals(paypalExpress.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")),
                paypalExpress);
        assertEquals(paypalExpress.setCustomerEmail("john@example.com").setCustomerPhone("555555"), paypalExpress);
        assertEquals(paypalExpress.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), paypalExpress);
        assertEquals( paypalExpress.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE").setBillingZipCode("M4B1B3")
                .setBillingState("BE"), paypalExpress);

        verify(paypalExpress).setTransactionId(uniqueId);
        verify(paypalExpress).setRemoteIp("82.137.112.202");
        verify(paypalExpress).setUsage("TICKETS");
        verify(paypalExpress).setCurrency(Currency.EUR.getCurrency());
        verify(paypalExpress).setAmount(new BigDecimal("2.00"));
        verify(paypalExpress).setCustomerEmail("john@example.com");
        verify(paypalExpress).setCustomerPhone("555555");
        verify(paypalExpress).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(paypalExpress).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(paypalExpress).setBillingPrimaryAddress("Berlin");
        verify(paypalExpress).setBillingSecondaryAddress("Berlin");
        verify(paypalExpress).setBillingFirstname("Plamen");
        verify(paypalExpress).setBillingLastname("Petrov");
        verify(paypalExpress).setBillingCity("Berlin");
        verify(paypalExpress).setBillingCountry("DE");
        verify(paypalExpress).setBillingZipCode("M4B1B3");
        verify(paypalExpress).setBillingState("BE");
        verifyNoMoreInteractions(paypalExpress);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testPayPalUWithMissingParams() {

        clearRequiredParams();

        assertNull(paypalExpress.setBillingCountry(null));
        verify(paypalExpress).setBillingCountry(null);
        verifyNoMoreInteractions(paypalExpress);

        verifyExecute();
    }
}