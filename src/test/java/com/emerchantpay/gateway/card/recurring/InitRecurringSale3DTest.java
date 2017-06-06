package com.emerchantpay.gateway.card.recurring;

import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSale3DRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InitRecurringSale3DTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private InitRecurringSale3DRequest initrecsale3d = new InitRecurringSale3DRequest();

    @Before
    public void createSale() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // Sale3D
        initrecsale3d.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("22.00")).setCurrency(Currency.USD.getCurrency())
                .setCardHolder("JOHN DOE").setCardNumber("4200000000000000").setExpirationMonth("02")
                .setExpirationYear("2020").setCvv("123").setCustomerEmail("john@example.com")
                .setCustomerPhone("+5555555555").billingAddress().setAddress1("Address1").setAddress2("Address2")
                .setFirstname("John").setLastname("Doe").setCountry("US").setCity("New York").setZipCode("1000")
                .setState("NY").done().setNotificationUrl(new URL("http://www.example.com/notification"))
                .setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));
    }

    public void setMissingParams() {
        initrecsale3d.billingAddress().setCountry(null).done();
    }


    @Test
    public void testSale3D() throws MalformedURLException, NoSuchAlgorithmException, UnsupportedEncodingException {

        mappedParams = new HashMap<String, Object>();

        elements = initrecsale3d.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), initrecsale3d.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("2200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+5555555555");
        assertEquals(mappedParams.get("notification_url"), new URL("http://www.example.com/notification"));
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), initrecsale3d.getBillingAddress());
    }

    @Test
    public void testSale3DWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = initrecsale3d.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), initrecsale3d.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
