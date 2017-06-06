package com.emerchantpay.gateway.api;

import java.lang.reflect.InvocationTargetException;

import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.NodeWrapper;

public class TransactionResult<T> {

	private Transaction transaction;
	private ValidationErrors errors;
	private T target;

	public static <T> T newInstanceFromNode(Class<T> klass, NodeWrapper node) {
		Throwable cause = null;
		try {
			return klass.getConstructor(NodeWrapper.class).newInstance(node);
		} catch (InstantiationException e) {
			cause = e;
		} catch (IllegalAccessException e) {
			cause = e;
		} catch (InvocationTargetException e) {
			cause = e;
		} catch (NoSuchMethodException e) {
			cause = e;
		}

		throw new IllegalArgumentException("Unknown class: " + klass, cause);
	}

	public TransactionResult() {
	}

	public TransactionResult(NodeWrapper node, Class<T> klass) {
		if (node.isSuccess()) {
			this.target = newInstanceFromNode(klass, node);
		} else {
			this.errors = new ValidationErrors(node);

			NodeWrapper transactionNode = node.findFirst("payment_response");
			if (transactionNode != null) {
				this.transaction = new Transaction(transactionNode);
			}
		}

		this.transaction = new Transaction(node);
	}

	public Transaction getTransaction() {

		return transaction;
	}

	public ValidationErrors getErrors() {
		return errors;
	}

	public T getTarget() {
		return target;
	}

	public boolean isSuccess() {
		return errors == null;
	}
}
