package com.emerchantpay.gateway.api.validation;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.exceptions.GenesisException;
import com.emerchantpay.gateway.api.requests.financial.apm.KlarnaItemsRequest;
import com.emerchantpay.gateway.model.klarna.KlarnaItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class GenesisValidator extends RegexValidator {

    private ArrayList<String> emptyParamsList = new ArrayList<String>();
    private ArrayList<String> missingParamsList = new ArrayList<String>();

    public GenesisValidator() {
        super();
    }

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
                        return validateRequiredParams(item.getRequiredParams());
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
                        return validateRequiredParams(item.getRequiredParams());
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