package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDealRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IDealRequestTest {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1.0);
    private static final String BIC = "RABONL2U";
    private String uniqueId;
    private GenesisClient client;
    private IDealRequest iDealRequest;

    @Before
    public void createIDeal() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        iDealRequest = new IDealRequest();
    }

    private void prepareObject() throws MalformedURLException {
        iDealRequest.setAmount(AMOUNT);
        iDealRequest.setTransactionId(uniqueId);
        iDealRequest.setBic(BIC);
        iDealRequest.setRemoteIp("82.137.112.202");
        iDealRequest.setCustomerEmail("test@abv.bg");
        iDealRequest.setCustomerPhone("124411");
        iDealRequest.setUsage("TICKETS");
        iDealRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        iDealRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        iDealRequest.setBillingPrimaryAddress("First Avenue");
        iDealRequest.setBillingSecondaryAddress("Second Avenue");
        iDealRequest.setBillingFirstname("John");
        iDealRequest.setBillingLastname("Doe");
        iDealRequest.setBillingCity("Rosario");
        iDealRequest.setBillingCountry(Country.Netherlands.getCountry());
        iDealRequest.setBillingZipCode("M4B1B3");
        iDealRequest.setBillingState("BE");
    }

    private void verifyExecute() throws MalformedURLException {
        prepareObject();
        iDealRequest.toXML();

        when(client.execute()).thenReturn(iDealRequest);

        assertEquals(AMOUNT, iDealRequest.getAmount());
        assertEquals(Currency.EUR.getCurrency(), iDealRequest.getCurrency());
        assertEquals(TransactionTypes.IDEAL, iDealRequest.getTransactionType());
        assertEquals(uniqueId, iDealRequest.getTransactionId());
        assertEquals(BIC, iDealRequest.getBic());
        assertEquals(iDealRequest, iDealRequest.setAmount(AMOUNT).setCurrency(Currency.EUR.getCurrency()).setBic(BIC));
        assertEquals(iDealRequest, iDealRequest.setTransactionId(uniqueId));

        assertEquals(iDealRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidArguments() throws MalformedURLException {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() throws MalformedURLException {
        prepareObject();
        iDealRequest.setAmount(null);
        iDealRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCurrencyIsNull() throws MalformedURLException {
        prepareObject();
        iDealRequest.setCurrency(null);
        iDealRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() throws MalformedURLException {
        prepareObject();
        iDealRequest.setTransactionId(null);
        iDealRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenCountryNetherlands() throws MalformedURLException {
        prepareObject();
        iDealRequest.setBillingCountry(Country.Netherlands.getCode());
        iDealRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCountry() throws MalformedURLException {
        prepareObject();
        iDealRequest.setBillingCountry(null);
        iDealRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCountryIsNotNetherlands() throws MalformedURLException {
        prepareObject();
        iDealRequest.setBillingCountry(Country.Finland.getCode());
        iDealRequest.toXML();
    }

}