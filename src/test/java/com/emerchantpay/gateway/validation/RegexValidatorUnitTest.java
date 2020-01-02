package com.emerchantpay.gateway.validation;

import com.emerchantpay.gateway.api.validation.GenesisValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexValidatorUnitTest {

    // Genesis Validator
    private GenesisValidator validator;

    // Parameters
    private BigDecimal amount;
    private String cardHolder;
    private String cardNumber;
    private String cvv;
    private String expirationMonth;
    private String expirationYear;
    private String customerEmail;
    private String customerPhone;
    private String notificationUrl;
    private String giftCardNumber;

    @Before
    public void setup() {

        validator = new GenesisValidator();

        // Intitial params
        amount = new BigDecimal("2.00");
        customerEmail = "johndoe@example.com";
        customerPhone = "+555555555";
        notificationUrl = "http://google.com";
    }

    // Amount
    @Test
    public void testAmountValidationSuccess() {
        assertTrue(validator.isValidAmount(amount));

        amount = new BigDecimal("0.99");
        assertTrue(validator.isValidAmount(amount));
    }

    @Test
    public void testAmountValidationFailed() {
        amount = new BigDecimal("0.00");
        assertFalse(validator.isValidAmount(amount));
    }

    // Credit card details
    // Card holder
    @Test
    public void testCardHolderSuccess() {
        cardHolder = "Emil Example";
        assertTrue(validator.isValidCardHolder(cardHolder));
    }

    @Test
    public void testCardHolderFailure() {
        cardHolder = "Emil Example123";
        assertFalse(validator.isValidCardHolder(cardHolder));
    }

    // Card number
    @Test
    public void testCardNumberSuccess() {
        cardNumber = "4200000000000000";
        assertTrue(validator.isValidCardNumber(cardNumber));
    }

    @Test
    public void testCardNumberFailure() {
        cardNumber = "42000000xxxx0000";
        assertFalse(validator.isValidCardNumber(cardNumber));
    }

    // CVV
    @Test
    public void testCVVSuccess() {
        cvv = "123";
        assertTrue(validator.isValidCvv(cvv));
    }

    @Test
    public void testCVVFailure() {
        cvv = "12345";
        assertFalse(validator.isValidCvv(cvv));
    }

    // Exp. month
    @Test
    public void testExpirationMonthSuccess() {
        expirationMonth = "02";
        assertTrue(validator.isValidExpMonth(expirationMonth));
    }

    @Test
    public void testExpirationMonthFailure() {
        expirationMonth = "12345";
        assertFalse(validator.isValidExpMonth(expirationMonth));
    }

    // Exp. Year
    @Test
    public void testExpirationYearSuccess() {
        expirationYear = "2020";
        assertTrue(validator.isValidExpYear(expirationYear));
    }

    @Test
    public void testExpirationYearFailure() {
        expirationYear = "20222";
        assertFalse(validator.isValidExpYear(expirationYear));
    }

    // Email
    @Test
    public void testEmailValidationSuccess() {
        assertTrue(validator.isValidEmail(customerEmail));
    }

    @Test
    public void testEmailValidationFailed() {
        customerEmail = "johndoe.example.com";
        assertFalse(validator.isValidEmail(customerEmail));

        customerEmail = "";
        assertFalse(validator.isValidEmail(customerEmail));
    }

    // Phone
    @Test
    public void testPhoneValidationSuccess() {
        assertTrue(validator.isValidPhone(customerPhone));
    }

    @Test
    public void testPhoneValidationFailed() {
        customerPhone = "test555555555";
        assertFalse(validator.isValidPhone(customerPhone));

        customerPhone = "";
        assertFalse(validator.isValidPhone(customerPhone));
    }

    // URLs
    @Test
    public void testUrlValidationSuccess() {
        assertTrue(validator.isValidUrl("notification_url", notificationUrl));
    }

    @Test
    public void testUrlValidationFailed() {
        notificationUrl = ":// should fail";
        assertFalse(validator.isValidUrl("notification_url", notificationUrl));

        notificationUrl = ".";
        assertFalse(validator.isValidUrl("notification_url", notificationUrl));

        notificationUrl = "google.com";
        assertFalse(validator.isValidUrl("notification_url", notificationUrl));
    }

    // Gift card number
    @Test
    public void testGiftCardNumberSuccess() {
        giftCardNumber = "7000001163991388834";
        assertTrue(validator.isValidGiftCard(giftCardNumber));
    }

    @Test
    public void testGiftCardNumberFailiure() {
        giftCardNumber = "test555555555";
        assertFalse(validator.isValidGiftCard(giftCardNumber));
    }
}

