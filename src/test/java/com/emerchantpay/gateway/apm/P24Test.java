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
		mappedParams = new HashMap<String, Object>();
		uniqueId = new StringUtils().generateUID();

		request.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
		request.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
		request.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
		request.setReturnSuccessUrl(new URL("https://example.com/return_success_url"))
				.setReturnFailureUrl(new URL("https://example.com/return_failure_url"));

		request.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
				.setBillingFirstname("Plamen").setBillingLastname("Petrov")
				.setBillingCity("Berlin").setBillingCountry("DE")
				.setBillingZipCode("M4B1B3").setBillingState("BE");

		mappedParams.put("base_attributes", request.buildBaseParams().getElements());
		mappedParams.put("payment_attributes", request.buildPaymentParams().getElements());
		mappedParams.put("customer_info_attributes", request.buildCustomerInfoParams().getElements());
		mappedParams.put("async_attributes",  request.buildAsyncParams().getElements());
	}

	public void setMissingParams() {
		request.setBillingCountry(null);
	}

	@Test
	public void testP24() throws MalformedURLException {

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

		assertEquals(mappedParams.get("base_attributes"), request.buildBaseParams().getElements());
		assertEquals(mappedParams.get("payment_attributes"), request.buildPaymentParams().getElements());
		assertEquals(mappedParams.get("customer_info_attributes"), request.buildCustomerInfoParams().getElements());
		assertEquals(mappedParams.get("async_attributes"), request.buildAsyncParams().getElements());
		assertEquals(mappedParams.get("is_payout"), null);
		assertEquals(mappedParams.get("billing_address"), request.getBillingAddress().getElements());
	}

	@Test
	public void testP24WithMissingParams() {

		setMissingParams();

		elements = request.buildBillingAddress().getElements();

		for (int i = 0; i < elements.size(); i++) {
			mappedParams.put(elements.get(i).getKey(), request.getBillingAddress().getElements().get(i).getValue());
		}

		assertNull(mappedParams.get("country"));
	}
}