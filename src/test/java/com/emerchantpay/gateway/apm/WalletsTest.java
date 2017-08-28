package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.wallets.EzeewalletRequest;
import com.emerchantpay.gateway.api.requests.financial.wallets.NetellerRequest;
import com.emerchantpay.gateway.api.requests.financial.wallets.WebMoneyRequest;
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

public class WalletsTest {

    private EzeewalletRequest ezee = new EzeewalletRequest();
    private WebMoneyRequest webmoney  = new WebMoneyRequest();
    private NetellerRequest neteller = new NetellerRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidEzee;
    private String uidWebmoney;
    private String uidNeteller;

    @Before
    public void createEzeewallet() throws MalformedURLException {
        uidEzee = new StringUtils().generateUID();

        // Ezeewallet
        ezee.setTransactionId(uidEzee).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00"))
                .setReturnSuccessUrl(new URL("http://www.example.com/success")).setSourceWalletId("john@example.com")
                .setSourceWalletPwd("UDBydsDBrYWxAQA==\\n").setReturnFailureUrl(new URL("http://www.example.com/failure"))
                .setNotificationURL(new URL("http://www.example.com/notification"));
    }

    @Before
    public void createWebmoney() throws MalformedURLException {
        uidWebmoney = new StringUtils().generateUID();

        // WebMoney
        webmoney.setTransactionId(uidWebmoney).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setIsPayout(true).setCustomerAccount("118221674199")
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        webmoney.setBillingPrimaryAddress("Berlin");
        webmoney.setBillingSecondaryAddress("Berlin");
        webmoney.setBillingFirstname("Plamen");
        webmoney.setBillingLastname("Petrov");
        webmoney.setBillingCity("Berlin");
        webmoney.setBillingCountry(Country.Germany.getCode());
        webmoney.setBillingZipCode("M4B1B3");
        webmoney.setBillingState("BE");
    }

    @Before
    public void createNeteller() throws MalformedURLException {
        uidNeteller = new StringUtils().generateUID();

        // Neteller
        neteller.setTransactionId(uidNeteller).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setCustomerAccount("453501020503").setAccountPassword("908379")
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        neteller.setBillingPrimaryAddress("Berlin");
        neteller.setBillingSecondaryAddress("Berlin");
        neteller.setBillingFirstname("Plamen");
        neteller.setBillingLastname("Petrov");
        neteller.setBillingCity("Berlin");
        neteller.setBillingCountry(Country.Germany.getCode());
        neteller.setBillingZipCode("M4B1B3");
        neteller.setBillingState("BE");
    }

    public void setMissingParams() {
        webmoney.setBillingCountry(null);
        neteller.setBillingCountry(null);
    }

    @Test
    public void testEzee() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = ezee.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), ezee.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidEzee);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("notification_url"), new URL("http://www.example.com/notification"));
        assertEquals(mappedParams.get("source_wallet_id"), "john@example.com");
        assertEquals(mappedParams.get("source_wallet_pwd"), "UDBydsDBrYWxAQA==\\n");
    }

    @Test
    public void testWebmoney() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = webmoney.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", webmoney.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), webmoney.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uidWebmoney);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("is_payout"), true);
        assertEquals(mappedParams.get("customer_account_id"), "118221674199");
        assertEquals(mappedParams.get("billing_address"), webmoney.getBillingAddress().getElements());
    }

    @Test
    public void testWebMoneyWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = webmoney.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), webmoney.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testNeteller() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = neteller.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", neteller.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), neteller.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uidNeteller);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("customer_account"), "453501020503");
        assertEquals(mappedParams.get("billing_address"), neteller.getBillingAddress().getElements());
    }

    @Test
    public void testNetellerWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = neteller.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), neteller.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}