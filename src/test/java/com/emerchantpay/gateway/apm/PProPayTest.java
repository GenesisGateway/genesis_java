package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.PProRequest;
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

public class PProPayTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private PProRequest ppro = new PProRequest();

    @Before
    public void createPPro() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // PPro
        ppro.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        ppro.setPaymentType("giropay");
        ppro.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        ppro.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        ppro.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));
        ppro.setBIC("GENODETT488").setIBAN("DE07444488880123456789");

        ppro.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE")
                .setBillingZipCode("M4B1B3").setBillingState("BE");

        mappedParams.put("base_attributes", ppro.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", ppro.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", ppro.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  ppro.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        ppro.setBillingCountry(null);
    }

    @Test
    public void testPPro() {

        elements = ppro.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", ppro.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), ppro.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), ppro.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), ppro.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), ppro.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), ppro.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("payment_type"), "giropay");
        assertEquals(mappedParams.get("bic"), "GENODETT488");
        assertEquals(mappedParams.get("iban"), "DE07444488880123456789");
        assertEquals(mappedParams.get("billing_address"), ppro.getBillingAddress().getElements());
    }

    @Test
    public void testPProWithMissingParams() {

        setMissingParams();

        elements = ppro.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), ppro.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}