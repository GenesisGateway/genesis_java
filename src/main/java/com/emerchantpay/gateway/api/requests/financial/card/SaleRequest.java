package com.emerchantpay.gateway.api.requests.financial.card;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.CreditCardAttributes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.UcofAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.*;
import com.emerchantpay.gateway.api.interfaces.financial.funding.FundingAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.traveldata.TravelDataAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import lombok.Getter;

import java.util.Arrays;
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

public class SaleRequest extends FinancialRequest implements CreditCardAttributes, CustomerInfoAttributes,
        DescriptorAttributes, RiskParamsAttributes, FXAttributes, ScaAttributes, BusinessParamsAttributes,
        CryptoAttributes, TravelDataAttributes, UcofAttributes, TokenizationAttributes, RecurringTypeAttributes,
        RecurringCategoryAttributes, ManagedRecurringAttributes, FundingAttributes {

    private String transactionType = TransactionTypes.SALE;
    private Boolean moto;
    private Boolean gaming;
    private Boolean schemeTokenized;

    @Getter
    private String referenceId;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public SaleRequest() {
        super();
    }

    @Override
    public Boolean getZeroAmountSupport(){
        return true;
    }

    public SaleRequest setMoto(Boolean moto) {
        this.moto = moto;
        return this;
    }

    public SaleRequest setGaming(Boolean gaming) {
        this.gaming = gaming;
        return this;
    }

    public SaleRequest setSchemeTokenized(Boolean schemeTokenized) {
        this.schemeTokenized = schemeTokenized;
        return this;
    }

    public SaleRequest setReferenceId(String referenceId) {
        this.referenceId = referenceId;
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

        boolean isSubsequentRecurringType = "subsequent".equalsIgnoreCase(getRecurringType());

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount() != null ? getAmount().toString() : null);
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.putAll(getCreditCardConditionalRequiredParams(getToken()));
        requiredParams.putAll(getTokenizationConditionalRequiredParams(getCustomerEmail(), getCardNumber(), isSubsequentRecurringType));
        if (!isSubsequentRecurringType) {
            requiredParams.put(RequiredParameters.cardHolder, getCardHolder());
        }

        // Validate request
        validator.isValidRequest(requiredParams);
        validateManagedRecurring(getRecurringType());

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement("reference_id", referenceId)
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCreditCardParams().toXML())
                .addElement("gaming", gaming)
                .addElement("moto", moto)
                .addElement(buildCryptoParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildRecurringAttrParams().toXML())
                .addElement(buildRecurringCategoryParams().toXML())
                .addElement(buildManagedRecurring().toXML())
                .addElement(buildBillingAddress(false).toXML())
                .addElement(buildShippingAddress(false).toXML())
                .addElement("dynamic_descriptor_params", buildDescriptorParams().toXML())
                .addElement("risk_params", buildRiskParams().toXML())
                .addElement("sca_params", buildScaParams().toXML())
                .addElement("scheme_tokenized", schemeTokenized)
                .addElement("business_attributes", buildBusinessParams().toXML())
                .addElement(buildFXParams().toXML())
                .addElement("travel", buildTravelDataParams().toXML())
                .addElement(buildUcofParams(getCredentialOnFile()).toXML())
                .addElement(buildTokenizationParams().toXML())
                .addElement("funding", buildFundingParams().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

    @Override
    public List<String> getAllowedRecurringTypes() {
        return Arrays.asList("initial", "managed", "subsequent");
    }


}