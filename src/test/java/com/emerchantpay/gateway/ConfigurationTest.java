package com.emerchantpay.gateway;

import static org.junit.Assert.*;

import com.emerchantpay.gateway.api.constants.ConsumerEndpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.constants.FXEndpoints;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import org.junit.Test;

import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.util.Configuration;

public class ConfigurationTest {

    Environments environment = new Environments("staging.gate");
    Endpoints endpoint = new Endpoints("emerchantpay.net");
    Configuration configurationConsumer = new Configuration(environment, endpoint, ConsumerEndpoints.CREATE_CONSUMER, "v1");
    Configuration configurationFX = new Configuration(environment, endpoint, FXEndpoints.FX_TIERS, "v1");
    Configuration configurationScaChecker = new Configuration(environment, endpoint, "v1");

    @Test
    public void testEnvironment() {
        Configuration configuration = new Configuration(environment, endpoint);

        assertEquals(Environments.STAGING.getEnvironmentName(), configuration.getEnvironment().getEnvironmentName());
        assertEquals(Endpoints.EMERCHANTPAY.getEndpointName(), configuration.getEndpoint().getEndpointName());
    }

    @Test
    public void testConsumerVersionSuccess() {
        assertEquals("v1", configurationConsumer.getConsumerVersion());
    }

    @Test(expected = GenesisException.class)
    public void testConsumerVersionFailure() {
        configurationConsumer.setConsumerAPIVersion("v5");
        assertEquals("v5", configurationConsumer.getConsumerVersion());
    }

    @Test
    public void testFXVersionSuccess() {
        assertEquals("v1", configurationFX.getFXAPIVersion());
    }

    @Test(expected = GenesisException.class)
    public void testFXAPIVersionFailure() {
        configurationFX.setFXAPIVersion("v5");
        assertEquals("v5", configurationFX.getFXAPIVersion());
    }

    @Test
    public void testScaCheckerVersionSuccess() {
        assertEquals("v1", configurationScaChecker.getScaCheckerAPIVersion());
    }

    @Test(expected = GenesisException.class)
    public void testScaCheckerAPIVersionFailure() {
        configurationScaChecker.setScaCheckerAPIVersion("v5");
        assertEquals("v5", configurationScaChecker.getFXAPIVersion());
    }

    @Test
    public void testConfigurationClone(){
        configurationConsumer.setUsername("user1234");
        configurationConsumer.setPassword("password12345");
        configurationConsumer.setToken("testtoken1234");

        Configuration configurationClone = configurationConsumer.clone();
        assertEquals(configurationConsumer.getUsername(), configurationClone.getUsername());
        assertEquals(configurationConsumer.getPassword(), configurationClone.getPassword());
        assertEquals(configurationConsumer.getToken(), configurationClone.getToken());
        assertEquals(configurationConsumer.getEnvironment(), configurationClone.getEnvironment());
        assertEquals(configurationConsumer.getEndpoint(), configurationClone.getEndpoint());
        assertEquals(configurationConsumer.getConsumerVersion(), configurationClone.getConsumerVersion());
        assertEquals(configurationConsumer.getBaseUrl(), configurationClone.getBaseUrl());
    }
}
