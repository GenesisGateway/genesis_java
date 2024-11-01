package com.emerchantpay.gateway.api.requests.financial.apm;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.SenderAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Currency;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class TransferToPayoutRequest extends FinancialRequest implements AsyncAttributes, SenderAttributes,
        BillingAddressAttributes {

    private String transactionType = TransactionTypes.TRANSFER_TO_PAYOUT;

    @Getter
    private String customerEmail;
    @Getter
    private String payerId;
    @Getter
    private String bankAccountNumber;
    // indian_financial_system_code
    @Getter
    private String ifsCode;
    @Getter
    private String msisdn;
    @Getter
    private String branchNumber;
    @Getter
    private String accountType;
    @Getter
    private String registeredName;
    @Getter
    private String registrationNumber;
    @Getter
    private String iban;
    @Getter
    private String idType;
    @Getter
    private String idNumber;

    private static final Set<String> currencies = new HashSet<>(Arrays.asList(
            Currency.EUR.getCurrency(),
            Currency.GBP.getCurrency(),
            Currency.HKD.getCurrency(),
            Currency.USD.getCurrency()
    ));

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public TransferToPayoutRequest(){
        super();
    }

    public TransferToPayoutRequest setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public TransferToPayoutRequest setPayerId(String payerId) {
        this.payerId = payerId;
        return this;
    }

    public TransferToPayoutRequest setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public TransferToPayoutRequest setIfsCode(String ifsCode) {
        this.ifsCode = ifsCode;
        return this;
    }

    public TransferToPayoutRequest setMsisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public TransferToPayoutRequest setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
        return this;
    }

    public TransferToPayoutRequest setAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public TransferToPayoutRequest setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
        return this;
    }

    public TransferToPayoutRequest setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    public TransferToPayoutRequest setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public TransferToPayoutRequest setIdType(String idType) {
        this.idType = idType;
        return this;
    }

    public TransferToPayoutRequest setIdNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void validateCurrency() throws InvalidParamException {
        if (!currencies.contains(getCurrency())) {
            throw new RequiredParamsException("Invalid currency code [" + getCurrency() + "]. Allowed currency codes are: "
                    + currencies.stream().sorted().collect(Collectors.toList()));
        }

        return;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
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
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.payerId, payerId);
        requiredParams.put(RequiredParameters.billingAddress, getBillingPrimaryAddress());

        // Validate request
        validator.isValidRequest(requiredParams);
        validateCurrency();

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildSenderParams().toXML())
                .addElement("customer_email", customerEmail).addElement("payer_id", payerId)
                .addElement("bank_account_number", bankAccountNumber).addElement("indian_financial_system_code", ifsCode)
                .addElement("msisdn", msisdn).addElement("branch_number", branchNumber)
                .addElement("account_type", accountType).addElement("registered_name", registeredName)
                .addElement("registration_number", registrationNumber).addElement("iban", iban)
                .addElement("id_type", idType).addElement("id_number", idNumber)
                .addElement("billing_address", buildBillingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

}
