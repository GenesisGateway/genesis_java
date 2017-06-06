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
    private HashMap<String, Object> mappedParams;

    private String uidSale;
    private String uidRecurring;
    private String uidinitRecurring;
    private String uidPayout;
    private String uidRefund;

    private SDDSaleRequest sddSale = new SDDSaleRequest();
    private SDDPayoutRequest sddPayout = new SDDPayoutRequest();
    private SDDInitRecurringSaleRequest sddInitRecurring = new SDDInitRecurringSaleRequest();
    private SDDRecurringSaleRequest sddRecurring = new SDDRecurringSaleRequest();
    private SDDRefundRequest sddRefund = new SDDRefundRequest();

    @Before
    public void createSSDSale() {
        uidSale = new StringUtils().generateUID();

        // SDD Sale
        sddSale.setTransactionId(uidSale).setRemoteIp("94.26.28.135").setUsage("TICKETS")
                .setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency())
                .setIBAN("DE09100100101234567894").setBIC("PBNKDEFFXXX").billingAddress().setFirstname("Plamen").setLastname("Petrov")
                .setCountry(Country.Germany.getCode()).done();
    }

    @Before
    public void createInitRecurring() {
        uidinitRecurring = new StringUtils().generateUID();

        // SDD Init Recurring Sale
        sddInitRecurring.setTransactionId(uidinitRecurring).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency()).setIBAN("DE09100100101234567894")
                .setBIC("PBNKDEFFXXX").billingAddress().setFirstname("Plamen").setLastname("Petrov")
                .setCountry(Country.Germany.getCode()).done();
    }

    @Before
    public void createSSDPayout() {
        uidPayout = new StringUtils().generateUID();

        // SDD Payout
        sddPayout.setTransactionId(uidPayout).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("2.00")).setCurrency(Currency.EUR.getCurrency()).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setIBAN("DE09100100101234567894").setBIC("PBNKDEFFXXX")
                .billingAddress().setAddress1("New York").setAddress2("Dallas")
                .setFirstname("Plamen").setLastname("Petrov").setCountry(Country.Germany.getCode()).setCity("Berlin")
                .setZipCode("1000").setState("NY").done()
                .shippingAddress().setAddress1("New York").setAddress2("Dallas").setFirstname("Plamen").setLastname("Petrov")
                .setCountry(Country.Germany.getCode()).setCity("Berlin").setZipCode("1000").setState("NY").done();
    }

    @Before
    public void createRecurringSale() {
       uidRecurring = new StringUtils().generateUID();

        // SDD Recurring Sale
        sddRecurring.setTransactionId(uidRecurring).setRemoteIp("94.198.63.122").setUsage("TICKETS")
                .setAmount(new BigDecimal("1.00")).setCurrency(Currency.EUR.getCurrency())
                .setReferenceId("c3fe2428802a2d3a0b6a17a64ab9bc06");
    }

    @Before
    public void createSDDRefund() {
        uidRefund = new StringUtils().generateUID();

        // SDD Refund
        sddRefund.setTransactionId(uidRefund).setRemoteIp("192.168.0.1").setUsage("TICKETS")
                .setAmount(new BigDecimal("1.00")).setCurrency(Currency.EUR.getCurrency())
                .setReferenceId("0ba6736103ce76be35527ebbe55e69c3");
    }

    @Test
    public void testSale() {
        mappedParams = new HashMap<String, Object>();

        elements = sddSale.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), sddSale.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidSale);
        assertEquals(mappedParams.get("remote_ip"), "94.26.28.135");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("billing_address"), sddSale.getBillingAddress());
    }

    @Test
    public void testSDDRecurringSale() {
        mappedParams = new HashMap<String, Object>();

        elements = sddRecurring.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), sddRecurring.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidRecurring);
        assertEquals(mappedParams.get("remote_ip"), "94.198.63.122");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("100"));
        assertEquals(mappedParams.get("reference_id"), "c3fe2428802a2d3a0b6a17a64ab9bc06");
    }

    @Test
    public void testInitRecurring() {
        mappedParams = new HashMap<String, Object>();

        elements = sddInitRecurring.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), sddInitRecurring.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidinitRecurring);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("billing_address"), sddInitRecurring.getBillingAddress());
    }

    @Test
    public void testSDDPayout() {

        mappedParams = new HashMap<String, Object>();

        elements = sddPayout.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), sddPayout.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidPayout);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("billing_address"), sddPayout.getBillingAddress());
    }

    @Test
    public void testSDDRefund() {
        mappedParams = new HashMap<String, Object>();

        elements = sddRefund.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), sddRefund.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidRefund);
        assertEquals(mappedParams.get("remote_ip"), "192.168.0.1");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("100"));
        assertEquals(mappedParams.get("reference_id"), "0ba6736103ce76be35527ebbe55e69c3");
    }
}
