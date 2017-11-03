package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.TrustlySaleRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.TrustlyWithdrawalRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TrustlyTest {

    private TrustlySaleRequest trustlySale = new TrustlySaleRequest();
    private TrustlyWithdrawalRequest trustlyWithdrawal  = new TrustlyWithdrawalRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidSale;
    private String uidWithdrawal;

    @Before
    public void createSale() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidSale = new StringUtils().generateUID();

        // Sale
        trustlySale.setTransactionId(uidSale).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        trustlySale.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        trustlySale.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        trustlySale.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        trustlySale.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE");

        mappedParams.put("base_attributes", trustlySale.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", trustlySale.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", trustlySale.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  trustlySale.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        trustlySale.setBillingCountry(null);
        trustlyWithdrawal.setBillingCountry(null);
    }

    @Before
    public void createWithdrawal() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidWithdrawal = new StringUtils().generateUID();

        // Withdrawal
        trustlyWithdrawal.setTransactionId(uidWithdrawal).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        trustlyWithdrawal.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        trustlyWithdrawal.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        trustlyWithdrawal.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));
        trustlyWithdrawal.setBirthDate("24-04-1988");

        trustlyWithdrawal.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE");

        mappedParams.put("base_attributes", trustlyWithdrawal.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", trustlyWithdrawal.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", trustlyWithdrawal.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  trustlyWithdrawal.buildAsyncParams().getElements());
    }

    @Test
    public void testSale() throws MalformedURLException {

        elements = trustlySale.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", trustlySale.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), trustlySale.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), trustlySale.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), trustlySale.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), trustlySale.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), trustlySale.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("billing_address"), trustlySale.getBillingAddress().getElements());
    }

    @Test
    public void testWithdrawal() throws MalformedURLException {

        elements = trustlyWithdrawal.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", trustlyWithdrawal.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), trustlyWithdrawal.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), trustlyWithdrawal.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), trustlyWithdrawal.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), trustlyWithdrawal.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), trustlyWithdrawal.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("billing_address"), trustlyWithdrawal.getBillingAddress().getElements());
    }

    @Test
    public void testSaleWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = trustlySale.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), trustlySale.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testWithdrawalWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = trustlyWithdrawal.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), trustlySale.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}