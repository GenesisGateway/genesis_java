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

        uniqueId = new StringUtils().generateUID();

        // AbnIDeal
        abnIDeal.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("20.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")).setCustomerBankId("INGBNL2A");

        abnIDeal.setBillingPrimaryAddress("Amsterdam Street 1");
        abnIDeal.setBillingSecondaryAddress("Amsterdam Street 2");
        abnIDeal.setBillingFirstname("Plamen");
        abnIDeal.setBillingLastname("Petrov");
        abnIDeal.setBillingCity("Amsterdam");
        abnIDeal.setBillingCountry("NL");
        abnIDeal.setBillingZipCode("NLB1B3");
        abnIDeal.setBillingState("NL");
    }

    public void setMissingParams() {
        abnIDeal.setCustomerBankId(null);
    }

    @Test
    public void testAbnIDeal() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();

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

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("2000"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("customer_bank_id"), "INGBNL2A");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), abnIDeal.getBillingAddress().getElements());
    }

    @Test
    public void testAbnIDealWithMissingParams() throws MalformedURLException {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();

        elements = abnIDeal.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), abnIDeal.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("customer_bank_id"));
    }
}