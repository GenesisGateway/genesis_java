package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.apm.TransferToPayoutRequest;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TransferToPayoutTest {

    private static final double AMOUNT = 1.50;
    private static final String CUSTOMER_EMAIL = "test@abv.bg";
    private static final String PAYER_ID = "2423";
    private static final String BANK_ACCOUNT_NUMBER = "12625243646455";
    private static final String IFS_CODE = "156";
    private static final String IBAN = "BG76FGDT0012625243646455";
    private static final String SENDER_ID_TYPE = "PASSPORT";
    private static final String SENDER_PROVINCE_STATE = "EL";
    private static final String SENDER_SOURCE_OF_FUNDS = "Savings";
    private static final String SENDER_COUNTRY_OF_BIRTH_ISO_CODE = "ISL";
    private static final String MSISDN = "376283";
    private static final String REGISTERED_NAME = "John Doe";
    private static final String REGISTRATION_NUMBER = "67262527364447";

    private String uniqueId;
    private GenesisClient client;
    private TransferToPayoutRequest transferToPayout;

    @Before
    public void createTransferToPayoutRequest() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        transferToPayout = new TransferToPayoutRequest();
    }

    private void prepareObjectInitial() {
        transferToPayout.setAmount(BigDecimal.valueOf(AMOUNT));
        transferToPayout.setCurrency(Currency.USD.getCurrency());
        transferToPayout.setTransactionId(UUID.randomUUID().toString());
        transferToPayout.setPayerId("2423");
        transferToPayout.setCustomerEmail("test@abv.bg");
        transferToPayout.setRemoteIp("82.137.112.202");
        transferToPayout.setUsage("Funding");
        transferToPayout.setBankAccountNumber("12625243646455");
        transferToPayout.setSenderFirstName("Tim");
        transferToPayout.setSenderLastName("Salun");
        transferToPayout.setSenderDateOfBirth("1990-03-10");
        transferToPayout.setSenderCountryIsoCode("IDN");
        transferToPayout.setBillingPrimaryAddress("First Avenue");
        transferToPayout.setBillingSecondaryAddress("Second Avenue");
        transferToPayout.setBillingFirstname("John");
        transferToPayout.setBillingLastname("Doe");
        transferToPayout.setBillingCity("Rosario");
        transferToPayout.setBillingCountry(Country.Indonesia.getCountry());
        transferToPayout.setBillingZipCode("M4B1B3");
        transferToPayout.setBillingState("NL");
    }

    private void prepareUrls() throws MalformedURLException {
        transferToPayout.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        transferToPayout.setReturnFailureUrl(new URL("http://www.example.com/failure"));
    }

    private void prepareObject() throws MalformedURLException {
        prepareObjectInitial();
        prepareUrls();
    }

    @Test
    public void testRequest() throws MalformedURLException {
        prepareObject();

        when(client.execute()).thenReturn(transferToPayout);

        assertEquals(BigDecimal.valueOf(AMOUNT), transferToPayout.getAmount());
        assertEquals(Currency.USD.getCurrency(), transferToPayout.getCurrency());
        assertEquals(TransactionTypes.TRANSFER_TO_PAYOUT, transferToPayout.getTransactionType());
        assertEquals(transferToPayout, transferToPayout.setAmount(BigDecimal.valueOf(AMOUNT)).setCurrency(Currency.USD.getCurrency()));
        assertEquals(transferToPayout, transferToPayout.setTransactionId(uniqueId));

        assertEquals(transferToPayout, transferToPayout.setCustomerEmail(CUSTOMER_EMAIL));
        assertEquals(transferToPayout, transferToPayout.setPayerId(PAYER_ID));
        assertEquals(transferToPayout, transferToPayout.setBankAccountNumber(BANK_ACCOUNT_NUMBER));
        assertEquals(transferToPayout, transferToPayout.setMsisdn(MSISDN));
        assertEquals(transferToPayout, transferToPayout.setRegisteredName(REGISTERED_NAME));
        assertEquals(transferToPayout, transferToPayout.setRegistrationNumber(REGISTRATION_NUMBER));
        assertEquals(transferToPayout, transferToPayout.setIfsCode(IFS_CODE));
        assertEquals(transferToPayout, transferToPayout.setIban(IBAN));
        assertEquals(CUSTOMER_EMAIL, transferToPayout.getCustomerEmail());
        assertEquals(PAYER_ID, transferToPayout.getPayerId());
        assertEquals(BANK_ACCOUNT_NUMBER, transferToPayout.getBankAccountNumber());
        assertEquals(MSISDN, transferToPayout.getMsisdn());
        assertEquals(REGISTERED_NAME, transferToPayout.getRegisteredName());
        assertEquals(REGISTRATION_NUMBER, transferToPayout.getRegistrationNumber());
        assertEquals(IFS_CODE, transferToPayout.getIfsCode());
        assertEquals(IBAN, transferToPayout.getIban());

        assertEquals(transferToPayout, transferToPayout.setSenderIdType(SENDER_ID_TYPE));
        assertEquals(transferToPayout, transferToPayout.setSenderProvinceState(SENDER_PROVINCE_STATE));
        assertEquals(transferToPayout, transferToPayout.setSenderSourceOfFunds(SENDER_SOURCE_OF_FUNDS));
        assertEquals(transferToPayout, transferToPayout.setSenderCountryOfBirthIsoCode(SENDER_COUNTRY_OF_BIRTH_ISO_CODE));
        assertEquals(SENDER_ID_TYPE, transferToPayout.getSenderIdType());
        assertEquals(SENDER_PROVINCE_STATE, transferToPayout.getSenderProvinceState());
        assertEquals(SENDER_SOURCE_OF_FUNDS, transferToPayout.getSenderSourceOfFunds());
        assertEquals(SENDER_COUNTRY_OF_BIRTH_ISO_CODE, transferToPayout.getSenderCountryOfBirthIsoCode());

        assertEquals(transferToPayout, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test(expected = RequiredParamsException.class)
    public void testAmount_MissingReturnSuccessUrl() throws MalformedURLException {
        prepareObjectInitial();
        transferToPayout.setReturnFailureUrl(new URL("http://www.example.com/failure"));

        transferToPayout.setReturnSuccessUrl(null);
        transferToPayout.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testAmount_MissingReturnFailureUrl() throws MalformedURLException {
        prepareObjectInitial();
        transferToPayout.setReturnSuccessUrl(new URL("http://www.example.com/success"));

        transferToPayout.setReturnFailureUrl(null);
        transferToPayout.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testAmount_MissingTransactionId() throws MalformedURLException {
        prepareObject();
        transferToPayout.setTransactionId(null);
        transferToPayout.toXML();
    }

    @Test(expected = RegexException.class)
    public void testAmount_MissingAmount() throws MalformedURLException {
        prepareObject();
        transferToPayout.setAmount(null);
        transferToPayout.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testAmount_MissingCurrency() throws MalformedURLException {
        prepareObject();
        transferToPayout.setCurrency(null);
        transferToPayout.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testAmount_MissingPayerId() throws MalformedURLException {
        prepareObject();
        transferToPayout.setPayerId(null);
        transferToPayout.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testAmount_MissingBillingCountry() throws MalformedURLException {
        prepareObject();
        transferToPayout.setBillingCountry(null);
        transferToPayout.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testAmount_MissingBillingPrimaryAddress() throws MalformedURLException {
        prepareObject();
        transferToPayout.setBillingPrimaryAddress(null);
        transferToPayout.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testValidateCurrency_notAllowedCurrency() throws MalformedURLException {
        prepareObject();
        transferToPayout.setCurrency("BGL");
        transferToPayout.toXML();
    }

}
