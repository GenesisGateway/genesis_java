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

import java.util.HashMap;

public interface SDDAttributes {

    // SDD Params
    default SDDAttributes setIban(String iban) {
        getSDDAttrParamsMap().put("iban", iban);
        getSDDAttrRequestBuilder().addElement("iban", iban);
        return this;
    }

    default String getIban() {
        return getSDDAttrParamsMap().get("iban");
    }

    default SDDAttributes setBic(String bic) {
        getSDDAttrParamsMap().put("bic", bic);
        getSDDAttrRequestBuilder().addElement("bic", bic);
        return this;
    }

    default String getBic() {
        return getSDDAttrParamsMap().get("bic");
    }

    default RequestBuilder buildSDDParams() {
        return getSDDAttrRequestBuilder();
    }

    RequestBuilder getSDDAttrRequestBuilder();

    HashMap<String, String> getSDDAttrParamsMap();
}
