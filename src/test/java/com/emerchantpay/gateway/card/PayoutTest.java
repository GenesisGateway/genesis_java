package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.requests.financial.card.PayoutRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class PayoutTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private PayoutRequest payout = new PayoutRequest();

    @Before
    public void createPayout() {
        uniqueId = new StringUtils().generateUID();

        // Payout
        payout.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency())
                .setCardholder("PLAMEN PETROV").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123").setCustomerEmail("john@example.com")
                .setCustomerPhone("+5555555555");

        payout.setBillingPrimaryAddress("Address1");
        payout.setBillingSecondaryAddress("Address2");
        payout.setBillingFirstname("John");
        payout.setBillingLastname("Doe");
        payout.setBillingCity("New York");
        payout.setBillingCountry("US");
        payout.setBillingZipCode("1000");
        payout.setBillingState("NY");
    }

    public void setMissingParams() {
        payout.setBillingCountry(null);
    }

    @Test
    public void testPayout() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = payout.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", payout.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), payout.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("2200"));
        assertEquals(mappedParams.get("card_number"), "4200000000000000");
        assertEquals(mappedParams.get("card_holder"), "PLAMEN PETROV");
        assertEquals(mappedParams.get("expiration_month"), "02");
        assertEquals(mappedParams.get("expiration_year"), "2020");
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+5555555555");
        assertEquals(mappedParams.get("billing_address"), payout.getBillingAddress().getElements());
    }

    @Test
    public void testPayoutWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = payout.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), payout.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}