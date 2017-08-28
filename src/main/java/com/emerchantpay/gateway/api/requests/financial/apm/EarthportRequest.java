package com.emerchantpay.gateway.api.requests.financial.apm;

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

public class EarthportRequest extends Request implements BillingAddressAttributes, ShippingAddressAttributes {

    protected Configuration configuration;
    private Http http;

    private NodeWrapper response;

    private String transactionType = TransactionTypes.EARTHPORT;
    private String transactionId;
    private String usage;
    private String remoteIP;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private String currency;
    private String accountName;
    private String bankName;
    private String iban;
    private String bic;
    private String accountNumber;
    private String bankCode;
    private String branchCode;
    private String accountSuffix;
    private String sortCode;
    private String abaRoutingNum;

    public EarthportRequest() {
        super();
    }

    public EarthportRequest(Configuration configuration) {

        super();
        this.configuration = configuration;
    }

    public EarthportRequest setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public EarthportRequest setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public EarthportRequest setAmount(BigDecimal amount) {

        this.amount = amount;
        return this;
    }

    public EarthportRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public EarthportRequest setRemoteIp(String remoteIP) {
        this.remoteIP = remoteIP;
        return this;
    }

    public EarthportRequest setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public EarthportRequest setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
        return this;
    }

    public EarthportRequest setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public EarthportRequest setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public EarthportRequest setIBAN(String iban) {
        this.iban = iban;
        return this;
    }

    public EarthportRequest setBIC(String bic) {
        this.bic = bic;
        return this;
    }

    public EarthportRequest setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public EarthportRequest setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public EarthportRequest setBranchCode(String branchCode) {
        this.branchCode = branchCode;
        return this;
    }

    public EarthportRequest setAccountNumberSuffix(String accountSuffix) {
        this.accountSuffix = accountSuffix;
        return this;
    }

    public EarthportRequest setSortCode(String sortCode) {
        this.sortCode = sortCode;
        return this;
    }

    public EarthportRequest setAbaRoutingNumber(String abaRoutingNum) {
        this.abaRoutingNum = abaRoutingNum;
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
                .addElement("account_name", accountName).addElement("bank_name", bankName)
                .addElement("customer_phone", customerPhone).addElement("iban", iban).addElement("bic", bic)
                .addElement("account_number", accountNumber).addElement("bank_code", bankCode)
                .addElement("branch_code", branchCode).addElement("account_number_suffix", accountSuffix)
                .addElement("sort_code", sortCode).addElement("aba_routing_number", abaRoutingNum)
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