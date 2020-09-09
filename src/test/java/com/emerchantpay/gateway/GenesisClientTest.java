package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.exceptions.AuthenticationException;
import com.emerchantpay.gateway.api.exceptions.DeprecatedMethodException;
import com.emerchantpay.gateway.api.exceptions.LimitsException;
import com.emerchantpay.gateway.api.requests.financial.apm.AbnIDealRequest;
import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;

import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.NodeWrapper;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GenesisClientTest {

    private SaleRequest sale;
    private AbnIDealRequest abnIDealRequest;
    private GenesisClient client;
    private TransactionGateway transaction;
    private NodeWrapper response;
    private ConsumerGateway consumer;
    private Configuration configuration;
    private String uid = new StringUtils().generateUID();

    @Before
    public void createMocks() {
        sale = mock(SaleRequest.class);
        abnIDealRequest = mock(AbnIDealRequest.class);
        client = mock(GenesisClient.class);
        transaction = mock(TransactionGateway.class);
        response = mock(NodeWrapper.class);
        consumer = mock(ConsumerGateway.class);
        configuration = mock(Configuration.class);
    }

    @Test
    public void executeRequest() {

        when(client.debugMode(isA(Boolean.class))).thenReturn(client);
        when(client.changeRequest(isA(Request.class))).thenReturn(client);
        when(client.getRequest()).thenReturn(sale);
        when(client.getTransactionType()).thenCallRealMethod();
        when(client.getResponse()).thenReturn(response);
        when(client.getTransaction()).thenReturn(transaction);
        when(client.execute()).thenReturn(sale);

        when(client.getAsyncExecute()).thenReturn(true);
        when(client.setAsyncExecute(isA(Boolean.class))).thenReturn(client);
        when(client.addRequest(isA(Request.class))).thenReturn(client);
        when(client.addRequest(isA(Request.class), isA(Configuration.class))).thenReturn(client);
        when(client.getTransaction(isA(String.class))).thenReturn(transaction);
        when(client.getConsumer(isA(String.class))).thenReturn(consumer);
        when(client.getResponse(isA(String.class))).thenReturn(response);

        assertEquals(client.debugMode(true), client);
        assertEquals(client.changeRequest(sale), client);
        assertEquals(client.getRequest(), sale);
        assertEquals(client.getTransactionType(), sale.getTransactionType());
        assertEquals(client.getResponse(), response);
        assertEquals(client.getTransaction(), transaction);
        assertEquals(client.execute(), sale);

        assertEquals(true, client.getAsyncExecute());
        assertEquals(client, client.setAsyncExecute(false));
        assertEquals(client, client.addRequest(sale));
        assertEquals(client, client.addRequest(sale, configuration));
        assertEquals(transaction, client.getTransaction(uid));
        assertEquals(consumer, client.getConsumer(uid));
        assertEquals(response, client.getResponse(uid));

        verify(client).debugMode(true);
        verify(client).changeRequest(sale);
        verify(client).getRequest();
        verify(client).getTransactionType();
        verify(client).getResponse();
        verify(client).getTransaction();
        verify(client).execute();

        verify(client).getAsyncExecute();
        verify(client).setAsyncExecute(false);
        verify(client).addRequest(sale);
        verify(client).addRequest(sale, configuration);
        verify(client).getTransaction(uid);
        verify(client).getConsumer(uid);
        verify(client).getResponse(uid);

        verifyNoMoreInteractions(client);
    }

    @Test(expected = AuthenticationException.class)
    public void executeRequestWithWrongCredentials() {

        when(client.debugMode(isA(Boolean.class))).thenReturn(client);
        when(client.changeRequest(isA(Request.class))).thenReturn(client);
        when(client.execute()).thenThrow(AuthenticationException.class);

        assertEquals(client.debugMode(true), client);
        assertEquals(client.changeRequest(sale), client);
        assertEquals(client.execute(), sale);


        verify(client).debugMode(true);
        verify(client).changeRequest(sale);
        verify(client).execute();

        verifyNoMoreInteractions(client);
    }

    @Test(expected = DeprecatedMethodException.class)
    public void executeDepricatedMethod() {

        when(client.debugMode(isA(Boolean.class))).thenReturn(client);
        when(client.changeRequest(isA(Request.class))).thenReturn(client);
        when(client.getRequest()).thenReturn(abnIDealRequest);
        when(client.getTransactionType()).thenCallRealMethod();
        when(client.getResponse()).thenReturn(response);
        when(client.getTransaction()).thenReturn(transaction);
        when(client.execute()).thenThrow(DeprecatedMethodException.class);

        assertEquals(client.debugMode(true), client);
        assertEquals(client.changeRequest(abnIDealRequest), client);
        assertEquals(client.getRequest(), abnIDealRequest);
        assertEquals(client.getTransactionType(), abnIDealRequest.getTransactionType());
        assertEquals(client.getResponse(), response);
        assertEquals(client.getTransaction(), transaction);
        assertEquals(client.execute(), new DeprecatedMethodException());

        verify(client).debugMode(true);
        verify(client).changeRequest(abnIDealRequest);
        verify(client).getRequest();
        verify(client).getTransactionType();
        verify(client).getResponse();
        verify(client).getTransaction();
        verify(client).execute();

        verifyNoMoreInteractions(client);
    }

    @Test(expected = LimitsException.class)
    public void maxRequestsNumberException() {
        List<Request> requestList = Collections.nCopies(client.getMaxRequestNumber() + 1, sale);
        GenesisClient client = new GenesisClient(configuration, requestList);
    }
}