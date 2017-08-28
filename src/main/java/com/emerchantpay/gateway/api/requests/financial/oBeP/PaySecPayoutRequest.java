package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BillingAddressAttributes;
import com.emerchantpay.gateway.api.interfaces.ShippingAddressAttributes;
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

public class PaySecPayoutRequest extends Request implements BillingAddressAttributes, ShippingAddressAttributes {

    protected Configuration configuration;
    private Http http;

    private NodeWrapper response;

    private String transactionType = TransactionTypes.PAYSEC_PAYOUT;
    private String transactionId;
    private String usage;
    private String remoteIP;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private String currency;
    private String bankCode;
    private String bankName;
    private String bankBranch;
    private String bankAccountName;
    private String bankAccountNumber;
    private URL returnSuccessUrl;
    private URL returnFailureUrl;
    private URL notificationUrl;

    public PaySecPayoutRequest() {
        super();
    }

    public PaySecPayoutRequest(Configuration configuration) {

        super();
        this.configuration = configuration;
    }

    public PaySecPayoutRequest setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public PaySecPayoutRequest setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public PaySecPayoutRequest setAmount(BigDecimal amount) {

        this.amount = amount;
        return this;
    }

    public PaySecPayoutRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public PaySecPayoutRequest setRemoteIp(String remoteIP) {
        this.remoteIP = remoteIP;
        return this;
    }

    public PaySecPayoutRequest setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public PaySecPayoutRequest setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
        return this;
    }

    public PaySecPayoutRequest setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public PaySecPayoutRequest setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public PaySecPayoutRequest setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
        return this;
    }

    public PaySecPayoutRequest setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
        return this;
    }

    public PaySecPayoutRequest setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public PaySecPayoutRequest setReturnSuccessUrl(URL returnSuccessUrl) {
        this.returnSuccessUrl = returnSuccessUrl;
        return this;
    }

    public PaySecPayoutRequest setReturnFailureUrl(URL returnFailureUrl) {
        this.returnFailureUrl = returnFailureUrl;
        return this;
    }

    public PaySecPayoutRequest setNotificationUrl(URL notificationUrl) {
        this.notificationUrl = notificationUrl;
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

        if (amount != null && currency != null) {

            Currency curr = new Currency();

            curr.setAmountToExponent(amount, currency);
            convertedAmount = curr.getAmount();
        }

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement("transaction_id", transactionId).addElement("usage", usage)
                .addElement("remote_ip", remoteIP).addElement("amount", convertedAmount)
                .addElement("currency", currency).addElement("customer_email", customerEmail)
                .addElement("customer_phone", customerPhone).addElement("bank_code", bankCode)
                .addElement("bank_name", bankName).addElement("bank_branch", bankBranch)
                .addElement("bank_account_name", bankAccountName).addElement("bank_account_number", bankAccountNumber)
                .addElement("return_success_url", returnSuccessUrl).addElement("return_failure_url", returnFailureUrl)
                .addElement("notification_url", notificationUrl)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public Request execute(Configuration configuration) {

        configuration.setAction("process");
        http = new Http(configuration);
        response = http.post(configuration.getBaseUrl(), this);

        return this;
    }

    public NodeWrapper getResponse() {
        return response;
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}