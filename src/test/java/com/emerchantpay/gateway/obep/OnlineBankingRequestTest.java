package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.OnlineBankingRequest;
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

public class OnlineBankingRequestTest {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1.0);
    private static final String BANK_CODE = "ABC";
    private static final String DOCUMENT_ID = "ABCDE1234F";
    private static final String PAYMENT_TYPE = "online_banking";
    private static final String VIRTUAL_PAYMENT_ADDRESS = "someone@bank";
    private static final String CONSUMER_REFERENCE = "someone@bank";
    private String uniqueId;
    private GenesisClient client;
    private OnlineBankingRequest onlineBankingRequest;

    @Before
    public void createObjects() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        onlineBankingRequest = prepareObject();
    }

    private OnlineBankingRequest prepareObject() throws MalformedURLException {
        onlineBankingRequest = new OnlineBankingRequest();
        onlineBankingRequest.setCurrency(Currency.CNY.getCurrency());
        onlineBankingRequest.setAmount(AMOUNT);
        onlineBankingRequest.setTransactionId(uniqueId);
        onlineBankingRequest.setRemoteIp("82.137.112.202");
        onlineBankingRequest.setCustomerEmail("test@abv.bg");
        onlineBankingRequest.setCustomerPhone("124411");
        onlineBankingRequest.setUsage("TICKETS");
        onlineBankingRequest.setBankCode(BANK_CODE);
        onlineBankingRequest.setDocumentId(DOCUMENT_ID);
        onlineBankingRequest.setVirtualPaymentAddress(VIRTUAL_PAYMENT_ADDRESS);
        onlineBankingRequest.setConsumerReference(CONSUMER_REFERENCE);
        onlineBankingRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        onlineBankingRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        onlineBankingRequest.setBillingPrimaryAddress("First Avenue");
        onlineBankingRequest.setBillingSecondaryAddress("Second Avenue");
        onlineBankingRequest.setBillingFirstname("John");
        onlineBankingRequest.setBillingLastname("Doe");
        onlineBankingRequest.setBillingCity("Rosario");
        onlineBankingRequest.setBillingCountry(Country.China.getCountry());
        onlineBankingRequest.setBillingZipCode("M4B1B3");
        onlineBankingRequest.setBillingState("BE");

        return onlineBankingRequest;
    }

    private void verifyExecute() throws MalformedURLException {
        onlineBankingRequest.toXML();

        when(client.execute()).thenReturn(onlineBankingRequest);

        assertEquals(AMOUNT, onlineBankingRequest.getAmount());
        assertEquals(Currency.CNY.getCurrency(), onlineBankingRequest.getCurrency());
        assertEquals(TransactionTypes.ONLINE_BANKING, onlineBankingRequest.getTransactionType());
        assertEquals(uniqueId, onlineBankingRequest.getTransactionId());
        assertEquals(BANK_CODE, onlineBankingRequest.getBankCode());
        assertEquals(DOCUMENT_ID, onlineBankingRequest.getDocumentId());
        assertEquals(VIRTUAL_PAYMENT_ADDRESS, onlineBankingRequest.getVirtualPaymentAddress());
        assertEquals(CONSUMER_REFERENCE, onlineBankingRequest.getConsumerReference());

        assertEquals(onlineBankingRequest, onlineBankingRequest.setAmount(AMOUNT).setCurrency(Currency.CNY.getCurrency()));
        assertEquals(onlineBankingRequest, onlineBankingRequest.setBankCode(BANK_CODE).setTransactionId(uniqueId));
        assertEquals(onlineBankingRequest, onlineBankingRequest.setDocumentId(DOCUMENT_ID).setPaymentType(PAYMENT_TYPE));
        assertEquals(onlineBankingRequest, onlineBankingRequest.setVirtualPaymentAddress(VIRTUAL_PAYMENT_ADDRESS)
                .setConsumerReference(CONSUMER_REFERENCE));

        assertEquals(onlineBankingRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidArguments() throws MalformedURLException {
        verifyExecute();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() throws MalformedURLException {
        onlineBankingRequest.setAmount(null);
        onlineBankingRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCurrency() throws MalformedURLException {
        onlineBankingRequest.setCurrency(null);
        onlineBankingRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() throws MalformedURLException {
        onlineBankingRequest.setTransactionId(null);
        onlineBankingRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingBankCode() throws MalformedURLException {
        onlineBankingRequest.setBankCode(null);
        onlineBankingRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenDefaultPaymentType() throws MalformedURLException {
        onlineBankingRequest.setPaymentType("");
        onlineBankingRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidPaymentType() throws MalformedURLException {
        onlineBankingRequest.setPaymentType("quick_payment");
        onlineBankingRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenPaymentTypeNotAllowed() throws MalformedURLException {
        onlineBankingRequest.setPaymentType("not_allowed");
        onlineBankingRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenCurrencyChinaYen() throws MalformedURLException {
        onlineBankingRequest.setCurrency(Currency.CNY.getCurrency());
        onlineBankingRequest.setBankCode("BOB");
        onlineBankingRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCurrencyChinaYenBankCodeNonValid() throws MalformedURLException {
        onlineBankingRequest.setCurrency(Currency.CNY.getCurrency());
        onlineBankingRequest.setBankCode("non_valid");
        onlineBankingRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenCurrencyChileanPeso() throws MalformedURLException {
        onlineBankingRequest.setCurrency(Currency.CLP.getCurrency());
        onlineBankingRequest.setBankCode("SP");
        onlineBankingRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCurrencyChileanPesoBankCodeNonValid() throws MalformedURLException {
        onlineBankingRequest.setCurrency(Currency.CLP.getCurrency());
        onlineBankingRequest.setBankCode("non_valid");
        onlineBankingRequest.toXML();
    }

    @Test(expected = InvalidParamException.class)
    public void testRequest_ShouldThrowException_WhenCurrencyIsNotAllowed() throws MalformedURLException {
        onlineBankingRequest.setCurrency(Currency.EUR.getCurrency());
        onlineBankingRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidVirtualPaymentAddress() {
        onlineBankingRequest.setVirtualPaymentAddress("user@domain.com");
        onlineBankingRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenInvalidVirtualPaymentAddress() throws MalformedURLException {
        onlineBankingRequest.setVirtualPaymentAddress("any_string");
        onlineBankingRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidConsumerReference() {
        onlineBankingRequest.setConsumerReference("user@domain.com");
        onlineBankingRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenInvalidConsumerReference() throws MalformedURLException {
        onlineBankingRequest.setConsumerReference("any_string");
        onlineBankingRequest.toXML();
    }
}