package com.emerchantpay.gateway.api.requests.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.BusinessParamsAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.traveldata.TravelDataAttributes;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;

public class CaptureRequest extends ReferenceRequest implements BusinessParamsAttributes, TravelDataAttributes {

    public CaptureRequest() {
        super();
    }

    /**
     * Sets the reference identifier for this request.
     *
     * @param referencialId the reference identifier to set
     * @return this instance of {@link <ClassName>}
     * @deprecated and scheduled for removal since {@code 1.18.8}, use {@link #setReferenceId(String)} instead.
     */
    @Deprecated
    public CaptureRequest setReferencialId(String referencialId) {
        setReferenceId(referencialId);
        return this;
    }

    @Override
    public String getTransactionType() {
        return TransactionTypes.CAPTURE;
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
    protected HashMap<String, String> getRequiredParams(){
        super.getRequiredParams();
        requiredParams.put(RequiredParameters.currency, getCurrency());
        return requiredParams;
    }

    protected RequestBuilder buildRequest(String root) {
        RequestBuilder builder = super.buildRequest(root);

        return builder.addElement("business_attributes", buildBusinessParams().toXML())
                .addElement("travel", buildTravelDataParams().toXML());
    }
}
