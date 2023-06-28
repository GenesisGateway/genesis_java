package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.customerinfo.SenderAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class SenderAttributesTest {

    private static final String VALID_US_DATE = "1990-03-15";
    private static final String INVALID_US_DATE = "15-03-1990";
    private static final String VALID_ISO_COUNTRY_FORMAT = "ISL";
    private static final String INVALID_ISO_COUNTRY_FORMAT = "ABCD";
    private static final String ANY_STRING = "ANY_STRING";

    private List<String> idTypes = Arrays.asList(
            "PASSPORT", "NATIONAL_ID", "DRIVING_LICENSE", "SOCIAL_SECURITY", "TAX_ID", "SENIOR_CITIZEN_ID", "BIRTH_CERTIFICATE",
            "VILLAGE_ELDER_ID", "RESIDENT_CARD", "ALIEN_REGISTRATION", "PAN_CARD", "VOTERS_ID", "HEALTH_CARD", "EMPLOYER_ID", "OTHER");

    private SenderAttributes senderAttributes;
    private RequestBuilder requestBuilder;
    private GenesisValidator validator;

    @Before
    public void createSenderAttributes() {
        requestBuilder = mock(RequestBuilder.class);
        senderAttributes = spy(SenderAttributes.class);
        validator = spy(GenesisValidator.class);
    }

    private void prepareObject() {
        when(senderAttributes.getSenderAttrRequestBuilder()).thenReturn(requestBuilder);
        when(senderAttributes.getValidator()).thenReturn(validator);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidSetterValues() {
        prepareObject();

        assertEquals(senderAttributes, senderAttributes.setSenderDateOfBirth(VALID_US_DATE));
        assertEquals(senderAttributes, senderAttributes.setSenderLastName("Doe"));
        assertEquals(senderAttributes, senderAttributes.setSenderFirstName("John"));
        assertEquals(senderAttributes, senderAttributes.setSenderCountryIsoCode(VALID_ISO_COUNTRY_FORMAT));
        assertEquals(senderAttributes, senderAttributes.setSenderIdNumber("12345678"));
        assertEquals(senderAttributes, senderAttributes.setSenderNationalityCountryIsoCode("ISL"));
        assertEquals(senderAttributes, senderAttributes.setSenderAddress("First Avenue"));
        assertEquals(senderAttributes, senderAttributes.setSenderOccupation("Accountant "));
        assertEquals(senderAttributes, senderAttributes.setSenderBeneficiaryRelationship("N"));
        assertEquals(senderAttributes, senderAttributes.setSenderPostalCode("M4B1B3"));
        assertEquals(senderAttributes, senderAttributes.setSenderCity("Lindau"));
        assertEquals(senderAttributes, senderAttributes.setSenderMsisdn("123"));
        assertEquals(senderAttributes, senderAttributes.setSenderGender("M"));
        assertEquals(senderAttributes, senderAttributes.setSenderIdType("PASSPORT"));
        assertEquals(senderAttributes, senderAttributes.setSenderProvinceState("EL"));
        assertEquals(senderAttributes, senderAttributes.setSenderSourceOfFunds("Savings"));
        assertEquals(senderAttributes, senderAttributes.setSenderCountryOfBirthIsoCode("ISL"));

        verify(senderAttributes).setSenderDateOfBirth(VALID_US_DATE);
        verify(senderAttributes).setSenderLastName("Doe");
        verify(senderAttributes).setSenderFirstName("John");
        verify(senderAttributes).setSenderCountryIsoCode(VALID_ISO_COUNTRY_FORMAT);
        verify(senderAttributes).setSenderIdNumber("12345678");
        verify(senderAttributes).setSenderNationalityCountryIsoCode("ISL");
        verify(senderAttributes).setSenderAddress("First Avenue");
        verify(senderAttributes).setSenderOccupation("Accountant ");
        verify(senderAttributes).setSenderBeneficiaryRelationship("N");
        verify(senderAttributes).setSenderPostalCode("M4B1B3");
        verify(senderAttributes).setSenderCity("Lindau");
        verify(senderAttributes).setSenderMsisdn("123");
        verify(senderAttributes).setSenderGender("M");
        verify(senderAttributes).setSenderIdType("PASSPORT");
        verify(senderAttributes).setSenderProvinceState("EL");
        verify(senderAttributes).setSenderSourceOfFunds("Savings");
        verify(senderAttributes).setSenderCountryOfBirthIsoCode("ISL");
        verify(senderAttributes, times(1)).setSenderIdType("PASSPORT");
        verify(senderAttributes, times(1)).isAllowedListOption("PASSPORT","sender_id_type", idTypes);
        verify(senderAttributes, times(1)).setSenderDateOfBirth(VALID_US_DATE);
        verify(senderAttributes, times(17)).getSenderAttrParamsMap();
        verify(senderAttributes, times(17)).getSenderAttrRequestBuilder();
        verify(senderAttributes, times(3)).getValidator();
        verifyNoMoreInteractions(senderAttributes);
    }

    @Test
    public void testRequest_ShouldThrowException_WhenSenderDateOfBirthWrongFormat() {
        prepareObject();

        assertThrows(InvalidParamException.class, () -> senderAttributes.setSenderDateOfBirth(INVALID_US_DATE));

        verify(senderAttributes).setSenderDateOfBirth(INVALID_US_DATE);
        verify(senderAttributes, never()).getSenderAttrRequestBuilder();
        verify(senderAttributes, times(1)).setSenderDateOfBirth(INVALID_US_DATE);
        verify(senderAttributes, times(1)).getValidator();
        verifyNoMoreInteractions(senderAttributes);
    }

    @Test
    public void testRequest_ShouldThrowException_WhenCountryIsoCodeWrong() {
        prepareObject();

        assertThrows(InvalidParamException.class, () -> senderAttributes.setSenderCountryIsoCode(INVALID_ISO_COUNTRY_FORMAT));

        verify(senderAttributes).setSenderCountryIsoCode(INVALID_ISO_COUNTRY_FORMAT);
        verify(senderAttributes, never()).getSenderAttrRequestBuilder();
        verify(senderAttributes, times(1)).setSenderCountryIsoCode(INVALID_ISO_COUNTRY_FORMAT);
        verify(senderAttributes, times(1)).getValidator();
        verifyNoMoreInteractions(senderAttributes);
    }

    @Test
    public void testRequest_ShouldThrowException_WhenCountryOfBirthIsoCodeWrong() {
        prepareObject();

        assertThrows(InvalidParamException.class, () -> senderAttributes.setSenderCountryOfBirthIsoCode(INVALID_ISO_COUNTRY_FORMAT));

        verify(senderAttributes).setSenderCountryOfBirthIsoCode(INVALID_ISO_COUNTRY_FORMAT);
        verify(senderAttributes, never()).getSenderAttrRequestBuilder();
        verify(senderAttributes, times(1)).setSenderCountryOfBirthIsoCode(INVALID_ISO_COUNTRY_FORMAT);
        verify(senderAttributes, times(1)).getValidator();
        verifyNoMoreInteractions(senderAttributes);
    }

    @Test
    public void testRequest_ShouldThrowException_WhenSenderIdTypeWrong() {
        prepareObject();

        assertThrows(InvalidParamException.class, () -> senderAttributes.setSenderIdType(ANY_STRING));

        verify(senderAttributes).setSenderIdType(ANY_STRING);
        verify(senderAttributes, never()).getSenderAttrRequestBuilder();
        verify(senderAttributes, times(1)).setSenderIdType(ANY_STRING);
        verify(senderAttributes, times(1)).isAllowedListOption(ANY_STRING,"sender_id_type", idTypes);
        verifyNoMoreInteractions(senderAttributes);
    }
}
