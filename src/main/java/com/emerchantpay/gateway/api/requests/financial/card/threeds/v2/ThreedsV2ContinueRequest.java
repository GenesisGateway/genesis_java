package com.emerchantpay.gateway.api.requests.financial.card.threeds.v2;

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

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.ContentTypes;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class ThreedsV2ContinueRequest extends Request {

    private String threedsMethodContinueUrl;
    private String uniqueId;
    private String threedsSignature;
    private BigDecimal amount;
    private String currency;
    private Instant threedsTimestamp;
    private Configuration configuration;
    private Http http;
    private NodeWrapper node;
    private static final String PATH_SEPARATOR = "/";

    public ThreedsV2ContinueRequest(){
        super();
    }

    public ThreedsV2ContinueRequest(Transaction trx, Configuration configuration) {
        if(trx.getUniqueId() == null || trx.getAmount() == null || trx.getCurrency() == null ||
                trx.getTimestamp() == null || configuration == null){

            throw new InvalidParamException("Response object is not complete or properties are missing.");
        }
        setUniqueId(trx.getUniqueId());
        setAmount(trx.getAmount());
        setCurrency(trx.getCurrency());
        setThreedsTimestamp(trx.getTimestamp());
        configuration.setAction(Endpoints.THREEDS_METHOD.getEndpointName() + PATH_SEPARATOR + getUniqueId());
        setThreedsConfiguration(configuration);
        setThreedsSignature(generateSignature());
    }

    public Configuration getThreedsConfiguration() {
        return configuration;
    }

    public ThreedsV2ContinueRequest setThreedsConfiguration(Configuration configuration) {
        this.configuration = configuration;
        //3DS v2 Continue request doesn't need token
        configuration.setTokenEnabled(false);
        configuration.setAction(Endpoints.THREEDS_METHOD.getEndpointName() + PATH_SEPARATOR + getUniqueId());
        return this;
    }

    public String getThreedsMethodContinueUrl() {
        if(threedsMethodContinueUrl == null){
            return generateEndpointUrl();
        } else {
            return threedsMethodContinueUrl;
        }
    }

    public ThreedsV2ContinueRequest setThreedsMethodContinueUrl(String threedsMethodContinueUrl) {
        if (getValidator().isValidUrl("threeds_method_continue_url", threedsMethodContinueUrl)) {
            this.threedsMethodContinueUrl = threedsMethodContinueUrl;
        }
        return this;
    }

    public String getUniqueId() {
        if(uniqueId == null){
            return extractUniqueIdFromUrl(getThreedsMethodContinueUrl());
        } else {
            return uniqueId;
        }
    }

    public ThreedsV2ContinueRequest setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public String getThreedsSignature() {
        if(threedsSignature == null){
            return generateSignature();
        } else {
            return threedsSignature;
        }
    }

    public ThreedsV2ContinueRequest setThreedsSignature(String threedsSignature) {
        this.threedsSignature = threedsSignature;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ThreedsV2ContinueRequest setAmount(BigDecimal amount) {

        this.amount = amount;
        return this;
    }

    public ThreedsV2ContinueRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public String getThreedsTimestamp() {
        if(threedsTimestamp != null){
            DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.from(ZoneOffset.UTC));
            return isoFormatter.format(threedsTimestamp);
        } else {
            return null;
        }
    }

    public ThreedsV2ContinueRequest setThreedsTimestamp(String threedsTimestamp) {
        DateTimeFormatter isoFormatter =  DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.from(ZoneOffset.UTC));
        try{
            this.threedsTimestamp = Instant.from(isoFormatter.parse(threedsTimestamp));
        } catch (DateTimeParseException e) {
            getValidator().getInvalidParams().add(RequiredParameters.threedsTimestamp);
        }

        return this;
    }

    @Override
    public String toXML() {

        return buildRequest("").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        if(getUniqueId() == null && getThreedsMethodContinueUrl() == null){
            throw new RequiredParamsException("At least one of " + RequiredParameters.threedsMethodContinueUrl +
                    " or " + RequiredParameters.threedsUniqueId + " must be provided.");
        }

        getValidator().isValidAmount(amount);

        ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
        if (!invalidParams.isEmpty()) {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }

        HashMap<String, String> requiredParams = new HashMap<String, String>();
        // Set required params
        requiredParams.put(RequiredParameters.threedsAmount, getAmount().toString());
        requiredParams.put(RequiredParameters.threedsTimestamp, getThreedsTimestamp());
        getValidator().isValidRequest(requiredParams);

        configuration.addQueryParameter("signature", getThreedsSignature());
        return new RequestBuilder(root);
    }

    public NodeWrapper execute() {
        //Request doesn't have a body but we call buildRequest to verify params are valid
        buildRequest("");
        Http http = new Http(configuration);
        node = http.put(configuration.getBaseUrl(), ContentTypes.APPLICATION_FORM_URLENCODE);
        return node;
    }

    public NodeWrapper getResponse() {
        return node;
    }

    public Transaction getTransactionResponse(){
        if(node != null){
            return new Transaction(node);
        } else {
            return null;
        }

    }

    //SHA512 of а concatenated string (unique_id, amount, timestamp, merchant_api_password)
    private String generateSignature() {
        String signature = null;
        BigDecimal convertedAmount = null;
        if (amount != null && currency != null) {
            Currency curr = new Currency();
            curr.setAmountToExponent(amount, currency);
            convertedAmount = curr.getAmount();
        }
        try {
            signature = SHA512Hasher.SHA512(getUniqueId() + convertedAmount + getThreedsTimestamp() + configuration.getPassword());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return signature;
    }

    private String generateEndpointUrl(){
        if(uniqueId != null && configuration != null){
            return configuration.getBaseUrl();
        }
        return null;
    }

    //Unique Id is last part of URL
    private String extractUniqueIdFromUrl(String url){
        if(url != null){
            String[] urlTokens = url.split(PATH_SEPARATOR);
            if(urlTokens.length > 0){
                return urlTokens[urlTokens.length - 1];
            }
        }
        return null;
    }
}