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
        uid = new StringUtils().generateUID();

        // Wechat
        wechat.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("20.00"))
                .setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555")
                .setProductCode("Test product code").setProductNumber(1234)
                .setProductDescription("Test product description")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"))
                .setReturnUrl(new URL("https://example.com/return"))
                .setNotificationUrl(new URL("https://example.com/notification"))
                .billingAddress().setAddress1("First Avenue").setAddress2("Second Avenue")
                .setFirstname("John").setLastname("Doe").setCity("Berlin")
                .setCountry(Country.Germany.getCode()).setZipCode("M4B1B3").setState("BE").done();
    }

    public void setMissingParams() {
        wechat.billingAddress().setCountry(null).done();
    }

    @Test
    public void testWechat() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = wechat.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), wechat.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uid);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("2000"));
        assertEquals(mappedParams.get("customer_email"), "john.doe@emerchantpay.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("product_code"), "Test product code");
        assertEquals(mappedParams.get("product_num"), 1234);
        assertEquals(mappedParams.get("product_desc"), "Test product description");
        assertEquals(mappedParams.get("return_url"), new URL("https://example.com/return"));
        assertEquals(mappedParams.get("return_success_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("https://example.com/return_failure"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), wechat.getBillingAddress());
    }

    @Test
    public void testWechatWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = wechat.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), wechat.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}
