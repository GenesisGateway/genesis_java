package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.constants.PaymentMethods;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.PProRequest;
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

public class PProPayTest {

    private String uniqueId;

    private GenesisClient client;
    private PProRequest ppro;

    @Before
    public void createPPro() {

        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        ppro = mock(PProRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(ppro.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(ppro);
        assertEquals(client.execute(), ppro);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testPPro() throws MalformedURLException {

        // PPro
        when(ppro.setTransactionId(isA(String.class))).thenReturn(ppro);
        when(ppro.setRemoteIp(isA(String.class))).thenReturn(ppro);
        when(ppro.setUsage(isA(String.class))).thenReturn(ppro);
        when(ppro.setCurrency(isA(String.class))).thenReturn(ppro);
        when(ppro.setAmount(isA(BigDecimal.class))).thenReturn(ppro);
        when(ppro.setCustomerEmail(isA(String.class))).thenReturn(ppro);
        when(ppro.setCustomerPhone(isA(String.class))).thenReturn(ppro);
        when(ppro.setReturnSuccessUrl(isA(URL.class))).thenReturn(ppro);
        when(ppro.setReturnFailureUrl(isA(URL.class))).thenReturn(ppro);
        when(ppro.setBIC(isA(String.class))).thenReturn(ppro);
        when(ppro.setIBAN(isA(String.class))).thenReturn(ppro);
        when(ppro.setPaymentType(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingPrimaryAddress(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingSecondaryAddress(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingZipCode(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingFirstname(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingLastname(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingCity(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingCountry(isA(String.class))).thenReturn(ppro);
        when(ppro.setBillingState(isA(String.class))).thenReturn(ppro);

        assertEquals(ppro.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), ppro);
        assertEquals(ppro.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")), ppro);
        assertEquals(ppro.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), ppro);
        assertEquals(ppro.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), ppro);
        assertEquals(ppro.setBIC("GENODETT488").setIBAN("DE07444488880123456789"), ppro);
        assertEquals(ppro.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE"), ppro);
        assertEquals(ppro.setPaymentType(PaymentMethods.GIRO_PAY), ppro);

        verify(ppro).setTransactionId(uniqueId);
        verify(ppro).setRemoteIp("82.137.112.202");
        verify(ppro).setUsage("TICKETS");
        verify(ppro).setPaymentType(PaymentMethods.GIRO_PAY);
        verify(ppro).setCurrency(Currency.EUR.getCurrency());
        verify(ppro).setAmount(new BigDecimal("2.00"));
        verify(ppro).setCustomerEmail("john@example.com");
        verify(ppro).setCustomerPhone("+55555555");
        verify(ppro).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(ppro).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(ppro).setBIC("GENODETT488");
        verify(ppro).setIBAN("DE07444488880123456789");
        verify(ppro).setBillingPrimaryAddress("Berlin");
        verify(ppro).setBillingSecondaryAddress("Berlin");
        verify(ppro).setBillingFirstname("Plamen");
        verify(ppro).setBillingLastname("Petrov");
        verify(ppro).setBillingCity("Berlin");
        verify(ppro).setBillingCountry("DE");
        verify(ppro).setBillingZipCode("M4B1B3");
        verify(ppro).setBillingState("BE");
        verifyNoMoreInteractions(ppro);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testPProWithMissingParams() {
        clearRequiredParams();

        assertNull(ppro.setBillingCountry(null));
        verify(ppro).setBillingCountry(null);
        verifyNoMoreInteractions(ppro);

        verifyExecute();
    }
}