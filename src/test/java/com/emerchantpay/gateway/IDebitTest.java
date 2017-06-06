package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayOutRequest;
import com.emerchantpay.gateway.model.Transaction;
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
    public void createIDeitPayIn() throws MalformedURLException {
        uidPayIn = new StringUtils().generateUID();

        // PayIn
        idebitPayIn.setTransactionId(uidPayIn).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setCurrency(Currency.CAD.getCurrency())
                .setAmount(new BigDecimal("100.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setCustomerAccountId("1534537").billingAddress().setAddress1("Toronto")
                .setAddress2("Toronto").setFirstname("Plamen").setLastname("Petrov").setCity("Toronto")
                .setCountry(Country.Canada.getCode()).setZipCode("M4B1B3").setState("ON").done()
                .setReturnUrl(new URL("https://example.com/return_success"))
                .setNotificationUrl(new URL("https://example.com/return_notification"));
    }

    @Before
    public void createIDebitPayOut() {
        uidPayOut = new StringUtils().generateUID();

        // PayIn
        idebitPayOut.setTransactionId(uidPayOut).setReferenceId("2bf62f87d232590a115530b0a0154505")
                .setCurrency(Currency.CAD.getCurrency()).setAmount(new BigDecimal("1.00"));
    }

    public void setMissingParams() {
        idebitPayIn.billingAddress().setCountry(null).done();
    }

    @Test
    public void testIDebitPayIn() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = idebitPayIn.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), idebitPayIn.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayIn);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.CAD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("10000"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/return_notification"));
        assertEquals(mappedParams.get("billing_address"), idebitPayIn.getBillingAddress());
    }

    @Test
    public void testIDebitPayInWithMissingParams() throws MalformedURLException {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = idebitPayIn.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), idebitPayIn.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testIDebitPayOut() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();

        elements = idebitPayOut.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), idebitPayOut.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayOut);
        assertEquals(mappedParams.get("currency"), Currency.CAD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("100"));
        assertEquals(mappedParams.get("reference_id"), "2bf62f87d232590a115530b0a0154505");
    }
}
