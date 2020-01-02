package com.emerchantpay.gateway.api.requests.nonfinancial.fx;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.FXEndpoints;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.model.fx.Rate;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Http;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;

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

public class FXSearchRateRequest extends Request {

    protected Configuration configuration;
    private Http http;

    private JsonNode node;

    private String tierId;
    private String sourceCurrency;
    private String targetCurrency;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public FXSearchRateRequest() {
        super();
    }

    public FXSearchRateRequest(Configuration configuration) {
        super();
        this.configuration = configuration;
    }

    public FXSearchRateRequest setTierId(String tierId) {
        this.tierId = tierId;
        return this;
    }

    public FXSearchRateRequest setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
        return this;
    }

    public FXSearchRateRequest setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
        return this;
    }

    protected RequestBuilder buildRequest(String root) {
        // Set required params
        requiredParams.put(RequiredParameters.tierId, tierId);
        requiredParams.put(RequiredParameters.sourceCurrency, sourceCurrency);
        requiredParams.put(RequiredParameters.targetCurrency, targetCurrency);

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("source_currency", sourceCurrency)
                .addElement("target_currency", targetCurrency);
    }

    public JsonNode execute() {
        configuration.setTokenEnabled(false);
        configuration.setAction(tierId + FXEndpoints.FX_SEARCH_RATE);
        http = new Http(configuration);
        node = http.postJson(configuration.getBaseUrl(), this);

        return node;
    }

    @Override
    public String toJSON() {
        return buildRequest("search_rate").toJSON();
    }

    public Rate getRate() {
        return new Rate(node);
    }
}