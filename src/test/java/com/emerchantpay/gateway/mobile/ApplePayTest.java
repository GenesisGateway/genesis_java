package com.emerchantpay.gateway.mobile;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.mobile.ApplePayRequest;
import com.emerchantpay.gateway.api.requests.financial.mobile.ApplePayPaymentTokenRequest;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ApplePayTest {

    private GenesisClient client;
    private ApplePayRequest applePayRequest;

    private String uid;

    @Before
    public void createUPI() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        applePayRequest = mock(ApplePayRequest.class);
    }

    public ApplePayRequest stubApplePay(){
        ApplePayRequest applePayRequest = new ApplePayRequest();

        applePayRequest.setTransactionId(uid);
        applePayRequest.setPaymentType("authorize");
        applePayRequest.setUsage("TICKETS");
        applePayRequest.setCurrency(Currency.USD.getCurrency());
        applePayRequest.setAmount(new BigDecimal("20.00"));
        applePayRequest.setCustomerEmail("john.doe@emerchantpay.com");
        applePayRequest.setCustomerPhone("+55555555");
        applePayRequest.setRemoteIp("82.137.112.202");
        applePayRequest.setBirthDate("12-03-1999");
        applePayRequest.setDocumentId("12345");
        applePayRequest.setCrypto(true);

        //Payment token
        ApplePayPaymentTokenRequest paymentToken = applePayRequest.getPaymentTokenRequest();
        paymentToken.setTokenVersion("TokenVersion");
        paymentToken.setTokenData("TokenData");
        paymentToken.setTokenSignature("TokenSignature");
        paymentToken.setTokenEphemeralPublicKey("TokenEphemeralPublicKey");
        paymentToken.setTokenPublicKeyHash("TokenPublicKeyHash");
        paymentToken.setTokenTransactionId(uid);
        paymentToken.setTokenDisplayName("TokenDisplayName");
        paymentToken.setTokenNetwork("TokenNetwork");
        paymentToken.setTokenType("TokenType");
        paymentToken.setTokenTransactionIdentifier(uid);

        //Business attributes
        applePayRequest.setBusinessEventStartDate("10-10-2020");
        applePayRequest.setBusinessEventEndDate("17-10-2020");
        applePayRequest.setBusinessEventId("Event1234");
        applePayRequest.setBusinessEventOrganizerId("Organizer1234");
        applePayRequest.setBusinessDateOfOrder("10-10-2020");
        applePayRequest.setBusinessDeliveryDate("17-10-2020");
        applePayRequest.setBusinessNameOfSupplier("Test supplier");

        applePayRequest.setBillingPrimaryAddress("First Avenue");
        applePayRequest.setBillingSecondaryAddress("Second Avenue");
        applePayRequest.setBillingFirstname("John");
        applePayRequest.setBillingLastname("Doe");
        applePayRequest.setBillingCity("Berlin");
        applePayRequest.setBillingCountry(Country.Germany.getCode());
        applePayRequest.setBillingZipCode("M4B1B3");
        applePayRequest.setBillingState("BE");

        applePayRequest.setShippingPrimaryAddress("First Avenue");
        applePayRequest.setShippingSecondaryAddress("Second Avenue");
        applePayRequest.setShippingFirstname("John");
        applePayRequest.setShippingLastname("Doe");
        applePayRequest.setShippingCity("Berlin");
        applePayRequest.setShippingCountry(Country.Germany.getCode());
        applePayRequest.setShippingZipCode("M4B1B3");
        applePayRequest.setShippingState("BE");

        return applePayRequest;
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(applePayRequest);
        assertEquals(client.execute(), applePayRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testApplePay() {
        ApplePayPaymentTokenRequest paymentToken = mock(ApplePayPaymentTokenRequest.class);

        when(applePayRequest.setTransactionId(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setPaymentType(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.getPaymentTokenRequest()).thenReturn(paymentToken);
        when(applePayRequest.setUsage(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setAmount(isA(BigDecimal.class))).thenReturn(applePayRequest);
        when(applePayRequest.setCurrency(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setCustomerEmail(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setCustomerPhone(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setRemoteIp(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBirthDate(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setDocumentId(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setCrypto(isA(Boolean.class))).thenReturn(applePayRequest);

        when(paymentToken.setTokenVersion(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenData(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenSignature(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenEphemeralPublicKey(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenPublicKeyHash(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenTransactionId(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenDisplayName(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenNetwork(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenType(isA(String.class))).thenReturn(paymentToken);
        when(paymentToken.setTokenTransactionIdentifier(isA(String.class))).thenReturn(paymentToken);

        when(applePayRequest.setBusinessEventStartDate(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBusinessEventEndDate(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBusinessEventId(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBusinessEventOrganizerId(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBusinessDateOfOrder(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBusinessDeliveryDate(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBusinessNameOfSupplier(isA(String.class))).thenReturn(applePayRequest);

        when(applePayRequest.setBillingPrimaryAddress(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBillingSecondaryAddress(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBillingFirstname(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBillingLastname(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBillingCity(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBillingCountry(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBillingZipCode(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setBillingState(isA(String.class))).thenReturn(applePayRequest);

        when(applePayRequest.setShippingPrimaryAddress(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setShippingSecondaryAddress(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setShippingFirstname(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setShippingLastname(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setShippingCity(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setShippingCountry(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setShippingZipCode(isA(String.class))).thenReturn(applePayRequest);
        when(applePayRequest.setShippingState(isA(String.class))).thenReturn(applePayRequest);


        assertEquals(applePayRequest, applePayRequest.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS"));
        assertEquals(paymentToken, applePayRequest.getPaymentTokenRequest());
        assertEquals(applePayRequest, applePayRequest.setPaymentType("authorize"));
        assertEquals(applePayRequest, applePayRequest.setCurrency(Currency.INR.getCurrency()).setAmount(new BigDecimal("20.00")));
        assertEquals(applePayRequest, applePayRequest.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"));
        assertEquals(applePayRequest, applePayRequest.setBirthDate("12-03-1999"));
        assertEquals(applePayRequest, applePayRequest.setDocumentId("12345"));
        assertEquals(applePayRequest, applePayRequest.setCrypto(true));

        assertEquals(paymentToken, paymentToken.setTokenVersion("TokenVersion").setTokenData("TokenData").setTokenSignature("TokenSignature")
                .setTokenEphemeralPublicKey("TokenEphemeralPublicKey").setTokenPublicKeyHash("TokenPublicKeyHash")
                .setTokenTransactionId(uid).setTokenDisplayName("TokenDisplayName").setTokenNetwork("TokenNetwork")
                .setTokenType("TokenType").setTokenTransactionIdentifier(uid));
        ;

        assertEquals(applePayRequest, applePayRequest.setBusinessEventStartDate("10-10-2020").setBusinessEventEndDate("17-10-2020")
                .setBusinessEventId("Event1234").setBusinessEventOrganizerId("Organizer1234").setBusinessDateOfOrder("10-10-2020")
                .setBusinessDeliveryDate("17-10-2020").setBusinessNameOfSupplier("Test supplier"));

        assertEquals(applePayRequest, applePayRequest.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE"));

        assertEquals(applePayRequest, applePayRequest.setShippingPrimaryAddress("First Avenue").setShippingSecondaryAddress("Second Avenue")
                .setShippingFirstname("John").setShippingLastname("Doe")
                .setShippingCity("Berlin").setShippingCountry(Country.Germany.getCode())
                .setShippingZipCode("M4B1B3").setShippingState("BE"));


        verify(applePayRequest).setTransactionId(uid);
        verify(applePayRequest).getPaymentTokenRequest();
        verify(applePayRequest).setPaymentType("authorize");
        verify(applePayRequest).setUsage("TICKETS");
        verify(applePayRequest).setCurrency(Currency.INR.getCurrency());
        verify(applePayRequest).setAmount(new BigDecimal("20.00"));
        verify(applePayRequest).setCustomerEmail("john.doe@emerchantpay.com");
        verify(applePayRequest).setCustomerPhone("+55555555");
        verify(applePayRequest).setRemoteIp("82.137.112.202");
        verify(applePayRequest).setBirthDate("12-03-1999");
        verify(applePayRequest).setDocumentId("12345");
        verify(applePayRequest).setCrypto(true);

        verify(paymentToken).setTokenVersion("TokenVersion");
        verify(paymentToken).setTokenData("TokenData");
        verify(paymentToken).setTokenSignature("TokenSignature");
        verify(paymentToken).setTokenEphemeralPublicKey("TokenEphemeralPublicKey");
        verify(paymentToken).setTokenPublicKeyHash("TokenPublicKeyHash");
        verify(paymentToken).setTokenTransactionId(uid);
        verify(paymentToken).setTokenDisplayName("TokenDisplayName");
        verify(paymentToken).setTokenNetwork("TokenNetwork");
        verify(paymentToken).setTokenType("TokenType");
        verify(paymentToken).setTokenTransactionIdentifier(uid);

        verify(applePayRequest).setBusinessEventStartDate("10-10-2020");
        verify(applePayRequest).setBusinessEventEndDate("17-10-2020");
        verify(applePayRequest).setBusinessEventEndDate("17-10-2020");
        verify(applePayRequest).setBusinessEventId("Event1234");
        verify(applePayRequest).setBusinessEventOrganizerId("Organizer1234");
        verify(applePayRequest).setBusinessDateOfOrder("10-10-2020");
        verify(applePayRequest).setBusinessDeliveryDate("17-10-2020");
        verify(applePayRequest).setBusinessNameOfSupplier("Test supplier");

        verify(applePayRequest).setBillingPrimaryAddress("First Avenue");
        verify(applePayRequest).setBillingSecondaryAddress("Second Avenue");
        verify(applePayRequest).setBillingFirstname("John");
        verify(applePayRequest).setBillingLastname("Doe");
        verify(applePayRequest).setBillingCity("Berlin");
        verify(applePayRequest).setBillingCountry(Country.Germany.getCode());
        verify(applePayRequest).setBillingZipCode("M4B1B3");
        verify(applePayRequest).setBillingState("BE");

        verify(applePayRequest).setShippingPrimaryAddress("First Avenue");
        verify(applePayRequest).setShippingSecondaryAddress("Second Avenue");
        verify(applePayRequest).setShippingFirstname("John");
        verify(applePayRequest).setShippingLastname("Doe");
        verify(applePayRequest).setShippingCity("Berlin");
        verify(applePayRequest).setShippingCountry(Country.Germany.getCode());
        verify(applePayRequest).setShippingZipCode("M4B1B3");
        verify(applePayRequest).setShippingState("BE");

        verifyNoMoreInteractions(applePayRequest);

        verifyExecute();
    }

    @Test (expected = RequiredParamsException.class)
    public void testMissingParam(){
        applePayRequest = stubApplePay();
        applePayRequest.setCurrency(null);
        applePayRequest.toXML();
    }

    @Test (expected = RequiredParamsException.class)
    public void testWrongPaymentType(){
        applePayRequest = stubApplePay();
        applePayRequest.setPaymentType("someWrongType");
        applePayRequest.toXML();
    }

    @Test (expected = RegexException.class)
    public void testInvalidBirthDate(){
        applePayRequest = stubApplePay();
        applePayRequest.setBirthDate("1-1-1999");
        applePayRequest.toXML();
    }

    @Test (expected = RequiredParamsException.class)
    public void testMissingTokenData(){
        applePayRequest = stubApplePay();
        applePayRequest.getPaymentTokenRequest().setTokenData("");
        applePayRequest.toXML();
    }

    @Test (expected = IOException.class)
    public void testSetPaymentTokenInvalidJson() throws IOException {
        applePayRequest = stubApplePay();
        applePayRequest.getPaymentTokenRequest().setPaymentTokenJson("Invalid JSON");
        applePayRequest.toXML();
    }

    @Test
    public void testSetPaymentToken() throws IOException {
        applePayRequest = stubApplePay();
        ApplePayPaymentTokenRequest paymentToken = applePayRequest.getPaymentTokenRequest();


        HashMap<String, String> paymentDataHeaderMap = new HashMap<String, String>();
        paymentDataHeaderMap.put(RequiredParameters.tokenPublicKeyHash, "test publicKeyHash");
        paymentDataHeaderMap.put(RequiredParameters.tokenEphemeralPublicKey, "test ephemeralPublicKey");
        paymentDataHeaderMap.put(RequiredParameters.tokenTransactionId, "test transactionId");

        HashMap<String, Object> paymentDataMap = new HashMap<String, Object>();
        paymentDataMap.put(RequiredParameters.tokenVersion, "test version");
        paymentDataMap.put(RequiredParameters.tokenData, "test data");
        paymentDataMap.put(RequiredParameters.tokenSignature, "test signature");
        paymentDataMap.put("header", paymentDataHeaderMap);

        HashMap<String, String> paymentMethodMap = new HashMap<String, String>();
        paymentMethodMap.put(RequiredParameters.tokenDisplayName, "test displayName");
        paymentMethodMap.put(RequiredParameters.tokenNetwork, "test network");
        paymentMethodMap.put(RequiredParameters.tokenType, "test type");

        HashMap<String, Object> paymentTokenMap = new HashMap<>();
        paymentTokenMap.put(RequiredParameters.tokenTransactionIdentifier, "test transactionIdentifier");
        paymentTokenMap.put("paymentData", paymentDataMap);
        paymentTokenMap.put("paymentMethod", paymentMethodMap);

        ObjectMapper mapper = new ObjectMapper();
        String testPaymentTokenJson = mapper.writeValueAsString(paymentTokenMap);
        paymentToken.setPaymentTokenJson(testPaymentTokenJson);

        //Compare using object mapper readTree to ignore order
        assertEquals(mapper.readTree(testPaymentTokenJson), mapper.readTree(paymentToken.toJSON()));

        JsonNode testRootExpected = mapper.readTree(testPaymentTokenJson);

        assertEquals(testRootExpected.get("paymentData").get(RequiredParameters.tokenVersion).asText(), paymentToken.getTokenVersion());
        assertEquals(testRootExpected.get("paymentData").get(RequiredParameters.tokenData).asText(), paymentToken.getTokenData());
        assertEquals(testRootExpected.get("paymentData").get(RequiredParameters.tokenSignature).asText(), paymentToken.getTokenSignature());
        assertEquals(testRootExpected.get("paymentData").get("header").get(RequiredParameters.tokenEphemeralPublicKey).asText(), paymentToken.getTokenEphemeralPublicKey());
        assertEquals(testRootExpected.get("paymentData").get("header").get(RequiredParameters.tokenPublicKeyHash).asText(), paymentToken.getTokenPublicKeyHash());
        assertEquals(testRootExpected.get("paymentData").get("header").get(RequiredParameters.tokenTransactionId).asText(), paymentToken.getTokenTransactionId());
        assertEquals(testRootExpected.get("paymentMethod").get(RequiredParameters.tokenDisplayName).asText(), paymentToken.getTokenDisplayName());
        assertEquals(testRootExpected.get("paymentMethod").get(RequiredParameters.tokenNetwork).asText(), paymentToken.getTokenNetwork());
        assertEquals(testRootExpected.get("paymentMethod").get(RequiredParameters.tokenType).asText(), paymentToken.getTokenType());
        assertEquals(testRootExpected.get(RequiredParameters.tokenTransactionIdentifier).asText(), paymentToken.getTokenTransactionIdentifier());

        applePayRequest.toXML();
    }
}
