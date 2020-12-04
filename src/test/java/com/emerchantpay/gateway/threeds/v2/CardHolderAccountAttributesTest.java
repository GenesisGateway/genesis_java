package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardHolderAccountAttributesTest {

    private Sale3DRequest sale3DRequest;
    private Random random;
    private LocalDate today;
    private DateTimeFormatter formatter;
    private static final int MAX_BOUND_NINETEEN = 19;

    @Before
    public void createThreedsV2Attributes() {
        sale3DRequest = new Sale3DRequest();
        random = new Random();
        today = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @Test
    public void buildCardHolderAttributes() {

        String creationDate = today.plusDays(random.nextInt(365)).format(formatter);
        sale3DRequest.set3dsV2CardHolderAccountCreationDate(creationDate);
        assertEquals(creationDate, sale3DRequest.get3dsV2CardHolderAccountCreationDate());

        String lastChangeDate = today.plusDays(random.nextInt(365)).format(formatter);
        sale3DRequest.set3dsV2CardHolderAccountLastChangeDate(lastChangeDate);
        assertEquals(lastChangeDate, sale3DRequest.get3dsV2CardHolderAccountLastChangeDate());

        String passwordChangeDate = today.plusDays(random.nextInt(365)).format(formatter);
        sale3DRequest.set3dsV2CardHolderAccountPasswordChangeDate(passwordChangeDate);
        assertEquals(passwordChangeDate, sale3DRequest.get3dsV2CardHolderAccountPasswordChangeDate());

        String shippingAddressFirstUsedDate = today.plusDays(random.nextInt(365)).format(formatter);
        sale3DRequest.set3dsV2CardHolderAccountShippingAddressDateFirstUsed(shippingAddressFirstUsedDate);
        assertEquals(shippingAddressFirstUsedDate, sale3DRequest.get3dsV2CardHolderAccountShippingAddressDateFirstUsed());

        String registrationDate = today.plusDays(random.nextInt(365)).format(formatter);
        sale3DRequest.set3dsV2CardHolderAccountRegistrationDate(registrationDate);
        assertEquals(registrationDate, sale3DRequest.get3dsV2CardHolderAccountRegistrationDate());

        String updateIndicator = "current_transaction";
        sale3DRequest.set3dsV2CardHolderAccountUpdateIndicator(updateIndicator);
        assertEquals(updateIndicator, sale3DRequest.get3dsV2CardHolderAccountUpdateIndicator());

        String passwordChangeIndicator = "30_to_60_days";
        sale3DRequest.set3dsV2CardHolderAccountPasswordChangeIndicator(passwordChangeIndicator);
        assertEquals(passwordChangeIndicator, sale3DRequest.get3dsV2CardHolderAccountPasswordChangeIndicator());

        String shippingAddressUsageIndicator = "more_than_60days";
        sale3DRequest.set3dsV2CardHolderAccountShippingAddressUsageIndicator(shippingAddressUsageIndicator);
        assertEquals(shippingAddressUsageIndicator, sale3DRequest.get3dsV2CardHolderAccountShippingAddressUsageIndicator());

        String suspiciousActivityIndicator = "suspicious_observed";
        sale3DRequest.set3dsV2CardHolderAccountSuspiciousActivityIndicator(suspiciousActivityIndicator);
        assertEquals(suspiciousActivityIndicator, sale3DRequest.get3dsV2CardHolderAccountSuspiciousActivityIndicator());

        String registrationIndicator = "guest_checkout";
        sale3DRequest.set3dsV2CardHolderAccountRegistrationIndicator(registrationIndicator);
        assertEquals(registrationIndicator, sale3DRequest.get3dsV2CardHolderAccountRegistrationIndicator());

        Integer trxActivityLast24Hours = random.nextInt(MAX_BOUND_NINETEEN);
        sale3DRequest.set3dsV2CardHolderAccountTransactionsActivityLast24Hours(trxActivityLast24Hours);
        assertEquals(trxActivityLast24Hours, sale3DRequest.get3dsV2CardHolderAccountTransactionsActivityLast24Hours());

        Integer trxActivityPreviousYear = random.nextInt(MAX_BOUND_NINETEEN);
        sale3DRequest.set3dsV2CardHolderAccountTransactionsActivityPreviousYear(trxActivityPreviousYear);
        assertEquals(trxActivityPreviousYear, sale3DRequest.get3dsV2CardHolderAccountTransactionsActivityPreviousYear());

        Integer provisionAttemptsLast24Hours = random.nextInt(MAX_BOUND_NINETEEN);
        sale3DRequest.set3dsV2CardHolderAccountProvisionAttemptsLast24Hours(provisionAttemptsLast24Hours);
        assertEquals(provisionAttemptsLast24Hours, sale3DRequest.get3dsV2CardHolderAccountProvisionAttemptsLast24Hours());

        Integer purchaseCountLast6Months = random.nextInt(MAX_BOUND_NINETEEN);
        sale3DRequest.set3dsV2CardHolderAccountPurchasesCountLast6Months(purchaseCountLast6Months);
        assertEquals(purchaseCountLast6Months, sale3DRequest.get3dsV2CardHolderAccountPurchasesCountLast6Months());

        assertTrue(sale3DRequest.build3DSv2CardHolderParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void updateIndicatorError(){
        sale3DRequest.set3dsV2CardHolderAccountUpdateIndicator("wrong value");
        sale3DRequest.build3DSv2CardHolderParams();
    }

    @Test(expected = InvalidParamException.class)
    public void passwordChangeIndicatorError(){
        sale3DRequest.set3dsV2CardHolderAccountPasswordChangeIndicator("wrong value");
        sale3DRequest.build3DSv2CardHolderParams();
    }

    @Test(expected = InvalidParamException.class)
    public void shippingAddrUsageIndicatorError(){
        sale3DRequest.set3dsV2CardHolderAccountShippingAddressUsageIndicator("wrong value");
        sale3DRequest.build3DSv2CardHolderParams();
    }

    @Test(expected = InvalidParamException.class)
    public void suspiciousActivityIndicatorError(){
        sale3DRequest.set3dsV2CardHolderAccountSuspiciousActivityIndicator("wrong value");
        sale3DRequest.build3DSv2CardHolderParams();
    }

    @Test(expected = InvalidParamException.class)
    public void registrationIndicatorError(){
        sale3DRequest.set3dsV2CardHolderAccountRegistrationIndicator("wrong value");
        sale3DRequest.build3DSv2CardHolderParams();
    }
}