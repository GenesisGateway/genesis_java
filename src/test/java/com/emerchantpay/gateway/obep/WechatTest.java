package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.requests.financial.oBeP.WechatRequest;
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

public class WechatTest {

    private WechatRequest wechat = new WechatRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uid;

    @Before
    public void createWechat() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uid = new StringUtils().generateUID();

        // Wechat
        wechat.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        wechat.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("20.00"));
        wechat.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555");
        wechat.setProductCode("Test product code").setProductNumber(1234)
                .setProductDescription("Test product description")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"));
        wechat.setReturnUrl(new URL("https://example.com/return"))
                .setNotificationUrl(new URL("https://example.com/notification"));

        wechat.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE");

        mappedParams.put("base_attributes", wechat.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", wechat.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", wechat.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", wechat.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        wechat.setBillingCountry(null);
    }

    @Test
    public void testWechat() throws MalformedURLException {

        elements = wechat.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", wechat.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), wechat.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), wechat.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), wechat.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), wechat.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), wechat.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("product_code"), "Test product code");
        assertEquals(mappedParams.get("product_num"), 1234);
        assertEquals(mappedParams.get("product_desc"), "Test product description");
        assertEquals(mappedParams.get("return_url"), new URL("https://example.com/return"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), wechat.getBillingAddress().getElements());
    }

    @Test
    public void testWechatWithMissingParams() {

        setMissingParams();

        elements = wechat.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), wechat.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}