package com.emerchantpay.gateway.card;

import com.emerchantpay.gateway.api.constants.MpiProtocolSubVersions;
import com.emerchantpay.gateway.api.constants.MpiProtocolVersions;
import com.emerchantpay.gateway.api.interfaces.financial.MpiAttributes;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MpiAttributesTest {

    private MpiAttributes mpiAttributes;
    private Random random;

    @Before
    public void createMpiAttributes() {
        mpiAttributes = mock(MpiAttributes.class);
        random = new Random();
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
        String randomProtocolVersion = MpiProtocolVersions.getAll()[(random.nextInt(MpiProtocolVersions.getAll().length))];
        String randomProtocolSubVersion = MpiProtocolSubVersions.getAll()[(random.nextInt(MpiProtocolSubVersions.getAll().length))];
        when(mpiAttributes.setMpiCavv(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiEci(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiProtocolVersion(randomProtocolVersion)).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiProtocolSubVersion(randomProtocolSubVersion)).thenReturn(mpiAttributes);
        when(mpiAttributes.is3DSv2()).thenReturn(true);
        when(mpiAttributes.setMpiDirectoryServerId(isA(String.class))).thenReturn(mpiAttributes);

        assertEquals(mpiAttributes.setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA="), mpiAttributes);
        assertEquals(mpiAttributes.setMpiEci("05"), mpiAttributes);
        assertEquals(mpiAttributes.setMpiProtocolVersion(randomProtocolVersion), mpiAttributes);
        assertEquals(mpiAttributes.setMpiProtocolSubVersion(randomProtocolSubVersion), mpiAttributes);
        assertEquals(mpiAttributes.setMpiDirectoryServerId("f38e6948-5388-41a6-bca4-b49723c19437"), mpiAttributes);
        assertTrue(mpiAttributes.is3DSv2());

        verify(mpiAttributes).setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA=");
        verify(mpiAttributes).setMpiEci("05");
        verify(mpiAttributes).setMpiProtocolVersion(randomProtocolVersion);
        verify(mpiAttributes).setMpiProtocolSubVersion(randomProtocolSubVersion);
        verify(mpiAttributes).setMpiDirectoryServerId("f38e6948-5388-41a6-bca4-b49723c19437");
    }

    @Test
    public void buildMpi3DSv2MissingAttributes() {
        String randomProtocolVersion = MpiProtocolVersions.getAll()[(random.nextInt(MpiProtocolVersions.getAll().length))];
        when(mpiAttributes.setMpiCavv(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiEci(isA(String.class))).thenReturn(mpiAttributes);
        when(mpiAttributes.setMpiProtocolVersion(randomProtocolVersion)).thenReturn(mpiAttributes);
        when(mpiAttributes.is3DSv2()).thenReturn(true);

        assertEquals(mpiAttributes.setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA="), mpiAttributes);
        assertEquals(mpiAttributes.setMpiEci("05"), mpiAttributes);
        assertEquals(mpiAttributes.setMpiProtocolVersion(randomProtocolVersion), mpiAttributes);
        assertTrue(mpiAttributes.is3DSv2());

        verify(mpiAttributes).setMpiCavv("AAACA1BHADYJkIASQkcAAAAAAAA=");
        verify(mpiAttributes).setMpiEci("05");
        verify(mpiAttributes).setMpiProtocolVersion(randomProtocolVersion);
    }
}
