package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.TrustlySaleRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.TrustlyWithdrawalRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TrustlyTest {

    private TrustlySaleRequest trustlySale = new TrustlySaleRequest();
    private TrustlyWithdrawalRequest trustlyWithdrawal  = new TrustlyWithdrawalRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidSale;
    private String uidWithdrawal;

    @Before
    public void createSale() throws MalformedURLException {
        uidSale = new StringUtils().generateUID();

        // Sale
        trustlySale.setTransactionId(uidSale).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")).billingAddress().setAddress1("Berlin")
                .setAddress2("Berlin").setFirstname("Plamen").setLastname("Petrov").setCity("Berlin")
                .setCountry(Country.Germany.getCode()).setZipCode("M4B1B3").setState("BE").done();
    }

    public void setMissingParams() {
        trustlySale.billingAddress().setCountry(null).done();
        trustlyWithdrawal.billingAddress().setCountry(null).done();
    }

    @Before
    public void createWithdrawal() throws MalformedURLException {
        uidWithdrawal = new StringUtils().generateUID();

        // Withdrawal
        trustlyWithdrawal.setTransactionId(uidWithdrawal).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")).billingAddress().setAddress1("Berlin")
                .setAddress2("Berlin").setFirstname("Plamen").setLastname("Petrov").setCity("Berlin")
                .setCountry(Country.Germany.getCode()).setZipCode("M4B1B3").setState("BE").done()
                .setBirthDate("24-04-1988");
    }

    @Test
    public void testSale() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = trustlySale.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), trustlySale.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidSale);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), trustlySale.getBillingAddress());
    }

    @Test
    public void testWithdrawal() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = trustlyWithdrawal.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), trustlyWithdrawal.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidWithdrawal);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("birth_date"), "24-04-1988");
        assertEquals(mappedParams.get("billing_address"), trustlyWithdrawal.getBillingAddress());
    }

    @Test
    public void testSaleWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = trustlySale.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), trustlySale.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }

    @Test
    public void testWithdrawalWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = trustlyWithdrawal.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), trustlySale.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
