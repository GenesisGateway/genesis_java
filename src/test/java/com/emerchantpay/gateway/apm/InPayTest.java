package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
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
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // InPay
        inPay.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        inPay.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        inPay.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        inPay.setReturnSuccessUrl(new URL("https://example.com/return_success_url"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure_url"));

        inPay.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE");

        mappedParams.put("base_attributes", inPay.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", inPay.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", inPay.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  inPay.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        inPay.setBillingCountry(null);
    }

    @Test
    public void testInPay() throws MalformedURLException {

        elements = inPay.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", inPay.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), inPay.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), inPay.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), inPay.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), inPay.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), inPay.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("is_payout"), null);
        assertEquals(mappedParams.get("billing_address"), inPay.getBillingAddress().getElements());
    }

    @Test(expected = RequiredParamsException.class)
    public void testInPayWithMissingParams() {

        setMissingParams();

        elements = inPay.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), inPay.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}