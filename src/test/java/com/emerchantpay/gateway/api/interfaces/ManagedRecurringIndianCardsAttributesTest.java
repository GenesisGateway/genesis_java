package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.financial.ManagedRecurringIndianCardsAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ManagedRecurringIndianCardsAttributesTest {

    private final String MANAGED_RECURRING = "managed_recurring";
    private final String MANAGED = "managed";
    private final String MODE_AUTOMATIC = "automatic";
    private final String PAY_MODIFICATION = "modification";
    private final String AMOUNT_FIXED = "fixed";
    private final String FREQUENCY_TWEEKLY = "twice_weekly";
    private final String REGISTR_REF_NUMBER = "30";
    private final Double MAX_AMOUNT_555 = 555.0;
    private final Integer MAX_COUNT_6 = 6;
    private final Boolean VALIDATED_TRUE = true;
    private final String ANY_STRING = "ANY_STRING";
    private RequestBuilder requestBuilder;
    private ManagedRecurringIndianCardsAttributes managedRecurringAttributes;
    private GenesisValidator validator;
    private HashMap<String, String> paramsMap;

    private void setStubs() {
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);
        when(requestBuilder.toXML()).thenReturn(MANAGED_RECURRING);
        when(managedRecurringAttributes.getRequiredIndianCardParams()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getCurrency()).thenReturn("EUR");
        when(managedRecurringAttributes.getValidator()).thenReturn(new GenesisValidator());
    }

    @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        managedRecurringAttributes = spy(ManagedRecurringIndianCardsAttributes.class);
        validator = spy(GenesisValidator.class);
        paramsMap = new HashMap<>();
    }

    @Test
    public void testManagedRecurring_ShouldSuccess_WhenValidParams() {
        HashMap<String,String> paramsMap = new HashMap<>();
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);
        when(managedRecurringAttributes.getValidator()).thenReturn(validator);
        when(managedRecurringAttributes.getCurrency()).thenReturn("EUR");

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setMode(MODE_AUTOMATIC));
        assertEquals(MODE_AUTOMATIC, managedRecurringAttributes.getMode());

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setPaymentType(PAY_MODIFICATION));
        assertEquals(PAY_MODIFICATION, managedRecurringAttributes.getPaymentType());

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setAmountType(AMOUNT_FIXED));
        assertEquals(AMOUNT_FIXED, managedRecurringAttributes.getAmountType());

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setFrequency(FREQUENCY_TWEEKLY));
        assertEquals(FREQUENCY_TWEEKLY, managedRecurringAttributes.getFrequency());

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setRegistrationReferenceNumber(REGISTR_REF_NUMBER));
        assertEquals(REGISTR_REF_NUMBER, managedRecurringAttributes.getRegistrationReferenceNumber());

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setMaxAmount(BigDecimal.valueOf(MAX_AMOUNT_555)));
        assertEquals(BigDecimal.valueOf(MAX_AMOUNT_555), managedRecurringAttributes.getMaxAmount());

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setMaxCount(MAX_COUNT_6));
        assertEquals(new Integer(MAX_COUNT_6), managedRecurringAttributes.getMaxCount());

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setValidated(VALIDATED_TRUE));
        assertEquals(new Boolean(VALIDATED_TRUE), managedRecurringAttributes.getValidated());
    }

    @Test
    public void testManagedRecurring_IndianCards_ShouldSuccess_WhenModeManual_ValidParams() {
        paramsMap.put("mode", "manual");
        paramsMap.put("payment_type", PAY_MODIFICATION);
        paramsMap.put("amount_type", AMOUNT_FIXED);
        paramsMap.put("frequency", FREQUENCY_TWEEKLY);
        paramsMap.put("registration_reference_number", REGISTR_REF_NUMBER);
        paramsMap.put("max_amount", "200");
        paramsMap.put("max_count", "99");
        paramsMap.put("validated", "true");

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);

        verify(managedRecurringAttributes).validateManagedRecurring(MANAGED);
    }

    @Test
    public void testManagedRecurring_IndianCards_ShouldSuccess_When_Setter_Getter_Valid() {
        paramsMap.put("mode", "manual");

        setStubs();
        managedRecurringAttributes.setPaymentType(PAY_MODIFICATION);
        managedRecurringAttributes.setAmountType(AMOUNT_FIXED);
        managedRecurringAttributes.setFrequency(FREQUENCY_TWEEKLY);
        managedRecurringAttributes.setRegistrationReferenceNumber(REGISTR_REF_NUMBER);
        managedRecurringAttributes.setMaxAmount(BigDecimal.valueOf(MAX_AMOUNT_555));
        managedRecurringAttributes.setMaxCount(MAX_COUNT_6);
        managedRecurringAttributes.setValidated(VALIDATED_TRUE);

        assertEquals(PAY_MODIFICATION, managedRecurringAttributes.getPaymentType());
        assertEquals(AMOUNT_FIXED, managedRecurringAttributes.getAmountType());
        assertEquals(FREQUENCY_TWEEKLY, managedRecurringAttributes.getFrequency());
        assertEquals(REGISTR_REF_NUMBER, managedRecurringAttributes.getRegistrationReferenceNumber());
        assertEquals(BigDecimal.valueOf(MAX_AMOUNT_555), managedRecurringAttributes.getMaxAmount());
        assertEquals(new Integer(MAX_COUNT_6), managedRecurringAttributes.getMaxCount());
        assertEquals(new Boolean(VALIDATED_TRUE), managedRecurringAttributes.getValidated());

        verify(managedRecurringAttributes).setPaymentType(PAY_MODIFICATION);
        verify(managedRecurringAttributes).setAmountType(AMOUNT_FIXED);
        verify(managedRecurringAttributes).setFrequency(FREQUENCY_TWEEKLY);
        verify(managedRecurringAttributes).setRegistrationReferenceNumber(REGISTR_REF_NUMBER);
        verify(managedRecurringAttributes).setMaxAmount(BigDecimal.valueOf(MAX_AMOUNT_555));
        verify(managedRecurringAttributes).setMaxCount(MAX_COUNT_6);
        verify(managedRecurringAttributes).setValidated(VALIDATED_TRUE);
    }

    @Test(expected = RequiredParamsException.class)
    public void testManagedRecurring_IndianCards_ShouldThrowException_When_Missed_PaymentType() {
        paramsMap.put("mode", "manual");
        paramsMap.put("payment_type", null);

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);
    }

    @Test(expected = RequiredParamsException.class)
    public void testManagedRecurring_IndianCards_ShouldThrowException_When_Missed_AmountType() {
        paramsMap.put("mode", "manual");
        paramsMap.put("amount_type", null);

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);
    }

    @Test(expected = RequiredParamsException.class)
    public void testManagedRecurring_IndianCards_ShouldThrowException_When_Missed_Frequency() {
        paramsMap.put("mode", "manual");
        paramsMap.put("frequency", null);

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);
    }

    @Test(expected = RequiredParamsException.class)
    public void testManagedRecurring_IndianCards_ShouldThrowException_When_Missed_RegRefNumber() {
        paramsMap.put("mode", "manual");
        paramsMap.put("registration_reference_number", null);

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);
    }

    @Test(expected = RequiredParamsException.class)
    public void testManagedRecurring_IndianCards_ShouldThrowException_When_Missed_MaxAmount() {
        paramsMap.put("mode", "manual");
        paramsMap.put("max_amount", null);

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);
    }

    @Test(expected = RequiredParamsException.class)
    public void testManagedRecurring_IndianCards_ShouldThrowException_When_Missed_MaxCount() {
        paramsMap.put("mode", "manual");
        paramsMap.put("max_count", null);

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);
    }

    @Test(expected = InvalidParamException.class)
    public void testManagedRecurring_ShouldThrowException_WhenPaymentTypeIsInvalid() {
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setPaymentType(ANY_STRING));
    }

    @Test(expected = InvalidParamException.class)
    public void testManagedRecurring_ShouldThrowException_WhenAmountTypeIsInvalid() {
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setAmountType(ANY_STRING));
    }

    @Test(expected = InvalidParamException.class)
    public void testManagedRecurring_ShouldThrowException_WhenFrequencyIsInvalid() {
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setFrequency(ANY_STRING));
    }

    @Test
    public void testConvertedAmount_ShouldSuccess_WhenValidParams() {
        when(managedRecurringAttributes.getCurrency()).thenReturn("EUR");
        when(managedRecurringAttributes.getValidator()).thenReturn(validator);

        assertEquals(BigDecimal.valueOf((int)(MAX_AMOUNT_555*100)), managedRecurringAttributes
                .getConvertedAmount(BigDecimal.valueOf(MAX_AMOUNT_555), "amount"));
    }

    @Test (expected = InvalidParamException.class)
    public void testConvertedAmount_ThrowException_WhenInvalidAmount() {
        when(managedRecurringAttributes.getCurrency()).thenReturn("EUR");
        when(managedRecurringAttributes.getValidator()).thenReturn(validator);

        assertEquals(BigDecimal.valueOf((int)(MAX_AMOUNT_555*100)), managedRecurringAttributes
                .getConvertedAmount(BigDecimal.valueOf(-2.50), "amount"));
    }

    @Test
    public void testExpToAmount_ShouldSuccess_WhenValidParams() {
        when(managedRecurringAttributes.getCurrency()).thenReturn("EUR");
        when(managedRecurringAttributes.getValidator()).thenReturn(validator);

        assertEquals(BigDecimal.valueOf(MAX_AMOUNT_555), managedRecurringAttributes
                .getExpToAmount(BigDecimal.valueOf(MAX_AMOUNT_555*100)));
    }
}
