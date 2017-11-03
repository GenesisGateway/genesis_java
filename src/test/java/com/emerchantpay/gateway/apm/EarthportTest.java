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
        mappedParams = new HashMap<String, Object>();
        uid = new StringUtils().generateUID();

        // Earthport
        earthport.setTransactionId(uid).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        earthport.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        earthport.setCustomerEmail("john.doe@emerchantpay.com").setCustomerPhone("+55555555");
        earthport.setAccountName("John Doe").setBankName("Barclays Bank PLC").setIBAN("DK5000400440116243")
                .setBIC("APMMDKKK").setAccountNumber("10352719").setBankCode("063").setBranchCode("169");

        earthport.setBillingPrimaryAddress("14, Copenhagen").setBillingSecondaryAddress("24, Copenhagen")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov").setBillingCity("Copenhagen")
                .setBillingCountry(Country.Denmark.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("CH");

        mappedParams.put("base_attributes", earthport.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", earthport.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", earthport.buildCustomerInfoParams().getElements());
    }

    public void setMissingParams() {
        earthport.setBillingCountry(null);
    }

    @Test
    public void testEarthport() {

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

        assertEquals(mappedParams.get("base_attributes"), earthport.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), earthport.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), earthport.buildCustomerInfoParams().getElements());
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

        elements = earthport.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), earthport.getBillingAddress().getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("country"), null);
    }
}