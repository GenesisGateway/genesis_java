package com.emerchantpay.gateway;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.model.Transaction;

public class TransactionGatewayTest {

	private TransactionGateway gateway;
	private TransactionResult<Transaction> result;
	private Transaction transaction;

	@Test
	public void parsePaymentResponse() {

		gateway = mock(TransactionGateway.class);
		result = mock(TransactionResult.class);
		transaction = mock(Transaction.class);

		when(gateway.getRequest()).thenReturn(result);
		when(result.getTransaction()).thenReturn(transaction);
		when(transaction.getStatus()).thenReturn("approved");

		assertEquals(gateway.getRequest(),result);
		assertEquals(result.getTransaction(), transaction);
		assertEquals(transaction.getStatus(), "approved");

		verify(gateway).getRequest();
		verify(result).getTransaction();
		verify(transaction).getStatus();
		
		verifyNoMoreInteractions(result);
	}
}