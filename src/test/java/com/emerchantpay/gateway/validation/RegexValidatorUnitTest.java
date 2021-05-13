package com.emerchantpay.gateway.validation;

import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

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

        //Verify zero amount support
        amount = new BigDecimal("0.00");
        assertTrue(validator.isValidAmount(amount, true));
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
        cardHolder = "John Doe";
        assertTrue(validator.isValidCardHolder(cardHolder));
    }

    @Test
    public void testCardHolderFailure() {
        cardHolder = "John123";
        assertFalse(validator.isValidCardHolder(cardHolder));
    }

    @Test
    public void testCardHolderSpecialCharsSuccess() {
        cardHolder = "张伟 王伟";
        assertTrue(validator.isValidCardHolder(cardHolder));
    }

    @Test
    public void testCardHolderSpecialCharsFailure() {
        cardHolder = "张伟 ";
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

    // Date in format dd-mm-yyyy. Leading zeros required
    @Test
    public void testDateValidationSuccess() {
        String dateStr = "10-12-1999";
        assertTrue(validator.isValidDate(dateStr, "some_date_param"));

        dateStr = "";
        assertTrue(validator.isValidDate(dateStr, "some_date_param"));
    }

    @Test
    public void testDateValidationFailure() {
        String dateStr = "1-1-2020";
        assertFalse(validator.isValidDate(dateStr, "some_date_param"));

        dateStr = "32-10-2020";
        assertFalse(validator.isValidDate(dateStr, "some_date_param"));

        dateStr = "12-13-2020";
        assertFalse(validator.isValidDate(dateStr, "some_date_param"));
    }

    // Date in format MMDD. Leading zeros required
    @Test
    public void test_isValidMonthDate_ShouldReturnTrue_WhenValidArguments() {
        String dateStr = "0207";
        assertTrue(validator.isValidMonthDate(dateStr, "some_date_param"));
    }

    // Date in format MMDD. Leading zeros required
    @Test
    public void test_isValidMonthDate_ShouldReturnFalse_WhenInValidDate() {
        String dateStr = "1307";
        assertFalse(validator.isValidMonthDate(dateStr, "some_date_param"));

        dateStr = "0233";
        assertFalse(validator.isValidMonthDate(dateStr, "some_date_param"));

        dateStr = "023";
        assertFalse(validator.isValidMonthDate(dateStr, "some_date_param"));

        dateStr = "02335";
        assertFalse(validator.isValidMonthDate(dateStr, "some_date_param"));
    }

    // Virtual Payment Address(VPA)
    @Test
    public void testVPAValidation_ShouldSuccess_WhenValidArguments() {
        String param = "virtual_payment_address";
        String vpa = "someone@bank";
        boolean isRequiredTrue = true;
        boolean isRequiredFalse = false;
        assertTrue(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredTrue));
        assertTrue(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredFalse));
    }

    @Test
    public void testVPAValidation_ShouldReturnTrueFalse_WhenValueInvalid() {
        String param = "virtual_payment_address";
        String vpa = "johndoe.bank";
        boolean isRequiredTrue = true;
        boolean isRequiredFalse = false;
        assertFalse(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredTrue));
        assertFalse(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredFalse));

        vpa = "";
        assertFalse(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredTrue));
        assertTrue(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredFalse));
    }

    @Test(expected = InvalidParamException.class)
    public void testVPAValidation_ShouldThrowException_WhenParamIsNotValid() {
        String param = "";
        String vpa = "johndoe.bank";
        boolean isRequiredTrue = true;
        boolean isRequiredFalse = false;
        assertFalse(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredTrue));
        assertFalse(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredFalse));

        param = null;
        vpa = "";
        assertFalse(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredTrue));
        assertTrue(validator.isValidVirtualPaymentAddress(param, vpa, isRequiredFalse));
    }

    //Neosurf voucher number
    @Test
    public void testNeosurfVoucherNumberValidationSuccess() {
        String voucherNumber = "ABCDE123";
        assertTrue(validator.isValidNeosurfVoucherNumber(voucherNumber, "some_param_name"));

        voucherNumber = "";
        assertTrue(validator.isValidNeosurfVoucherNumber(voucherNumber, "some_param_name"));
    }

    @Test
    public void testNeosurfVoucherNumberValidationFailure() {
        String voucherNumber = "1234*567890";
        assertFalse(validator.isValidNeosurfVoucherNumber(voucherNumber, "some_param_name"));

        voucherNumber = "ABCDE123456";
        assertFalse(validator.isValidNeosurfVoucherNumber(voucherNumber, "some_param_name"));
    }

    //Car Rental extra charges
    @Test
    public void testCarExtraChargesValidationSuccess() {
        String carExtraCharges = "12345";
        assertTrue(validator.isValidCarExtraCharge(carExtraCharges, "some_param_name"));

        carExtraCharges = "";
        assertTrue(validator.isValidCarExtraCharge(carExtraCharges, "some_param_name"));
    }

    @Test
    public void testCarExtraChargesValidationFailure() {
        String carExtraCharges = "1238";
        assertFalse(validator.isValidCarExtraCharge(carExtraCharges, "some_param_name"));

        carExtraCharges = "12311111";
        assertFalse(validator.isValidCarExtraCharge(carExtraCharges, "some_param_name"));
    }

    //Hotel Rental extra charges
    @Test
    public void testHotelExtraChargesValidationSuccess() {
        String hotelExtraCharges = "234567";
        assertTrue(validator.isValidHotelExtraCharge(hotelExtraCharges, "some_param_name"));

        hotelExtraCharges = "";
        assertTrue(validator.isValidHotelExtraCharge(hotelExtraCharges, "some_param_name"));
    }

    @Test
    public void testHotelExtraChargesValidationFailure() {
        String hotelExtraCharges = "123";
        assertFalse(validator.isValidHotelExtraCharge(hotelExtraCharges, "some_param_name"));

        hotelExtraCharges = "234567777";
        assertFalse(validator.isValidHotelExtraCharge(hotelExtraCharges, "some_param_name"));
    }

    @Test
    public void testStringSize_ShouldReturnTrue_WhenValidArguments() {
        String operator = "MOVITEL";
        assertTrue(validator.isValidStringSize(operator, 7, "operator"));
        assertTrue(validator.isValidStringSize(operator, 8, "operator"));
    }

    @Test
    public void testStringSize_ShouldReturnFalse_WhenSizeIsNotValid() {
        String operator = "MOVITEL";
        assertFalse(validator.isValidStringSize(operator, 6, "operator"));
    }

}