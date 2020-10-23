package com.emerchantpay.gateway.cash;

import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.SouthAmericanPaymentRequest;
import com.emerchantpay.gateway.api.requests.financial.cash.southamerican.*;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class SouthAmericanCashPaymentsTest {

    private String uid;
    private Random random;
    private LocalDate today;
    private DateTimeFormatter formatter;
    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND_NINE = 9;
    private static final int MAX_BOUND_NINETEEN = 19;

    @Before
    public void createSouthAmericanPayment() {
        uid = new StringUtils().generateUID();
        random = new Random();
        today = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public void stubAndVerify(SouthAmericanPaymentRequest request, String countryCode) throws MalformedURLException {

        request.setTransactionId(uid);
        assertEquals(uid, request.getTransactionId());

        request.setRemoteIp("82.137.112.202");
        assertEquals("82.137.112.202", request.getRemoteIp());

        String usage = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        request.setUsage(usage);
        assertEquals(usage, request.getUsage());

        BigDecimal amount = new BigDecimal(10);
        request.setAmount(amount);
        assertEquals(amount, request.getAmount());

        request.setCurrency(Currency.USD.getCurrency());
        assertEquals(Currency.USD.getCurrency(), request.getCurrency());

        request.setCustomerEmail("john.doe@emerchantpay.com");
        assertEquals("john.doe@emerchantpay.com", request.getCustomerEmail());

        request.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        assertEquals("http://www.example.com/success", request.getReturnSuccessUrl());

        request.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        assertEquals("http://www.example.com/failure", request.getReturnFailureUrl());

        String consumerReference = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        request.setConsumerReference(consumerReference);
        assertEquals(consumerReference, request.getConsumerReference());

        String nationalId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        request.setNationalId(nationalId);
        assertEquals(nationalId, request.getNationalId());

        String birthDate = today.minusYears(random.nextInt(100)).format(formatter);
        request.setBirthDate(birthDate);
        assertEquals(birthDate, request.getBirthDate());

        String billingPrimaryAddress = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        request.setBillingPrimaryAddress(billingPrimaryAddress);
        assertEquals(billingPrimaryAddress, request.getBillingPrimaryAddress());

        String billingSecondaryAddress = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        request.setBillingSecondaryAddress(billingSecondaryAddress);
        assertEquals(billingSecondaryAddress, request.getBillingSecondary());

        String billingFirstName = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        request.setBillingFirstname(billingFirstName);
        assertEquals(billingFirstName, request.getBillingFirstName());

        String billingLastName = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        request.setBillingLastname(billingLastName);
        assertEquals(billingLastName, request.getBillingLastName());

        String billingCity = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        request.setBillingCity(billingCity);
        assertEquals(billingCity, request.getBillingCity());

        request.setBillingCountry(countryCode);
        assertEquals(countryCode, request.getBillingCountryCode());

        String billingZipCode = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        request.setBillingZipCode(billingZipCode);
        assertEquals(billingZipCode, request.getBillingZipCode());

        String billingState = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        request.setBillingState(billingState);
        assertEquals(billingState, request.getBillingState());

        assertTrue(request.toXML() instanceof String);
        assertTrue(request.toQueryString("") instanceof String);
    }

    @Test (expected = RequiredParamsException.class)
    public void testMissingParams(){
        BalotoRequest balotoRequest = new BalotoRequest();
        balotoRequest.setAmount(new BigDecimal("20.00"));
        balotoRequest.toXML();
    }

    @Test (expected = RegexException.class)
    public void testWrongBirthDate() throws MalformedURLException {
        BalotoRequest balotoRequest = new BalotoRequest();
        stubAndVerify(balotoRequest, balotoRequest.getAllowedBillingCountries().get(0));
        balotoRequest.setBirthDate("2020-10-44");
        balotoRequest.toXML();
    }

    @Test
    public void testBaloto() throws MalformedURLException {
        BalotoRequest balotoRequest = new BalotoRequest();
        stubAndVerify(balotoRequest, balotoRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testBalotoWrongCountry() throws MalformedURLException {
        BalotoRequest balotoRequest = new BalotoRequest();
        stubAndVerify(balotoRequest, balotoRequest.getAllowedBillingCountries().get(0));
        balotoRequest.setBillingCountry(Country.Germany.getCode());
        balotoRequest.toXML();
    }

    @Test
    public void testBancodeOccidente() throws MalformedURLException {
        BancodeOccidenteRequest bancodeOccidenteRequest = new BancodeOccidenteRequest();
        stubAndVerify(bancodeOccidenteRequest, bancodeOccidenteRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testBancodeOccidenteWrongCountry() throws MalformedURLException {
        BancodeOccidenteRequest bancodeOccidenteRequest = new BancodeOccidenteRequest();
        stubAndVerify(bancodeOccidenteRequest, bancodeOccidenteRequest.getAllowedBillingCountries().get(0));
        bancodeOccidenteRequest.setBillingCountry(Country.Germany.getCode());
        bancodeOccidenteRequest.toXML();
    }

    @Test
    public void testBoleto() throws MalformedURLException {
        BoletoRequest boletoRequest = new BoletoRequest();
        stubAndVerify(boletoRequest, boletoRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testBoletoWrongCountry() throws MalformedURLException {
        BoletoRequest boletoRequest = new BoletoRequest();
        stubAndVerify(boletoRequest, boletoRequest.getAllowedBillingCountries().get(0));
        boletoRequest.setBillingCountry(Country.Germany.getCode());
        boletoRequest.toXML();
    }

    @Test
    public void testEfecty() throws MalformedURLException {
        EfectyRequest efectyRequest = new EfectyRequest();
        stubAndVerify(efectyRequest, efectyRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testEfectyWrongCountry() throws MalformedURLException {
        EfectyRequest efectyRequest = new EfectyRequest();
        stubAndVerify(efectyRequest, efectyRequest.getAllowedBillingCountries().get(0));
        efectyRequest.setBillingCountry(Country.Germany.getCode());
        efectyRequest.toXML();
    }

    @Test
    public void testOXXO() throws MalformedURLException {
        OXXORequest oxxoRequest = new OXXORequest();
        stubAndVerify(oxxoRequest, oxxoRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testOXXOWrongCountry() throws MalformedURLException {
        OXXORequest oxxoRequest = new OXXORequest();
        stubAndVerify(oxxoRequest, oxxoRequest.getAllowedBillingCountries().get(0));
        oxxoRequest.setBillingCountry(Country.Germany.getCode());
        oxxoRequest.toXML();
    }

    @Test
    public void testPagoFacil() throws MalformedURLException {
        PagoFacilRequest pagoFacilRequest = new PagoFacilRequest();
        stubAndVerify(pagoFacilRequest, pagoFacilRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testPagoFacilWrongCountry() throws MalformedURLException {
        PagoFacilRequest pagoFacilRequest = new PagoFacilRequest();
        stubAndVerify(pagoFacilRequest, pagoFacilRequest.getAllowedBillingCountries().get(0));
        pagoFacilRequest.setBillingCountry(Country.Germany.getCode());
        pagoFacilRequest.toXML();
    }

    @Test
    public void testRedpagos() throws MalformedURLException {
        RedpagosRequest redpagosRequest = new RedpagosRequest();
        stubAndVerify(redpagosRequest, redpagosRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testRedpagosWrongCountry() throws MalformedURLException {
        RedpagosRequest redpagosRequest = new RedpagosRequest();
        stubAndVerify(redpagosRequest, redpagosRequest.getAllowedBillingCountries().get(0));
        redpagosRequest.setBillingCountry(Country.Germany.getCode());
        redpagosRequest.toXML();
    }
}