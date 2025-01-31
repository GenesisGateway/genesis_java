package com.emerchantpay.gateway.api.requests.financial.card.recurring;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.CreditCardAttributes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.*;
import com.emerchantpay.gateway.api.interfaces.financial.traveldata.TravelDataAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
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
 * @deprecated Init Recurring Sale transaction will be soon deprecated. Please start using Sale or Authorize transaction
 * with initial recurring type instead.
 */
@Deprecated
public class InitRecurringSaleRequest extends FinancialRequest implements CreditCardAttributes,
        CustomerInfoAttributes, DescriptorAttributes, RiskParamsAttributes, FXAttributes, BusinessParamsAttributes,
        TravelDataAttributes, TokenizationAttributes, RecurringCategoryAttributes, ManagedRecurringAttributes {

    private String transactionType = TransactionTypes.INIT_RECURRING_SALE;
    private Boolean moto;
    private Boolean schemeTokenized;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public InitRecurringSaleRequest() {
        super();
    }

    @Override
    public Boolean getZeroAmountSupport(){
        return true;
    }

    public InitRecurringSaleRequest setMoto(Boolean moto) {
        this.moto = moto;
        return this;
    }

    public InitRecurringSaleRequest setSchemeTokenized(Boolean schemeTokenized) {
        this.schemeTokenized = schemeTokenized;
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

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.cardHolder, getCardHolder());
        requiredParams.putAll(getCreditCardConditionalRequiredParams(getToken()));
        requiredParams.putAll(getTokenizationConditionalRequiredParams(getCustomerEmail(), getCardNumber()));

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCreditCardParams().toXML())
                .addElement("moto", moto)
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildRecurringCategoryParams().toXML())
                .addElement(buildManagedRecurring().toXML())
                .addElement(buildBillingAddress(false).toXML())
                .addElement(buildShippingAddress(false).toXML())
                .addElement("dynamic_descriptor_params", buildDescriptorParams().toXML())
                .addElement("risk_params", buildRiskParams().toXML())
                .addElement("scheme_tokenized", schemeTokenized)
                .addElement("business_attributes", buildBusinessParams().toXML())
                .addElement(buildFXParams().toXML())
                .addElement("travel", buildTravelDataParams().toXML())
                .addElement(buildTokenizationParams().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}