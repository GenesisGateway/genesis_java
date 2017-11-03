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
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Sale
        sale.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        sale.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency());
        sale.setCardHolder("PLAMEN PETROV").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123");
        sale.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555");

        sale.setBillingPrimaryAddress("Address1")
                .setBillingSecondaryAddress("Address2")
                .setBillingFirstname("John")
                .setBillingLastname("Doe").setBillingCity("New York")
                .setBillingCountry("US")
                .setBillingZipCode("1000")
                .setBillingState("NY");

        mappedParams.put("base_attributes", sale.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sale.buildPaymentParams().getElements());
        mappedParams.put("credit_card_attributes", sale.buildCreditCardParams().getElements());
        mappedParams.put("customer_info_attributes", sale.buildCustomerInfoParams().getElements());
    }

    public void setMissingParams() {
        sale.setBillingCountry(null);
    }

    @Test
    public void testSale() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), sale.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sale.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("credit_card_attributes"), sale.buildCreditCardParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), sale.buildCustomerInfoParams().getElements());
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