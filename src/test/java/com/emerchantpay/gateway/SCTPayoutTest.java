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
        mappedParams = new HashMap<String, Object>();
        uidPayout = new StringUtils().generateUID();

        // SCT Payout
        sctPayout.setTransactionId(uidPayout).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        sctPayout.setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency());
        sctPayout.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        sctPayout.setIban("DE09100100101234567894").setBic("PBNKDEFFXXX");

        // Billing Address
        sctPayout.setBillingPrimaryAddress("New York").setBillingSecondaryAddress("Dallas")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("1000").setBillingState("NY");

        // Shipping Address
        sctPayout.setShippingPrimaryAddress("New York").setShippingSecondaryAddress("Dallas")
                .setShippingFirstname("Plamen").setShippingLastname("Petrov")
                .setShippingCity("Berlin").setShippingCountry(Country.Germany.getCode())
                .setShippingZipCode("1000").setShippingState("NY");

        mappedParams.put("base_attributes", sctPayout.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sctPayout.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", sctPayout.buildCustomerInfoParams().getElements());
    }

    @Test
    public void testSCTPayout() {

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

        assertEquals(mappedParams.get("base_attributes"), sctPayout.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sctPayout.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), sctPayout.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("billing_address"), sctPayout.getBillingAddress().getElements());
    }
}