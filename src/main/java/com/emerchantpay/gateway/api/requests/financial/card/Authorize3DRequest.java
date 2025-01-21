package com.emerchantpay.gateway.api.requests.financial.card;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.CreditCardAttributes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.*;
import com.emerchantpay.gateway.api.interfaces.financial.funding.FundingAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.threeds.v2.ThreedsV2Attributes;
import com.emerchantpay.gateway.api.interfaces.financial.traveldata.TravelDataAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

public class Authorize3DRequest extends FinancialRequest implements CreditCardAttributes,
        DescriptorAttributes, CustomerInfoAttributes, NotificationAttributes, AsyncAttributes,
        MpiAttributes, RiskParamsAttributes, FXAttributes, ScaAttributes, BusinessParamsAttributes, CryptoAttributes,
        TravelDataAttributes, ThreedsV2Attributes, PreauthorizationAttributes, TokenizationAttributes,
        RecurringTypeAttributes, RecurringCategoryAttributes, ManagedRecurringAttributes, FundingAttributes {

    private String transactionType = TransactionTypes.AUTHORIZE_3D;

    private Boolean moto;
    private Boolean gaming;
    private Boolean schemeTokenized;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    //3DSv2 Attributes
    private HashMap<String, RequestBuilder> threedsV2RequestBuildersMap;
    private HashMap<String, HashMap<String, String>> threedsV2AttrParamsMap;

    public Authorize3DRequest() {
        super();
    }

    public Authorize3DRequest setMoto(Boolean moto) {
        this.moto = moto;
        return this;
    }

    public Authorize3DRequest setGaming(Boolean gaming) {
        this.gaming = gaming;
        return this;
    }

    public Authorize3DRequest setSchemeTokenized(Boolean schemeTokenized) {
        this.schemeTokenized = schemeTokenized;
        return this;
    }

    @Override
    public Boolean getZeroAmountSupport(){
        return true;
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
    public HashMap<String, RequestBuilder> getThreedsV2RequestBuildersMap() {
        if(threedsV2RequestBuildersMap == null){
            threedsV2RequestBuildersMap = new HashMap<>();
        }
        return threedsV2RequestBuildersMap;
    }

    @Override
    public HashMap<String, HashMap<String, String>> getThreedsV2AttrParamsMap() {
        if(threedsV2AttrParamsMap == null){
            threedsV2AttrParamsMap = new HashMap<>();
        }
        return threedsV2AttrParamsMap;
    }

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.cardHolder, getCardHolder());
        requiredParams.put(RequiredParameters.schemeTokenized, Objects.toString(schemeTokenized, ""));
        requiredParams.putAll(getCreditCardConditionalRequiredParams(getToken()));
        requiredParams.putAll(getTokenizationConditionalRequiredParams(getCustomerEmail(), getCardNumber()));

        // Validate request
        validator.isValidRequest(requiredParams);
        validateManagedRecurring(getRecurringType());

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCreditCardParams().toXML())
                .addElement("gaming", gaming)
                .addElement("moto", moto)
                .addElement(buildCryptoParams().toXML())
                .addElement(buildNotificationParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement(buildRecurringAttrParams().toXML())
                .addElement(buildRecurringCategoryParams().toXML())
                .addElement(buildManagedRecurring().toXML())
                .addElement(buildBillingAddress(false).toXML())
                .addElement(buildShippingAddress(false).toXML())
                .addElement("dynamic_descriptor_params", buildDescriptorParams().toXML())
                .addElement("mpi_params", buildMpiParams().toXML())
                .addElement("risk_params", buildRiskParams().toXML())
                .addElement("sca_params", buildScaParams().toXML())
                .addElement("scheme_tokenized", schemeTokenized)
                .addElement("business_attributes", buildBusinessParams().toXML())
                .addElement(buildFXParams().toXML())
                .addElement("travel", buildTravelDataParams().toXML())
                .addElement("threeds_v2_params", buildThreedsV2Params().toXML())
                .addElement(buildPreauthorizationParams().toXML())
                .addElement(buildTokenizationParams().toXML())
                .addElement("funding", buildFundingParams().toXML());
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
