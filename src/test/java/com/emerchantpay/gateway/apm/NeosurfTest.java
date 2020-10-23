package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.apm.NeosurfRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NeosurfTest {

    private NeosurfRequest neosurfRequest;
    private String uid;
    private Random random;
    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND_NINE = 9;
    private static final int MAX_BOUND_NINETEEN = 19;

    @Before
    public void createNeosurf() {
        neosurfRequest = new NeosurfRequest();
        uid = new StringUtils().generateUID();
        random = new Random();
    }

    @Test
    public void testNeosurf(){

        neosurfRequest.setTransactionId(uid);
        assertEquals(uid, neosurfRequest.getTransactionId());

        neosurfRequest.setRemoteIp("82.137.112.202");
        assertEquals("82.137.112.202", neosurfRequest.getRemoteIp());

        String usage = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        neosurfRequest.setUsage(usage);
        assertEquals(usage, neosurfRequest.getUsage());

        BigDecimal amount = new BigDecimal(10);
        neosurfRequest.setAmount(amount);
        assertEquals(amount, neosurfRequest.getAmount());

        neosurfRequest.setCurrency(Currency.EUR.getCurrency());
        assertEquals(Currency.EUR.getCurrency(), neosurfRequest.getCurrency());

        String voucherNumber = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setVoucherNumber(voucherNumber);
        assertEquals(voucherNumber, neosurfRequest.getVoucherNumber());

        neosurfRequest.setCustomerPhone("+55555555");
        assertEquals("+55555555", neosurfRequest.getCustomerPhone());

        neosurfRequest.setCustomerEmail("john.doe@emerchantpay.com");
        assertEquals("john.doe@emerchantpay.com", neosurfRequest.getCustomerEmail());

        String billingPrimaryAddress = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setBillingPrimaryAddress(billingPrimaryAddress);
        assertEquals(billingPrimaryAddress, neosurfRequest.getBillingPrimaryAddress());

        String billingSecondaryAddress = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setBillingSecondaryAddress(billingSecondaryAddress);
        assertEquals(billingSecondaryAddress, neosurfRequest.getBillingSecondary());

        String billingFirstName = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setBillingFirstname(billingFirstName);
        assertEquals(billingFirstName, neosurfRequest.getBillingFirstName());

        String billingLastName = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setBillingLastname(billingLastName);
        assertEquals(billingLastName, neosurfRequest.getBillingLastName());

        String billingCity = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setBillingCity(billingCity);
        assertEquals(billingCity, neosurfRequest.getBillingCity());

        neosurfRequest.setBillingCountry(Country.Germany.getCode());
        assertEquals(Country.Germany.getCode(), neosurfRequest.getBillingCountryCode());

        String billingZipCode = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setBillingZipCode(billingZipCode);
        assertEquals(billingZipCode, neosurfRequest.getBillingZipCode());

        String billingState = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINE) + MIN_BOUND);
        neosurfRequest.setBillingState(billingState);
        assertEquals(billingState, neosurfRequest.getBillingState());

        assertTrue(neosurfRequest.toXML() instanceof String);
        assertTrue(neosurfRequest.toQueryString("") instanceof String);
    }

    @Test (expected = RequiredParamsException.class)
    public void testNeosurfWithMissingParams(){
        neosurfRequest.setAmount(new BigDecimal("20.00"));
        neosurfRequest.toXML();
    }

    @Test (expected = RegexException.class)
    public void testNeosurfWithWrongVoucher(){
        neosurfRequest.setTransactionId(uid);
        neosurfRequest.setAmount(new BigDecimal("20.00"));
        neosurfRequest.setCurrency(Currency.EUR.getCurrency());
        neosurfRequest.setVoucherNumber("ABC*162");
        neosurfRequest.setRemoteIp("82.137.112.202");
        neosurfRequest.toXML();
    }

}
