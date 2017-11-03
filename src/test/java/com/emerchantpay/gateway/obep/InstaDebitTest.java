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
    public void createIDebitPayIn() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidPayIn = new StringUtils().generateUID();

        // PayIn
        instadebitPayIn.setTransactionId(uidPayIn).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        instadebitPayIn.setCurrency(Currency.CAD.getCurrency())
                .setAmount(new BigDecimal("100.00"));
        instadebitPayIn.setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555");
        instadebitPayIn.setCustomerAccountId("1534537")
                .setReturnUrl(new URL("https://example.com/return_success"))
                .setNotificationUrl(new URL("https://example.com/return_notification"));

        instadebitPayIn.setBillingPrimaryAddress("Toronto").setBillingSecondaryAddress("Toronto")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Toronto").setBillingCountry(Country.Canada.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("ON");

        mappedParams.put("base_attributes", instadebitPayIn.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", instadebitPayIn.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", instadebitPayIn.buildCustomerInfoParams().getElements());
    }

    @Before
    public void createInstaDebitPayOut() {
        mappedParams = new HashMap<String, Object>();
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

        assertEquals(mappedParams.get("base_attributes"), instadebitPayIn.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), instadebitPayIn.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), instadebitPayIn.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("return_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/return_notification"));
        assertEquals(mappedParams.get("billing_address"), instadebitPayIn.getBillingAddress().getElements());
    }

    @Test
    public void testIDebitPayInWithMissingParams() throws MalformedURLException {

        setMissingParams();

        elements = instadebitPayIn.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), instadebitPayIn.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testInstaDebitPayOut() throws MalformedURLException {

        elements = instadebitPayOut.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), instadebitPayOut.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayOut);
        assertEquals(mappedParams.get("payment_attributes"), instadebitPayOut.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("reference_id"), "2bf62f87d232590a115530b0a0154505");
    }
}