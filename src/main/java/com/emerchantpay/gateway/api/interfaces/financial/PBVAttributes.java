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

public interface PBVAttributes {

    // PBV Params
    default PBVAttributes setCardType(String cardType) {
        getPBVAttrParamsMap().put("card_type", cardType);
        getPBVAttrRequestBuilder().addElement("card_type", cardType);
        return this;
    }

    default String getCardType() {
        return getPBVAttrParamsMap().get("card_type");
    }

    default PBVAttributes setRedeemType(String redeemType) {
        getPBVAttrParamsMap().put("redeem_type", redeemType);
        getPBVAttrRequestBuilder().addElement("redeem_type", redeemType);
        return this;
    }

    default String getRedeemType() {
        return getPBVAttrParamsMap().get("redeem_type");
    }

    default RequestBuilder buildPBVParams() {
        return getPBVAttrRequestBuilder();
    }

    RequestBuilder getPBVAttrRequestBuilder();

    HashMap<String, String> getPBVAttrParamsMap();
}
