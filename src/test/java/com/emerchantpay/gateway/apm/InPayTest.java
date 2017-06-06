package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.InPayRequest;
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

public class InPayTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;


    private InPayRequest inPay = new InPayRequest();

    @Before
    public void createInPay() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // InPay
        inPay.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"))
                .setCustomerEmail("john@example.com").setCustomerPhone("+55555555")
                .setReturnSuccessUrl(new URL("https://example.com/return_success_url"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure_url"))
                .billingAddress().setAddress1("Berlin").setAddress2("Berlin").setFirstname("Plamen")
                .setLastname("Petrov").setCity("Berlin").setCountry("DE").setZipCode("M4B1B3").setState("BE").done();
    }

    public void setMissingParams() {
        inPay.billingAddress().setCountry(null).done();
    }

    @Test
    public void testInPay() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = inPay.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), inPay.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("is_payout"), null);
        assertEquals(mappedParams.get("return_success_url"),  new URL("https://example.com/return_success_url"));
        assertEquals(mappedParams.get("return_failure_url"),  new URL("https://example.com/return_failure_url"));
        assertEquals(mappedParams.get("billing_address"), inPay.getBillingAddress());
    }

    @Test
    public void testInPayWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = inPay.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), inPay.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
