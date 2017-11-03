package com.emerchantpay.gateway.card.recurring;

import com.emerchantpay.gateway.api.requests.financial.card.recurring.RecurringSaleRequest;
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

public class RecurringSaleTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private RecurringSaleRequest recurringSale = new RecurringSaleRequest();

    @Before
    public void createInitRecurring() {
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Recurring
        recurringSale.setTransactionId(uniqueId).setUsage("TICKETS").setRemoteIp("192.168.0.1");
        recurringSale.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("10.00"));
        recurringSale.setReferenceId("2ee4287e67971380ef7f97d5743bb523");

        mappedParams.put("base_attributes", recurringSale.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", recurringSale.buildPaymentParams().getElements());
}

    public void setMissingParams() {
        recurringSale.setReferenceId(null);
    }

    @Test
    public void testRecurring() throws MalformedURLException {

        elements = recurringSale.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), recurringSale.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), recurringSale.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), recurringSale.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("reference_id"), "2ee4287e67971380ef7f97d5743bb523");
    }

    @Test
    public void testInitRecurringWithMissingParams() {
        setMissingParams();

        mappedParams = new HashMap<String, Object>();

        elements = recurringSale.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), recurringSale.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("reference_id"));
    }
}
