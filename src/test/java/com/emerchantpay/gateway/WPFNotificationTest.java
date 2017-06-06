package com.emerchantpay.gateway;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.util.Configuration;
import org.junit.Before;
import org.junit.Test;

import com.emerchantpay.gateway.util.SHA512Hasher;

import static org.junit.Assert.assertEquals;

public class WPFNotificationTest {

	private Configuration configuration;

	private List<Map.Entry<String, Object>> elements;
	private HashMap<String, Object> mappedParams;

	private WPFNotificationGateway notification = new WPFNotificationGateway();

	@Before
	public void createNotification() throws MalformedURLException, UnsupportedEncodingException, NoSuchAlgorithmException {

		this.configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
		this.configuration.setPassword("TEST123");

		notification.setNotificationURL(new URL("https://example.com"))
				.setUniqueId("98361e223e596fa88ff63ee8c5325638")
				.setSignature(SHA512Hasher.SHA512("98361e223e596fa88ff63ee8c5325638" + configuration.getPassword()))
				.setStatus("approved");
	}

	@Test
	public void testNotification() throws UnsupportedEncodingException, NoSuchAlgorithmException {

		mappedParams = new HashMap<String, Object>();

		elements = notification.getElements();

		for (int i = 0; i < elements.size(); i++) {
			mappedParams.put(elements.get(i).getKey(), notification.getElements().get(i).getValue());
		}

		assertEquals(mappedParams.get("payment_transaction_unique_id"), "98361e223e596fa88ff63ee8c5325638");
		assertEquals(mappedParams.get("signature"), SHA512Hasher.SHA512("98361e223e596fa88ff63ee8c5325638"
				+ configuration.getPassword()));
		assertEquals(mappedParams.get("wpf_status"), "approved");
	}
}
