package com.emerchantpay.gateway;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emerchantpay.gateway.api.exceptions.ResponseException;
import org.junit.Before;
import org.junit.Test;

import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.model.Transaction;

import java.util.LinkedList;

public class TransactionGatewayTest {

	private TransactionGateway gateway;
	private TransactionResult<Transaction> result;
	private Transaction transaction;

	@Before
	public void setup() {
		gateway = mock(TransactionGateway.class);
		result = mock(TransactionResult.class);
		transaction = mock(Transaction.class);
	}

	@Test
	public void parsePaymentSuccess() {
		when(gateway.getRequest()).thenReturn(result);
		when(result.getTransaction()).thenReturn(transaction);
		when(transaction.getStatus()).thenReturn("approved");
        Transaction paymentTransaction = mock(Transaction.class);
        LinkedList paymentTransactions = new LinkedList<>();
        paymentTransactions.add(paymentTransaction);
        when(transaction.getPaymentTransactions()).thenReturn(paymentTransactions);

		assertEquals(gateway.getRequest(),result);
		assertEquals(result.getTransaction(), transaction);
		assertEquals(transaction.getStatus(), "approved");
        assertEquals(transaction.getPaymentTransactions(), paymentTransactions);

		verify(gateway).getRequest();
		verify(result).getTransaction();
		verify(transaction).getStatus();
        verify(transaction).getPaymentTransactions();

		verifyNoMoreInteractions(result);
	}

	@Test(expected = ResponseException.class)
	public void parsePaymentFailure() {
        when(gateway.getRequest()).thenReturn(result);
        when(result.getTransaction()).thenReturn(transaction);
        when(transaction.getStatus()).thenReturn("error");
        when(result.getTransaction()).thenThrow(ResponseException.class);

        assertEquals(gateway.getRequest(),result);
        assertEquals(result.getTransaction(), transaction);
        assertEquals(transaction.getStatus(), "error");

        verify(gateway).getRequest();
        verify(result).getTransaction();
        verify(transaction).getStatus();

        verifyNoMoreInteractions(result);
	}
}