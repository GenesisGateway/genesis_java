package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.api.requests.financial.oBeP.PaySecPayoutRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.PaySecRequest;
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

public class PaySecRequestsTest {

    private PaySecRequest paysec = new PaySecRequest();
    private PaySecPayoutRequest paysecPayout = new PaySecPayoutRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uid;
    private String uidPayout;

    @Before
    public void createPaySec() throws MalformedURLException {
        uid = new StringUtils().generateUID();

        // PaySec
        paysec.setTransactionId(uid).setRemoteIp("94.26.28.135").setUsage("TICKETS")
                .setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("10.00"))
                .setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"))
                .setNotificationUrl(new URL("https://example.com/notification"));

        paysec.setBillingPrimaryAddress("First Avenue");
        paysec.setBillingSecondaryAddress("Second Avenue");
        paysec.setBillingFirstname("John");
        paysec.setBillingLastname("Doe");
        paysec.setBillingCity("Beijing");
        paysec.setBillingCountry(Country.China.getCode());
        paysec.setBillingZipCode("M4B1B3");
        paysec.setBillingState("BJ");
    }

    @Before
    public void createPaySecPayout() throws MalformedURLException {
        uidPayout = new StringUtils().generateUID();

        // PaySec Payout
        paysecPayout.setTransactionId(uidPayout).setRemoteIp("94.26.28.135").setUsage("TICKETS")
                .setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("600.00"))
                .setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555")
                .setBankCode("BOC").setBankName("深圳发展银行").setBankBranch("Test Bank Branch")
                .setBankAccountName("Test Bank Account Name").setBankAccountNumber("1234123412341234")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"))
                .setNotificationUrl(new URL("https://example.com/notification"));

        paysecPayout.setBillingPrimaryAddress("First Avenue");
        paysecPayout.setBillingSecondaryAddress("Berlin");
        paysecPayout.setBillingFirstname("John");
        paysecPayout.setBillingLastname("Doe");
        paysecPayout.setBillingCity(Country.China.getCode());
        paysecPayout.setBillingCountry("DE");
        paysecPayout.setBillingZipCode("M4B1B3");
        paysecPayout.setBillingState("BJ");

    }

    public void setMissingParams() {
        paysec.setBillingCountry(null);
        paysecPayout.setBillingCountry(null);
    }

    @Test
    public void testPaySec() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = paysec.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", paysec.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), paysec.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uid);
        assertEquals(mappedParams.get("remote_ip"), "94.26.28.135");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.CNY.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("1000"));
        assertEquals(mappedParams.get("customer_email"), "john.doe@emerchantpay.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("https://example.com/return_failure"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), paysec.getBillingAddress().getElements());
    }

    @Test
    public void testPaySecWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = paysec.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), paysec.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }

    @Test
    public void testPaySecPayout() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = paysecPayout.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", paysecPayout.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), paysecPayout.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayout);
        assertEquals(mappedParams.get("remote_ip"), "94.26.28.135");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.CNY.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("60000"));
        assertEquals(mappedParams.get("customer_email"), "john.doe@emerchantpay.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("bank_code"), "BOC");
        assertEquals(mappedParams.get("bank_name"), "深圳发展银行");
        assertEquals(mappedParams.get("bank_branch"), "Test Bank Branch");
        assertEquals(mappedParams.get("bank_account_number"), "1234123412341234");
        assertEquals(mappedParams.get("bank_account_name"), "Test Bank Account Name");
        assertEquals(mappedParams.get("return_success_url"), new URL("https://example.com/return_success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("https://example.com/return_failure"));
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), paysecPayout.getBillingAddress().getElements());
    }

    @Test
    public void testPaySecPayoutWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = paysecPayout.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), paysec.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}