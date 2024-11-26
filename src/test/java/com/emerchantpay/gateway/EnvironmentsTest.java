package com.emerchantpay.gateway;

import static org.junit.Assert.*;

import com.emerchantpay.gateway.api.constants.Environments;
import org.junit.Test;

public class EnvironmentsTest {

	@Test
	public void testStagingEnvironment() {
		assertEquals(Environments.STAGING.getEnvironmentName(), new Environments("staging").getEnvironmentName());
	}

	@Test
	public void testProductionEnvironment() {
		assertEquals(Environments.PRODUCTION.getEnvironmentName(), new Environments("prod").getEnvironmentName());
	}
}
