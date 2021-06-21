package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/*
UCOF in request

#MasterCard
<credential_on_file>merchant_unscheduled</credential_on_file>
<credential_on_file_transaction_identifier>MCC242736</credential_on_file_transaction_identifier>
<credential_on_file_settlement_date>0107</credential_on_file_settlement_date>

#Visa
<credential_on_file>merchant_unscheduled</credential_on_file>
<credential_on_file_transaction_identifier>MCC242736</credential_on_file_transaction_identifier>

Initial customer initiated in request
<credential_on_file>initial_customer_initiated</credential_on_file>

Subsequent customer initiated in request
<credential_on_file>subsequent_customer_initiated</credential_on_file>
 */
public class UcofAttributesTest {

    private static final String VALID_SETT_DATE = "0107";
    private static final String INVALID_SETT_DATE = "0133";
    private static final String TRANSACTION_IDENTIFIER = "MCC242736";
    private static final HashMap<String, String> params = new HashMap<String, String>() {
        {
            put("credential_on_file_transaction_identifier", TRANSACTION_IDENTIFIER);
            put("credential_on_file_settlement_date", VALID_SETT_DATE);
        }
    };

    private RequestBuilder requestBuilder;
    private UcofAttributes cofAdditionalAttributes;
    private GenesisValidator validator;

    @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        cofAdditionalAttributes = spy(UcofAttributes.class);
        validator = spy(GenesisValidator.class);
    }

    private void prepareObject() {
        when(cofAdditionalAttributes.getUcofAttrRequestBuilder()).thenReturn(requestBuilder);
        when(cofAdditionalAttributes.getUcofAttrParamsMap()).thenReturn(params);
        when(cofAdditionalAttributes.getValidator()).thenReturn(validator);
    }

    @Test
    public void testGetUcofRequestBuilder_ShouldSuccess_WhenValidArguments() {
        prepareObject();

        assertEquals(requestBuilder, cofAdditionalAttributes.buildUcofParams("merchant_unscheduled"));
    }

    @Test(expected = RegexException.class)
    public void testGetUcofRequestBuilder_ThrowException_WhenInValidDate() {
        prepareObject();
        when(cofAdditionalAttributes.getUcofSettlementDate()).thenReturn(INVALID_SETT_DATE);
        when(cofAdditionalAttributes.getValidator().isValidMonthDate(anyString(), anyString())).thenReturn(false);

        assertEquals(requestBuilder, cofAdditionalAttributes.buildUcofParams("merchant_unscheduled"));
    }

    @Test
    public void testGetUcofRequestBuilder_ShouldSuccess_WhenValidArgumentsNotMerchantUnscheduledCOF() {
        when(cofAdditionalAttributes.getValidator()).thenReturn(validator);
        when(cofAdditionalAttributes.getUcofAttrRequestBuilder()).thenReturn(requestBuilder);
        when(cofAdditionalAttributes.getUcofSettlementDate()).thenReturn(null);

        assertEquals(requestBuilder, cofAdditionalAttributes.buildUcofParams("initial_customer_initiated"));
    }

    @Test
    public void testGetUcofConditionalFields_ShouldSuccess_WhenMerchantUnscheduledCOF() {
        prepareObject();

        assertEquals(requestBuilder, cofAdditionalAttributes.buildUcofParams("merchant_unscheduled"));

        cofAdditionalAttributes.setUcofTransactionIdentifier(TRANSACTION_IDENTIFIER);
        cofAdditionalAttributes.setUcofSettlementDate(VALID_SETT_DATE);
        assertEquals(TRANSACTION_IDENTIFIER, cofAdditionalAttributes.getUcofTransactionIdentifier());
        assertEquals(VALID_SETT_DATE, cofAdditionalAttributes.getUcofSettlementDate());
    }

}