package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.exceptions.AuthenticationException;
import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;

import com.emerchantpay.gateway.util.NodeWrapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GenesisClientTest {

	private SaleRequest sale;
	private GenesisClient client;
	private TransactionGateway transaction;
	private NodeWrapper response;

	@Before
	public void createMocks() {
		sale = mock(SaleRequest.class);
		client = mock(GenesisClient.class);
		transaction = mock(TransactionGateway.class);
		response = mock(NodeWrapper.class);
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

		assertEquals(client.debugMode(true), client);
		assertEquals(client.changeRequest(sale), client);
		assertEquals(client.getRequest(), sale);
		assertEquals(client.getTransactionType(), sale.getTransactionType());
		assertEquals(client.getResponse(), response);
		assertEquals(client.getTransaction(), transaction);
		assertEquals(client.execute(), sale);

		verify(client).debugMode(true);
		verify(client).changeRequest(sale);
		verify(client).getRequest();
		verify(client).getTransactionType();
		verify(client).getResponse();
		verify(client).getTransaction();
		verify(client).execute();

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
}