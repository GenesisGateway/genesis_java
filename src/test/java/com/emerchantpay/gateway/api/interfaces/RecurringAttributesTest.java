package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.financial.RecurringTypeAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecurringAttributesTest {

    private static final String ANY_STRING = "ANY_STRING";
    private RequestBuilder requestBuilder;
    private RecurringTypeAttributes recurringAttributes;
    private GenesisValidator validator;

    @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        recurringAttributes = spy(RecurringTypeAttributes.class);
        validator = spy(GenesisValidator.class);
    }

    public void prepareObject() {
        when(recurringAttributes.getRecurringTypeAttrRequestBuilder()).thenReturn(requestBuilder);
    }

    @Test
    public void testBuildRecurringParams_ShouldSuccess_WhenValidArguments() {
        prepareObject();
        when(recurringAttributes.setRecurringType("initial")).thenReturn(recurringAttributes);

        assertEquals(requestBuilder, recurringAttributes.buildRecurringAttrParams());
    }

    @Test(expected = InvalidParamException.class)
    public void testBuildRecurringParams_ThrowException_WhenInvalidRecurringType() {
        prepareObject();
        when(recurringAttributes.setRecurringType(ANY_STRING)).thenReturn(recurringAttributes);

        assertEquals(requestBuilder, recurringAttributes.buildRecurringAttrParams());
    }

    @Test
    public void testGetRecurringAtt_ShouldSuccess_WhenValidArguments() {
        prepareObject();

        assertEquals("initial", recurringAttributes.getAllowedRecurringTypes().get(0));
        assertEquals("managed", recurringAttributes.getAllowedRecurringTypes().get(1));
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testGetRecurringAtt_ThrowException_WhenInvalidRecurringTypeIndex() {
        prepareObject();

        assertEquals(ANY_STRING, recurringAttributes.getAllowedRecurringTypes().get(2));
    }

}
