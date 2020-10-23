package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.PayURequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PayUTest {

    private PayURequest payURequest;
    private String uid;
    private Random random;

    @Before
    public void createNeosurf() {
        payURequest = new PayURequest();
        uid = new StringUtils().generateUID();
        random = new Random();
    }

    @Test
    public void testNeosurf() throws MalformedURLException {

        payURequest.setTransactionId(uid);
        assertEquals(uid, payURequest.getTransactionId());

        payURequest.setRemoteIp("82.137.112.202");
        assertEquals("82.137.112.202", payURequest.getRemoteIp());

        String usage = RandomStringUtils.randomAlphanumeric(random.nextInt(19) + 1);
        payURequest.setUsage(usage);
        assertEquals(usage, payURequest.getUsage());

        BigDecimal amount = new BigDecimal(10);
        payURequest.setAmount(amount);
        assertEquals(amount, payURequest.getAmount());

        payURequest.setCurrency(Currency.PLN.getCurrency());
        assertEquals(Currency.PLN.getCurrency(), payURequest.getCurrency());

        payURequest.setCustomerEmail("john.doe@emerchantpay.com");
        assertEquals("john.doe@emerchantpay.com", payURequest.getCustomerEmail());

        payURequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        assertEquals("http://www.example.com/success", payURequest.getReturnSuccessUrl());

        payURequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        assertEquals("http://www.example.com/failure", payURequest.getReturnFailureUrl());

        String billingPrimaryAddress = RandomStringUtils.randomAlphanumeric(random.nextInt(9) + 1);
        payURequest.setBillingPrimaryAddress(billingPrimaryAddress);
        assertEquals(billingPrimaryAddress, payURequest.getBillingPrimaryAddress());

        String billingSecondaryAddress = RandomStringUtils.randomAlphanumeric(random.nextInt(9) + 1);
        payURequest.setBillingSecondaryAddress(billingSecondaryAddress);
        assertEquals(billingSecondaryAddress, payURequest.getBillingSecondary());

        String billingFirstName = RandomStringUtils.randomAlphanumeric(random.nextInt(9) + 1);
        payURequest.setBillingFirstname(billingFirstName);
        assertEquals(billingFirstName, payURequest.getBillingFirstName());

        String billingLastName = RandomStringUtils.randomAlphanumeric(random.nextInt(9) + 1);
        payURequest.setBillingLastname(billingLastName);
        assertEquals(billingLastName, payURequest.getBillingLastName());

        String billingCity = RandomStringUtils.randomAlphanumeric(random.nextInt(9) + 1);
        payURequest.setBillingCity(billingCity);
        assertEquals(billingCity, payURequest.getBillingCity());

        payURequest.setBillingCountry(Country.Poland.getCode());
        assertEquals(Country.Poland.getCode(), payURequest.getBillingCountryCode());

        String billingZipCode = RandomStringUtils.randomAlphanumeric(random.nextInt(9) + 1);
        payURequest.setBillingZipCode(billingZipCode);
        assertEquals(billingZipCode, payURequest.getBillingZipCode());

        String billingState = RandomStringUtils.randomAlphanumeric(random.nextInt(9) + 1);
        payURequest.setBillingState(billingState);
        assertEquals(billingState, payURequest.getBillingState());

        assertTrue(payURequest.toXML() instanceof String);
        assertTrue(payURequest.toQueryString("") instanceof String);
    }

    @Test (expected = RequiredParamsException.class)
    public void testNeosurfWithMissingParams(){
        payURequest.setAmount(new BigDecimal("20.00"));
        payURequest.toXML();
    }

    @Test (expected = InvalidParamException.class)
    public void testNeosurfWithWrongCurrency() throws MalformedURLException {
        payURequest.setTransactionId(uid);
        payURequest.setAmount(new BigDecimal("20.00"));
        payURequest.setCurrency(Currency.EUR.getCurrency());
        payURequest.setRemoteIp("82.137.112.202");
        payURequest.setBillingCountry(Country.CzechRepublic.getCode());
        payURequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        payURequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        payURequest.toXML();
    }

    @Test (expected = InvalidParamException.class)
    public void testNeosurfWithWrongCountry() throws MalformedURLException {
        payURequest.setTransactionId(uid);
        payURequest.setAmount(new BigDecimal("20.00"));
        payURequest.setCurrency(Currency.PLN.getCurrency());
        payURequest.setRemoteIp("82.137.112.202");
        payURequest.setBillingCountry(Country.Germany.getCode());
        payURequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        payURequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        payURequest.toXML();
    }
}
