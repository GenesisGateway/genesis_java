package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.card.CreditRequest;
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

public class CreditTest {

    private String uniqueId;

    private GenesisClient client;
    private CreditRequest credit;

    @Before
    public void createCredit() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        credit = mock(CreditRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(credit.setReferenceId(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(credit);
        assertEquals(client.execute(), credit);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testCredit()  {

        // Credit
        when(credit.setTransactionId(isA(String.class))).thenReturn(credit);
        when(credit.setRemoteIp(isA(String.class))).thenReturn(credit);
        when(credit.setUsage(isA(String.class))).thenReturn(credit);
        when(credit.setCurrency(isA(String.class))).thenReturn(credit);
        when(credit.setAmount(isA(BigDecimal.class))).thenReturn(credit);
        when(credit.setReferenceId(isA(String.class))).thenReturn(credit);

        assertEquals(credit.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"), credit);
        assertEquals(credit.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")), credit);
        assertEquals(credit.setReferenceId("57b3a7b166ffe873d0a11863560b410c"), credit);

        verify(credit).setTransactionId(uniqueId);
        verify(credit).setRemoteIp("82.137.112.202");
        verify(credit).setUsage("TICKETS");
        verify(credit).setCurrency(Currency.USD.getCurrency());
        verify(credit).setAmount(new BigDecimal("2.00"));
        verify(credit).setReferenceId("57b3a7b166ffe873d0a11863560b410c");
        verifyNoMoreInteractions(credit);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testCreditWithMissingParams() {
        clearRequiredParams();

        assertNull(credit.setReferenceId(null));
        verify(credit).setReferenceId(null);
        verifyNoMoreInteractions(credit);

        verifyExecute();
    }
}
