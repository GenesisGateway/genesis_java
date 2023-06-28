package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.financial.RecurringCategoryAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecurringCategoryAttributesTest {

    private static final String ANY_STRING = "ANY_STRING";
    private RequestBuilder requestBuilder;
    private RecurringCategoryAttributes recurringCategory;
    private GenesisValidator validator;
    private HashMap<String, String> paramsMap;

    public void prepareObject() {
        when(recurringCategory.getRecurringCategoryAttrRequestBuilder()).thenReturn(requestBuilder);
        when(recurringCategory.getRecurringCategoryAttrParamsMap()).thenReturn(paramsMap);
    }

    @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        recurringCategory = spy(RecurringCategoryAttributes.class);
        validator = spy(GenesisValidator.class);
        paramsMap = new HashMap<>();
    }

    @Test
    public void testBuildRecurringCatParams_ShouldSuccess_WhenValidArguments() {
        prepareObject();
        when(recurringCategory.setRecurringCategory("subscription")).thenReturn(recurringCategory);
        when(recurringCategory.setRecurringCategory("standing_order")).thenReturn(recurringCategory);

        assertEquals("standing_order", recurringCategory.getRecurringCategory());
        assertEquals(requestBuilder, recurringCategory.buildRecurringCategoryParams());
    }

    @Test(expected = InvalidParamException.class)
    public void testRecurrencyCategory_ThrowException_WhenInvalidRecurringCategory() {
        prepareObject();
        when(recurringCategory.setRecurringCategory(ANY_STRING)).thenReturn(recurringCategory);
    }
}
