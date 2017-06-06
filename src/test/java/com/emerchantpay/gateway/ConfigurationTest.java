package com.emerchantpay.gateway;

import static org.junit.Assert.*;

import com.emerchantpay.gateway.api.constants.Environments;
import org.junit.Test;

import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.util.Configuration;

public class ConfigurationTest {

	Environments environment = new Environments("staging.gate");
	Endpoints endpoint = new Endpoints("emerchantpay.net");

	@Test
	public void testEnvironment() {
		Configuration configuration = new Configuration(environment, endpoint);

		assertEquals(Environments.STAGING.toString(), configuration.getEnvironment().toString());
		assertEquals(Endpoints.EMERCHANTPAY.toString(), endpoint.toString());
	}
}
