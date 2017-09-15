package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.requests.financial.oBeP.InstaDebitPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.InstaDebitPayOutRequest;
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

public class InstaDebitTest {
    private InstaDebitPayInRequest instadebitPayIn = new InstaDebitPayInRequest();
    private InstaDebitPayOutRequest instadebitPayOut = new InstaDebitPayOutRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidPayIn;
    private String uidPayOut;

    @Before
    public void createIDeitPayIn() throws MalformedURLException {
        uidPayIn = new StringUtils().generateUID();

        // PayIn
        instadebitPayIn.setTransactionId(uidPayIn).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setCurrency(Currency.CAD.getCurrency())
                .setAmount(new BigDecimal("100.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setCustomerAccountId("1534537")
                .setReturnUrl(new URL("https://example.com/return_success"))
                .setNotificationUrl(new URL("https://example.com/return_notification"));

        instadebitPayIn.setBillingPrimaryAddress("Toronto");
        instadebitPayIn.setBillingSecondaryAddress("Toronto");
        instadebitPayIn.setBillingFirstname("Plamen");
        instadebitPayIn.setBillingLastname("Petrov");
        instadebitPayIn.setBillingCity("Toronto");
        instadebitPayIn.setBillingCountry(Country.Canada.getCode());
        instadebitPayIn.setBillingZipCode("M4B1B3");
        instadebitPayIn.setBillingState("ON");
    }

    @Before
    public void createInstaDebitPayOut() {
        uidPayOut = new StringUtils().generateUID();

        // PayIn
        instadebitPayOut.setTransactionId(uidPayOut).setReferenceId("2bf62f87d232590a115530b0a0154505")
                .setCurrency(Currency.CAD.getCurrency()).setAmount(new BigDecimal("1.00"));
    }

    public void setMissingParams() {
        instadebitPayIn.setBillingCountry(null);
    }

    @Test
    public void testInstaDebitPayIn() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = instadebitPayIn.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", instadebitPayIn.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), instadebitPayIn.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayIn);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.CAD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("10000"));
        assertEquals(mappedParams.get("return_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/return_notification"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("billing_address"), instadebitPayIn.getBillingAddress().getElements());
    }

    @Test
    public void testIDebitPayInWithMissingParams() throws MalformedURLException {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = instadebitPayIn.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), instadebitPayIn.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testInstaDebitPayOut() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();

        elements = instadebitPayOut.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), instadebitPayOut.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayOut);
        assertEquals(mappedParams.get("currency"), Currency.CAD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("100"));
        assertEquals(mappedParams.get("reference_id"), "2bf62f87d232590a115530b0a0154505");
    }
}