package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.BusinessApplicationIdentifierTypes;
import com.emerchantpay.gateway.api.constants.IdentifierTypes;
import com.emerchantpay.gateway.api.constants.ReceiverAccountTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.financial.funding.FundingAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.funding.ReceiverAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.funding.SenderAttributes;
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
    private final String RECEIVER_ADDRESS = "Sofia, Filip Kutev str";
    private final String RECEIVER_CITY = "Sofia";
    private final String RECEIVER_STATE = "State";
    private final String SENDER_NAME = "sender_name";
    private final String REFERENCE_NUMBER ="reference_number";
    private final String SENDER_ADDRESS ="Sofia, Filip Kutev str";
    private final String STATE = "State";
    private final String SENDER_CITY = "London";
    private final String ANY_STRING = "ANY_STRING";
    private RequestBuilder requestBuilder;
    private FundingAttributes fundingAttributes;

    private ReceiverAttributes receiverAttributes;
    private SenderAttributes senderAttributes;

    private HashMap<String, String> paramsMap;

    private void setStubs() {
        when(fundingAttributes.getFundingAttrParamsMap()).thenReturn(paramsMap);
        when(receiverAttributes.getReceiverAttrParamsMap()).thenReturn(paramsMap);
        when(senderAttributes.getSenderAttributeParamsMap()).thenReturn(paramsMap);
        when(fundingAttributes.getFundingAttrRequestBuilder()).thenReturn(requestBuilder);
        when(receiverAttributes.getReceiverAttrRequestBuilder()).thenReturn(requestBuilder);
        when(senderAttributes.getSenderAttrributeRequestBuilder()).thenReturn(requestBuilder);
        when(fundingAttributes.getValidator()).thenReturn(new RequiredParametersValidator(paramsMap));
    }

    @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        fundingAttributes = spy(FundingAttributes.class);
        receiverAttributes = spy(ReceiverAttributes.class);
        senderAttributes = spy(SenderAttributes.class);
        paramsMap = new HashMap<>();
    }

    @Test
    public void testFunding_ShouldSuccess_WhenValidParams() {
        setStubs();

        assertEquals(fundingAttributes, fundingAttributes.setIdentifierType(IdentifierTypes.GENERAL_PERSON_TO_PERSON.getType()));
        assertEquals(IdentifierTypes.GENERAL_PERSON_TO_PERSON.getType(), fundingAttributes.getIdentifierType());
        assertEquals(fundingAttributes, fundingAttributes.setBusinessApplicationIdentifier(BusinessApplicationIdentifierTypes.FUND_TRANSFER.getType()));
        assertEquals(BusinessApplicationIdentifierTypes.FUND_TRANSFER.getType(), fundingAttributes.getBusinessApplicationIdentifier());
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
        assertEquals(receiverAttributes, receiverAttributes.setReceiverAddress(RECEIVER_ADDRESS));
        assertEquals(RECEIVER_ADDRESS, receiverAttributes.getReceiverAddress());
        assertEquals(receiverAttributes, receiverAttributes.setReceiverState(RECEIVER_STATE));
        assertEquals(RECEIVER_STATE, receiverAttributes.getReceiverState());
        assertEquals(receiverAttributes, receiverAttributes.setReceiverCity(RECEIVER_CITY));
        assertEquals(RECEIVER_CITY, receiverAttributes.getReceiverCity());
        assertEquals(senderAttributes, senderAttributes.setSenderName(SENDER_NAME));
        assertEquals(SENDER_NAME, senderAttributes.getSenderName());
        assertEquals(senderAttributes, senderAttributes.setSenderCountry(Country.Germany.getCode()));
        assertEquals("DE", senderAttributes.getSenderCountry());
        assertEquals(senderAttributes, senderAttributes.setSenderReferenceNumber(REFERENCE_NUMBER));
        assertEquals(REFERENCE_NUMBER, senderAttributes.getSenderReferenceNumber());
        assertEquals(senderAttributes, senderAttributes.setFundingSenderAddress(SENDER_ADDRESS));
        assertEquals(SENDER_ADDRESS, senderAttributes.getFundingSenderAddress());
        assertEquals(senderAttributes, senderAttributes.setSenderState(STATE));
        assertEquals(STATE, senderAttributes.getSenderState());
        assertEquals(senderAttributes, senderAttributes.setSenderCity(SENDER_CITY));
        assertEquals(SENDER_CITY, senderAttributes.getSenderCity());
    }

    @Test(expected = InvalidParamException.class)
    public void testFunding_ThrowException_WhenInvalidIdentifierType() {
        setStubs();

        fundingAttributes.setIdentifierType(ANY_STRING);
        assertEquals(requestBuilder, fundingAttributes.buildFundingParams());
    }

    @Test(expected = InvalidParamException.class)
    public void testFunding_ThrowException_WhenBusinessApplicationIdentifier() {
        setStubs();

        fundingAttributes.setBusinessApplicationIdentifier(ANY_STRING);
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
