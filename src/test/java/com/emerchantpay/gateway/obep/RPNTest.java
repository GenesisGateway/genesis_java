package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.requests.financial.oBeP.RPNRequest;
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

public class RPNTest {

    private RPNRequest rpnRequest = new RPNRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uid;

    @Before
    public void createRPNPay() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uid = new StringUtils().generateUID();

        // RPN
        rpnRequest.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        rpnRequest.setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("1000.00"));
        rpnRequest.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555");
        rpnRequest.setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"));
        rpnRequest.setNotificationUrl(new URL("https://example.com/notification"));

        rpnRequest.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe").setBillingCity("Beijing")
                .setBillingCountry(Country.China.getCode()).setBillingZipCode("M4B1B3")
                .setBillingState("BJ");

        mappedParams.put("base_attributes", rpnRequest.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", rpnRequest.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", rpnRequest.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", rpnRequest.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        rpnRequest.setBillingCountry(null);
    }

    @Test
    public void testRPNPay() throws MalformedURLException {

        elements = rpnRequest.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", rpnRequest.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), rpnRequest.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), rpnRequest.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), rpnRequest.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), rpnRequest.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), rpnRequest.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), rpnRequest.getBillingAddress().getElements());
    }

    @Test
    public void testRPNWithMissingParams() {

        setMissingParams();

        elements = rpnRequest.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), rpnRequest.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}