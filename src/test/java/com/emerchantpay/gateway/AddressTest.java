package com.emerchantpay.gateway;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.util.Configuration;

public class AddressTest {

	private Configuration configuration;

	@Before
	public void createConfiguration() {
		this.configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
	}

	@Test
	public void buildAddress() {
		GenesisClient client = new GenesisClient(configuration);

		// Billing Address
		client.setSale().setBillingFirstname("John");
		client.setSale().setBillingLastname("Doe");
		client.setSale().setBillingPrimaryAddress("Fifth avenue");
		client.setSale().setBillingSecondaryAddress("Fourth avenue");
		client.setSale().setBillingCountry("US");
		client.setSale().setBillingCity("New York");
		client.setSale().setBillingState("NY");
		client.setSale().setBillingZipCode("16000");

		client.setSale().buildBillingAddress();

		assertEquals("<first_name>John</first_name>" + "<last_name>Doe</last_name>"
						+ "<address1>Fifth avenue</address1>" + "<address2>Fourth avenue</address2>" + "<city>New York</city>"
						+ "<zip_code>16000</zip_code>" + "<state>NY</state>" + "<country>US</country>",
				client.setSale().getBillingAddress().toXML());

		// Shipping Address
		client.setSale().setShippingFirstname("John");
		client.setSale().setShippingLastname("Doe");
		client.setSale().setShippingPrimaryAddress("Fifth avenue");
		client.setSale().setShippingSecondaryAddress("Fourth avenue");
		client.setSale().setShippingCountry("US");
		client.setSale().setShippingCity("New York");
		client.setSale().setShippingState("NY");
		client.setSale().setShippingZipCode("16000");

		client.setSale().buildShippingAddress();

		assertEquals("<first_name>John</first_name>" + "<last_name>Doe</last_name>"
						+ "<address1>Fifth avenue</address1>" + "<address2>Fourth avenue</address2>" + "<city>New York</city>"
						+ "<zip_code>16000</zip_code>" + "<state>NY</state>" + "<country>US</country>",
				client.setSale().getShippingAddress().toXML());
	}
}