package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
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

public class AuthorizeTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private AuthorizeRequest authorize = new AuthorizeRequest();

    @Before
    public void createAuthorize() {

        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Authorize
        authorize.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        authorize.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00"));
        authorize.setCardNumber("4200000000000000").setCardHolder("PLAMEN PETROV").setCvv("123")
                .setExpirationMonth("02").setExpirationYear("2020");
        authorize.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        authorize.setBirthDate("24-04-1988");

        authorize.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("New York").setBillingCountry("US")
                .setBillingZipCode("M4B1B3").setBillingState("CA");

        mappedParams.put("base_attributes", authorize.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", authorize.buildPaymentParams().getElements());
        mappedParams.put("credit_card_attributes", authorize.buildCreditCardParams().getElements());
        mappedParams.put("customer_info_attributes", authorize.buildCustomerInfoParams().getElements());
    }

    public void setMissingParams() {
        authorize.setCardNumber(null);
    }

    @Test
    public void testAuthorize() throws MalformedURLException {

        elements = authorize.getElements();

        mappedParams.put("billing_address", authorize.getBillingAddress().getElements());

        assertEquals(mappedParams.get("base_attributes"), authorize.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), authorize.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("credit_card_attributes"), authorize.buildCreditCardParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), authorize.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("billing_address"), authorize.getBillingAddress().getElements());
    }

    @Test
    public void testAuthorizeWithMissingParams() {
        setMissingParams();

        mappedParams = new HashMap<String, Object>();

        elements = authorize.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), authorize.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("card_number"));
    }
}