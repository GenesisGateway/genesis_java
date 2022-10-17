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

import java.util.HashMap;

public interface ThreedsV2CommonAttributes extends CardHolderAccountAttributes,
        ControlAttributes, MerchantRiskAttributes, PurchaseAttributes, RecurringAttributes{

    default RequestBuilder get3DSv2CardHolderAttrRequestBuilder(){
        RequestBuilder cardHolderAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("cardHolderAccount");
        if(cardHolderAttrRequestBuilder == null){
            cardHolderAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("cardHolderAccount", cardHolderAttrRequestBuilder);
        }
        return cardHolderAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2CardHolderAttrParamsMap(){
        HashMap<String, String> cardHolderAttrParamsMap = getThreedsV2AttrParamsMap().get("cardHolderAccount");
        if(cardHolderAttrParamsMap == null){
            cardHolderAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("cardHolderAccount", cardHolderAttrParamsMap);
        }
        return cardHolderAttrParamsMap;
    };

    default RequestBuilder get3DSv2ControlAttrRequestBuilder(){
        RequestBuilder controlAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("control");
        if(controlAttrRequestBuilder == null){
            controlAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("control", controlAttrRequestBuilder);
        }
        return controlAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2ControlAttrParamsMap(){
        HashMap<String, String> controlAttrParamsMap = getThreedsV2AttrParamsMap().get("control");
        if(controlAttrParamsMap == null){
            controlAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("control", controlAttrParamsMap);
        }
        return controlAttrParamsMap;
    };

    default RequestBuilder get3DSv2MerchantRiskAttrRequestBuilder(){
        RequestBuilder merchantRiskAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("merchantRisk");
        if(merchantRiskAttrRequestBuilder == null){
            merchantRiskAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("merchantRisk", merchantRiskAttrRequestBuilder);
        }
        return merchantRiskAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2MerchantRiskAttrParamsMap(){
        HashMap<String, String> merchantRiskAttrParamsMap = getThreedsV2AttrParamsMap().get("merchantRisk");
        if(merchantRiskAttrParamsMap == null){
            merchantRiskAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("merchantRisk", merchantRiskAttrParamsMap);
        }
        return merchantRiskAttrParamsMap;
    };

    default RequestBuilder get3DSv2PurchaseAttrRequestBuilder(){
        RequestBuilder purchaseAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("purchase");
        if(purchaseAttrRequestBuilder == null){
            purchaseAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("purchase", purchaseAttrRequestBuilder);
        }
        return purchaseAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2PurchaseAttrParamsMap(){
        HashMap<String, String> purchaseAttrParamsMap = getThreedsV2AttrParamsMap().get("purchase");
        if(purchaseAttrParamsMap == null){
            purchaseAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("purchase", purchaseAttrParamsMap);
        }
        return purchaseAttrParamsMap;
    };

    default RequestBuilder get3DSv2RecurringAttrRequestBuilder(){
        RequestBuilder recurringAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("recurring");
        if(recurringAttrRequestBuilder == null){
            recurringAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("recurring", recurringAttrRequestBuilder);
        }
        return recurringAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2RecurringAttrParamsMap(){
        HashMap<String, String> recurringAttrParamsMap = getThreedsV2AttrParamsMap().get("recurring");
        if(recurringAttrParamsMap == null){
            recurringAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("recurring", recurringAttrParamsMap);
        }
        return recurringAttrParamsMap;
    };

    default RequestBuilder buildThreedsV2Params() {

        RequestBuilder threedsV2AttrRequestBuilder = new RequestBuilder("");
        threedsV2AttrRequestBuilder.addElement("control", build3DSv2ControlParams().toXML())
                .addElement("purchase", build3DSv2PurchaseParams().toXML())
                .addElement("merchant_risk", build3DSv2MerchantRiskParams().toXML())
                .addElement("card_holder_account", build3DSv2CardHolderParams().toXML())
                .addElement("recurring", build3DSv2RecurringParams().toXML());

        return threedsV2AttrRequestBuilder;
    }

    HashMap<String, RequestBuilder> getThreedsV2RequestBuildersMap();

    HashMap<String, HashMap<String, String>> getThreedsV2AttrParamsMap();
}
