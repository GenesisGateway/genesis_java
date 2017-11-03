package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.PayPalExpressRequest;
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

public class PayPalExpressTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private PayPalExpressRequest paypalExpress = new PayPalExpressRequest();

    @Before
    public void createPayPal() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Checkout
        paypalExpress.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        paypalExpress.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        paypalExpress.setCustomerEmail("john@example.com").setCustomerPhone("555555");
        paypalExpress.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));

        paypalExpress.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry("DE").setBillingZipCode("M4B1B3")
                .setBillingState("BE");

        mappedParams.put("base_attributes", paypalExpress.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", paypalExpress.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", paypalExpress.buildCustomerInfoParams().getElements());
        mappedParams.put("async_attributes",  paypalExpress.buildAsyncParams().getElements());
    }

    public void setMissingParams() {
        paypalExpress.setBillingCountry(null);
    }

    @Test
    public void testPayPalExpress() throws MalformedURLException {

        elements = paypalExpress.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", paypalExpress.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), paypalExpress.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), paypalExpress.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), paypalExpress.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), paypalExpress.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("async_attributes"), paypalExpress.buildAsyncParams().getElements());
        assertEquals(mappedParams.get("billing_address"), paypalExpress.getBillingAddress().getElements());
    }

    @Test
    public void testPayPalUWithMissingParams() {

        setMissingParams();

        elements = paypalExpress.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), paypalExpress.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}