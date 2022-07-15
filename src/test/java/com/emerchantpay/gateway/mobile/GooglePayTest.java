package com.emerchantpay.gateway.mobile;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.mobile.GooglePayPaymentTokenRequest;
import com.emerchantpay.gateway.api.requests.financial.mobile.GooglePayRequest;
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

public class GooglePayTest {

    private GenesisClient client;
    private GooglePayRequest googlePayRequest;

    private String uid;

    @Before
    public void createGooglePay() {
        uid = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        googlePayRequest = mock(GooglePayRequest.class);
    }

    public GooglePayRequest stubGooglePay(){
        GooglePayRequest googlePayRequest = new GooglePayRequest();

        googlePayRequest.setTransactionId(uid);
        googlePayRequest.setPaymentSubType("authorize");
        googlePayRequest.setUsage("TICKETS");
        googlePayRequest.setCurrency(Currency.USD.getCurrency());
        googlePayRequest.setAmount(new BigDecimal("20.00"));
        googlePayRequest.setCustomerEmail("john.doe@emerchantpay.com");
        googlePayRequest.setCustomerPhone("+55555555");
        googlePayRequest.setRemoteIp("82.137.112.202");
        googlePayRequest.setBirthDate("12-03-1999");
        googlePayRequest.setDocumentId("12345");

        //Payment token
        GooglePayPaymentTokenRequest paymentToken = googlePayRequest.getPaymentTokenRequest();
        paymentToken.setTokenSignature("test Token signature");
        paymentToken.setTokenProtocolVersion("test Token Protocol version");
        paymentToken.setSignedTokenKey("test Signed Token key");
        paymentToken.setSignedTokenMessage("test Signed Token message");
        paymentToken.setTokenSignatures(new String[]{"test Signatures"});
        paymentToken.setTokenTransactionIdentifier(uid);

        //Business attributes
        googlePayRequest.setBusinessEventStartDate("10-10-2020");
        googlePayRequest.setBusinessEventEndDate("17-10-2020");
        googlePayRequest.setBusinessEventId("Event1234");
        googlePayRequest.setBusinessEventOrganizerId("Organizer1234");
        googlePayRequest.setBusinessDateOfOrder("10-10-2020");
        googlePayRequest.setBusinessDeliveryDate("17-10-2020");
        googlePayRequest.setBusinessNameOfSupplier("Test supplier");

        googlePayRequest.setBillingPrimaryAddress("First Avenue");
        googlePayRequest.setBillingSecondaryAddress("Second Avenue");
        googlePayRequest.setBillingFirstname("John");
        googlePayRequest.setBillingLastname("Doe");
        googlePayRequest.setBillingCity("Berlin");
        googlePayRequest.setBillingCountry(Country.Germany.getCode());
        googlePayRequest.setBillingZipCode("M4B1B3");
        googlePayRequest.setBillingState("BE");

        googlePayRequest.setShippingPrimaryAddress("First Avenue");
        googlePayRequest.setShippingSecondaryAddress("Second Avenue");
        googlePayRequest.setShippingFirstname("John");
        googlePayRequest.setShippingLastname("Doe");
        googlePayRequest.setShippingCity("Berlin");
        googlePayRequest.setShippingCountry(Country.Germany.getCode());
        googlePayRequest.setShippingZipCode("M4B1B3");
        googlePayRequest.setShippingState("BE");

        return googlePayRequest;
    }

    public void verifyExecute() {
        when(client.execute()).thenReturn(googlePayRequest);
        assertEquals(client.execute(), googlePayRequest);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testGooglePay() {
        GooglePayPaymentTokenRequest paymentToken = mock(GooglePayPaymentTokenRequest.class);

        when(googlePayRequest.setTransactionId(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setPaymentSubType(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.getPaymentTokenRequest()).thenReturn(paymentToken);
        when(googlePayRequest.setUsage(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setAmount(isA(BigDecimal.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setCurrency(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setCustomerEmail(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setCustomerPhone(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setRemoteIp(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBirthDate(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setDocumentId(isA(String.class))).thenReturn(googlePayRequest);

        when(googlePayRequest.setBusinessEventStartDate(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBusinessEventEndDate(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBusinessEventId(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBusinessEventOrganizerId(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBusinessDateOfOrder(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBusinessDeliveryDate(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBusinessNameOfSupplier(isA(String.class))).thenReturn(googlePayRequest);

        when(googlePayRequest.setBillingPrimaryAddress(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBillingSecondaryAddress(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBillingFirstname(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBillingLastname(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBillingCity(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBillingCountry(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBillingZipCode(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setBillingState(isA(String.class))).thenReturn(googlePayRequest);

        when(googlePayRequest.setShippingPrimaryAddress(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setShippingSecondaryAddress(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setShippingFirstname(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setShippingLastname(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setShippingCity(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setShippingCountry(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setShippingZipCode(isA(String.class))).thenReturn(googlePayRequest);
        when(googlePayRequest.setShippingState(isA(String.class))).thenReturn(googlePayRequest);


        assertEquals(googlePayRequest, googlePayRequest.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS"));
        assertEquals(paymentToken, googlePayRequest.getPaymentTokenRequest());
        assertEquals(googlePayRequest, googlePayRequest.setPaymentSubType("authorize"));
        assertEquals(googlePayRequest, googlePayRequest.setCurrency(Currency.INR.getCurrency()).setAmount(new BigDecimal("20.00")));
        assertEquals(googlePayRequest, googlePayRequest.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555"));
        assertEquals(googlePayRequest, googlePayRequest.setBirthDate("12-03-1999"));
        assertEquals(googlePayRequest, googlePayRequest.setDocumentId("12345"));

        assertEquals(googlePayRequest, googlePayRequest.setBusinessEventStartDate("10-10-2020").setBusinessEventEndDate("17-10-2020")
                .setBusinessEventId("Event1234").setBusinessEventOrganizerId("Organizer1234").setBusinessDateOfOrder("10-10-2020")
                .setBusinessDeliveryDate("17-10-2020").setBusinessNameOfSupplier("Test supplier"));

        assertEquals(googlePayRequest, googlePayRequest.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE"));

        assertEquals(googlePayRequest, googlePayRequest.setShippingPrimaryAddress("First Avenue").setShippingSecondaryAddress("Second Avenue")
                .setShippingFirstname("John").setShippingLastname("Doe")
                .setShippingCity("Berlin").setShippingCountry(Country.Germany.getCode())
                .setShippingZipCode("M4B1B3").setShippingState("BE"));


        verify(googlePayRequest).setTransactionId(uid);
        verify(googlePayRequest).getPaymentTokenRequest();
        verify(googlePayRequest).setPaymentSubType("authorize");
        verify(googlePayRequest).setUsage("TICKETS");
        verify(googlePayRequest).setCurrency(Currency.INR.getCurrency());
        verify(googlePayRequest).setAmount(new BigDecimal("20.00"));
        verify(googlePayRequest).setCustomerEmail("john.doe@emerchantpay.com");
        verify(googlePayRequest).setCustomerPhone("+55555555");
        verify(googlePayRequest).setRemoteIp("82.137.112.202");
        verify(googlePayRequest).setBirthDate("12-03-1999");
        verify(googlePayRequest).setDocumentId("12345");

        verify(googlePayRequest).setBusinessEventStartDate("10-10-2020");
        verify(googlePayRequest).setBusinessEventEndDate("17-10-2020");
        verify(googlePayRequest).setBusinessEventEndDate("17-10-2020");
        verify(googlePayRequest).setBusinessEventId("Event1234");
        verify(googlePayRequest).setBusinessEventOrganizerId("Organizer1234");
        verify(googlePayRequest).setBusinessDateOfOrder("10-10-2020");
        verify(googlePayRequest).setBusinessDeliveryDate("17-10-2020");
        verify(googlePayRequest).setBusinessNameOfSupplier("Test supplier");

        verify(googlePayRequest).setBillingPrimaryAddress("First Avenue");
        verify(googlePayRequest).setBillingSecondaryAddress("Second Avenue");
        verify(googlePayRequest).setBillingFirstname("John");
        verify(googlePayRequest).setBillingLastname("Doe");
        verify(googlePayRequest).setBillingCity("Berlin");
        verify(googlePayRequest).setBillingCountry(Country.Germany.getCode());
        verify(googlePayRequest).setBillingZipCode("M4B1B3");
        verify(googlePayRequest).setBillingState("BE");

        verify(googlePayRequest).setShippingPrimaryAddress("First Avenue");
        verify(googlePayRequest).setShippingSecondaryAddress("Second Avenue");
        verify(googlePayRequest).setShippingFirstname("John");
        verify(googlePayRequest).setShippingLastname("Doe");
        verify(googlePayRequest).setShippingCity("Berlin");
        verify(googlePayRequest).setShippingCountry(Country.Germany.getCode());
        verify(googlePayRequest).setShippingZipCode("M4B1B3");
        verify(googlePayRequest).setShippingState("BE");

        verifyNoMoreInteractions(googlePayRequest);

        verifyExecute();
    }

    @Test (expected = RequiredParamsException.class)
    public void testMissingParam(){
        googlePayRequest = stubGooglePay();
        googlePayRequest.setCurrency(null);
        googlePayRequest.toXML();
    }

    @Test (expected = RequiredParamsException.class)
    public void testWrongPaymentType(){
        googlePayRequest = stubGooglePay();
        googlePayRequest.setPaymentSubType("someWrongType");
        googlePayRequest.toXML();
    }

    @Test (expected = RegexException.class)
    public void testInvalidBirthDate(){
        googlePayRequest = stubGooglePay();
        googlePayRequest.setBirthDate("1-1-1999");
        googlePayRequest.toXML();
    }

    @Test (expected = RequiredParamsException.class)
    public void testMissingTokenSignature() {
        googlePayRequest = stubGooglePay();
        googlePayRequest.getPaymentTokenRequest().setTokenSignature("");
        googlePayRequest.toXML();
    }

    @Test (expected = IOException.class)
    public void testSetPaymentTokenInvalidJson() throws IOException {
        googlePayRequest = stubGooglePay();
        googlePayRequest.getPaymentTokenRequest().setPaymentTokenJson("Invalid JSON");
        googlePayRequest.toXML();
    }

    @Test
    public void testSetPaymentToken() throws IOException {
        googlePayRequest = stubGooglePay();
        GooglePayPaymentTokenRequest paymentToken = googlePayRequest.getPaymentTokenRequest();
        HashMap<String, String> paymentMap = new HashMap<>();
        paymentMap.put(RequiredParameters.tokenSignature, "test Token signature");
        paymentMap.put(RequiredParameters.tokenProtocolVersion, "test Token Protocol version");
        paymentMap.put(RequiredParameters.tokenSignedMessage, "test Signed Token message");

        HashMap<String, String> intermediateSigningKeyMap = new HashMap<>();
        intermediateSigningKeyMap.put(RequiredParameters.tokenSignedKey, "test Signed Token key");
        intermediateSigningKeyMap.put(RequiredParameters.tokenSignatures, "test Signatures");

        HashMap<String, Object> paymentTokenMap = new HashMap<>();
        paymentTokenMap.put("", paymentMap);
        paymentTokenMap.put("intermediateSigningKey", intermediateSigningKeyMap);

        ObjectMapper mapper = new ObjectMapper();
        String testPaymentTokenJson = mapper.writeValueAsString(paymentTokenMap);
        paymentToken.setPaymentToken(testPaymentTokenJson);

        JsonNode testRootExpected = mapper.readTree(testPaymentTokenJson);

        assertEquals(testRootExpected.get("").get(RequiredParameters.tokenSignature).asText(), paymentToken.getTokenSignature());
        assertEquals(testRootExpected.get("").get(RequiredParameters.tokenProtocolVersion).asText(), paymentToken.getTokenProtocolVersion());
        assertEquals(testRootExpected.get("").get(RequiredParameters.tokenSignedMessage).asText(), paymentToken.getTokenSignedMessage());
        assertEquals(testRootExpected.get("intermediateSigningKey").get(RequiredParameters.tokenSignedKey).asText(), paymentToken.getTokenSignedKey());
        assertEquals(testRootExpected.get("intermediateSigningKey").get(RequiredParameters.tokenSignatures).asText(), paymentToken.getTokenSignatures()[0]);

        googlePayRequest.toXML();
    }
}
