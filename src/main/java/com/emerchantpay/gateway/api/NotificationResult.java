package com.emerchantpay.gateway.api;

import java.lang.reflect.InvocationTargetException;

import com.emerchantpay.gateway.model.Notification;
import com.emerchantpay.gateway.model.WPFNotification;
import com.emerchantpay.gateway.util.NodeWrapper;

public class NotificationResult<T> {

	private Notification notification;
	private WPFNotification wpfNotification;
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

	public NotificationResult() {
	}

	public NotificationResult(NodeWrapper node, Class<T> klass) {
		if (node.isSuccess()) {
			this.target = newInstanceFromNode(klass, node);
		} else {
			this.errors = new ValidationErrors(node);

			NodeWrapper notificationNode = node.findFirst("echo_notification");
			if (notificationNode != null) {
				this.notification = new Notification(notificationNode);
				this.wpfNotification = new WPFNotification(notificationNode);
			}
		}

		this.notification = new Notification(node);
		this.wpfNotification = new WPFNotification(node);
	}

	public Notification getNotification() {
		return notification;
	}

	public WPFNotification getWPFNotification() {
		return wpfNotification;
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
