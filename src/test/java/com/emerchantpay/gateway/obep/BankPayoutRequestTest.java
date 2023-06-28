package com.emerchantpay.gateway.obep;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.BankPayoutRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BankPayoutRequestTest {

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1.0);
    private static final String BANK_BRANCH = "HDFC0000001";
    private static final String BANK_ACCOUNT_NUMBER = "1234123412341234";
    private static final String BANK_ACCOUNT_NAME = "Anurak Nghuen";
    private static final String BANK_ACCOUNT_TYPE = "S";
    private static final String PAYER_BANK_PHONE_NUMBER = "01234567891";

    private String uniqueId;
    private GenesisClient client;
    private BankPayoutRequest bankPayoutRequest;

    @Before
    public void createObjects() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        bankPayoutRequest = prepareRequest();
    }

    private BankPayoutRequest prepareRequest() throws MalformedURLException {
        BankPayoutRequest request = new BankPayoutRequest();
        request.setAmount(BigDecimal.valueOf(1.00));
        request.setCurrency(Currency.INR.getCurrency());
        request.setTransactionId(UUID.randomUUID().toString());
        request.setCustomerEmail("test@abv.bg");
        request.setCustomerPhone("124411");
        request.setRemoteIp("82.137.112.202");
        request.setUsage("TICKETS");
        request.setNotificationUrl(new URL("http://www.example.com/notification"));
        request.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        request.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        request.setBillingPrimaryAddress("First Avenue");
        request.setBillingSecondaryAddress("Second Avenue");
        request.setBillingFirstname("John");
        request.setBillingLastname("Doe");
        request.setBillingCity("Rosario");
        request.setBillingCountry(Country.Belgium.getCountry());
        request.setBillingZipCode("M4B1B3");
        request.setBillingState("BE");
        return request;
    }

    public void verifyExecute() throws MalformedURLException {
        prepareRequest();
        bankPayoutRequest.toXML();

        when(client.execute()).thenReturn(bankPayoutRequest);

        assertEquals(BigDecimal.valueOf(1.0), bankPayoutRequest.getAmount());
        assertEquals(Currency.INR.getCurrency(), bankPayoutRequest.getCurrency());
        assertEquals("bank_payout", bankPayoutRequest.getTransactionType());
        assertEquals(bankPayoutRequest, bankPayoutRequest.setAmount(BigDecimal.valueOf(1.0)).setCurrency(Currency.INR.getCurrency()));

        assertEquals(bankPayoutRequest, bankPayoutRequest.setBankBranch(BANK_BRANCH));
        assertEquals(bankPayoutRequest, bankPayoutRequest.setBankAccountName(BANK_ACCOUNT_NAME));
        assertEquals(bankPayoutRequest, bankPayoutRequest.setBankAccountNumber(BANK_ACCOUNT_NUMBER));
        assertEquals(bankPayoutRequest, bankPayoutRequest.setBankAccountType(BANK_ACCOUNT_TYPE));
        assertEquals(bankPayoutRequest, bankPayoutRequest.setPayerBankPhoneNumber(PAYER_BANK_PHONE_NUMBER));

        assertEquals(BANK_BRANCH, bankPayoutRequest.getBankBranch());
        assertEquals(BANK_ACCOUNT_NAME, bankPayoutRequest.getBankAccountName());
        assertEquals(BANK_ACCOUNT_NUMBER, bankPayoutRequest.getBankAccountNumber());
        assertEquals(BANK_ACCOUNT_TYPE, bankPayoutRequest.getBankAccountType());
        assertEquals(PAYER_BANK_PHONE_NUMBER, bankPayoutRequest.getPayerBankPhoneNumber());

        assertEquals(bankPayoutRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public  void testRequest_ShouldSuccess_WhenValidArguments() throws MalformedURLException {
        verifyExecute();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenMissingAmount() {
        bankPayoutRequest.setAmount(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingCurrency() {
        bankPayoutRequest.setCurrency(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingTransactionId() {
        bankPayoutRequest.setTransactionId(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingBillingPrimaryAddress() {
        bankPayoutRequest.setBillingPrimaryAddress(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingBillingFirstname() {
        bankPayoutRequest.setBillingFirstname(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingBillingLastname() {
        bankPayoutRequest.setBillingLastname(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingBillingState() {
        bankPayoutRequest.setBillingState(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenMissingBillingCountry() {
        bankPayoutRequest.setBillingCountry(null);
        bankPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testRequest_ShouldThrowException_WhenWrongCurrency() {
        bankPayoutRequest.setCurrency("BGL");
        bankPayoutRequest.toXML();
    }

    @Test
    public void testRequest_ShouldSuccess_WhenValidBirthDate() {
        bankPayoutRequest.setBirthDate("12-10-2023");
        bankPayoutRequest.toXML();
    }

    @Test(expected = RegexException.class)
    public void testRequest_ShouldThrowException_WhenWrongBirthDate() {
        bankPayoutRequest.setBirthDate("BGL");
        bankPayoutRequest.toXML();
    }
}
