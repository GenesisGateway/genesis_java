package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.requests.financial.sdd.*;
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

public class SDDRequestsTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams = new HashMap<String, Object>();

    private String uidSale;
    private String uidRecurring;
    private String uidinitRecurring;
    private String uidRefund;

    private SDDSaleRequest sddSale = new SDDSaleRequest();
    private SDDInitRecurringSaleRequest sddInitRecurring = new SDDInitRecurringSaleRequest();
    private SDDRecurringSaleRequest sddRecurring = new SDDRecurringSaleRequest();
    private SDDRefundRequest sddRefund = new SDDRefundRequest();

    @Before
    public void createSSDSale() {
        uidSale = new StringUtils().generateUID();

        // SDD Sale
        sddSale.setTransactionId(uidSale).setRemoteIp("94.26.28.135").setUsage("TICKETS");
        sddSale.setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency());
        sddSale.setIban("DE09100100101234567894").setBic("PBNKDEFFXXX");

        sddSale.setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCountry(Country.Germany.getCode());

        mappedParams.put("base_attributes", sddSale.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sddSale.buildPaymentParams().getElements());
        mappedParams.put("sdd_attributes", sddSale.buildSDDParams().getElements());
    }

    @Before
    public void createInitRecurring() {
        uidinitRecurring = new StringUtils().generateUID();

        // SDD Init Recurring Sale
        sddInitRecurring.setTransactionId(uidinitRecurring).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        sddInitRecurring.setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency());
        sddInitRecurring.setIban("DE09100100101234567894")
                .setBic("PBNKDEFFXXX");

        sddInitRecurring.setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCountry(Country.Germany.getCode());

        mappedParams.put("base_attributes", sddInitRecurring.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sddInitRecurring.buildPaymentParams().getElements());
        mappedParams.put("sdd_attributes", sddInitRecurring.buildSDDParams().getElements());
    }

    @Before
    public void createRecurringSale() {
        uidRecurring = new StringUtils().generateUID();

        // SDD Recurring Sale
        sddRecurring.setTransactionId(uidRecurring).setRemoteIp("94.198.63.122").setUsage("TICKETS");
        sddRecurring.setAmount(new BigDecimal("1.00")).setCurrency(Currency.EUR.getCurrency());
        sddRecurring.setReferenceId("c3fe2428802a2d3a0b6a17a64ab9bc06");

        mappedParams.put("base_attributes", sddRecurring.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sddRecurring.buildPaymentParams().getElements());
    }

    @Before
    public void createSDDRefund() {
        uidRefund = new StringUtils().generateUID();

        // SDD Refund
        sddRefund.setTransactionId(uidRefund).setRemoteIp("192.168.0.1").setUsage("TICKETS");
        sddRefund.setAmount(new BigDecimal("1.00")).setCurrency(Currency.EUR.getCurrency());
        sddRefund.setReferenceId("0ba6736103ce76be35527ebbe55e69c3");

        mappedParams.put("base_attributes", sddRefund.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", sddRefund.buildPaymentParams().getElements());
    }

    @Test
    public void testSale() {
        elements = sddSale.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address") {
                mappedParams.put("billing_address", sddSale.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), sddSale.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), sddSale.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sddSale.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("sdd_attributes"), sddSale.buildSDDParams().getElements());
        assertEquals(mappedParams.get("billing_address"), sddSale.getBillingAddress().getElements());
    }

    @Test
    public void testInitRecurring() {
        elements = sddInitRecurring.getElements();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey() == "billing_address")
            {
                mappedParams.put("billing_address", sddInitRecurring.getBillingAddress().getElements());
            }
            else {
                mappedParams.put(elements.get(i).getKey(), sddInitRecurring.getElements().get(i).getValue());
            }
        }

        assertEquals(mappedParams.get("base_attributes"), sddInitRecurring.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sddInitRecurring.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("sdd_attributes"), sddInitRecurring.buildSDDParams().getElements());
        assertEquals(mappedParams.get("billing_address"), sddInitRecurring.getBillingAddress().getElements());
    }

    @Test
    public void testSDDRecurringSale() {
        elements = sddRecurring.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), sddRecurring.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), sddRecurring.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sddRecurring.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("reference_id"), "c3fe2428802a2d3a0b6a17a64ab9bc06");
    }

    @Test
    public void testSDDRefund() {
        elements = sddRefund.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), sddRefund.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), sddRefund.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), sddRefund.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("reference_id"), "0ba6736103ce76be35527ebbe55e69c3");
    }
}