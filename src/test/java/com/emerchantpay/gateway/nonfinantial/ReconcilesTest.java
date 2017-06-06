package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFReconcileRequest;
import com.emerchantpay.gateway.util.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class ReconcilesTest {

    private Configuration configuration;

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private ReconcileRequest reconcileRequest = new ReconcileRequest();
    private WPFReconcileRequest reconcileWpf = new WPFReconcileRequest();
    private ReconcileByDateRequest reconcileByDate = new ReconcileByDateRequest();

    @Before
    public void createConfiguration() {
        this.configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
    }

    @Before
    public void createSingle() {

        // Single
        reconcileRequest.setArn("74537604221431003881865");
    }

    @Before
    public void createWpf() {

        // WPF
        reconcileWpf.setUniqueId("26aa150ee68b1b2d6758a0e6c44fce4c");
    }

    @Before
    public void createChargebackByDate() {

        // By Date
        reconcileByDate.setStartDate("20-04-2016").setEndDate("20-04-2017").setPage(2);
    }

    @Test
    public void testSingle() {

        mappedParams = new HashMap<String, Object>();

        elements = reconcileRequest.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), reconcileRequest.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("arn"), "74537604221431003881865");
    }

    @Test
    public void testWPF() {

        mappedParams = new HashMap<String, Object>();

        elements = reconcileWpf.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), reconcileWpf.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("unique_id"), "26aa150ee68b1b2d6758a0e6c44fce4c");
    }

    @Test
    public void testByDate() {

        mappedParams = new HashMap<String, Object>();

        elements = reconcileByDate.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), reconcileByDate.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("start_date"), "20-04-2016");
        assertEquals(mappedParams.get("end_date"), "20-04-2017");
        assertEquals(mappedParams.get("page"), 2);
    }
}
