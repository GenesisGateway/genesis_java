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

    RequestBuilder requestBuilder = new RequestBuilder("");
    HashMap<String, String> paramsMap = new HashMap<String, String>();

    // Base Params
    default BaseAttributes setTransactionId(String transactionId) {
        paramsMap.put("transaction_id", transactionId);
        requestBuilder.addElement("transaction_id", transactionId);
        return this;
    }

    default String getTransactionId() {
        return paramsMap.get("transaction_id");
    }

    default BaseAttributes setUsage(String usage) {
        paramsMap.put("usage", usage);
        requestBuilder.addElement("usage", usage);
        return this;
    }

    default String getUsage() {
        return paramsMap.get("usage");
    }

    default BaseAttributes setRemoteIp(String remoteIP) {
        paramsMap.put("remote_ip", remoteIP);
        requestBuilder.addElement("remote_ip", remoteIP);
        return this;
    }

    default String getRemoteIp() {
        return paramsMap.get("remote_ip");
    }


    default RequestBuilder buildBaseParams() {
        return requestBuilder;
    }
}