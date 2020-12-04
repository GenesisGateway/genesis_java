package com.emerchantpay.gateway.api.interfaces.financial.threeds.v2;

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
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public interface PurchaseAttributes {

    //Static final modifiers are redundant for interface attributes
    //Category allowed values: goods, service, check_acceptance, account_funding, quasi_cash, prepaid_activation, loan
    ArrayList<String> ALLOWED_CATEGORIES = new ArrayList<String>(Arrays.asList("goods", "service", "check_acceptance", "account_funding",
            "quasi_cash", "prepaid_activation", "loan"));

    default PurchaseAttributes set3dsV2PurchaseCategory(String category) {
        get3DSv2PurchaseAttrParamsMap().put("category", category);
        get3DSv2PurchaseAttrRequestBuilder().addElement("category", category);
        return this;
    }

    default String get3dsV2PurchaseCategory() {
        return get3DSv2PurchaseAttrParamsMap().get("category");
    }

    default RequestBuilder build3DSv2PurchaseParams() {
        if (get3dsV2PurchaseCategory() != null && !ALLOWED_CATEGORIES.contains(get3dsV2PurchaseCategory())) {
            throw new InvalidParamException("3DS purchase category", ALLOWED_CATEGORIES);
        }
        return get3DSv2PurchaseAttrRequestBuilder();
    }

    RequestBuilder get3DSv2PurchaseAttrRequestBuilder();

    HashMap<String, String> get3DSv2PurchaseAttrParamsMap();
}