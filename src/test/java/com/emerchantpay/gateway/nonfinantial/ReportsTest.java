package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ReportsByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ReportsRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class ReportsTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private ReportsRequest reportRequest = new ReportsRequest();
    private ReportsByDateRequest reportByDate = new ReportsByDateRequest();

    @Before
    public void createSingle() {

        // Single
        reportRequest.setArn("74537604221431003881865");
    }

    @Before
    public void createChargebackByDate() {

        // By Date
        reportByDate.setStartDate("20-04-2016").setEndDate("20-04-2017").setPage(2);
    }

    @Test
    public void testSingle() {

        mappedParams = new HashMap<String, Object>();

        elements = reportRequest.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), reportRequest.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("arn"), "74537604221431003881865");
    }

    @Test
    public void testByDate() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = reportByDate.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), reportByDate.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("start_date"), "20-04-2016");
        assertEquals(mappedParams.get("end_date"), "20-04-2017");
        assertEquals(mappedParams.get("page"), 2);
    }
}
