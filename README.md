Genesis Java
===========

[![Build Status](https://img.shields.io/travis/GenesisGateway/genesis_java.svg?style=flat)](https://travis-ci.org/GenesisGateway/genesis_java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.emerchantpay.gateway/genesis-java/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.emerchantpay.gateway/genesis-java/)
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
        <version>1.1.0</version>
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

        GenesisClient client = new GenesisClient(configuration);

        AuthorizeRequest  authorize = new AuthorizeRequest();

        authorize.setTransactionId("43671")
                .setUsage("40208 concert tickets")
                .setGaming(true)
                .setRemoteIp("245.253.2.12").setCurrency("USD")
                .setAmount(new BigDecimal(50.00))
                .setCardholder("Emil Example")
                .setCardNumber("4200000000000000")
                .setExpirationMonth("01")
                .setExpirationYear("2020")
                .setCvv("123")
                .billingAddress()
                .setFirstname("Travis")
                .setLastname("Pastrana")
                .setAddress1("Muster Str. 12")
                .setZipCode("10178")
                .setCity("Los Angeles")
                .setState("CA")
                .setCountry("USA").done();

        authorize.execute(configuration);

        // Parse Payment result
        TransactionResult<? extends Transaction> result = client.getTransaction().getAuthorize(authorize);
        System.out("Transaction Id: " + result.getTransaction.getTransactionId());
    }
}
```


Endpoints
---------

The current version supports two endpoints: ```ECOMPROCESSING``` and ```EMERCHANTPAY```

Request types
-------------

You can use the following request types to initialize the Genesis client:

```text
// Generic transaction operations
api.requests.financial.CaptureRequest
api.requests.financial.RefundRequest
api.requests.financial.VoidRequest

// Alternative Payment Methods transactions
api.requests.financial.apm.AbnIDealRequest
api.requests.financial.apm.CashURequest
api.requests.financial.apm.InPayRequest
api.requests.financial.apm.P24Request
api.requests.financial.apm.PaySafeCardRequest
api.requests.financial.apm.POLiRequest
api.requests.financial.apm.PProRequest
api.requests.financial.apm.SofortRequest
api.requests.financial.apm.TrustlySaleRequest
api.requests.financial.apm.TrustlyWithdrawalRequest
api.requests.financial.apm.PayPalExpressRequest
api.requests.financial.apm.EarthportRequest

// PayByVouchers transactions
api.requests.financial.pbv.PBVSaleRequest
api.requests.financial.pbv.PBVYeePayRequest

// Credit Cards transactions
api.requests.financial.card.AuthorizeRequest
api.requests.financial.card.Authorize3DRequest
api.requests.financial.card.CreditRequesst
api.requests.financial.card.PayoutRequest
api.requests.financial.card.SaleRequest
api.requests.financial.card.Sale3DRequest
api.requests.financial.card.recurring.InitRecurringSaleRequest
api.requests.financial.card.recurring.InitRecurringSale3DRequest
api.requests.financial.card.recurring.RecurringSaleRequest

//Sepa Direct Debit transactions
api.requests.financial.sdd.SDDSaleRequest
api.requests.financial.sct.SCTPayoutRequest
api.requests.financial.sdd.SDDRefundRequest
api.requests.financial.sdd.SDDInitRecurringSaleRequest
api.requests.financial.sdd.SDDRecurringSaleRequest

//Online Banking Payments
api.requests.financial.apm.CitadelPayInRequest
api.requests.financial.apm.CitadelPayOutRequest
api.requests.financial.oBeP.InstadebitPayInRequest
api.requests.financial.oBeP.InstaDebitPayInRequest
api.requests.financial.oBeP.IDebitPayInRequest
api.requests.financial.oBeP.IDebitPayOutRequest
api.requests.financial.oBeP.AlipayRequest
api.requests.financial.oBeP.WechatRequest
api.requests.financial.oBeP.PaySecRequest
api.requests.financial.oBeP.PaySecPayoutRequest

// Electronic Wallets transactions
api.requests.financial.wallets.eZeeWalletRequest
api.requests.financial.wallets.NetellerRequest
api.requests.financial.wallets.WebMoneyRequest

// Generic (Non-Financial) requests
api.requests.nonfinancial.AVSRequest
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
```


More information about each one of the request types can be found in the Genesis API Documentation and the Wiki

Running Tests
--------------

* mvn test