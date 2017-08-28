package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.requests.financial.sct.SCTPayoutRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SCTPayoutTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidPayout;

    private SCTPayoutRequest sctPayout = new SCTPayoutRequest();

    @Before
    public void createSCTPayout() {
        uidPayout = new StringUtils().generateUID();

        // SCT Payout
        sctPayout.setTransactionId(uidPayout).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency()).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setIBAN("DE09100100101234567894").setBIC("PBNKDEFFXXX");

        // Billing Address
        sctPayout.setBillingPrimaryAddress("New York");
        sctPayout.setBillingSecondaryAddress("Dallas");
        sctPayout.setBillingFirstname("Plamen");
        sctPayout.setBillingLastname("Petrov");
        sctPayout.setBillingCity("Berlin");
        sctPayout.setBillingCountry(Country.Germany.getCode());
        sctPayout.setBillingZipCode("1000");
        sctPayout.setBillingState("NY");

        // Shipping Address
        sctPayout.setShippingPrimaryAddress("New York");
        sctPayout.setShippingSecondaryAddress("Dallas");
        sctPayout.setShippingFirstname("Plamen");
        sctPayout.setShippingLastname("Petrov");
        sctPayout.setShippingCity("Berlin");
        sctPayout.setShippingCountry(Country.Germany.getCode());
        sctPayout.setShippingZipCode("1000");
        sctPayout.setShippingState("NY");
    }

    @Test
    public void testSCTPayout() {

        mappedParams = new HashMap<String, Object>();

        elements = sctPayout.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", sctPayout.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), sctPayout.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayout);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("billing_address"), sctPayout.getBillingAddress().getElements());
    }
}