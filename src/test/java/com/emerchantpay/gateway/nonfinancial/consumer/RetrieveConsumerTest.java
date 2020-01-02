package com.emerchantpay.gateway.nonfinancial.consumer;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.requests.nonfinancial.consumer.RetrieveConsumerRequest;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RetrieveConsumerTest {

    private GenesisClient client;
    private RetrieveConsumerRequest retrieveConsumerRequest;

    @Before
    public void stubRetrieveConsumer() {
        client = mock(GenesisClient.class);
        retrieveConsumerRequest = new RetrieveConsumerRequest();
        retrieveConsumerRequest.setEmail("john@example.com");
        retrieveConsumerRequest.toXML();
    }

    public void verifyRetrieveConsumerExecute() {
        when(client.execute()).thenReturn(retrieveConsumerRequest);
        assertEquals(client.execute(), retrieveConsumerRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRetrieveConsumerSuccess() {
        verifyRetrieveConsumerExecute();
    }

    @Test(expected = GenesisException.class)
    public void testRetrieveConsumerFailure() {
        retrieveConsumerRequest.setConsumerId(null);
        retrieveConsumerRequest.setEmail(null);
        retrieveConsumerRequest.toXML();
        verifyRetrieveConsumerExecute();
    }
}