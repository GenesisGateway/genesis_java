package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.POLiRequest;
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

public class POLiTest {

    private POLiRequest poli = new POLiRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    @Before
    public void createSale() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // POLi
        poli.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.AUD.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")).billingAddress().setAddress1("Sydney 1")
                .setAddress2("Sydney 2").setFirstname("Plamen").setLastname("Petrov").setCity("Sydney")
                .setCountry(Country.Australia.getCode()).setZipCode("S4C1C3").setState("SY").done();
    }

    public void setMissingParams() {
        poli.billingAddress().setCountry(null).done();
    }

    @Test
    public void testPoli() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = poli.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), poli.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.AUD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("return_success_url"), new URL("http://www.example.com/success"));
        assertEquals(mappedParams.get("return_failure_url"), new URL("http://www.example.com/failure"));
        assertEquals(mappedParams.get("billing_address"), poli.getBillingAddress());
    }



    @Test
    public void testPoliWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = poli.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), poli.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
