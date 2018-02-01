package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;

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

public class RPNPayoutRequest extends Request implements PaymentAttributes, CustomerInfoAttributes, AsyncAttributes {

    private String transactionType = TransactionTypes.RPN_PAYOUT;
    private BigDecimal amount;
    private String currency;
    private URL notificationUrl;
    private String bankName;
    private String subBranch;
    private String bankAccountName;
    private String bankCardNumber;
    private String province;
    private String area;

    public RPNPayoutRequest() {
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
    public RPNPayoutRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    public RPNPayoutRequest setNotificationUrl(URL notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    public RPNPayoutRequest setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public RPNPayoutRequest setSubBranch(String subBranch) {
        this.subBranch = subBranch;
        return this;
    }

    public RPNPayoutRequest setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
        return this;
    }

    public RPNPayoutRequest setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
        return this;
    }

    public RPNPayoutRequest setProvince(String province) {
        this.province = province;
        return this;
    }

    public RPNPayoutRequest setArea(String area) {
        this.area = area;
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
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement("notification_url", notificationUrl)
                .addElement("bank_name", bankName)
                .addElement("sub_branch", subBranch)
                .addElement("bank_account_name", bankAccountName)
                .addElement("bank_card_number", bankCardNumber)
                .addElement("province", province)
                .addElement("area", area)
                .addElement("billing_address", buildBillingAddress().toXML())
                .addElement("shipping_address", buildShippingAddress().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}