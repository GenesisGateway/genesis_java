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
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Sale3D
        sale3d.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        sale3d.setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency());
        sale3d.setCardHolder("JOHN DOE").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123");
        sale3d.setCustomerEmail("john@example.com").setCustomerPhone("+5555555555");
        sale3d.setNotificationUrl(new URL("http://www.example.com/notification"));
        sale3d.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        sale3d.setBillingPrimaryAddress("Address1")
                .setBillingSecondaryAddress("Address2")
                .setBillingFirstname("John")
                .setBillingLastname("Doe")
                .setBillingCity("New York")
                .setBillingCountry("US")
                .setBillingZipCode("1000")
                .setBillingState("NY");

        mappedParams.put("base_attributes", sale3d.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sale3d.buildPaymentParams().getElements());
        mappedParams.put("credit_card_attributes", sale3d.buildCreditCardParams().getElements());
        mappedParams.put("customer_info_attributes", sale3d.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", sale3d.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        sale3d.setBillingCountry(null);
    }


    @Test
    public void testSale3D() throws MalformedURLException, NoSuchAlgorithmException, UnsupportedEncodingException {

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

        assertEquals(mappedParams.get("base_attributes"), sale3d.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sale3d.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("credit_card_attributes"), sale3d.buildCreditCardParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), sale3d.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), sale3d.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("notification_url"), new URL("http://www.example.com/notification"));
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