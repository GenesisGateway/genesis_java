package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.financial.ManagedRecurringAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParametersValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ManagedRecurringAttributesTest {

    private final String MANAGED_RECURRING = "managed_recurring";
    private final String MANAGED = "managed";
    private final String MODE_AUTOMATIC = "automatic";
    private final String INT_DAYS = "days";
    private final String INT_MONTHS = "months";
    private final String FIRST_DATE_VALUE = "2023-05-30";
    private final int TIME_OF_DAY = 10;
    private final int PERIOD = 30;
    private final Double RECURRING_AMOUNT = 555.0;
    private final String ANY_STRING = "ANY_STRING";
    private RequestBuilder requestBuilder;
    private ManagedRecurringAttributes managedRecurringAttributes;
    private HashMap<String, String> paramsMap;

    private void setStubs() {
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);
        when(requestBuilder.toXML()).thenReturn(MANAGED_RECURRING);
        when(managedRecurringAttributes.getRequiredManagedRecurringParams()).thenReturn(paramsMap);
        when(managedRecurringAttributes.buildManagedRecurring(false, MANAGED_RECURRING))
                .thenReturn(requestBuilder);
        when(managedRecurringAttributes.getCurrency()).thenReturn("EUR");
        when(managedRecurringAttributes.getValidator()).thenReturn(new GenesisValidator());
    }

    private void setStubsWithValidator() {
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);
        when(managedRecurringAttributes.getValidator()).thenReturn(new RequiredParametersValidator(paramsMap));
        when(managedRecurringAttributes.getCurrency()).thenReturn("EUR");
    }

     @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        managedRecurringAttributes = spy(ManagedRecurringAttributes.class);
        paramsMap = new HashMap<>();
    }

    @Test
    public void testManagedRecurring_ShouldSuccess_WhenValidParams() {
        setStubsWithValidator();

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setMode(MODE_AUTOMATIC));
        assertEquals(MODE_AUTOMATIC, managedRecurringAttributes.getMode());
        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setInterval(INT_MONTHS));
        assertEquals(INT_MONTHS, managedRecurringAttributes.getInterval());
        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setFirstDate(FIRST_DATE_VALUE));
        assertEquals(FIRST_DATE_VALUE, managedRecurringAttributes.getFirstDate());
        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setTimeOfDay(TIME_OF_DAY));
        assertEquals(new Integer(TIME_OF_DAY), managedRecurringAttributes.getTimeOfDay());
        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setPeriod(PERIOD));
        assertEquals(new Integer(PERIOD), managedRecurringAttributes.getPeriod());
        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setManagedRecurringAmount(BigDecimal.valueOf(RECURRING_AMOUNT)));
        assertEquals(BigDecimal.valueOf(RECURRING_AMOUNT), managedRecurringAttributes.getManagedRecurringAmount());

    }

    @Test(expected = InvalidParamException.class)
    public void testManagedRecurring_ShouldThrowException_WhenModeIsInvalid() {
        when(managedRecurringAttributes.getManagedRecurringAttrParamsMap()).thenReturn(paramsMap);
        when(managedRecurringAttributes.getManagedRecurringAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(managedRecurringAttributes, managedRecurringAttributes.setMode(ANY_STRING));
    }

    @Test
    public void testManagedRecurringRequestBuilder_ShouldSuccess_WhenModeAutomatic_ValidParams() {
        paramsMap.put("mode", MODE_AUTOMATIC);
        paramsMap.put("period", "30");

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);

        assertEquals(requestBuilder, managedRecurringAttributes.buildManagedRecurring());
    }

    @Test(expected = RequiredParamsException.class)
    public void testManagedRecurringRequestBuilder_ShouldThrowException_WhenPeriod_IsMissing() {
        paramsMap.put("mode", MODE_AUTOMATIC);
        paramsMap.put("period", "");

        setStubs();
        managedRecurringAttributes.validateManagedRecurring(MANAGED);

        assertEquals(requestBuilder, managedRecurringAttributes.buildManagedRecurring());
    }

    @Test
    public void testManagedRecurringRequestBuilder_ShouldSuccess_WhenInterval_isValid() {
        paramsMap.put("mode", MODE_AUTOMATIC);
        setStubs();

        managedRecurringAttributes.setInterval(INT_DAYS);
        assertEquals(INT_DAYS, managedRecurringAttributes.getInterval());

        managedRecurringAttributes.setInterval(INT_MONTHS);
        assertEquals(INT_MONTHS, managedRecurringAttributes.getInterval());
    }

    @Test(expected = InvalidParamException.class)
    public void testManagedRecurringRequestBuilder_ShouldThrowException_WhenInterval_isInvalid() {
        paramsMap.put("mode", MODE_AUTOMATIC);
        setStubs();

        managedRecurringAttributes.setInterval(ANY_STRING);
        assertEquals(ANY_STRING, managedRecurringAttributes.getInterval());
    }

    @Test
    public void testManagedRecurringRequestBuilder_ShouldSuccess_WhenFirstDate_Valid() {
        setStubsWithValidator();

        managedRecurringAttributes.setFirstDate("2023-07-20");
    }

    @Test(expected = InvalidParamException.class)
    public void testManagedRecurringRequestBuilder_ShouldThrowException_WhenFirstDate_Invalid() {
        setStubsWithValidator();

        managedRecurringAttributes.setFirstDate("2234-34-23");
    }

}
