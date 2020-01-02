package com.emerchantpay.gateway.nonfinancial.consumer;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.consumer.DisableConsumerRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DisableConsumerTest {

    private GenesisClient client;
    private DisableConsumerRequest disableConsumerRequest;

    @Before
    public void stubDisableConsumer() throws MalformedURLException {
        client = mock(GenesisClient.class);
        disableConsumerRequest = new DisableConsumerRequest();
        disableConsumerRequest.setConsumerId("123456");
        disableConsumerRequest.setEmail("john@example.com");
        disableConsumerRequest.toXML();
    }

    public void verifyDisableConsumerExecute() {
        when(client.execute()).thenReturn(disableConsumerRequest);
        assertEquals(client.execute(), disableConsumerRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testDisableConsumerSuccess() {
        verifyDisableConsumerExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testDisableConsumerFailure() {
        disableConsumerRequest.setConsumerId(null);
        disableConsumerRequest.toXML();
        verifyDisableConsumerExecute();
    }
}