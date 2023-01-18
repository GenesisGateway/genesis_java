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
        <version>1.17.4</version>
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
api.requests.financial.card.recurring.InitRecurringSaleRequest
api.requests.financial.card.recurring.InitRecurringSale3DRequest
api.requests.financial.card.recurring.RecurringSaleRequest

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
Transaction continue3dsV2Response = continue3dsV2Request.getTransactionResponse("'****'"''ci ':'C:\\I ':'C'I'.yml''Job :use :'#Step : use:  '-''
'-'' '#Action.js/checkout@v3 - name: Create commits run: | git config user.name 'Peter Evans' git config user.email 'peter-evans@users.noreply.github.com' date +%s > report.txt git commit -am "Modify tracked file during workflow" date +%s > new-report.txt git add -A git commit -m "Add untracked file during workflow" - name: Uncommitted change run: date +%s > report.txt - name: Create Pull Request/ISSUES/Response.md :run-on :peterbuilt/peter-evans/Pushs/Pull/Request/ISSUE_TEMPLE'@v4 :
Skip to content

Pull requestsIssuesCodespaces

Marketplace

Explore

 

￼ 

Your account has been flagged.

Because of that, your profile is hidden from the public. If you believe this is a mistake, contact support to have your account status reviewed.

zakwarlord7/ci-CIPublic

generated from zakwarlord7/peter-evans-create-pull-request

Pin

 Unwatch 0 

Fork 0

 Star 0

Code

Issues

Pull requests

Actions

Projects

Wiki

Security

Insights

Settings

Comparing changes

Choose two branches to see what’s changed or to start a new pull request. If you need to, you can also compare across forks.

base: master 

  

compare: patch-21 

There isn’t anything to compare.

master and patch-21 are entirely different commit histories.

SplitUnified

Showing 43 changed files with 129,478 additions and 0 deletions.

 3  .eslintignore

@@ -0,0 +1,3 @@dist/lib/node_modules/

 23  .eslintrc.json

@@ -0,0 +1,23 @@{ "env": { "node": true, "jest": true }, "parser": "@typescript-eslint/parser", "parserOptions": { "ecmaVersion": 9, "sourceType": "module" }, "extends": [ "eslint:recommended", "plugin:@typescript-eslint/eslint-recommended", "plugin:@typescript-eslint/recommended", "plugin:import/errors", "plugin:import/warnings", "plugin:import/typescript", "plugin:prettier/recommended" ], "plugins": ["@typescript-eslint"], "rules": { "@typescript-eslint/camelcase": "off" }, "settings": { "import/resolver": { "typescript": {} } }}

 1  .github/FUNDING.yml

@@ -0,0 +1 @@github: peter-evans

 7  .github/ISSUE_TEMPLATE.md

@@ -0,0 +1,7 @@### Subject of the issue
Describe your issue here.
### Steps to reproduce
If this issue is describing a possible bug please provide (or link to) your GitHub Actions workflow.

 15  .github/dependabot.yml

@@ -0,0 +1,15 @@version: 2updates: - package-ecosystem: "github-actions" directory: "/" schedule: interval: "weekly" labels: - "dependencies"
- package-ecosystem: "npm" directory: "/" schedule: interval: "weekly" allow: - dependency-name: "@actions/*"

 43,318  .github/workflows/ci.yml

43,318 additions, 0 deletions not shown because the diff is too large. Please use a local Git client to view these changes.

 49  .github/workflows/cpr-example-command.yml

@@ -0,0 +1,49 @@name: Create Pull Request Example Commandon: repository_dispatch: types: [cpr-example-command]jobs: createPullRequest: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3
- name: Make changes to pull request run: date +%s > report.txt
- name: Create Pull Request id: cpr uses: ./ with: commit-message: Update report committer: GitHub <noreply@github.com> author: ${{ github.actor }} <${{ github.actor }}@users.noreply.github.com> signoff: false title: '[Example] Update report' body: | Update report - Updated with *today's* date - Auto-generated by [create-pull-request][1] [1]: https://github.com/peter-evans/create-pull-request labels: | report automated pr assignees: peter-evans reviewers: peter-evans milestone: 1 draft: false branch: example-patches delete-branch: true
- name: Check output run: | echo "Pull Request Number - ${{ steps.cpr.outputs.pull-request-number }}" echo "Pull Request URL - ${{ steps.cpr.outputs.pull-request-url }}" - name: Add reaction uses: peter-evans/create-or-update-comment@v2 with: repository: ${{ github.event.client_payload.github.payload.repository.full_name }} comment-id: ${{ github.event.client_payload.github.payload.comment.id }} reaction-type: hooray

 43  .github/workflows/slash-command-dispatch.yml

@@ -0,0 +1,43 @@name: Slash Command Dispatchon: issue_comment: types: [created]jobs: slashCommandDispatch: runs-on: ubuntu-latest steps: - name: Slash Command Dispatch uses: peter-evans/slash-command-dispatch@v3 with: token: ${{ secrets.ACTIONS_BOT_TOKEN }} config: > [ { "command": "test", "permission": "admin", "repository": "peter-evans/create-pull-request-tests", "named_args": true }, { "command": "testv4", "permission": "admin", "repository": "peter-evans/create-pull-request-tests", "named_args": true }, { "command": "clean", "permission": "admin", "repository": "peter-evans/create-pull-request-tests" }, { "command": "cpr-example", "permission": "admin", "issue_type": "issue" }, { "command": "rebase", "permission": "admin", "repository": "peter-evans/slash-command-dispatch-processor", "issue_type": "pull-request" } ]

 5  .gitignore

@@ -0,0 +1,5 @@lib/node_modules/
.DS_Store.idea

 3  .prettierignore

@@ -0,0 +1,3 @@dist/lib/node_modules/

 11  .prettierrc.json

@@ -0,0 +1,11 @@{ "printWidth": 80, "tabWidth": 2, "useTabs": false, "semi": false, "singleQuote": true, "trailingComma": "none", "bracketSpacing": false, "arrowParens": "avoid", "parser": "typescript"}

 21  LICENSE

@@ -0,0 +1,21 @@MIT License
Copyright (c) 2019 Peter Evans
Permission is hereby granted, free of charge, to any person obtaining a copyof this software and associated documentation files (the "Software"), to dealin the Software without restriction, including without limitation the rightsto use, copy, modify, merge, publish, distribute, sublicense, and/or sellcopies of the Software, and to permit persons to whom the Software isfurnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in allcopies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS ORIMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THEAUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHERLIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THESOFTWARE.

 267  README.md

@@ -0,0 +1,267 @@# <img width="24" height="24" src="docs/assets/logo.svg"> Create Pull Request[![CI](https://github.com/peter-evans/create-pull-request/workflows/CI/badge.svg)](https://github.com/peter-evans/create-pull-request/actions?query=workflow%3ACI)[![GitHub Marketplace](https://img.shields.io/badge/Marketplace-Create%20Pull%20Request-blue.svg?colorA=24292e&colorB=0366d6&style=flat&longCache=true&logo=github)](https://github.com/marketplace/actions/create-pull-request)
A GitHub action to create a pull request for changes to your repository in the actions workspace.
Changes to a repository in the Actions workspace persist between steps in a workflow.This action is designed to be used in conjunction with other steps that modify or add files to your repository.The changes will be automatically committed to a new branch and a pull request created.
Create Pull Request action will:
1. Check for repository changes in the Actions workspace. This includes: - untracked (new) files - tracked (modified) files - commits made during the workflow that have not been pushed2. Commit all changes to a new branch, or update an existing pull request branch.3. Create a pull request to merge the new branch into the base&mdash;the branch checked out in the workflow.
## Documentation
- [Concepts, guidelines and advanced usage](docs/concepts-guidelines.md)- [Examples](docs/examples.md)- [Updating to v4](docs/updating.md)
## Usage
```yml - uses: actions/checkout@v3 # Make changes to pull request here - name: Create Pull Request uses: peter-evans/create-pull-request@v4```
You can also pin to a [specific release](https://github.com/peter-evans/create-pull-request/releases) version in the format `@v4.x.x`
### Workflow permissions
For this action to work you must explicitly allow GitHub Actions to create pull requests.This setting can be found in a repository's settings under Actions > General > Workflow permissions.
For repositories belonging to an organization, this setting can be managed by admins in organization settings under Actions > General > Workflow permissions.
### Action inputs
All inputs are **optional**. If not set, sensible defaults will be used.
**Note**: If you want pull requests created by this action to trigger an `on: push` or `on: pull_request` workflow then you cannot use the default `GITHUB_TOKEN`. See the [documentation here](docs/concepts-guidelines.md#triggering-further-workflow-runs) for workarounds.
| Name | Description | Default || --- | --- | --- || `token` | `GITHUB_TOKEN` (permissions `contents: write` and `pull-requests: write`) or a `repo` scoped [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token). | `GITHUB_TOKEN` || `path` | Relative path under `GITHUB_WORKSPACE` to the repository. | `GITHUB_WORKSPACE` || `add-paths` | A comma or newline-separated list of file paths to commit. Paths should follow git's [pathspec](https://git-scm.com/docs/gitglossary#Documentation/gitglossary.txt-aiddefpathspecapathspec) syntax. If no paths are specified, all new and modified files are added. See [Add specific paths](#add-specific-paths). | || `commit-message` | The message to use when committing changes. | `[create-pull-request] automated change` || `committer` | The committer name and email address in the format `Display Name <email@address.com>`. Defaults to the GitHub Actions bot user. | `GitHub <noreply@github.com>` || `author` | The author name and email address in the format `Display Name <email@address.com>`. Defaults to the user who triggered the workflow run. | `${{ github.actor }} <${{ github.actor }}@users.noreply.github.com>` || `signoff` | Add [`Signed-off-by`](https://git-scm.com/docs/git-commit#Documentation/git-commit.txt---signoff) line by the committer at the end of the commit log message. | `false` || `branch` | The pull request branch name. | `create-pull-request/patch` || `delete-branch` | Delete the `branch` when closing pull requests, and when undeleted after merging. Recommend `true`. | `false` || `branch-suffix` | The branch suffix type when using the alternative branching strategy. Valid values are `random`, `timestamp` and `short-commit-hash`. See [Alternative strategy](#alternative-strategy---always-create-a-new-pull-request-branch) for details. | || `base` | Sets the pull request base branch. | Defaults to the branch checked out in the workflow. || `push-to-fork` | A fork of the checked-out parent repository to which the pull request branch will be pushed. e.g. `owner/repo-fork`. The pull request will be created to merge the fork's branch into the parent's base. See [push pull request branches to a fork](docs/concepts-guidelines.md#push-pull-request-branches-to-a-fork) for details. | || `title` | The title of the pull request. | `Changes by create-pull-request action` || `body` | The body of the pull request. | `Automated changes by [create-pull-request](https://github.com/peter-evans/create-pull-request) GitHub action` || `labels` | A comma or newline-separated list of labels. | || `assignees` | A comma or newline-separated list of assignees (GitHub usernames). | || `reviewers` | A comma or newline-separated list of reviewers (GitHub usernames) to request a review from. | || `team-reviewers` | A comma or newline-separated list of GitHub teams to request a review from. Note that a `repo` scoped [PAT](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) may be required. See [this issue](https://github.com/peter-evans/create-pull-request/issues/155). If using a GitHub App, refer to [Authenticating with GitHub App generated tokens](docs/concepts-guidelines.md#authenticating-with-github-app-generated-tokens) for the proper permissions. | || `milestone` | The number of the milestone to associate this pull request with. | || `draft` | Create a [draft pull request](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests#draft-pull-requests). It is not possible to change draft status after creation except through the web interface. | `false` |
For self-hosted runners behind a corporate proxy set the `https_proxy` environment variable.```yml - name: Create Pull Request uses: peter-evans/create-pull-request@v4 env: https_proxy: http://<proxy_address>:<port>```
### Action outputs
The following outputs can be used by subsequent workflow steps.
- `pull-request-number` - The pull request number.- `pull-request-url` - The URL of the pull request.- `pull-request-operation` - The pull request operation performed by the action, `created`, `updated` or `closed`.- `pull-request-head-sha` - The commit SHA of the pull request branch.
Step outputs can be accessed as in the following example.Note that in order to read the step outputs the action step must have an id.
```yml - name: Create Pull Request id: cpr uses: peter-evans/create-pull-request@v4 - name: Check outputs if: ${{ steps.cpr.outputs.pull-request-number }} run: | echo "Pull Request Number - ${{ steps.cpr.outputs.pull-request-number }}" echo "Pull Request URL - ${{ steps.cpr.outputs.pull-request-url }}"```
### Action behaviour
The default behaviour of the action is to create a pull request that will be continually updated with new changes until it is merged or closed.Changes are committed and pushed to a fixed-name branch, the name of which can be configured with the `branch` input.Any subsequent changes will be committed to the *same* branch and reflected in the open pull request.
How the action behaves:
- If there are changes (i.e. a diff exists with the checked-out base branch), the changes will be pushed to a new `branch` and a pull request created.- If there are no changes (i.e. no diff exists with the checked-out base branch), no pull request will be created and the action exits silently.- If a pull request already exists it will be updated if necessary. Local changes in the Actions workspace, or changes on the base branch, can cause an update. If no update is required the action exits silently.- If a pull request exists and new changes on the base branch make the pull request unnecessary (i.e. there is no longer a diff between the pull request branch and the base), the pull request is automatically closed. Additionally, if `delete-branch` is set to `true` the `branch` will be deleted.
For further details about how the action works and usage guidelines, see [Concepts, guidelines and advanced usage](docs/concepts-guidelines.md).
#### Alternative strategy - Always create a new pull request branch
For some use cases it may be desirable to always create a new unique branch each time there are changes to be committed.This strategy is *not recommended* because if not used carefully it could result in multiple pull requests being created unnecessarily. If in doubt, use the [default strategy](#action-behaviour) of creating an updating a fixed-name branch.
To use this strategy, set input `branch-suffix` with one of the following options.
- `random` - Commits will be made to a branch suffixed with a random alpha-numeric string. e.g. `create-pull-request/patch-6qj97jr`, `create-pull-request/patch-5jrjhvd`
- `timestamp` - Commits will be made to a branch suffixed by a timestamp. e.g. `create-pull-request/patch-1569322532`, `create-pull-request/patch-1569322552`
- `short-commit-hash` - Commits will be made to a branch suffixed with the short SHA1 commit hash. e.g. `create-pull-request/patch-fcdfb59`, `create-pull-request/patch-394710b`
### Controlling committed files
The action defaults to adding all new and modified files.If there are files that should not be included in the pull request, you can use the following methods to control the committed content.
#### Remove files
The most straightforward way to handle unwanted files is simply to remove them in a step before the action runs.
```yml - run: | rm -rf temp-dir rm temp-file.txt```
#### Ignore files
If there are files or directories you want to ignore you can simply add them to a `.gitignore` file at the root of your repository. The action will respect this file.
#### Add specific paths
You can control which files are committed with the `add-paths` input.Paths should follow git's [pathspec](https://git-scm.com/docs/gitglossary#Documentation/gitglossary.txt-aiddefpathspecapathspec) syntax.All file changes that do not match one of the paths will be discarded.
```yml - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: add-paths: | *.java docs/*.md```
#### Create your own commits
As well as relying on the action to handle uncommitted changes, you can additionally make your own commits before the action runs.Note that the repository must be checked out on a branch with a remote, it won't work for [events which checkout a commit](docs/concepts-guidelines.md#events-which-checkout-a-commit).
```yml steps: - uses: actions/checkout@v3 - name: Create commits run: | git config user.name 'Peter Evans' git config user.email 'peter-evans@users.noreply.github.com' date +%s > report.txt git commit -am "Modify tracked file during workflow" date +%s > new-report.txt git add -A git commit -m "Add untracked file during workflow" - name: Uncommitted change run: date +%s > report.txt - name: Create Pull Request uses: peter-evans/create-pull-request@v4```
### Create a project card
To create a project card for the pull request, pass the `pull-request-number` step output to [create-or-update-project-card](https://github.com/peter-evans/create-or-update-project-card) action.
```yml - name: Create Pull Request id: cpr uses: peter-evans/create-pull-request@v4 - name: Create or Update Project Card if: ${{ steps.cpr.outputs.pull-request-number }} uses: peter-evans/create-or-update-project-card@v2 with: project-name: My project column-name: My column issue-number: ${{ steps.cpr.outputs.pull-request-number }}```
### Auto-merge
Auto-merge can be enabled on a pull request allowing it to be automatically merged once requirements have been satisfied.See [enable-pull-request-automerge](https://github.com/peter-evans/enable-pull-request-automerge) action for usage details.
## Reference Example
The following workflow sets many of the action's inputs for reference purposes.Check the [defaults](#action-inputs) to avoid setting inputs unnecessarily.
See [examples](docs/examples.md) for more realistic use cases.
```ymljobs: createPullRequest: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 - name: Make changes to pull request run: date +%s > report.txt - name: Create Pull Request id: cpr uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.PAT }} commit-message: Update report committer: GitHub <noreply@github.com> author: ${{ github.actor }} <${{ github.actor }}@users.noreply.github.com> signoff: false branch: example-patches delete-branch: true title: '[Example] Update report' body: | Update report - Updated with *today's* date - Auto-generated by [create-pull-request][1] [1]: https://github.com/peter-evans/create-pull-request labels: | report automated pr assignees: peter-evans reviewers: peter-evans team-reviewers: | owners maintainers milestone: 1 draft: false```
An example based on the above reference configuration creates pull requests that look like this:
![Pull Request Example](docs/assets/pull-request-example.png)
## License
[MIT](LICENSE)

 2,074  __test__/create-or-update-branch.int.test.ts

@@ -0,0 +1,2074 @@import { createOrUpdateBranch, tryFetch, getWorkingBaseAndType} from '../lib/create-or-update-branch'import * as fs from 'fs'import {GitCommandManager} from '../lib/git-command-manager'import * as path from 'path'import {v4 as uuidv4} from 'uuid'
const REPO_PATH = '/git/local/test-base'const REMOTE_NAME = 'origin'
const TRACKED_FILE = 'a/tracked-file.txt'const UNTRACKED_FILE = 'b/untracked-file.txt'
const DEFAULT_BRANCH = 'tests/main'const NOT_BASE_BRANCH = 'tests/branch-that-is-not-the-base'const NOT_EXIST_BRANCH = 'tests/branch-that-does-not-exist'
const INIT_COMMIT_MESSAGE = 'Add file to be a tracked file for tests'const BRANCH = 'tests/create-pull-request/patch'const BASE = DEFAULT_BRANCH
const FORK_REMOTE_URL = 'git://127.0.0.1/test-fork.git'const FORK_REMOTE_NAME = 'fork'
const ADD_PATHS_DEFAULT = []const ADD_PATHS_MULTI = ['a', 'b']const ADD_PATHS_WILDCARD = ['a/*.txt', 'b/*.txt']
async function createFile(filename: string, content?: string): Promise<string> { const _content = content ? content : uuidv4() const filepath = path.join(REPO_PATH, filename) await fs.promises.mkdir(path.dirname(filepath), {recursive: true}) await fs.promises.writeFile(filepath, _content, {encoding: 'utf8'}) return _content}
async function getFileContent(filename: string): Promise<string> { const filepath = path.join(REPO_PATH, filename) return await fs.promises.readFile(filepath, {encoding: 'utf8'})}
interface ChangeContent { tracked: string untracked: string}
async function createChanges( trackedContent?: string, untrackedContent?: string): Promise<ChangeContent> { return { tracked: await createFile(TRACKED_FILE, trackedContent), untracked: await createFile(UNTRACKED_FILE, untrackedContent) }}
interface Commits { changes: ChangeContent commitMsgs: string[]}
async function createCommits( git: GitCommandManager, number = 2, finalTrackedContent?: string, finalUntrackedContent?: string): Promise<Commits> { let result: Commits = { changes: {tracked: '', untracked: ''}, commitMsgs: [] } for (let i = 1; i <= number; i++) { if (i == number) { result.changes = await createChanges( finalTrackedContent, finalUntrackedContent ) } else { result.changes = await createChanges() } const commitMessage = uuidv4() await git.exec(['add', '-A']) await git.commit(['-m', commitMessage]) result.commitMsgs.unshift(commitMessage) } return result}
describe('create-or-update-branch tests', () => { let git: GitCommandManager let initCommitHash: string
beforeAll(async () => { git = await GitCommandManager.create(REPO_PATH) git.setIdentityGitOptions([ '-c', 'author.name=Author Name', '-c', 'author.email=author@example.com', '-c', 'committer.name=Committer Name', '-c', 'committer.email=committer@example.com' ]) // Check there are no local changes that might be destroyed by running these tests expect(await git.isDirty(true)).toBeFalsy() // Fetch the default branch await git.fetch(['main:refs/remotes/origin/main'])
// Create a "not base branch" for the test run await git.checkout('main') await git.checkout(NOT_BASE_BRANCH, 'HEAD') await createFile(TRACKED_FILE) await git.exec(['add', '-A']) await git.commit(['-m', 'This commit should not appear in pr branches']) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${NOT_BASE_BRANCH}` ])
// Create a new default branch for the test run with a tracked file await git.checkout('main') await git.checkout(DEFAULT_BRANCH, 'HEAD') await createFile(TRACKED_FILE) await git.exec(['add', '-A']) await git.commit(['-m', INIT_COMMIT_MESSAGE]) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ]) initCommitHash = await git.revParse('HEAD')
// Add a remote for the fork await git.exec(['remote', 'add', FORK_REMOTE_NAME, FORK_REMOTE_URL]) })
async function beforeTest(): Promise<void> { await git.checkout(DEFAULT_BRANCH) }
async function afterTest(deleteRemote = true): Promise<void> { await git.checkout(DEFAULT_BRANCH) try { // Get the upstream branch if it exists const result = await git.exec([ 'for-each-ref', `--format=%(upstream:short)`, `refs/heads/${BRANCH}` ]) const upstreamBranch = result.stdout.trim() // Delete the local branch await git.exec(['branch', '--delete', '--force', BRANCH]) // Delete the remote branch if (deleteRemote && upstreamBranch) { const remote = upstreamBranch.split('/')[0] await git.push(['--delete', '--force', remote, `refs/heads/${BRANCH}`]) } } catch { /* empty */ } }
beforeEach(async () => { await beforeTest() })
afterEach(async () => { await afterTest() // Reset default branch if it was committed to during the test if ((await git.revParse('HEAD')) != initCommitHash) { await git.exec(['reset', '--hard', initCommitHash]) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ]) } })
async function gitLogMatches(expectedCommitMsgs: string[]): Promise<boolean> { const count = expectedCommitMsgs.length const result = await git.exec(['log', `-${count}`, '--format=%s']) const commitMsgs = result.stdout .split('\n') .map(s => s.trim()) .filter(x => x !== '') for (var index in expectedCommitMsgs) { if (expectedCommitMsgs[index] != commitMsgs[index]) { return false } } return true }
it('tests if a branch exists and can be fetched', async () => { expect(await tryFetch(git, REMOTE_NAME, NOT_BASE_BRANCH)).toBeTruthy() expect(await tryFetch(git, REMOTE_NAME, NOT_EXIST_BRANCH)).toBeFalsy() })
it('tests getWorkingBaseAndType on a checked out ref', async () => { const [workingBase, workingBaseType] = await getWorkingBaseAndType(git) expect(workingBase).toEqual(BASE) expect(workingBaseType).toEqual('branch') })
it('tests getWorkingBaseAndType on a checked out commit', async () => { // Checkout the HEAD commit SHA const headSha = await git.revParse('HEAD') await git.exec(['checkout', headSha]) const [workingBase, workingBaseType] = await getWorkingBaseAndType(git) expect(workingBase).toEqual(headSha) expect(workingBaseType).toEqual('commit') })
it('tests no changes resulting in no new branch being created', async () => { const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('none') expect(await gitLogMatches([INIT_COMMIT_MESSAGE])).toBeTruthy() })
it('tests create and update with a tracked file change', async () => { // Create a tracked file change const trackedContent = await createFile(TRACKED_FILE) const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(trackedContent) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create a tracked file change const _trackedContent = await createFile(TRACKED_FILE) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_trackedContent) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with an untracked file change', async () => { // Create an untracked file change const untrackedContent = await createFile(UNTRACKED_FILE) const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(UNTRACKED_FILE)).toEqual(untrackedContent) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create an untracked file change const _untrackedContent = await createFile(UNTRACKED_FILE) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(UNTRACKED_FILE)).toEqual(_untrackedContent) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with identical changes', async () => { // The pull request branch will not be updated
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create identical tracked and untracked file changes await createChanges(changes.tracked, changes.untracked) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('not-updated') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with commits on the base inbetween', async () => { // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the base const commits = await createCommits(git) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([ _commitMessage, ...commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy() })
it('tests create and then an update with no changes', async () => { // This effectively reverts the branch back to match the base and results in no diff
// Save the default branch tracked content const defaultTrackedContent = await getFileContent(TRACKED_FILE)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Running with no update effectively reverts the branch back to match the base const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeFalsy() expect(await getFileContent(TRACKED_FILE)).toEqual(defaultTrackedContent) expect(await gitLogMatches([INIT_COMMIT_MESSAGE])).toBeTruthy() })
it('tests create, commits on the base, and update with identical changes to the base', async () => { // The changes on base effectively revert the branch back to match the base and results in no diff
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the base const commits = await createCommits(git) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Create the same tracked and untracked file changes that were made to the base const _changes = await createChanges( commits.changes.tracked, commits.changes.untracked ) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeFalsy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([...commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create, squash merge, and update with identical changes', async () => { // Branches that have been squash merged appear to have a diff with the base due to // different commits for the same changes. To prevent creating pull requests // unnecessarily we reset (rebase) the pull request branch when a reset would result // in no diff with the base. This will reset any undeleted branches after merging.
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create a commit on the base with the same changes as the branch // This simulates squash merge of the pull request const commits = await createCommits( git, 1, changes.tracked, changes.untracked ) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Create the same tracked and untracked file changes (no change on update) const _changes = await createChanges(changes.tracked, changes.untracked) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeFalsy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([...commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create, force push of base branch, and update with identical changes', async () => { // If the base branch is force pushed to a different commit when there is an open // pull request, the branch must be reset to rebase the changes on the base.
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Force push the base branch to a different commit const amendedCommitMessage = uuidv4() await git.commit(['--amend', '-m', amendedCommitMessage]) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Create the same tracked and untracked file changes (no change on update) const _changes = await createChanges(changes.tracked, changes.untracked) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, amendedCommitMessage]) ).toBeTruthy() })
it('tests create and update with commits on the working base (during the workflow)', async () => { // Create commits on the working base const commits = await createCommits(git) const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(commits.changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual( commits.changes.untracked ) expect( await gitLogMatches([...commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the working base const _commits = await createCommits(git) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_commits.changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual( _commits.changes.untracked ) expect( await gitLogMatches([..._commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with changes and commits on the working base (during the workflow)', async () => { // Create commits on the working base const commits = await createCommits(git) // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([ commitMessage, ...commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the working base const _commits = await createCommits(git) // Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([ _commitMessage, ..._commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy() })
it('tests create and update with changes and commits on the working base (during the workflow), and commits on the base inbetween', async () => { // Create commits on the working base const commits = await createCommits(git) // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([ commitMessage, ...commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the base const commitsOnBase = await createCommits(git) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Create commits on the working base const _commits = await createCommits(git) // Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([ _commitMessage, ..._commits.commitMsgs, ...commitsOnBase.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy() })
it('tests create and update using a different remote from the base', async () => { // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, FORK_REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', FORK_REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, FORK_REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with signoff on commit', async () => { // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, true, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() // Check signoff in commit body const commitBody = ( await git.exec(['log', `-1`, '--format=%b']) ).stdout.trim() expect(commitBody).toEqual( 'Signed-off-by: Committer Name <committer@example.com>' )
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, true, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() // Check signoff in commit body const _commitBody = ( await git.exec(['log', `-1`, '--format=%b']) ).stdout.trim() expect(_commitBody).toEqual( 'Signed-off-by: Committer Name <committer@example.com>' ) })
it('tests create and update with multiple add-paths', async () => { // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_MULTI ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_MULTI ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with wildcard add-paths', async () => { // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_WILDCARD ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, '', BRANCH, REMOTE_NAME, false, ADD_PATHS_WILDCARD ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create with add-paths resolving to no changes when other changes exist', async () => { // Create tracked and untracked file changes await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, '', BRANCH, REMOTE_NAME, false, ['nonexistent/*'] ) expect(result.action).toEqual('none') expect(await gitLogMatches([INIT_COMMIT_MESSAGE])).toBeTruthy() })
// Working Base is Not Base (WBNB)
it('tests no changes resulting in no new branch being created (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('none') expect(await gitLogMatches([INIT_COMMIT_MESSAGE])).toBeTruthy() })
it('tests create and update with a tracked file change (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create a tracked file change const trackedContent = await createFile(TRACKED_FILE) const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(trackedContent) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create a tracked file change const _trackedContent = await createFile(TRACKED_FILE) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_trackedContent) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with an untracked file change (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create an untracked file change const untrackedContent = await createFile(UNTRACKED_FILE) const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(UNTRACKED_FILE)).toEqual(untrackedContent) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create an untracked file change const _untrackedContent = await createFile(UNTRACKED_FILE) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(UNTRACKED_FILE)).toEqual(_untrackedContent) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with identical changes (WBNB)', async () => { // The pull request branch will not be updated
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create identical tracked and untracked file changes await createChanges(changes.tracked, changes.untracked) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('not-updated') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with commits on the base inbetween (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the base const commits = await createCommits(git) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([ _commitMessage, ...commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy() })
it('tests create and then an update with no changes (WBNB)', async () => { // This effectively reverts the branch back to match the base and results in no diff
// Save the default branch tracked content const defaultTrackedContent = await getFileContent(TRACKED_FILE)
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Running with no update effectively reverts the branch back to match the base const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeFalsy() expect(await getFileContent(TRACKED_FILE)).toEqual(defaultTrackedContent) expect(await gitLogMatches([INIT_COMMIT_MESSAGE])).toBeTruthy() })
it('tests create, commits on the base, and update with identical changes to the base (WBNB)', async () => { // The changes on base effectively revert the branch back to match the base and results in no diff // This scenario will cause cherrypick to fail due to an empty commit. // The commit is empty because the changes now exist on the base.
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the base const commits = await createCommits(git) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create the same tracked and untracked file changes that were made to the base const _changes = await createChanges( commits.changes.tracked, commits.changes.untracked ) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeFalsy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([...commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create, squash merge, and update with identical changes (WBNB)', async () => { // Branches that have been squash merged appear to have a diff with the base due to // different commits for the same changes. To prevent creating pull requests // unnecessarily we reset (rebase) the pull request branch when a reset would result // in no diff with the base. This will reset any undeleted branches after merging.
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create a commit on the base with the same changes as the branch // This simulates squash merge of the pull request const commits = await createCommits( git, 1, changes.tracked, changes.untracked ) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create the same tracked and untracked file changes (no change on update) const _changes = await createChanges(changes.tracked, changes.untracked) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeFalsy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([...commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create, force push of base branch, and update with identical changes (WBNB)', async () => { // If the base branch is force pushed to a different commit when there is an open // pull request, the branch must be reset to rebase the changes on the base.
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Force push the base branch to a different commit const amendedCommitMessage = uuidv4() await git.commit(['--amend', '-m', amendedCommitMessage]) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create the same tracked and untracked file changes (no change on update) const _changes = await createChanges(changes.tracked, changes.untracked) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, amendedCommitMessage]) ).toBeTruthy() })
it('tests create and update with commits on the working base (during the workflow) (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create commits on the working base const commits = await createCommits(git) const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(commits.changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual( commits.changes.untracked ) expect( await gitLogMatches([...commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create commits on the working base const _commits = await createCommits(git) const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_commits.changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual( _commits.changes.untracked ) expect( await gitLogMatches([..._commits.commitMsgs, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with changes and commits on the working base (during the workflow) (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create commits on the working base const commits = await createCommits(git) // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([ commitMessage, ...commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create commits on the working base const _commits = await createCommits(git) // Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([ _commitMessage, ..._commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy() })
it('tests create and update with changes and commits on the working base (during the workflow), and commits on the base inbetween (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create commits on the working base const commits = await createCommits(git) // Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([ commitMessage, ...commits.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the base const commitsOnBase = await createCommits(git) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create commits on the working base const _commits = await createCommits(git) // Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([ _commitMessage, ..._commits.commitMsgs, ...commitsOnBase.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy() })
it('tests create and update using a different remote from the base (WBNB)', async () => { // Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, FORK_REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', FORK_REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Set the working base to a branch that is not the pull request base await git.checkout(NOT_BASE_BRANCH)
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, FORK_REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
// Working Base is Not a Ref (WBNR) // A commit is checked out leaving the repository in a "detached HEAD" state
it('tests create and update in detached HEAD state (WBNR)', async () => { // Checkout the HEAD commit SHA const headSha = await git.revParse('HEAD') await git.checkout(headSha)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Checkout the HEAD commit SHA const _headSha = await git.revParse('HEAD') await git.checkout(_headSha)
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([_commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy() })
it('tests create and update with commits on the base inbetween, in detached HEAD state (WBNR)', async () => { // Checkout the HEAD commit SHA const headSha = await git.revParse('HEAD') await git.checkout(headSha)
// Create tracked and untracked file changes const changes = await createChanges() const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(result.action).toEqual('created') expect(await getFileContent(TRACKED_FILE)).toEqual(changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(changes.untracked) expect( await gitLogMatches([commitMessage, INIT_COMMIT_MESSAGE]) ).toBeTruthy()
// Push pull request branch to remote await git.push([ '--force-with-lease', REMOTE_NAME, `HEAD:refs/heads/${BRANCH}` ])
await afterTest(false) await beforeTest()
// Create commits on the base const commitsOnBase = await createCommits(git) await git.push([ '--force', REMOTE_NAME, `HEAD:refs/heads/${DEFAULT_BRANCH}` ])
// Checkout the HEAD commit SHA const _headSha = await git.revParse('HEAD') await git.checkout(_headSha)
// Create tracked and untracked file changes const _changes = await createChanges() const _commitMessage = uuidv4() const _result = await createOrUpdateBranch( git, _commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) expect(_result.action).toEqual('updated') expect(_result.hasDiffWithBase).toBeTruthy() expect(await getFileContent(TRACKED_FILE)).toEqual(_changes.tracked) expect(await getFileContent(UNTRACKED_FILE)).toEqual(_changes.untracked) expect( await gitLogMatches([ _commitMessage, ...commitsOnBase.commitMsgs, INIT_COMMIT_MESSAGE ]) ).toBeTruthy() })
// This failure mode is a limitation of the action. Controlling your own commits cannot be used in detached HEAD state. // https://github.com/peter-evans/create-pull-request/issues/902 it('tests failure to create with commits on the working base (during the workflow) in detached HEAD state (WBNR)', async () => { // Checkout the HEAD commit SHA const headSha = await git.revParse('HEAD') await git.checkout(headSha)
// Create commits on the working base const commits = await createCommits(git) const commitMessage = uuidv4() const result = await createOrUpdateBranch( git, commitMessage, BASE, BRANCH, REMOTE_NAME, false, ADD_PATHS_DEFAULT ) // The action cannot successfully create the branch expect(result.action).toEqual('none') })})

 41  __test__/entrypoint.sh

@@ -0,0 +1,41 @@#!/bin/sh -lset -euo pipefail
# Save the working directoryWORKINGDIR=$PWD
# Create and serve a remote repomkdir -p /git/remotegit config --global init.defaultBranch maingit init --bare /git/remote/test-base.gitgit daemon --verbose --enable=receive-pack --base-path=/git/remote --export-all /git/remote &>/dev/null &
# Give the daemon time to startsleep 2
# Create a local clone and make an initial commitmkdir -p /git/localgit clone git://127.0.0.1/test-base.git /git/local/test-basecd /git/local/test-basegit config --global user.email "you@example.com"git config --global user.name "Your Name"echo "#test-base" > README.mdgit add .git commit -m "initial commit"git push -ugit log -1 --pretty=onelinegit config --global --unset user.emailgit config --global --unset user.namegit config -l
# Clone a server-side fork of the base repocd $WORKINGDIRgit clone --mirror git://127.0.0.1/test-base.git /git/remote/test-fork.gitcd /git/remote/test-fork.gitgit log -1 --pretty=oneline
# Restore the working directorycd $WORKINGDIR
# Execute integration testsjest int --runInBand

 49  __test__/git-auth-helper.int.test.ts

@@ -0,0 +1,49 @@import {GitCommandManager} from '../lib/git-command-manager'import {GitAuthHelper} from '../lib/git-auth-helper'
const REPO_PATH = '/git/local/test-base'
const extraheaderConfigKey = 'http.https://github.com/.extraheader'
describe('git-auth-helper tests', () => { let git: GitCommandManager let gitAuthHelper: GitAuthHelper
beforeAll(async () => { git = await GitCommandManager.create(REPO_PATH) gitAuthHelper = new GitAuthHelper(git) })
it('tests save and restore with no persisted auth', async () => { await gitAuthHelper.savePersistedAuth() await gitAuthHelper.restorePersistedAuth() })
it('tests configure and removal of auth', async () => { await gitAuthHelper.configureToken('github-token') expect(await git.configExists(extraheaderConfigKey)).toBeTruthy() expect(await git.getConfigValue(extraheaderConfigKey)).toEqual( 'AUTHORIZATION: basic eC1hY2Nlc3MtdG9rZW46Z2l0aHViLXRva2Vu' )
await gitAuthHelper.removeAuth() expect(await git.configExists(extraheaderConfigKey)).toBeFalsy() })
it('tests save and restore of persisted auth', async () => { const extraheaderConfigValue = 'AUTHORIZATION: basic ***persisted-auth***' await git.config(extraheaderConfigKey, extraheaderConfigValue)
await gitAuthHelper.savePersistedAuth()
const exists = await git.configExists(extraheaderConfigKey) expect(exists).toBeFalsy()
await gitAuthHelper.restorePersistedAuth()
const configValue = await git.getConfigValue(extraheaderConfigKey) expect(configValue).toEqual(extraheaderConfigValue)
await gitAuthHelper.removeAuth() })})

 23  __test__/integration-tests.sh

@@ -0,0 +1,23 @@#!/usr/bin/env bashset -euo pipefail
IMAGE="cpr-integration-tests:latest"ARG1=${1:-}
if [[ "$(docker images -q $IMAGE 2> /dev/null)" == "" || $ARG1 == "build" ]]; then echo "Building Docker image $IMAGE ..."
cat > Dockerfile << EOFFROM node:16-alpineRUN apk --no-cache add git git-daemonRUN npm install jest jest-environment-jsdom --globalWORKDIR /cprCOPY __test__/entrypoint.sh /entrypoint.shENTRYPOINT ["/entrypoint.sh"]EOF
docker build --no-cache -t $IMAGE . rm Dockerfilefi
docker run -v $PWD:/cpr $IMAGE

 165  __test__/utils.unit.test.ts

@@ -0,0 +1,165 @@import * as path from 'path'import * as utils from '../lib/utils'
const originalGitHubWorkspace = process.env['GITHUB_WORKSPACE']
describe('utils tests', () => { beforeAll(() => { // GitHub workspace process.env['GITHUB_WORKSPACE'] = __dirname })
afterAll(() => { // Restore GitHub workspace delete process.env['GITHUB_WORKSPACE'] if (originalGitHubWorkspace) { process.env['GITHUB_WORKSPACE'] = originalGitHubWorkspace } })
test('getStringAsArray splits string input by newlines and commas', async () => { const array = utils.getStringAsArray('1, 2, 3\n4, 5, 6') expect(array.length).toEqual(6)
const array2 = utils.getStringAsArray('') expect(array2.length).toEqual(0) })
test('getRepoPath successfully returns the path to the repository', async () => { expect(utils.getRepoPath()).toEqual(process.env['GITHUB_WORKSPACE']) expect(utils.getRepoPath('foo')).toEqual( path.resolve(process.env['GITHUB_WORKSPACE'] || '', 'foo') ) })
test('getRemoteDetail successfully parses remote URLs', async () => { const remote1 = utils.getRemoteDetail( 'https://github.com/peter-evans/create-pull-request' ) expect(remote1.protocol).toEqual('HTTPS') expect(remote1.repository).toEqual('peter-evans/create-pull-request')
const remote2 = utils.getRemoteDetail( 'https://xxx:x-oauth-basic@github.com/peter-evans/create-pull-request' ) expect(remote2.protocol).toEqual('HTTPS') expect(remote2.repository).toEqual('peter-evans/create-pull-request')
const remote3 = utils.getRemoteDetail( 'git@github.com:peter-evans/create-pull-request.git' ) expect(remote3.protocol).toEqual('SSH') expect(remote3.repository).toEqual('peter-evans/create-pull-request')
const remote4 = utils.getRemoteDetail( 'https://github.com/peter-evans/create-pull-request.git' ) expect(remote4.protocol).toEqual('HTTPS') expect(remote4.repository).toEqual('peter-evans/create-pull-request')
const remote5 = utils.getRemoteDetail( 'https://github.com/peter-evans/ungit' ) expect(remote5.protocol).toEqual('HTTPS') expect(remote5.repository).toEqual('peter-evans/ungit')
const remote6 = utils.getRemoteDetail( 'https://github.com/peter-evans/ungit.git' ) expect(remote6.protocol).toEqual('HTTPS') expect(remote6.repository).toEqual('peter-evans/ungit')
const remote7 = utils.getRemoteDetail( 'git@github.com:peter-evans/ungit.git' ) expect(remote7.protocol).toEqual('SSH') expect(remote7.repository).toEqual('peter-evans/ungit') })
test('getRemoteDetail fails to parse a remote URL', async () => { const remoteUrl = 'https://github.com/peter-evans' try { utils.getRemoteDetail(remoteUrl) // Fail the test if an error wasn't thrown expect(true).toEqual(false) } catch (e: any) { expect(e.message).toEqual( `The format of '${remoteUrl}' is not a valid GitHub repository URL` ) } })
test('getRemoteUrl successfully returns remote URLs', async () => { const url1 = utils.getRemoteUrl( 'HTTPS', 'github.com', 'peter-evans/create-pull-request' ) expect(url1).toEqual('https://github.com/peter-evans/create-pull-request')
const url2 = utils.getRemoteUrl( 'SSH', 'github.com', 'peter-evans/create-pull-request' ) expect(url2).toEqual('git@github.com:peter-evans/create-pull-request.git')
const url3 = utils.getRemoteUrl( 'HTTPS', 'mygithubserver.com', 'peter-evans/create-pull-request' ) expect(url3).toEqual( 'https://mygithubserver.com/peter-evans/create-pull-request' ) })
test('secondsSinceEpoch returns the number of seconds since the Epoch', async () => { const seconds = `${utils.secondsSinceEpoch()}` expect(seconds.length).toEqual(10) })
test('randomString returns strings of length 7', async () => { for (let i = 0; i < 1000; i++) { expect(utils.randomString().length).toEqual(7) } })
test('parseDisplayNameEmail successfully parses display name email formats', async () => { const parsed1 = utils.parseDisplayNameEmail('abc def <abc@def.com>') expect(parsed1.name).toEqual('abc def') expect(parsed1.email).toEqual('abc@def.com')
const parsed2 = utils.parseDisplayNameEmail( 'github-actions[bot] <41898282+github-actions[bot]@users.noreply.github.com>' ) expect(parsed2.name).toEqual('github-actions[bot]') expect(parsed2.email).toEqual( '41898282+github-actions[bot]@users.noreply.github.com' ) })
test('parseDisplayNameEmail fails to parse display name email formats', async () => { const displayNameEmail1 = 'abc@def.com' try { utils.parseDisplayNameEmail(displayNameEmail1) // Fail the test if an error wasn't thrown expect(true).toEqual(false) } catch (e: any) { expect(e.message).toEqual( `The format of '${displayNameEmail1}' is not a valid email address with display name` ) }
const displayNameEmail2 = ' < >' try { utils.parseDisplayNameEmail(displayNameEmail2) // Fail the test if an error wasn't thrown expect(true).toEqual(false) } catch (e: any) { expect(e.message).toEqual( `The format of '${displayNameEmail2}' is not a valid email address with display name` ) } })})

 86  action.yml

@@ -0,0 +1,86 @@name: 'Create Pull Request'description: 'Creates a pull request for changes to your repository in the actions workspace'inputs: token: description: 'GITHUB_TOKEN or a `repo` scoped Personal Access Token (PAT)' default: ${{ github.token }} path: description: > Relative path under $GITHUB_WORKSPACE to the repository. Defaults to $GITHUB_WORKSPACE. add-paths: description: > A comma or newline-separated list of file paths to commit. Paths should follow git's pathspec syntax. Defaults to adding all new and modified files. commit-message: description: 'The message to use when committing changes.' default: '[create-pull-request] automated change' committer: description: > The committer name and email address in the format `Display Name <email@address.com>`. Defaults to the GitHub Actions bot user. default: 'GitHub <noreply@github.com>' author: description: > The author name and email address in the format `Display Name <email@address.com>`. Defaults to the user who triggered the workflow run. default: '${{ github.actor }} <${{ github.actor }}@users.noreply.github.com>' signoff: description: 'Add `Signed-off-by` line by the committer at the end of the commit log message.' default: false branch: description: 'The pull request branch name.' default: 'create-pull-request/patch' delete-branch: description: > Delete the `branch` when closing pull requests, and when undeleted after merging. Recommend `true`. default: false branch-suffix: description: 'The branch suffix type when using the alternative branching strategy.' base: description: > The pull request base branch. Defaults to the branch checked out in the workflow. push-to-fork: description: > A fork of the checked out parent repository to which the pull request branch will be pushed. e.g. `owner/repo-fork`. The pull request will be created to merge the fork's branch into the parent's base. title: description: 'The title of the pull request.' default: 'Changes by create-pull-request action' body: description: 'The body of the pull request.' default: 'Automated changes by [create-pull-request](https://github.com/peter-evans/create-pull-request) GitHub action' labels: description: 'A comma or newline separated list of labels.' assignees: description: 'A comma or newline separated list of assignees (GitHub usernames).' reviewers: description: 'A comma or newline separated list of reviewers (GitHub usernames) to request a review from.' team-reviewers: description: > A comma or newline separated list of GitHub teams to request a review from. Note that a `repo` scoped Personal Access Token (PAT) may be required. milestone: description: 'The number of the milestone to associate the pull request with.' draft: description: 'Create a draft pull request. It is not possible to change draft status after creation except through the web interface' default: falseoutputs: pull-request-number: description: 'The pull request number' pull-request-url: description: 'The URL of the pull request.' pull-request-operation: description: 'The pull request operation performed by the action, `created`, `updated` or `closed`.' pull-request-head-sha: description: 'The commit SHA of the pull request branch.'runs: using: 'node16' main: 'dist/index.js'branding: icon: 'git-pull-request' color: 'gray-dark'

 1,010  dist/bridge.js

Load diff

Large diffs are not rendered by default.

 977  dist/events.js

Load diff

Large diffs are not rendered by default.

 63,547  dist/index.js

Load diff

Large diffs are not rendered by default.

 469  dist/setup-node-sandbox.js

Load diff

Large diffs are not rendered by default.

 457  dist/setup-sandbox.js

Load diff

Large diffs are not rendered by default.

 68  docs/assets/cpr-gitgraph.htm

@@ -0,0 +1,68 @@<!DOCTYPE html><html lang="en">
<head> <meta charset="UTF-8"> <title>create-pull-request GitHub action</title></head>
<body> <!-- partial:index.partial.html --> <div id="graph-container"></div> <!-- partial --> <script src='https://cdn.jsdelivr.net/npm/@gitgraph/js'></script> <script> const graphContainer = document.getElementById("graph-container");
const customTemplate = GitgraphJS.templateExtend(GitgraphJS.TemplateName.Metro, { commit: { message: { displayAuthor: false, displayHash: false, }, }, });
// Instantiate the graph. const gitgraph = GitgraphJS.createGitgraph(graphContainer, { template: customTemplate, orientation: "vertical-reverse" });
const main = gitgraph.branch("main"); main.commit("Last commit on base"); const localMain = gitgraph.branch("<#1> main (local)"); localMain.commit({ subject: "<uncommitted changes>", body: "Changes to the local base during the workflow", }) const remotePatch = gitgraph.branch("create-pull-request/patch"); remotePatch.merge({ branch: localMain, commitOptions: { subject: "[create-pull-request] automated change", body: "Changes pushed to create the remote branch", }, }); main.commit("New commit on base");
const localMain2 = gitgraph.branch("<#2> main (local)"); localMain2.commit({ subject: "<uncommitted changes>", body: "Changes to the updated local base during the workflow", }) remotePatch.merge({ branch: localMain2, commitOptions: { subject: "[create-pull-request] automated change", body: "Changes force pushed to update the remote branch", }, });
main.merge(remotePatch);
</script>
</body>
</html>

 BIN +108 KB docs/assets/cpr-gitgraph.png

Unable to render rich display

 6  docs/assets/logo.svg

Unable to render rich display

 BIN +327 KB docs/assets/pull-request-example.png

Unable to render rich display

 371  docs/concepts-guidelines.md

@@ -0,0 +1,371 @@# Concepts, guidelines and advanced usage
This document covers terminology, how the action works, general usage guidelines, and advanced usage.
- [Terminology](#terminology)- [Events and checkout](#events-and-checkout)- [How the action works](#how-the-action-works)- [Guidelines](#guidelines) - [Providing a consistent base](#providing-a-consistent-base) - [Events which checkout a commit](#events-which-checkout-a-commit) - [Restrictions on repository forks](#restrictions-on-repository-forks) - [Triggering further workflow runs](#triggering-further-workflow-runs) - [Security](#security)- [Advanced usage](#advanced-usage) - [Creating pull requests in a remote repository](#creating-pull-requests-in-a-remote-repository) - [Push using SSH (deploy keys)](#push-using-ssh-deploy-keys) - [Push pull request branches to a fork](#push-pull-request-branches-to-a-fork) - [Authenticating with GitHub App generated tokens](#authenticating-with-github-app-generated-tokens) - [GPG commit signature verification](#gpg-commit-signature-verification) - [Running in a container or on self-hosted runners](#running-in-a-container-or-on-self-hosted-runners)
## Terminology
[Pull requests](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests#about-pull-requests) are proposed changes to a repository branch that can be reviewed by a repository's collaborators before being accepted or rejected. 
A pull request references two branches:
- The `base` of a pull request is the branch you intend to change once the proposed changes are merged.- The `branch` of a pull request represents what you intend the `base` to look like when merged. It is the `base` branch *plus* changes that have been made to it.
## Events and checkout
This action expects repositories to be checked out with the official GitHub Actions [checkout](https://github.com/actions/checkout) action.For each [event type](https://docs.github.com/en/actions/reference/events-that-trigger-workflows) there is a default `GITHUB_SHA` that will be checked out.
The default can be overridden by specifying a `ref` on checkout.
```yml - uses: actions/checkout@v3 with: ref: develop```
## How the action works
Unless the `base` input is supplied, the action expects the target repository to be checked out on the pull request `base`&mdash;the branch you intend to modify with the proposed changes.
Workflow steps:
1. Checkout the `base` branch2. Make changes3. Execute `create-pull-request` action
The following git diagram shows how the action creates and updates a pull request branch.
![Create Pull Request GitGraph](assets/cpr-gitgraph.png)
## Guidelines
### Providing a consistent base
For the action to work correctly it should be executed in a workflow that checks out a *consistent* base branch. This will be the base of the pull request unless overridden with the `base` input.
This means your workflow should be consistently checking out the branch that you intend to modify once the PR is merged.
In the following example, the [`push`](https://docs.github.com/en/actions/reference/events-that-trigger-workflows#push) and [`create`](https://docs.github.com/en/actions/reference/events-that-trigger-workflows#create) events both trigger the same workflow. This will cause the checkout action to checkout inconsistent branches and commits. Do *not* do this. It will cause multiple pull requests to be created for each additional `base` the action is executed against.
```ymlon: push: create:jobs: example: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3```
There may be use cases where it makes sense to execute the workflow on a branch that is not the base of the pull request. In these cases, the base branch can be specified with the `base` action input. The action will attempt to rebase changes made during the workflow on to the actual base.
### Events which checkout a commit
The [default checkout](#events-and-checkout) for the majority of events will leave the repository checked out on a branch.However, some events such as `release` and `pull_request` will leave the repository in a "detached HEAD" state.This is because they checkout a commit, not a branch.In these cases, you *must supply* the `base` input so the action can rebase changes made during the workflow for the pull request.
Workflows triggered by [`pull_request`](https://docs.github.com/en/actions/reference/events-that-trigger-workflows#pull_request) events will by default check out a merge commit. Set the `base` input as follows to base the new pull request on the current pull request's branch.
```yml - uses: peter-evans/create-pull-request@v4 with: base: ${{ github.head_ref }}```
Workflows triggered by [`release`](https://docs.github.com/en/actions/reference/events-that-trigger-workflows#release) events will by default check out a tag. For most use cases, you will need to set the `base` input to the branch name of the tagged commit.
```yml - uses: peter-evans/create-pull-request@v4 with: base: main```
### Restrictions on repository forks
GitHub Actions have imposed restrictions on workflow runs triggered by public repository forks.Private repositories can be configured to [enable workflows](https://docs.github.com/en/github/administering-a-repository/disabling-or-limiting-github-actions-for-a-repository#enabling-workflows-for-private-repository-forks) from forks to run without restriction.
The restrictions apply to the `pull_request` event triggered by a fork opening a pull request in the upstream repository.
- Events from forks cannot access secrets, except for the default `GITHUB_TOKEN`. > With the exception of GITHUB_TOKEN, secrets are not passed to the runner when a workflow is triggered from a forked repository. [GitHub Actions: Using encrypted secrets in a workflow](https://docs.github.com/en/actions/configuring-and-managing-workflows/creating-and-storing-encrypted-secrets#using-encrypted-secrets-in-a-workflow)
- The `GITHUB_TOKEN` has read-only access when an event is triggered by a forked repository.
[GitHub Actions: Permissions for the GITHUB_TOKEN](https://docs.github.com/en/actions/configuring-and-managing-workflows/authenticating-with-the-github_token#permissions-for-the-github_token)
These restrictions mean that during a `pull_request` event triggered by a forked repository, actions have no write access to GitHub resources and will fail on any attempt.
A job condition can be added to prevent workflows from executing when triggered by a repository fork.
```ymlon: pull_requestjobs: example: runs-on: ubuntu-latest # Check if the event is not triggered by a fork if: github.event.pull_request.head.repo.full_name == github.repository```
For further reading regarding the security of pull requests, see this GitHub blog post titled [Keeping your GitHub Actions and workflows secure: Preventing pwn requests](https://securitylab.github.com/research/github-actions-preventing-pwn-requests/)
### Triggering further workflow runs
Pull requests created by the action using the default `GITHUB_TOKEN` cannot trigger other workflows. If you have `on: pull_request` or `on: push` workflows acting as checks on pull requests, they will not run.
> When you use the repository's `GITHUB_TOKEN` to perform tasks, events triggered by the `GITHUB_TOKEN` will not create a new workflow run. This prevents you from accidentally creating recursive workflow runs. For example, if a workflow run pushes code using the repository's `GITHUB_TOKEN`, a new workflow will not run even when the repository contains a workflow configured to run when `push` events occur.[GitHub Actions: Triggering a workflow from a workflow](https://docs.github.com/en/actions/using-workflows/triggering-a-workflow#triggering-a-workflow-from-a-workflow)
#### Workarounds to trigger further workflow runs
There are a number of workarounds with different pros and cons.
- Use the default `GITHUB_TOKEN` and allow the action to create pull requests that have no checks enabled. Manually close pull requests and immediately reopen them. This will enable `on: pull_request` workflows to run and be added as checks. To prevent merging of pull requests without checks erroneously, use [branch protection rules](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/defining-the-mergeability-of-pull-requests).
- Use a `repo` scoped [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) created on an account that has write access to the repository that pull requests are being created in. This is the standard workaround and [recommended by GitHub](https://docs.github.com/en/actions/reference/events-that-trigger-workflows#triggering-new-workflows-using-a-personal-access-token). However, the PAT cannot be scoped to a specific repository so the token becomes a very sensitive secret. If this is a concern, the PAT can instead be created for a dedicated [machine account](https://docs.github.com/en/github/site-policy/github-terms-of-service#3-account-requirements) that has collaborator access to the repository. Also note that because the account that owns the PAT will be the creator of pull requests, that user account will be unable to perform actions such as request changes or approve the pull request.
- Use [SSH (deploy keys)](#push-using-ssh-deploy-keys) to push the pull request branch. This is arguably more secure than using a PAT because deploy keys can be set per repository. However, this method will only trigger `on: push` workflows.
- Use a [machine account that creates pull requests from its own fork](#push-pull-request-branches-to-a-fork). This is the most secure because the PAT created only grants access to the machine account's fork, not the main repository. This method will trigger `on: pull_request` workflows to run. Workflows triggered `on: push` will not run because the push event is in the fork.
- Use a [GitHub App to generate a token](#authenticating-with-github-app-generated-tokens) that can be used with this action. GitHub App generated tokens are more secure than using a PAT because GitHub App access permissions can be set with finer granularity and are scoped to only repositories where the App is installed. This method will trigger both `on: push` and `on: pull_request` workflows.
### Security
From a security perspective it's good practice to fork third-party actions, review the code, and use your fork of the action in workflows.By using third-party actions directly the risk exists that it could be modified to do something malicious, such as capturing secrets.
Alternatively, use the action directly and reference the commit hash for the version you want to target.```yml - uses: thirdparty/foo-action@172ec762f2ac8e050062398456fccd30444f8f30```
This action uses [ncc](https://github.com/vercel/ncc) to compile the Node.js code and dependencies into a single JavaScript file under the [dist](https://github.com/peter-evans/create-pull-request/tree/main/dist) directory.
## Advanced usage
### Creating pull requests in a remote repository
Checking out a branch from a different repository from where the workflow is executing will make *that repository* the target for the created pull request. In this case, a `repo` scoped [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) is required.
```yml - uses: actions/checkout@v3 with: token: ${{ secrets.PAT }} repository: owner/repo # Make changes to pull request here - uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.PAT }}```
### Push using SSH (deploy keys)
[Deploy keys](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys) can be set per repository and so are arguably more secure than using a `repo` scoped [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token).Allowing the action to push with a configured deploy key will trigger `on: push` workflows. This makes it an alternative to using a PAT to trigger checks for pull requests.Note that you cannot use deploy keys alone to [create a pull request in a remote repository](#creating-pull-requests-in-a-remote-repository) because then using a PAT would become a requirement. This method only makes sense if creating a pull request in the repository where the workflow is running.
How to use SSH (deploy keys) with create-pull-request action:
1. [Create a new SSH key pair](https://docs.github.com/en/github/authenticating-to-github/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent#generating-a-new-ssh-key) for your repository. Do not set a passphrase.2. Copy the contents of the public key (.pub file) to a new repository [deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys) and check the box to "Allow write access."3. Add a secret to the repository containing the entire contents of the private key.4. As shown in the example below, configure `actions/checkout` to use the deploy key you have created.
```yml steps: - uses: actions/checkout@v3 with: ssh-key: ${{ secrets.SSH_PRIVATE_KEY }} # Make changes to pull request here - name: Create Pull Request uses: peter-evans/create-pull-request@v4```
### Push pull request branches to a fork
Instead of pushing pull request branches to the repository you want to update, you can push them to a fork of that repository.This allows you to employ the [principle of least privilege](https://en.wikipedia.org/wiki/Principle_of_least_privilege) by using a dedicated user acting as a [machine account](https://docs.github.com/en/github/site-policy/github-terms-of-service#3-account-requirements).This user only has `read` access to the main repository.It will use their own fork to push code and create the pull request.Note that if you choose to use this method (not give the machine account `write` access to the repository) the following inputs cannot be used: `labels`, `assignees`, `reviewers`, `team-reviewers` and `milestone`.
1. Create a new GitHub user and login.2. Fork the repository that you will be creating pull requests in.3. Create a [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token).4. Logout and log back into your main user account.5. Add a secret to your repository containing the above PAT.6. As shown in the following example workflow, set the `push-to-fork` input to the full repository name of the fork.
```yaml - uses: actions/checkout@v3 # Make changes to pull request here - uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.MACHINE_USER_PAT }} push-to-fork: machine-user/fork-of-repository```
### Authenticating with GitHub App generated tokens
A GitHub App can be created for the sole purpose of generating tokens for use with GitHub actions.These tokens can be used in place of `GITHUB_TOKEN` or a [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token).GitHub App generated tokens are more secure than using a PAT because GitHub App access permissions can be set with finer granularity and are scoped to only repositories where the App is installed.
1. Create a minimal [GitHub App](https://docs.github.com/en/developers/apps/creating-a-github-app), setting the following fields:
- Set `GitHub App name`. - Set `Homepage URL` to anything you like, such as your GitHub profile page. - Uncheck `Active` under `Webhook`. You do not need to enter a `Webhook URL`. - Under `Repository permissions: Contents` select `Access: Read & write`. - Under `Repository permissions: Pull requests` select `Access: Read & write`. - Under `Organization permissions: Members` select `Access: Read-only`. - **NOTE**: Only needed if you would like add teams as reviewers to PRs.
2. Create a Private key from the App settings page and store it securely.
3. Install the App on any repository where workflows will run requiring tokens.
4. Set secrets on your repository containing the GitHub App ID, and the private key you created in step 2. e.g. `APP_ID`, `APP_PRIVATE_KEY`.
5. The following example workflow shows how to use [tibdex/github-app-token](https://github.com/tibdex/github-app-token) to generate a token for use with this action.
```yaml steps: - uses: actions/checkout@v3 - uses: tibdex/github-app-token@v1 id: generate-token with: app_id: ${{ secrets.APP_ID }} private_key: ${{ secrets.APP_PRIVATE_KEY }} # Make changes to pull request here - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: token: ${{ steps.generate-token.outputs.token }}```
### GPG commit signature verification
The action can use GPG to sign commits with a GPG key that you generate yourself.
1. Follow GitHub's guide to [generate a new GPG key](https://docs.github.com/en/github/authenticating-to-github/generating-a-new-gpg-key).
2. [Add the public key](https://docs.github.com/en/github/authenticating-to-github/adding-a-new-gpg-key-to-your-github-account) to the user account associated with the [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) that you will use with the action.
3. Copy the private key to your clipboard, replacing `email@example.com` with the email address of your GPG key. ``` # macOS gpg --armor --export-secret-key email@example.com | pbcopy ```
4. Paste the private key into a repository secret where the workflow will run. e.g. `GPG_PRIVATE_KEY`
5. Create another repository secret for the key's passphrase, if applicable. e.g. `GPG_PASSPHRASE`
6. The following example workflow shows how to use [crazy-max/ghaction-import-gpg](https://github.com/crazy-max/ghaction-import-gpg) to import your GPG key and allow the action to sign commits.
Note that the `committer` email address *MUST* match the email address used to create your GPG key.
```yaml steps: - uses: actions/checkout@v3 - uses: crazy-max/ghaction-import-gpg@v3 with: gpg-private-key: ${{ secrets.GPG_PRIVATE_KEY }} passphrase: ${{ secrets.GPG_PASSPHRASE }} git-user-signingkey: true git-commit-gpgsign: true # Make changes to pull request here - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.PAT }} committer: example <email@example.com>```
### Running in a container or on self-hosted runners
This action can be run inside a container, or on [self-hosted runners](https://docs.github.com/en/actions/hosting-your-own-runners), by installing the necessary dependencies.
This action requires `git` to be installed and on the `PATH`. Note that `actions/checkout` requires Git 2.18 or higher to be installed, otherwise it will just download the source of the repository instead of cloning it.
The following examples of running in a container show the dependencies being installed during the workflow, but they could also be pre-installed in a custom image.
**Alpine container example:**```ymljobs: createPullRequestAlpine: runs-on: ubuntu-latest container: image: alpine steps: - name: Install dependencies run: apk --no-cache add git - uses: actions/checkout@v3 # Make changes to pull request here - name: Create Pull Request uses: peter-evans/create-pull-request@v4```
**Ubuntu container example:**```ymljobs: createPullRequestAlpine: runs-on: ubuntu-latest container: image: ubuntu steps: - name: Install dependencies run: | apt-get update apt-get install -y software-properties-common add-apt-repository -y ppa:git-core/ppa apt-get install -y git - uses: actions/checkout@v3 # Make changes to pull request here - name: Create Pull Request uses: peter-evans/create-pull-request@v4```

 634  docs/examples.md

@@ -0,0 +1,634 @@# Examples
- [Use case: Create a pull request to update X on push](#use-case-create-a-pull-request-to-update-x-on-push) - [Update project authors](#update-project-authors) - [Keep a branch up-to-date with another](#keep-a-branch-up-to-date-with-another)- [Use case: Create a pull request to update X on release](#use-case-create-a-pull-request-to-update-x-on-release) - [Update changelog](#update-changelog)- [Use case: Create a pull request to update X periodically](#use-case-create-a-pull-request-to-update-x-periodically) - [Update NPM dependencies](#update-npm-dependencies) - [Update Gradle dependencies](#update-gradle-dependencies) - [Update Cargo dependencies](#update-cargo-dependencies) - [Update SwaggerUI for GitHub Pages](#update-swaggerui-for-github-pages) - [Keep a fork up-to-date with its upstream](#keep-a-fork-up-to-date-with-its-upstream) - [Spider and download a website](#spider-and-download-a-website)- [Use case: Create a pull request to update X by calling the GitHub API](#use-case-create-a-pull-request-to-update-x-by-calling-the-github-api) - [Call the GitHub API from an external service](#call-the-github-api-from-an-external-service) - [Call the GitHub API from another GitHub Actions workflow](#call-the-github-api-from-another-github-actions-workflow)- [Use case: Create a pull request to modify/fix pull requests](#use-case-create-a-pull-request-to-modifyfix-pull-requests) - [autopep8](#autopep8)- [Misc workflow tips](#misc-workflow-tips) - [Filtering push events](#filtering-push-events) - [Dynamic configuration using variables](#dynamic-configuration-using-variables) - [Setting the pull request body from a file](#setting-the-pull-request-body-from-a-file) - [Using a markdown template](#using-a-markdown-template) - [Debugging GitHub Actions](#debugging-github-actions)

## Use case: Create a pull request to update X on push
This pattern will work well for updating any kind of static content based on pushed changes. Care should be taken when using this pattern in repositories with a high frequency of commits.
### Update project authors
Raises a pull request to update a file called `AUTHORS` with the git user names and email addresses of contributors.
```ymlname: Update AUTHORSon: push: branches: - mainjobs: updateAuthors: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 with: fetch-depth: 0 - name: Update AUTHORS run: | git log --format='%aN <%aE>%n%cN <%cE>' | sort -u > AUTHORS - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: commit-message: update authors title: Update AUTHORS body: Credit new contributors by updating AUTHORS branch: update-authors```### Keep a branch up-to-date with anotherThis is a use case where a branch should be kept up to date with another by opening a pull request to update it. The pull request should then be updated with new changes until it is merged or closed.In this example scenario, a branch called `production` should be updated via pull request to keep it in sync with `main`. Merging the pull request is effectively promoting those changes to production.```ymlname: Create production promotion pull requeston: push: branches: - mainjobs: productionPromotion: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 with: ref: production - name: Reset promotion branch run: | git fetch origin main:main git reset --hard main - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: branch: production-promotion```## Use case: Create a pull request to update X on releaseThis pattern will work well for updating any kind of static content based on the tagged commit of a release. Note that because `release` is one of the [events which checkout a commit](concepts-guidelines.md#events-which-checkout-a-commit) it is necessary to supply the `base` input to the action.### Update changelogRaises a pull request to update the `CHANGELOG.md` file based on the tagged commit of the release.Note that [git-chglog](https://github.com/git-chglog/git-chglog/) requires some configuration files to exist in the repository before this workflow will work.This workflow assumes the tagged release was made on a default branch called `main`.```ymlname: Update Changelogon: release: types: [published]jobs: updateChangelog: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 with: fetch-depth: 0 - name: Update Changelog run: | curl -o git-chglog -L https://github.com/git-chglog/git-chglog/releases/download/0.9.1/git-chglog_linux_amd64 chmod u+x git-chglog ./git-chglog -o CHANGELOG.md rm git-chglog - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: commit-message: update changelog title: Update Changelog body: Update changelog to reflect release changes branch: update-changelog base: main```## Use case: Create a pull request to update X periodicallyThis pattern will work well for updating any kind of static content from an external source. The workflow executes on a schedule and raises a pull request when there are changes.### Update NPM dependenciesThis workflow will create a pull request for npm dependencies.It works best in combination with a build workflow triggered on `push` and `pull_request`.A [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) can be used in order for the creation of the pull request to trigger further workflows. See the [documentation here](concepts-guidelines.md#triggering-further-workflow-runs) for further details.```ymlname: Update Dependencieson: schedule: - cron: '0 10 * * 1'jobs: update-dep: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 - uses: actions/setup-node@v3 with: node-version: '16.x' - name: Update dependencies run: | npx -p npm-check-updates ncu -u npm install - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.PAT }} commit-message: Update dependencies title: Update dependencies body: | - Dependency updates Auto-generated by [create-pull-request][1] [1]: https://github.com/peter-evans/create-pull-request branch: update-dependencies```The above workflow works best in combination with a build workflow triggered on `push` and `pull_request`.```ymlname: CIon: push: branches: [main] pull_request: branches: [main]jobs: build: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 - uses: actions/setup-node@v3 with: node-version: 16.x - run: npm ci - run: npm run test - run: npm run build```### Update Gradle dependenciesThe following workflow will create a pull request for Gradle dependencies.It requires first configuring your project to use Gradle lockfiles.See [here](https://github.com/peter-evans/gradle-auto-dependency-updates) for how to configure your project and use the following workflow.```ymlname: Update Dependencieson: schedule: - cron: '0 1 * * 1'jobs: update-dep: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 - uses: actions/setup-java@v2 with: distribution: 'temurin' java-version: 1.8 - name: Grant execute permission for gradlew run: chmod +x gradlew - name: Perform dependency resolution and write new lockfiles run: ./gradlew dependencies --write-locks - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.PAT }} commit-message: Update dependencies title: Update dependencies body: | - Dependency updates Auto-generated by [create-pull-request][1] [1]: https://github.com/peter-evans/create-pull-request branch: update-dependencies```### Update Cargo dependenciesThe following workflow will create a pull request for Cargo dependencies.It optionally uses [`cargo-edit`](https://github.com/killercup/cargo-edit) to update `Cargo.toml` and keep it in sync with `Cargo.lock`.```ymlname: Update Dependencieson: schedule: - cron: '0 1 * * 1'jobs: update-dep: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 - name: Update dependencies run: | cargo install cargo-edit cargo update cargo upgrade --to-lockfile - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.PAT }} commit-message: Update dependencies title: Update dependencies body: | - Dependency updates Auto-generated by [create-pull-request][1] [1]: https://github.com/peter-evans/create-pull-request branch: update-dependencies```### Update SwaggerUI for GitHub PagesWhen using [GitHub Pages to host Swagger documentation](https://github.com/peter-evans/swagger-github-pages), this workflow updates the repository with the latest distribution of [SwaggerUI](https://github.com/swagger-api/swagger-ui).You must create a file called `swagger-ui.version` at the root of your repository before running.```ymlname: Update Swagger UIon: schedule: - cron: '0 10 * * *'jobs: updateSwagger: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 - name: Get Latest Swagger UI Release id: swagger-ui run: | release_tag=$(curl -sL https://api.github.com/repos/swagger-api/swagger-ui/releases/latest | jq -r ".tag_name") echo "release_tag=$release_tag" >> $GITHUB_OUTPUT current_tag=$(<swagger-ui.version) echo "current_tag=$current_tag" >> $GITHUB_OUTPUT - name: Update Swagger UI if: steps.swagger-ui.outputs.current_tag != steps.swagger-ui.outputs.release_tag env: RELEASE_TAG: ${{ steps.swagger-ui.outputs.release_tag }} SWAGGER_YAML: "swagger.yaml" run: | # Delete the dist directory and index.html rm -fr dist index.html # Download the release curl -sL -o $RELEASE_TAG https://api.github.com/repos/swagger-api/swagger-ui/tarball/$RELEASE_TAG # Extract the dist directory tar -xzf $RELEASE_TAG --strip-components=1 $(tar -tzf $RELEASE_TAG | head -1 | cut -f1 -d"/")/dist rm $RELEASE_TAG # Move index.html to the root mv dist/index.html . # Fix references in index.html sed -i "s|https://petstore.swagger.io/v2/swagger.json|$SWAGGER_YAML|g" index.html sed -i "s|href=\"./|href=\"dist/|g" index.html sed -i "s|src=\"./|src=\"dist/|g" index.html # Update current release echo ${{ steps.swagger-ui.outputs.release_tag }} > swagger-ui.version - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: commit-message: Update swagger-ui to ${{ steps.swagger-ui.outputs.release_tag }} title: Update SwaggerUI to ${{ steps.swagger-ui.outputs.release_tag }} body: | Updates [swagger-ui][1] to ${{ steps.swagger-ui.outputs.release_tag }} Auto-generated by [create-pull-request][2] [1]: https://github.com/swagger-api/swagger-ui [2]: https://github.com/peter-evans/create-pull-request labels: dependencies, automated pr branch: swagger-ui-updates```### Keep a fork up-to-date with its upstreamThis example is designed to be run in a seperate repository from the fork repository itself.The aim of this is to prevent committing anything to the fork's default branch would cause it to differ from the upstream.In the following example workflow, `owner/repo` is the upstream repository and `fork-owner/repo` is the fork. It assumes the default branch of the upstream repository is called `main`.The [Personal Access Token (PAT)](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) should have `repo` scope. Additionally, if the upstream makes changes to the `.github/workflows` directory, the action will be unable to push the changes to a branch and throw the error "_(refusing to allow a GitHub App to create or update workflow `.github/workflows/xxx.yml` without `workflows` permission)_". To allow these changes to be pushed to the fork, add the `workflow` scope to the PAT. Of course, allowing this comes with the risk that the workflow changes from the upstream could run and do something unexpected. Disabling GitHub Actions in the fork is highly recommended to prevent this.When you merge the pull request make sure to choose the [`Rebase and merge`](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-request-merges#rebase-and-merge-your-pull-request-commits) option. This will make the fork's commits match the commits on the upstream.```ymlname: Update forkon: schedule: - cron: '0 0 * * 0'jobs: updateFork: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 with: repository: fork-owner/repo - name: Reset the default branch with upstream changes run: | git remote add upstream https://github.com/owner/repo.git git fetch upstream main:upstream-main git reset --hard upstream-main - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: token: ${{ secrets.PAT }} branch: upstream-changes```### Spider and download a websiteThis workflow spiders a website and downloads the content. Any changes to the website will be raised in a pull request.```ymlname: Download Websiteon: schedule: - cron: '0 10 * * *'jobs: format: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 - name: Download website run: | wget \ --recursive \ --level=2 \ --wait=1 \ --no-clobber \ --page-requisites \ --html-extension \ --convert-links \ --domains quotes.toscrape.com \ http://quotes.toscrape.com/ - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: commit-message: update local website copy title: Automated Updates to Local Website Copy body: This is an auto-generated PR with website updates. branch: website-updates```## Use case: Create a pull request to update X by calling the GitHub APIYou can use the GitHub API to trigger a webhook event called [`repository_dispatch`](https://docs.github.com/en/actions/reference/events-that-trigger-workflows#repository_dispatch) when you want to trigger a workflow for any activity that happens outside of GitHub.This pattern will work well for updating any kind of static content from an external source.You can modify any of the examples in the previous section to work in this fashion.Set the workflow to execute `on: repository_dispatch`.```ymlon: repository_dispatch: types: [create-pull-request]```### Call the GitHub API from an external serviceAn `on: repository_dispatch` workflow can be triggered by a call to the GitHub API as follows.- `[username]` is a GitHub username- `[token]` is a `repo` scoped [Personal Access Token](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token)- `[repository]` is the name of the repository the workflow resides in.```curl -XPOST -u "[username]:[token]" \ -H "Accept: application/vnd.github.everest-preview+json" \ -H "Content-Type: application/json" \ https://api.github.com/repos/[username]/[repository]/dispatches \ --data '{"event_type": "create-pull-request"}'```### Call the GitHub API from another GitHub Actions workflowAn `on: repository_dispatch` workflow can be triggered from another workflow with [repository-dispatch](https://github.com/peter-evans/repository-dispatch) action.```yml- name: Repository Dispatch uses: peter-evans/repository-dispatch@v2 with: token: ${{ secrets.REPO_ACCESS_TOKEN }} repository: username/my-repo event-type: create-pull-request client-payload: '{"ref": "${{ github.ref }}", "sha": "${{ github.sha }}"}'```## Use case: Create a pull request to modify/fix pull requests**Note**: While the following approach does work, my strong recommendation would be to use a slash command style "ChatOps" solution for operations on pull requests. See [slash-command-dispatch](https://github.com/peter-evans/slash-command-dispatch) for such a solution.This is a pattern that lends itself to automated code linting and fixing. A pull request can be created to fix or modify something during an `on: pull_request` workflow. The pull request containing the fix will be raised with the original pull request as the base. This can be then be merged to update the original pull request and pass any required tests.Note that due to [token restrictions on public repository forks](https://docs.github.com/en/actions/configuring-and-managing-workflows/authenticating-with-the-github_token#permissions-for-the-github_token), workflows for this use case do not work for pull requests raised from forks.Private repositories can be configured to [enable workflows](https://docs.github.com/en/github/administering-a-repository/disabling-or-limiting-github-actions-for-a-repository#enabling-workflows-for-private-repository-forks) from forks to run without restriction. ### autopep8The following is an example workflow for a use case where [autopep8 action](https://github.com/peter-evans/autopep8) runs as both a check on pull requests and raises a further pull request to apply code fixes.How it works:1. When a pull request is raised the workflow executes as a check2. If autopep8 makes any fixes a pull request will be raised for those fixes to be merged into the current pull request branch. The workflow then deliberately causes the check to fail.3. When the pull request containing the fixes is merged the workflow runs again. This time autopep8 makes no changes and the check passes.4. The original pull request can now be merged.```ymlname: autopep8on: pull_requestjobs: autopep8: # Check if the PR is not raised by this workflow and is not from a fork if: startsWith(github.head_ref, 'autopep8-patches') == false && github.event.pull_request.head.repo.full_name == github.repository runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 with: ref: ${{ github.head_ref }} - name: autopep8 id: autopep8 uses: peter-evans/autopep8@v1 with: args: --exit-code --recursive --in-place --aggressive --aggressive . - name: Set autopep8 branch name id: vars run: | branch-name="autopep8-patches/${{ github.head_ref }}" echo "branch-name=$branch-name" >> $GITHUB_OUTPUT - name: Create Pull Request if: steps.autopep8.outputs.exit-code == 2 uses: peter-evans/create-pull-request@v4 with: commit-message: autopep8 action fixes title: Fixes by autopep8 action body: This is an auto-generated PR with fixes by autopep8. labels: autopep8, automated pr branch: ${{ steps.vars.outputs.branch-name }} - name: Fail if autopep8 made changes if: steps.autopep8.outputs.exit-code == 2 run: exit 1```## Misc workflow tips### Filtering push eventsFor workflows using `on: push` you may want to ignore push events for tags and only execute for branches. Specifying `branches` causes only events on branches to trigger the workflow. The `'**'` wildcard will match any branch name.```ymlon: push: branches: - '**' ```If you have a workflow that contains jobs to handle push events on branches as well as tags, you can make sure that the job where you use `create-pull-request` action only executes when `github.ref` is a branch by using an `if` condition as follows.```ymlon: pushjobs: createPullRequest: if: startsWith(github.ref, 'refs/heads/') runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 ... someOtherJob: runs-on: ubuntu-latest steps: - uses: actions/checkout@v3 ...```### Dynamic configuration using variablesThe following examples show how configuration for the action can be dynamically defined in a previous workflow step.Note that the step where output variables are defined must have an id.```yml - name: Set output variables id: vars run: | pr_title="[Test] Add report file $(date +%d-%m-%Y)" pr_body="This PR was auto-generated on $(date +%d-%m-%Y) \ by [create-pull-request](https://github.com/peter-evans/create-pull-request)." echo "pr_title=$pr_title" >> $GITHUB_OUTPUT echo "pr_body=$pr_body" >> $GITHUB_OUTPUT - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: title: ${{ steps.vars.outputs.pr_title }} body: ${{ steps.vars.outputs.pr_body }}```### Setting the pull request body from a fileThis example shows how file content can be read into a variable and passed to the action.```yml - id: get-pr-body run: | body=$(cat pr-body.txt) delimiter="$(openssl rand -hex 8)" echo "body<<$delimiter" >> $GITHUB_OUTPUT echo "$body" >> $GITHUB_OUTPUT echo "$delimiter" >> $GITHUB_OUTPUT - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: body: ${{ steps.get-pr-body.outputs.body }}```### Using a markdown templateIn this example, a markdown template file is added to the repository at `.github/pull-request-template.md` with the following content.```This is a test pull request templateRender template variables such as {{ .foo }} and {{ .bar }}.```The template is rendered using the [render-template](https://github.com/chuhlomin/render-template) action and the result is used to create the pull request.```yml - name: Render template id: template uses: chuhlomin/render-template@v1.4 with: template: .github/pull-request-template.md vars: | foo: this bar: that - name: Create Pull Request uses: peter-evans/create-pull-request@v4 with: body: ${{ steps.template.outputs.result }}```### Debugging GitHub Actions#### Runner Diagnostic Logging[Runner diagnostic logging](https://docs.github.com/en/actions/configuring-and-managing-workflows/managing-a-workflow-run#enabling-runner-diagnostic-logging) provides additional log files that contain information about how a runner is executing an action.To enable runner diagnostic logging, set the secret `ACTIONS_RUNNER_DEBUG` to `true` in the repository that contains the workflow.#### Step Debug Logging[Step debug logging](https://docs.github.com/en/actions/configuring-and-managing-workflows/managing-a-workflow-run#enabling-step-debug-logging) increases the verbosity of a job's logs during and after a job's execution.To enable step debug logging set the secret `ACTIONS_STEP_DEBUG` to `true` in the repository that contains the workflow.#### Output Various Contexts```yml steps: - name: Dump GitHub context env: GITHUB_CONTEXT: ${{ toJson(github) }} run: echo "$GITHUB_CONTEXT" - name: Dump job context env: JOB_CONTEXT: ${{ toJson(job) }} run: echo "$JOB_CONTEXT" - name: Dump steps context env: STEPS_CONTEXT: ${{ toJson(steps) }} run: echo "$STEPS_CONTEXT" - name: Dump runner context env: RUNNER_CONTEXT: ${{ toJson(runner) }} run: echo "$RUNNER_CONTEXT" - name: Dump strategy context env: STRATEGY_CONTEXT: ${{ toJson(strategy) }} run: echo "$STRATEGY_CONTEXT" - name: Dump matrix context env: MATRIX_CONTEXT: ${{ toJson(matrix) }} run: echo "$MATRIX_CONTEXT"```

 87  docs/updating.md

@@ -0,0 +1,87 @@## Updating from `v3` to `v4`
### Breaking changes
- The `add-paths` input no longer accepts `-A` as a valid value. When committing all new and modified files the `add-paths` input should be omitted.
- If using self-hosted runners or GitHub Enterprise Server, there are minimum requirements for `v4` to run. See "What's new" below for details.
### What's new
- Updated runtime to Node.js 16 - The action now requires a minimum version of v2.285.0 for the [Actions Runner](https://github.com/actions/runner/releases/tag/v2.285.0). - If using GitHub Enterprise Server, the action requires [GHES 3.4](https://docs.github.com/en/enterprise-server@3.4/admin/release-notes) or later.
## Updating from `v2` to `v3`
### Breaking changes
- The `author` input now defaults to the user who triggered the workflow run. This default is set via [action.yml](../action.yml) as `${{ github.actor }} <${{ github.actor }}@users.noreply.github.com>`, where `github.actor` is the GitHub user account associated with the run. For example, `peter-evans <peter-evans@users.noreply.github.com>`.
To continue to use the `v2` default, set the `author` input as follows. ```yaml - uses: peter-evans/create-pull-request@v3 with: author: github-actions[bot] <41898282+github-actions[bot]@users.noreply.github.com> ```
- The `author` and `committer` inputs are no longer cross-used if only one is supplied. Additionally, when neither input is set, the `author` and `committer` are no longer determined from an existing identity set in git config. In both cases, the inputs will fall back to their default set in [action.yml](../action.yml).
- Deprecated inputs `project` and `project-column` have been removed in favour of an additional action step. See [Create a project card](https://github.com/peter-evans/create-pull-request#create-a-project-card) for details.
- Deprecated output `pr_number` has been removed in favour of `pull-request-number`.
- Input `request-to-parent` has been removed in favour of `push-to-fork`. This greatly simplifies pushing the pull request branch to a fork of the parent repository. See [Push pull request branches to a fork](concepts-guidelines.md#push-pull-request-branches-to-a-fork) for details.
e.g. ```yaml - uses: actions/checkout@v2 # Make changes to pull request here - uses: peter-evans/create-pull-request@v3 with: token: ${{ secrets.MACHINE_USER_PAT }} push-to-fork: machine-user/fork-of-repository ```
### What's new
- The action has been converted to Typescript giving it a significant performance improvement.
- If you run this action in a container, or on [self-hosted runners](https://docs.github.com/en/actions/hosting-your-own-runners), `python` and `pip` are no longer required dependencies. See [Running in a container or on self-hosted runners](concepts-guidelines.md#running-in-a-container-or-on-self-hosted-runners) for details.
- Inputs `labels`, `assignees`, `reviewers` and `team-reviewers` can now be newline separated, or comma separated. e.g. ```yml labels: | chore dependencies automated ```
## Updating from `v1` to `v2`
### Breaking changes
- `v2` now expects repositories to be checked out with `actions/checkout@v2`
To use `actions/checkout@v1` the following step to checkout the branch is necessary. ```yml - uses: actions/checkout@v1 - name: Checkout branch run: git checkout "${GITHUB_REF:11}" ```
- The two branch naming strategies have been swapped. Fixed-branch naming strategy is now the default. i.e. `branch-suffix: none` is now the default and should be removed from configuration if set.
- `author-name`, `author-email`, `committer-name`, `committer-email` have been removed in favour of `author` and `committer`. They can both be set in the format `Display Name <email@address.com>`
If neither `author` or `committer` are set the action will default to making commits as the GitHub Actions bot user.
### What's new
- Unpushed commits made during the workflow before the action runs will now be considered as changes to be raised in the pull request. See [Create your own commits](https://github.com/peter-evans/create-pull-request#create-your-own-commits) for details.- New commits made to the pull request base will now be taken into account when pull requests are updated.- If an updated pull request no longer differs from its base it will automatically be closed and the pull request branch deleted.

 11  jest.config.js

@@ -0,0 +1,11 @@module.exports = { clearMocks: true, moduleFileExtensions: ['js', 'ts'], testEnvironment: 'node', testMatch: ['**/*.test.ts'], testRunner: 'jest-circus/runner', transform: { '^.+\\.ts$': 'ts-jest' }, verbose: true}

 14,160  package-lock.json

Load diff

Large diffs are not rendered by default.

 58  package.json

@@ -0,0 +1,58 @@{ "name": "create-pull-request", "version": "4.0.0", "private": true, "description": "Creates a pull request for changes to your repository in the actions workspace", "main": "lib/main.js", "scripts": { "build": "tsc && ncc build", "format": "prettier --write '**/*.ts'", "format-check": "prettier --check '**/*.ts'", "lint": "eslint src/**/*.ts", "test:unit": "jest unit", "test:int": "__test__/integration-tests.sh", "test": "npm run test:unit && npm run test:int" }, "repository": { "type": "git", "url": "git+https://github.com/peter-evans/create-pull-request.git" }, "keywords": [ "actions", "pull", "request" ], "author": "Peter Evans", "license": "MIT", "bugs": { "url": "https://github.com/peter-evans/create-pull-request/issues" }, "homepage": "https://github.com/peter-evans/create-pull-request", "dependencies": { "@actions/core": "^1.10.0", "@actions/exec": "^1.1.1", "@octokit/core": "^3.5.1", "@octokit/plugin-paginate-rest": "^2.17.0", "@octokit/plugin-rest-endpoint-methods": "^5.13.0", "proxy-agent": "^5.0.0", "uuid": "^8.3.2" }, "devDependencies": { "@types/jest": "^27.5.0", "@types/node": "^16.11.11", "@typescript-eslint/parser": "^5.5.0", "@vercel/ncc": "^0.32.0", "eslint": "^8.3.0", "eslint-import-resolver-typescript": "^2.5.0", "eslint-plugin-github": "^4.3.5", "eslint-plugin-import": "^2.25.3", "eslint-plugin-jest": "^26.1.5", "jest": "^28.1.0", "jest-circus": "^28.1.0", "jest-environment-jsdom": "^28.1.0", "js-yaml": "^4.1.0", "prettier": "^2.5.0", "ts-jest": "^28.0.2", "typescript": "^4.5.2" }}

 294  src/create-or-update-branch.ts

@@ -0,0 +1,294 @@import * as core from '@actions/core'import {GitCommandManager} from './git-command-manager'import {v4 as uuidv4} from 'uuid'
const CHERRYPICK_EMPTY = 'The previous cherry-pick is now empty, possibly due to conflict resolution.'const NOTHING_TO_COMMIT = 'nothing to commit, working tree clean'
export enum WorkingBaseType { Branch = 'branch', Commit = 'commit'}
export async function getWorkingBaseAndType( git: GitCommandManager): Promise<[string, WorkingBaseType]> { const symbolicRefResult = await git.exec( ['symbolic-ref', 'HEAD', '--short'], true ) if (symbolicRefResult.exitCode == 0) { // A ref is checked out return [symbolicRefResult.stdout.trim(), WorkingBaseType.Branch] } else { // A commit is checked out (detached HEAD) const headSha = await git.revParse('HEAD') return [headSha, WorkingBaseType.Commit] }}
export async function tryFetch( git: GitCommandManager, remote: string, branch: string): Promise<boolean> { try { await git.fetch([`${branch}:refs/remotes/${remote}/${branch}`], remote, [ '--force' ]) return true } catch { return false }}
// Return the number of commits that branch2 is ahead of branch1async function commitsAhead( git: GitCommandManager, branch1: string, branch2: string): Promise<number> { const result = await git.revList( [`${branch1}...${branch2}`], ['--right-only', '--count'] ) return Number(result)}
// Return true if branch2 is ahead of branch1async function isAhead( git: GitCommandManager, branch1: string, branch2: string): Promise<boolean> { return (await commitsAhead(git, branch1, branch2)) > 0}
// Return the number of commits that branch2 is behind branch1async function commitsBehind( git: GitCommandManager, branch1: string, branch2: string): Promise<number> { const result = await git.revList( [`${branch1}...${branch2}`], ['--left-only', '--count'] ) return Number(result)}
// Return true if branch2 is behind branch1async function isBehind( git: GitCommandManager, branch1: string, branch2: string): Promise<boolean> { return (await commitsBehind(git, branch1, branch2)) > 0}
// Return true if branch2 is even with branch1async function isEven( git: GitCommandManager, branch1: string, branch2: string): Promise<boolean> { return ( !(await isAhead(git, branch1, branch2)) && !(await isBehind(git, branch1, branch2)) )}
function splitLines(multilineString: string): string[] { return multilineString .split('\n') .map(s => s.trim()) .filter(x => x !== '')}
export async function createOrUpdateBranch( git: GitCommandManager, commitMessage: string, base: string, branch: string, branchRemoteName: string, signoff: boolean, addPaths: string[]): Promise<CreateOrUpdateBranchResult> { // Get the working base. // When a ref, it may or may not be the actual base. // When a commit, we must rebase onto the actual base. const [workingBase, workingBaseType] = await getWorkingBaseAndType(git) core.info(`Working base is ${workingBaseType} '${workingBase}'`) if (workingBaseType == WorkingBaseType.Commit && !base) { throw new Error(`When in 'detached HEAD' state, 'base' must be supplied.`) }
// If the base is not specified it is assumed to be the working base. base = base ? base : workingBase const baseRemote = 'origin'
// Set the default return values const result: CreateOrUpdateBranchResult = { action: 'none', base: base, hasDiffWithBase: false, headSha: '' }
// Save the working base changes to a temporary branch const tempBranch = uuidv4() await git.checkout(tempBranch, 'HEAD') // Commit any uncommitted changes if (await git.isDirty(true, addPaths)) { core.info('Uncommitted changes found. Adding a commit.') const aopts = ['add'] if (addPaths.length > 0) { aopts.push(...['--', ...addPaths]) } else { aopts.push('-A') } await git.exec(aopts, true) const popts = ['-m', commitMessage] if (signoff) { popts.push('--signoff') } const commitResult = await git.commit(popts, true) // 'nothing to commit' can occur when core.autocrlf is set to true if ( commitResult.exitCode != 0 && !commitResult.stdout.includes(NOTHING_TO_COMMIT) ) { throw new Error(`Unexpected error: ${commitResult.stderr}`) } }
// Remove uncommitted tracked and untracked changes await git.exec(['reset', '--hard']) await git.exec(['clean', '-f', '-d'])
// Perform fetch and reset the working base // Commits made during the workflow will be removed if (workingBaseType == WorkingBaseType.Branch) { core.info(`Resetting working base branch '${workingBase}'`) if (branchRemoteName == 'fork') { // If pushing to a fork we must fetch with 'unshallow' to avoid the following error on git push // ! [remote rejected] HEAD -> tests/push-branch-to-fork (shallow update not allowed) await git.fetch([`${workingBase}:${workingBase}`], baseRemote, [ '--force' ]) } else { // If the remote is 'origin' we can git reset await git.checkout(workingBase) await git.exec(['reset', '--hard', `${baseRemote}/${workingBase}`]) } }
// If the working base is not the base, rebase the temp branch commits // This will also be true if the working base type is a commit if (workingBase != base) { core.info( `Rebasing commits made to ${workingBaseType} '${workingBase}' on to base branch '${base}'` ) // Checkout the actual base await git.fetch([`${base}:${base}`], baseRemote, ['--force']) await git.checkout(base) // Cherrypick commits from the temporary branch starting from the working base const commits = await git.revList( [`${workingBase}..${tempBranch}`, '.'], ['--reverse'] ) for (const commit of splitLines(commits)) { const result = await git.cherryPick( ['--strategy=recursive', '--strategy-option=theirs', commit], true ) if (result.exitCode != 0 && !result.stderr.includes(CHERRYPICK_EMPTY)) { throw new Error(`Unexpected error: ${result.stderr}`) } } // Reset the temp branch to the working index await git.checkout(tempBranch, 'HEAD') // Reset the base await git.fetch([`${base}:${base}`], baseRemote, ['--force']) }
// Try to fetch the pull request branch if (!(await tryFetch(git, branchRemoteName, branch))) { // The pull request branch does not exist core.info(`Pull request branch '${branch}' does not exist yet.`) // Create the pull request branch await git.checkout(branch, tempBranch) // Check if the pull request branch is ahead of the base result.hasDiffWithBase = await isAhead(git, base, branch) if (result.hasDiffWithBase) { result.action = 'created' core.info(`Created branch '${branch}'`) } else { core.info( `Branch '${branch}' is not ahead of base '${base}' and will not be created` ) } } else { // The pull request branch exists core.info( `Pull request branch '${branch}' already exists as remote branch '${branchRemoteName}/${branch}'` ) // Checkout the pull request branch await git.checkout(branch)
// Reset the branch if one of the following conditions is true. // - If the branch differs from the recreated temp branch. // - If the recreated temp branch is not ahead of the base. This means there will be // no pull request diff after the branch is reset. This will reset any undeleted // branches after merging. In particular, it catches a case where the branch was // squash merged but not deleted. We need to reset to make sure it doesn't appear // to have a diff with the base due to different commits for the same changes. // - If the number of commits ahead of the base branch differs between the branch and // temp branch. This catches a case where the base branch has been force pushed to // a new commit. // For changes on base this reset is equivalent to a rebase of the pull request branch. const tempBranchCommitsAhead = await commitsAhead(git, base, tempBranch) const branchCommitsAhead = await commitsAhead(git, base, branch) if ( (await git.hasDiff([`${branch}..${tempBranch}`])) || branchCommitsAhead != tempBranchCommitsAhead || !(tempBranchCommitsAhead > 0) // !isAhead ) { core.info(`Resetting '${branch}'`) // Alternatively, git switch -C branch tempBranch await git.checkout(branch, tempBranch) }
// Check if the pull request branch has been updated // If the branch was reset or updated it will be ahead // It may be behind if a reset now results in no diff with the base if (!(await isEven(git, `${branchRemoteName}/${branch}`, branch))) { result.action = 'updated' core.info(`Updated branch '${branch}'`) } else { result.action = 'not-updated' core.info( `Branch '${branch}' is even with its remote and will not be updated` ) }
// Check if the pull request branch is ahead of the base result.hasDiffWithBase = await isAhead(git, base, branch) }
// Get the pull request branch SHA result.headSha = await git.revParse('HEAD')
// Delete the temporary branch await git.exec(['branch', '--delete', '--force', tempBranch])
return result}
interface CreateOrUpdateBranchResult { action: string base: string hasDiffWithBase: boolean headSha: string}

 260  src/create-pull-request.ts

@@ -0,0 +1,260 @@import * as core from '@actions/core'import { createOrUpdateBranch, getWorkingBaseAndType, WorkingBaseType} from './create-or-update-branch'import {GitHubHelper} from './github-helper'import {GitCommandManager} from './git-command-manager'import {GitAuthHelper} from './git-auth-helper'import * as utils from './utils'
export interface Inputs { token: string path: string addPaths: string[] commitMessage: string committer: string author: string signoff: boolean branch: string deleteBranch: boolean branchSuffix: string base: string pushToFork: string title: string body: string labels: string[] assignees: string[] reviewers: string[] teamReviewers: string[] milestone: number draft: boolean}
export async function createPullRequest(inputs: Inputs): Promise<void> { let gitAuthHelper try { if (!inputs.token) { throw new Error(`Input 'token' not supplied. Unable to continue.`) }
// Get the repository path const repoPath = utils.getRepoPath(inputs.path) // Create a git command manager const git = await GitCommandManager.create(repoPath)
// Save and unset the extraheader auth config if it exists core.startGroup('Save persisted git credentials') gitAuthHelper = new GitAuthHelper(git) await gitAuthHelper.savePersistedAuth() core.endGroup()
// Init the GitHub client const githubHelper = new GitHubHelper(inputs.token)
core.startGroup('Determining the base and head repositories') // Determine the base repository from git config const remoteUrl = await git.tryGetRemoteUrl() const baseRemote = utils.getRemoteDetail(remoteUrl) // Determine the head repository; the target for the pull request branch const branchRemoteName = inputs.pushToFork ? 'fork' : 'origin' const branchRepository = inputs.pushToFork ? inputs.pushToFork : baseRemote.repository if (inputs.pushToFork) { // Check if the supplied fork is really a fork of the base core.info( `Checking if '${branchRepository}' is a fork of '${baseRemote.repository}'` ) const parentRepository = await githubHelper.getRepositoryParent( branchRepository ) if (parentRepository != baseRemote.repository) { throw new Error( `Repository '${branchRepository}' is not a fork of '${baseRemote.repository}'. Unable to continue.` ) } // Add a remote for the fork const remoteUrl = utils.getRemoteUrl( baseRemote.protocol, baseRemote.hostname, branchRepository ) await git.exec(['remote', 'add', 'fork', remoteUrl]) } core.endGroup() core.info( `Pull request branch target repository set to ${branchRepository}` )
// Configure auth if (baseRemote.protocol == 'HTTPS') { core.startGroup('Configuring credential for HTTPS authentication') await gitAuthHelper.configureToken(inputs.token) core.endGroup() }
core.startGroup('Checking the base repository state') const [workingBase, workingBaseType] = await getWorkingBaseAndType(git) core.info(`Working base is ${workingBaseType} '${workingBase}'`) // When in detached HEAD state (checked out on a commit), we need to // know the 'base' branch in order to rebase changes. if (workingBaseType == WorkingBaseType.Commit && !inputs.base) { throw new Error( `When the repository is checked out on a commit instead of a branch, the 'base' input must be supplied.` ) } // If the base is not specified it is assumed to be the working base. const base = inputs.base ? inputs.base : workingBase // Throw an error if the base and branch are not different branches // of the 'origin' remote. An identically named branch in the `fork` // remote is perfectly fine. if (branchRemoteName == 'origin' && base == inputs.branch) { throw new Error( `The 'base' and 'branch' for a pull request must be different branches. Unable to continue.` ) } // For self-hosted runners the repository state persists between runs. // This command prunes the stale remote ref when the pull request branch was // deleted after being merged or closed. Without this the push using // '--force-with-lease' fails due to "stale info." // https://github.com/peter-evans/create-pull-request/issues/633 await git.exec(['remote', 'prune', branchRemoteName]) core.endGroup()
// Apply the branch suffix if set if (inputs.branchSuffix) { switch (inputs.branchSuffix) { case 'short-commit-hash': // Suffix with the short SHA1 hash inputs.branch = `${inputs.branch}-${await git.revParse('HEAD', [ '--short' ])}` break case 'timestamp': // Suffix with the current timestamp inputs.branch = `${inputs.branch}-${utils.secondsSinceEpoch()}` break case 'random': // Suffix with a 7 character random string inputs.branch = `${inputs.branch}-${utils.randomString()}` break default: throw new Error( `Branch suffix '${inputs.branchSuffix}' is not a valid value. Unable to continue.` ) } }
// Output head branch core.info( `Pull request branch to create or update set to '${inputs.branch}'` )
// Configure the committer and author core.startGroup('Configuring the committer and author') const parsedAuthor = utils.parseDisplayNameEmail(inputs.author) const parsedCommitter = utils.parseDisplayNameEmail(inputs.committer) git.setIdentityGitOptions([ '-c', `author.name=${parsedAuthor.name}`, '-c', `author.email=${parsedAuthor.email}`, '-c', `committer.name=${parsedCommitter.name}`, '-c', `committer.email=${parsedCommitter.email}` ]) core.info( `Configured git committer as '${parsedCommitter.name} <${parsedCommitter.email}>'` ) core.info( `Configured git author as '${parsedAuthor.name} <${parsedAuthor.email}>'` ) core.endGroup()
// Create or update the pull request branch core.startGroup('Create or update the pull request branch') const result = await createOrUpdateBranch( git, inputs.commitMessage, inputs.base, inputs.branch, branchRemoteName, inputs.signoff, inputs.addPaths ) core.endGroup()
if (['created', 'updated'].includes(result.action)) { // The branch was created or updated core.startGroup( `Pushing pull request branch to '${branchRemoteName}/${inputs.branch}'` ) await git.push([ '--force-with-lease', branchRemoteName, `HEAD:refs/heads/${inputs.branch}` ]) core.endGroup() }
// Set the base. It would have been '' if not specified as an input inputs.base = result.base
if (result.hasDiffWithBase) { // Create or update the pull request core.startGroup('Create or update the pull request') const pull = await githubHelper.createOrUpdatePullRequest( inputs, baseRemote.repository, branchRepository ) core.endGroup()
// Set outputs core.startGroup('Setting outputs') core.setOutput('pull-request-number', pull.number) core.setOutput('pull-request-url', pull.html_url) if (pull.created) { core.setOutput('pull-request-operation', 'created') } else if (result.action == 'updated') { core.setOutput('pull-request-operation', 'updated') } core.setOutput('pull-request-head-sha', result.headSha) // Deprecated core.exportVariable('PULL_REQUEST_NUMBER', pull.number) core.endGroup() } else { // There is no longer a diff with the base // Check we are in a state where a branch exists if (['updated', 'not-updated'].includes(result.action)) { core.info( `Branch '${inputs.branch}' no longer differs from base branch '${inputs.base}'` ) if (inputs.deleteBranch) { core.info(`Deleting branch '${inputs.branch}'`) await git.push([ '--delete', '--force', branchRemoteName, `refs/heads/${inputs.branch}` ]) // Set outputs core.startGroup('Setting outputs') core.setOutput('pull-request-operation', 'closed') core.endGroup() } } } } catch (error) { core.setFailed(utils.getErrorMessage(error)) } finally { // Remove auth and restore persisted auth config if it existed core.startGroup('Restore persisted git credentials') await gitAuthHelper.removeAuth() await gitAuthHelper.restorePersistedAuth() core.endGroup() }}

 127  src/git-auth-helper.ts

@@ -0,0 +1,127 @@import * as core from '@actions/core'import * as fs from 'fs'import {GitCommandManager} from './git-command-manager'import * as path from 'path'import {URL} from 'url'import * as utils from './utils'
export class GitAuthHelper { private git: GitCommandManager private gitConfigPath: string private extraheaderConfigKey: string private extraheaderConfigPlaceholderValue = 'AUTHORIZATION: basic ***' private extraheaderConfigValueRegex = '^AUTHORIZATION:' private persistedExtraheaderConfigValue = ''
constructor(git: GitCommandManager) { this.git = git this.gitConfigPath = path.join( this.git.getWorkingDirectory(), '.git', 'config' ) const serverUrl = this.getServerUrl() this.extraheaderConfigKey = `http.${serverUrl.origin}/.extraheader` }
async savePersistedAuth(): Promise<void> { // Save and unset persisted extraheader credential in git config if it exists this.persistedExtraheaderConfigValue = await this.getAndUnset() }
async restorePersistedAuth(): Promise<void> { if (this.persistedExtraheaderConfigValue) { try { await this.setExtraheaderConfig(this.persistedExtraheaderConfigValue) core.info('Persisted git credentials restored') } catch (e) { core.warning(utils.getErrorMessage(e)) } } }
async configureToken(token: string): Promise<void> { // Encode and configure the basic credential for HTTPS access const basicCredential = Buffer.from( `x-access-token:${token}`, 'utf8' ).toString('base64') core.setSecret(basicCredential) const extraheaderConfigValue = `AUTHORIZATION: basic ${basicCredential}` await this.setExtraheaderConfig(extraheaderConfigValue) }
async removeAuth(): Promise<void> { await this.getAndUnset() }
private async setExtraheaderConfig( extraheaderConfigValue: string ): Promise<void> { // Configure a placeholder value. This approach avoids the credential being captured // by process creation audit events, which are commonly logged. For more information, // refer to https://docs.microsoft.com/en-us/windows-server/identity/ad-ds/manage/component-updates/command-line-process-auditing // See https://github.com/actions/checkout/blob/main/src/git-auth-helper.ts#L267-L274 await this.git.config( this.extraheaderConfigKey, this.extraheaderConfigPlaceholderValue ) // Replace the placeholder await this.gitConfigStringReplace( this.extraheaderConfigPlaceholderValue, extraheaderConfigValue ) }
private async getAndUnset(): Promise<string> { let configValue = '' // Save and unset persisted extraheader credential in git config if it exists if ( await this.git.configExists( this.extraheaderConfigKey, this.extraheaderConfigValueRegex ) ) { configValue = await this.git.getConfigValue( this.extraheaderConfigKey, this.extraheaderConfigValueRegex ) if ( await this.git.tryConfigUnset( this.extraheaderConfigKey, this.extraheaderConfigValueRegex ) ) { core.info(`Unset config key '${this.extraheaderConfigKey}'`) } else { core.warning( `Failed to unset config key '${this.extraheaderConfigKey}'` ) } } return configValue }
private async gitConfigStringReplace( find: string, replace: string ): Promise<void> { let content = (await fs.promises.readFile(this.gitConfigPath)).toString() const index = content.indexOf(find) if (index < 0 || index != content.lastIndexOf(find)) { throw new Error(`Unable to replace '${find}' in ${this.gitConfigPath}`) } content = content.replace(find, replace) await fs.promises.writeFile(this.gitConfigPath, content) }
private getServerUrl(): URL { // todo: remove GITHUB_URL after support for GHES Alpha is no longer needed // See https://github.com/actions/checkout/blob/main/src/url-helper.ts#L22-L29 return new URL( process.env['GITHUB_SERVER_URL'] || process.env['GITHUB_URL'] || 'https://github.com' ) }}

 303  src/git-command-manager.ts

@@ -0,0 +1,303 @@import * as exec from '@actions/exec'import * as io from '@actions/io'import * as utils from './utils'import * as path from 'path'
const tagsRefSpec = '+refs/tags/*:refs/tags/*'
export class GitCommandManager { private gitPath: string private workingDirectory: string // Git options used when commands require an identity private identityGitOptions?: string[]
private constructor(workingDirectory: string, gitPath: string) { this.workingDirectory = workingDirectory this.gitPath = gitPath }
static async create(workingDirectory: string): Promise<GitCommandManager> { const gitPath = await io.which('git', true) return new GitCommandManager(workingDirectory, gitPath) }
setIdentityGitOptions(identityGitOptions: string[]): void { this.identityGitOptions = identityGitOptions }
async checkout(ref: string, startPoint?: string): Promise<void> { const args = ['checkout', '--progress'] if (startPoint) { args.push('-B', ref, startPoint) } else { args.push(ref) } // https://github.com/git/git/commit/a047fafc7866cc4087201e284dc1f53e8f9a32d5 args.push('--') await this.exec(args) }
async cherryPick( options?: string[], allowAllExitCodes = false ): Promise<GitOutput> { const args = ['cherry-pick'] if (this.identityGitOptions) { args.unshift(...this.identityGitOptions) }
if (options) { args.push(...options) }
return await this.exec(args, allowAllExitCodes) }
async commit( options?: string[], allowAllExitCodes = false ): Promise<GitOutput> { const args = ['commit'] if (this.identityGitOptions) { args.unshift(...this.identityGitOptions) }
if (options) { args.push(...options) }
return await this.exec(args, allowAllExitCodes) }
async config( configKey: string, configValue: string, globalConfig?: boolean ): Promise<void> { await this.exec([ 'config', globalConfig ? '--global' : '--local', configKey, configValue ]) }
async configExists( configKey: string, configValue = '.', globalConfig?: boolean ): Promise<boolean> { const output = await this.exec( [ 'config', globalConfig ? '--global' : '--local', '--name-only', '--get-regexp', configKey, configValue ], true ) return output.exitCode === 0 }
async fetch( refSpec: string[], remoteName?: string, options?: string[] ): Promise<void> { const args = ['-c', 'protocol.version=2', 'fetch'] if (!refSpec.some(x => x === tagsRefSpec)) { args.push('--no-tags') }
args.push('--progress', '--no-recurse-submodules') if ( utils.fileExistsSync(path.join(this.workingDirectory, '.git', 'shallow')) ) { args.push('--unshallow') }
if (options) { args.push(...options) }
if (remoteName) { args.push(remoteName) } else { args.push('origin') } for (const arg of refSpec) { args.push(arg) }
await this.exec(args) }
async getConfigValue(configKey: string, configValue = '.'): Promise<string> { const output = await this.exec([ 'config', '--local', '--get-regexp', configKey, configValue ]) return output.stdout.trim().split(`${configKey} `)[1] }
getWorkingDirectory(): string { return this.workingDirectory }
async hasDiff(options?: string[]): Promise<boolean> { const args = ['diff', '--quiet'] if (options) { args.push(...options) } const output = await this.exec(args, true) return output.exitCode === 1 }
async isDirty(untracked: boolean, pathspec?: string[]): Promise<boolean> { const pathspecArgs = pathspec ? ['--', ...pathspec] : [] // Check untracked changes const sargs = ['--porcelain', '-unormal'] sargs.push(...pathspecArgs) if (untracked && (await this.status(sargs))) { return true } // Check working index changes if (await this.hasDiff(pathspecArgs)) { return true } // Check staged changes const dargs = ['--staged'] dargs.push(...pathspecArgs) if (await this.hasDiff(dargs)) { return true } return false }
async push(options?: string[]): Promise<void> { const args = ['push'] if (options) { args.push(...options) } await this.exec(args) }
async revList( commitExpression: string[], options?: string[] ): Promise<string> { const args = ['rev-list'] if (options) { args.push(...options) } args.push(...commitExpression) const output = await this.exec(args) return output.stdout.trim() }
async revParse(ref: string, options?: string[]): Promise<string> { const args = ['rev-parse'] if (options) { args.push(...options) } args.push(ref) const output = await this.exec(args) return output.stdout.trim() }
async status(options?: string[]): Promise<string> { const args = ['status'] if (options) { args.push(...options) } const output = await this.exec(args) return output.stdout.trim() }
async symbolicRef(ref: string, options?: string[]): Promise<string> { const args = ['symbolic-ref', ref] if (options) { args.push(...options) } const output = await this.exec(args) return output.stdout.trim() }
async tryConfigUnset( configKey: string, configValue = '.', globalConfig?: boolean ): Promise<boolean> { const output = await this.exec( [ 'config', globalConfig ? '--global' : '--local', '--unset', configKey, configValue ], true ) return output.exitCode === 0 }
async tryGetRemoteUrl(): Promise<string> { const output = await this.exec( ['config', '--local', '--get', 'remote.origin.url'], true )
if (output.exitCode !== 0) { return '' }
const stdout = output.stdout.trim() if (stdout.includes('\n')) { return '' }
return stdout }
async exec(args: string[], allowAllExitCodes = false): Promise<GitOutput> { const result = new GitOutput()
const env = {} for (const key of Object.keys(process.env)) { env[key] = process.env[key] }
const stdout: string[] = [] const stderr: string[] = []
const options = { cwd: this.workingDirectory, env, ignoreReturnCode: allowAllExitCodes, listeners: { stdout: (data: Buffer) => { stdout.push(data.toString()) }, stderr: (data: Buffer) => { stderr.push(data.toString()) } } }
result.exitCode = await exec.exec(`"${this.gitPath}"`, args, options) result.stdout = stdout.join('') result.stderr = stderr.join('') return result }}
class GitOutput { stdout = '' stderr = '' exitCode = 0}

 183  src/github-helper.ts

@@ -0,0 +1,183 @@import * as core from '@actions/core'import {Inputs} from './create-pull-request'import {Octokit, OctokitOptions} from './octokit-client'import * as utils from './utils'
const ERROR_PR_REVIEW_FROM_AUTHOR = 'Review cannot be requested from pull request author'
interface Repository { owner: string repo: string}
interface Pull { number: number html_url: string created: boolean}
export class GitHubHelper { private octokit: InstanceType<typeof Octokit>
constructor(token: string) { const options: OctokitOptions = {} if (token) { options.auth = `${token}` } options.baseUrl = process.env['GITHUB_API_URL'] || 'https://api.github.com' this.octokit = new Octokit(options) }
private parseRepository(repository: string): Repository { const [owner, repo] = repository.split('/') return { owner: owner, repo: repo } }
private async createOrUpdate( inputs: Inputs, baseRepository: string, headRepository: string ): Promise<Pull> { const [headOwner] = headRepository.split('/') const headBranch = `${headOwner}:${inputs.branch}` const headBranchFull = `${headRepository}:${inputs.branch}`
// Try to create the pull request try { core.info(`Attempting creation of pull request`) const {data: pull} = await this.octokit.rest.pulls.create({ ...this.parseRepository(baseRepository), title: inputs.title, head: headBranch, base: inputs.base, body: inputs.body, draft: inputs.draft }) core.info( `Created pull request #${pull.number} (${headBranch} => ${inputs.base})` ) return { number: pull.number, html_url: pull.html_url, created: true } } catch (e) { if ( utils.getErrorMessage(e).includes(`A pull request already exists for`) ) { core.info(`A pull request already exists for ${headBranch}`) } else { throw e } }
// Update the pull request that exists for this branch and base core.info(`Fetching existing pull request`) const {data: pulls} = await this.octokit.rest.pulls.list({ ...this.parseRepository(baseRepository), state: 'open', head: headBranchFull, base: inputs.base }) core.info(`Attempting update of pull request`) const {data: pull} = await this.octokit.rest.pulls.update({ ...this.parseRepository(baseRepository), pull_number: pulls[0].number, title: inputs.title, body: inputs.body }) core.info( `Updated pull request #${pull.number} (${headBranch} => ${inputs.base})` ) return { number: pull.number, html_url: pull.html_url, created: false } }
async getRepositoryParent(headRepository: string): Promise<string> { const {data: headRepo} = await this.octokit.rest.repos.get({ ...this.parseRepository(headRepository) }) if (!headRepo.parent) { throw new Error( `Repository '${headRepository}' is not a fork. Unable to continue.` ) } return headRepo.parent.full_name }
async createOrUpdatePullRequest( inputs: Inputs, baseRepository: string, headRepository: string ): Promise<Pull> { // Create or update the pull request const pull = await this.createOrUpdate( inputs, baseRepository, headRepository )
// Apply milestone if (inputs.milestone) { core.info(`Applying milestone '${inputs.milestone}'`) await this.octokit.rest.issues.update({ ...this.parseRepository(baseRepository), issue_number: pull.number, milestone: inputs.milestone }) } // Apply labels if (inputs.labels.length > 0) { core.info(`Applying labels '${inputs.labels}'`) await this.octokit.rest.issues.addLabels({ ...this.parseRepository(baseRepository), issue_number: pull.number, labels: inputs.labels }) } // Apply assignees if (inputs.assignees.length > 0) { core.info(`Applying assignees '${inputs.assignees}'`) await this.octokit.rest.issues.addAssignees({ ...this.parseRepository(baseRepository), issue_number: pull.number, assignees: inputs.assignees }) }
// Request reviewers and team reviewers const requestReviewersParams = {} if (inputs.reviewers.length > 0) { requestReviewersParams['reviewers'] = inputs.reviewers core.info(`Requesting reviewers '${inputs.reviewers}'`) } if (inputs.teamReviewers.length > 0) { requestReviewersParams['team_reviewers'] = inputs.teamReviewers core.info(`Requesting team reviewers '${inputs.teamReviewers}'`) } if (Object.keys(requestReviewersParams).length > 0) { try { await this.octokit.rest.pulls.requestReviewers({ ...this.parseRepository(baseRepository), pull_number: pull.number, ...requestReviewersParams }) } catch (e) { if (utils.getErrorMessage(e).includes(ERROR_PR_REVIEW_FROM_AUTHOR)) { core.warning(ERROR_PR_REVIEW_FROM_AUTHOR) } else { throw e } } }
return pull }}

 38  src/main.ts

@@ -0,0 +1,38 @@import * as core from '@actions/core'import {Inputs, createPullRequest} from './create-pull-request'import {inspect} from 'util'import * as utils from './utils'
async function run(): Promise<void> { try { const inputs: Inputs = { token: core.getInput('token'), path: core.getInput('path'), addPaths: utils.getInputAsArray('add-paths'), commitMessage: core.getInput('commit-message'), committer: core.getInput('committer'), author: core.getInput('author'), signoff: core.getBooleanInput('signoff'), branch: core.getInput('branch'), deleteBranch: core.getBooleanInput('delete-branch'), branchSuffix: core.getInput('branch-suffix'), base: core.getInput('base'), pushToFork: core.getInput('push-to-fork'), title: core.getInput('title'), body: core.getInput('body'), labels: utils.getInputAsArray('labels'), assignees: utils.getInputAsArray('assignees'), reviewers: utils.getInputAsArray('reviewers'), teamReviewers: utils.getInputAsArray('team-reviewers'), milestone: Number(core.getInput('milestone')), draft: core.getBooleanInput('draft') } core.debug(`Inputs: ${inspect(inputs)}`)
await createPullRequest(inputs) } catch (error) { core.setFailed(utils.getErrorMessage(error)) }}
run()

 28  src/octokit-client.ts

@@ -0,0 +1,28 @@import {Octokit as Core} from '@octokit/core'import {paginateRest} from '@octokit/plugin-paginate-rest'import {restEndpointMethods} from '@octokit/plugin-rest-endpoint-methods'import ProxyAgent from 'proxy-agent'export {RestEndpointMethodTypes} from '@octokit/plugin-rest-endpoint-methods'export {OctokitOptions} from '@octokit/core/dist-types/types'
export const Octokit = Core.plugin( paginateRest, restEndpointMethods, autoProxyAgent)
// Octokit plugin to support the standard environment variables http_proxy, https_proxy and no_proxyfunction autoProxyAgent(octokit: Core) { const proxy = process.env.https_proxy || process.env.HTTPS_PROXY || process.env.http_proxy || process.env.HTTP_PROXY
if (!proxy) return
const agent = new ProxyAgent() octokit.hook.before('request', options => { options.request.agent = agent })}

 170  src/utils.ts

@@ -0,0 +1,170 @@import * as core from '@actions/core'import * as fs from 'fs'import * as path from 'path'
export function getInputAsArray( name: string, options?: core.InputOptions): string[] { return getStringAsArray(core.getInput(name, options))}
export function getStringAsArray(str: string): string[] { return str .split(/[\n,]+/) .map(s => s.trim()) .filter(x => x !== '')}
export function getRepoPath(relativePath?: string): string { let githubWorkspacePath = process.env['GITHUB_WORKSPACE'] if (!githubWorkspacePath) { throw new Error('GITHUB_WORKSPACE not defined') } githubWorkspacePath = path.resolve(githubWorkspacePath) core.debug(`githubWorkspacePath: ${githubWorkspacePath}`)
let repoPath = githubWorkspacePath if (relativePath) repoPath = path.resolve(repoPath, relativePath)
core.debug(`repoPath: ${repoPath}`) return repoPath}
interface RemoteDetail { hostname: string protocol: string repository: string}
export function getRemoteDetail(remoteUrl: string): RemoteDetail { // Parse the protocol and github repository from a URL // e.g. HTTPS, peter-evans/create-pull-request const githubUrl = process.env['GITHUB_SERVER_URL'] || 'https://github.com'
const githubServerMatch = githubUrl.match(/^https?:\/\/(.+)$/i) if (!githubServerMatch) { throw new Error('Could not parse GitHub Server name') }
const hostname = githubServerMatch[1]
const httpsUrlPattern = new RegExp( '^https?://.*@?' + hostname + '/(.+/.+?)(\\.git)?$', 'i' ) const sshUrlPattern = new RegExp('^git@' + hostname + ':(.+/.+)\\.git$', 'i')
const httpsMatch = remoteUrl.match(httpsUrlPattern) if (httpsMatch) { return { hostname, protocol: 'HTTPS', repository: httpsMatch[1] } }
const sshMatch = remoteUrl.match(sshUrlPattern) if (sshMatch) { return { hostname, protocol: 'SSH', repository: sshMatch[1] } }
throw new Error( `The format of '${remoteUrl}' is not a valid GitHub repository URL` )}
export function getRemoteUrl( protocol: string, hostname: string, repository: string): string { return protocol == 'HTTPS' ? `https://${hostname}/${repository}` : `git@${hostname}:${repository}.git`}
export function secondsSinceEpoch(): number { const now = new Date() return Math.round(now.getTime() / 1000)}
export function randomString(): string { return Math.random().toString(36).substr(2, 7)}
interface DisplayNameEmail { name: string email: string}
export function parseDisplayNameEmail( displayNameEmail: string): DisplayNameEmail { // Parse the name and email address from a string in the following format // Display Name <email@address.com> const pattern = /^([^<]+)\s*<([^>]+)>$/i
// Check we have a match const match = displayNameEmail.match(pattern) if (!match) { throw new Error( `The format of '${displayNameEmail}' is not a valid email address with display name` ) }
// Check that name and email are not just whitespace const name = match[1].trim() const email = match[2].trim() if (!name || !email) { throw new Error( `The format of '${displayNameEmail}' is not a valid email address with display name` ) }
return { name: name, email: email }}
export function fileExistsSync(path: string): boolean { if (!path) { throw new Error("Arg 'path' must not be empty") }
let stats: fs.Stats try { stats = fs.statSync(path) } catch (error) { if (hasErrorCode(error) && error.code === 'ENOENT') { return false }
throw new Error( `Encountered an error when checking whether path '${path}' exists: ${getErrorMessage( error )}` ) }
if (!stats.isDirectory()) { return true }
return false}
/* eslint-disable @typescript-eslint/no-explicit-any */function hasErrorCode(error: any): error is {code: string} { return typeof (error && error.code) === 'string'}
export function getErrorMessage(error: unknown) { if (error instanceof Error) return error.message return String(error)}

 16  tsconfig.json

@@ -0,0 +1,16 @@{ "compilerOptions": { "target": "es6", "module": "commonjs", "lib": [ "es6" ], "outDir": "./lib", "rootDir": "./src", "declaration": true, "strict": true, "noImplicitAny": false, "esModuleInterop": true }, "exclude": ["__test__", "lib", "node_modules"]}

Footer

© 2022 GitHub, Inc.

Footer navigation

Terms

Privacy

Security

Status

Docs

Contact GitHub

Pricing

API

Training

Blog

About

Comparing master...patch-21 · zakwarlord7/ci-CI






> Be accurate --- Ensure the Estate name on the check matches our records exactly.  
> Consult a professional --- Consult a lawyer mail in all aspects of  administering the Estate, 
> seek advice from the lawyer or accountant on taxes owed by the Estate.  
> Separate the Estate from your own finances --- 
> law forbids mingling the Estate with your personal finances.  
> Keep good records --- 
> Account for every transaction 
> Keep records of all canceled checks-and-reciepts in a safe place.  
> These are just some important points in Estate administration.  
> We would be happy to advise you of ways to 
> find answers to your other questions.  
> Checking Account 
> Identification
> 9-digit Routing                  Account Number
> _______|_______      ______________|___________     
> |                           |      |                                                 |
> |:071921891|:|        |4720416547||'                     |Routing/Transit Number and Account Numbers shown to the left are for your new account.  
Refer to them when making deposits and making withdrawls quickly and accuratly.  
Keep this and all business financial information secure.  
Be sure to ask about ordering checks for your new account.
________________________________________________________________________________________________________________________________________
________________________________________________________________________________________________________________________________________   
_________________________________([$OBJ]  S e c u r i t y  e n h a n c e d  d o c u m e n t.    S e e   b a c k   f o r   d e t a i l s .     [$OBJ])_________________________________
_________________________________________________________________________________________________________________________________________________________________________________
                                               NO.
                                                              [$OBJ]PNCBANK                                                                                                                                                                                                                              
                                                                           PNC Bank, N.A.      071
                                                                                                                                                                                                                              DATE_________________________7_0_-_2189/719
                                                                                   
                                                                                   7364
PAY TO THE ORDER OF_____________________________________________________________________________________________________________________________________________________| $ |____________________________|

_______________________________________________VOID____________________________________________________________________________________________________________DOLLARS [$OBJ] Security

                                                                         Features

                                                                         Detaile on

                                                                         Back.
ESTATE OF
                                                                                                                                        
                                                                                MP } EXECUTOR/                                 {AUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHO                                                                                       
RIZEDSIGNATUREAUTHORIZEDSIGNATURE}ADMINISTRATOR
                                                                                                                                        
                                                                                MP }PERSONAL                                   {AUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHO                                                                                       
RIZEDSIGNATUREAUTHORIZEDSIGNATURE} REPRESENTATIVE
                                                                                                                                        
                                                                                MP } TRUSTEE                                  {AUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHO                                                                                       
RIZEDSIGNATUREAUTHORIZEDSIGNATURE}


a071921891a_4720416547c
                                




______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
________________________________________________________________________________________________________________________________________   
_________________________________([$OBJ]  S e c u r i t y  e n h a n c e d  d o c u m e n t.    S e e   b a c k   f o r   d e t a i l s .     [$OBJ])_________________________________
_________________________________________________________________________________________________________________________________________________________________________________
                                               NO.
                                                              [$OBJ]PNCBANK                                                                                                                                                                                                                              
                                                                           PNC Bank, N.A.      071
                                                                                                                                                                                                                              DATE_________________________7_0_-_2189/719
                                                                                   
                                                                                   7364
PAY TO THE ORDER OF_____________________________________________________________________________________________________________________________________________________| $ |____________________________|

_______________________________________________VOID____________________________________________________________________________________________________________DOLLARS [$OBJ] Security

                                                                         Features

                                                                         Detaile on

                                                                         Back.
ESTATE OF
                                                                                                                                        
                                                                                MP } EXECUTOR/                                 {AUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHO                                                                                       
RIZEDSIGNATUREAUTHORIZEDSIGNATURE}ADMINISTRATOR
                                                                                                                                        
                                                                                MP }PERSONAL                                   {AUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHO                                                                                       
RIZEDSIGNATUREAUTHORIZEDSIGNATURE} REPRESENTATIVE
                                                                                                                                        
                                                                                MP } TRUSTEE                                  {AUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHORIZEDSIGNATUREAUTHO                                                                                       
RIZEDSIGNATUREAUTHORIZEDSIGNATURE}




a071921891a_4720416547c
                                         



____________________________________________________________________________________________________________________________________________________________________________________                                                                                                                       
Form 1040-V 2021                                                                                                                                                                                                                              Page 2
________________________________________________________________________________________________________________________________________
IF you live in...                                                                                                                                                  THEN use this address to send in your payment... 
________________________________________________________________________________________________________________________________________
Alabama, Florida, Georgia, Louisiana, Missippi, North Carolina, South Carolina, Tennessee, Texas|  Internal Revenue Service 
                                                                                                                                                                                               |            P.O. Box 1214 
                                                                                                                                                                                               | Charolette, NC 28201-1214
________________________________________________________________________________________________________________________________________
TO PAY YOUR TAXES DUE BY CHECK, MAIL THIS FORM TO THE THE ADDRESS LISTED BELOW
________________________________________________________________________________________________________________________________________
                                                                                                                                                                                                                                Form 1040-V       2021
 ----------------------------------------------------------<  Detach Here and Mail With Your Payment and Return  >---------------------------------------------------------------------
Department of the Treasury 2021 Form 1040-V Payment Voucher
Internal Revenue Service      (99)  
> Use this voucher when making a payment with Form 1040.
> Do not staple this voucher or your payment to Form 1040.
> Make your check or money order payable to the 'United States Treasury. '______________________________________________________________
> Write your Social Security nnumber (SSN) on your check or money order. | Enter the amount of your payment . . . . . . . .>|      7,567,263,607.      |                                                                                                                 REV 04/09/22 INTUIT.CG.      1555                                   ________________________________________________________________________________________________________________________________________
ZACHRY T WOOD
                                                                                                                                                     INTERNAL  REVENUE SERVICE
5222 BRADFORD DR                                                                                                             P.O. BOX 1214
DALLAS TX 75235-8313                                                                                                     CHARLOTTE, NC 28201-1214
'''
'''
'''
'''
'''
'______________633441725__BH__WOOD__30__0__202112__610


Other Adjustments to Net Income Available to Common Stockholders
Discontinued Operations                                                                                                                                                                      76,033,000,000.00 20,642,000,000 18,936,000,000
Reported Normalized and Operating Income/Expense
Supplemental Section
Total Revenue as Reported, Supplemental                                                                                                                                                         25763700000000  7532500000000 6511800000000 6188000000000 5531400000000 5689800000000 4617300000000 3829700000000 4115900000000 4607500000000 404990000000                                        
Total Operating Profit/Loss as Reported, Supplemental                                                                                                                                          787140000000000  2188500000000 2103100000000 1936100000000 1643700000000 1565100000000 1121300000000 638300000000 797700000000 926600000000 91770000000
0eported Effective Tax Rate                                                                                                                                                                               0.162           0.179         0.157         0.158         0.158         0.159         0.119          0.181
eported Normalized Income     
eported Normalized Operating Profit                                                                                                                                                                                                                                                                                                                                                                                                                                              
ther Adjustments to Net Income Available to Common Stockholder
Discontinued Operations Basic EPS                                                                                                                                                                       $312.65 113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 Basic EPS from Continuing Operations 113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operatio
Diluted EPS                                                                                                                                                                                             $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35  10.12 312.65
Diluted EPS from Continuing Operations                                                                                                                                                                  $112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operati 
Basic Weighted Average Shares Outstanding                                                                                                                                                          66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68646500000 68880400000 69274100000
Diluted Weighted Average Shares Outstanding                                                                                                                                                        67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 69519300000 69819900000 
Reported Normalized Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            $$2583.87
Basic EPS                                                                                                                                                                                               $11388 31150 28440 27690 26630 22540 16550 10210 00996 15490 10210
Diluted EPS                                                                                                                                                                                             $11220 30690 27990 27260 26290 22300 16400 10130 00987 15350 10190 
Basic WASO                                                                                                                                                                                         66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68465000000 688804000000 692741000000
Diluted WASO                                                                                                                                                                                       67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 695193000000 6981990000000
Basic EPS                                                                                                                                                                                               113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations                                                                                                                                                                    113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operation
Diluted EPS                                                                                                                                                                                             112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.1
Diluted EPS from Continuing Operations                                                                                                                                                                  112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations
Basic Weighted Average Shares Outstanding                                                                                                                                                                                                       667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted Weighted Average Shares Outstanding                                                                                                                                                                                                               677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Reported Normalized Diluted EPS                                                                                                                                                                                                                                312.65
Basic EPS                                                                                                                                                                                                                                                      113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2 1
Diluted EPS                                                                                                                                                                                                                                                    112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Basic WASO                                                                                                                                                                                                                                                667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted WASO                                                                                                                                                                                                                                              677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Change in Cash                                                                                                                                                                                                                                            20,945,000,000 23,719,000,000 23,630,000,000 26,622,000,000 26,465,000,000
Effect of Exchange Rate Changes                                                                                                                                                                                                                           25930000000 235000000000                                                                                                                                                                                                                                                           3,175,000,000 300,000,000 6,126,000,000-
Cash and Cash Equivalents, Beginning of Period                                                                                                                                                                                                            181000000000 146000000000 183,000,000 -143,000,000 210,000,000Cash Flow Supplemental Section                                                                                                                                                                                                                                                   23,719,000,000,000.00 $26,622,000,000,000.00 $26,465,000,000,000.00 $20,129,000,000,000.00
Change in Cash as Reported, Supplemental                                                                                                                                                                                                                  2,774,000,000 89,000,000 -2,992,000,000 6,336,000,000
Income Tax Paid, Supplemental                                                                                                                                                                                                                             13,412,000,000 157,000,000
Repayments for Long Term Debt                                                                                                                                                                                                                             182527 161857
Costs and expenses:
Cost of revenues                                                                                                                                                                                                                                          84732 71896
Research and development                                                                                                                                                                                                                                  27573 26018
Sales and marketing                                                                                                                                                                                                                                       17946 18464
General and administrative                                                                                                                                                                                                                                11052 9551
European Commission fines                                                                                                                                                                                                                                 0 1697
Total costs and expenses                                                                                                                                                                                                                                  141303 127626
Income from operations                                                                                                                                                                                                                                    41224 34231
Other income (expense), net                                                                                                                                                                                                                               6858000000 5394
Income before income taxes                                                                                                                                                                                                                         22,677,000,000 19,289,000,000
Provision for income taxes                                                                                                                                                                                                                         22,677,000,000 19,289,000,000+Net income 22,677,000,000 19,289,000,000                                                                                                                                                                                                                                                         18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00                                                                                                                                                                                                                                                          18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00                                                                                                                                                                                                                                                          18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00                                                                                                                                                                                                                                                          18,936,000,000.00                                              
3/6/2022 at 5:47 PM 
Q4 2021 Q3 2021 Q2 2021 Q1 2021 Q4 2020
GOOGL_income-statement_Quarterly_As_Originally_Reported                                                                                                                                                                                                24,934,000,000 25,539,000,000 37,497,000,000 31,211,000,000 30,818,000,000 24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
Cash Flow from Operating Activities, Indirect                                                                                                                                                                                                          24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
Net Cash Flow from Continuing Operating Activities, Indirect                                                                                                                                                                                           20,642,000,000 18,936,000,000 18,525,000,000 17,930,000,000 15,227,000,000
Cash Generated from Operating Activities                                                                                                                                                                                                                6,517,000,000 3,797,000,000 4,236,000,000 2,592,000,000 5,748,000,000
Income/Loss before Non-Cash Adjustment                                                                                                                                                                                                                  3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Total Adjustments for Non-Cash Items                                                                                                                                                                                                                    3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Depreciation, Amortization and Depletion, Non-Cash Adjustment                                                                                                                                                                                           3,215,000,000 3,085,000,000 2,730,000,000 2,525,000,000 3,539,000,000
Depreciation and Amortization, Non-Cash Adjustment                                                                                                                                                                                                        224,000,000 219,000,000 215,000,000 228,000,000 186,000,000
Depreciation, Non-Cash Adjustment                                                                                                                                                                                                                       3,954,000,000 3,874,000,000 3,803,000,000 3,745,000,000 3,223,000,000
Amortization, Non-Cash Adjustment                                                                                                                                                                                                                       1,616,000,000 1,287,000,000- 379,000,000 1,100,000,000 1,670,000,000
Stock-Based Compensation, Non-Cash Adjustment                                                                                                                                                                                                           2,478,000,000- 2,158,000,000- 2,883,000,000- 4,751,000,000- 3,262,000,000-
Taxes, Non-Cash Adjustment                                                                                                                                                                                                                              2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000-
Investment Income/Loss, Non-Cash Adjustment                                                                                                                                                                                                                14,000,000 64,000,000- 8,000,000- 255,000,000 392,000,000-
Gain/Loss on Financial Instruments, Non-Cash Adjustment                                                                                                                                                                                                 2,225,000,000 2,806,000,000- 871,000,000- 1,233,000,000 1,702,000,000-
Other Non-Cash Items                                                                                                                                                                                                                                    5,819,000,000- 2,409,000,000- 3,661,000,000 2,794,000,000- 5,445,000,000-
Changes in Operating Capital                                                                                                                                                                                                                            5,819,000,000- 2,409,000,000- 3,661,000,000 2,794,000,000- 5,445,000,000-
Change in Trade and Other Receivables                                                                                                                                                                                                                     399,000,000- 1,255,000,000- 199,000,000 7,000,000- 738,000,000-
Change in Trade/Accounts Receivable                                                                                                                                                                                                                     6,994,000,000 3,157,000,000 4,074,000,000- 4,956,000,000 6,938,000,000
Change in Other Current Assets                                                                                                                                                                                                                          1,157,000,000 238,000,000- 130,000,000- 982,000,000 963,000,000
Change in Payables and Accrued Expenses                                                                                                                                                                                                                 1,157,000,000 238,000,000- 130,000,000- 982,000,000 963,000,000
Change in Trade and Other Payables                                                                                                                                                                                                                      5,837,000,000 2,919,000,000 4,204,000,000- 3,974,000,000 5,975,000,000
Change in Trade/Accounts Payable                                                                                                                                                                                                                          368,000,000 272,000,000- 3,000,000 137,000,000 207,000,000
Change in Accrued Expenses                                                                                                                                                                                                                              3,369,000,000 3,041,000,000- 1,082,000,000 785,000,000 740,000,000
Change in Deferred Assets/Liabilities 
Change in Other Operating Capital                                                                                                                                                                                                                      11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
Change in Prepayments and Deposits                                                                                                                                                                                                                     11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
Cash Flow from Investing Activities 
Cash Flow from Continuing Investing Activities                                                                                                                                                                                                         6,383,000,000- 6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000 -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000
Purchase/Sale and Disposal of Property, Plant and Equipment,Net 
Purchase of Property, Plant and Equipment                                                                                                                                                                                                                385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
Sale and Disposal of Property, Plant and Equipment                                                                                                                                                                                                       385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
Purchase/Sale of Business, Net                                                                                                                                                                                                                         4,348,000,000 3,360,000,000 3,293,000,000 2,195,000,000 -1,375,000,000
Purchase/Acquisition of Business                                                                                                                                                                                                                      40,860,000,000 35,153,000,000 24,949,000,000 -37,072,000,000 -36,955,000,000
Purchase/Sale of Investments, NetPurchase of Investments                                                                                                                                                                                              36,512,000,000 31,793,000,000 21,656,000,000 39,267,000,000 35,580,000,000 100,000,000 388,000,000 23,000,000 30,000,000 -57,000,000Sale of Investments
Other Investing Cash Flow                                                                                                                                                                                                                             15,254,000,000
Purchase/Sale of Other Non-Current Assets, Net                                                                                                                                                                                                        16,511,000,000 15,254,000,000 15,991,000,000 13,606,000,000 9,270,000,000
Sales of Other Non-Current Assets                                                                                                                                                                                                                     16,511,000,000 12,610,000,000 15,991,000,000 13,606,000,000 9,270,000,000
Cash Flow from Financing Activities                                                                                                                                                                                                                   13,473,000,000 12,610,000,000 12,796,000,000 11,395,000,000 7,904,000,000
Cash Flow from Continuing Financing Activities                                                                                                                                                                                                        13,473,000,000 12,796,000,000 11,395,000,000 -7,904,000,000
Issuance of/Payments for Common Stock, Net
Payments for Common Stock                                                                                                                                                                                                                                115,000,000 42,000,000 1,042,000,000 37,000,000 57,000,000
Proceeds from Issuance of Common Stock                                                                                                                                                                                                                   115,000,000 6,350,000,000 1,042,000,000 -37,000,000 -57,000,000
Issuance of/Repayments for Debt, Net                                                                                                                                                                                                                   6,250,000,000 6,392,000,000 6,699,000,000 900,000,000 0
Issuance of/Repayments for Long Term Debt, Net                                                                                                                                                                                                         6,365,000,000 2,602,000,000 7,741,000,000 -937,000,000 -57,000,000
Proceeds from Issuance of Long Term Debt
Repayments for Long Term Debt                                                                                                                                                                                                                          2,923,000,000 2,453,000,000 2,184,000,000 -1,647,000,000
Proceeds from Issuance/Exercising of Stock Options/Warrants                                                                                                                                                                                              300,000,000 10,000,000 3.38E+11
Other Financing Cash Flow
Cash and Cash Equivalents, End of Period
+*include interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
and Class C capital stock (in dollars par share)
Diluted net income per share of Class A and Class B common
stock and Class C capital stock (in dollars par share)
nclude interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
and Class C capital stock (in dollars par share)
Diluted net income per share of Class A and Class B common
stock and Class C capital stock (in dollars par share)Fiscal year end September 28th., 2022. | USD
Image color is enhanced to show details 
Discontinued Operations
Form 1040 (2021) 76,033,000,000.00 20,642,000,000 18,936,000,000
Reported Normalized and Operating Income/Expense
Supplemental Section
Total Revenue as Reported, Supplemental 25763700000000 7532500000000 6511800000000 6188000000000 5531400000000 5689800000000 4617300000000 3829700000000 4115900000000 4607500000000 404990000000
Total Operating Profit/Loss as Reported, Supplemental 787140000000000 2188500000000 2103100000000 1936100000000 1643700000000 1565100000000 1121300000000 638300000000 797700000000 926600000000 91770000000
0eported Effective Tax Rate 0.162 0.179 0.157 0.158 0.158 0.159 0.119 0.181
eported Normalized Income
eported Normalized Operating Profit
ther Adjustments to Net Income Available to Common Stockholder
Discontinued Operations Basic EPS 312.65 $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 Basic EPS from Continuing Operations 113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operatio
Diluted EPS 112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12 312.65
Diluted EPS from Continuing Operations 112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operati
Basic Weighted Average Shares Outstanding 66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68646500000 68880400000 69274100000
Diluted Weighted Average Shares Outstanding 67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 69519300000 69819900000
Reported Normalized Diluted EPS $$2583.87
Basic EPS 11388 31150 28440 27690 26630 22540 16550 10210 00996 15490 10210
Diluted EPS 11220 30690 27990 27260 26290 22300 16400 10130 00987 15350 10190
Basic WASO 66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68465000000 688804000000 692741000000
Diluted WASO 67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 695193000000 6981990000000
Basic EPS 113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations 113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operation
Diluted EPS 112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.1
Diluted EPS from Continuing Operations 112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations
Basic Weighted Average Shares Outstanding 667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted Weighted Average Shares Outstanding 677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Reported Normalized Diluted EPS 31265
Basic EPS 113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2 1
Diluted EPS 112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Basic WASO 667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted WASO 677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Change in Cash 20,945,000,000 23,719,000,000 23,630,000,000 26,622,000,000 26,465,000,000
Effect of Exchange Rate Changes 25930000000 235000000000 3,175,000,000 300,000,000 6,126,000,000-
Cash and Cash Equivalents, Beginning of Period 181000000000 146000000000 183,000,000 -143,000,000 210,000,000Cash Flow Supplemental Section 23,719,000,000,000.00 $26,622,000,000,000.00 $26,465,000,000,000.00 $20,129,000,000,000.00
Change in Cash as Reported, Supplemental 2,774,000,000 89,000,000 -2,992,000,000 6,336,000,000
Income Tax Paid, Supplemental 13,412,000,000 157,000,000
Repayments for Long Term Debt 182527 161857
Costs and expenses:
Cost of revenues 84732 71896
Research and development 27573 26018
Sales and marketing 17946 18464
General and administrative 11052 9551
European Commission fines 0 1697
Total costs and expenses 141303 127626
Income from operations 41224 34231
Other income (expense), net 6858000000 5394
Income before income taxes 22,677,000,000 19,289,000,000
Provision for income taxes 22,677,000,000 19,289,000,000+Net income 22,677,000,000 19,289,000,000
18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00
18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00
18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00
18,936,000,000.00
3/6/2022 at 5:47 PM
Q4 2021 Q3 2021 Q2 2021 Q1 2021 Q4 2020
GOOGL_income-statement_Quarterly_As_Originally_Reported 24,934,000,000 25,539,000,000 37,497,000,000 31,211,000,000 30,818,000,000 24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
Cash Flow from Operating Activities, Indirect 24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
Net Cash Flow from Continuing Operating Activities, Indirect 20,642,000,000 18,936,000,000 18,525,000,000 17,930,000,000 15,227,000,000
Cash Generated from Operating Activities 6,517,000,000 3,797,000,000 4,236,000,000 2,592,000,000 5,748,000,000
Income/Loss before Non-Cash Adjustment 3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Total Adjustments for Non-Cash Items 3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Depreciation, Amortization and Depletion, Non-Cash Adjustment 3,215,000,000 3,085,000,000 2,730,000,000 2,525,000,000 3,539,000,000
Depreciation and Amortization, Non-Cash Adjustment 224,000,000 219,000,000 215,000,000 228,000,000 186,000,000
Depreciation, Non-Cash Adjustment 3,954,000,000 3,874,000,000 3,803,000,000 3,745,000,000 3,223,000,000
Amortization, Non-Cash Adjustment 1,616,000,000 1,287,000,000- 379,000,000 1,100,000,000 1,670,000,000
Stock-Based Compensation, Non-Cash Adjustment 2,478,000,000- 2,158,000,000- 2,883,000,000- 4,751,000,000- 3,262,000,000-
Taxes, Non-Cash Adjustment 2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000-
Investment Income/Loss, Non-Cash Adjustment 14,000,000 64,000,000- 8,000,000- 255,000,000 392,000,000-
Gain/Loss on Financial Instruments, Non-Cash Adjustment 2,225,000,000 2,806,000,000- 871,000,000- 1,233,000,000 1,702,000,000-
Other Non-Cash Items 5,819,000,000- 2,409,000,000- 3,661,000,000 2,794,000,000- 5,445,000,000-
Changes in Operating Capital 5,819,000,000- 2,409,000,000- 3,661,000,000 2,794,000,000- 5,445,000,000-
Change in Trade and Other Receivables 399,000,000- 1,255,000,000- 199,000,000 7,000,000- 738,000,000-
Change in Trade/Accounts Receivable 6,994,000,000 3,157,000,000 4,074,000,000- 4,956,000,000 6,938,000,000
Change in Other Current Assets 1,157,000,000 238,000,000- 130,000,000- 982,000,000 963,000,000
Change in Payables and Accrued Expenses 1,157,000,000 238,000,000- 130,000,000- 982,000,000 963,000,000
Change in Trade and Other Payables 5,837,000,000 2,919,000,000 4,204,000,000- 3,974,000,000 5,975,000,000
Change in Trade/Accounts Payable 368,000,000 272,000,000- 3,000,000 137,000,000 207,000,000
Change in Accrued Expenses 3,369,000,000 3,041,000,000- 1,082,000,000 785,000,000 740,000,000
Change in Deferred Assets/Liabilities
Change in Other Operating Capital 11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
Change in Prepayments and Deposits 11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
Cash Flow from Investing Activities
Cash Flow from Continuing Investing Activities 6,383,000,000- 6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000 -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000
Purchase/Sale and Disposal of Property, Plant and Equipment,Net
Purchase of Property, Plant and Equipment 385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
Sale and Disposal of Property, Plant and Equipment 385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
Purchase/Sale of Business, Net 4,348,000,000 3,360,000,000 3,293,000,000 2,195,000,000 -1,375,000,000
Purchase/Acquisition of Business 40,860,000,000 35,153,000,000 24,949,000,000 -37,072,000,000 -36,955,000,000
Purchase/Sale of Investments, NetPurchase of Investments 36,512,000,000 31,793,000,000 21,656,000,000 39,267,000,000 35,580,000,000 100,000,000 388,000,000 23,000,000 30,000,000 -57,000,000Sale of Investments
Other Investing Cash Flow 15,254,000,000
Purchase/Sale of Other Non-Current Assets, Net 16,511,000,000 15,254,000,000 15,991,000,000 13,606,000,000 9,270,000,000
Sales of Other Non-Current Assets 16,511,000,000 12,610,000,000 15,991,000,000 13,606,000,000 9,270,000,000
Cash Flow from Financing Activities 13,473,000,000 12,610,000,000 12,796,000,000 11,395,000,000 7,904,000,000
Cash Flow from Continuing Financing Activities 13,473,000,000 12,796,000,000 11,395,000,000 -7,904,000,000
Issuance of/Payments for Common Stock, Net
Payments for Common Stock 115,000,000 42,000,000 1,042,000,000 37,000,000 57,000,000
Proceeds from Issuance of Common Stock 115,000,000 6,350,000,000 1,042,000,000 -37,000,000 -57,000,000
Issuance of/Repayments for Debt, Net 6,250,000,000 6,392,000,000 6,699,000,000 900,000,000 0
Issuance of/Repayments for Long Term Debt, Net 6,365,000,000 2,602,000,000 7,741,000,000 -937,000,000 -57,000,000
Proceeds from Issuance of Long Term Debt
Repayments for Long Term Debt 2,923,000,000 2,453,000,000 2,184,000,000 -1,647,000,000
Proceeds from Issuance/Exercising of Stock Options/Warrants 300,000,000 10,000,000 3.38E+11
Other Financing Cash Flow
Cash and Cash Equivalents, End of Period *include interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
and Class C capital stock (in dollars par share)
Diluted net income per share of Class A and Class B common
stock and Class C capital stock (in dollars par share)
nclude interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
and Class C capital stock (in dollars par share)
Diluted net income per share of Class A and Class B common
stock and Class C capital stock (in dollars par share)Fiscal year end September 28th., 2022. | USD
Image color is enhanced to show detailsCONSOLIDATED BALANCE SHEETS (Parenthetical) - $ / shares Dec. 31, 2020 Dec. 31, 2019
Stockholders’ equity: infiniti'
Convertible preferred stock, par value per (1)share $ 10000.00 $ 10000.00
Convertible preferred stock, shares authorized (in shares) 100,000,000 100,000,000
Convertible preferred stock, shares issued (in shares) 0 0
Convertible preferred stock, shares outstanding (in shares) 0 0
Common stock and capital stock, par value (in dollars per share) $ 2587.27 $ 2587.27
Common stock and capital stock, shares authorized (in shares) 15,000,000,000 15,000,000,000
Common stock and capital stock, shares issued (in shares) 675,222,000 688,335,000
Common stock and capital stock, shares outstanding (in shares) 675,222,000 688,335,000
Class A Common Stock
Stockholders’ equity:
Common stock and capital stock, shares authorized (in shares) 9,000,000,000 9,000,000,000
Common stock and capital stock, shares issued (in shares) 300,730,000 299,828,000
Common stock and capital stock, shares outstanding (in shares) 300,730,000 299,828,000
Class B Common Stock
Stockholders’ equity:
Common stock and capital stock, shares authorized (in shares) 3,000,000,000 3,000,000,000
Common stock and capital stock, shares issued (in shares) 45,843,000 46,441,000
Common stock and capital stock, shares outstanding (in shares) 45,843,000 46,441,000
Class C Capital Stock
Stockholders’ equity:
Common stock and capital stock, shares authorized (in shares) 3,000,000,000 3,000,000,000
Common stock and capital stock, shares issued (in shares) 328,649,000 342,066,000
Common stock and capital stock, shares outstanding (in shares) 328,649,000 342,066,000 
This Product Contains Sensitive Taxpayer Data  
 Request Date: 08-02-2022  Response Date: 08-02-2022  Tracking Number: 102398244811 
 Account Transcript 
 FORM NUMBER: 1040 TAX PERIOD: Dec. 31, 2020 
 TAXPAYER IDENTIFICATION NUMBER: XXX-XX-1725 
 ZACH T WOO
 3050 R 
 --- ANY MINUS SIGN SHOWN BELOW SIGNIFIES A CREDIT AMOUNT ---  

United States Department of the Treasury
________________________________________
Revenue 
Service 
%ZACHRY T WOODMBR
NASDAQGOOG
5323 BRADFORD DR
DALLAS, TX 75235
Q4 2020 Q4 2019
+Calendar Year
+Due: 04/18/2022
+Dec. 31, 2020 Dec. 31, 2019 +USD in "000'"s
Other Adjustments to Net Income Available to Common Stockholders
Discontinued Operations
Form 1040 (2021) 76,033,000,000.00 20,642,000,000 18,936,000,000
Reported Normalized and Operating Income/Expense
Supplemental Section
Total Revenue as Reported, Supplemental
25763700000000 7532500000000 6511800000000 6188000000000 5531400000000 5689800000000 4617300000000 3829700000000 4115900000000 4607500000000 404990000000
Total Operating Profit/Loss as Reported, Supplemental 787140000000000 2188500000000 2103100000000 1936100000000 1643700000000 1565100000000 1121300000000 638300000000 797700000000 926600000000 91770000000
0eported Effective Tax Rate 0.162 0.179 0.157 0.158 0.158 0.159 0.119 0.181
eported Normalized Income
eported Normalized Operating Profit
ther Adjustments to Net Income Available to Common Stockholder
Discontinued Operations Basic EPS 312.65 $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 Basic EPS from Continuing Operations 113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operatio
Diluted EPS 112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12 312.65
Diluted EPS from Continuing Operations 112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operati
Basic Weighted Average Shares Outstanding 66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68646500000 68880400000 69274100000
Diluted Weighted Average Shares Outstanding 67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 69519300000 69819900000
Reported Normalized Diluted EPS $$2583.87
Basic EPS 11388 31150 28440 27690 26630 22540 16550 10210 00996 15490 10210
Diluted EPS 11220 30690 27990 27260 26290 22300 16400 10130 00987 15350 10190
Basic WASO 66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68465000000 688804000000 692741000000
Diluted WASO 67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 695193000000 6981990000000
Basic EPS 113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations 113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operation
Diluted EPS 112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.1
Diluted EPS from Continuing Operations 112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations
Basic Weighted Average Shares Outstanding 667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted Weighted Average Shares Outstanding 677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Reported Normalized Diluted EPS 312.65
Basic EPS 113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2 1
Diluted EPS 112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Basic WASO 667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted WASO 677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Change in Cash 20,945,000,000 23,719,000,000 23,630,000,000 26,622,000,000 26,465,000,000
Effect of Exchange Rate Changes 25930000000 235000000000 3,175,000,000 300,000,000 6,126,000,000-
Cash and Cash Equivalents, Beginning of Period 181000000000 146000000000 183,000,000 -143,000,000 210,000,000Cash Flow Supplemental Section 23,719,000,000,000.00 $26,622,000,000,000.00 $26,465,000,000,000.00 $20,129,000,000,000.00
Change in Cash as Reported, Supplemental 2,774,000,000 89,000,000 -2,992,000,000 6,336,000,000
Income Tax Paid, Supplemental 13,412,000,000 157,000,000
Repayments for Long Term Debt 182527 161857
Costs and expenses:
Cost of revenues 84732 71896
Research and development 27573 26018
Sales and marketing 17946 18464
General and administrative 11052 9551
European Commission fines 0 1697
Total costs and expenses 141303 127626
Income from operations 41224 34231
Other income (expense), net 6858000000 5394
Income before income taxes 22,677,000,000 19,289,000,000
Provision for income taxes 22,677,000,000 19,289,000,000+Net income 22,677,000,000 19,289,000,000
18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00
18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00
18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00
18,936,000,000.00
3/6/2022 at 5:47 PM
Q4 2021 Q3 2021 Q2 2021 Q1 2021 Q4 2020
GOOGL_income-statement_Quarterly_As_Originally_Reported 24,934,000,000 25,539,000,000 37,497,000,000 31,211,000,000 30,818,000,000 24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
Cash Flow from Operating Activities, Indirect 24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
Net Cash Flow from Continuing Operating Activities, Indirect 20,642,000,000 18,936,000,000 18,525,000,000 17,930,000,000 15,227,000,000
Cash Generated from Operating Activities 6,517,000,000 3,797,000,000 4,236,000,000 2,592,000,000 5,748,000,000
Income/Loss before Non-Cash Adjustment 3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Total Adjustments for Non-Cash Items 3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Depreciation, Amortization and Depletion, Non-Cash Adjustment 3,215,000,000 3,085,000,000 2,730,000,000 2,525,000,000 3,539,000,000
Depreciation and Amortization, Non-Cash Adjustment 224,000,000 219,000,000 215,000,000 228,000,000 186,000,000
Depreciation, Non-Cash Adjustment 3,954,000,000 3,874,000,000 3,803,000,000 3,745,000,000 3,223,000,000
Amortization, Non-Cash Adjustment 1,616,000,000 1,287,000,000- 379,000,000 1,100,000,000 1,670,000,000
Stock-Based Compensation, Non-Cash Adjustment 2,478,000,000- 2,158,000,000- 2,883,000,000- 4,751,000,000- 3,262,000,000-
Taxes, Non-Cash Adjustment 2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000-
Investment Income/Loss, Non-Cash Adjustment 14,000,000 64,000,000- 8,000,000- 255,000,000 392,000,000-
Gain/Loss on Financial Instruments, Non-Cash Adjustment 2,225,000,000 2,806,000,000- 871,000,000- 1,233,000,000 1,702,000,000-
Other Non-Cash Items 5,819,000,000- 2,409,000,000- 3,661,000,000 2,794,000,000- 5,445,000,000-
Changes in Operating Capital 5,819,000,000- 2,409,000,000- 3,661,000,000 2,794,000,000- 5,445,000,000-
Change in Trade and Other Receivables 399,000,000- 1,255,000,000- 199,000,000 7,000,000- 738,000,000-
Change in Trade/Accounts Receivable 6,994,000,000 3,157,000,000 4,074,000,000- 4,956,000,000 6,938,000,000
Change in Other Current Assets 1,157,000,000 238,000,000- 130,000,000- 982,000,000 963,000,000
Change in Payables and Accrued Expenses 1,157,000,000 238,000,000- 130,000,000- 982,000,000 963,000,000
Change in Trade and Other Payables 5,837,000,000 2,919,000,000 4,204,000,000- 3,974,000,000 5,975,000,000
Change in Trade/Accounts Payable 368,000,000 272,000,000- 3,000,000 137,000,000 207,000,000
Change in Accrued Expenses 3,369,000,000 3,041,000,000- 1,082,000,000 785,000,000 740,000,000
Change in Deferred Assets/Liabilities
Change in Other Operating Capital 11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
Change in Prepayments and Deposits 11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
Cash Flow from Investing Activities
Cash Flow from Continuing Investing Activities 6,383,000,000- 6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000 -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000
Purchase/Sale and Disposal of Property, Plant and Equipment,Net
Purchase of Property, Plant and Equipment 385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
Sale and Disposal of Property, Plant and Equipment 385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
Purchase/Sale of Business, Net 4,348,000,000 3,360,000,000 3,293,000,000 2,195,000,000 -1,375,000,000
Purchase/Acquisition of Business 40,860,000,000 35,153,000,000 24,949,000,000 -37,072,000,000 -36,955,000,000
Purchase/Sale of Investments, NetPurchase of Investments 36,512,000,000 31,793,000,000 21,656,000,000 39,267,000,000 35,580,000,000 100,000,000 388,000,000 23,000,000 30,000,000 -57,000,000Sale of Investments
Other Investing Cash Flow 15,254,000,000
Purchase/Sale of Other Non-Current Assets, Net 16,511,000,000 15,254,000,000 15,991,000,000 13,606,000,000 9,270,000,000
Sales of Other Non-Current Assets 16,511,000,000 12,610,000,000 15,991,000,000 13,606,000,000 9,270,000,000
Cash Flow from Financing Activities 13,473,000,000 12,610,000,000 12,796,000,000 11,395,000,000 7,904,000,000
Cash Flow from Continuing Financing Activities 13,473,000,000 12,796,000,000 11,395,000,000 -7,904,000,000
Issuance of/Payments for Common Stock, Net
Payments for Common Stock 115,000,000 42,000,000 1,042,000,000 37,000,000 57,000,000
Proceeds from Issuance of Common Stock 115,000,000 6,350,000,000 1,042,000,000 -37,000,000 -57,000,000
Issuance of/Repayments for Debt, Net 6,250,000,000 6,392,000,000 6,699,000,000 900,000,000 0
Issuance of/Repayments for Long Term Debt, Net 6,365,000,000 2,602,000,000 7,741,000,000 -937,000,000 -57,000,000
Proceeds from Issuance of Long Term Debt
Repayments for Long Term Debt 2,923,000,000 2,453,000,000 2,184,000,000 -1,647,000,000
Proceeds from Issuance/Exercising of Stock Options/Warrants 300,000,000 10,000,000 3.38E+11
Other Financing Cash Flow
Cash and Cash Equivalents, End of Period
+*include interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
and Class C capital stock (in dollars par share)
Diluted net income per share of Class A and Class B common
stock and Class C capital stock (in dollars par share)
nclude interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
and Class C capital stock (in dollars par share)
Diluted net income per share of Class A and Class B common
stock and Class C capital stock (in dollars par share)Fiscal year end September 28th., 2022. | USD
Image color is enhanced to show detailsCONSOLIDATED BALANCE SHEETS (Parenthetical) - $ / shares Dec. 31, 2020 Dec. 31, 2019
Stockholders’ equity: infiniti'
Convertible preferred stock, par value per (1)share $ 10000.00 $ 10000.00
Convertible preferred stock, shares authorized (in shares) 100,000,000 100,000,000
Convertible preferred stock, shares issued (in shares) 0 0
Convertible preferred stock, shares outstanding (in shares) 0 0
Common stock and capital stock, par value (in dollars per share) $ 2587.27 $ 2587.27
Common stock and capital stock, shares authorized (in shares) 15,000,000,000 15,000,000,000
Common stock and capital stock, shares issued (in shares) 675,222,000 688,335,000
Common stock and capital stock, shares outstanding (in shares) 675,222,000 688,335,000
Class A Common Stock
Stockholders’ equity:
Common stock and capital stock, shares authorized (in shares) 9,000,000,000 9,000,000,000
Common stock and capital stock, shares issued (in shares) 300,730,000 299,828,000
Common stock and capital stock, shares outstanding (in shares) 300,730,000 299,828,000
Class B Common Stock
Stockholders’ equity:
Common stock and capital stock, shares authorized (in shares) 3,000,000,000 3,000,000,000
Common stock and capital stock, shares issued (in shares) 45,843,000 46,441,000
Common stock and capital stock, shares outstanding (in shares) 45,843,000 46,441,000
Class C Capital Stock
Stockholders’ equity:
Common stock and capital stock, shares authorized (in shares) 3,000,000,000 3,000,000,000
Common stock and capital stock, shares issued (in shares) 328,649,000 342,066,000
Common stock and capital stock, shares outstanding (in shares) 328,649,000 342,066,000

 ACCOUNT BALANCE :0.00 :
 ACCRUED INTEREST :0.00 :AS OF: Mar. 28, 2022  ACCRUED PENALTY: 0.00 AS OF: Mar. 28, 2022 
 ACCOUNT BALANCE 
 PLUS ACCRUALS 
 (this is not a 
 payoff amount) :0.00 :
 ** INFORMATION FROM THE RETURN OR AS ADJUSTED **  
 EXEMPTIONS :0.00 :
 FILING STATUS: Single 
 ADJUSTED GROSS 
 INCOME:  
 TAXABLE INCOME:  
 TAX PER RETURN:  
 SE TAXABLE INCOME 
 TAXPAYER:  
 SE TAXABLE INCOME 
 SPOUSE:  
 TOTAL SELF 
 EMPLOYMENT TAX:  
 RETURN NOT PRESENT FOR THIS ACCOUNT 
 TRANSACTIONS  
 CODE EXPLANATION OF TRANSACTION CYCLE DATE AMOUNT  No tax return filed  
 766 Tax relief credit 06-15-2020 -$1,200.00  846 Refund issued 06-05-2020 $1,200.00 
 290 Additional tax assessed 20202205 06-15-2020 $0.00  76254-999-05099-0 
 971 Notice issued 06-15-2020 $0.00  766 Tax relief credit 01-18-2021 -$600.00  846 Refund issued 01-06-2021 $600.00 
 290 Additional tax assessed 20205302 01-18-2021 $0.00  76254-999-05055-0 
 663 Estimated tax payment 01-05-2021 -$9,000,000.00  662 Removed estimated tax payment 01-05-2021 $9,000,000.00  740 Undelivered refund returned to IRS 01-18-2021 -$600.00 
 767 Reduced or removed tax relief 01-18-2021 $600.00  credit 
 971 Notice issued 03-28-2022 $0.00
 This Product Contains Sensitive Taxpayer Data 
For the period 04/13/2022 to 04/29/2022
Business Checking Summary
Account number :47-2041-6547 :
Overdraft Protection has not beeen established for this account. :
Please contact us if you would like to set up this service. :
Valance Summary :
Begininning Balance :107.80 :
Deposits and other deductions :2,270,001.91 :
Ending balance :0.00 :
Average Ledger balance :29.27 :
Average collected balance :29.27 :
Overdraft and Returned Item Fee Summary :
Total Overdraft Fees :.00 :
Total for this Period :.00 :
Total Year to Date :252.00 :
Total Returned Item Fees (NSF) :
Toatal this Period :72.00 :
Total Year to Date :324.00 :
Total NSF/ Overdraft Fee :
Total for this Period :.00 :
Total Year to Date :432.00 :
This Product Contains Sensitive Taxpayer Data :
Request Date :07-29-2022 :
Response Date :07-29-2022 :
Tracking Number :102393399156 :
Customer File Number :132624428 :
Wage Income Transcript :
SSN Provided :XXX-XX-1725 :
Tax Period Requested :December, 2020 :
Form W-2 Wage and TAx Statement :
Employer :
Employer's Identitfication Number (EIN) :XXXXX4661 :
Employee :
Employee's Social Security Number :XXX-XX-1725 :
Submission Type: Original document
Wages, Tips and Other Compensdation :2267700000000000
Federal Income Tax Withheld :
Social Security Wages :2267700000000000
Social Security Taax Withheld :
Medicare Wages and Tips :2267700000000000
Medicare Tax Withheld :
Allocated Tips :
Dependant Care Benefits :
Deffered Compensation :
Code "Q" Nontaxable Combat Pay :
Code "V" Employer Contributions to a Health Savings Account :
Code "f" Deferrals under a Section 409A nonqualified Deferred Compensation plan :
Code "Z" Income under a Section 409A On a nonqualified Defered Compensation plan :
Code "R" Employer's Contribution to MSA :
Code "S" Employer's Contribution to Simple Account :
Code "T" Income From exercise of non-statutory stock options :
Code "AA" Designated Roth Contributions under a Section 401(k) Pan :
Code "BB" Designated Roth Contributions under a Section 403(b) Pan :
Code "DD" Cost of Employer-Sponsored Health Coverage :
Code "EE" Designatied ROTH Contributions Under a Governmental Section 457(b) reimbursement arrangement :
Code "FF" Permitted benefits under a qualified small employer health reimbursement arrangement :
Code "GG" INcome from Qualified Equity Grants Under Section 83(i) :
Code "HH" Aggregate Defferals Under Section 83(i) Electionsas of the Cloase of the Calendar Year :
Third Party Sick Pay Indicator :
Statutory Employee :
Retirement Pan Indicator :
Statutory Employee :
W2 Submission Type :
W2 WHC SSN Validation Code :
FORM 1099-G
Payer :
Payer's Federal Identification Number (FIN) :XXXXX4775
THE
101 EA

Reciepient :
Reciepient's Social Security Number :XXX-XX-1725 :
WOO ZACH T :
5222 B :

Submission Type :
Account Number (Optional) :
ATAA Payments :
Tax Withheld :
Taxable Grants :
Unemployement Compensation :
Agricultural Subsidiaries :
Prior Year Refund :
Markey gain on Commodity Credit Corporation loans repaid :
Year of Refund :
1099G Offset :
Second TIN Notice :
This Product Contains Sensitive Taxpart Date :
  Department of the Treasury
>   +Internal Revenue Service +Q4 2020 Q4 2019
>   +Calendar Year
>   +Due: 04/18/2022
>   +Dec. 31, 2020 Dec. 31, 2019 +USD in "000'"s
>   Other Adjustments to Net Income Available to Common Stockholders
>   Discontinued Operations
>   Form 1040 (2021)                                                                                                                                                                                                                                          76,033,000,000.00 20,642,000,000 18,936,000,000
>   Reported Normalized and Operating Income/Expense
>   Supplemental Section
>   Total Revenue as Reported, Supplemental                      
                                                                                                                                                                                                                                                             5763700000000  7532500000000 6511800000000 6188000000000 5531400000000 5689800000000 4617300000000 3829700000000 4115900000000 4607500000000 404990000000
>   Total Operating Profit/Loss as Reported, Supplemental
                                                                                                                                                                                                                                                             787140000000000 2188500000000 2103100000000 1936100000000 1643700000000 1565100000000 1121300000000 638300000000 797700000000 926600000000 91770000000
>   0eported Effective Tax Rate                                                                                                                                                                                                                                         0.162         0.179         0.157         0.158         0.158         0.159         0.119          0.181
>   eported Normalized Income
>   eported Normalized Operating Profit
>   ther Adjustments to Net Income Available to Common Stockholder
>   Discontinued Operations Basic EPS                                                                                                                                                                                                                         312.65 $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 
    Basic EPS from Continuing Operations                                                                                                                                                                                                                      113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
>   Basic EPS from Discontinued Operatio
>   Diluted EPS                                                                                                                                                                                                                                               112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35  10.12 312.65
>   Diluted EPS from Continuing Operations                                                                                                                                                                                                                    112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
>   Diluted EPS from Discontinued Operati
>   Basic Weighted Average Shares Outstanding                                                                                                                                                                                                                 66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68646500000 68880400000 69274100000
>   Diluted Weighted Average Shares Outstanding                                                                                                                                                                                                               67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 69519300000 69819900000
>   Reported Normalized Diluted EPS $$2583.87
>   Basic EPS                                                                                                                                                                                                                                                 11388 31150 28440 27690 26630 22540 16550 10210 00996 15490 10210
>   Diluted EPS                                                                                                                                                                                                                                               11220 30690 27990 27260 26290 22300 16400 10130 00987 15350 10190
>   Basic WASO                                                                                                                                                                                                                                                66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68465000000 688804000000 692741000000
>   Diluted WASO                                                                                                                                                                                                                                              67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 695193000000 6981990000000
>   Basic EPS                                                                                                                                                                                                                                                 113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
>   Basic EPS from Continuing Operations                                                                                                                                                                                                                      113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
>   Basic EPS from Discontinued Operation
>   Diluted EPS                                                                                                                                                                                                                                               112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.1
>   Diluted EPS from Continuing Operations                                                                                                                                                                                                                    112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
>   Diluted EPS from Discontinued Operations
>   Basic Weighted Average Shares Outstanding                                                                                                                                                                                                                 667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
>   Diluted Weighted Average Shares Outstanding                                                                                                                                                                                                               677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
>   Reported Normalized Diluted EPS                                                                                                                                                                                                                           312.65
>   Basic EPS                                                                                                                                                                                                                                                 113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2 1
>   Diluted EPS                                                                                                                                                                                                                                               112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
>   Basic WASO                                                                                                                                                                                                                                                667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
>   Diluted WASO                                                                                                                                                                                                                                              677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
>   Change in Cash                                                                                                                                                                                                                                            20,945,000,000 23,719,000,000 23,630,000,000 26,622,000,000 26,465,000,000
>   Effect of Exchange Rate Changes                                                                                                                                                                                                                           25930000000 235000000000 3,175,000,000 300,000,000 6,126,000,000-
>   Cash and Cash Equivalents, Beginning of Period                                                                                                                                                                                                            181000000000 146000000000 183,000,000 -143,000,000 210,000,000
    Cash Flow Supplemental Section 
                                                                                                                                                                                                                                                             23,719,000,000,000.00 $26,622,000,000,000.00 $26,465,000,000,000.00 $20,129,000,000,000.00
>   Change in Cash as Reported, Supplemental                                                                                                                                                                                                                  2,774,000,000 89,000,000 -2,992,000,000 6,336,000,000
>   Income Tax Paid, Supplemental                                                                                                                                                                                                                             13,412,000,000 157,000,000
>   Repayments for Long Term Debt                                                                                                                                                                                                                             182527 161857
>   Costs and expenses:
>   Cost of revenues                                                                                                                                                                                                                                                 84732 71896
>   Research and development                                                                                                                                                                                                                                         27573 26018
>   Sales and marketing                                                                                                                                                                                                                                              17946 18464
>   General and administrative                                                                                                                                                                                                                                       11052 9551
>   European Commission fines                                                                                                                                                                                                                                            0 1697
>   Total costs and expenses                                                                                                                                                                                                                                        141303 127626
>   Income from operations                                                                                                                                                                                                                                           41224 34231
>   Other income (expense), net                                                                                                                                                                                                                                  6858000000 5394
>   Income before income taxes                                                                                                                                                                                                                               22,677,000,000 19,289,000,000
>   Provision for income taxes                                                                                                                                                                                                                               22,677,000,000 19,289,000,000
    Net income                                                                                                                                                                                                                                               22,677,000,000 19,289,000,000 18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 
>   3/6/2022 at 5:47 PM
>   Q4 2021 Q3 2021 Q2 2021 Q1 2021 Q4 2020
>   GOOGL_income-statement_Quarterly_As_Originally_Reported                                                                                                                                                                                                  24,934,000,000 25,539,000,000 37,497,000,000  31,211,000,000 30,818,000,000 24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
>   Cash Flow from Operating Activities, Indirect                                                                                                                                                                                                            24,934,000,000 25,539,000,000 21,890,000,000  19,289,000,000  22,677,000,000
>   Net Cash Flow from Continuing Operating Activities, Indirect                                                                                                                                                                                             20,642,000,000 18,936,000,000 18,525,000,000  17,930,000,000  15,227,000,000
>   Cash Generated from Operating Activities                                                                                                                                                                                                                  6,517,000,000  3,797,000,000  4,236,000,000   2,592,000,000   5,748,000,000
>   Income/Loss before Non-Cash Adjustment                                                                                                                                                                                                                    3,439,000,000  3,304,000,000  2,945,000,000   2,753,000,000  3,725,000,000
>   Total Adjustments for Non-Cash Items                                                                                                                                                                                                                      3,439,000,000  3,304,000,000  2,945,000,000   2,753,000,000  3,725,000,000
>   Depreciation, Amortization and Depletion, Non-Cash Adjustment                                                                                                                                                                                             3,215,000,000  3,085,000,000  2,730,000,000   2,525,000,000  3,539,000,000
>   Depreciation and Amortization, Non-Cash Adjustment                                                                                                                                                                                                          224,000,000    219,000,000    215,000,000     228,000,000    186,000,000
>   Depreciation, Non-Cash Adjustment                                                                                                                                                                                                                         3,954,000,000  3,874,000,000  3,803,000,000   3,745,000,000  3,223,000,000
>   Amortization, Non-Cash Adjustment                                                                                                                                                                                                                         1,616,000,000  1,287,000,000-   379,000,000   1,100,000,000  1,670,000,000
>   Stock-Based Compensation, Non-Cash Adjustment                                                                                                                                                                                                             2,478,000,000- 2,158,000,000- 2,883,000,000-  4,751,000,000- 3,262,000,000-
>   Taxes, Non-Cash Adjustment                                                                                                                                                                                                                                2,478,000,000  2,158,000,000- 2,883,000,000   4,751,000,000- 3,262,000,000-
>   Investment Income/Loss, Non-Cash Adjustment                                                                                                                                                                                                                  14,000,000    64,000,000-      8,000,000-    255,000,000    392,000,000-
>   Gain/Loss on Financial Instruments, Non-Cash Adjustment                                                                                                                                                                                                   2,225,000,000  2,806,000,000-   871,000,000-  1,233,000,000  1,702,000,000-
>   Other Non-Cash Items                                                                                                                                                                                                                                      5,819,000,000- 2,409,000,000- 3,661,000,000   2,794,000,000- 5,445,000,000-
>   Changes in Operating Capital                                                                                                                                                                                                                              5,819,000,000- 2,409,000,000- 3,661,000,000   2,794,000,000- 5,445,000,000-
>   Change in Trade and Other Receivables                                                                                                                                                                                                                       399,000,000- 1,255,000,000-   199,000,000   7,000,000-       738,000,000-
>   Change in Trade/Accounts Receivable                                                                                                                                                                                                                       6,994,000,000  3,157,000,000  4,074,000,000-  4,956,000,000  6,938,000,000
>   Change in Other Current Assets                                                                                                                                                                                                                            1,157,000,000    238,000,000-   130,000,000-    982,000,000   963,000,000
>   Change in Payables and Accrued Expenses                                                                                                                                                                                                                   1,157,000,000    238,000,000-   130,000,000-    982,000,000   963,000,000
>   Change in Trade and Other Payables                                                                                                                                                                                                                        5,837,000,000  2,919,000,000  4,204,000,000-  3,974,000,000  5,975,000,000
>   Change in Trade/Accounts Payable                                                                                                                                                                                                                            368,000,000    272,000,000-     3,000,000     137,000,000    207,000,000
>   Change in Accrued Expenses                                                                                                                                                                                                                                3,369,000,000  3,041,000,000- 1,082,000,000     785,000,000    740,000,000
>   Change in Deferred Assets/Liabilities
>   Change in Other Operating Capital                                                                                                                                                                                                                        11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
>   Change in Prepayments and Deposits                                                                                                                                                                                                                       11,016,000,000- 10,050,000,000- 9,074,000,000- 5,383,000,000- 7,281,000,000
>   Cash Flow from Investing Activities
>   Cash Flow from Continuing Investing Activities                                                                                                                                                                                                           6,383,000,000- 6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000 -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000
>   Purchase/Sale and Disposal of Property, Plant and Equipment,Net
>   Purchase of Property, Plant and Equipment                                                                                                                                                                                                                  385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
>   Sale and Disposal of Property, Plant and Equipment                                                                                                                                                                                                         385,000,000 259,000,000 308,000,000 1,666,000,000- 370,000,000-
>   Purchase/Sale of Business, Net                                                                                                                                                                                                                           4,348,000,000 3,360,000,000 3,293,000,000 2,195,000,000 -1,375,000,000
>   Purchase/Acquisition of Business                                                                                                                                                                                                                        40,860,000,000 35,153,000,000 24,949,000,000 -37,072,000,000 -36,955,000,000
>   Purchase/Sale of Investments, NetPurchase of Investments                                                                                                                                                                                                36,512,000,000 31,793,000,000 21,656,000,000 39,267,000,000 35,580,000,000 100,000,000 388,000,000 23,000,000 30,000,000 -57,000,000Sale of Investments
>   Other Investing Cash Flow                                                                                                                                                                                                                               15,254,000,000
>   Purchase/Sale of Other Non-Current Assets, Net                                                                                                                                                                                                          16,511,000,000 15,254,000,000 15,991,000,000 13,606,000,000 9,270,000,000
>   Sales of Other Non-Current Assets                                                                                                                                                                                                                       16,511,000,000 12,610,000,000 15,991,000,000 13,606,000,000 9,270,000,000
>   Cash Flow from Financing Activities                                                                                                                                                                                                                     13,473,000,000 12,610,000,000 12,796,000,000 11,395,000,000 7,904,000,000
>   Cash Flow from Continuing Financing Activities                                                                                                                                                                                                          13,473,000,000 12,796,000,000 11,395,000,000 -7,904,000,000
>   Issuance of/Payments for Common Stock, Net
>   Payments for Common Stock                                                                                                                                                                                                                                  115,000,000 42,000,000 1,042,000,000 37,000,000 57,000,000
>   Proceeds from Issuance of Common Stock                                                                                                                                                                                                                     115,000,000 6,350,000,000 1,042,000,000 -37,000,000 -57,000,000
>   Issuance of/Repayments for Debt, Net                                                                                                                                                                                                                     6,250,000,000 6,392,000,000 6,699,000,000 900,000,000 0
>   Issuance of/Repayments for Long Term Debt, Net                                                                                                                                                                                                           6,365,000,000 2,602,000,000 7,741,000,000 -937,000,000 -57,000,000
>   Proceeds from Issuance of Long Term Debt
>   Repayments for Long Term Debt                                                                                                                                                                                                                            2,923,000,000 2,453,000,000 2,184,000,000 -1,647,000,000
>   Proceeds from Issuance/Exercising of Stock Options/Warrants                                                                                                                                                                                                300,000,000 10,000,000 3.38E+11
>   Other Financing Cash Flow
>   Cash and Cash Equivalents, End of Period
>   +*include interest paid, capital obligation, and underweighting
>   Basic net income per share of Class A and B common stock
>   and Class C capital stock (in dollars par share)
>   Diluted net income per share of Class A and Class B common
>   stock and Class C capital stock (in dollars par share)
>   nclude interest paid, capital obligation, and underweighting
>   Basic net income per share of Class A and B common stock
>   and Class C capital stock (in dollars par share)
>   Diluted net income per share of Class A and Class B common
>   stock and Class C capital stock (in dollars par share)Fiscal year end September 28th., 2022. | USD
>   Image color is enhanced to show detailsCONSOLIDATED BALANCE SHEETS (Parenthetical) - $ / shares        Dec. 31, 2020        Dec. 31, 2019
>   Stockholders’ equity:                        infiniti'
>   Convertible preferred stock, par value per (1)share                       $ 10000.00           $ 10000.00
>   Convertible preferred stock, shares authorized (in shares)         100,000,000          100,000,000
>   Convertible preferred stock, shares issued (in shares)         0          0
>   Convertible preferred stock, shares outstanding (in shares)         0          0
>   Common stock and capital stock, par value (in dollars per share)           $ 2587.27               $ 2587.27
>   Common stock and capital stock, shares authorized (in shares)         15,000,000,000          15,000,000,000
>   Common stock and capital stock, shares issued (in shares)                675,222,000          688,335,000
>   Common stock and capital stock, shares outstanding (in shares)           675,222,000          688,335,000
>   Class A Common Stock
>   Stockholders’ equity:
>   Common stock and capital stock, shares authorized (in shares)         9,000,000,000          9,000,000,000
>   Common stock and capital stock, shares issued (in shares)               300,730,000          299,828,000
>   Common stock and capital stock, shares outstanding (in shares)          300,730,000          299,828,000
>   Class B Common Stock
>   Stockholders’ equity:
>   Common stock and capital stock, shares authorized (in shares)         3,000,000,000          3,000,000,000
>   Common stock and capital stock, shares issued (in shares)                45,843,000             46,441,000
>   Common stock and capital stock, shares outstanding (in shares)           45,843,000             46,441,000
>   Class C Capital Stock
>   Stockholders’ equity:
>   Common stock and capital stock, shares authorized (in shares)         3,000,000,000          3,000,000,000
>   Common stock and capital stock, shares issued (in shares)               328,649,000            342,066,000
>   Common stock and capital stock, shares outstanding (in shares)          328,649,000            342,066,000

''-'-''Presses '"'<'#'!'"'Start'_menu'for 'ant/mana'.yml'@bParadice'/'Star'Gazer's''
'e'*'check**\*exec '"'$ 'make:'$'MAKEFILE/rakefile.GEMS/.spec ':''
'Run::/Scripts::/:Build::"build'_script":,''
'//NPORT-filer-information'@https://sec.gov :
notifications :''
'document :''
'e-mail :pncalerts'@pnc.com'"''
''-then-sends-remittnance_advice-via'-e'-mail-To :e-mail :pncalerts'@https://pncalerts.com ':' ' 'bitore.sig/BITCORE'@atom/electron'@kite.u'''stablizing..., installations :; :prerequisite-installation_build_script's:installation/hardward_dependeabilities :#For :frost'$'@'V8/nazt ::installation :requirements :install :Automatically :-with :fwireless_wifi'@ghttps::/google.fi//posted/POST\NPORT-Filer-Information'@sec.gov :                                                                     Cash and Cash Equivalents, Beginning of Period                                                                                                                                                                                                                                                                                                                                                                                                                                   
Department of the Treasury
Internal Revenue Service
Q4 2020 Q4 2019
Calendar Year                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
Due: 04/18/2022                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ec. 31, 2020 Dec. 31, 2019  
 +USD in "000'"s 
Other Adjustments to Net Income Available to Common Stockholders                                                                                                                                                                                                                                                                                                                                                                                                    
Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
Form 1040 (2021)                                                                                                                                                                                                                                                                                                                                                                                                    $76,033,000,000.00 20,642,000,000 18,936,000,000
Reported Normalized and Operating Income/Expense                                                                                                                                                                                                                                                                                                                                                                                                                           
Supplemental Section                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
Total Revenue as Reported, Supplemental                                                                                                                                                                              $257,637,000,000.00 75,325,000,000 65,118,000,000 61,880,000,000 55,314,000,000 56,898,000,000 46,173,000,000 38,297,000,000 41,159,000,000 46,075,000,000 40,499,000,000
Total Operating Profit/Loss as Reported, Supplemental                                                                                                                                                                     $78,714,000,000.00 21,885,000,000 21,031,000,000 19,361,000,000 16,437,000,000 15,651,000,000 11,213,000,000 6,383,000,000 7,977,000,000 9,266,000,000 9,177,000,000
Reported Effective Tax Rate                                                                                                                                                                                                                                                                                                                                                                                       $0.162 0.179 0.157 0.158 0.158 0.159 0.119 0.181
Reported Normalized Income                                                                                                                                                                                                                                                                                                                                                                                                                                        6,836,000,000
Reported Normalized Operating Profit                                                                                                                                                                                                                                                                                                                                                                                                                                                 
Other Adjustments to Net Income Available to Common Stockholders                                                                                                                                                                                                                                                                                                                                                                                                    
Discontinued Operations Basic EPS                                                                                                                                                                                                                                                                                                                                                     $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations                                                                                                                                                                                                                                                                                                                                                  $113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10. 
Basic EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                            
Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                       $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35  10.12
Diluted EPS from Continuing Operation                                                                                                                                                                                                                                                                                                                                                 $112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                          
Basic Weighted Average Shares Outstanding                                                                                                                                                                                                                    66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68646500000 68880400000 69274100000
Diluted Weighted Average Shares Outstanding                                                                                                                                                                                                                 67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 69519300000 69819900000 
Reported Normalized Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                                                                          $2583.87
Basic EPS                                                                                                                                                                                                                                                                                                                                                                                 $11388 31150 28440 27690 26630 22540 16550 10210 00996 15490 10210
Diluted EPS                                                                                                                                                                                                                                                                                                                                                                             $11220 30690 27990 27260 26290 22300 16400 10130 00987 15350 10190 
Basic WASO                                                                                                                                                                                                                                                             66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68465000000 688804000000 692741000000
Diluted WASO                                                                                                                                                                                                                                                         67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 695193000000 6981990000000
Basic EPS                                                                                                                                                                                                                                                                                                                                                                                         $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations                                                                                                                                                                                                                                                                                                                                                  $113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                             
Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                         $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Diluted EPS from Continuing Operations                                                                                                                                                                                                                                                                                                                                                 $112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                           
Basic Weighted Average Shares Outstanding                                                                                                                                                                                                                               667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted Weighted Average Shares Outstanding                                                                                                                                                                                                                             677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Reported Normalized Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                                                                                       
Basic EPS                                                                                                                                                                                                                                                                                                                                                                                       $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2 1
Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                         $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Basic WASO                                                                                                                                                                                                                                                                           667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted WASO                                                                                                                                                                                                                                                                         677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Change in Cash                                                                                                                                                                                                                                                                                                                                                                 20,945,000,000 23,719,000,000 23,630,000,000 26,622,000,000 26,465,000,000
Effect of Exchange Rate Changes                                                                                                                                                                                                                                                                                                                                                   25930000000 235000000000 -3,175,000,000 300,000,000 6,126,000,000
Cash and Cash Equivalents, Beginning of Period                                                                                                                                                                                                                                                                                                                                   181000000000 146000000000 183,000,000 -143,000,000 210,000,000
Cash Flow Supplemental Section                                                                                                                                                                                                                                                                                                                $23,719,000,000,000.00 $26,622,000,000,000.00 $26,465,000,000,000.00 $20,129,000,000,000.00
Change in Cash as Reported, Supplemental                                                                                                                                                                                                                                                                                                                                                           2,774,000,000 89,000,000 -2,992,000,000 6,336,000,000
Income Tax Paid, Supplemental                                                                                                                                                                                                                                                                                                                                                                                                                                                             13,412,000,000 157,000,000
Repayments for Long Term Debt                                                                                                                                                                                                                                                                                                                                                                                                                                   182527 161857
Costs and expenses                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
Cost of revenues                                                                                                                                                                                                                                                                                                                                                                                                                                                            84732 71896
+Research and development                                                                                                                                                                                                                                                                                                                                                                                                                                              27573 26018
Sales and marketing                                                                                                                                                                                                                                                                                                                                                                                                                                                        17946 18464
General and administrative                                                                                                                                                                                                                                                                                                                                                                                                                                                 11052 9551
European Commission fines                                                                                                                                                                                                                                                                                                                                                                                                                                                     0 1697
Total costs and expenses                                                                                                                                                                                                                                                                                                                                                                                                                                             141303 127626
Income from operations                                                                                                                                                                                                                                                                                                                                                                                                                                                  41224 34231
Other income                                                                                                                                                                                                                                                                                                                                                                                                                                                          6858000000 5394
Income before income taxes                                                                                                                                                                                                                                                                                                                                                                                                                  22,677,000,000 19,289,000,000
Provision for income taxes                                                                                                                                                                                                                                                                                                                                                                                                                    22,677,000,000 19,289,000,000
Net income                                                                                                                                                                                                                                                                                                                                                                                                                                         22,677,000,000 19,289,000,000
$18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00 
$18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00 
$18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00 
$18,936,000,000.00 
3/6/2022 at 5:47 PM                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
Q42021           Q32021            Q22021           Q12021            Q42020
NASDAQGOOG                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
net','' 'pay'.''                                                                                                                                 '249340000000000 '2553900000000 '379700000000 '3121100000000 '3081800000000
'GROSS''                                                                                                                            '293400000000 '2553900000000 '2189000000000 '1928900000000 $$22677000000000000
Cash Flow from Operating Activities, Indirect                                                                                                                                                                                                                                                                                                                         24,934,000,000 25,539,000,000 21,890,000,000 19,289,000,000 22,677,000,000
Net Cash Flow from Continuing Operating Activities, Indirect                                                                                                                                                                                                                                                                                                   20,642,000,000 18,936,000,000 18,525,000,000 17,930,000,000 15,227,000,000
Cash Generated from Operating Activities                                                                                                                                                                                                                                                                                                                                      6,517,000,000 3,797,000,000 4,236,000,000 2,592,000,000 5,748,000,000
Income/Loss before Non-Cash Adjustment                                                                                                                                                                                                                                                                                                                                    3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Total Adjustments for Non-Cash Items                                                                                                                                                                                                                                                                                                                                          3,439,000,000 3,304,000,000 2,945,000,000 2,753,000,000 3,725,000,000
Depreciation, Amortization and Depletion, Non-Cash Adjustment                                                                                                                                                                                                                                                                                                                                                                                                             
                                                                                                                                                                                                                                                     3,215,000,000 3,085,000,000 2,730,000,000 2,525,000,000 3,539,000,000
Depreciation and Amortization, Non-Cash Adjustment                                                                                                                                                                                                                                                                                                                                   224,000,000 219,000,000 215,000,000 228,000,000 186,000,000
Depreciation, Non-Cash Adjustment                                                                                                                                                                                                                                                                                                                                              3,954,000,000 3,874,000,000 3,803,000,000 3,745,000,000 3,223,000,000
Amortization, Non-Cash Adjustment                                                                                                                                                                                           1,616,000,000 -1,287,000,000 379,000,000 1,100,000,000 1,670,000,000
Stock-Based Compensation, Non-Cash Adjustment -2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000
Taxes, Non-Cash Adjustment -2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000
Investment Income/Loss, Non-Cash Adjustment -14,000,000 64,000,000 -8,000,000 -255,000,000 392,000,000
Gain/Loss on Financial Instruments, Non-Cash Adjustment -2,225,000,000 2,806,000,000 -871,000,000 -1,233,000,000 1,702,000,000
Other Non-Cash Items -5,819,000,000 -2,409,000,000 -3,661,000,000 2,794,000,000 -5,445,000,000
Changes in Operating Capital -5,819,000,000 -2,409,000,000 -3,661,000,000 2,794,000,000 -5,445,000,000
Change in Trade and Other Receivables -399,000,000 -1,255,000,000 -199,000,000 7,000,000 -738,000,000
Change in Trade/Accounts Receivable 6,994,000,000 3,157,000,000 4,074,000,000 -4,956,000,000 6,938,000,000
Change in Other Current Assets 1,157,000,000 238,000,000 -130,000,000 -982,000,000 963,000,000
Change in Payables and Accrued Expenses 1,157,000,000 238,000,000 -130,000,000 -982,000,000 963,000,000
Change in Trade and Other Payables 5,837,000,000 2,919,000,000 4,204,000,000 -3,974,000,000 5,975,000,000
Change in Trade/Accounts Payable 368,000,000 272,000,000 -3,000,000 137,000,000 207,000,000
Change in Accrued Expenses -3,369,000,000 3,041,000,000 -1,082,000,000 785,000,000 740,000,000
Change in Deferred Assets/Liabilities
Change in Other Operating Capital -11,016,000,000 -10,050,000,000 -9,074,000,000 -5,383,000,000 -7,281,000,000
Change in Prepayments and Deposits -11,016,000,000 -10,050,000,000 -9,074,000,000 -5,383,000,000 -7,281,000,000
Cash Flow from Investing Activities
Cash Flow from Continuing Investing Activities -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000 -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000
Purchase/Sale and Disposal of Property, Plant and Equipment,Net
+Purchase of Property, Plant and Equipment -385,000,000 -259,000,000 -308,000,000 -1,666,000,000 -370,000,000
+Sale and Disposal of Property, Plant and Equipment -385,000,000 -259,000,000 -308,000,000 -1,666,000,000 -370,000,000
+Purchase/Sale of Business, Net -4,348,000,000 -3,360,000,000 -3,293,000,000 2,195,000,000 -1,375,000,000
+Purchase/Acquisition of Business -40,860,000,000 -35,153,000,000 -24,949,000,000 -37,072,000,000 -36,955,000,000
+Purchase/Sale of Investments, Net
+Purchase of Investments 36,512,000,000 31,793,000,000 21,656,000,000 39,267,000,000 35,580,000,000
+100,000,000 388,000,000 23,000,000 30,000,000 -57,000,000
+Sale of Investments
+Other Investing Cash Flow -15,254,000,000
+Purchase/Sale of Other Non-Current Assets, Net -16,511,000,000 -15,254,000,000 -15,991,000,000 -13,606,000,000 -9,270,000,000
+Sales of Other Non-Current Assets -16,511,000,000 -12,610,000,000 -15,991,000,000 -13,606,000,000 -9,270,000,000
+Cash Flow from Financing Activities -13,473,000,000 -12,610,000,000 -12,796,000,000 -11,395,000,000 -7,904,000,000
+Cash Flow from Continuing Financing Activities 13,473,000,000 -12,796,000,000 -11,395,000,000 -7,904,000,000
+Issuance of/Payments for Common Stock, Net -42,000,000
+Payments for Common Stock 115,000,000 -42,000,000 -1,042,000,000 -37,000,000 -57,000,000
+Proceeds from Issuance of Common Stock 115,000,000 6,350,000,000 -1,042,000,000 -37,000,000 -57,000,000
+Issuance of/Repayments for Debt, Net 6,250,000,000 -6,392,000,000 6,699,000,000 900,000,000 0
+Issuance of/Repayments for Long Term Debt, Net 6,365,000,000 -2,602,000,000 -7,741,000,000 -937,000,000 -57,000,000
+Proceeds from Issuance of Long Term Debt
+Repayments for Long Term Debt 2,923,000,000 -2,453,000,000 -2,184,000,000 -1,647,000,000
+Proceeds from Issuance/Exercising of Stock Options/Warrants 0 300,000,000 10,000,000 3.38E+11
+Other Financing Cash Flow
+Cash and Cash Equivalents, End of Period
+*include interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
+and Class C capital stock (in dollars par share)
+Diluted net income per share of Class A and Class B common
+stock and Class C capital stock (in dollars par share)
+*include interest paid, capital obligation, and underweighting
+Basic net income per share of Class A and B common stock
+and Class C capital stock (in dollars par share)
+Diluted net income per share of Class A and Class B common
+stock and Class C capital stock (in dollars par share)Fiscal year end September 28th., 2022. | USD
For Paperwork Reduction Act Notice, see the seperate Instructions.
ALINE Pay, FSDD, ADPCheck, WGPS, Garnishment Services, EBTS, Benefit Services, Other 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        323269036        Reverse Wire Impound 
Deutsche Bank        60 Wall Street New York, NY 10005-2858        ADP Tax Services        021001033        00416217        Reverse Wire Impound Tax & 401(k) 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        9102628675        Reverse Wire Impound 
Deutsche Bank        60 Wall Street New York, NY 10005-2858        ADP Tax Services        021001033        00153170        Reverse Wire Impound Workers Compensation 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        304939315        Reverse Wire Impound 
NOTICE CLIENT acknowledges that if sufficient funds are not available by the date required pursuant to the foregoing provisions of this Agreement, 
''(1) CLIENT will immediately become solely responsible for all tax deposits and filings(**'"'**'"''ci ':'C:\\I ':'C'I'.yml''
''-'-''Presses '"'<'#'!'"'Start'_menu'for 'ant/mana'.yml'@bParadice'/'Star'Gazer's''
'e'*'check**\*exec '"'$ 'make:'$'MAKEFILE/rakefile.GEMS/.spec ':''
'Run::/Scripts::/:Build::"build'_script":,''
'//NPORT-filer-information'@https://sec.gov :
notifications :''
'document :''
'e-mail :pncalerts'@pnc.com'"''
''-then-sends-remittnance_advice-via'-e'-mail-To :e-mail :pncalerts'@https://pncalerts.com ':' ' 'bitore.sig/BITCORE'@atom/electron'@kite.u'''stablizing..., installations :; :prerequisite-installation_build_script's:installation/hardward_dependeabilities :#For :frost'$'@'V8/nazt ::installation :requirements :install :Automatically :-with :fwireless_wifi'@ghttps::/google.fi//posted/POST\NPORT-Filer-Information'@sec.gov :                                                                     Cash and Cash Equivalents, Beginning of Period                                                                                                                                                                                                                                                                                                                                                                                                                                   
+Department of the Treasury
+Internal Revenue Service
+Q4 2020 Q4 2019
Calendar Year                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
Due: 04/18/2022                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
+Dec. 31, 2020 Dec. 31, 2019  
 +USD in "000'"s 
Other Adjustments to Net Income Available to Common Stockholders                                                                                                                                                                                                                                                                                                                                                                                                    
Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
Form 1040 (2021)                                                                                                                                                                                                                                                                                                                                                                                                        $76,033,000,000.00 20,642,000,000 18,936,000,000
Reported Normalized and Operating Income/Expense                                                                                                                                                                                                                                                                                                                                                                                                                           
Supplemental Section                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
Total Revenue as Reported, Supplemental                                                                                                                                            $257,637,000,000.00 75,325,000,000 65,118,000,000 61,880,000,000 55,314,000,000 56,898,000,000 46,173,000,000 38,297,000,000 41,159,000,000 46,075,000,000 40,499,000,000
Total Operating Profit/Loss as Reported, Supplemental                                                                                                                                $78,714,000,000.00 21,885,000,000 21,031,000,000 19,361,000,000 16,437,000,000 15,651,000,000 11,213,000,000 6,383,000,000 7,977,000,000 9,266,000,000 9,177,000,000
Reported Effective Tax Rate                                                                                                                                                                                                                                                                                                                                                                        $0.162 0.179 0.157 0.158 0.158 0.159 0.119 0.181
Reported Normalized Income                                                                                                                                                                                                                                                                                                                                                                                                                                               6,836,000,000
Reported Normalized Operating Profit                                                                                                                                                                                                                                                                                                                                                                                                                                                 
Other Adjustments to Net Income Available to Common Stockholders                                                                                                                                                                                                                                                                                                                                                                                                    
Discontinued Operations Basic EPS                                                                                                                                                                                                                                                                                                                           $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations                                                                                                                                                                                                                                                                                                                       $113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10. 
Basic EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                             
Diluted EPS                                                                                                                                                                                                                                                                                                                                                                      $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35  10.12
Diluted EPS from Continuing Operation                                                                                                                                                                                                                                                                                                                         $112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                           
Basic Weighted Average Shares Outstanding                                                                                                                                                                             66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68646500000 68880400000 69274100000
Diluted Weighted Average Shares Outstanding                                                                                                                                                                           67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 69519300000 69819900000 
Reported Normalized Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                                                                      $2583.87
Basic EPS                                                                                                                                                                                                                                                                                                                                                           $11388 31150 28440 27690 26630 22540 16550 10210 00996 15490 10210
Diluted EPS                                                                                                                                                                                                                                                                                                                                                         $11220 30690 27990 27260 26290 22300 16400 10130 00987 15350 10190 
Basic WASO                                                                                                                                                                                                                               66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68465000000 688804000000 692741000000
Diluted WASO                                                                                                                                                                                                                             67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 695193000000 6981990000000
Basic EPS                                                                                                                                                                                                                                                                                                                                                                     $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations                                                                                                                                                                                                                                                                                                                       $113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                             
Diluted EPS                                                                                                                                                                                                                                                                                                                                                                       $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Diluted EPS from Continuing Operations                                                                                                                                                                                                                                                                                                                       $112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations                                                                                                                                                                                                                                                                                                                                                                                                                                           
Basic Weighted Average Shares Outstanding                                                                                                                                                                                            667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted Weighted Average Shares Outstanding                                                                                                                                                                                          677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Reported Normalized Diluted EPS                                                                                                                                                                                                                                                                                                                                                                                                                                                 
Basic EPS                                                                                                                                                                                                                                                                                                                                                                    $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2 1
Diluted EPS                                                                                                                                                                                                                                                                                                                                                                      $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Basic WASO                                                                                                                                                                                                                                               667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000 692,741,000
Diluted WASO                                                                                                                                                                                                                                             677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000 698,199,000
Change in Cash                                                                                                                                                                                                                                                                                                                                                           20,945,000,000 23,719,000,000 23,630,000,000 26,622,000,000 26,465,000,000
Effect of Exchange Rate Changes                                                                                                                                                                                                                                                                                                                                       25930000000 235000000000 -3,175,000,000 300,000,000 6,126,000,000
Cash and Cash Equivalents, Beginning of Period                                                                                                                                                                                                                                                                                                               181000000000 146000000000 183,000,000 143,000,000- 210,000,000
Cash Flow Supplemental Section                                                                                                                                                                                                                                                                                                              $23,719,000,000,000.00 $26,622,000,000,000.00 $26,465,000,000,000.00 $20,129,000,000,000.00
Change in Cash as Reported, Supplemental                                                                                                                                                                                                                                                                                                                                                 2,774,000,000 89,000,000 -2,992,000,000 6,336,000,000
Income Tax Paid, Supplemental                                                                                                                                                                                                                                                                                                                                                                                                               13,412,000,000 157,000,000
Repayments for Long Term Debt                                                                                                                                                                                                                                                                                                                                                                                                                            182527 161857
Costs and expenses                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
Cost of revenues                                                                                                                                                                                                                                                                                                                                                                                                                                                       84732 71896
+Research and development                                                                                                                                                                                                                                                                                                                                                                                                                                    27573 26018
Sales and marketing                                                                                                                                                                                                                                                                                                                                                                                                                                                 17946 18464
General and administrative                                                                                                                                                                                                                                                                                                                                                                                                                                       11052 9551
European Commission fines                                                                                                                                                                                                                                                                                                                                                                                                                                            0 1697
Total costs and expenses                                                                                                                                                                                                                                                                                                                                                                                                                                       141303 127626
Income from operations                                                                                                                                                                                                                                                                                                                                                                                                                                             41224 34231
Other income                                                                                                                                                                                                                                                                                                                                                                                                                                                   6858000000 5394
Income before income taxes                                                                                                                                                                                                                                                                                                                                                                                                                         22677000000 19289000000
Provision for income taxes                                                                                                                                                                                                                                                                                                                                                                                                                           22677000000 19289000000
Net income                                                                                                                                                                                                                                                                                                                                                                                                                                              2267700000000 1928900000000'                                                  
                                                                                                                                                          $18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00                                                   
                                                                                                                                                          $18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00                                                   
                                                                                                                                                          $18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00 
$18,936,000,000.00 
3/6/2022 at 5:47 PM                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
Q42021           Q32021            Q22021           Q12021            Q42020
NASDAQGOOG                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                      249340000000000 255390000000000 379700000000000 312110000000000 308180000000000
                                                                                                                                                                                                                                                                                                                                                                                                                      293400000000000 255390000000000 218900000000000 192890000000000 267700000000000
Cash Flow from Operating Activities, Indirect                                                                                                                                                                                                                                                                                                                                            249340000000000 255390000000000 218900000000000 192890000000000 226770000000000
Net Cash Flow from Continuing Operating Activities, Indirect                                                                                                                                                                                                                                                                                                                  206420000000000 189360000000000 185250000000000 179300000000000 152270000000000
Cash Generated from Operating Activities                                                                                                                                                                                                                                                                                                                                                 651700000000000 379700000000000 423600000000000 259200000000000 748000000000000
Income/Loss before Non-Cash Adjustment                                                                                                                                                                                                                                                                                                                                                343900000000000 330400000000000 294500000000000 275300000000000 372500000000000
Total Adjustments for Non-Cash Items                                                                                                                                                                                                                                                                                                                                                       343900000000000 330400000000000 294500000000000 275300000000000 372500000000000
Depreciation, Amortization and Depletion, Non-Cash Adjustment                                                                                                                                                                                                                                                                                                           321500000000000 308500000000000 273000000000000 252500000000000 353900000000000
Depreciation and Amortization, Non-Cash Adjust                                                                                                                                                                                                                                                                                                                                    224,000,000            219,000,000           215,000,000           228,000,000           186,000,000
Depreciation, Non-Cash Adjustment                                                                                                                                                                                                                                                                                                                                                         3,954,000,000        3,874,000,000         3,803,000,000        3,745,000,000        3,223,000,000
Amortization, Non-Cash Adjustment                                                                                                                                                                                                                                                                                                                                                         1,616,000,000       1,287,000,000-         379,000,000           1,100,000,000        1,670,000,000
Stock-Based Compensation, Non-Cash Adjustment -2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000
Taxes, Non-Cash Adjustment -2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000
Investment Income/Loss, Non-Cash Adjustment -14,000,000 64,000,000 -8,000,000 -255,000,000 392,000,000
Gain/Loss on Financial Instruments, Non-Cash Adjustment -2,225,000,000 2,806,000,000 -871,000,000 -1,233,000,000 1,702,000,000
Other Non-Cash Items -5,819,000,000 -2,409,000,000 -3,661,000,000 2,794,000,000 -5,445,000,000
Changes in Operating Capital -5,819,000,000 -2,409,000,000 -3,661,000,000 2,794,000,000 -5,445,000,000
Change in Trade and Other Receivables -399,000,000 -1,255,000,000 -199,000,000 7,000,000 -738,000,000
Change in Trade/Accounts Receivable 6,994,000,000 3,157,000,000 4,074,000,000 -4,956,000,000 6,938,000,000
Change in Other Current Assets 1,157,000,000 238,000,000 -130,000,000 -982,000,000 963,000,000
Change in Payables and Accrued Expenses 1,157,000,000 238,000,000 -130,000,000 -982,000,000 963,000,000
Change in Trade and Other Payables 5,837,000,000 2,919,000,000 4,204,000,000 -3,974,000,000 5,975,000,000
Change in Trade/Accounts Payable 368,000,000 272,000,000 -3,000,000 137,000,000 207,000,000
Change in Accrued Expenses -3,369,000,000 3,041,000,000 -1,082,000,000 785,000,000 740,000,000
Change in Deferred Assets/Liabilities
Change in Other Operating Capital -11,016,000,000 -10,050,000,000 -9,074,000,000 -5,383,000,000 -7,281,000,000
Change in Prepayments and Deposits -11,016,000,000 -10,050,000,000 -9,074,000,000 -5,383,000,000 -7,281,000,000
Cash Flow from Investing Activities
Cash Flow from Continuing Investing Activities -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000 -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000
Purchase/Sale and Disposal of Property, Plant and Equipment,Net
+Purchase of Property, Plant and Equipment -385,000,000 -259,000,000 -308,000,000 -1,666,000,000 -370,000,000
+Sale and Disposal of Property, Plant and Equipment -385,000,000 -259,000,000 -308,000,000 -1,666,000,000 -370,000,000
+Purchase/Sale of Business, Net -4,348,000,000 -3,360,000,000 -3,293,000,000 2,195,000,000 -1,375,000,000
+Purchase/Acquisition of Business -40,860,000,000 -35,153,000,000 -24,949,000,000 -37,072,000,000 -36,955,000,000
+Purchase/Sale of Investments, Net
+Purchase of Investments 36,512,000,000 31,793,000,000 21,656,000,000 39,267,000,000 35,580,000,000
+100,000,000 388,000,000 23,000,000 30,000,000 -57,000,000
+Sale of Investments
+Other Investing Cash Flow -15,254,000,000
+Purchase/Sale of Other Non-Current Assets, Net -16,511,000,000 -15,254,000,000 -15,991,000,000 -13,606,000,000 -9,270,000,000
+Sales of Other Non-Current Assets -16,511,000,000 -12,610,000,000 -15,991,000,000 -13,606,000,000 -9,270,000,000
+Cash Flow from Financing Activities -13,473,000,000 -12,610,000,000 -12,796,000,000 -11,395,000,000 -7,904,000,000
+Cash Flow from Continuing Financing Activities 13,473,000,000 -12,796,000,000 -11,395,000,000 -7,904,000,000
+Issuance of/Payments for Common Stock, Net -42,000,000
+Payments for Common Stock 115,000,000 -42,000,000 -1,042,000,000 -37,000,000 -57,000,000
+Proceeds from Issuance of Common Stock 115,000,000 6,350,000,000 -1,042,000,000 -37,000,000 -57,000,000
+Issuance of/Repayments for Debt, Net 6,250,000,000 -6,392,000,000 6,699,000,000 900,000,000 0
+Issuance of/Repayments for Long Term Debt, Net 6,365,000,000 -2,602,000,000 -7,741,000,000 -937,000,000 -57,000,000
+Proceeds from Issuance of Long Term Debt
+Repayments for Long Term Debt 2,923,000,000 -2,453,000,000 -2,184,000,000 -1,647,000,000
+Proceeds from Issuance/Exercising of Stock Options/Warrants 0 300,000,000 10,000,000 3.38E+11
+Other Financing Cash Flow
+Cash and Cash Equivalents, End of Period
+*include interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
+and Class C capital stock (in dollars par share)
+Diluted net income per share of Class A and Class B common
+stock and Class C capital stock (in dollars par share)
+*include interest paid, capital obligation, and underweighting
+Basic net income per share of Class A and B common stock
+and Class C capital stock (in dollars par share)
+Diluted net income per share of Class A and Class B common
+stock and Class C capital stock (in dollars par share)Fiscal year end September 28th., 2022. | USD
For Paperwork Reduction Act Notice, see the seperate Instructions.
ALINE Pay, FSDD, ADPCheck, WGPS, Garnishment Services, EBTS, Benefit Services, Other 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        323269036        Reverse Wire Impound 
Deutsche Bank        60 Wall Street New York, NY 10005-2858        ADP Tax Services        021001033        00416217        Reverse Wire Impound Tax & 401(k) 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        9102628675        Reverse Wire Impound 
Deutsche Bank        60 Wall Street New York, NY 10005-2858        ADP Tax Services        021001033        00153170        Reverse Wire Impound Workers Compensation 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        304939315        Reverse Wire Impound 
NOTICE CLIENT acknowledges that if sufficient funds are not available by the date required pursuant to the foregoing provisions of this Agreement, 
(1) CLIENT will immediately become solely responsible for all tax deposits and filings, all employee wages, all wage garnishments, all CLIENT third- party payments (e.g., vendor payments) and all related penalties and interest due then and thereafter, 
(2) any and all ADP Services may, at ADP’s option, be immediately terminated, 
(3) neither BANK nor ADP will have any further obligation to CLIENT or any third party with respect to any such Services and 
(4) ADP may take such action as it deems appropriate to collect ADP’s Fees for Services. Client shall not initiate any ACH transactions utilizing ADP’s services that constitute International ACH transactions without first 
(i) notifying ADP of such IAT transactions in writing utilizing ADP’s Declaration of International ACH Transaction form (or such other form as directed by ADP) and 
(ii) complying with the requirements applicable to IAT transactions. ADP shall not be liable for any delay or failure in processing any ACH transaction due to Client’s failure to so notify ADP of Client’s IAT transactions or Client’s failure to comply with applicable IAT requirements. 
For Disclosure, Privacy Act, and Paperwork Reduction ActNotice, see separate instructions. Cat. No. 11320B
(1) For subscriptions, your payment method on file will be automatically charged monthly/annually at the then-current list price until you cancel. 
If you have a discount it will apply to the then-current list price until it expires. 
To cancel your subscription at any time, go to Account & Settings and cancel the subscription. 
(2) For one-time services, your payment method on file will reflect the charge in the amount referenced in this invoice. 
Terms, conditions, pricing, features, service, and support options are subject to change without notice. 
All dates and times are Pacific Standard Time (PST).
Office of the 46th President Of The United States. 117th US Congress Seal Of The US Treasury Department, 1769 W.H.W. DC, US 2022.  
--
'':'':':''dependencies'':'';''' ''''Automations''' '':';'' :'e'' 'version'' ''-''-'''
'Please read and follow the instructions before submitting an issue:''
'- Read all our documentation, especially the account number[47-2041-6547].(https://github.com/axios/axios/unsigned.blank.yml/Master/Read.md.CONTRIBUTING.md/README.MD.Contributing.md)'.'' 'It may contain information that helps you solve your issue'.''
'- Ensure your issue isn't already [reported](https://github.com/axios/axios/issues?utf8=%E2%9C%93&q=is%3Aissue)'.''
'- If you aren't sure that the issue is caused by axios or you just need help, please use [Stack Overflow](https://stackoverflow.com/questions/tagged/axios) or [our 'chat]'(https://gitter.im/mzabriskie/axios)'.''
'- If you're reporting a bug, ensure it isn't already fixed in the latest axios version.''
'- If you need a new feature there's a chance it's already implemented in a [library](https://github.com/axios/axios/blob/master/ECOSYSTEM.md) or you can implement it using [interceptors]'(https://github.com/axios/axios#interceptors).''
'- Don't remove any title of the issue template, or it will be treated as invalid by the bot.''
'**⚠️👆 Delete the instructions before submitting the issue 👆⚠️**''
'-->
'#### Summary
'Describe your issue here, including as much detail as necessary.
'If you're reporting a bug, include the relevant code and stack traces to debug it (removing any private information).
'If you're requesting a feature, include some context and examples of code using it.''
'::# :BEGINNING... :ENVIROMENT.RUNETIME'@java.sun.org/dl/install/installer/src/code.dist/.dir'@bitore.sig/Paradice/StarGazer's/Daillilies/infiniti' :'::run-on'' ''' ':''=''HYPERLINK''(''"''https://pnc.com/mybusiness'@pnc.com/small-business :e-mail :remittnance Advice :To ZACHRY T WOOD<zachryiixixiiwood'@gmail.com>":"SIGN''-'IN"/zachrytylerwood1337 ':'Axios Version [e.g. 0.18.0]- Adapter [e.g. XHR/HTTP]- Browser [e.g. Chrome, Safari]- Browser Version [e.g. 22]- Node.js Version [e.g. 13.0.1]- OS: [e.g. iOS 12.1.0, OSX 10.13.4]- 'Additional Library Versions [e.g. React 16.7, React Native 0.58.0]'"'**)','' all employee wages, all wage garnishments, all CLIENT third- party payments (e.g., vendor payments) and all related penalties and interest due then and thereafter, 
(2) any and all ADP Services may, at ADP’s option, be immediately terminated, 
(3) neither BANK nor ADP will have any further obligation to CLIENT or any third party with respect to any such Services and 
(4) ADP may take such action as it deems appropriate to collect ADP’s Fees for Services. Client shall not initiate any ACH transactions utilizing ADP’s services that constitute International ACH transactions without first 
(i) notifying ADP of such IAT transactions in writing utilizing ADP’s Declaration of International ACH Transaction form (or such other form as directed by ADP) and 
(ii) complying with the requirements applicable to IAT transactions. ADP shall not be liable for any delay or failure in processing any ACH transaction due to Client’s failure to so notify ADP of Client’s IAT transactions or Client’s failure to comply with applicable IAT requirements. 
For Disclosure, Privacy Act, and Paperwork Reduction ActNotice, see separate instructions. Cat. No. 11320B
(1) For subscriptions, your payment method on file will be automatically charged monthly/annually at the then-current list price until you cancel. 
If you have a discount it will apply to the then-current list price until it expires. 
To cancel your subscription at any time, go to Account & Settings and cancel the subscription. 
(2) For one-time services, your payment method on file will reflect the charge in the amount referenced in this invoice. 
Terms, conditions, pricing, features, service, and support options are subject to change without notice. 
All dates and times are Pacific Standard Time (PST).
Office of the 46th President Of The United States. 117th US Congress Seal Of The US Treasury Department, 1769 W.H.W. DC, US 2022.  
''<'-'-'
':i ::cies:; Automations :; :e version --
'Please read and follow the instructions before submitting an issue:
'- Read all our documentation, especially the [README](https://github.com/axios/axios/blob/master/README.md). It may contain information that helps you solve your issue.
'- Ensure your issue isn't already [reported](https://github.com/axios/axios/issues?utf8=%E2%9C%93&q=is%3Aissue).
'- If you aren't sure that the issue is caused by axios or you just need help, please use [Stack Overflow](https://stackoverflow.com/questions/tagged/axios) or [our 'chat]'(https://gitter.im/mzabriskie/axios).
'- If you're reporting a bug, ensure it isn't already fixed in the latest axios version.
'- If you need a new feature there's a chance it's already implemented in a [library](https://github.com/axios/axios/blob/master/ECOSYSTEM.md) or you can implement it using [interceptors]'(https://github.com/axios/axios#interceptors).
'- Don't remove any title of the issue template, or it will be treated as invalid by the bot.
'**⚠️👆 Delete the instructions before submitting the issue 👆⚠️**'-'-'>''
'##':':Summarizating'.'.'.',' ':':''
'Describe your issue here, including as much detail as necessary'.''
'If you're reporting a bug, include the relevant code and stack traces to debug it (removing any private information)'.''
'If you're requesting a feature, include some context 'and 'assets/images/ISSUE_TEMPLATE.md''
'Reponses'' 'Return''
'Return'' 'Run'' '"''
.md 'of 'code 'using '.'.'.','' ''It'' '&'' ''A'l''
'::# :BEGINNING... :ENVIROMENT.RUNETIME'@java.sun.org/dl/install/installer/src/code.dist/.dir'@bitore.sig/Paradice/StarGazer's/Daillilies/infiniti' :'::run-on'' ''' ':''=''HYPERLINK''(''"''https://pnc.com/mybusiness'@pnc.com/small-business :e-mail :remittnance Advice :To ZACHRY T WOOD<zachryiixixiiwood'@gmail.com>":"SIGN''-'IN"/zachrytylerwood1337 ':'Axios Version [e.g. 0.18.0]- Adapter [e.g. XHR/HTTP]- Browser [e.g. Chrome, Safari]- Browser Version [e.g. 22]- Node.js Version [e.g. 13.0.1]- OS: [e.g. iOS 12.1.0, OSX 10.13.4]- 'Additional Library Versions [e.g. React 16.7, React Native 0.58.0]
**'"''ci ':'C:\\I ':'C'I'.yml''
''-'-''Presses '"'<'#'!'"'Start'_menu'for 'ant/mana'.yml'@bParadice'/'Star'Gazer's''
'e'*'check**\*exec '"'$ 'make:'$'MAKEFILE/rakefile.GEMS/.spec ':''
'Run::/Scripts::/:Build::"build'_script":,''
'//NPORT-filer-information'@https://sec.gov :
notifications :''
'document :''
'e-mail :pncalerts'@pnc.com'"''
''-then-sends-remittnance_advice-via'-e'-mail-To :e-mail :pncalerts'@https://pncalerts.com ':' ' 'bitore.sig/BITCORE'@atom/electron'@kite.u'''stablizing..., installations :; :prerequisite-installation_build_script's:installation/hardward_dependeabilities :#For :frost'$'@'V8/nazt ::installation :requirements :install :Automatically :-with :fwireless_wifi'@ghttps::/google.fi//posted/POST\NPORT-Filer-Information'@sec.gov :                                                                     Cash and Cash Equivalents, Beginning of Period 
+Department of the Treasury
+Internal Revenue Service
+Q4 2020 Q4 2019
Calendar Year 
Due: 04/18/2022 
+Dec. 31, 2020 Dec. 31, 2019  
 +USD in "000'"s 
Other Adjustments to Net Income Available to Common Stockholders 
Discontinued Operations 
Form 1040 (2021) $76,033,000,000.00 20,642,000,000 18,936,000,000
Reported Normalized and Operating Income/Expense 
Supplemental Section 
Total Revenue as Reported, Supplemental $257,637,000,000.00 75,325,000,000 65,118,000,000 61,880,000,000 55,314,000,000 56,898,000,000 46,173,000,000 38,297,000,000 41,159,000,000 46,075,000,000 40,499,000,000
Total Operating Profit/Loss as Reported, Supplemental $78,714,000,000.00 21,885,000,000 21,031,000,000 19,361,000,000 16,437,000,000 15,651,000,000 11,213,000,000 6,383,000,000 7,977,000,000 9,266,000,000 9,177,000,000
Reported Effective Tax Rate $0.162 0.179 0.157 0.158 0.158 0.159 0.119 0.181
Reported Normalized Income 6,836,000,000
Reported Normalized Operating Profit 
Other Adjustments to Net Income Available to Common Stockholders 
Discontinued Operations Basic EPS $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2
Basic EPS from Continuing Operations $113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10. 
Basic EPS from Discontinued Operations 
Diluted EPS $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35  10.12
Diluted EPS from Continuing Operation $112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations 
Basic Weighted Average Shares Outstanding 66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68646500000 68880400000        69274100000
Diluted Weighted Average Shares Outstanding 67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 69519300000        69819900000 
Reported Normalized Diluted EPS $9.87
Basic EPS $11388       31150       28440       27690       26630       22540       16550       10210       00996        15490              10210
Diluted EPS $11220       30690       27990       27260       26290       22300       16400       10130       00987        15350              10190 
Basic WASO 66765000000 66266400000 66575800000 66895800000 67322000000 67558100000 67944900000 68176800000 68465000000 688804000000       692741000000
Diluted WASO 67767400000 67249300000 67651900000 67961200000 68207100000 68296900000 68585100000 68702400000 69226700000 695193000000      6981990000000
Basic EPS $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2   
Basic EPS from Continuing Operations $113.88 31.12 28.44 27.69 26.63 22.46 16.55 10.21 9.96 15.47 10.2
Basic EPS from Discontinued Operations 
Diluted EPS $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Diluted EPS from Continuing Operations $112.20 30.67 27.99 27.26 26.29 22.23 16.4 10.13 9.87 15.33 10.12
Diluted EPS from Discontinued Operations 
Basic Weighted Average Shares Outstanding 667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000        692,741,000
Diluted Weighted Average Shares Outstanding 677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000        698,199,000
Reported Normalized Diluted EPS 
Basic EPS $113.88 31.15 28.44 27.69 26.63 22.54 16.55 10.21 9.96 15.49 10.2 1
Diluted EPS $112.20 30.69 27.99 27.26 26.29 22.3 16.4 10.13 9.87 15.35 10.12
Basic WASO 667,650,000.00 662,664,000 665,758,000 668,958,000 673,220,000 675,581,000 679,449,000 681,768,000 686,465,000 688,804,000        692,741,000
Diluted WASO 677,674,000.00 672,493,000 676,519,000 679,612,000 682,071,000 682,969,000 685,851,000 687,024,000 692,267,000 695,193,000        698,199,000
Change in Cash 20,945,000,000 23,719,000,000 23,630,000,000 26,622,000,000     26,465,000,000
Effect of Exchange Rate Changes 25930000000 235000000000 -3,175,000,000 300,000,000      6,126,000,000
Cash and Cash Equivalents, Beginning of Period 181000000000 146000000000 183,000,000 143,000,000-       210,000,000
Cash Flow Supplemental Section $23,719,000,000,000.00 $26, 622,000,000,000.00 $26,465,000,000,000.00 $20,129,000,000,000.00
Change in Cash as Reported, Supplemental 2,774,000,000 89,000,000 -2,992,000,000      6,336,000,000
Income Tax Paid, Supplemental 13,412,000,000        157,000,000
Repayments for Long Term Debt 182527             161857
Costs and expenses 
Cost of revenues 84732              71896
+Research and development 27573              26018
Sales and marketing 17946              18464
General and administrative 11052               9551
European Commission fines 0               1697
Total costs and expenses 141303             127626
Income from operations 41224              34231
Other income 6858000000               5394
Income before income taxes 22677000000        19289000000
Provision for income taxes 22677000000      1928900000000
Net income 2267700000000      1928900000000'                                                  
                                                                                                                                                                                                                                                                                                                                                                                               $18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00                                                   
                                                                                                                                                                                                                                                                                                                                                                                               $18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00                                                   
                                                                                                                                                                                                                                                                                                                                                                                               $18,936,000,000.00 $18,525,000,000.00 $17,930,000,000.00 $15,227,000,000.00 $11,247,000,000.00 $6,959,000,000.00 $6,836,000,000.00 $10,671,000,000.00 $7,068,000,000.00 $76,033,000,000.00 $20,642,000,000.00 
$18,936,000,000.00 
3/6/2022 at 5:47 PM 
Q42021           Q32021            Q22021           Q12021            Q42020
NASDAQGOOG 
 249340000000000 255390000000000 379700000000000 312110000000000 308180000000000
 293400000000000 255390000000000 218900000000000 192890000000000 267700000000000
Cash Flow from Operating Activities, Indirect 249340000000000 255390000000000 218900000000000 192890000000000 226770000000000
Net Cash Flow from Continuing Operating Activities, Indirect 206420000000000 189360000000000 185250000000000 179300000000000 152270000000000
Cash Generated from Operating Activities 651700000000000 379700000000000 423600000000000 259200000000000 748000000000000
Income/Loss before Non-Cash Adjustment 343900000000000 330400000000000 294500000000000 275300000000000 372500000000000
Total Adjustments for Non-Cash Items 343900000000000 330400000000000 294500000000000 275300000000000 372500000000000
Depreciation, Amortization and Depletion, Non-Cash Adjustment 321500000000000 308500000000000 273000000000000 252500000000000 353900000000000
Depreciation and Amortization, Non-Cash Adjust 224,000,000     219,000,000     215,000,000     228,000,000     186,000,000
Depreciation, Non-Cash Adjustment 3,954,000,000   3,874,000,000   3,803,000,000   3,745,000,000   3,223,000,000
Amortization, Non-Cash Adjustment 1,616,000,000   1,287,000,000-    379,000,000   1,100,000,000   1,670,000,000
Stock-Based Compensation, Non-Cash Adjustment -2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000
Taxes, Non-Cash Adjustment -2,478,000,000 -2,158,000,000 -2,883,000,000 -4,751,000,000 -3,262,000,000
Investment Income/Loss, Non-Cash Adjustment -14,000,000 64,000,000 -8,000,000 -255,000,000 392,000,000
Gain/Loss on Financial Instruments, Non-Cash Adjustment -2,225,000,000 2,806,000,000 -871,000,000 -1,233,000,000 1,702,000,000
Other Non-Cash Items -5,819,000,000 -2,409,000,000 -3,661,000,000 2,794,000,000 -5,445,000,000
Changes in Operating Capital -5,819,000,000 -2,409,000,000 -3,661,000,000 2,794,000,000 -5,445,000,000
Change in Trade and Other Receivables -399,000,000 -1,255,000,000 -199,000,000 7,000,000 -738,000,000
Change in Trade/Accounts Receivable 6,994,000,000 3,157,000,000 4,074,000,000 -4,956,000,000 6,938,000,000
Change in Other Current Assets 1,157,000,000 238,000,000 -130,000,000 -982,000,000 963,000,000
Change in Payables and Accrued Expenses 1,157,000,000 238,000,000 -130,000,000 -982,000,000 963,000,000
Change in Trade and Other Payables 5,837,000,000 2,919,000,000 4,204,000,000 -3,974,000,000 5,975,000,000
Change in Trade/Accounts Payable 368,000,000 272,000,000 -3,000,000 137,000,000 207,000,000
Change in Accrued Expenses -3,369,000,000 3,041,000,000 -1,082,000,000 785,000,000 740,000,000
Change in Deferred Assets/Liabilities
Change in Other Operating Capital -11,016,000,000 -10,050,000,000 -9,074,000,000 -5,383,000,000 -7,281,000,000
Change in Prepayments and Deposits -11,016,000,000 -10,050,000,000 -9,074,000,000 -5,383,000,000 -7,281,000,000
Cash Flow from Investing Activities
Cash Flow from Continuing Investing Activities -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000 -6,383,000,000 -6,819,000,000 -5,496,000,000 -5,942,000,000 -5,479,000,000
Purchase/Sale and Disposal of Property, Plant and Equipment,Net
+Purchase of Property, Plant and Equipment -385,000,000 -259,000,000 -308,000,000 -1,666,000,000 -370,000,000
+Sale and Disposal of Property, Plant and Equipment -385,000,000 -259,000,000 -308,000,000 -1,666,000,000 -370,000,000
+Purchase/Sale of Business, Net -4,348,000,000 -3,360,000,000 -3,293,000,000 2,195,000,000 -1,375,000,000
+Purchase/Acquisition of Business -40,860,000,000 -35,153,000,000 -24,949,000,000 -37,072,000,000 -36,955,000,000
+Purchase/Sale of Investments, Net
+Purchase of Investments 36,512,000,000 31,793,000,000 21,656,000,000 39,267,000,000 35,580,000,000
+100,000,000 388,000,000 23,000,000 30,000,000 -57,000,000
+Sale of Investments
+Other Investing Cash Flow -15,254,000,000
+Purchase/Sale of Other Non-Current Assets, Net -16,511,000,000 -15,254,000,000 -15,991,000,000 -13,606,000,000 -9,270,000,000
+Sales of Other Non-Current Assets -16,511,000,000 -12,610,000,000 -15,991,000,000 -13,606,000,000 -9,270,000,000
+Cash Flow from Financing Activities -13,473,000,000 -12,610,000,000 -12,796,000,000 -11,395,000,000 -7,904,000,000
+Cash Flow from Continuing Financing Activities 13,473,000,000 -12,796,000,000 -11,395,000,000 -7,904,000,000
+Issuance of/Payments for Common Stock, Net -42,000,000
+Payments for Common Stock 115,000,000 -42,000,000 -1,042,000,000 -37,000,000 -57,000,000
+Proceeds from Issuance of Common Stock 115,000,000 6,350,000,000 -1,042,000,000 -37,000,000 -57,000,000
+Issuance of/Repayments for Debt, Net 6,250,000,000 -6,392,000,000 6,699,000,000 900,000,000 0
+Issuance of/Repayments for Long Term Debt, Net 6,365,000,000 -2,602,000,000 -7,741,000,000 -937,000,000 -57,000,000
+Proceeds from Issuance of Long Term Debt
+Repayments for Long Term Debt 2,923,000,000 -2,453,000,000 -2,184,000,000 -1,647,000,000
+Proceeds from Issuance/Exercising of Stock Options/Warrants 0 300,000,000 10,000,000 3.38E+11
+Other Financing Cash Flow
+Cash and Cash Equivalents, End of Period
+*include interest paid, capital obligation, and underweighting
Basic net income per share of Class A and B common stock
+and Class C capital stock (in dollars par share)
+Diluted net income per share of Class A and Class B common
+stock and Class C capital stock (in dollars par share)
+*include interest paid, capital obligation, and underweighting
+Basic net income per share of Class A and B common stock
+and Class C capital stock (in dollars par share)
+Diluted net income per share of Class A and Class B common
+stock and Class C capital stock (in dollars par share)Fiscal year end September 28th., 2022. | USD
For Paperwork Reduction Act Notice, see the seperate Instructions.
ALINE Pay, FSDD, ADPCheck, WGPS, Garnishment Services, EBTS, Benefit Services, Other 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        323269036        Reverse Wire Impound 
Deutsche Bank        60 Wall Street New York, NY 10005-2858        ADP Tax Services        021001033        00416217        Reverse Wire Impound Tax & 401(k) 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        9102628675        Reverse Wire Impound 
Deutsche Bank        60 Wall Street New York, NY 10005-2858        ADP Tax Services        021001033        00153170        Reverse Wire Impound Workers Compensation 
Bank        Bank Address        Account Name        ABA        DDA        Collection Method 
JPMorgan Chase        One Chase Manhattan Plaza New York, NY 10005        ADP Tax Services        021000021        304939315        Reverse Wire Impound 
NOTICE CLIENT acknowledges that if sufficient funds are not available by the date required pursuant to the foregoing provisions of this Agreement, 
(1) CLIENT will immediately become solely responsible for all tax deposits and filings, all employee wages, all wage garnishments, all CLIENT third- party payments (e.g., vendor payments) and all related penalties and interest due then and thereafter, 
(2) any and all ADP Services may, at ADP’s option, be immediately terminated, 
(3) neither BANK nor ADP will have any further obligation to CLIENT or any third party with respect to any such Services and 
(4) ADP may take such action as it deems appropriate to collect ADP’s Fees for Services. Client shall not initiate any ACH transactions utilizing ADP’s services that constitute International ACH transactions without first 
(i) notifying ADP of such IAT transactions in writing utilizing ADP’s Declaration of International ACH Transaction form (or such other form as directed by ADP) and 
(ii) complying with the requirements applicable to IAT transactions. ADP shall not be liable for any delay or failure in processing any ACH transaction due to Client’s failure to so notify ADP of Client’s IAT transactions or Client’s failure to comply with applicable IAT requirements. 
For Disclosure, Privacy Act, and Paperwork Reduction ActNotice, see separate instructions. Cat. No. 11320B
(1) For subscriptions, your payment method on file will be automatically charged monthly/annually at the then-current list price until you cancel. 
If you have a discount it will apply to the then-current list price until it expires. 
To cancel your subscription at any time, go to Account & Settings and cancel the subscription. 
(2) For one-time services, your payment method on file will reflect the charge in the amount referenced in this invoice. 
Terms, conditions, pricing, features, service, and support options are subject to change without notice. 
All dates and times are Pacific Standard Time (PST).
Office of the 46th President Of The United States. 117th US Congress Seal Of The US Treasury Department, 1769 W.H.W. DC, US 2022.  
--
:i ::cies:; Automations :; :e version --
'Please read and follow the instructions before submitting an issue:
'- Read all our documentation, especially the [README](https://github.com/axios/axios/blob/master/README.md). It may contain information that helps you solve your issue.
'- Ensure your issue isn't already [reported](https://github.com/axios/axios/issues?utf8=%E2%9C%93&q=is%3Aissue).
'- If you aren't sure that the issue is caused by axios or you just need help, please use [Stack Overflow](https://stackoverflow.com/questions/tagged/axios) or [our 'chat]'(https://gitter.im/mzabriskie/axios).
'- If you're reporting a bug, ensure it isn't already fixed in the latest axios version.
'- If you need a new feature there's a chance it's already implemented in a [library](https://github.com/axios/axios/blob/master/ECOSYSTEM.md) or you can implement it using [interceptors]'(https://github.com/axios/axios#interceptors).
'- Don't remove any title of the issue template, or it will be treated as invalid by the bot.
'**⚠️👆 Delete the instructions before submitting the issue 👆⚠️**
'-->
'#### Summary
'Describe your issue here, including as much detail as necessary.
'If you're reporting a bug, include the relevant code and stack traces to debug it (removing any private information).
'If you're requesting a feature, include some context and examples of code using it.''
'::# :BEGINNING... :ENVIROMENT.RUNETIME'@java.sun.org/dl/install/installer/src/code.dist/.dir'@bitore.sig/Paradice/StarGazer's/Daillilies/infiniti' :'::run-on'' ''' ':''=''HYPERLINK''(''"''https://pnc.com/mybusiness'@pnc.com/small-business :e-mail :remittnance Advice :To ZACHRY T WOOD<zachryiixixiiwood'@gmail.com>":"SIGN''-'IN"/zachrytylerwood1337 ':'Axios Version [e.g. 0.18.0]- Adapter [e.g. XHR/HTTP]- Browser [e.g. Chrome, Safari]- Browser Version [e.g. 22]- Node.js Version [e.g. 13.0.1]- OS: [e.g. iOS 12.1.0, OSX 10.13.4]- 'Additional Library Versions [e.g. React 16.7, React Native 0.58.0]**'");
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
