package com.emerchantpay.gateway.api.validation;

import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;

import java.util.ArrayList;
import java.util.HashMap;

public class RequiredParametersValidator extends GenesisValidator {

    private HashMap<String, String> parametersMap;
    private ArrayList<String> missingParamsList = new ArrayList<String>();

    public RequiredParametersValidator() {
        super();
    }

    public RequiredParametersValidator(HashMap<String, String> parametersMap) {
        super();

        this.parametersMap = parametersMap;
    }

    public Boolean isValidRequiredParams() {
        // Missing params
        StringBuilder missingParams = new StringBuilder();

        for (String key : parametersMap.keySet()) {
            if (parametersMap.get(key) == null || parametersMap.get(key).isEmpty()) {
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

        if (missingParamsList.isEmpty()) {
            return true;
        } else {
            throw new RequiredParamsException("Required params is missing: " + missingParams.toString());
        }
    }

    public HashMap<String, String> setParameters(HashMap<String, String> parameters) {
        this.parametersMap = parameters;
        return this.parametersMap;
    }
}
