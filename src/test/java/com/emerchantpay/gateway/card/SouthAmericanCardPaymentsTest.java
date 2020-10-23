package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.SouthAmericanPaymentRequest;
import com.emerchantpay.gateway.api.requests.financial.card.southamerican.*;
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


public class SouthAmericanCardPaymentsTest {

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
        TarjetaShoppingRequest tarjetaShoppingRequest = new TarjetaShoppingRequest();
        tarjetaShoppingRequest.setAmount(new BigDecimal("20.00"));
        tarjetaShoppingRequest.toXML();
    }

    @Test (expected = RegexException.class)
    public void testWrongBirthDate() throws MalformedURLException {
        TarjetaShoppingRequest tarjetaShoppingRequest = new TarjetaShoppingRequest();
        stubAndVerify(tarjetaShoppingRequest, tarjetaShoppingRequest.getAllowedBillingCountries().get(0));
        tarjetaShoppingRequest.setBirthDate("2020-10-44");
        tarjetaShoppingRequest.toXML();
    }

    @Test
    public void testTarjetaShopping() throws MalformedURLException {
        TarjetaShoppingRequest tarjetaShoppingRequest = new TarjetaShoppingRequest();
        stubAndVerify(tarjetaShoppingRequest, tarjetaShoppingRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testTarjetaShoppingWrongCountry() throws MalformedURLException {
        TarjetaShoppingRequest tarjetaShoppingRequest = new TarjetaShoppingRequest();
        stubAndVerify(tarjetaShoppingRequest, tarjetaShoppingRequest.getAllowedBillingCountries().get(0));
        tarjetaShoppingRequest.setBillingCountry(Country.Germany.getCode());
        tarjetaShoppingRequest.toXML();
    }

    @Test
    public void testArgencard() throws MalformedURLException {
        ArgencardRequest argencardRequest = new ArgencardRequest();
        stubAndVerify(argencardRequest, argencardRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testArgencardWrongCountry() throws MalformedURLException {
        ArgencardRequest argencardRequest = new ArgencardRequest();
        stubAndVerify(argencardRequest, argencardRequest.getAllowedBillingCountries().get(0));
        argencardRequest.setBillingCountry(Country.Germany.getCode());
        argencardRequest.toXML();
    }

    @Test
    public void testAura() throws MalformedURLException {
        AuraRequest auraRequest = new AuraRequest();
        stubAndVerify(auraRequest, auraRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testAuraWrongCountry() throws MalformedURLException {
        AuraRequest auraRequest = new AuraRequest();
        stubAndVerify(auraRequest, auraRequest.getAllowedBillingCountries().get(0));
        auraRequest.setBillingCountry(Country.Germany.getCode());
        auraRequest.toXML();
    }

    @Test
    public void testCabal() throws MalformedURLException {
        CabalRequest cabalRequest = new CabalRequest();
        stubAndVerify(cabalRequest, cabalRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testCabalWrongCountry() throws MalformedURLException {
        CabalRequest cabalRequest = new CabalRequest();
        stubAndVerify(cabalRequest, cabalRequest.getAllowedBillingCountries().get(0));
        cabalRequest.setBillingCountry(Country.Germany.getCode());
        cabalRequest.toXML();
    }

    @Test
    public void testElo() throws MalformedURLException {
        EloRequest eloRequest = new EloRequest();
        stubAndVerify(eloRequest, eloRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testEloWrongCountry() throws MalformedURLException {
        EloRequest eloRequest = new EloRequest();
        stubAndVerify(eloRequest, eloRequest.getAllowedBillingCountries().get(0));
        eloRequest.setBillingCountry(Country.Germany.getCode());
        eloRequest.toXML();
    }

    @Test
    public void testCencosud() throws MalformedURLException {
        CencosudRequest cencosudRequest = new CencosudRequest();
        stubAndVerify(cencosudRequest, cencosudRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testCencosudWrongCountry() throws MalformedURLException {
        CencosudRequest cencosudRequest = new CencosudRequest();
        stubAndVerify(cencosudRequest, cencosudRequest.getAllowedBillingCountries().get(0));
        cencosudRequest.setBillingCountry(Country.Germany.getCode());
        cencosudRequest.toXML();
    }

    @Test
    public void testNativa() throws MalformedURLException {
        NativaRequest nativaRequest = new NativaRequest();
        stubAndVerify(nativaRequest, nativaRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testNativaWrongCountry() throws MalformedURLException {
        NativaRequest nativaRequest = new NativaRequest();
        stubAndVerify(nativaRequest, nativaRequest.getAllowedBillingCountries().get(0));
        nativaRequest.setBillingCountry(Country.Germany.getCode());
        nativaRequest.toXML();
    }

    @Test
    public void testNaranja() throws MalformedURLException {
        NaranjaRequest naranjaRequest = new NaranjaRequest();
        stubAndVerify(naranjaRequest, naranjaRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testNaranjaWrongCountry() throws MalformedURLException {
        NaranjaRequest naranjaRequest = new NaranjaRequest();
        stubAndVerify(naranjaRequest, naranjaRequest.getAllowedBillingCountries().get(0));
        naranjaRequest.setBillingCountry(Country.Germany.getCode());
        naranjaRequest.toXML();
    }
}