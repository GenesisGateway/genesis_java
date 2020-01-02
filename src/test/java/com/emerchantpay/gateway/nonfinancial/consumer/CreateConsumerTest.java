package com.emerchantpay.gateway.nonfinancial.consumer;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.consumer.CreateConsumerRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateConsumerTest {

    private GenesisClient client;
    private CreateConsumerRequest createConsumerRequest;

    @Before
    public void stubCreateConsumer() {
        client = mock(GenesisClient.class);
        createConsumerRequest = new CreateConsumerRequest();
        createConsumerRequest.setEmail("john@example.com");
        createConsumerRequest.setBillingCountry("US");
        createConsumerRequest.toXML();
    }

    public void verifyCreateConsumerExecute() {
        when(client.execute()).thenReturn(createConsumerRequest);
        assertEquals(client.execute(), createConsumerRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testCreateConsumerSuccess() {
        verifyCreateConsumerExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testCreateConsumerFailure() {
        createConsumerRequest.setEmail(null);
        createConsumerRequest.toXML();
        verifyCreateConsumerExecute();
    }
}