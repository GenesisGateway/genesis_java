package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.constants.Banks;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVYeePayRequest;
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

public class PBVTest {

    private PBVSaleRequest pbvSale = new PBVSaleRequest();
    private PBVYeePayRequest pbvYeepay = new PBVYeePayRequest();

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uidSale;
    private String uidYeepay;

    @Before
    public void createSale() throws MalformedURLException {
        uidSale = new StringUtils().generateUID();

        // Sale
        pbvSale.setTransactionId(uidSale).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setCardholder("PLAMEN PETROV").setExpirationMonth("05").setExpirationYear("2020")
                .setCardNumber("4200000000000000").setCvv("123").billingAddress().setAddress1("New York")
                .setAddress2("Sydney 2").setFirstname("Plamen").setLastname("Petrov").setCity("New York")
                .setCountry("US").setZipCode("N4C1C3").setState("NY").done().setBirthDate("24-04-1988");
    }

    @Before
    public void createYeePay() throws MalformedURLException {
        uidYeepay = new StringUtils().generateUID();

        // YeePay
        pbvYeepay.setTransactionId(uidYeepay).setRemoteIp("82.137.112.202").setUsage("TICKETS")
                .setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")).setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555").setProductName("Interstellar").setProductCategory("Movie")
                .setCustomerId("120104198302030841").setCustomerBankId(Banks.ICBC).setCustomerName("李珊珊")
                .setBankAccountNumber("6222020302063077036");
    }

    public void setMissingParams() {
        pbvSale.billingAddress().setCountry(null).done();
    }

    @Test
    public void testSale() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = pbvSale.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), pbvSale.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidSale);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("card_number"), "4200000000000000");
        assertEquals(mappedParams.get("card_holder"), "PLAMEN PETROV");
        assertEquals(mappedParams.get("expiration_month"), "05");
        assertEquals(mappedParams.get("expiration_year"), "2020");
        assertEquals(mappedParams.get("birth_date"), "24-04-1988");
        assertEquals(mappedParams.get("billing_address"), pbvSale.getBillingAddress());
    }

    @Test
    public void testSaleWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = pbvSale.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), pbvSale.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testYeePay() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();

        elements = pbvYeepay.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), pbvYeepay.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uidYeepay);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.USD.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("product_name"), "Interstellar");
        assertEquals(mappedParams.get("product_category"), "Movie");
        assertEquals(mappedParams.get("customer_name"), "李珊珊");
        assertEquals(mappedParams.get("customer_id_number"), "120104198302030841");
        assertEquals(mappedParams.get("customer_bank_id"), Banks.ICBC);
        assertEquals(mappedParams.get("bank_account_number"), "6222020302063077036");
    }

}
