package com.emerchantpay.gateway;

import static org.junit.Assert.assertEquals;

import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;
import org.junit.Before;
import org.junit.Test;

public class AddressTest {

	SaleRequest sale;

	@Before
	public void createAddress() {
		sale = new SaleRequest();

		// Billing Address
		sale.setBillingFirstname("John").setBillingLastname("Doe").setBillingPrimaryAddress("Fifth avenue")
				.setBillingSecondaryAddress("Fourth avenue").setBillingCity("New York")
				.setBillingZipCode("16000").setBillingState("NY")
				.setBillingCountry("US");

		sale.buildBillingAddress();

		// Shipping Address
		sale.setShippingFirstname("John").setShippingLastname("Doe").setShippingPrimaryAddress("Fifth avenue")
				.setShippingSecondaryAddress("Fourth avenue").setShippingCity("New York")
				.setShippingZipCode("16000").setShippingState("NY")
				.setShippingCountry("US");

		sale.buildShippingAddress();
	}

	@Test
	public void buildAddress() {

		assertEquals("<first_name>John</first_name>" + "<last_name>Doe</last_name>"
						+ "<address1>Fifth avenue</address1>" + "<address2>Fourth avenue</address2>" + "<city>New York</city>"
						+ "<zip_code>16000</zip_code>" + "<state>NY</state>" + "<country>US</country>",
				sale.getBillingAddress().toXML());

		assertEquals("<first_name>John</first_name>" + "<last_name>Doe</last_name>"
						+ "<address1>Fifth avenue</address1>" + "<address2>Fourth avenue</address2>" + "<city>New York</city>"
						+ "<zip_code>16000</zip_code>" + "<state>NY</state>" + "<country>US</country>",
				sale.getShippingAddress().toXML());
	}
}