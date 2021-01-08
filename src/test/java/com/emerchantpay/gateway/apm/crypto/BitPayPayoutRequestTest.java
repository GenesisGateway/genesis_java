package com.emerchantpay.gateway.apm.crypto;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.CryptoWalletProviders;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.apm.crypto.BitPayPayoutRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class BitPayPayoutRequestTest {

    private static final String CRYPTO_ADDRESS = "mtXWDB6k5yC5v7TcwKZHB89SUp85yCKshy";
    private static final String CRYPTO_WALLET_PROVIDER = "BitGo";
    private static final double AMOUNT = 1.0;

    private String uniqueId;
    private GenesisClient client;
    private BitPayPayoutRequest bitPayPayoutRequest;

    @Before
    public void createBitPayPayout() {
        uniqueId = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        bitPayPayoutRequest = new BitPayPayoutRequest();
    }

    private void prepareObject() throws MalformedURLException {
        bitPayPayoutRequest.setCurrency(Currency.EUR.getCurrency());
        bitPayPayoutRequest.setTransactionId(uniqueId);
        bitPayPayoutRequest.setRemoteIp("82.137.112.202");
        bitPayPayoutRequest.setCustomerEmail("test@abv.bg");
        bitPayPayoutRequest.setCustomerPhone("124411");
        bitPayPayoutRequest.setUsage("TICKETS");
        bitPayPayoutRequest.setNotificationUrl(new URL("http://www.example.com/notification"));
        bitPayPayoutRequest.setReturnSuccessUrl(new URL("http://www.example.com/success"));
        bitPayPayoutRequest.setReturnFailureUrl(new URL("http://www.example.com/failure"));
        bitPayPayoutRequest.setBillingPrimaryAddress("First Avenue");
        bitPayPayoutRequest.setBillingSecondaryAddress("Second Avenue");
        bitPayPayoutRequest.setBillingFirstname("John");
        bitPayPayoutRequest.setBillingLastname("Doe");
        bitPayPayoutRequest.setBillingCity("Rosario");
        bitPayPayoutRequest.setBillingCountry(Country.Belgium.getCountry());
        bitPayPayoutRequest.setBillingZipCode("M4B1B3");
        bitPayPayoutRequest.setBillingState("BE");
    }

    private void verifyExecute() throws MalformedURLException {
        prepareObject();
        bitPayPayoutRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPayPayoutRequest.setCryptoAddress(CRYPTO_ADDRESS);
        bitPayPayoutRequest.setCryptoWalletProvider(CRYPTO_WALLET_PROVIDER);
        bitPayPayoutRequest.toXML();

        when(client.execute()).thenReturn(bitPayPayoutRequest);

        assertEquals(BigDecimal.valueOf(AMOUNT), bitPayPayoutRequest.getAmount());
        assertEquals(Currency.EUR.getCurrency(), bitPayPayoutRequest.getCurrency());
        assertEquals(CRYPTO_ADDRESS, bitPayPayoutRequest.getCryptoAddress());
        assertEquals(CRYPTO_WALLET_PROVIDER, bitPayPayoutRequest.getCryptoWalletProvider());
        assertEquals(TransactionTypes.BITPAY_PAYOUT, bitPayPayoutRequest.getTransactionType());
        assertEquals(bitPayPayoutRequest, bitPayPayoutRequest.setAmount(BigDecimal.valueOf(AMOUNT)).setCurrency(Currency.EUR.getCurrency()));
        assertEquals(bitPayPayoutRequest, bitPayPayoutRequest.setTransactionId(uniqueId));

        assertEquals(bitPayPayoutRequest, client.execute());

        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testRequest() throws MalformedURLException {
        verifyExecute();
    }

    @Test
    public void testValidateCryptoWalletProvider() {
        assertEquals("BitGo", CryptoWalletProviders.validate("bitgo"));
        assertEquals("Uphold", CryptoWalletProviders.validate("uphold"));
        assertEquals("Circle", CryptoWalletProviders.validate("circle"));
        assertEquals("Coinbase", CryptoWalletProviders.validate("coinbase"));
        assertEquals("GDax", CryptoWalletProviders.validate("gDax"));
        assertEquals("Gemini", CryptoWalletProviders.validate("gemini"));
        assertEquals("ITBit", CryptoWalletProviders.validate("itBit"));
        assertEquals("Kraken", CryptoWalletProviders.validate("kraken"));
        assertEquals("other", CryptoWalletProviders.validate("other"));
        assertEquals("other", CryptoWalletProviders.validate("any_other_provider"));
        assertNull(CryptoWalletProviders.validate(null));
    }

    @Test(expected = RequiredParamsException.class)
    public void testValidateCryptoWalletProvider_MissingAmount() throws MalformedURLException {
        prepareObject();
        bitPayPayoutRequest.setCryptoAddress(CRYPTO_ADDRESS);
        bitPayPayoutRequest.setCryptoWalletProvider(CRYPTO_WALLET_PROVIDER);
        bitPayPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testValidateCryptoWalletProvider_MissingCryptoAddress() throws MalformedURLException {
        prepareObject();
        bitPayPayoutRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPayPayoutRequest.setCryptoWalletProvider(CRYPTO_WALLET_PROVIDER);
        bitPayPayoutRequest.toXML();
    }

    @Test(expected = RequiredParamsException.class)
    public void testValidateCryptoWalletProvider_MissingCryptoWalletProvider() throws MalformedURLException {
        prepareObject();
        bitPayPayoutRequest.setAmount(BigDecimal.valueOf(AMOUNT));
        bitPayPayoutRequest.setCryptoAddress(CRYPTO_ADDRESS);
        bitPayPayoutRequest.toXML();
    }
}
