package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.constants.*;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.emerchantpay.gateway.util.Configuration;

public class ConfigurationTest {

    private Environments stageEnvironment;
    private Endpoints empEndpoint;
    private Configuration configurationConsumer;
    private Configuration configurationFX;
    private Configuration configurationScaChecker;
    private Configuration configurationStaging;
    private Configuration configurationProd;

    @BeforeEach
    public void setUp() {
        stageEnvironment = Environments.STAGING;
        Environments prodEnvironment = Environments.PRODUCTION;
        empEndpoint = Endpoints.EMERCHANTPAY;
        configurationConsumer = new Configuration(stageEnvironment, empEndpoint, ConsumerEndpoints.CREATE_CONSUMER, "v1");
        configurationFX = new Configuration(stageEnvironment, empEndpoint, FXEndpoints.FX_TIERS, "v1");
        configurationScaChecker = new Configuration(stageEnvironment, empEndpoint, "v1");
        configurationStaging = new Configuration(stageEnvironment, empEndpoint);
        configurationProd = new Configuration(prodEnvironment, empEndpoint);
    }

    @Test
    public void testEnvironment() {
        Configuration configuration = new Configuration(stageEnvironment, empEndpoint);

        assertEquals(Environments.STAGING.getEnvironmentName(), configuration.getEnvironment().getEnvironmentName());
        assertEquals(Endpoints.EMERCHANTPAY.getEndpointName(), configuration.getEndpoint().getEndpointName());
    }

    @Test
    public void testConsumerVersionSuccess() {
        assertEquals("v1", configurationConsumer.getConsumerVersion());
    }

    @Test
    public void testConsumerVersionFailure() {
        assertThrows(GenesisException.class, () -> configurationConsumer.setConsumerAPIVersion("v5"));
    }

    @Test
    public void testFXVersionSuccess() {
        assertEquals("v1", configurationFX.getFXAPIVersion());
    }

    @Test
    public void testFXAPIVersionFailure() {
        assertThrows(GenesisException.class, () -> configurationFX.setFXAPIVersion("v5"));
    }

    @Test
    public void testScaCheckerVersionSuccess() {
        assertEquals("v1", configurationScaChecker.getScaCheckerAPIVersion());
    }

    @Test
    public void testScaCheckerAPIVersionFailure() {
        assertThrows(GenesisException.class, () -> configurationScaChecker.setScaCheckerAPIVersion("v5"));
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

    @Test
    public void testConfigurationQueryParams(){
        String urlWithoutQueryParams = configurationConsumer.getBaseUrl();
        configurationConsumer.addQueryParameter("param1", "value1");
        configurationConsumer.addQueryParameter("param2", "value2");
        String urlWithParams = configurationConsumer.getBaseUrl();
        String expectedQueryParam = "?param1=value1&param2=value2";
        assertTrue(urlWithParams.contains(expectedQueryParam));
        configurationConsumer.clearQueryParameters();
        assertEquals(urlWithoutQueryParams, configurationConsumer.getBaseUrl());
    }

    @Test
    public void testSmartRoutingEnabledStageBaseURl(){
        configurationStaging.setAction(EndpointActions.TRANSACTIONS);
        configurationStaging.setTokenEnabled(false);
        String baseUrl = configurationStaging.getBaseUrl();
        String expectedUrl = "https://staging.api.emerchantpay.net/transactions";

        assertEquals(expectedUrl, baseUrl);
    }

    @Test
    public void testSmartRoutingDisabledStageBaseURl(){
        configurationStaging.setAction(EndpointActions.PROCESS);
        configurationStaging.setTokenEnabled(true);
        configurationStaging.setToken("testTerminalToken");
        String baseUrl = configurationStaging.getBaseUrl();
        String expectedUrl = "https://staging.gate.emerchantpay.net/process/testTerminalToken";

        assertEquals(expectedUrl, baseUrl);
    }

    @Test
    public void testSmartRoutingEnabledProdBaseURl(){
        configurationProd.setAction(EndpointActions.TRANSACTIONS);
        configurationProd.setTokenEnabled(false);
        String baseUrl = configurationProd.getBaseUrl();
        String expectedUrl = "https://prod.api.emerchantpay.net/transactions";

        assertEquals(expectedUrl, baseUrl);
    }

    @Test
    public void testSmartRoutingDisabledProdBaseURl(){
        configurationProd.setAction(EndpointActions.PROCESS);
        configurationProd.setTokenEnabled(true);
        configurationProd.setToken("testTerminalToken");
        String baseUrl = configurationProd.getBaseUrl();
        String expectedUrl = "https://gate.emerchantpay.net/process/testTerminalToken";

        assertEquals(expectedUrl, baseUrl);
    }

    @Test
    public void testWpfStageBaseURl(){
        configurationStaging.setWpfEnabled(true);
        configurationStaging.setTokenEnabled(false);
        configurationStaging.setAction("wpf/reconcile");
        String baseUrl = configurationStaging.getBaseUrl();
        String expectedUrl = "https://staging.wpf.emerchantpay.net/wpf/reconcile";

        assertEquals(expectedUrl, baseUrl);
    }

    @Test
    public void testWpfProdBaseURl(){
        configurationProd.setWpfEnabled(true);
        configurationProd.setTokenEnabled(false);
        configurationProd.setAction("wpf/reconcile");
        String baseUrl = configurationProd.getBaseUrl();
        String expectedUrl = "https://wpf.emerchantpay.net/wpf/reconcile";

        assertEquals(expectedUrl, baseUrl);
    }

    @Test
    public void testArbitraryEnvironmentApiURl(){
        Configuration arbitraryConfig = new Configuration(new Environments("sub4.sub3"), empEndpoint);
        arbitraryConfig.setWpfEnabled(true);
        arbitraryConfig.setTokenEnabled(false);
        arbitraryConfig.setAction("wpf/reconcile");
        String baseUrl = arbitraryConfig.getBaseUrl();
        String expectedUrl = "https://sub4.sub3.emerchantpay.net/wpf/reconcile";

        assertEquals(expectedUrl, baseUrl);
    }
}