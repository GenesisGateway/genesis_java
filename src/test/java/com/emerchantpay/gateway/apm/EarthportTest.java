package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.EarthportRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EarthportTest {

    private EarthportRequest earthport = new EarthportRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uid;

    @Before
    public void createEarthport() {
        uid = new StringUtils().generateUID();

        // Earthport
        earthport.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"))
                .setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555")
                .setAccountName("John Doe").setBankName("Barclays Bank PLC").setIBAN("DK5000400440116243")
                .setBIC("APMMDKKK").setAccountNumber("10352719").setBankCode("063").setBranchCode("169");

        earthport.setBillingPrimaryAddress("14, Copenhagen");
        earthport.setBillingSecondaryAddress("24, Copenhagen");
        earthport.setBillingFirstname("Plamen");
        earthport.setBillingLastname("Petrov");
        earthport.setBillingCity("Copenhagen");
        earthport.setBillingCountry(Country.Denmark.getCode());
        earthport.setBillingZipCode("M4B1B3");
        earthport.setBillingState("CH");
    }

    public void setMissingParams() {
        earthport.setBillingCountry(null);
    }

    @Test
    public void testEarthport() {

        mappedParams = new HashMap<String, Object>();

        elements = earthport.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", earthport.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), earthport.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("transaction_id"), uid);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john.doe@emerchantpay.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("account_name"), "John Doe");
        assertEquals(mappedParams.get("bank_name"), "Barclays Bank PLC");
        assertEquals(mappedParams.get("iban"), "DK5000400440116243");
        assertEquals(mappedParams.get("bic"), "APMMDKKK");
        assertEquals(mappedParams.get("account_number"), "10352719");
        assertEquals(mappedParams.get("bank_code"), "063");
        assertEquals(mappedParams.get("branch_code"), "169");
        assertEquals(mappedParams.get("billing_address"), earthport.getBillingAddress().getElements());
    }

    @Test
    public void testEarthportWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = earthport.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), earthport.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}