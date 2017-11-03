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

        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // CashU
        cashU.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        cashU.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("15.00"));
        cashU.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        cashU.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        cashU.setBillingPrimaryAddress("Fifth avenue 1").setBillingSecondaryAddress("Fifth avenue 2")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov").setBillingCity("New York")
                .setBillingCountry("US").setBillingZipCode("M4B1B3").setBillingState("WA");

        mappedParams.put("base_attributes", cashU.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", cashU.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", cashU.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  cashU.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        cashU.setBillingCountry(null);
    }

    @Test
    public void testCashU() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), cashU.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), cashU.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), cashU.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), cashU.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("billing_address"), cashU.getBillingAddress().getElements());
    }

    @Test
    public void testCashUWithMissingParams() {

        setMissingParams();

        elements = cashU.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), cashU.getBillingAddress().getElements().get(i).getValue());
        }

        System.out.println(mappedParams.get("country"));
        assertNull(mappedParams.get("country"));
    }
}