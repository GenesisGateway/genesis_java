package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.CitadelPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.CitadelPayOutRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CitadelTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidPayIn;
    private String uidPayOut;

    private CitadelPayInRequest citadelPayIn = new CitadelPayInRequest();
    private CitadelPayOutRequest citadelPayOut = new CitadelPayOutRequest();

    @Before
    public void createCitadelIn() throws MalformedURLException {
       uidPayIn = new StringUtils().generateUID();

        // PayIn
        citadelPayIn.setTransactionId(uidPayIn).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("https://www.gmail.com"))
                .setReturnFailureUrl(new URL("https://www.google.com"))
                .setNotificationUrl(new URL("http://www.example.com/notification")).setMerchantCustomerId("1534537")
                .billingAddress().setAddress1("Berlin").setAddress2("Berlin").setFirstname("Plamen")
                .setLastname("Petrov").setCity("Berlin").setCountry("DE").setZipCode("M4B1B3").setState("BE").done();
    }

    @Before
    public void createCitadelOut() {
       uidPayOut = new StringUtils().generateUID();

        // PayOut
        citadelPayOut.setTransactionId(uidPayOut).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency()).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setHolderName("John Doe").setAccountNumber("1534537").setIBAN("DE89370400440532013000")
                .setSwiftCode("DEUTDEDB899").billingAddress().setAddress1("Toronto").setAddress2("Berlin").setFirstname("Plamen")
                .setLastname("Petrov").setCity("Berlin").setCountry("DE").setZipCode("M4B1B3").setState("BE").done()
                .setBankName("Royal Bank").setBankCity("Berlin");
    }

    public void setMissingParams() {
        citadelPayIn.billingAddress().setCountry(null).done();
        citadelPayOut.billingAddress().setCountry(null).done();
    }

    @Test
    public void testCitadelPayIn() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();

        elements = citadelPayIn.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), citadelPayIn.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayIn);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("notification_url"), new URL("http://www.example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), citadelPayIn.getBillingAddress());
    }

    @Test
    public void testCitadelPayInWithMissingParams() {
        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = citadelPayIn.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), citadelPayIn.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }

    @Test
    public void testCitadelPayOut() {
        mappedParams = new HashMap<String, Object>();

        elements = citadelPayOut.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), citadelPayOut.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayOut);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("holder_name"), "John Doe");
        assertEquals(mappedParams.get("billing_address"), citadelPayOut.getBillingAddress());
        assertEquals(mappedParams.get("bank_name"), "Royal Bank");
        assertEquals(mappedParams.get("bank_city"), "Berlin");
    }


    @Test
    public void testCitadelPayOutWithMissingParams() {
        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = citadelPayOut.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), citadelPayOut.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
