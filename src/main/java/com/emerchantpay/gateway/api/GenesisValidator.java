package com.emerchantpay.gateway.api;

import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.model.klarna.KlarnaItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class GenesisValidator {

    private Boolean isValid;

    private ArrayList<String> missingParamsList = new ArrayList<String>();


    public Boolean validateRequiredParams(HashMap<String, String> requiredParams) {
        // Missing params
        StringBuilder missingParams = new StringBuilder();

        for (String key : requiredParams.keySet()) {
            if ((requiredParams.get(key) == null || requiredParams.get(key).isEmpty()
                    || requiredParams.get(key).equals("null")) && !missingParamsList.contains(key)) {
                missingParamsList.add(key);
            }
        }

        for (Integer i = 0; i < missingParamsList.size(); i++) {
            if (i == 0) {
                missingParams.append(missingParamsList.get(0));
            } else {
                missingParams.append(", " + missingParamsList.get(i));
            }
        }

        if (!missingParamsList.isEmpty()) {
            if (missingParamsList.size() > 1) {
                throw new GenesisException(missingParams.toString() + " Required params are missing");
            } else {
                throw new GenesisException(missingParams.toString() + "Required param is missing");
            }
        } else {
            return true;
        }
    }

    public Boolean isValidRequest(String transactionType, Request request, BigDecimal transactionAmount, BigDecimal orderTaxAmount) {
        KlarnaItemsRequest klarnaRequest = (KlarnaItemsRequest) request;

        switch (transactionType) {
            case "klarna_authorize":
                if (klarnaRequest != null) {
                    for (KlarnaItem item : klarnaRequest.getItems()) {
                        isValid = validateRequiredParams(item.getRequiredParams());
                    }

                    // Validate amounts
                    validateTransactionAmount(klarnaRequest, transactionAmount);
                    validateOrderAmount(klarnaRequest, orderTaxAmount);
                } else {
                    isValid = false;
                    throw new GenesisException("Empty (null) required parameter: items");
                }

                if (!isValid) {
                    return false;
                } else {
                    return true;
                }
            default:
                return isValid;
        }
    }

    public Boolean isValidRequest(String transactionType, Request request, BigDecimal transactionAmount) {
        KlarnaItemsRequest klarnaRequest = (KlarnaItemsRequest) request;

        switch (transactionType) {
            case "klarna_capture":
                if (klarnaRequest != null) {
                    for (KlarnaItem item : klarnaRequest.getItems()) {
                        isValid = validateRequiredParams(item.getRequiredParams());
                    }

                    // Validate amounts
                    validateTransactionAmount(klarnaRequest, transactionAmount);
                } else {
                    isValid = false;
                    throw new GenesisException("Empty (null) required parameter: items");
                }

                if (!isValid) {
                    return false;
                } else {
                    return true;
                }
            default:
                return isValid;
        }
    }

    protected void validateTransactionAmount(KlarnaItemsRequest klarnaRequest, BigDecimal transactionAmount) {
        // Transaction amount
        if (transactionAmount != null
                && transactionAmount.doubleValue() > 0
                && klarnaRequest.getTotalAmounts().doubleValue() > 0
                && transactionAmount.doubleValue() == klarnaRequest.getTotalAmounts().doubleValue()) {
            isValid = true;
        } else {
            isValid = false;
            throw new GenesisException("Please populate valid total amounts");
        }
    }

    protected void validateOrderAmount(KlarnaItemsRequest klarnaRequest, BigDecimal orderTaxAmount) {
        if (orderTaxAmount != null) {
            if (orderTaxAmount.doubleValue() > 0
                    && orderTaxAmount.doubleValue() == klarnaRequest.getTotalTaxAmounts().doubleValue()) {
                isValid = true;
            } else {
                isValid = false;
                throw new GenesisException("Please populate valid total tax amounts");
            }
        }
    }

    public Boolean isValidData() {
        return isValid;
    }
}
