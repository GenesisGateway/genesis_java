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
                .setReturnFailureUrl(new URL("http://www.example.com/failure")).billingAddress().setAddress1("Fifth avenue 1")
                .setAddress2("Fifth avenue 2").setFirstname("Plamen").setLastname("Petrov").setCity("New York").setCountry("US")
                .setZipCode("M4B1B3").setState("WA").done();
    }

    public void setMissingParams() {
        cashU.billingAddress().setCountry(null).done();
    }

    @Test
    public void testCashU() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();
        elements = cashU.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), cashU.getElements().get(i).getValue());
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
        assertEquals(mappedParams.get("billing_address"), cashU.getBillingAddress());
    }

    @Test
    public void testCashUWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = cashU.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), cashU.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
