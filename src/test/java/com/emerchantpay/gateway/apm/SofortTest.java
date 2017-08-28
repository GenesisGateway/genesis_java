package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.SofortRequest;
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

public class SofortTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private SofortRequest sofort = new SofortRequest();

    @Before
    public void createSofort() throws MalformedURLException {

        uniqueId = new StringUtils().generateUID();

        // Sofort
        sofort.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        sofort.setBillingPrimaryAddress("Berlin");
        sofort.setBillingSecondaryAddress("Berlin");
        sofort.setBillingFirstname("Plamen");
        sofort.setBillingLastname("Petrov");
        sofort.setBillingCity("Berlin");
        sofort.setBillingCountry("DE");
        sofort.setBillingZipCode("M4B1B3");
        sofort.setBillingState("BE");
    }

    public void setMissingParams() {
        sofort.setBillingCountry(null);
    }

    @Test
    public void testSofort() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = sofort.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", sofort.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), sofort.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), sofort.getBillingAddress().getElements());
    }

    @Test
    public void testSofortWithMissingParams()  {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = sofort.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), sofort.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}