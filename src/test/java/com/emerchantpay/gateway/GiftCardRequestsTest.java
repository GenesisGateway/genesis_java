package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.giftcards.FashionchequeRequest;
import com.emerchantpay.gateway.api.requests.financial.giftcards.GiftCardRequest;
import com.emerchantpay.gateway.api.requests.financial.giftcards.IntersolveRequest;
import com.emerchantpay.gateway.api.requests.financial.giftcards.TcsRequest;
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

public class GiftCardRequestsTest {

    private String fashionChequeUId;
    private String intersolveUId;
    private String tcsUId;

    private GenesisClient client;
    private FashionchequeRequest fashioncheque;
    private IntersolveRequest intersolve;
    private TcsRequest tcs;

    @Before
    public void createFashioncheque() throws MalformedURLException {
        fashionChequeUId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        fashioncheque = mock(FashionchequeRequest.class);
    }

    @Before
    public void createIntersolve() {
        intersolveUId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        intersolve = mock(IntersolveRequest.class);
    }

    @Before
    public void createTCS() {
        tcsUId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        tcs = mock(TcsRequest.class);
    }

    @Test
    public void testFashioncheque() throws MalformedURLException {

        // Fashioncheque
        when(fashioncheque.setTransactionId(isA(String.class))).thenReturn(fashioncheque);
        when(fashioncheque.setRemoteIp(isA(String.class))).thenReturn(fashioncheque);
        when(fashioncheque.setUsage(isA(String.class))).thenReturn(fashioncheque);
        when(fashioncheque.setAmount(isA(BigDecimal.class))).thenReturn(fashioncheque);
        when(fashioncheque.setCurrency(isA(String.class))).thenReturn(fashioncheque);
        when(fashioncheque.setCardNumber(isA(String.class))).thenReturn(fashioncheque);
        when(fashioncheque.setCvv(isA(String.class))).thenReturn(fashioncheque);

        setDynamicDescriptorStubs(fashioncheque);

        assertEquals(fashioncheque.setTransactionId(fashionChequeUId)
                .setRemoteIp("82.137.112.202").setUsage("TICKETS"), fashioncheque);
        assertEquals(fashioncheque.setCurrency(Currency.EUR.getCurrency())
                .setAmount(new BigDecimal("2.00")), fashioncheque);
        assertEquals(fashioncheque.setCardNumber("6046425114923545647").setCvv("673815"), fashioncheque);
        assertEquals(fashioncheque.setDynamicMerchantName("John Doe").setDynamicMerchantName("New York"),
                fashioncheque);

        verify(fashioncheque).setTransactionId(fashionChequeUId);
        verify(fashioncheque).setRemoteIp("82.137.112.202");
        verify(fashioncheque).setUsage("TICKETS");
        verify(fashioncheque).setCurrency(Currency.EUR.getCurrency());
        verify(fashioncheque).setAmount(new BigDecimal("2.00"));
        verify(fashioncheque).setCardNumber("6046425114923545647");
        verify(fashioncheque).setCvv("673815");
        verify(fashioncheque).setDynamicMerchantName("John Doe");
        verify(fashioncheque).setDynamicMerchantName("New York");
        verifyNoMoreInteractions(fashioncheque);

        verifyClientExecute(fashioncheque);
    }

    @Test
    public void testIntersolve() throws MalformedURLException {

        // Intersolve
        when(intersolve.setTransactionId(isA(String.class))).thenReturn(intersolve);
        when(intersolve.setRemoteIp(isA(String.class))).thenReturn(intersolve);
        when(intersolve.setUsage(isA(String.class))).thenReturn(intersolve);
        when(intersolve.setAmount(isA(BigDecimal.class))).thenReturn(intersolve);
        when(intersolve.setCurrency(isA(String.class))).thenReturn(intersolve);
        when(intersolve.setCardNumber(isA(String.class))).thenReturn(intersolve);
        when(intersolve.setCvv(isA(String.class))).thenReturn(intersolve);

        setDynamicDescriptorStubs(intersolve);

        assertEquals(intersolve.setTransactionId(intersolveUId)
                .setRemoteIp("82.137.112.202").setUsage("TICKETS"), intersolve);
        assertEquals(intersolve.setCurrency(Currency.EUR.getCurrency())
                .setAmount(new BigDecimal("2.00")), intersolve);
        assertEquals(intersolve.setCardNumber("7000001163991388834").setCvv("673815"), intersolve);
        assertEquals(intersolve.setDynamicMerchantName("John Doe").setDynamicMerchantName("New York"),
                intersolve);

        verify(intersolve).setTransactionId(intersolveUId);
        verify(intersolve).setRemoteIp("82.137.112.202");
        verify(intersolve).setUsage("TICKETS");
        verify(intersolve).setCurrency(Currency.EUR.getCurrency());
        verify(intersolve).setAmount(new BigDecimal("2.00"));
        verify(intersolve).setCardNumber("7000001163991388834");
        verify(intersolve).setCvv("673815");
        verify(intersolve).setDynamicMerchantName("John Doe");
        verify(intersolve).setDynamicMerchantName("New York");
        verifyNoMoreInteractions(intersolve);

        verifyClientExecute(intersolve);
    }

    @Test
    public void testTCS() throws MalformedURLException {

        // TCS
        when(tcs.setTransactionId(isA(String.class))).thenReturn(tcs);
        when(tcs.setRemoteIp(isA(String.class))).thenReturn(tcs);
        when(tcs.setUsage(isA(String.class))).thenReturn(tcs);
        when(tcs.setAmount(isA(BigDecimal.class))).thenReturn(tcs);
        when(tcs.setCurrency(isA(String.class))).thenReturn(tcs);
        when(tcs.setCardNumber(isA(String.class))).thenReturn(tcs);
        when(tcs.setCvv(isA(String.class))).thenReturn(tcs);

        setDynamicDescriptorStubs(tcs);

        assertEquals(tcs.setTransactionId(tcsUId)
                .setRemoteIp("82.137.112.202").setUsage("TICKETS"), tcs);
        assertEquals(tcs.setCurrency(Currency.EUR.getCurrency())
                .setAmount(new BigDecimal("2.00")), tcs);
        assertEquals(tcs.setCardNumber("6046425117120757123").setCvv("121839"), tcs);
        assertEquals(tcs.setDynamicMerchantName("John Doe").setDynamicMerchantName("New York"),
                tcs);

        verify(tcs).setTransactionId(tcsUId);
        verify(tcs).setRemoteIp("82.137.112.202");
        verify(tcs).setUsage("TICKETS");
        verify(tcs).setCurrency(Currency.EUR.getCurrency());
        verify(tcs).setAmount(new BigDecimal("2.00"));
        verify(tcs).setCardNumber("6046425117120757123");
        verify(tcs).setCvv("121839");
        verify(tcs).setDynamicMerchantName("John Doe");
        verify(tcs).setDynamicMerchantName("New York");
        verifyNoMoreInteractions(tcs);

        verifyClientExecute(tcs);
    }

    @Test(expected = ApiException.class)
    public void testFashionchequeWithMissingParams() {
        clearRequiredParams();
        assertNull(fashioncheque.setCardNumber(null));
        verify(fashioncheque).setCardNumber(null);
        verifyNoMoreInteractions(fashioncheque);

        verifyClientExecute(fashioncheque);
    }

    @Test(expected = ApiException.class)
    public void testIntersolveWithMissingParams() {
        clearRequiredParams();
        assertNull(intersolve.setCardNumber(null));
        verify(intersolve).setCardNumber(null);
        verifyNoMoreInteractions(intersolve);

        verifyClientExecute(intersolve);
    }

    @Test(expected = ApiException.class)
    public void testTCSWithMissingParams() {
        clearRequiredParams();
        assertNull(tcs.setCardNumber(null));
        verify(tcs).setCardNumber(null);
        verifyNoMoreInteractions(tcs);

        verifyClientExecute(tcs);
    }

    private void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(fashioncheque.setCardNumber(null)).thenThrow(exception);
        when(intersolve.setCardNumber(null)).thenThrow(exception);
        when(tcs.setCardNumber(null)).thenThrow(exception);
    }

    private void verifyClientExecute(GiftCardRequest request) {
        when(client.execute()).thenReturn(request);
        assertEquals(client.execute(), request);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    private void setDynamicDescriptorStubs(GiftCardRequest request) {
        when(request.setDynamicMerchantName(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantCity(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantCountry(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantState(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantZipCode(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantAddress(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantUrl(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantPhone(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantServiceCity(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantServiceCountry(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantServiceState(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantServiceZipCode(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantServicePhone(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantGeoCoordinates(isA(String.class))).thenReturn(request);
        when(request.setDynamicMerchantServiceGeoCoordinates(isA(String.class))).thenReturn(request);
        when(request.setDynamicSubMerchantId(isA(String.class))).thenReturn(request);
    }
}