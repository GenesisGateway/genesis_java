package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.wallets.EzeewalletRequest;
import com.emerchantpay.gateway.api.requests.financial.wallets.NetellerRequest;
import com.emerchantpay.gateway.api.requests.financial.wallets.WebMoneyRequest;
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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class WalletsTest {

    private GenesisClient client;

    private EzeewalletRequest ezee = new EzeewalletRequest();
    private WebMoneyRequest webmoney  = new WebMoneyRequest();
    private NetellerRequest neteller = new NetellerRequest();

    private String uidEzee;
    private String uidWebmoney;
    private String uidNeteller;

    @Before
    public void createEzeewallet() {
        uidEzee = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        ezee = mock(EzeewalletRequest.class);
    }

    @Before
    public void createWebmoney() {
        uidWebmoney = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        webmoney = mock(WebMoneyRequest.class);
    }

    @Before
    public void createNeteller() throws MalformedURLException {
        uidNeteller = new StringUtils().generateUID();

        client = mock(GenesisClient.class);
        neteller = mock(NetellerRequest.class);
    }

    public void clearRequiredParams() {
        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
        ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
                new Throwable());

        when(ezee.setBillingCountry(null)).thenThrow(exception);
        when(webmoney.setBillingCountry(null)).thenThrow(exception);
        when(neteller.setBillingCountry(null)).thenThrow(exception);
    }

    public void verifyEzeeExecute() {
        when(client.execute()).thenReturn(ezee);
        assertEquals(client.execute(), ezee);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyWebmoneyExecute() {
        when(client.execute()).thenReturn(webmoney);
        assertEquals(client.execute(), webmoney);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    public void verifyNetellerExecute() {
        when(client.execute()).thenReturn(neteller);
        assertEquals(client.execute(), neteller);
        verify(client).execute();
        verifyNoMoreInteractions(client);
    }

    @Test
    public void testEzee() throws MalformedURLException {

        // Ezeewallet
        when(ezee.setTransactionId(isA(String.class))).thenReturn(ezee);
        when(ezee.setRemoteIp(isA(String.class))).thenReturn(ezee);
        when(ezee.setUsage(isA(String.class))).thenReturn(ezee);
        when(ezee.setCurrency(isA(String.class))).thenReturn(ezee);
        when(ezee.setAmount(isA(BigDecimal.class))).thenReturn(ezee);
        when(ezee.setSourceWalletId(isA(String.class))).thenReturn(ezee);
        when(ezee.setSourceWalletPwd(isA(String.class))).thenReturn(ezee);
        when(ezee.setReturnSuccessUrl(isA(URL.class))).thenReturn(ezee);
        when(ezee.setReturnFailureUrl(isA(URL.class))).thenReturn(ezee);
        when(ezee.setNotificationUrl(isA(URL.class))).thenReturn(ezee);

        assertEquals(ezee.setTransactionId(uidEzee).setRemoteIp("82.137.112.202").setUsage("TICKETS"), ezee);
        assertEquals(ezee.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")), ezee);
        assertEquals(ezee.setSourceWalletId("john@example.com").setSourceWalletPwd("UDBydsDBrYWxAQA==\\n"), ezee);
        assertEquals(ezee.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), ezee);
        assertEquals(ezee.setNotificationUrl(new URL("http://www.example.com/notification")), ezee);


        verify(ezee).setTransactionId(uidEzee);
        verify(ezee).setRemoteIp("82.137.112.202");
        verify(ezee).setUsage("TICKETS");
        verify(ezee).setCurrency(Currency.USD.getCurrency());
        verify(ezee).setAmount(new BigDecimal("2.00"));
        verify(ezee).setSourceWalletId("john@example.com");
        verify(ezee).setSourceWalletPwd("UDBydsDBrYWxAQA==\\n");
        verify(ezee).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(ezee).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(ezee).setNotificationUrl(new URL("http://www.example.com/notification"));
        verifyNoMoreInteractions(ezee);

        verifyEzeeExecute();
    }

    @Test(expected = ApiException.class)
    public void testEzeewalletWithMissingParams() {
        clearRequiredParams();

        assertNull(ezee.setBillingCountry(null));
        verify(ezee).setBillingCountry(null);
        verifyNoMoreInteractions(ezee);

        verifyEzeeExecute();
    }

    @Test
    public void testWebmoney() throws MalformedURLException {

        // WebMoney
        when(webmoney.setTransactionId(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setRemoteIp(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setUsage(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setCurrency(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setAmount(isA(BigDecimal.class))).thenReturn(webmoney);
        when(webmoney.setCustomerEmail(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setCustomerPhone(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setIsPayout(isA(Boolean.class))).thenReturn(webmoney);
        when(webmoney.setCustomerAccount(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setReturnSuccessUrl(isA(URL.class))).thenReturn(webmoney);
        when(webmoney.setReturnFailureUrl(isA(URL.class))).thenReturn(webmoney);
        when(webmoney.setBillingPrimaryAddress(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setBillingSecondaryAddress(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setBillingZipCode(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setBillingFirstname(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setBillingLastname(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setBillingCity(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setBillingCountry(isA(String.class))).thenReturn(webmoney);
        when(webmoney.setBillingState(isA(String.class))).thenReturn(webmoney);

        assertEquals(webmoney.setTransactionId(uidWebmoney).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                webmoney);
        assertEquals(webmoney.setCurrency(Currency.USD.getCurrency()).setAmount(new BigDecimal("2.00")), webmoney);
        assertEquals(webmoney.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), webmoney);
        assertEquals(webmoney.setIsPayout(true), webmoney);
        assertEquals(webmoney.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), webmoney);
        assertEquals(webmoney.setCustomerAccount("118221674199"), webmoney);
        assertEquals(webmoney.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE"), webmoney);

        verify(webmoney).setTransactionId(uidWebmoney);
        verify(webmoney).setRemoteIp("82.137.112.202");
        verify(webmoney).setUsage("TICKETS");
        verify(webmoney).setCurrency(Currency.USD.getCurrency());
        verify(webmoney).setAmount(new BigDecimal("2.00"));
        verify(webmoney).setCustomerEmail("john@example.com");
        verify(webmoney).setCustomerPhone("+55555555");
        verify(webmoney).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(webmoney).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(webmoney).setIsPayout(true);
        verify(webmoney).setCustomerAccount("118221674199");
        verify(webmoney).setBillingPrimaryAddress("Berlin");
        verify(webmoney).setBillingSecondaryAddress("Berlin");
        verify(webmoney).setBillingFirstname("Plamen");
        verify(webmoney).setBillingLastname("Petrov");
        verify(webmoney).setBillingCity("Berlin");
        verify(webmoney).setBillingCountry(Country.Germany.getCode());
        verify(webmoney).setBillingZipCode("M4B1B3");
        verify(webmoney).setBillingState("BE");
        verifyNoMoreInteractions(webmoney);

        verifyWebmoneyExecute();
    }

    @Test(expected = ApiException.class)
    public void testWebMoneyWithMissingParams() {

        clearRequiredParams();

        assertNull(webmoney.setBillingCountry(null));
        verify(webmoney).setBillingCountry(null);
        verifyNoMoreInteractions(webmoney);

        verifyWebmoneyExecute();
    }

    @Test
    public void testNeteller() throws MalformedURLException {

        // Neteller
        when(neteller.setTransactionId(isA(String.class))).thenReturn(neteller);
        when(neteller.setRemoteIp(isA(String.class))).thenReturn(neteller);
        when(neteller.setUsage(isA(String.class))).thenReturn(neteller);
        when(neteller.setCurrency(isA(String.class))).thenReturn(neteller);
        when(neteller.setAmount(isA(BigDecimal.class))).thenReturn(neteller);
        when(neteller.setCustomerEmail(isA(String.class))).thenReturn(neteller);
        when(neteller.setCustomerPhone(isA(String.class))).thenReturn(neteller);
        when(neteller.setReturnSuccessUrl(isA(URL.class))).thenReturn(neteller);
        when(neteller.setReturnFailureUrl(isA(URL.class))).thenReturn(neteller);
        when(neteller.setCustomerAccount(isA(String.class))).thenReturn(neteller);
        when(neteller.setAccountPassword(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingPrimaryAddress(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingSecondaryAddress(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingZipCode(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingFirstname(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingLastname(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingCity(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingCountry(isA(String.class))).thenReturn(neteller);
        when(neteller.setBillingState(isA(String.class))).thenReturn(neteller);

        assertEquals(neteller.setTransactionId(uidNeteller).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
                neteller);
        assertEquals(neteller.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")), neteller);
        assertEquals(neteller.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), neteller);
        assertEquals(neteller.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")), neteller);
        assertEquals(neteller.setCustomerAccount("453501020503").setAccountPassword("908379"), neteller);
        assertEquals(neteller.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE"), neteller);

        verify(neteller).setTransactionId(uidNeteller);
        verify(neteller).setRemoteIp("82.137.112.202");
        verify(neteller).setUsage("TICKETS");
        verify(neteller).setCurrency(Currency.EUR.getCurrency());
        verify(neteller).setAmount(new BigDecimal("2.00"));
        verify(neteller).setCustomerEmail("john@example.com");
        verify(neteller).setCustomerPhone("+55555555");
        verify(neteller).setReturnSuccessUrl(new URL("http://www.example.com/success"));
        verify(neteller).setReturnFailureUrl(new URL("http://www.example.com/failure"));
        verify(neteller).setCustomerAccount("453501020503");
        verify(neteller).setAccountPassword("908379");
        verify(neteller).setBillingPrimaryAddress("Berlin");
        verify(neteller).setBillingSecondaryAddress("Berlin");
        verify(neteller).setBillingFirstname("Plamen");
        verify(neteller).setBillingLastname("Petrov");
        verify(neteller).setBillingCity("Berlin");
        verify(neteller).setBillingCountry(Country.Germany.getCode());
        verify(neteller).setBillingZipCode("M4B1B3");
        verify(neteller).setBillingState("BE");
        verifyNoMoreInteractions(neteller);

        verifyNetellerExecute();

        neteller.setTransactionId(uidNeteller).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        neteller.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        neteller.setCustomerEmail("john@example.com").setCustomerPhone("+55555555");
        neteller.setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure"));
        neteller.setCustomerAccount("453501020503").setAccountPassword("908379");

        neteller.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
                .setBillingFirstname("Plamen").setBillingLastname("Petrov")
                .setBillingCity("Berlin").setBillingCountry(Country.Germany.getCode())
                .setBillingZipCode("M4B1B3").setBillingState("BE");
    }

    @Test(expected = ApiException.class)
    public void testNetellerWithMissingParams() {

        clearRequiredParams();

        assertNull(neteller.setBillingCountry(null));
        verify(neteller).setBillingCountry(null);
        verifyNoMoreInteractions(neteller);

        verifyNetellerExecute();
    }
}