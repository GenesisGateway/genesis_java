package com.emerchantpay.gateway;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.emerchantpay.gateway.api.RequestBuilder;

public class RequestBuilderTest {

	@Test
	public void toXml() {
		RequestBuilder builder = new RequestBuilder("payment_transaction");
		builder.addElement("name", "value");
		String result = builder.toXML();
		assertEquals("<payment_transaction><name>value</name></payment_transaction>", result);
	}

	// reveal protected methods for test
	private static class Open extends RequestBuilder {
		public Open() {
			super("open");
		}

		public static String publicBuildXmlElement(String name, Object object) {
			return buildXMLElement(name, object);
		}

		public static String formatMap(String name, Map<String, Object> map) {
			return formatAsXML(name, map);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void list() {
		@SuppressWarnings("unused")
		Open builder = new Open();
		@SuppressWarnings("rawtypes")
		List<String> items = new ArrayList();
		items.add("Authorize");
		items.add("WPF");
		String element = Open.publicBuildXmlElement("payments", items);
		assertEquals("<payments type=\"array\"><item>Authorize</item><item>WPF</item></payments>", element);
	}

	@Test
	public void map() {
		@SuppressWarnings("unused")
		Open builder = new Open();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transaction_id", "123456");
		map.put("transaction_type", "Authorize");
		String element = Open.formatMap("payments", map);
		assertEquals(
				"<payments><transaction_id>123456</transaction_id><transaction_type>Authorize</transaction_type></payments>",
				element);
	}
}
