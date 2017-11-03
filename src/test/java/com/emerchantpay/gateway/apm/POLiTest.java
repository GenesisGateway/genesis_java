package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.POLiRequest;
import com.emerchantpay.gateway.util.Country;
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

public class POLiTest {

    private POLiRequest poli = new POLiRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    @Before
    public void createPoli() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // POLi
        poli.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        poli.setCurrency(Currency.AUD.getCurrency()).setAmount(new BigDecimal("2.00"));
        poli.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        poli.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        poli.setBillingPrimaryAddress("Sydney 1").setBillingSecondaryAddress("Sydney 2")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Sydney").setBillingCountry(Country.Australia.getCode())
                .setBillingZipCode("S4C1C3").setBillingState("SY");

        mappedParams.put("base_attributes", poli.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", poli.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", poli.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  poli.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        poli.setBillingCountry(null);
    }

    @Test
    public void testPoli() throws MalformedURLException {

        elements = poli.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", poli.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), poli.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), poli.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), poli.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), poli.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), poli.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("billing_address"), poli.getBillingAddress().getElements());
    }

    @Test
    public void testPoliWithMissingParams() {

        setMissingParams();

        elements = poli.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), poli.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}