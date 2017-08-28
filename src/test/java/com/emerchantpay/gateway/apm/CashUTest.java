package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.CashURequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CashUTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private CashURequest  cashU = new CashURequest();

    @Before
    public void createCashU() throws MalformedURLException {

        uniqueId = new StringUtils().generateUID();

        // CashU
        cashU.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("15.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        cashU.setBillingPrimaryAddress("Fifth avenue 1");
        cashU.setBillingSecondaryAddress("Fifth avenue 2");
        cashU.setBillingFirstname("Plamen");
        cashU.setBillingLastname("Petrov");
        cashU.setBillingCity("New York");
        cashU.setBillingCountry("US");
        cashU.setBillingZipCode("M4B1B3");
        cashU.setBillingState("WA");
    }

    public void setMissingParams() {
        cashU.setBillingCountry(null);
    }

    @Test
    public void testCashU() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();
        elements = cashU.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", cashU.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), cashU.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("1500"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), cashU.getBillingAddress().getElements());
    }

    @Test
    public void testCashUWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = cashU.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), cashU.getBillingAddress().getElements().get(i).getValue());
        }

        System.out.println(mappedParams.get("country"));
        assertNull(mappedParams.get("country"));
    }
}