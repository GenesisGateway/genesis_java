package com.emerchantpay.gateway.card.recurring;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.RecurringSaleRequest;
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

public class RecurringSaleTest {

    private String uniqueId;

    private GenesisClient client;
    private RecurringSaleRequest recurringSale;

    @Before
    public void createInitRecurring() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        recurringSale = mock(RecurringSaleRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(recurringSale.setReferenceId(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(recurringSale);
        assertEquals(client.execute(), recurringSale);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRecurring() {
        // Recurring
        when(recurringSale.setTransactionId(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setUsage(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setRemoteIp(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setAmount(isA(BigDecimal.class))).thenReturn(recurringSale);
        when(recurringSale.setCurrency(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setReferenceId(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setMoto(isA(Boolean.class))).thenReturn(recurringSale);

        assertEquals(recurringSale.setTransactionId(uniqueId).setUsage("TICKETS").setRemoteIp("192.168.0.1"),
                recurringSale);
        assertEquals(recurringSale.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00")),
                recurringSale);
        assertEquals(recurringSale.setReferenceId("2ee4287e67971380ef7f97d5743bb523"), recurringSale);
        assertEquals(recurringSale.setMoto(true), recurringSale);

        verify(recurringSale).setTransactionId(uniqueId);
        verify(recurringSale).setUsage("TICKETS");
        verify(recurringSale).setRemoteIp("192.168.0.1");
        verify(recurringSale).setCurrency(Currency.USD.getCurrency());
        verify(recurringSale).setAmount(new BigDecimal("10.00"));
        verify(recurringSale).setReferenceId("2ee4287e67971380ef7f97d5743bb523");
        verify(recurringSale).setMoto(true);
        verifyNoMoreInteractions(recurringSale);

        verifyExecute();
    }

    @Test
    public void testRecurringWithoutCurrency() {
        // Recurring without currency
        when(recurringSale.setTransactionId(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setUsage(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setRemoteIp(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setAmount(isA(BigDecimal.class))).thenReturn(recurringSale);
        when(recurringSale.setCurrency(isA(String.class))).thenReturn(recurringSale);
        when(recurringSale.setReferenceId(isA(String.class))).thenReturn(recurringSale);

        assertEquals(recurringSale.setTransactionId(uniqueId).setUsage("TICKETS").setRemoteIp("192.168.0.1"),
                recurringSale);
        assertEquals(recurringSale.setAmount(new BigDecimal("10.00")),
                recurringSale);
        assertEquals(recurringSale.setReferenceId("2ee4287e67971380ef7f97d5743bb523"), recurringSale);

        verify(recurringSale).setTransactionId(uniqueId);
        verify(recurringSale).setUsage("TICKETS");
        verify(recurringSale).setRemoteIp("192.168.0.1");
        verify(recurringSale).setAmount(new BigDecimal("10.00"));
        verify(recurringSale).setReferenceId("2ee4287e67971380ef7f97d5743bb523");
        verifyNoMoreInteractions(recurringSale);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testInitRecurringWithMissingParams() {
        clearRequiredParams();

        assertNull(recurringSale.setReferenceId(null));
        verify(recurringSale).setReferenceId(null);
        verifyNoMoreInteractions(recurringSale);

        verifyExecute();
    }
}
