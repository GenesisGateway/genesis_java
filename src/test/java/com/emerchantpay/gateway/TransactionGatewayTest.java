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
        when(transaction.getRedirectUrl()).thenReturn("https://redirectURL.com");
        when(transaction.getRedirectUrlType()).thenReturn("3ds_v2_challenge");
        when(transaction.getThreedsMethodUrl()).thenReturn("https://staging.gate.emerchantpay.net/threeds/threeds_method");
        when(transaction.getThreedsMethodContinueUrl()).thenReturn("https://staging.gate.emerchantpay.net/threeds/threeds_method/1234");
        Transaction paymentTransaction = mock(Transaction.class);
        LinkedList<Transaction> paymentTransactions = new LinkedList<>();
        paymentTransactions.add(paymentTransaction);
        when(transaction.getPaymentTransactions()).thenReturn(paymentTransactions);

		assertEquals(gateway.getRequest(),result);
		assertEquals(result.getTransaction(), transaction);
		assertEquals(transaction.getStatus(), "approved");
        assertEquals(transaction.getPaymentTransactions(), paymentTransactions);
        assertEquals(transaction.getRedirectUrl(), "https://redirectURL.com");
        assertEquals(transaction.getRedirectUrlType(), "3ds_v2_challenge");
        assertEquals(transaction.getThreedsMethodUrl(), "https://staging.gate.emerchantpay.net/threeds/threeds_method");
        assertEquals(transaction.getThreedsMethodContinueUrl(), "https://staging.gate.emerchantpay.net/threeds/threeds_method/1234");

		verify(gateway).getRequest();
		verify(result).getTransaction();
		verify(transaction).getStatus();
        verify(transaction).getPaymentTransactions();
        verify(transaction).getRedirectUrl();
        verify(transaction).getRedirectUrlType();
        verify(transaction).getThreedsMethodUrl();
        verify(transaction).getThreedsMethodContinueUrl();

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