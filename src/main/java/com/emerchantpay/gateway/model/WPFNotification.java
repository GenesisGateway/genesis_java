package com.emerchantpay.gateway.model;

import com.emerchantpay.gateway.util.NodeWrapper;

public class WPFNotification {
	private String rawDocument;
	private String uniqueId;

	public WPFNotification(NodeWrapper node) {
		this.rawDocument = node.toString();
		this.uniqueId = node.findString("wpf_unique_id");
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public String getDocument() {
		return rawDocument;
	}
}
