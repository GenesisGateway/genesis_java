package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CredentialOnFileAttributesTest {

    private static final String INITIAL_CUSTOMER_INITIATED = "initial_customer_initiated";
    private RequestBuilder requestBuilder;
    private CredentialOnFileAttributes cofAttributes;

    @Before
    public void createObjects() {
        requestBuilder = mock(RequestBuilder.class);
        cofAttributes = spy(CredentialOnFileAttributes.class);
    }

    @Test
    public void testGetCOFRequestBuilder_ShouldSuccess_WhenCOFValid() {
        when(cofAttributes.getCredentialOnFile()).thenReturn(INITIAL_CUSTOMER_INITIATED);
        when(cofAttributes.getCredentialOnFileAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(requestBuilder, cofAttributes.buildCredentialOnFileParam());
    }

    @Test
    public void testGetCOFRequestBuilder_ShouldSuccess_WhenCOFIsNull() {
        when(cofAttributes.getCredentialOnFile()).thenReturn(null);
        when(cofAttributes.getCredentialOnFileAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(requestBuilder, cofAttributes.buildCredentialOnFileParam());
    }

    @Test
    public void testGetCOFRequestBuilder_ShouldSuccess_WhenCOFIsEmpty() {
        when(cofAttributes.getCredentialOnFile()).thenReturn("");
        when(cofAttributes.getCredentialOnFileAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(requestBuilder, cofAttributes.buildCredentialOnFileParam());
    }

    @Test(expected = InvalidParamException.class)
    public void testGetCOFRequestBuilder_ShouldThrowException_WhenCOFInvalid() {
        when(cofAttributes.getCredentialOnFile()).thenReturn("other_value");
        when(cofAttributes.getCredentialOnFileAttrRequestBuilder()).thenReturn(requestBuilder);

        assertEquals(requestBuilder, cofAttributes.buildCredentialOnFileParam());
    }
}