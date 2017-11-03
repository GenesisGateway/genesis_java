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
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Payout
        payout.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        payout.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency());
        payout.setCardHolder("PLAMEN PETROV").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123");
        payout.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555");

        payout.setBillingPrimaryAddress("Address1")
                .setBillingSecondaryAddress("Address2")
                .setBillingFirstname("John")
                .setBillingLastname("Doe")
                .setBillingCity("New York")
                .setBillingCountry("US")
                .setBillingZipCode("1000")
                .setBillingState("NY");

        mappedParams.put("base_attributes", payout.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", payout.buildPaymentParams().getElements());
        mappedParams.put("credit_card_attributes", payout.buildCreditCardParams().getElements());
        mappedParams.put("customer_info_attributes", payout.buildCustomerInfoParams().getElements());
    }

    public void setMissingParams() {
        payout.setBillingCountry(null);
    }

    @Test
    public void testPayout() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), payout.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), payout.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("credit_card_attributes"), payout.buildCreditCardParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), payout.buildCustomerInfoParams().getElements());
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