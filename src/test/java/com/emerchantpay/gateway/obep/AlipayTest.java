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
        mappedParams = new HashMap<String, Object>();
        uid = new StringUtils().generateUID();

        // AliPay
        alipay.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        alipay.setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("1000.00"));
        alipay.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555");
        alipay.setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"));
        alipay.setNotificationUrl(new URL("https://example.com/notification"));

        alipay.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe").setBillingCity("Beijing")
                .setBillingCountry(Country.China.getCode()).setBillingZipCode("M4B1B3")
                .setBillingState("BJ");

        mappedParams.put("base_attributes", alipay.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", alipay.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", alipay.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", alipay.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        alipay.setBillingCountry(null);
    }

    @Test
    public void testAliPay() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), alipay.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), alipay.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), alipay.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), alipay.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), alipay.getBillingAddress().getElements());
    }

    @Test
    public void testAliPayWithMissingParams() {

        setMissingParams();

        elements = alipay.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), alipay.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}