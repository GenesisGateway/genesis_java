package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Sale3DTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private Sale3DRequest sale3d = new Sale3DRequest();

    @Before
    public void createSale() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // Sale3D
        sale3d.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency())
                .setCardholder("JOHN DOE").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123").setCustomerEmail("john@example.com")
                .setCustomerPhone("+5555555555").setNotificationUrl(new URL("http://www.example.com/notification"))
                .setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        sale3d.setBillingPrimaryAddress("Address1");
        sale3d.setBillingSecondaryAddress("Address2");
        sale3d.setBillingFirstname("John");
        sale3d.setBillingLastname("Doe");
        sale3d.setBillingCity("New York");
        sale3d.setBillingCountry("US");
        sale3d.setBillingZipCode("1000");
        sale3d.setBillingState("NY");
    }

    public void setMissingParams() {
        sale3d.setBillingCountry(null);
    }


    @Test
    public void testSale3D() throws MalformedURLException, NoSuchAlgorithmException, UnsupportedEncodingException {

        mappedParams = new HashMap<String, Object>();

        elements = sale3d.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", sale3d.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), sale3d.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("2200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+5555555555");
        assertEquals(mappedParams.get("notification_url"), new URL("http://www.example.com/notification"));
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), sale3d.getBillingAddress().getElements());
    }

    @Test
    public void testSale3DWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = sale3d.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), sale3d.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}