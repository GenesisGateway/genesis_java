package com.emerchantpay.gateway;

import java.math.BigDecimal;
import java.util.UUID;

import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;
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


		SaleRequest sale = new SaleRequest();

		sale.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS");
		sale.setGaming(true).setMoto(true);
		sale.setAmount(new BigDecimal("20.00")).setCurrency("USD");
		sale.setCardHolder("JOHN DOE").setCardNumber("4200000000000000").setExpirationMonth("02")
				.setExpirationYear("2020").setCvv("123");
		sale.setCustomerEmail("john@example.com").setCustomerPhone("555555");

		sale.setBillingPrimaryAddress("New York").setBillingSecondaryAddress("Dallas")
				.setBillingFirstname("Plamen").setBillingLastname("Petrov").setBillingCity("Sofia")
				.setBillingCountry(Country.Bulgaria.getCode()).setBillingZipCode("1000")
				.setBillingState("NY");

		GenesisClient client = new GenesisClient(configuration, sale);
		client.execute();
	}
}