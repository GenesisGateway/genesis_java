package com.emerchantpay.gateway.api.interfaces.financial.funding;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.HashMap;

public interface SenderAttributes {

    default SenderAttributes setSenderName(String name) {
        getSenderAttributeParamsMap().put("name", name);
        getSenderAttrributeRequestBuilder().addElement("name", name);
        return this;
    }

    default SenderAttributes setSenderReferenceNumber(String referenceNumber) {
        getSenderAttributeParamsMap().put("reference_number", referenceNumber);
        getSenderAttrributeRequestBuilder().addElement("reference_number", referenceNumber);
        return this;
    }

    default SenderAttributes setSenderCountry(String country) {
        getSenderAttributeParamsMap().put("country", country);
        getSenderAttrributeRequestBuilder().addElement("country", country);
        return this;
    }

    default SenderAttributes setFundingSenderAddress(String address) {
        getSenderAttributeParamsMap().put("address", address);
        getSenderAttrributeRequestBuilder().addElement("address", address);
        return this;
    }

    default SenderAttributes setSenderState(String state) {
        getSenderAttributeParamsMap().put("state", state);
        getSenderAttrributeRequestBuilder().addElement("state", state);
        return this;
    }

    default SenderAttributes setSenderCity(String city) {
        getSenderAttributeParamsMap().put("city", city);
        getSenderAttrributeRequestBuilder().addElement("city", city);
        return this;
    }

    default RequestBuilder buildSenderParams() {
        if (getSenderCountry() != null && getSenderCountry().length() > 3) {
            throw new InvalidParamException("Invalid Sender country code " + getSenderCountry() + ". Allowed country codes are: with max 3 chars.");
        }

        return getSenderAttrributeRequestBuilder();
    }


    default String getSenderName() {
        return getSenderAttributeParamsMap().get("name");
    }

    default String getSenderReferenceNumber() {
        return getSenderAttributeParamsMap().get("reference_number");
    }

    default String getSenderCountry() {
        return getSenderAttributeParamsMap().get("country");
    }

    default String getFundingSenderAddress() {
        return getSenderAttributeParamsMap().get("address");
    }

    default String getSenderState() {
        return getSenderAttributeParamsMap().get("state");
    }

    default String getSenderCity() {
        return getSenderAttributeParamsMap().get("city");
    }

    RequestBuilder getSenderAttrributeRequestBuilder();
    HashMap<String, String> getSenderAttributeParamsMap();
    GenesisValidator getValidator();
}
