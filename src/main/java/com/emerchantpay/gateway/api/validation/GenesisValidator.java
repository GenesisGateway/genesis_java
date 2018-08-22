package com.emerchantpay.gateway.api.validation;

import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.model.klarna.KlarnaItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class GenesisValidator extends RegexValidator {

    private ArrayList<String> notValidParamsList = new ArrayList<String>();
    private ArrayList<String> missingParamsList = new ArrayList<String>();

    public GenesisValidator() {
        super();
    }

    public Boolean isValidRequiredParams(HashMap<String, String> requiredParams) {
        // Missing params
        for (String key : requiredParams.keySet()) {
            if ((requiredParams.get(key) == null || requiredParams.get(key).isEmpty())
                    && !missingParamsList.contains(key)) {
                missingParamsList.add(key);
            }
        }

        if (!missingParamsList.isEmpty()) {
            throw new RequiredParamsException(String.join(", ", missingParamsList) + " Required param(s) are missing");
        } else {
            return true;
        }
    }

    // Klarna requests validation
    protected Boolean validateTransactionAmount(KlarnaItemsRequest klarnaRequest, BigDecimal transactionAmount) {
        // Transaction amount
        if (transactionAmount != null
                && transactionAmount.doubleValue() > 0
                && klarnaRequest.getTotalAmounts().doubleValue() > 0
                && transactionAmount.doubleValue() == klarnaRequest.getTotalAmounts().doubleValue()) {
            return true;
        } else {
            throw new GenesisException("Please populate valid total amounts");
        }
    }

    public Boolean isValidRequest(HashMap<String, String> requiredParams) {
        if (isValidRequiredParams(requiredParams)) {
            return true;
        } else {
            return false;
        }
    }

    protected Boolean validateOrderTaxAmount(KlarnaItemsRequest klarnaRequest, BigDecimal orderTaxAmount) {
        // Order tax amount
        if (orderTaxAmount != null && orderTaxAmount.doubleValue() > 0
                && orderTaxAmount.doubleValue() == klarnaRequest.getTotalTaxAmounts().doubleValue()) {
            return true;
        } else {
            throw new GenesisException("Please populate valid total tax amounts");
        }
    }

    public Boolean isValidKlarnaAuthorizeRequest(String transactionType, KlarnaItemsRequest klarnaRequest,
                                                 BigDecimal transactionAmount, BigDecimal orderTaxAmount) {
        switch (transactionType) {
            case "klarna_authorize":
                if (klarnaRequest != null) {
                    for (KlarnaItem item : klarnaRequest.getItems()) {
                        return isValidRequiredParams(item.getRequiredParams());
                    }

                    // Validate amounts
                    validateTransactionAmount(klarnaRequest, transactionAmount);
                    validateOrderTaxAmount(klarnaRequest, orderTaxAmount);
                } else {
                    throw new GenesisException("Empty (null) required parameter: items");
                }
            default:
                return false;
        }
    }

    public Boolean isValidKlarnaCaptureRequest(String transactionType, KlarnaItemsRequest klarnaRequest,
                                               BigDecimal transactionAmount) {
        switch (transactionType) {
            case "klarna_capture":
                if (klarnaRequest != null) {
                    for (KlarnaItem item : klarnaRequest.getItems()) {
                        return isValidRequiredParams(item.getRequiredParams());
                    }

                    // Validate amounts
                    validateTransactionAmount(klarnaRequest, transactionAmount);
                } else {
                    throw new GenesisException("Empty (null) required parameter: items");
                }
            default:
                return false;
        }
    }
}