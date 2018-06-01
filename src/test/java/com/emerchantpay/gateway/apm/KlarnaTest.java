package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.GenesisValidator;
import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.constants.KlarnaItemTypes;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.requests.financial.apm.klarna.KlarnaAuthorizeRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.klarna.KlarnaCaptureRequest;
import com.emerchantpay.gateway.model.klarna.KlarnaItem;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class KlarnaTest {

    // Genesis Validator
    private GenesisValidator validator;

    // Payment Klarna Authorize
    private KlarnaAuthorizeRequest klarnaAuthorize;

    // Payment Klarna Capture
    private KlarnaCaptureRequest klarnaCapture;

    // Transaction types
    private ArrayList<String> transactionTypes;

    // Parameters
    private String transactionId;
    private BigDecimal amount;
    private String customerEmail;
    private String customerPhone;

    @Before
    public void setupOneItem() throws IllegalAccessException {
        validator = new GenesisValidator();

        // Intitial params
        transactionId = UUID.randomUUID().toString();
        amount = new BigDecimal("4.00");
        customerEmail = "johndoe@example.com";
        customerPhone = "+555555555";

        // Transaction types list
        transactionTypes = new ArrayList<String>();
        transactionTypes.add(TransactionTypes.KLARNA_AUTHORIZE);

        // Klarna Authorize
        klarnaAuthorize = new KlarnaAuthorizeRequest();

        klarnaAuthorize.setTransactionId(transactionId);
        klarnaAuthorize.setAmount(amount).setCurrency(Currency.EUR.getCurrency());
        klarnaAuthorize.setCustomerEmail(customerEmail).setCustomerPhone(customerPhone);

        klarnaAuthorize.setBillingCountry(Country.Sweden.getCode()).setBillingCity("Stockholm")
                .setBillingFirstname("Sigurdsson").setBillingLastname("Swenson")
                .setBillingPrimaryAddress("Street 1").setBillingSecondaryAddress("Street 2");

        // Klarna Authorize
        klarnaCapture = new KlarnaCaptureRequest();

        klarnaCapture.setTransactionId(transactionId);
        klarnaCapture.setAmount(amount).setCurrency(Currency.EUR.getCurrency());
        klarnaCapture.setCustomerEmail(customerEmail).setCustomerPhone(customerPhone);

        klarnaCapture.setBillingCountry(Country.Sweden.getCode()).setBillingCity("Stockholm")
                .setBillingFirstname("Sigurdsson").setBillingLastname("Swenson")
                .setBillingPrimaryAddress("Street 1").setBillingSecondaryAddress("Street 2");
    }

    @Test
    public void testWithOneItem() {
        KlarnaItem klarnaItem = new KlarnaItem("TICKETS", KlarnaItemTypes.PHYSICAL, 2,
                new BigDecimal("2.00"), new BigDecimal("4.00"));

        assertTrue(validator.isValidRequest(TransactionTypes.KLARNA_AUTHORIZE,
                (Request) klarnaAuthorize.addKlarnaItem(klarnaItem), amount, klarnaAuthorize.getOrderTaxAmount()));
    }

    @Test
    public void testAuthorizeWithMoreThanOneItem() throws IllegalAccessException {
        amount = new BigDecimal("6.00");

        // Change amount
        klarnaAuthorize.setAmount(amount);

        // Klarna Item 1
        KlarnaItem item1 = new KlarnaItem("TICKETS", KlarnaItemTypes.PHYSICAL, 2,
                new BigDecimal("2.00"), new BigDecimal("4.00"));

        // Klarna Item 2
        KlarnaItem item2 = new KlarnaItem("TICKETS", KlarnaItemTypes.DISCOUNT, 2,
                new BigDecimal("2.00"), new BigDecimal("2.00"));
        item2.setTotalDiscountAmount(new BigDecimal("2.00"));

        ArrayList<KlarnaItem> klarnaItemsList = new ArrayList<KlarnaItem>();
        klarnaItemsList.add(item1);
        klarnaItemsList.add(item2);

        assertTrue(validator.isValidRequest(TransactionTypes.KLARNA_AUTHORIZE,
                (Request) klarnaAuthorize.addKlarnaItems(klarnaItemsList), amount, klarnaAuthorize.getOrderTaxAmount()));
        assertTrue(validator.isValidRequest(TransactionTypes.KLARNA_CAPTURE,
                (Request) klarnaCapture.addKlarnaItems(klarnaItemsList), amount));
    }

    @Test(expected = GenesisException.class)
    public void testWithoutItems() {
        assertFalse(validator.isValidRequest(TransactionTypes.KLARNA_AUTHORIZE,
                null, amount, klarnaAuthorize.getOrderTaxAmount()));
        assertFalse(validator.isValidRequest(TransactionTypes.KLARNA_CAPTURE,
                null, amount));
    }
}
