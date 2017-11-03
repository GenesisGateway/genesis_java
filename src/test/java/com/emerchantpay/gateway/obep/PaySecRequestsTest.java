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
        mappedParams = new HashMap<String, Object>();
        uid = new StringUtils().generateUID();

        // PaySec
        paysec.setTransactionId(uid).setRemoteIp("94.26.28.135").setUsage("TICKETS");
        paysec.setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("10.00"));
        paysec.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555");
        paysec.setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"));
        paysec.setNotificationUrl(new URL("https://example.com/notification"));

        paysec.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Second Avenue")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity("Beijing").setBillingCountry(Country.China.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BJ");

        mappedParams.put("base_attributes", paysec.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", paysec.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", paysec.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", paysec.buildAsyncParams().getElements());
    }

    @Before
    public void createPaySecPayout() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidPayout = new StringUtils().generateUID();

        // PaySec Payout
        paysecPayout.setTransactionId(uidPayout).setRemoteIp("94.26.28.135").setUsage("TICKETS");
        paysecPayout
                .setCurrency(Currency.CNY.getCurrency()).setAmount(new BigDecimal("600.00"));
        paysecPayout
                .setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555");
        paysecPayout.setBankCode("BOC").setBankName("深圳发展银行").setBankBranch("Test Bank Branch")
                .setBankAccountName("Test Bank Account Name").setBankAccountNumber("1234123412341234")
                .setReturnSuccessUrl(new URL("https://example.com/return_success"))
                .setReturnFailureUrl(new URL("https://example.com/return_failure"));
        paysecPayout.setNotificationUrl(new URL("https://example.com/notification"));

        paysecPayout.setBillingPrimaryAddress("First Avenue").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("John").setBillingLastname("Doe")
                .setBillingCity(Country.China.getCode()).setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BJ");

        mappedParams.put("base_attributes", paysecPayout.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", paysecPayout.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", paysecPayout.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", paysecPayout.buildAsyncParams().getElements());

    }

    public void setMissingParams() {
        paysec.setBillingCountry(null);
        paysecPayout.setBillingCountry(null);
    }

    @Test
    public void testPaySec() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), paysec.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), paysec.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), paysec.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), paysec.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), paysec.getBillingAddress().getElements());
    }

    @Test
    public void testPaySecWithMissingParams() {

        setMissingParams();

        elements = paysec.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), paysec.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }

    @Test
    public void testPaySecPayout() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), paysec.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), paysec.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), paysec.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), paysec.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("bank_code"), "BOC");
        assertEquals(mappedParams.get("bank_name"), "深圳发展银行");
        assertEquals(mappedParams.get("bank_branch"), "Test Bank Branch");
        assertEquals(mappedParams.get("bank_account_number"), "1234123412341234");
        assertEquals(mappedParams.get("bank_account_name"), "Test Bank Account Name");
        assertEquals(mappedParams.get("notification_url"), new URL("https://example.com/notification"));
        assertEquals(mappedParams.get("billing_address"), paysecPayout.getBillingAddress().getElements());
    }

    @Test
    public void testPaySecPayoutWithMissingParams() {

        setMissingParams();

        elements = paysecPayout.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), paysec.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}