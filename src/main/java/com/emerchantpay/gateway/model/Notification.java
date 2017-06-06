package com.emerchantpay.gateway.model;

import com.emerchantpay.gateway.util.NodeWrapper;

public class Notification {
	private String rawDocument;
	private String uniqueId;

	public Notification(NodeWrapper node) {
		this.rawDocument = node.toString();
		this.uniqueId = node.findString("unique_id");
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public String getDocument() {
		return rawDocument;
	}
}
