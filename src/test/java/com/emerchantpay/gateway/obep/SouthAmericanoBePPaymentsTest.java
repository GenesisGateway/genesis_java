package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.SouthAmericanPaymentRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.southamerican.*;
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


public class SouthAmericanoBePPaymentsTest {

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
        BancodoBrasilRequest bancodoBrasilRequest = new BancodoBrasilRequest();
        bancodoBrasilRequest.setAmount(new BigDecimal("20.00"));
        bancodoBrasilRequest.toXML();
    }

    @Test (expected = RegexException.class)
    public void testWrongBirthDate() throws MalformedURLException {
        BancodoBrasilRequest bancodoBrasilRequest = new BancodoBrasilRequest();
        stubAndVerify(bancodoBrasilRequest, bancodoBrasilRequest.getAllowedBillingCountries().get(0));
        bancodoBrasilRequest.setBirthDate("2020-10-44");
        bancodoBrasilRequest.toXML();
    }

    @Test
    public void testBancodoBrasil() throws MalformedURLException {
        BancodoBrasilRequest bancodoBrasilRequest = new BancodoBrasilRequest();
        stubAndVerify(bancodoBrasilRequest, bancodoBrasilRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testBancodoBrasilWrongCountry() throws MalformedURLException {
        BancodoBrasilRequest bancodoBrasilRequest = new BancodoBrasilRequest();
        stubAndVerify(bancodoBrasilRequest, bancodoBrasilRequest.getAllowedBillingCountries().get(0));
        bancodoBrasilRequest.setBillingCountry(Country.Germany.getCode());
        bancodoBrasilRequest.toXML();
    }

    @Test
    public void testBancomer() throws MalformedURLException {
        BancomerRequest bancomerRequest = new BancomerRequest();
        stubAndVerify(bancomerRequest, bancomerRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testBancomerWrongCountry() throws MalformedURLException {
        BancomerRequest bancomerRequest = new BancomerRequest();
        stubAndVerify(bancomerRequest, bancomerRequest.getAllowedBillingCountries().get(0));
        bancomerRequest.setBillingCountry(Country.Germany.getCode());
        bancomerRequest.toXML();
    }

    @Test
    public void testBradesco() throws MalformedURLException {
        BradescoRequest bradescoRequest = new BradescoRequest();
        stubAndVerify(bradescoRequest, bradescoRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testBradescoWrongCountry() throws MalformedURLException {
        BradescoRequest bradescoRequest = new BradescoRequest();
        stubAndVerify(bradescoRequest, bradescoRequest.getAllowedBillingCountries().get(0));
        bradescoRequest.setBillingCountry(Country.Germany.getCode());
        bradescoRequest.toXML();
    }

    @Test
    public void testDavivienda() throws MalformedURLException {
        DaviviendaRequest daviviendaRequest = new DaviviendaRequest();
        stubAndVerify(daviviendaRequest, daviviendaRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testDaviviendaWrongCountry() throws MalformedURLException {
        DaviviendaRequest daviviendaRequest = new DaviviendaRequest();
        stubAndVerify(daviviendaRequest, daviviendaRequest.getAllowedBillingCountries().get(0));
        daviviendaRequest.setBillingCountry(Country.Germany.getCode());
        daviviendaRequest.toXML();
    }

    @Test
    public void testItau() throws MalformedURLException {
        ItauRequest itauRequest = new ItauRequest();
        stubAndVerify(itauRequest, itauRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testItauWrongCountry() throws MalformedURLException {
        ItauRequest itauRequest = new ItauRequest();
        stubAndVerify(itauRequest, itauRequest.getAllowedBillingCountries().get(0));
        itauRequest.setBillingCountry(Country.Germany.getCode());
        itauRequest.toXML();
    }

    @Test
    public void testPSE() throws MalformedURLException {
        PSERequest pseRequest = new PSERequest();
        stubAndVerify(pseRequest, pseRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testPSEWrongCountry() throws MalformedURLException {
        PSERequest pseRequest = new PSERequest();
        stubAndVerify(pseRequest, pseRequest.getAllowedBillingCountries().get(0));
        pseRequest.setBillingCountry(Country.Germany.getCode());
        pseRequest.toXML();
    }

    @Test
    public void testRapiPago() throws MalformedURLException {
        RapiPagoRequest rapiPagoRequest = new RapiPagoRequest();
        stubAndVerify(rapiPagoRequest, rapiPagoRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testRapiPagoWrongCountry() throws MalformedURLException {
        RapiPagoRequest rapiPagoRequest = new RapiPagoRequest();
        stubAndVerify(rapiPagoRequest, rapiPagoRequest.getAllowedBillingCountries().get(0));
        rapiPagoRequest.setBillingCountry(Country.Germany.getCode());
        rapiPagoRequest.toXML();
    }

    @Test
    public void testSantander() throws MalformedURLException {
        SantanderRequest santanderRequest = new SantanderRequest();
        stubAndVerify(santanderRequest, santanderRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testSantanderWrongCountry() throws MalformedURLException {
        SantanderRequest santanderRequest = new SantanderRequest();
        stubAndVerify(santanderRequest, santanderRequest.getAllowedBillingCountries().get(0));
        santanderRequest.setBillingCountry(Country.Germany.getCode());
        santanderRequest.toXML();
    }

    @Test
    public void testWebpay() throws MalformedURLException {
        WebpayRequest webpayRequest = new WebpayRequest();
        stubAndVerify(webpayRequest, webpayRequest.getAllowedBillingCountries().get(0));
    }

    @Test (expected = InvalidParamException.class)
    public void testWebpayWrongCountry() throws MalformedURLException {
        WebpayRequest webpayRequest = new WebpayRequest();
        stubAndVerify(webpayRequest, webpayRequest.getAllowedBillingCountries().get(0));
        webpayRequest.setBillingCountry(Country.Germany.getCode());
        webpayRequest.toXML();
    }
}