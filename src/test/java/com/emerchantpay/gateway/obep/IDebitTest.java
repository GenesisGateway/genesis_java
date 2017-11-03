package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayOutRequest;
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

public class IDebitTest {

    private IDebitPayInRequest idebitPayIn = new IDebitPayInRequest();
    private IDebitPayOutRequest idebitPayOut = new IDebitPayOutRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidPayIn;
    private String uidPayOut;

    @Before
    public void createIDebitPayIn() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidPayIn = new StringUtils().generateUID();

        // PayIn
        idebitPayIn.setTransactionId(uidPayIn).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        idebitPayIn.setCurrency(Currency.CAD.getCurrency())
                .setAmount(new BigDecimal("100.00"));
        idebitPayIn.setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555");
        idebitPayIn.setCustomerAccountId("1534537")
                .setReturnUrl(new URL("https://example.com/return_success"))
                .setNotificationUrl(new URL("https://example.com/return_notification"));

        idebitPayIn.setBillingPrimaryAddress("Toronto").setBillingSecondaryAddress("Toronto")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Toronto").setBillingCountry(Country.Canada.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("ON");

        mappedParams.put("base_attributes", idebitPayIn.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", idebitPayIn.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", idebitPayIn.buildCustomerInfoParams().getElements());
    }

    @Before
    public void createIDebitPayOut() {
        mappedParams = new HashMap<String, Object>();
        uidPayOut = new StringUtils().generateUID();

        // PayOut
        idebitPayOut.setTransactionId(uidPayOut).setReferenceId("2bf62f87d232590a115530b0a0154505")
                .setCurrency(Currency.CAD.getCurrency()).setAmount(new BigDecimal("1.00"));

        mappedParams.put("payment_attributes", idebitPayIn.buildPaymentParams().getElements());
    }

    public void setMissingParams() {
        idebitPayIn.setBillingCountry(null);
    }

    @Test
    public void testIDebitPayIn() throws MalformedURLException {

        elements = idebitPayIn.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", idebitPayIn.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), idebitPayIn.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), idebitPayIn.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), idebitPayIn.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), idebitPayIn.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("return_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/return_notification"));
        assertEquals(mappedParams.get("billing_address"), idebitPayIn.getBillingAddress().getElements());
    }

    @Test
    public void testIDebitPayInWithMissingParams() throws MalformedURLException {

        setMissingParams();

        elements = idebitPayIn.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), idebitPayIn.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testIDebitPayOut() throws MalformedURLException {

        elements = idebitPayOut.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), idebitPayOut.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayOut);
        assertEquals(mappedParams.get("payment_attributes"), idebitPayOut.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("reference_id"), "2bf62f87d232590a115530b0a0154505");
    }
}