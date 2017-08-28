package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.requests.financial.oBeP.AlipayRequest;
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

public class AlipayTest {

    private AlipayRequest alipay = new AlipayRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uid;

    @Before
    public void createAliPay() throws MalformedURLException {
        uid = new StringUtils().generateUID();

        // AliPay
        alipay.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("1000.00"))
                .setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"))
                .setNotificationUrl(new URL("https://example.com/notification"));

        alipay.setBillingPrimaryAddress("First Avenue");
        alipay.setBillingSecondaryAddress("Second Avenue");
        alipay.setBillingFirstname("John");
        alipay.setBillingLastname("Doe");
        alipay.setBillingCity("Beijing");
        alipay.setBillingCountry(Country.China.getCode());
        alipay.setBillingZipCode("M4B1B3");
        alipay.setBillingState("BJ");
    }

    public void setMissingParams() {
        alipay.setBillingCountry(null);
    }

    @Test
    public void testAliPay() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = alipay.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", alipay.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), alipay.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uid);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.CNY.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("100000"));
        assertEquals(mappedParams.get("customer_email"), "john.doe@emerchantpay.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("https://example.com/return_failure"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), alipay.getBillingAddress().getElements());
    }

    @Test
    public void testAliPayWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = alipay.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), alipay.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}