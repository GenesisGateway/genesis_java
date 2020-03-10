package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.constants.MpiProtocolVersions;
import com.emerchantpay.gateway.api.interfaces.financial.MpiAttributes;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MpiAttributesTest {

    private MpiAttributes mpiAttributes;

    @Before
    public void createMpiAttributes() {
        mpiAttributes = mock(MpiAttributes.class);
    }

    @Test
    public void buildMpi3DSv1Attributes() {
        when(mpiAttributes.setMpiCavv(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiEci(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiXid(isA(String.class))).thenReturn(mpiAttributes);

        assertEquals(mpiAttributes.setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA="), mpiAttributes);
        assertEquals(mpiAttributes.setMpiEci("05"), mpiAttributes);
        assertEquals(mpiAttributes.setMpiXid("Fifth 0pv62FIrT5qQODB7DCewKgEBAQI="), mpiAttributes);
        assertFalse(mpiAttributes.is3DSv2());

        verify(mpiAttributes).setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA=");
        verify(mpiAttributes).setMpiEci("05");
        verify(mpiAttributes).setMpiXid("Fifth 0pv62FIrT5qQODB7DCewKgEBAQI=");
    }

    @Test
    public void buildMpi3DSv2Attributes() {
        when(mpiAttributes.setMpiCavv(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiEci(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiProtocolVersion(MpiProtocolVersions.PROTOCOL_VERSION_2)).thenReturn(mpiAttributes);
        when(mpiAttributes.is3DSv2()).thenReturn(true);
        when(mpiAttributes.setMpiDirectoryServerId(isA(String.class))).thenReturn(mpiAttributes);

        assertEquals(mpiAttributes.setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA="), mpiAttributes);
        assertEquals(mpiAttributes.setMpiEci("05"), mpiAttributes);
        assertEquals(mpiAttributes.setMpiProtocolVersion(MpiProtocolVersions.PROTOCOL_VERSION_2), mpiAttributes);
        assertEquals(mpiAttributes.setMpiDirectoryServerId("f38e6948-5388-41a6-bca4-b49723c19437"), mpiAttributes);
        assertTrue(mpiAttributes.is3DSv2());

        verify(mpiAttributes).setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA=");
        verify(mpiAttributes).setMpiEci("05");
        verify(mpiAttributes).setMpiProtocolVersion(MpiProtocolVersions.PROTOCOL_VERSION_2);
        verify(mpiAttributes).setMpiDirectoryServerId("f38e6948-5388-41a6-bca4-b49723c19437");
    }

    @Test
    public void buildMpi3DSv2MissingAttributes() {
        when(mpiAttributes.setMpiCavv(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiEci(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiProtocolVersion(MpiProtocolVersions.PROTOCOL_VERSION_2)).thenReturn(mpiAttributes);
        when(mpiAttributes.is3DSv2()).thenReturn(true);
        when(mpiAttributes.getMpiConditionalRequiredFields()).thenReturn(new HashMap<String, String>() {
            {
                put(RequiredParameters.mpiDirectoryServerId, null);
            }
        });

        assertEquals(mpiAttributes.setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA="), mpiAttributes);
        assertEquals(mpiAttributes.setMpiEci("05"), mpiAttributes);
        assertEquals(mpiAttributes.setMpiProtocolVersion(MpiProtocolVersions.PROTOCOL_VERSION_2), mpiAttributes);
        assertTrue(mpiAttributes.is3DSv2());
        assertTrue(!mpiAttributes.getMpiConditionalRequiredFields().isEmpty());

        verify(mpiAttributes).setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA=");
        verify(mpiAttributes).setMpiEci("05");
        verify(mpiAttributes).setMpiProtocolVersion(MpiProtocolVersions.PROTOCOL_VERSION_2);
    }
}
