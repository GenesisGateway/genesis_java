package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.CryptoWalletProviders;

import java.util.HashMap;

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

public interface BankAttributes {

    default BankAttributes setBankName(String bankName) {
        getBankAttrParamsMap().put("bank_name", bankName);
        getBankAttrRequestBuilder().addElement("bank_name", bankName);
        return this;
    }

    default String getBankName() {
        return getBankAttrParamsMap().get("bank_name");
    }

    default BankAttributes setBankProvince(String bankProvince) {
        getBankAttrParamsMap().put("bank_province", bankProvince);
        getBankAttrRequestBuilder().addElement("bank_province", bankProvince);
        return this;
    }

    default String getBankProvince() {
        return getBankAttrParamsMap().get("bank_province");
    }

    default BankAttributes setBankBranch(String bankBranch) {
        getBankAttrParamsMap().put("bank_branch", bankBranch);
        getBankAttrRequestBuilder().addElement("bank_branch", bankBranch);
        return this;
    }

    default String getBankBranch() {
        return getBankAttrParamsMap().get("bank_branch");
    }

    default BankAttributes setBankAccountName(String bankAccName) {
        getBankAttrParamsMap().put("bank_account_name", bankAccName);
        getBankAttrRequestBuilder().addElement("bank_account_name", bankAccName);
        return this;
    }

    default String getBankAccountName() {
        return getBankAttrParamsMap().get("bank_account_name");
    }

    default BankAttributes setBankAccountNumber(String bankAccNumber) {
        getBankAttrParamsMap().put("bank_account_number", bankAccNumber);
        getBankAttrRequestBuilder().addElement("bank_account_number", bankAccNumber);
        return this;
    }

    default String getBankAccountNumber() {
        return getBankAttrParamsMap().get("bank_account_number");
    }

    default BankAttributes setBankAccountType(String bankAccountType) {
        getBankAttrParamsMap().put("bank_account_type", bankAccountType);
        getBankAttrRequestBuilder().addElement("bank_account_type", bankAccountType);
        return this;
    }

    default String getBankAccountType() {
        return getBankAttrParamsMap().get("bank_account_type");
    }

    default BankAttributes setPayerBankPhoneNumber(String payerBankPhoneNumber) {
        getBankAttrParamsMap().put("payer_bank_phone_number", payerBankPhoneNumber);
        getBankAttrRequestBuilder().addElement("payer_bank_phone_number", payerBankPhoneNumber);
        return this;
    }

    default String getPayerBankPhoneNumber() {
        return getBankAttrParamsMap().get("payer_bank_phone_number");
    }

    default BankAttributes setBankAccountVerificationDigit(String bankAccountVerificationDigit) {
        getBankAttrParamsMap().put("bank_account_verification_digit", bankAccountVerificationDigit);
        getBankAttrRequestBuilder().addElement("bank_account_verification_digit", bankAccountVerificationDigit);
        return this;
    }

    default String getBankAccountVerificationDigit() {
        return getBankAttrParamsMap().get("bank_account_verification_digit");
    }

    default RequestBuilder buildBankParams() {
        return getBankAttrRequestBuilder();
    }

    RequestBuilder getBankAttrRequestBuilder();

    HashMap<String, String> getBankAttrParamsMap();

}
