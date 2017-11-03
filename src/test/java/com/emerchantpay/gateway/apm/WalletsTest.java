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
        mappedParams = new HashMap<String, Object>();
        uidEzee = new StringUtils().generateUID();

        // Ezeewallet
        ezee.setTransactionId(uidEzee).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        ezee.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00"));
        ezee.setSourceWalletId("john@example.com");
        ezee.setSourceWalletPwd("UDBydsDBrYWxAQA==\\n");
        ezee.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));
        ezee.setNotificationURL(new URL("http://www.example.com/notification"));

        mappedParams.put("base_attributes", ezee.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", ezee.buildPaymentParams().getElements());
        mappedParams.put("async_atrributes", ezee.buildAsyncParams().getElements());
    }

    @Before
    public void createWebmoney() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidWebmoney = new StringUtils().generateUID();

        // WebMoney
        webmoney.setTransactionId(uidWebmoney).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        webmoney.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00"));
        webmoney.setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555");
        webmoney.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));
        webmoney.setIsPayout(true).setCustomerAccount("118221674199");

        webmoney.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE");


        mappedParams.put("base_attributes", webmoney.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", webmoney.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", webmoney.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", webmoney.buildAsyncParams().getElements());
    }

    @Before
    public void createNeteller() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidNeteller = new StringUtils().generateUID();

        // Neteller
        neteller.setTransactionId(uidNeteller).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        neteller.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        neteller.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        neteller.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));;
        neteller.setCustomerAccount("453501020503").setAccountPassword("908379");

        neteller.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE");

        mappedParams.put("base_attributes", neteller.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", neteller.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", neteller.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes", neteller.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        webmoney.setBillingCountry(null);
        neteller.setBillingCountry(null);
    }

    @Test
    public void testEzee() throws MalformedURLException {

        elements = ezee.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), ezee.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), ezee.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), ezee.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), ezee.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("notification_url"), new URL("http://www.example.com/notification"));
        assertEquals(mappedParams.get("source_wallet_id"), "john@example.com");
        assertEquals(mappedParams.get("source_wallet_pwd"), "UDBydsDBrYWxAQA==\\n");
    }

    @Test
    public void testWebmoney() throws MalformedURLException {

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

        assertEquals(mappedParams.get("base_attributes"), webmoney.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), webmoney.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), webmoney.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), webmoney.buildAsyncParams().getElements());
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

        assertEquals(mappedParams.get("base_attributes"), neteller.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), neteller.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), neteller.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), neteller.buildAsyncParams().getElements());
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