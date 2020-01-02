package com.emerchantpay.gateway.api;

import com.emerchantpay.gateway.model.Consumer;
import com.emerchantpay.gateway.util.NodeWrapper;

import java.lang.reflect.InvocationTargetException;

public class ConsumerResult<T> {

	private Consumer consumer;
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

	public ConsumerResult() {
	}

	public ConsumerResult(NodeWrapper node, Class<T> klass) {
		this.consumer = new Consumer(node);
	}

	public Consumer getConsumer() {
		return consumer;
	}


	public T getTarget() {
		return target;
	}
}
