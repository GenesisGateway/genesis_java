package com.emerchantpay.gateway.api.requests.wpf;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.RiskParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.*;
import com.emerchantpay.gateway.api.interfaces.financial.funding.FundingAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.threeds.v2.ThreedsV2CommonAttributes;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.model.klarna.KlarnaItem;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WPFCreateRequest extends Request implements PaymentAttributes, CustomerInfoAttributes,
        DescriptorAttributes, NotificationAttributes, AsyncAttributes, RiskParamsAttributes, PendingPaymentAttributes,
        ThreedsV2CommonAttributes, TokenizationAttributes, RecurringCategoryAttributes, FundingAttributes {

    @Setter
    @Accessors(chain = true)
    private String description;
    private URL cancelUrl;
    private BigDecimal amount;
    private String currency;
    @Setter
    @Accessors(chain = true)
    private Integer lifetime;
    @Setter
    @Accessors(chain = true)
    private String customerGender;
    private BigDecimal orderTaxAmount;
    @Getter
    @Setter
    @Accessors(chain = true)
    private Boolean payLater = false;
    @Getter
    @Setter
    @Accessors(chain = true)
    private Boolean scaPreference;
    @Getter
    @Setter
    @Accessors(chain = true)
    private String webPaymentFormId;

    private final TransactionTypesRequest transactionTypes = new TransactionTypesRequest(this);

    //3DSv2 Attributes
    private HashMap<String, RequestBuilder> threedsV2RequestBuildersMap;
    private HashMap<String, HashMap<String, String>> threedsV2AttrParamsMap;

    // Reminders
    @Getter
    private final RemindersRequest reminders = new RemindersRequest(this);

    // Klarna items
    private KlarnaItemsRequest klarnaItemsRequest;

    // Required params
    private final HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private final GenesisValidator validator = new GenesisValidator();

    public WPFCreateRequest() {
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

    @Override
    public HashMap<String, RequestBuilder> getThreedsV2RequestBuildersMap() {
        if(threedsV2RequestBuildersMap == null){
            threedsV2RequestBuildersMap = new HashMap<String, RequestBuilder>();
        }
        return threedsV2RequestBuildersMap;
    }

    @Override
    public HashMap<String, HashMap<String, String>> getThreedsV2AttrParamsMap() {
        if(threedsV2AttrParamsMap == null){
            threedsV2AttrParamsMap = new HashMap<String, HashMap<String, String>>();
        }
        return threedsV2AttrParamsMap;
    }

    public WPFCreateRequest setReturnCancelUrl(URL cancelUrl) {
        if (validator.isValidUrl("return_cancel_url", String.valueOf(cancelUrl))) {
            this.cancelUrl = cancelUrl;
        }

        return this;
    }

    public WPFCreateRequest setReturnPendingUrl(URL pendingUrl) {
        //Keep method for compatibility but redirect to PendingPaymentAttributes interface method
        setReturnPendingUrl(String.valueOf(pendingUrl));
        return this;
    }

    public TransactionTypesRequest addTransactionType(String transactionType) {
        transactionTypes.addTransaction(transactionType.toLowerCase());
        return transactionTypes;
    }

    public TransactionTypesRequest addTransactionType(String transactionType, ArrayList<HashMap<String, String>> params) {
        transactionTypes.addTransaction(transactionType.toLowerCase());

        for (HashMap<String, String> map : params) {
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                String key = mapEntry.getKey();
                String value = mapEntry.getValue();

                transactionTypes.addParam(key, value);
            }
        }

        return transactionTypes;
    }

    public KlarnaItemsRequest addKlarnaItem(KlarnaItem klarnaItem) {
        return klarnaItemsRequest = new KlarnaItemsRequest(klarnaItem);
    }

    public KlarnaItemsRequest addKlarnaItems(ArrayList<KlarnaItem> klarnaItems) {
        return klarnaItemsRequest = new KlarnaItemsRequest(klarnaItems);
    }

    public RemindersRequest addReminder(String channel, Integer after) {
        return reminders.addReminder(channel, after);
    }

    public TransactionTypesRequest addTransactionTypes(ArrayList<String> transactions) {

        for (String t : transactions) {
            transactionTypes.addTransaction(t.toLowerCase());
        }

        return transactionTypes;
    }

    @Override
    public String getTransactionType() {
        return "wpf_payment";
    }

    @Override
    public String toXML() {
        return buildRequest("wpf_payment").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    @Override
    public Boolean getZeroAmountSupport(){
        return true;
    }

    protected RequestBuilder buildRequest(String root) {

        RequestBuilder requestBuilder = new RequestBuilder(root).addElement(buildBaseParams().toXML())
                .addElement(buildPaymentParams().toXML())
                .addElement(buildCustomerInfoParams().toXML())
                .addElement("description", description)
                .addElement(buildNotificationParams().toXML())
                .addElement(buildAsyncParams().toXML())
                .addElement("return_cancel_url", cancelUrl)
                .addElement(buildPendingPaymentParams().toXML())
                .addElement("lifetime", lifetime)
                .addElement(buildRecurringCategoryParams().toXML())
                .addElement(buildBillingAddress(false).toXML())
                .addElement(buildShippingAddress(false).toXML())
                .addElement("transaction_types", transactionTypes)
                .addElement("risk_params", buildRiskParams().toXML())
                .addElement("dynamic_descriptor_params", buildDescriptorParams().toXML())
                .addElement("pay_later", payLater)
                .addElement("sca_preference", scaPreference)
                .addElement("web_payment_form_id", webPaymentFormId)
                .addElement("threeds_v2_params", buildThreedsV2Params().toXML())
                .addElement(buildTokenizationParams().toXML())
                .addElement("funding", buildFundingParams().toXML());

        // Klarna payment method
        if (klarnaItemsRequest != null
                && transactionTypes.getTransactionTypes().contains(TransactionTypes.KLARNA_AUTHORIZE)) {
            requestBuilder.addElement("customer_gender", customerGender);
            requestBuilder.addElement("order_tax_amount", orderTaxAmount);
            requestBuilder.addElement(klarnaItemsRequest.toXML());

            validator.isValidKlarnaAuthorizeRequest(TransactionTypes.KLARNA_AUTHORIZE, klarnaItemsRequest,
                    amount, orderTaxAmount);
        }

        // Pay Later
        if (Boolean.TRUE.equals(payLater)) {
            requestBuilder.addElement("reminders", reminders);
        }

        // Set required params
        requiredParams.put(RequiredParameters.transactionId, getTransactionId());
        requiredParams.put(RequiredParameters.amount, getAmount().toString());
        requiredParams.put(RequiredParameters.currency, getCurrency());
        requiredParams.put(RequiredParameters.description, description);
        requiredParams.put(RequiredParameters.notificationUrl, getNotificationUrl());
        requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
        requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
        requiredParams.put(RequiredParameters.returnCancelUrl, String.valueOf(cancelUrl));
        requiredParams.put(RequiredParameters.transactionTypes, transactionTypes.toXML());
        if ((getConsumerId() != null && !getConsumerId().isEmpty()) || Boolean.TRUE.equals(getRememberCard())) {
            requiredParams.put(RequiredParameters.customerEmail, getCustomerEmail());
        }

        // Validate request
        validator.isValidRequest(requiredParams);

        return requestBuilder;
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("wpf_payment").getElements();
    }

}
