package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.RetrievalByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.RetrievalRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class RetrievalsTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private RetrievalRequest retrieval = new RetrievalRequest();
    private RetrievalByDateRequest retrievalByDate = new RetrievalByDateRequest();

    @Before
    public void createSingle() {

        // Single
        retrieval.setArn("74537604221431003881865");
    }

    @Before
    public void createChargebackByDate() {

        // By Date
        retrievalByDate.setStartDate("20-04-2016").setEndDate("20-04-2017").setPage(2);
    }

    @Test
    public void testSingle() {

        mappedParams = new HashMap<String, Object>();

        elements = retrieval.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), retrieval.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("arn"), "74537604221431003881865");
    }

    @Test
    public void testByDate() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = retrievalByDate.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), retrievalByDate.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("start_date"), "20-04-2016");
        assertEquals(mappedParams.get("end_date"), "20-04-2017");
        assertEquals(mappedParams.get("page"), 2);
    }
}
