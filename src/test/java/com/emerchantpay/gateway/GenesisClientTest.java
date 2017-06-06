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
				.setCustomerEmail("john@example.com").setCustomerPhone("555555").billingAddress()
				.setAddress1("New York").setAddress2("Dallas").setFirstname("Plamen").setLastname("Petrov")
				.setCountry(Country.Bulgaria.getCode()).setCity("Sofia").setZipCode("1000").setState("NY").done()
				.setUsage("TICKETS").execute(configuration);
	}
}
