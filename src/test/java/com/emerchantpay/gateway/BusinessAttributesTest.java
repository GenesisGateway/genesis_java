package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class BusinessAttributesTest {

    private BusinessParamsAttributes businessAttributes;

    @Before
    public void createBusinessAttributes() {
        businessAttributes = mock(BusinessParamsAttributes.class);
    }

    @Test
    public void buildBusinessAttributes() {

        when(businessAttributes.setBusinessEventStartDate(isA(String.class))).thenReturn(businessAttributes);
        when(businessAttributes.setBusinessEventEndDate(isA(String.class))).thenReturn(businessAttributes);
        when(businessAttributes.setBusinessEventId(isA(String.class))).thenReturn(businessAttributes);
        when(businessAttributes.setBusinessEventOrganizerId(isA(String.class))).thenReturn(businessAttributes);
        when(businessAttributes.setBusinessDateOfOrder(isA(String.class))).thenReturn(businessAttributes);
        when(businessAttributes.setBusinessDeliveryDate(isA(String.class))).thenReturn(businessAttributes);
        when(businessAttributes.setBusinessNameOfSupplier(isA(String.class))).thenReturn(businessAttributes);
        when(businessAttributes.setBusinessPaymentType(isA(String.class))).thenReturn(businessAttributes);

        assertEquals(businessAttributes, businessAttributes.setBusinessEventStartDate("10-10-2020"));
        assertEquals(businessAttributes, businessAttributes.setBusinessEventEndDate("17-10-2020"));
        assertEquals(businessAttributes, businessAttributes.setBusinessEventId("Event1234"));
        assertEquals(businessAttributes, businessAttributes.setBusinessEventOrganizerId("Organizer1234"));
        assertEquals(businessAttributes, businessAttributes.setBusinessDateOfOrder("10-10-2020"));
        assertEquals(businessAttributes, businessAttributes.setBusinessDeliveryDate("17-10-2020"));
        assertEquals(businessAttributes, businessAttributes.setBusinessNameOfSupplier("Test supplier"));
        assertEquals(businessAttributes, businessAttributes.setBusinessPaymentType(BusinessParamsAttributes.PAYMENT_METHOD_DEPOSIT));

        verify(businessAttributes).setBusinessEventStartDate("10-10-2020");
        verify(businessAttributes).setBusinessEventEndDate("17-10-2020");
        verify(businessAttributes).setBusinessEventId("Event1234");
        verify(businessAttributes).setBusinessEventOrganizerId("Organizer1234");
        verify(businessAttributes).setBusinessDateOfOrder("10-10-2020");
        verify(businessAttributes).setBusinessDeliveryDate("17-10-2020");
        verify(businessAttributes).setBusinessNameOfSupplier("Test supplier");
        verify(businessAttributes).setBusinessPaymentType(BusinessParamsAttributes.PAYMENT_METHOD_DEPOSIT);

        verifyNoMoreInteractions(businessAttributes);
    }

    @Test(expected = InvalidParamException.class)
    public void businessPaymentTypeError(){
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setBusinessPaymentType("invalid_type");

        saleRequest.buildBusinessParams();
    }
}
