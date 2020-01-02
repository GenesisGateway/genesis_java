package com.emerchantpay.gateway.nonfinancial.consumer;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.consumer.UpdateConsumerRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateConsumerTest {

    private GenesisClient client;
    private UpdateConsumerRequest updateConsumerRequest;

    @Before
    public void stubUpdateConsumer() {
        client = mock(GenesisClient.class);
        updateConsumerRequest = new UpdateConsumerRequest();
        updateConsumerRequest.setConsumerId("123456");
        updateConsumerRequest.setEmail("john@example.com");
        updateConsumerRequest.setBillingCountry("US");
        updateConsumerRequest.toXML();
    }

    public void verifyUpdateConsumerExecute() {
        when(client.execute()).thenReturn(updateConsumerRequest);
        assertEquals(client.execute(), updateConsumerRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testUpdateConsumerSuccess() {
        verifyUpdateConsumerExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testUpdateConsumerFailure() {
        updateConsumerRequest.setConsumerId(null);
        updateConsumerRequest.setEmail(null);
        updateConsumerRequest.toXML();
        verifyUpdateConsumerExecute();
    }
}