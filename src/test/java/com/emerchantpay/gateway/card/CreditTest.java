package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.requests.financial.card.CreditRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CreditTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private CreditRequest credit = new CreditRequest();

    @Before
    public void createCredit() {
        mappedParams = new HashMap<String, Object>();
        uniqueId = new StringUtils().generateUID();

        // Credit
        credit.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        credit.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00"));
        credit.setReferencialId("57b3a7b166ffe873d0a11863560b410c");

        mappedParams.put("base_attributes", credit.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", credit.buildPaymentParams().getElements());
    }

    public void setMissingParams() {
        credit.setReferencialId(null);
    }

    @Test
    public void testCredit()  {

        elements = credit.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), credit.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), credit.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), credit.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("reference_id"), "57b3a7b166ffe873d0a11863560b410c");
    }

    @Test
    public void testCreditWithMissingParams() {
        setMissingParams();

        mappedParams = new HashMap<String, Object>();

        elements = credit.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), credit.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("reference_id"));
    }
}
