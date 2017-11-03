package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.requests.wpf.WPFCreateRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFReconcileRequest;
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

public class WPFRequestsTest {

    private WPFCreateRequest wpfCreate = new WPFCreateRequest();
    private WPFReconcileRequest wpfReconcile  = new WPFReconcileRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidWpf;
    private String uidReconcile;

    @Before
    public void createWPF() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidWpf = new StringUtils().generateUID();

        // Create WPF
        wpfCreate.setTransactionId(uidWpf).setUsage("TICKETS");
        wpfCreate.setDescription("TEST Description");
        wpfCreate.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00"));
        wpfCreate.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        wpfCreate.setReturnSuccessUrl(new URL("https://www.example.com/success"))
                .setReturnFailureUrl(new URL("https://www.example.com/failure"));
        wpfCreate.setReturnCancelUrl(new URL("https://example.com/return_cancel"))
                .setNotificationUrl(new URL("https://example.com/notification"));

        wpfCreate.setBillingPrimaryAddress("Berlin")
                .setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen")
                .setBillingLastname("Petrov").setBillingCity("Berlin")
                .setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3")
                .setBillingState("BE");

        wpfCreate.setLifetime(60);

        mappedParams.put("base_attributes", wpfCreate.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", wpfCreate.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", wpfCreate.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", wpfCreate.buildAsyncParams().getElements());
    }

    @Before
    public void createReconcile() {

        uidReconcile = new StringUtils().generateUID();
        wpfReconcile.setUniqueId(uidReconcile);
    }

    public void setMissingParams() {
        wpfCreate.setBillingCountry(null);
    }

    @Test
    public void testWPF() throws MalformedURLException {

        elements = wpfCreate.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", wpfCreate.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), wpfCreate.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), wpfCreate.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), wpfCreate.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), wpfCreate.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), wpfCreate.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("description"), "TEST Description");
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("return_cancel_url"), new URL("https://example.com/return_cancel"));
        assertEquals(mappedParams.get("lifetime"), 60);
        assertEquals(mappedParams.get("billing_address"), wpfCreate.getBillingAddress().getElements());
    }

    @Test
    public  void testReconcile() {
        mappedParams = new HashMap<String, Object>();

        elements = wpfReconcile.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), wpfReconcile.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("unique_id"), uidReconcile);
    }

    @Test
    public void testWPFWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = wpfCreate.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), wpfCreate.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}