package com.emerchantpay.gateway.api.requests.nonfinancial.fx;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.model.fx.Tier;
import com.emerchantpay.gateway.model.fx.Tiers;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Http;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

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

public class FXGetTiersRequest extends Request {

    protected Configuration configuration;
    private Http http;

    private JsonNode node;

    public FXGetTiersRequest() {
        super();
    }

    public FXGetTiersRequest(Configuration configuration) {
        super();
        this.configuration = configuration;
    }

    public JsonNode execute() {
        configuration.setTokenEnabled(false);
        configuration.setAction("");
        http = new Http(configuration);
        node = http.getJson(configuration.getBaseUrl());

        return node;
    }

    public List<Tier> getTires() {
        return new Tiers(node).getTiersList();
    }
}