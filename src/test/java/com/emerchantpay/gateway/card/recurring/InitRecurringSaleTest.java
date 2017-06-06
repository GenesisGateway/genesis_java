package com.emerchantpay.gateway.card.recurring;

import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSaleRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InitRecurringSaleTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private InitRecurringSaleRequest initRecurring = new InitRecurringSaleRequest();

    @Before
    public void createInitRecurring() {

        uniqueId = new StringUtils().generateUID();

        // Init Recurring
        initRecurring.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00"))
                .setCardNumber("4200000000000000").setCardHolder("PLAMEN PETROV").setCvv("123").setExpirationMonth("02")
                .setExpirationYear("2020").setCustomerEmail("john@example.com").setCustomerPhone("+55555555")
                .billingAddress().setAddress1("Berlin").setAddress2("Berlin").setFirstname("Plamen")
                .setLastname("Petrov").setCity("New York").setCountry("US").setZipCode("M4B1B3").setState("CA").done()
                .setBirthDate("24-04-1988");
}

    public void setMissingParams() {
        initRecurring.setCardNumber(null);
    }

    @Test
    public void testInitRecurring() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = initRecurring.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), initRecurring.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("1000"));
        assertEquals(mappedParams.get("card_number"), "4200000000000000");
        assertEquals(mappedParams.get("card_holder"), "PLAMEN PETROV");
        assertEquals(mappedParams.get("expiration_month"), "02");
        assertEquals(mappedParams.get("expiration_year"), "2020");
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("birth_date"), "24-04-1988");
        assertEquals(mappedParams.get("billing_address"), initRecurring.getBillingAddress());
    }

    @Test
    public void testInitRecurringWithMissingParams() {
        setMissingParams();

        mappedParams = new HashMap<String, Object>();

        elements = initRecurring.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), initRecurring.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("card_number"));
    }
}
