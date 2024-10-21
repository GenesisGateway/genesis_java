package com.emerchantpay.gateway.api.interfaces.financial;

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

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ErrorMessages;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface TokenizationAttributes {

    String rememberCardParamName = "remember_card";
    String consumerIdParamName = "consumer_id";
    String tokenParamName = "token";

    default TokenizationAttributes setConsumerId(String consumerId) {
        getTokenizationAttrParamsMap().put(consumerIdParamName, consumerId);
        getTokenizationAttrRequestBuilder().addElement(consumerIdParamName, consumerId);
        return this;
    }

    default String getConsumerId() {
        return getTokenizationAttrParamsMap().get(consumerIdParamName);
    }

    default TokenizationAttributes setToken(String token) {
        getTokenizationAttrParamsMap().put(tokenParamName, token);
        getTokenizationAttrRequestBuilder().addElement(tokenParamName, token);
        return this;
    }

    default String getToken() {
        return getTokenizationAttrParamsMap().get(tokenParamName);
    }

    default TokenizationAttributes setRememberCard(Boolean rememberCard) {
        getTokenizationAttrParamsMap().put(rememberCardParamName, String.valueOf(rememberCard));
        getTokenizationAttrRequestBuilder().addElement(rememberCardParamName, rememberCard);
        return this;
    }

    default Boolean getRememberCard() {
        String rememberCardStr = getTokenizationAttrParamsMap().get(rememberCardParamName);
        if(rememberCardStr != null){
            return Boolean.parseBoolean(rememberCardStr);
        } else{
            return false;
        }
    }

    default RequestBuilder buildTokenizationParams() {
        String consumerId = getConsumerId();
        if (consumerId != null && !consumerId.isEmpty()) {
            getValidator().isValidConsumerId(consumerId);
        }
        return getTokenizationAttrRequestBuilder();
    }

    default HashMap<String, String> getTokenizationConditionalRequiredParams(String customerEmail, String cardNumber,
                                                                             boolean isSubsequentRec){
        HashMap<String, String> requiredParams = new HashMap<String, String>();
        if(getToken() != null && !getToken().isEmpty()){
            requiredParams.put(RequiredParameters.customerEmail, customerEmail);
            requiredParams.put(RequiredParameters.consumerId, getConsumerId());
        }
        if(getRememberCard()){
            requiredParams.put(RequiredParameters.cardNumber, cardNumber);
            requiredParams.put(RequiredParameters.customerEmail, customerEmail);
        }
        if(getConsumerId() != null && !getConsumerId().isEmpty()){
            requiredParams.put(RequiredParameters.customerEmail, customerEmail);
        }

        List<String> tokenAndCardNumberParams = Arrays.asList(new String[]{RequiredParameters.cardNumber, RequiredParameters.token});
        if((cardNumber == null || cardNumber.isEmpty()) && (getToken() == null || getToken().isEmpty()) && !isSubsequentRec){
            throw new RequiredParamsException(ErrorMessages.AT_LEAST_ONE_PARAMETER_REQUIRED + tokenAndCardNumberParams);
        }

        if(getToken() != null && !getToken().isEmpty() && cardNumber != null && !cardNumber.isEmpty()){
            throw new RequiredParamsException(ErrorMessages.ONLY_ONE_PARAMETER_ALLOWED + tokenAndCardNumberParams);
        }
        return requiredParams;
    }

    default HashMap<String, String> getTokenizationConditionalRequiredParams(String customerEmail, String cardNumber){
        return getTokenizationConditionalRequiredParams(customerEmail, cardNumber, false);
    }

    RequestBuilder getTokenizationAttrRequestBuilder();

    HashMap<String, String> getTokenizationAttrParamsMap();

    GenesisValidator getValidator();
}
