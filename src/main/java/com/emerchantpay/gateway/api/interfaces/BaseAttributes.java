package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;

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

public interface BaseAttributes {

    // Base Params
    default BaseAttributes setTransactionId(String transactionId) {
        getBaseAttrParamsMap().put("transaction_id", transactionId);
        getBaseAttrRequestBuilder().addElement("transaction_id", transactionId);
        return this;
    }

    default String getTransactionId() {
        return getBaseAttrParamsMap().get("transaction_id");
    }

    default BaseAttributes setUsage(String usage) {
        getBaseAttrParamsMap().put("usage", usage);
        getBaseAttrRequestBuilder().addElement("usage", usage);
        return this;
    }

    default String getUsage() {
        return getBaseAttrParamsMap().get("usage");
    }

    default BaseAttributes setRemoteIp(String remoteIP) {
        getBaseAttrParamsMap().put("remote_ip", remoteIP);
        getBaseAttrRequestBuilder().addElement("remote_ip", remoteIP);
        return this;
    }

    default String getRemoteIp() {
        return getBaseAttrParamsMap().get("remote_ip");
    }


    default RequestBuilder buildBaseParams() {
        return getBaseAttrRequestBuilder();
    }

    RequestBuilder getBaseAttrRequestBuilder();

    HashMap<String, String> getBaseAttrParamsMap();
}