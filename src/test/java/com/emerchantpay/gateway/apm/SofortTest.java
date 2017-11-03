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
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Sofort
        sofort.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        sofort.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        sofort.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        sofort.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        sofort.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE");

        mappedParams.put("base_attributes", sofort.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sofort.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", sofort.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  sofort.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        sofort.setBillingCountry(null);
    }

    @Test
    public void testSofort() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), sofort.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sofort.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), sofort.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), sofort.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("billing_address"), sofort.getBillingAddress().getElements());
    }

    @Test
    public void testSofortWithMissingParams()  {

        setMissingParams();

        elements = sofort.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), sofort.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}