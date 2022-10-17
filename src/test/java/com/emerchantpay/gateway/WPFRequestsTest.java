package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.constants.ReminderConstants;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.wpf.WPFCreateRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFReconcileRequest;
import com.emerchantpay.gateway.model.Reminder;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class WPFRequestsTest {

    private GenesisClient client;
    private WPFCreateRequest wpfCreate;
    private WPFReconcileRequest wpfReconcile;

    private String uidWpf;
    private String uidReconcile;

    private Random random;
    private LocalDate today;
    private DateTimeFormatter formatter;
    private static final int MAX_BOUND = 19;

    @Before
    public void createWPF() throws MalformedURLException {
        uidWpf = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        wpfCreate = mock(WPFCreateRequest.class);

        random = new Random();
        today = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @Before
    public void createReconcile() {
        uidReconcile = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        wpfReconcile = mock(WPFReconcileRequest.class);
    }

    public WPFCreateRequest stubWPF() throws MalformedURLException {
        wpfCreate = new WPFCreateRequest();
        wpfCreate.setTransactionId(uidWpf);
        wpfCreate.setAmount(new BigDecimal("2.00"));
        wpfCreate.setCurrency("EUR");
        wpfCreate.setDescription("TICKETS");
        wpfCreate.setBillingCountry("DE");
        wpfCreate.setReturnSuccessUrl(new URL("https://example.com"));
        wpfCreate.setReturnFailureUrl(new URL("https://example.com"));
        wpfCreate.setNotificationUrl(new URL("https://example.com"));
        return wpfCreate;
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(wpfCreate.setBillingCountry(null)).thenThrow(exception);
        when(wpfReconcile.setUniqueId(null)).thenThrow(exception);
    }

    public void clearCustomerInfoParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(wpfCreate.setCustomerEmail(null)).thenThrow(exception);
        when(wpfCreate.setCustomerPhone(null)).thenThrow(exception);
    }

    public void verifyWPFExecute() {
        when(client.execute()).thenReturn(wpfCreate);
        assertEquals(client.execute(), wpfCreate);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyReconcileExecute() {
        when(client.execute()).thenReturn(wpfReconcile);
        assertEquals(client.execute(), wpfReconcile);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testWPF() throws MalformedURLException {

        // Create WPF
        when(wpfCreate.setTransactionId(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setUsage(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setDescription(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setAmount(isA(BigDecimal.class))).thenReturn(wpfCreate);
        when(wpfCreate.setCurrency(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setCustomerEmail(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setCustomerPhone(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setNotificationUrl(isA(URL.class))).thenReturn(wpfCreate);
        when(wpfCreate.setReturnSuccessUrl(isA(URL.class))).thenReturn(wpfCreate);
        when(wpfCreate.setReturnFailureUrl(isA(URL.class))).thenReturn(wpfCreate);
        when(wpfCreate.setReturnCancelUrl(isA(URL.class))).thenReturn(wpfCreate);
        when(wpfCreate.setReturnPendingUrl(isA(URL.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingPrimaryAddress(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingSecondaryAddress(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingFirstname(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingLastname(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingCity(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingCountry(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingZipCode(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setBillingState(isA(String.class))).thenReturn(wpfCreate);
        when(wpfCreate.setLifetime(isA(Integer.class))).thenReturn(wpfCreate);
        when(wpfCreate.setScaPreference(isA(Boolean.class))).thenCallRealMethod();
        when(wpfCreate.getScaPreference()).thenCallRealMethod();

        assertEquals(wpfCreate.setTransactionId(uidWpf).setUsage("TICKETS"), wpfCreate);
        assertEquals(wpfCreate.setDescription("TEST Description"), wpfCreate);
        assertEquals(wpfCreate.setCurrency(Currency.USD.getCurrency())
                .setAmount(new BigDecimal("2.00")), wpfCreate);
        assertEquals(wpfCreate.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), wpfCreate);
        assertEquals(wpfCreate.setReturnSuccessUrl(new URL("https://www.example.com/success"))
                .setReturnFailureUrl(new URL("https://www.example.com/failure")), wpfCreate);
        assertEquals(wpfCreate.setReturnCancelUrl(new URL("https://example.com/return_cancel"))
                .setNotificationUrl(new URL("https://example.com/notification")), wpfCreate);
        assertEquals(wpfCreate.setReturnPendingUrl(new URL("https://example.com/return_pending")), wpfCreate);
        assertEquals(wpfCreate.setBillingPrimaryAddress("Berlin")
                .setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen")
                .setBillingLastname("Petrov").setBillingCity("Berlin")
                .setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3")
                .setBillingState("BE"), wpfCreate);
        assertEquals(wpfCreate.setLifetime(60), wpfCreate);
        assertEquals(wpfCreate, wpfCreate.setScaPreference(true));
        assertEquals(true, wpfCreate.getScaPreference());

        verify(wpfCreate).setTransactionId(uidWpf);
        verify(wpfCreate).setUsage("TICKETS");
        verify(wpfCreate).setDescription("TEST Description");
        verify(wpfCreate).setCurrency(Currency.USD.getCurrency());
        verify(wpfCreate).setAmount(new BigDecimal("2.00"));
        verify(wpfCreate).setCustomerEmail("john@example.com");
        verify(wpfCreate).setCustomerPhone("+55555555");
        verify(wpfCreate).setReturnSuccessUrl(new URL("https://www.example.com/success"));
        verify(wpfCreate).setReturnFailureUrl(new URL("https://www.example.com/failure"));
        verify(wpfCreate).setReturnCancelUrl(new URL("https://example.com/return_cancel"));
        verify(wpfCreate).setReturnPendingUrl(new URL("https://example.com/return_pending"));
        verify(wpfCreate).setNotificationUrl(new URL("https://example.com/notification"));
        verify(wpfCreate).setBillingPrimaryAddress("Berlin");
        verify(wpfCreate).setBillingSecondaryAddress("Berlin");
        verify(wpfCreate).setBillingFirstname("Plamen");
        verify(wpfCreate).setBillingLastname("Petrov");
        verify(wpfCreate).setBillingCity("Berlin");
        verify(wpfCreate).setBillingCountry(Country.Germany.getCode());
        verify(wpfCreate).setBillingZipCode("M4B1B3");
        verify(wpfCreate).setBillingState("BE");
        verify(wpfCreate).setLifetime(60);
        verify(wpfCreate).setScaPreference(true);
        verify(wpfCreate).getScaPreference();
        verifyNoMoreInteractions(wpfCreate);

        verifyWPFExecute();
    }

    @Test
    public void testReconcile() {
        when(wpfReconcile.setUniqueId(isA(String.class))).thenReturn(wpfReconcile);

        assertEquals(wpfReconcile.setUniqueId(uidReconcile), wpfReconcile);

        verify(wpfReconcile).setUniqueId(uidReconcile);
    }

    @Test(expected = ApiException.class)
    public void testWPFWithMissingRequiredParams() {
        clearRequiredParams();
        assertNull(wpfCreate.setBillingCountry(null));
        verify(wpfCreate).setBillingCountry(null);
        verifyNoMoreInteractions(wpfCreate);

        verifyWPFExecute();
    }

    @Test(expected = ApiException.class)
    public void testWPFWithMissingCustomerInfoParams() {
        clearCustomerInfoParams();
        assertNull(wpfCreate.setCustomerEmail(null));
        verify(wpfCreate).setCustomerEmail(null);
        assertNull(wpfCreate.setCustomerPhone(null));
        verify(wpfCreate).setCustomerPhone(null);
        verifyNoMoreInteractions(wpfCreate);

        verifyWPFExecute();
    }

    @Test
    public void testRemindersSuccess() throws MalformedURLException {
        stubWPF();
        wpfCreate.setPayLater(true);
        wpfCreate.addReminder(ReminderConstants.REMINDERS_CHANNEL_EMAIL, 1)
                .addReminder(ReminderConstants.REMINDERS_CHANNEL_SMS, 1).done();

        assertEquals(wpfCreate.setPayLater(true), wpfCreate);
        assertEquals(wpfCreate.addReminder(ReminderConstants.REMINDERS_CHANNEL_EMAIL, 1), wpfCreate.getReminders());
        assertEquals(wpfCreate.addReminder(ReminderConstants.REMINDERS_CHANNEL_SMS, 1), wpfCreate.getReminders());
        assertNotNull(wpfCreate.getReminders());
    }

    @Test(expected = GenesisException.class)
    public void testRemindersFailure() throws MalformedURLException {
        stubWPF();
        wpfCreate.setPayLater(true);
        wpfCreate.addReminder("test", 1).done();

        assertEquals(wpfCreate.addReminder("test", 1), wpfCreate.getReminders());
        assertEquals(wpfCreate.getReminders().getList(), new ArrayList<Reminder>());
    }

    @Test
    public void testSuccessTokenization() throws MalformedURLException {
        stubWPF();
        wpfCreate.setConsumerId("123456");
        wpfCreate.setCustomerEmail("test@example.com");
        wpfCreate.setRememberCard(true);
        wpfCreate.toXML();

        assertEquals(wpfCreate.setConsumerId("123456"), wpfCreate);
        assertEquals(wpfCreate.setRememberCard(true), wpfCreate);
    }

    @Test(expected = RequiredParamsException.class)
    public void testFailureTokenization() throws MalformedURLException {
        stubWPF();
        wpfCreate.setConsumerId("123456");
        wpfCreate.setRememberCard(true);
        wpfCreate.toXML();

        assertEquals(wpfCreate.setConsumerId("123456"), wpfCreate);
        assertEquals(wpfCreate.setRememberCard(true), wpfCreate);
    }

    @Test(expected = ApiException.class)
    public void testWPFReconcileWithMissingParams() {
        clearRequiredParams();
        assertNull(wpfReconcile.setUniqueId(null));
        verify(wpfReconcile).setUniqueId(null);
        verifyNoMoreInteractions(wpfReconcile);

        verifyReconcileExecute();
    }

    @Test
    public void testSuccessConsumerPhone() throws MalformedURLException {
        stubWPF();
        wpfCreate.setCustomerPhone("+444444");
        wpfCreate.setCustomerPhone("+444 444");
        wpfCreate.setCustomerPhone("+55555555");

        assertNotNull(wpfCreate.buildCustomerInfoParams());
    }

    @Test(expected = RegexException.class)
    public void testFailureConsumerPhone() throws MalformedURLException {
        stubWPF();
        wpfCreate.setCustomerPhone("+444 444");

        assertNotNull(wpfCreate.buildCustomerInfoParams());
    }

    @Test
    public void testWPFWithFXRateId() throws MalformedURLException {
        stubWPF();
        wpfCreate.addTransactionType(TransactionTypes.AUTHORIZE).addParam("fx_rate_id", "123");
        wpfCreate.toXML();

        assertEquals(wpfCreate.addTransactionType(TransactionTypes.AUTHORIZE)
                .addParam("fx_rate_id", "123").done(), wpfCreate);
        assertTrue(wpfCreate.addTransactionType(TransactionTypes.AUTHORIZE)
                .addParam("fx_rate_id", "123").toXML()
                .contains("<fx_rate_id>123</fx_rate_id>"));
    }


    @Test
    public void testTwoWPFRequestParams() throws MalformedURLException {

        //First request
        WPFCreateRequest wpfCreateRequest1 = stubWPF();
        wpfCreateRequest1.setCustomerEmail("john@example.com");

        //Second request - no customer email
        WPFCreateRequest wpfCreateRequest2 = stubWPF();

        assertNotEquals(wpfCreateRequest1.getValidator(), wpfCreateRequest2.getValidator());
        assertNotEquals(wpfCreateRequest1.getCustomerInfoAttrRequestBuilder(), wpfCreateRequest2.getCustomerInfoAttrRequestBuilder());
        assertNotEquals(wpfCreateRequest1.toXML(), wpfCreateRequest2.toXML());
        assertNotEquals(wpfCreateRequest1.getCustomerEmail(), wpfCreateRequest2.getCustomerEmail());
        assertNotEquals(wpfCreateRequest1.getRequest(), wpfCreateRequest2.getRequest());
    }

    @Test(expected = RegexException.class)
    public void testNegativeAmountError() throws MalformedURLException {
        WPFCreateRequest wpfCreateRequest = stubWPF();
        wpfCreateRequest.setCurrency(Currency.USD.getCurrency());
        wpfCreateRequest.setAmount(new BigDecimal("-22.00"));
        wpfCreateRequest.buildPaymentParams();
    }

    @Test
    public void testZeroAmount() throws MalformedURLException {
        WPFCreateRequest wpfCreateRequest = stubWPF();
        wpfCreateRequest.setCurrency(Currency.USD.getCurrency());
        BigDecimal amount = new BigDecimal("0.00");
        wpfCreateRequest.setAmount(new BigDecimal("0.00"));
        assertEquals(amount, wpfCreateRequest.getAmount());
        assertTrue(wpfCreateRequest.buildPaymentParams() instanceof RequestBuilder);
    }

    @Test
    public void buildThreedsV2Attributes() {
        wpfCreate = new WPFCreateRequest();

        //Control attributes
        String challengeWindowSize = "390x400";
        wpfCreate.set3dsV2ControlChallengeWindowSize(challengeWindowSize);
        assertEquals(challengeWindowSize, wpfCreate.get3dsV2ControlChallengeWindowSize());

        String challengeIndicator = "preference";
        wpfCreate.set3dsV2ControlChallengeIndicator(challengeIndicator);
        assertEquals(challengeIndicator, wpfCreate.get3dsV2ControlChallengeIndicator());

        //Card holder account attributes
        String creationDate = today.plusDays(random.nextInt(365)).format(formatter);
        wpfCreate.set3dsV2CardHolderAccountCreationDate(creationDate);
        assertEquals(creationDate, wpfCreate.get3dsV2CardHolderAccountCreationDate());

        String lastChangeDate = today.plusDays(random.nextInt(365)).format(formatter);
        wpfCreate.set3dsV2CardHolderAccountLastChangeDate(lastChangeDate);
        assertEquals(lastChangeDate, wpfCreate.get3dsV2CardHolderAccountLastChangeDate());

        String passwordChangeDate = today.plusDays(random.nextInt(365)).format(formatter);
        wpfCreate.set3dsV2CardHolderAccountPasswordChangeDate(passwordChangeDate);
        assertEquals(passwordChangeDate, wpfCreate.get3dsV2CardHolderAccountPasswordChangeDate());

        String shippingAddressFirstUsedDate = today.plusDays(random.nextInt(365)).format(formatter);
        wpfCreate.set3dsV2CardHolderAccountShippingAddressDateFirstUsed(shippingAddressFirstUsedDate);
        assertEquals(shippingAddressFirstUsedDate, wpfCreate.get3dsV2CardHolderAccountShippingAddressDateFirstUsed());

        String registrationDate = today.plusDays(random.nextInt(365)).format(formatter);
        wpfCreate.set3dsV2CardHolderAccountRegistrationDate(registrationDate);
        assertEquals(registrationDate, wpfCreate.get3dsV2CardHolderAccountRegistrationDate());

        String updateIndicator = "current_transaction";
        wpfCreate.set3dsV2CardHolderAccountUpdateIndicator(updateIndicator);
        assertEquals(updateIndicator, wpfCreate.get3dsV2CardHolderAccountUpdateIndicator());

        String passwordChangeIndicator = "30_to_60_days";
        wpfCreate.set3dsV2CardHolderAccountPasswordChangeIndicator(passwordChangeIndicator);
        assertEquals(passwordChangeIndicator, wpfCreate.get3dsV2CardHolderAccountPasswordChangeIndicator());

        String shippingAddressUsageIndicator = "more_than_60days";
        wpfCreate.set3dsV2CardHolderAccountShippingAddressUsageIndicator(shippingAddressUsageIndicator);
        assertEquals(shippingAddressUsageIndicator, wpfCreate.get3dsV2CardHolderAccountShippingAddressUsageIndicator());

        String suspiciousActivityIndicator = "suspicious_observed";
        wpfCreate.set3dsV2CardHolderAccountSuspiciousActivityIndicator(suspiciousActivityIndicator);
        assertEquals(suspiciousActivityIndicator, wpfCreate.get3dsV2CardHolderAccountSuspiciousActivityIndicator());

        String registrationIndicator = "guest_checkout";
        wpfCreate.set3dsV2CardHolderAccountRegistrationIndicator(registrationIndicator);
        assertEquals(registrationIndicator, wpfCreate.get3dsV2CardHolderAccountRegistrationIndicator());

        Integer trxActivityLast24Hours = random.nextInt(MAX_BOUND);
        wpfCreate.set3dsV2CardHolderAccountTransactionsActivityLast24Hours(trxActivityLast24Hours);
        assertEquals(trxActivityLast24Hours, wpfCreate.get3dsV2CardHolderAccountTransactionsActivityLast24Hours());

        Integer trxActivityPreviousYear = random.nextInt(MAX_BOUND);
        wpfCreate.set3dsV2CardHolderAccountTransactionsActivityPreviousYear(trxActivityPreviousYear);
        assertEquals(trxActivityPreviousYear, wpfCreate.get3dsV2CardHolderAccountTransactionsActivityPreviousYear());

        Integer provisionAttemptsLast24Hours = random.nextInt(MAX_BOUND);
        wpfCreate.set3dsV2CardHolderAccountProvisionAttemptsLast24Hours(provisionAttemptsLast24Hours);
        assertEquals(provisionAttemptsLast24Hours, wpfCreate.get3dsV2CardHolderAccountProvisionAttemptsLast24Hours());

        Integer purchaseCountLast6Months = random.nextInt(MAX_BOUND);
        wpfCreate.set3dsV2CardHolderAccountPurchasesCountLast6Months(purchaseCountLast6Months);
        assertEquals(purchaseCountLast6Months, wpfCreate.get3dsV2CardHolderAccountPurchasesCountLast6Months());

        //Merchant risk attributes
        String shippingIndicator = "digital_goods";
        wpfCreate.set3dsV2MerchantRiskShippingIndicator(shippingIndicator);
        assertEquals(shippingIndicator, wpfCreate.get3dsV2MerchantRiskShippingIndicator());

        String deliveryTimeframe = "electronic";
        wpfCreate.set3dsV2MerchantRiskDeliveryTimeframe(deliveryTimeframe);
        assertEquals(deliveryTimeframe, wpfCreate.get3dsV2MerchantRiskDeliveryTimeframe());

        String reorderItemIndicator = "first_time";
        wpfCreate.set3dsV2MerchantRiskReorderItemsIndicator(reorderItemIndicator);
        assertEquals(reorderItemIndicator, wpfCreate.get3dsV2MerchantRiskReorderItemsIndicator());

        String preorderPurchaseIndicator = "merchandise_available";
        wpfCreate.set3dsV2MerchantRiskPreOrderPurchaseIndicator(preorderPurchaseIndicator);
        assertEquals(preorderPurchaseIndicator, wpfCreate.get3dsV2MerchantRiskPreOrderPurchaseIndicator());

        String preorderDate = today.plusDays(random.nextInt(365)).format(formatter);
        wpfCreate.set3dsV2MerchantRiskPreOrderDate(preorderDate);
        assertEquals(preorderDate, wpfCreate.get3dsV2MerchantRiskPreOrderDate());

        wpfCreate.set3dsV2MerchantRiskGiftCard(true);
        assertEquals(true, wpfCreate.get3dsV2MerchantRiskGiftCard());

        Integer giftCardCount = random.nextInt(MAX_BOUND);
        wpfCreate.set3dsV2MerchantRiskGiftCardCount(giftCardCount);
        assertEquals(giftCardCount, wpfCreate.get3dsV2MerchantRiskGiftCardCount());

        //Purchase attributes
        String category = "prepaid_activation";
        wpfCreate.set3dsV2PurchaseCategory(category);
        assertEquals(category, wpfCreate.get3dsV2PurchaseCategory());
        assertTrue(wpfCreate.build3DSv2PurchaseParams() instanceof RequestBuilder);

        //Recurring attributes
        String expirationDate = today.plusDays(random.nextInt(365)).format(formatter);
        wpfCreate.set3dsV2RecurringExpirationDate(expirationDate);
        assertEquals(expirationDate, wpfCreate.get3dsV2RecurringExpirationDate());

        Integer frequency = random.nextInt(MAX_BOUND) + 1;
        wpfCreate.set3dsV2RecurringFrequency(frequency);
        assertEquals(frequency, wpfCreate.get3dsV2RecurringFrequency());

        assertTrue(wpfCreate.buildThreedsV2Params() instanceof RequestBuilder);
    }
}