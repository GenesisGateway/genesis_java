package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Currency;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

public class OnlineBankingRequest extends FinancialRequest implements CustomerInfoAttributes, AsyncAttributes, BillingAddressAttributes, ShippingAddressAttributes {

    private static final String transactionType = TransactionTypes.ONLINE_BANKING;

    @Getter
    private String bankCode;
    @Getter
    private String documentId;
    @Getter
    private String paymentType;
    @Getter
    private String virtualPaymentAddress;
    @Getter
    private String consumerReference;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    private static final ArrayList<String> allowedPaymentTypes = new ArrayList<>(Arrays.asList(
            "online_banking",
            "quick_payment",
            "qr_payment",
            "netbanking",
            "alipay_qr",
            "bancomer",
            "scotiabank"
    ));

    private static final ArrayList<String> allowedBankCodesCNY = new ArrayList<>(Arrays.asList(
            "ABC",
            "BOB",
            "BOC",
            "BOCO",
            "CCB",
            "CCD",
            "CEB",
            "CIB",
            "CMB",
            "CMBC",
            "CITIC",
            "ICBC",
            "GDB",
            "HXB",
            "PINGANBANK",
            "PSBC",
            "QUICKPAY",
            "SHB",
            "SPABANK",
            "SPDB",
            "YLB"));

    private static final ArrayList<String> allowedBankCodesCLP = new ArrayList<>(Collections.singletonList(
            "SP"));

    private static final ArrayList<String> allowedBankCodesINR = new ArrayList<>(Arrays.asList(
            "ABPB",
            "AIRP",
            "ALLA",
            "ANDB",
            "BARB_R",
            "BBKM",
            "BKDN",
            "BKID",
            "CBIN",
            "CIUB",
            "CNRB",
            "CORP",
            "COSB",
            "CSBK",
            "DBSS",
            "DCBL",
            "DEUT",
            "DLXB",
            "ESFB",
            "FDRL",
            "HDFC",
            "IBKL",
            "ICIC",
            "IDFB",
            "IDIB",
            "INDB",
            "IOBA",
            "JAKA",
            "JSBP",
            "KARB",
            "KKBK",
            "KVBL",
            "LAVB_C",
            "LAVB_R",
            "MAHB",
            "NKGS",
            "ORBC",
            "PMCB",
            "PSIB",
            "PUNB_R",
            "RATN",
            "SBBJ",
            "SBHY",
            "SBIN",
            "SBMY",
            "SBTR",
            "SCBL",
            "SIBL",
            "SRCB",
            "STBP",
            "SVCB",
            "SYNB",
            "TMBL",
            "TNSC",
            "UBIN",
            "UCBA",
            "UTBI",
            "UTIB",
            "VIJB",
            "YESB"));

    private static final ArrayList<String> allowedBankCodesIDR = new ArrayList<>(Arrays.asList(
            "ATMVA",
            "BCA_IDR",
            "BRI_IDR",
            "BNI_IDR",
            "BTN_IDR",
            "CIMB_IDR",
            "DMN_IDR",
            "MDR_IDR",
            "PMB_IDR",
            "VA",
            "SP"));

    private static final ArrayList<String> allowedBankCodesMYR = new ArrayList<>(Arrays.asList(
            "CASH-711",
            "FPX_ABB,",
            "AFFIN-EPG",
            "FPX_ABMB",
            "FPX_AMB",
            "AMB-W2W",
            "FPX_BIMB",
            "BANKISLAM",
            "FPX_BMMB",
            "FPX_BKRM",
            "FPX_BSN",
            "CIMB_MYR",
            "FPX_CIMBCLICKS",
            "CIMB-CLICKS",
            "FPX",
            "FPX_HLB",
            "HLB-ONL",
            "HLE_MYR",
            "FPX_KFN",
            "MAY_MYR",
            "FPX_MB2U",
            "MB2U",
            "FPX_OCBC",
            "FPX_PBB",
            "PUBLICKBANK",
            "PBE_MYR",
            "RHB_MYR",
            "FPX_RHB",
            "RHB-ONL",
            "FPX_SCB",
            "FPX_UOB"));

    private static final ArrayList<String> allowedBankCodesPYG = new ArrayList<>(Collections.singletonList(
            "PE"));

    private static final ArrayList<String> allowedBankCodesPHP = new ArrayList<>(Collections.singletonList(
            "DRAGONPAY"));

    private static final ArrayList<String> allowedBankCodesSGD = new ArrayList<>(Collections.singletonList(
            "ENETS-D"));

    private static final ArrayList<String> allowedBankCodesTHB = new ArrayList<>(Arrays.asList(
            "BBL_THB",
            "TH_PB_BBLPN",
            "KKB_THB",
            "BAY_THB",
            "TH_PB_BAYPN",
            "KTB_THB",
            "TH_PB_KTBPN",
            "SCB_THB",
            "TH_PB_SCBPN",
            "UOB_THB"));

    private static final ArrayList<String> allowedBankCodesUYU = new ArrayList<>(Collections.singletonList(
            "AI"));

    private static final ArrayList<String> allowedBankCodesVND = new ArrayList<>(Arrays.asList(
            "VTCP_VPBANK",
            "VTCP_ABBANK",
            "VTCP_ACB",
            "VTCP_AGRIBANK",
            "VTCP_BACABANK",
            "VTCP_BIDV",
            "VTCP_BVB",
            "VTCP_DONGABANK",
            "VTCP_EXIMBANK",
            "VTCP_GPBANK",
            "VTCP_HDBANK",
            "VTCP_LVPB",
            "VTCP_MB",
            "VTCP_MARITIMEBANK",
            "VTCP_NAMABANK",
            "VTCP_NAVIBANK",
            "VTCP_OCEANBANK",
            "VTCP_PGBANK",
            "VTCP_PHUONGDONG",
            "VTCP_SHB",
            "VTCP_SACOMBANK",
            "VTCP-SAIGON_BANK",
            "VTCP_SEAABANK",
            "TCP_TECHCOMBANK",
            "VTCP_TIENPHONGBANK",
            "VTCP_VIB",
            "VTCP_VIETABANK",
            "VTCP_VIETCOMBANK",
            "VTCP_VIETINBANK"));

    private static final ArrayList<String> allowedBankCodesPEN = new ArrayList<>(Arrays.asList(
            "BC",
            "IB",
            "EF",
            "BP"
    ));

    private static final ArrayList<String> allowedBankCodesEUR = new ArrayList<>(Arrays.asList(
            "PF",
            "BCT",
            "TRL"
    ));

    private static final ArrayList<String> allowedBankCodesUSD = new ArrayList<>(Arrays.asList(
            "SN",
            "IT",
            "BR",
            "BB",
            "WP",
            "BN",
            "PS",
            "BO"
    ));

    private static final ArrayList<String> allowedBankCodesMXN = new ArrayList<>(Arrays.asList(
            "SE",
            "BQ"
    ));

    private static final ArrayList<String> allowedBankCodesBRL = new ArrayList<>(Arrays.asList(
            "CA"
    ));

    private static final ArrayList<String> allowedBankCodesCHF = new ArrayList<>(Arrays.asList(
            "PF"
    ));

    private static final ArrayList<String> allowedBankCodesCAD = new ArrayList<>(Arrays.asList(
            "CPI"
    ));

    private static final ArrayList<String> allowedBankCodesPLN = new ArrayList<>(Arrays.asList(
            "BLK"
    ));

    private static final ArrayList<String> allowedBankCodesAUD = new ArrayList<>(Arrays.asList(
            "PID"
    ));

    private static final ArrayList<String> allowedBankCodesGBP = new ArrayList<>(Arrays.asList(
            "TRL"
    ));

    private static final ArrayList<String> allowedBankCodesNZD = new ArrayList<>(Arrays.asList(
            "POLI"
    ));

    private static final HashMap<String, ArrayList<String>> currenciesBankCodesMap = new HashMap<String, ArrayList<String>>();

    {
        currenciesBankCodesMap.put(Currency.CNY.getCurrency(), allowedBankCodesCNY);
        currenciesBankCodesMap.put(Currency.CLP.getCurrency(), allowedBankCodesCLP);
        currenciesBankCodesMap.put(Currency.INR.getCurrency(), allowedBankCodesINR);
        currenciesBankCodesMap.put(Currency.IDR.getCurrency(), allowedBankCodesIDR);
        currenciesBankCodesMap.put(Currency.MYR.getCurrency(), allowedBankCodesMYR);
        currenciesBankCodesMap.put(Currency.PYG.getCurrency(), allowedBankCodesPYG);
        currenciesBankCodesMap.put(Currency.PHP.getCurrency(), allowedBankCodesPHP);
        currenciesBankCodesMap.put(Currency.SGD.getCurrency(), allowedBankCodesSGD);
        currenciesBankCodesMap.put(Currency.THB.getCurrency(), allowedBankCodesTHB);
        currenciesBankCodesMap.put(Currency.UYU.getCurrency(), allowedBankCodesUYU);
        currenciesBankCodesMap.put(Currency.VND.getCurrency(), allowedBankCodesVND);
        currenciesBankCodesMap.put(Currency.PEN.getCurrency(), allowedBankCodesPEN);
        currenciesBankCodesMap.put(Currency.EUR.getCurrency(), allowedBankCodesEUR);
        currenciesBankCodesMap.put(Currency.USD.getCurrency(), allowedBankCodesUSD);
        currenciesBankCodesMap.put(Currency.MXN.getCurrency(), allowedBankCodesMXN);
        currenciesBankCodesMap.put(Currency.BRL.getCurrency(), allowedBankCodesBRL);
        currenciesBankCodesMap.put(Currency.CHF.getCurrency(), allowedBankCodesCHF);
        currenciesBankCodesMap.put(Currency.CAD.getCurrency(), allowedBankCodesCAD);
        currenciesBankCodesMap.put(Currency.PLN.getCurrency(), allowedBankCodesPLN);
        currenciesBankCodesMap.put(Currency.AUD.getCurrency(), allowedBankCodesAUD);
        currenciesBankCodesMap.put(Currency.GBP.getCurrency(), allowedBankCodesGBP);
        currenciesBankCodesMap.put(Currency.NZD.getCurrency(), allowedBankCodesNZD);
    }

    public OnlineBankingRequest() {
        super();
    }

    @Override
    public OnlineBankingRequest setAmount(BigDecimal amount) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setAmount(amount);
        return this;
    }

    @Override
    public OnlineBankingRequest setCurrency(String currency) {
        //TODO: Do we really need to return this class, not PaymentAttributes, like all standard methods do?
        super.setCurrency(currency);
        return this;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    public OnlineBankingRequest setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public OnlineBankingRequest setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public OnlineBankingRequest setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public OnlineBankingRequest setVirtualPaymentAddress(String virtualPaymentAddress) {
        this.virtualPaymentAddress = virtualPaymentAddress;
        return this;
    }

    public OnlineBankingRequest setConsumerReference(String consumerReference) {
        this.consumerReference = consumerReference;
        return this;
    }

    @Override
    public String toXML() {
        return buildRequest("payment_transaction").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.amount, (getAmount() != null ? getAmount().toString() : null));
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.remoteIp, getRemoteIp());
        requiredParams.put(RequiredParameters.bankCode, getBankCode());
        requiredParams.put(RequiredParameters.billingAddress, getBillingPrimaryAddress());
        requiredParams.put(RequiredParameters.country, getBillingCountryCode());

        // Validate request
        validator.isValidRequest(requiredParams);

        if (!validator.isValidVirtualPaymentAddress("virtual_payment_address", getVirtualPaymentAddress(), false)
                || !validator.isValidVirtualPaymentAddress("consumer_reference", getConsumerReference(), false)) {
            ArrayList<String> invalidParams = new ArrayList<String>(validator.getInvalidParams());
            validator.clearInvalidParams();
            throw new RegexException(invalidParams);
        }

        validateAllowedPaymentTypes();
        validateAllowedCurrencyBankCode(getCurrency(), getBankCode());

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement("bank_code", bankCode)
                .addElement("document_id", documentId)
                .addElement("payment_type", getPaymentType())
                .addElement("virtual_payment_address", getVirtualPaymentAddress())
                .addElement("consumer_reference", getConsumerReference())
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

    private void validateAllowedPaymentTypes() {
        if (getPaymentType() != null && !getPaymentType().isEmpty() && !allowedPaymentTypes.contains(getPaymentType())) {
            throw new InvalidParamException("Invalid payment type. Allowed types are: "
                    + allowedPaymentTypes);
        }
    }

    private void validateAllowedCurrencyBankCode(String currency, String bankCode) {
        for (String currencyKey : currenciesBankCodesMap.keySet()) {
            if (currencyKey.equals(currency)) {
                ArrayList<String> bankCodesList = currenciesBankCodesMap.get(currencyKey);
                if (bankCodesList.contains(bankCode)) {
                    return;
                } else {
                    throw new InvalidParamException("Invalid bank code for " + currencyKey + ". Allowed bank codes are: "
                            + bankCodesList);
                }
            }
        }

        throw new InvalidParamException("Invalid currency " + currency);
    }
}