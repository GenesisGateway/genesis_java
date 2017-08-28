package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.api.requests.nonfinancial.AccountVerificationRequest;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class AccountVerificationTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private AccountVerificationRequest accountVerification = new AccountVerificationRequest();

    @Before
    public void createAuthorize3D() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // Account Verification
        accountVerification.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setMoto(true).setCardHolder("JOHN DOE").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123").setCustomerEmail("john@example.com")
                .setCustomerPhone("+5555555555");

        accountVerification.setBillingPrimaryAddress("Address1");
        accountVerification.setBillingSecondaryAddress("Address2");
        accountVerification.setBillingFirstname("John");
        accountVerification.setBillingLastname("Doe");
        accountVerification.setBillingCity("New York");
        accountVerification.setBillingCountry("US");
        accountVerification.setBillingZipCode("1000");
        accountVerification.setBillingState("NY");
    }

    public void setMissingParams() {
        accountVerification.setBillingCountry(null);
    }

    @Test
    public void testAccountVerification() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();

        elements = accountVerification.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", accountVerification.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), accountVerification.getElements().get(i).getValue());
            }
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
        assertEquals(mappedParams.get("billing_address"), accountVerification.getBillingAddress().getElements());
    }

    @Test
    public void testAccountVerificationWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = accountVerification.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), accountVerification.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}