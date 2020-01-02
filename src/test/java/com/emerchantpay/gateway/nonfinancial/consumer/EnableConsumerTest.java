package com.emerchantpay.gateway.nonfinancial.consumer;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.consumer.EnableConsumerRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EnableConsumerTest {

    private GenesisClient client;
    private EnableConsumerRequest enableConsumerRequest;

    @Before
    public void stubEnableConsumer() throws MalformedURLException {
        client = mock(GenesisClient.class);
        enableConsumerRequest = new EnableConsumerRequest();
        enableConsumerRequest.setConsumerId("123456");
        enableConsumerRequest.setEmail("john@example.com");
        enableConsumerRequest.toXML();
    }

    public void verifyEnableConsumerExecute() {
        when(client.execute()).thenReturn(enableConsumerRequest);
        assertEquals(client.execute(), enableConsumerRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testEnableConsumerSuccess() {
        verifyEnableConsumerExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testEnableConsumerFailure() {
        enableConsumerRequest.setConsumerId("123456");
        enableConsumerRequest.setEmail(null);
        enableConsumerRequest.toXML();
        verifyEnableConsumerExecute();
    }
}