package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.api.requests.nonfinancial.AVSRequest;
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


public class AVSTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private AVSRequest avsRequest = new AVSRequest();

    @Before
    public void createAuthorize3D() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // AVS
        avsRequest.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setMoto(true).setCardHolder("JOHN DOE").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123").setCustomerEmail("john@example.com")
                .setCustomerPhone("+5555555555").billingAddress().setAddress1("Address1").setAddress2("Address2")
                .setFirstname("John").setLastname("Doe").setCountry("US").setCity("New York").setZipCode("1000")
                .setState("NY").done().setBirthDate("24-04-1988");
    }

    public void setMissingParams() {
        avsRequest.getBillingAddress().setCountry(null).done();
    }

    @Test
    public void testAVS() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();

        elements = avsRequest.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), avsRequest.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("moto"), true);
        assertEquals(mappedParams.get("card_number"), "4200000000000000");
        assertEquals(mappedParams.get("card_holder"), "JOHN DOE");
        assertEquals(mappedParams.get("expiration_month"), "02");
        assertEquals(mappedParams.get("expiration_year"), "2020");
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+5555555555");
        assertEquals(mappedParams.get("billing_address"), avsRequest.getBillingAddress());
        assertEquals(mappedParams.get("birth_date"), "24-04-1988");
    }

    @Test
    public void testAVSWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = avsRequest.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), avsRequest.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
