package com.emerchantpay.gateway.nonfinancial;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.nonfinancial.BlacklistRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class BlacklistTest {

    private GenesisClient client;
    private BlacklistRequest blacklist;

    @Before
    public void createBlacklist() {

        client = mock(GenesisClient.class);
        blacklist = mock(BlacklistRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(blacklist.setCardNumber(null)).thenThrow(exception);
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(blacklist);
        assertEquals(client.execute(), blacklist);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testBlacklist() {

        // Blacklist
        when(blacklist.setCardNumber(isA(String.class))).thenReturn(blacklist);
        when(blacklist.setTerminalToken(isA(String.class))).thenReturn(blacklist);

        assertEquals(blacklist.setCardNumber("4200000000000000"), blacklist);
        assertEquals(blacklist.setTerminalToken("abd30ed00f88f838c5d233cbb62b6da0b69267b4"), blacklist);

        verify(blacklist).setCardNumber("4200000000000000");
        verify(blacklist).setTerminalToken("abd30ed00f88f838c5d233cbb62b6da0b69267b4");
        verifyNoMoreInteractions(blacklist);

        verifyExecute();
    }

    @Test(expected = ApiException.class)
    public void testBlacklistWithMissingParams() {

        clearRequiredParams();
        assertNull(blacklist.setCardNumber(null));
        verify(blacklist).setCardNumber(null);
        verifyNoMoreInteractions(blacklist);

        verifyExecute();
    }
}
