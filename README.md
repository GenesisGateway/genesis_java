Genesis Java
===========

[![Build Status](https://img.shields.io/travis/GenesisGateway/genesis_java.svg?style=flat)](https://travis-ci.org/GenesisGateway/genesis_java)
[![Maven Central](https://central.sonatype.com/artifact/com.emerchantpay.gateway/genesis-java/)](https://central.sonatype.com/artifact/com.emerchantpay.gateway/genesis-java/)
[![Software License](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](LICENSE)

Overview
--------

Client Library for processing payments through Genesis Payment Processing Gateway. Its highly recommended to checkout "Genesis Payment Gateway API Documentation" first, in order to get an overview of Genesis's Payment Gateway API and functionality.

Requirements
------------

* JDK >= 1.8
* Maven >= 3.3.9

Installation and Setup
------------

* clone or [download](https://github.com/GenesisGateway/genesis_java/archive/master.zip) this repo

```sh
git clone http://github.com/GenesisGateway/genesis_java genesis_java
cd genesis_java
```
* mvn clean install
* Add this to your pom.xml file:
```xml
<dependency>
        <groupId>com.emerchantpay.gateway</groupId>
        <artifactId>genesis-java</artifactId>
        <version>1.18.7</version>
</dependency>
```

Example
------------------

```
import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.Configuration;

public class GenesisExample {
    public static void main() {
        // Create configuration
        Configuration configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);

        configuration.setUsername("SET_YOUR_USERNAME");
        configuration.setPassword("SET_YOUR_PASSWORD");
        configuration.setToken("SET_YOUR_TOKEN");

        String uniqueId = new StringUtils().generateUID();

        AuthorizeRequest authorize = new AuthorizeRequest();

        authorize.setTransactionId(uniqueId)
                .setUsage("40208 concert tickets")
                .setRemoteIp("245.253.2.12");
        authorize.setGaming(true);

        authorize.setCurrency("USD").setAmount(new BigDecimal("50.00"));

        authorize.setCardHolder("Emil Example")
                .setCardNumber("4200000000000000")
                .setExpirationMonth("01")
                .setExpirationYear("2020")
                .setCvv("123");

        authorize.setBillingFirstname("Travis")
                .setBillingLastname("Pastrana")
                .setBillingPrimaryAddress("Muster Str. 12")
                .setBillingZipCode("10178")
                .setBillingCity("Los Angeles")
                .setBillingState("CA").setBillingCountry("US");

        GenesisClient client = new GenesisClient(configuration, authorize);
        client.debugMode(true);
        client.execute();

        // Parse Payment result
        TransactionResult<? extends Transaction> result = client.getTransaction().getRequest();
        System.out.println("Transaction Id: " + result.getTransaction().getTransactionId());
    }
}
```

Smart Routing
-------------------

The Smart Routing API is a higher-level abstraction that allows for simpler and more efficient gateway Processing API integration. 
It does not require the terminal token. This by itself minimizes the need for complex customer-level manual routing to 
terminals set up on the gateway platform configuration layer.

By default the Smart Router is disabled. Contact your account manager to use the functionality.

Smart Router can be enabled as follows:
```java
// Enable smart routing globally
Configuration configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);
configuration.setForceSmartRouting(true);
// Enable smart routing per request
AuthorizeRequest authorize = new AuthorizeRequest();
authorize.setUseSmartRouting(true);
```

Notifications
-------------

```
import com.emerchantpay.gateway.NotificationGateway;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.util.Configuration;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationExample {
    public static void main() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Create configuration
        Configuration configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);

        configuration.setUsername("SET_YOUR_USERNAME");
        configuration.setPassword("SET_YOUR_PASSWORD");
        configuration.setToken("SET_YOUR_TOKEN");

        Map<String, String> map = new HashMap<String, String>();

        map.put("transaction_id", "82803B4C-70CC-43BD-8B21-FD0395285B40");
        map.put("unique_id", "fc6c3c8c0219730c7a099eaa540f70dc");
        map.put("signature", "3070d7030806a3763fcae73923d9c2da0898dd19");
        map.put("transaction_type", "sale3d");
        map.put("terminal_token", configuration.getToken());
        map.put("status", "approved");
        map.put("amount", "500");
        map.put("eci", "05");
        map.put("avs_response_code", "5I");
        map.put("avs_response_text", "Response+provided+by+issuer+processor%3B+ Address+information+not+verified");

        NotificationGateway notification = new NotificationGateway(configuration, map);

        // Execute Reconcile request
        notification.initReconciliation();

        // Get status of Reconcile response
        // String status = notification.getReconcilation().getStatus();

        // Generate the Gateway response
        notification.generateResponse();

        // Get Gateway response
        String response = notification.getResponse().toXML();
        System.out.println(response);

        // Get List with Response params
        List<Map.Entry<String,Object>> responseParamsList = notification.getResponseParams();

        for (Map.Entry<String,Object> param: responseParamsList) {
           System.out.print(param.getKey() + "=" + param.getValue());
        }

        // Get unique id param from Response
        String uniqueId = notification.getResponseUniqueId();
        System.out.println(uniqueId);
    }
}
```

Endpoints
---------

The current version supports two endpoints: ```ECOMPROCESSING``` and ```EMERCHANTPAY```

Proxy configuration
-------------------

Proxy configuration is optional. You can use proxy host domain name or IP address and proxy port number.

```java
// Create proxy configuration
GenesisClient client = new GenesisClient(configuration, request);
client.setProxyHost("proxy_host");
client.setProxyPort(8080);
client.debugMode(true);
client.execute();
```

Request types
-------------

You can use the following request types to initialize the Genesis client:

```text
// Generic transaction operations
api.requests.financial.CaptureRequest
api.requests.financial.RefundRequest
api.requests.financial.VoidRequest

// Alternative Payment Methods transactions
api.requests.financial.apm.CashURequest
api.requests.financial.apm.crypto.BitPayPayoutRequest
api.requests.financial.apm.crypto.BitPaySaleRequest
api.requests.financial.apm.EarthportRequest
api.requests.financial.apm.NeosurfRequest
api.requests.financial.apm.P24Request
api.requests.financial.apm.PayPalExpressRequest
api.requests.financial.apm.PaySafeCardRequest
api.requests.financial.apm.POLiRequest
api.requests.financial.apm.PProRequest
api.requests.financial.apm.SofortRequest
api.requests.financial.apm.TransferToPayoutRequest
api.requests.financial.apm.TrustlySaleRequest
api.requests.financial.card.TarjetaShoppingRequest

// PayByVouchers transactions
api.requests.financial.pbv.PBVYeePayRequest

// Credit Cards transactions
api.requests.financial.card.AuthorizeRequest
api.requests.financial.card.Authorize3DRequest
api.requests.financial.card.BancontactRequest
api.requests.financial.card.CreditRequest
api.requests.financial.card.PayoutRequest
api.requests.financial.card.SaleRequest
api.requests.financial.card.Sale3DRequest

//South American Card transactions
api.requests.financial.card.southamerican.ArgencardRequest
api.requests.financial.card.southamerican.AuraRequest
api.requests.financial.card.southamerican.CabalRequest
api.requests.financial.card.southamerican.CencosudRequest
api.requests.financial.card.southamerican.EloRequest
api.requests.financial.card.southamerican.NaranjaRequest
api.requests.financial.card.southamerican.NativaRequest
api.requests.financial.card.southamerican.TarjetaShoppingRequest

//South American Cash Payment transactions
api.requests.financial.cash.southamerican.BalotoRequest
api.requests.financial.cash.southamerican.BancodeOccidenteRequest
api.requests.financial.cash.southamerican.BoletoRequest
api.requests.financial.cash.southamerican.EfectyRequest
api.requests.financial.cash.southamerican.OXXORequest
api.requests.financial.cash.southamerican.PagoFacilRequest
api.requests.financial.cash.southamerican.RedpagosRequest

//Sepa Direct Debit transactions
api.requests.financial.sdd.SDDSaleRequest
api.requests.financial.sct.SCTPayoutRequest
api.requests.financial.sdd.SDDRefundRequest
api.requests.financial.sdd.SDDInitRecurringSaleRequest
api.requests.financial.sdd.SDDRecurringSaleRequest

//Online Banking Payments
api.requests.financial.apm.BankPayoutRequest
api.requests.financial.apm.CitadelPayInRequest
api.requests.financial.apm.CitadelPayOutRequest
api.requests.financial.oBeP.AlipayRequest
api.requests.financial.oBeP.IDealRequest
api.requests.financial.oBeP.IDebitPayInRequest
api.requests.financial.oBeP.IDebitPayOutRequest
api.requests.financial.oBeP.InstaDebitPayInRequest
api.requests.financial.oBeP.MultibancoRequest
api.requests.financial.oBeP.MyBankRequest
api.requests.financial.oBeP.OnlineBankingRequest
api.requests.financial.oBeP.PaySecPayoutRequest
api.requests.financial.oBeP.PaySecRequest
api.requests.financial.oBeP.PayURequest
api.requests.financial.oBeP.RPNPayoutRequest
api.requests.financial.oBeP.RPNRequest
api.requests.financial.oBeP.UPIRequest
api.requests.financial.oBeP.WechatRequest

// Electronic Wallets transactions
api.requests.financial.wallets.eZeeWalletRequest
api.requests.financial.wallets.NetellerRequest
api.requests.financial.wallets.WebMoneyRequest

// Generic (Non-Financial) requests
api.requests.nonfinancial.AccountVerificationRequest
api.requests.nonfinancial.BlacklistRequest

// Chargeback information request
api.requests.nonfinancial.fraud.ChargebackByDateRequest
api.requests.nonfinancial.fraud.ChargebackRequest

// SAFE/TC40 Report
api.requests.nonfinancial.fraud.ReportsByDateRequest
api.requests.nonfinancial.fraud.ReportsRequest

// Retrieval request
api.requests.nonfinancial.fraud.RetrievalByDateRequest
api.requests.nonfinancial.fraud.RetrievalRequest

// Reconcile requests
api.requests.nonfinancial.reconcile.ReconcileByDate
api.requests.nonfinancial.reconcile.ReconcileRequest

// Get ABN iDEAL supported banks
api.requests.nonfinancial.retrieve.AbnIDealBanksRetrieveRequest

// Web Payment Form (Checkout) requests
api.requests.wpf.WPFCreateRequest
api.requests.wpf.WPFReconcileRequest

// Gift Card requests
api.requests.financial.giftcards.FashionchequeRequest
api.requests.financial.giftcards.IntersolveRequest
api.requests.financial.giftcards.TCSRequest

//Mobile Payments
api.requests.financial.mobile.ApplePayRequest
api.requests.financial.mobile.GooglePayRequest
api.requests.financial.mobile.AfricanMobilePayoutRequest
api.requests.financial.mobile.AfricanMobileSaleRequest

//South American oBeP requests
api.requests.financial.oBeP.southamerican.BancodoBrasilRequest
api.requests.financial.oBeP.southamerican.BancomerRequest
api.requests.financial.oBeP.southamerican.BradescoRequest
api.requests.financial.oBeP.southamerican.DaviviendaRequest
api.requests.financial.oBeP.southamerican.ItauRequest
api.requests.financial.oBeP.southamerican.PSERequest
api.requests.financial.oBeP.southamerican.RapiPagoRequest
api.requests.financial.oBeP.southamerican.SantanderRequest
api.requests.financial.oBeP.southamerican.WebpayRequest

//Preauthorizations requests
api.requests.financial.preauthorization.IncrementalAuthorizeRequest
api.requests.financial.preauthorization.PartialReversalRequest

// Consumer API
api.requests.nonfinancial.consumer.CreateConsumerRequest
api.requests.nonfinancial.consumer.RetrieveConsumerRequest
api.requests.nonfinancial.consumer.EnableConsumerRequest
api.requests.nonfinancial.consumer.DisableConsumerRequest
api.requests.nonfinancial.consumer.UpdateConsumerRequest
api.requests.nonfinancial.consumer.GetConsumerCardsRequest
```


```java
// Consumer API example

// Create configuration
Configuration configuration = new Configuration(Environments.STAGING,
    Endpoints.EMERCHANTPAY,
    ConsumerEndpoints.RETRIEVE_CONSUMER,
    ConsumerEndpoints.CONSUMER_API_VERSION);
configuration.setUsername("SET_YOUR_USERNAME");
configuration.setPassword("SET_YOUR_PASSWORD");

RetrieveConsumerRequest retrieveConsumer = new RetrieveConsumerRequest();
retrieveConsumer.setConsumerId("123456");
retrieveConsumer.setEmail("john@example.com");

GenesisClient client = new GenesisClient(configuration, retrieveConsumer);
client.debugMode(true);
client.execute();

// Parse Consumer result
ConsumerResult<? extends Consumer> result = client.getConsumer().getRequest();
String consumerId = result.getConsumer().getConsumerId();
System.out.println("Consumer Id: " + consumerId);
```


```java
// FX API example

// Create configuration
Configuration configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY, FXEndpoints.FX_TIERS, FXEndpoints.FX_API_VERSION);

configuration.setUsername("SET_YOUR_USERNAME");
configuration.setPassword("SET_YOUR_PASSWORD");

FXSearchRateRequest fxSearchRate = new FXSearchRateRequest(configuration);
fxSearchRate.setTierId("TIER_ID")
        .setTargetCurrency("USD")
        .setSourceCurrency("EUR");
fxSearchRate.execute();
System.out.println(fxSearchRate.getRate().getTradingRate());
```

```java
// SCA Check example
// Create configuration
Configuration configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY, Endpoints.SCA_CHECKER_API_VERSION);

configuration.setUsername("SET_YOUR_USERNAME");
configuration.setPassword("SET_YOUR_PASSWORD");

ScaCheckerRequest scaCheckRequest = new ScaCheckerRequest(configuration);

scaCheckRequest.setCardNumber("4200000000000000")
        .setMit(false)
        .setMoto(false)
        .setRecurring(false)
        .setTransactionAmount(new BigDecimal("2.00"))
        .setTransactionCurrency("EUR");

scaCheckRequest.execute();
System.out.println("Response: " + scaCheckRequest.getResponse().getDocument());
```

```java
// 3DS v2 example

Sale3DRequest sale3dsV2Request = new Sale3DRequest();
//Set Sale3D and 3DS v2 attributes. Please refer documentation for more details.

GenesisClient client = new GenesisClient(configuration, sale3dsV2Request);
client.debugMode(true);
client.execute();
//Retrieve Sale 3DS v2 transaction response
Transaction sale3dsV2TrxResponse = client.getTransaction().getRequest().getTransaction();

//Asynchronous 3 D Sv2 Frictionless
System.out.println("Frictionless Response Status: " + sale3dsV2TrxResponse.getStatus());// status should be approved
//Asynchronous 3 D Sv2 Challenge
System.out.println("Challenge Response Status: " + sale3dsV2TrxResponse.getStatus());// status should be pending_async
System.out.println("Challenge Response redirect URL: " + sale3dsV2TrxResponse.getRedirectUrl());//redirect URL should be present
System.out.println("Challenge Response redirect URL type: " + sale3dsV2TrxResponse.getRedirectUrlType());//should be 3ds_v2_challenge
//Asynchronous With 3 Ds Method
System.out.println("With 3 Ds Method Response Status: " + sale3dsV2TrxResponse.getStatus());// status should be pending_async
System.out.println("With 3 Ds Method Response threeds method URL: " + sale3dsV2TrxResponse.getThreedsMethodUrl());//three DS method URL should be present
System.out.println("With 3 Ds Method Response threeds method continue URL: " + sale3dsV2TrxResponse.getThreedsMethodContinueUrl());//three DS method continue URL should be present

//Continue request
ThreedsV2ContinueRequest continue3dsV2Request = new ThreedsV2ContinueRequest(sale3dsV2TrxResponse, configuration);
continue3dsV2Request.execute();
//Retrieve continue response
Transaction continue3dsV2Response = continue3dsV2Request.getTransactionResponse();
//Frictionless With 3 Ds Method
System.out.println("Continue Method Frictionless Response Status: " + continue3dsV2Response.getStatus());//status should be approved
//Challenge With 3 Ds Method Request
System.out.println("Continue Method with Challenge Response Status: " + continue3dsV2Response.getStatus());//status should be pending_async
System.out.println("Continue Method with Challenge Response redirect URL: " + continue3dsV2Response.getRedirectUrl());//redirect URL should be present
System.out.println("Continue Method with Challenge Response redirect URL type: " + continue3dsV2Response.getRedirectUrlType());//should be 3ds_v2_challenge
```

More information about each one of the request types can be found in the Genesis API Documentation and the Wiki

Running Tests
--------------

* mvn test