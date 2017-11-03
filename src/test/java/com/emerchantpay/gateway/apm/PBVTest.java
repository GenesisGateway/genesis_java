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
        mappedParams = new HashMap<String, Object>();
        uidSale = new StringUtils().generateUID();

        // Sale
        pbvSale.setTransactionId(uidSale).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        pbvSale.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00"));
        pbvSale.setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555");
        pbvSale.setCardHolder("PLAMEN PETROV").setExpirationMonth("05").setExpirationYear("2020")
                .setCardNumber("4200000000000000").setCvv("123").setBirthDate("24-04-1988");

        pbvSale.setBillingPrimaryAddress("New York").setBillingSecondaryAddress("New York")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("New York").setBillingCountry("US")
                .setBillingZipCode("N4C1C3").setBillingState("NY");

        mappedParams.put("base_attributes", pbvSale.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", pbvSale.buildPaymentParams().getElements());
        mappedParams.put("credit_card_attributes", pbvSale.buildCreditCardParams().getElements());
        mappedParams.put("customer_info_attributes", pbvSale.buildCustomerInfoParams().getElements());
    }

    @Before
    public void createYeePay() throws MalformedURLException {
        mappedParams = new HashMap<String, Object>();
        uidYeepay = new StringUtils().generateUID();

        // YeePay
        pbvYeepay.setTransactionId(uidYeepay).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        pbvYeepay.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00"));
        pbvYeepay.setCustomerEmail("john@example.com")
                .setCustomerPhone("+55555555");
        pbvYeepay.setProductName("Interstellar").setProductCategory("Movie").setCustomerId("120104198302030841")
                .setCustomerBankId(Banks.ICBC).setCustomerName("李珊珊")
                .setBankAccountNumber("6222020302063077036");

        mappedParams.put("base_attributes", pbvYeepay.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", pbvYeepay.buildPaymentParams().getElements());
        mappedParams.put("customer_info_attributes", pbvYeepay.buildCustomerInfoParams().getElements());
    }

    public void setMissingParams() {
        pbvSale.setBillingCountry(null);
    }

    @Test
    public void testSale() throws MalformedURLException {

        elements = pbvSale.getElements();

        mappedParams.put("billing_address", pbvSale.getBillingAddress().getElements());

        assertEquals(mappedParams.get("base_attributes"), pbvSale.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), pbvSale.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("credit_card_attributes"), pbvSale.buildCreditCardParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), pbvSale.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("billing_address"), pbvSale.getBillingAddress().getElements());
    }

    @Test
    public void testSaleWithMissingParams() {

        setMissingParams();

        elements = pbvSale.buildBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), pbvSale.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }

    @Test
    public void testYeePay() throws MalformedURLException {

        elements = pbvYeepay.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), pbvYeepay.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), pbvYeepay.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), pbvYeepay.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("customer_info_attributes"), pbvYeepay.buildCustomerInfoParams().getElements());
        assertEquals(mappedParams.get("product_name"), "Interstellar");
        assertEquals(mappedParams.get("product_category"), "Movie");
        assertEquals(mappedParams.get("customer_name"), "李珊珊");
        assertEquals(mappedParams.get("customer_id_number"), "120104198302030841");
        assertEquals(mappedParams.get("customer_bank_id"), Banks.ICBC);
        assertEquals(mappedParams.get("bank_account_number"), "6222020302063077036");
    }
}