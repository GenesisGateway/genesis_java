package com.emerchantpay.gateway.api.requests.financial.apm;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;

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

public class CitadelPayOutRequest extends Request implements PaymentAttributes, CustomerInfoAttributes, AsyncAttributes {

    private String transactionType = TransactionTypes.CITADEL_PAYOUT;
    private URL notificationUrl;
    private BigDecimal amount;
    private String currency;
    private String holderName;
    private String iban;
    private String swiftCode;
    private String accountNumber;
    private String bankName;
    private String bankCity;
    private String bankCode;
    private String branchCode;
    private String branchCheckDigit;

    public CitadelPayOutRequest() {
        super();
    }

    @Override
    public PaymentAttributes setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public PaymentAttributes setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    public CitadelPayOutRequest setNotificationUrl(URL notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    public CitadelPayOutRequest setHolderName(String holderName) {
        this.holderName = holderName;
        return this;
    }

    public CitadelPayOutRequest setIBAN(String iban) {
        this.iban = iban;
        return this;
    }

    public CitadelPayOutRequest setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
        return this;
    }

    public CitadelPayOutRequest setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public CitadelPayOutRequest setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public CitadelPayOutRequest setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public CitadelPayOutRequest setBankCity(String bankCity) {
        this.bankCity = bankCity;
        return this;
    }

    public CitadelPayOutRequest setBranchCode(String branchCode) {
        this.branchCode = branchCode;
        return this;
    }


    public CitadelPayOutRequest setBranchCheckDigit(String branchCheckDigit) {
        this.branchCheckDigit = branchCheckDigit;
        return this;
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

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement("notification_url", notificationUrl)
                .addElement("holder_name", holderName)
                .addElement("iban", iban).addElement("swift_code", swiftCode).addElement("bank_name", bankName)
                .addElement("bank_code", bankCode).addElement("bank_city", bankCity).addElement("branch_code", branchCode)
                .addElement("branch_check_digit", branchCheckDigit).addElement("account_number", accountNumber)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}