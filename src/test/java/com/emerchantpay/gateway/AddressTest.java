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

		assertEquals("<billing_address>" + "<first_name>John</first_name>" + "<last_name>Doe</last_name>"
				+ "<address1>Fifth avenue</address1>" + "<address2>Fourth avenue</address2>" + "<city>New York</city>"
				+ "<zip_code>16000</zip_code>" + "<state>NY</state>" + "<country>US</country>" + "</billing_address>",
				client.setSale().billingAddress().setFirstname("John").setLastname("Doe").setAddress1("Fifth avenue")
						.setAddress2("Fourth avenue").setCountry("US").setCity("New York").setState("NY")
						.setZipCode("16000").toXML());

		assertEquals("<shipping_address>" + "<first_name>John</first_name>" + "<last_name>Doe</last_name>"
				+ "<address1>Fifth avenue</address1>" + "<address2>Fourth avenue</address2>" + "<city>New York</city>"
				+ "<zip_code>16000</zip_code>" + "<state>NY</state>" + "<country>US</country>" + "</shipping_address>",
				client.setSale().shippingAddress().setFirstname("John").setLastname("Doe").setAddress1("Fifth avenue")
						.setAddress2("Fourth avenue").setCountry("US").setCity("New York").setState("NY")
						.setZipCode("16000").toXML());
	}
}
