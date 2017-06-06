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
        uidWpf = new StringUtils().generateUID();

        // Create WPF
        wpfCreate.setTransactionId(uidWpf).setUsage("TICKETS").setDescription("TEST Description")
                .setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("https://www.example.com/success"))
                .setReturnFailureUrl(new URL("https://www.example.com/failure"))
                .setReturnCancelUrl(new URL("https://example.com/return_cancel"))
                .setNotificationUrl(new URL("https://example.com/notification")).billingAddress()
                .setAddress1("Berlin").setAddress2("Berlin").setFirstname("Plamen").setLastname("Petrov")
                .setCity("Berlin").setCountry(Country.Germany.getCode()).setZipCode("M4B1B3").setState("BE").done();
    }

    @Before
    public void createReconcile() {

        uidReconcile = new StringUtils().generateUID();
        wpfReconcile.setUniqueId(uidReconcile);
    }

    public void setMissingParams() {
        wpfCreate.billingAddress().setCountry(null).done();
    }

    @Test
    public void testWPF() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = wpfCreate.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), wpfCreate.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidWpf);
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("description"), "TEST Description");
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("return_success_url"), new URL("https://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("https://www.example.com/failure"));
        assertEquals(mappedParams.get("return_cancel_url"), new URL("https://example.com/return_cancel"));
        assertEquals(mappedParams.get("billing_address"), wpfCreate.getBillingAddress());
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
        elements = wpfCreate.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), wpfCreate.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}
