package com.emerchantpay.gateway.api.requests.financial.card.recurring;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.traveldata.TravelDataAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.util.Currency;

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

/**
 * @deprecated Recurring Sale transaction will be soon deprecated. Please start using Sale or Authorize transaction
 * with initial recurring type instead.
 */
@Deprecated
public class RecurringSaleRequest extends FinancialRequest implements BusinessParamsAttributes, TravelDataAttributes {

    private String transactionType = TransactionTypes.RECURRING_SALE;
    private String referenceId;
    private Boolean moto;

    public RecurringSaleRequest() {
        super();
    }

    public  RecurringSaleRequest setMoto(Boolean moto) {
        this.moto = moto;
        return this;
    }

    public RecurringSaleRequest setReferenceId(String referencialId) {
        this.referenceId = referencialId;
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

    @Override
    public RequestBuilder buildPaymentParams() {

        BigDecimal convertedAmount = null;

        if (getAmount() != null && getCurrency() != null) {

            Currency curr = new Currency();

            curr.setAmountToExponent(getAmount(), getCurrency());
            convertedAmount = curr.getAmount();
        } else if (getAmount() != null) {
            convertedAmount = getAmount();
        }

        if (getValidator().isValidAmount(convertedAmount)) {
            getPaymentAttrRequestBuilder().addElement("amount", convertedAmount);
        } else {
            throw new RegexException(getValidator().getInvalidParams());
        }

        return getPaymentAttrRequestBuilder();
    }

    protected RequestBuilder buildRequest(String root) {

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement("business_attributes", buildBusinessParams().toXML())
                .addElement("reference_id", referenceId)
                .addElement("moto", moto)
                .addElement("travel", buildTravelDataParams().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
