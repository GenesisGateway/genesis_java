package com.emerchantpay.gateway.nonfinancial.consumer;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.nonfinancial.consumer.GetConsumerCardsRequest;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GetConsumerCardsTest {

    private GenesisClient client;
    private GetConsumerCardsRequest getConsumerCardsRequest;

    @Before
    public void stubGetConsumerCards() {
        client = mock(GenesisClient.class);
        getConsumerCardsRequest = new GetConsumerCardsRequest();
        getConsumerCardsRequest.setConsumerId("123456");
        getConsumerCardsRequest.setEmail("john@example.com");
        getConsumerCardsRequest.toXML();
    }

    public void verifyUpdateConsumerExecute() {
        when(client.execute()).thenReturn(getConsumerCardsRequest);
        assertEquals(client.execute(), getConsumerCardsRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testGetConsumerCardsSuccess() {
        verifyUpdateConsumerExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testGetConsumerCardsFailure() {
        getConsumerCardsRequest.setConsumerId(null);
        getConsumerCardsRequest.setEmail("john@example.com");
        getConsumerCardsRequest.toXML();
        verifyUpdateConsumerExecute();
    }
}