package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ChargebackByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ChargebackRequest;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class CharbacksTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private ChargebackRequest chargeback = new ChargebackRequest();
    private ChargebackByDateRequest chargeByDate = new ChargebackByDateRequest();

    @Before
    public void createSingle() {

        // Single
        chargeback.setArn("74537604221431003881865");
    }

    @Before
    public void createChargebackByDate() {

        // By Date
        chargeByDate.setStartDate("20-04-2016").setEndDate("20-04-2017").setPage(2);
    }

    @Test
    public void testSingle() {

        mappedParams = new HashMap<String, Object>();

        elements = chargeback.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), chargeback.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("arn"), "74537604221431003881865");
    }

    @Test
    public void testByDate() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = chargeByDate.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), chargeByDate.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("start_date"), "20-04-2016");
        assertEquals(mappedParams.get("end_date"), "20-04-2017");
        assertEquals(mappedParams.get("page"), 2);
    }
}
