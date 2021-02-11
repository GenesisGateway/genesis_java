package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ScaExemptions;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.financial.ScaAttributes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class ScaAttributesTest {
    private static final String LOW_RISK = "low_risk";
    private static final String OTHER_VALUE = "other_value";
    private static final String EMPTY_VALUE = "";
    private static final int TWO_TIMES = 2;
    private ScaAttributes scaAttributes;
    private RequestBuilder requestBuilder;

    @Before
    public void createMpiAttributes() {
        scaAttributes = mock(ScaAttributes.class);
        requestBuilder = mock(RequestBuilder.class);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidSetterValues() {
        when(scaAttributes.setScaExemption(isA(String.class))).thenReturn(scaAttributes);
        when(scaAttributes.setScaVisaMerchantId(isA(String.class))).thenReturn(scaAttributes);

        assertEquals(scaAttributes, scaAttributes.setScaExemption(ScaExemptions.EXEMPTION_TRUSTED_MERCHANT));
        assertEquals(scaAttributes, scaAttributes.setScaVisaMerchantId("00000000"));

        verify(scaAttributes).setScaExemption(ScaExemptions.EXEMPTION_TRUSTED_MERCHANT);
        verify(scaAttributes).setScaVisaMerchantId("00000000");

        verifyNoMoreInteractions(scaAttributes);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidExemption() {
        doReturn(LOW_RISK).when(scaAttributes).getScaExemption();
        doReturn(requestBuilder).when(scaAttributes).getScaAttrRequestBuilder();
        doCallRealMethod().when(scaAttributes).buildScaParams();

        assertEquals(LOW_RISK, scaAttributes.getScaExemption());
        assertEquals(requestBuilder, scaAttributes.buildScaParams());

        verify(scaAttributes).buildScaParams();
        verify(scaAttributes, times(TWO_TIMES)).getScaExemption();
        verify(scaAttributes).getScaAttrRequestBuilder();
        verifyNoMoreInteractions(scaAttributes);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenNullExemption() {
        doReturn(null).when(scaAttributes).getScaExemption();
        doReturn(requestBuilder).when(scaAttributes).getScaAttrRequestBuilder();
        doCallRealMethod().when(scaAttributes).buildScaParams();

        assertNull(scaAttributes.getScaExemption());
        assertEquals(requestBuilder, scaAttributes.buildScaParams());

        verify(scaAttributes).buildScaParams();
        verify(scaAttributes, times(TWO_TIMES)).getScaExemption();
        verify(scaAttributes).getScaAttrRequestBuilder();
        verifyNoMoreInteractions(scaAttributes);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenEmptyExemption() {
        doReturn(EMPTY_VALUE).when(scaAttributes).getScaExemption();
        doReturn(requestBuilder).when(scaAttributes).getScaAttrRequestBuilder();
        doCallRealMethod().when(scaAttributes).buildScaParams();

        assertEquals(EMPTY_VALUE, scaAttributes.getScaExemption());
        assertEquals(requestBuilder, scaAttributes.buildScaParams());

        verify(scaAttributes).buildScaParams();
        verify(scaAttributes, times(TWO_TIMES)).getScaExemption();
        verify(scaAttributes).getScaAttrRequestBuilder();
        verifyNoMoreInteractions(scaAttributes);
    }

    @Test
    public void testRequest_ShouldThrowException_WhenInvalidExemption() {
        doReturn(OTHER_VALUE).when(scaAttributes).getScaExemption();
        doCallRealMethod().when(scaAttributes).buildScaParams();

        assertEquals(OTHER_VALUE, scaAttributes.getScaExemption());
        assertThrows(InvalidParamException.class, () -> scaAttributes.buildScaParams());

        verify(scaAttributes).buildScaParams();
        verify(scaAttributes, never()).getScaAttrRequestBuilder();
        verify(scaAttributes, times(TWO_TIMES)).getScaExemption();
        verifyNoMoreInteractions(scaAttributes);
    }
}