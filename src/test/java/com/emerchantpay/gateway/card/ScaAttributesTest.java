package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.constants.ScaExemptions;
import com.emerchantpay.gateway.api.interfaces.financial.ScaAttributes;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class ScaAttributesTest {
    private ScaAttributes scaAttributes;

    @Before
    public void createMpiAttributes() {
        scaAttributes = mock(ScaAttributes.class);
    }

    @Test
    public void buildScaAttributes() {
        when(scaAttributes.setScaExemption(isA(String.class))).thenReturn(scaAttributes);
        when(scaAttributes.setScaVisaMerchantId(isA(String.class))).thenReturn(scaAttributes);

        assertEquals(scaAttributes.setScaExemption(ScaExemptions.EXEMPTION_TRUSTED_MERCHANT), scaAttributes);
        assertEquals(scaAttributes.setScaVisaMerchantId("00000000"), scaAttributes);

        verify(scaAttributes).setScaExemption(ScaExemptions.EXEMPTION_TRUSTED_MERCHANT);
        verify(scaAttributes).setScaVisaMerchantId("00000000");

        verifyNoMoreInteractions(scaAttributes);
    }

    @Test
    public void buildScaMissingAttributes() {
        when(scaAttributes.setScaExemption(isA(String.class))).thenReturn(scaAttributes);
        when(scaAttributes.getScaConditionalRequiredFields()).thenReturn(new HashMap<String, String>() {
            {
                put(RequiredParameters.scaVisaMerchantId, null);
            }
        });

        assertEquals(scaAttributes.setScaExemption(ScaExemptions.EXEMPTION_LOW_VALUE), scaAttributes);
        assertTrue(!scaAttributes.getScaConditionalRequiredFields().isEmpty());

        verify(scaAttributes).setScaExemption(ScaExemptions.EXEMPTION_LOW_VALUE);
    }
}
