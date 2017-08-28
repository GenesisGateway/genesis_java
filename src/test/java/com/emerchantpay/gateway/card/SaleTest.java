package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;
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


public class SaleTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private SaleRequest sale = new SaleRequest();

    @Before
    public void createSale() {
        uniqueId = new StringUtils().generateUID();

        // Sale
        sale.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency())
                .setCardholder("PLAMEN PETROV").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123").setCustomerEmail("john@example.com")
                .setCustomerPhone("+5555555555");

        sale.setBillingPrimaryAddress("Address1");
        sale.setBillingSecondaryAddress("Address2");
        sale.setBillingFirstname("John");
        sale.setBillingLastname("Doe");
        sale.setBillingCity("New York");
        sale.setBillingCountry("US");
        sale.setBillingZipCode("1000");
        sale.setBillingState("NY");
    }

    public void setMissingParams() {
        sale.setBillingCountry(null);
    }

    @Test
    public void testSale() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = sale.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", sale.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), sale.getElements().get(i).getValue());
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
        assertEquals(mappedParams.get("billing_address"), sale.getBillingAddress().getElements());
    }

    @Test
    public void testSaleWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = sale.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), sale.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}