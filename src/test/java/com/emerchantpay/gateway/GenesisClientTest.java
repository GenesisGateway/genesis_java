package com.emerchantpay.gateway;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;

import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.api.exceptions.AuthenticationException;

public class GenesisClientTest {

	private Configuration configuration;

	@Before
	public void createConfiguration() {

		this.configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
	}

	@Test(expected = AuthenticationException.class)
	public void executeRequest() {

		UUID uniqueId = UUID.randomUUID();

		GenesisClient client = new GenesisClient(configuration);

		client.setSale().setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setGaming(true).setMoto(true)
				.setAmount(new BigDecimal("20.00")).setCurrency("USD").setCardholder("JOHN DOE")
				.setCardNumber("4200000000000000").setExpirationMonth("02").setExpirationYear("2020").setCvv("123")
				.setCustomerEmail("john@example.com").setCustomerPhone("555555").setUsage("TICKETS");

		client.setSale().setBillingPrimaryAddress("New York");
		client.setSale().setBillingSecondaryAddress("Dallas");
		client.setSale().setBillingFirstname("Plamen");
		client.setSale().setBillingLastname("Petrov");
		client.setSale().setBillingCity("Sofia");
		client.setSale().setBillingCountry(Country.Bulgaria.getCode());
		client.setSale().setBillingZipCode("1000");
		client.setSale().setBillingState("NY");

		client.setSale().execute(configuration);
	}
}