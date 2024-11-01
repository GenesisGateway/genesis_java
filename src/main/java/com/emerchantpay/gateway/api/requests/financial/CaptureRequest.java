package com.emerchantpay.gateway.api.requests.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.traveldata.TravelDataAttributes;

public class CaptureRequest extends FinancialRequest implements BusinessParamsAttributes, TravelDataAttributes {

    private String transactionType = TransactionTypes.CAPTURE;

    private String referenceId;

    public CaptureRequest() {
        super();
    }

    public CaptureRequest setReferencialId(String referencialId) {
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

    protected RequestBuilder buildRequest(String root) {

        return new RequestBuilder(root).addElement("transaction_type", transactionType)
                .addElement(buildBaseParams().toXML()).addElement(buildPaymentParams().toXML())
                .addElement("business_attributes", buildBusinessParams().toXML())
                .addElement("reference_id", referenceId)
                .addElement("travel", buildTravelDataParams().toXML());
    }
}
