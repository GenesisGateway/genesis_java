package com.emerchantpay.gateway.card.recurring;

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
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Init Recurring
        initRecurring.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        initRecurring.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00"));
        initRecurring.setCardNumber("4200000000000000").setCardHolder("PLAMEN PETROV").setCvv("123")
                .setExpirationMonth("02").setExpirationYear("2020");
        initRecurring.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        initRecurring.setBirthDate("24-04-1988");

        initRecurring.setBillingPrimaryAddress("Berlin")
                .setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen")
                .setBillingLastname("Petrov")
                .setBillingCity("Berlin")
                .setBillingCountry("US")
                .setBillingZipCode("M4B1B3")
                .setBillingState("CA");

        mappedParams.put("base_attributes", initRecurring.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", initRecurring.buildPaymentParams().getElements());
        mappedParams.put("credit_card_attributes", initRecurring.buildCreditCardParams().getElements());
        mappedParams.put("customer_info_attributes", initRecurring.buildCustomerInfoParams().getElements());
    }

    public void setMissingParams() {
        initRecurring.setCardNumber(null);
    }

    @Test
    public void testInitRecurring() throws MalformedURLException {

        elements = initRecurring.getElements();

        mappedParams.put("billing_address", initRecurring.getBillingAddress().getElements());

        assertEquals(mappedParams.get("base_attributes"), initRecurring.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), initRecurring.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("credit_card_attributes"), initRecurring.buildCreditCardParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), initRecurring.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("billing_address"), initRecurring.getBillingAddress().getElements());
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