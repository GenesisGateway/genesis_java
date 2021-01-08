package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Currency;

import java.math.BigDecimal;
import java.util.*;

public class OnlineBankingRequest extends Request implements PaymentAttributes, CustomerInfoAttributes, AsyncAttributes, BillingAddressAttributes, ShippingAddressAttributes {

    private static final String transactionType = TransactionTypes.ONLINE_BANKING;
    private String currency;
    private BigDecimal amount;
    private String bankCode;
    private String documentId;
    private String paymentType;
    private String virtualPaymentAddress;
    private String consumerReference;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    private static final ArrayList<String> allowedPaymentTypes = new ArrayList<>(Arrays.asList(
            "online_banking",
            "quick_payment",
            "qr_payment",
            "netbanking",
            "alipay_qr"
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

    private static final ArrayList<String> allowedBankCodesCLP = new ArrayList<>(Arrays.asList(
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

    private static final ArrayList<String> allowedBankCodesPYG = new ArrayList<>(Arrays.asList(
            "PE"));

    private static final ArrayList<String> allowedBankCodesPHP = new ArrayList<>(Arrays.asList(
            "DRAGONPAY"));

    private static final ArrayList<String> allowedBankCodesSGD = new ArrayList<>(Arrays.asList(
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

    private static final ArrayList<String> allowedBankCodesUYU = new ArrayList<>(Arrays.asList(
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
    }

    public OnlineBankingRequest() {
        super();
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public OnlineBankingRequest setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public OnlineBankingRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public OnlineBankingRequest setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public String getDocumentId() {
        return documentId;
    }

    public OnlineBankingRequest setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public OnlineBankingRequest setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getVirtualPaymentAddress() {
        return virtualPaymentAddress;
    }

    public OnlineBankingRequest setVirtualPaymentAddress(String virtualPaymentAddress) {
        this.virtualPaymentAddress = virtualPaymentAddress;
        return this;
    }

    public String getConsumerReference() {
        return consumerReference;
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