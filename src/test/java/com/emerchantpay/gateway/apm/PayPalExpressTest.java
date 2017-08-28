package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.PayPalExpressRequest;
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

public class PayPalExpressTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private PayPalExpressRequest paypalExpress = new PayPalExpressRequest();

    @Before
    public void createPayPal() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // Checkout
        paypalExpress.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        paypalExpress.setBillingPrimaryAddress("Berlin");
        paypalExpress.setBillingSecondaryAddress("Berlin");
        paypalExpress.setBillingFirstname("Plamen");
        paypalExpress.setBillingLastname("Petrov");
        paypalExpress.setBillingCity("Berlin");
        paypalExpress.setBillingCountry("DE");
        paypalExpress.setBillingZipCode("M4B1B3");
        paypalExpress.setBillingState("BE");
    }

    public void setMissingParams() {
        paypalExpress.setBillingCountry(null);
    }

    @Test
    public void testPayPalExpress() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();

        elements = paypalExpress.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", paypalExpress.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), paypalExpress.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), paypalExpress.getBillingAddress().getElements());
    }

    @Test
    public void testPayPalUWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = paypalExpress.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), paypalExpress.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}