package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.IdentifierTypes;
import com.emerchantpay.gateway.api.constants.ReceiverAccountTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.financial.funding.FundingAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.funding.ReceiverAttributes;
import com.emerchantpay.gateway.api.validation.RequiredParametersValidator;
import com.emerchantpay.gateway.util.Country;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FundingAttributesTest {

    private final String FIRSTNAME = "first_name";

    private final String LASTNAME = "last_name";

    private final String ACCOUNT_NUMBER = "3212322121";
    private final String ANY_STRING = "ANY_STRING";
    private RequestBuilder requestBuilder;
    private FundingAttributes fundingAttributes;

    private ReceiverAttributes receiverAttributes;

    private HashMap<String, String> paramsMap;

    private void setStubs() {
        when(fundingAttributes.getFundingAttrParamsMap()).thenReturn(paramsMap);
        when(receiverAttributes.getReceiverAttrParamsMap()).thenReturn(paramsMap);
        when(fundingAttributes.getFundingAttrRequestBuilder()).thenReturn(requestBuilder);
        when(receiverAttributes.getReceiverAttrRequestBuilder()).thenReturn(requestBuilder);
        when(fundingAttributes.getValidator()).thenReturn(new RequiredParametersValidator(paramsMap));
    }

    @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        fundingAttributes = spy(FundingAttributes.class);
        receiverAttributes = spy(ReceiverAttributes.class);
        paramsMap = new HashMap<>();
    }

    @Test
    public void testFunding_ShouldSuccess_WhenValidParams() {
        setStubs();

        assertEquals(fundingAttributes, fundingAttributes.setIdentifierType(IdentifierTypes.GENERAL_PERSON_TO_PERSON.getType()));
        assertEquals(IdentifierTypes.GENERAL_PERSON_TO_PERSON.getType(), fundingAttributes.getIdentifierType());
        assertEquals(receiverAttributes, receiverAttributes.setReceiverFirstname(FIRSTNAME));
        assertEquals(FIRSTNAME, receiverAttributes.getReceiverFirstname());
        assertEquals(receiverAttributes, receiverAttributes.setReceiverLastname(LASTNAME));
        assertEquals(LASTNAME, receiverAttributes.getReceiverLastname());
        assertEquals(receiverAttributes, receiverAttributes.setReceiverCountry(Country.Germany.getCode()));
        assertEquals("DE", receiverAttributes.getReceiverCountry());
        assertEquals(receiverAttributes, receiverAttributes.setReceiverAccountNumber(ACCOUNT_NUMBER));
        assertEquals(ACCOUNT_NUMBER, receiverAttributes.getReceiverAccountNumber());
        assertEquals(receiverAttributes, receiverAttributes.setReceiverAccountNumberType(ReceiverAccountTypes.IBAN.getType()));
        assertEquals(ReceiverAccountTypes.IBAN.getType(), receiverAttributes.getReceiverAccountNumberType());
    }

    @Test(expected = InvalidParamException.class)
    public void testFunding_ThrowException_WhenInvalidIdentifierType() {
        setStubs();

        fundingAttributes.setIdentifierType(ANY_STRING);
        assertEquals(requestBuilder, fundingAttributes.buildFundingParams());
    }

    @Test(expected = InvalidParamException.class)
    public void testFunding_ThrowException_WhenInvalidReceiverAccountType() {
        setStubs();

        assertEquals(fundingAttributes, fundingAttributes.setIdentifierType(IdentifierTypes.GENERAL_PERSON_TO_PERSON.getType()));
        assertEquals(IdentifierTypes.GENERAL_PERSON_TO_PERSON.getType(), fundingAttributes.getIdentifierType());
        receiverAttributes.setReceiverAccountNumberType(ANY_STRING);
        assertEquals(requestBuilder, fundingAttributes.buildFundingParams());
    }

    @Test(expected = InvalidParamException.class)
    public void testFunding_ThrowException_WhenInvalidCountry() {
        setStubs();

        when(fundingAttributes.setIdentifierType(ANY_STRING)).thenReturn(fundingAttributes);
        receiverAttributes.setReceiverCountry(ANY_STRING);
        assertEquals(requestBuilder, fundingAttributes.buildFundingParams());
    }
}
