package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.P24Request;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class P24Test {

	private List<Map.Entry<String, Object>> elements;
	private HashMap<String, Object> mappedParams;

	private String uniqueId;

	private P24Request request = new P24Request();

	@Before
	public void createP24() throws MalformedURLException {

		uniqueId = new StringUtils().generateUID();

		request.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
				.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"))
				.setCustomerEmail("john@example.com").setCustomerPhone("+55555555")
				.setReturnSuccessUrl(new URL("https://example.com/return_success_url"))
				.setReturnFailureUrl(new URL("https://example.com/return_failure_url"));

		request.setBillingPrimaryAddress("Berlin");
		request.setBillingSecondaryAddress("Berlin");
		request.setBillingFirstname("Plamen");
		request.setBillingLastname("Petrov");
		request.setBillingCity("Berlin");
		request.setBillingCountry("DE");
		request.setBillingZipCode("M4B1B3");
		request.setBillingState("BE");
	}

	public void setMissingParams() {
		request.setBillingCountry(null);
	}

	@Test
	public void testP24() throws MalformedURLException {
		mappedParams = new HashMap<String, Object>();

		elements = request.getElements();

		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getKey() == "billing_address")
			{
				mappedParams.put("billing_address", request.getBillingAddress().getElements());
			}
			else {
				mappedParams.put(elements.get(i).getKey(), request.getElements().get(i).getValue());
			}
		}

		assertEquals(mappedParams.get("transaction_id"), uniqueId);
		assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
		assertEquals(mappedParams.get("usage"), "TICKETS");
		assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
		assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
		assertEquals(mappedParams.get("customer_email"), "john@example.com");
		assertEquals(mappedParams.get("customer_phone"), "+55555555");
		assertEquals(mappedParams.get("is_payout"), null);
		assertEquals(mappedParams.get("return_success_url"),  new URL("https://example.com/return_success_url"));
		assertEquals(mappedParams.get("return_failure_url"),  new URL("https://example.com/return_failure_url"));
		assertEquals(mappedParams.get("billing_address"), request.getBillingAddress().getElements());
	}

	@Test
	public void testP24WithMissingParams() {

		setMissingParams();

		mappedParams = new HashMap<String, Object>();
		elements = request.buildBillingAddress().getElements();

		for (int i = 0; i < elements.size(); i++) {
			mappedParams.put(elements.get(i).getKey(), request.getBillingAddress().getElements().get(i).getValue());
		}

		assertNull(mappedParams.get("country"));
	}
}