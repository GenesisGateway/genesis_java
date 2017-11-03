package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.AbnIDealRequest;
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

public class AbnIDealTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private AbnIDealRequest abnIDeal = new AbnIDealRequest();

    @Before
    public void createAbnIDeal() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // AbnIDeal
        abnIDeal.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        abnIDeal.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("20.00"));
        abnIDeal.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        abnIDeal.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));
        abnIDeal.setCustomerBankId("INGBNL2A");

        abnIDeal.setBillingPrimaryAddress("Amsterdam Street 1").setBillingSecondaryAddress("Amsterdam Street 2")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov").setBillingCity("Amsterdam")
                .setBillingCountry("NL").setBillingZipCode("NLB1B3").setBillingState("NL");

        mappedParams.put("base_attributes", abnIDeal.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", abnIDeal.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", abnIDeal.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  abnIDeal.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        abnIDeal.setCustomerBankId(null);
    }

    @Test
    public void testAbnIDeal() throws MalformedURLException {

        elements = abnIDeal.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", abnIDeal.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), abnIDeal.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), abnIDeal.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), abnIDeal.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), abnIDeal.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), abnIDeal.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("customer_bank_id"), "INGBNL2A");
        assertEquals(mappedParams.get("billing_address"), abnIDeal.getBillingAddress().getElements());
    }

    @Test
    public void testAbnIDealWithMissingParams() throws MalformedURLException {

        setMissingParams();

        elements = abnIDeal.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), abnIDeal.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("customer_bank_id"));
    }
}