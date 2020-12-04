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
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;

public interface ThreedsV2Attributes extends MethodAttributes, BrowserAttributes, CardHolderAccountAttributes,
        ControlAttributes, MerchantRiskAttributes, PurchaseAttributes, RecurringAttributes, SdkAttributes{

    default RequestBuilder get3DSv2MethodAttrRequestBuilder(){
        RequestBuilder methodAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("method");
        if(methodAttrRequestBuilder == null){
            methodAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("method", methodAttrRequestBuilder);
        }
        return methodAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2MethodAttrParamsMap(){
        HashMap<String, String> methodAttrParamsMap = getThreedsV2AttrParamsMap().get("method");
        if(methodAttrParamsMap == null){
            methodAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("method", methodAttrParamsMap);
        }
        return methodAttrParamsMap;
    };

    default RequestBuilder get3DSv2BrowserAttrRequestBuilder(){
        RequestBuilder browserAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("browser");
        if(browserAttrRequestBuilder == null){
            browserAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("browser", browserAttrRequestBuilder);
        }
        return browserAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2BrowserAttrParamsMap(){
        HashMap<String, String> browserAttrParamsMap = getThreedsV2AttrParamsMap().get("browser");
        if(browserAttrParamsMap == null){
            browserAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("browser", browserAttrParamsMap);
        }
        return browserAttrParamsMap;
    };

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

    default RequestBuilder get3DSv2SdkAttrRequestBuilder(){
        RequestBuilder sdkAttrRequestBuilder = getThreedsV2RequestBuildersMap().get("sdk");
        if(sdkAttrRequestBuilder == null){
            sdkAttrRequestBuilder = new RequestBuilder("");
            getThreedsV2RequestBuildersMap().put("sdk", sdkAttrRequestBuilder);
        }
        return sdkAttrRequestBuilder;
    };

    default HashMap<String, String> get3DSv2SdkAttrParamsMap(){
        HashMap<String, String> sdkAttrParamsMap = getThreedsV2AttrParamsMap().get("sdk");
        if(sdkAttrParamsMap == null){
            sdkAttrParamsMap = new HashMap<String, String>();
            getThreedsV2AttrParamsMap().put("sdk", sdkAttrParamsMap);
        }
        return sdkAttrParamsMap;
    };

    default RequestBuilder buildThreedsV2Params() {

        HashMap<String, String> requiredParams = new HashMap<String, String>();
        if(get3dsV2ControlDeviceType() == "browser"){
            //All browser attributes are required
            requiredParams.put(RequiredParameters.acceptHeader, get3dsV2BrowserAcceptHeader());
            requiredParams.put(RequiredParameters.javaEnabled, String.valueOf(get3dsV2BrowserJavaEnabled()));
            requiredParams.put(RequiredParameters.language, get3dsV2BrowserLanguage());
            requiredParams.put(RequiredParameters.colorDepth, String.valueOf(get3dsV2BrowserColorDepth()));
            requiredParams.put(RequiredParameters.screenHeight, String.valueOf(get3dsV2BrowserScreenHeight()));
            requiredParams.put(RequiredParameters.screenWidth, String.valueOf(get3dsV2BrowserScreenWidth()));
            requiredParams.put(RequiredParameters.timeZoneOffset, get3dsV2BrowserTimeZoneOffset());
            requiredParams.put(RequiredParameters.userAgent, get3dsV2BrowserUserAgent());
        } else if(get3dsV2ControlDeviceType() == "application"){
            //All SDK attributes are required
            requiredParams.put(RequiredParameters.threedsV2interface, get3dsV2SDKInterface());
            String uiTypes = null;
            if(get3dsV2SDKUITypes() != null){
                //If array is null String.valueOf would return "null" which will be considered as a value
                uiTypes = String.valueOf(get3dsV2SDKUITypes());
            }
            requiredParams.put(RequiredParameters.uiType, uiTypes);
            requiredParams.put(RequiredParameters.applicationId, get3dsV2SDKApplicationId());
            requiredParams.put(RequiredParameters.encryptedData, get3dsV2SDKEncryptedData());
            requiredParams.put(RequiredParameters.ephemeralPublicKeyPair, get3dsV2SDKEphemeralPublicKeyPair());
            requiredParams.put(RequiredParameters.maxTimeout, String.valueOf(get3dsV2SDKMaxTimeout()));
            requiredParams.put(RequiredParameters.referenceNumber, get3dsV2SDKReferenceNumber());
        }
        // Validate request
        getValidator().isValidRequest(requiredParams);

        RequestBuilder threedsV2AttrRequestBuilder = new RequestBuilder("");
        threedsV2AttrRequestBuilder.addElement("threeds_method", build3DSv2MethodParams())
                .addElement("control", build3DSv2ControlParams().toXML())
                .addElement("purchase", build3DSv2PurchaseParams().toXML())
                .addElement("merchant_risk", build3DSv2MerchantRiskParams().toXML())
                .addElement("card_holder_account", build3DSv2CardHolderParams().toXML())
                .addElement("browser", build3DSv2BrowserParams().toXML())
                .addElement("sdk", build3DSv2SdkParams().toXML())
                .addElement("recurring", build3DSv2RecurringParams().toXML());

        return threedsV2AttrRequestBuilder;
    }

    HashMap<String, RequestBuilder> getThreedsV2RequestBuildersMap();

    HashMap<String, HashMap<String, String>> getThreedsV2AttrParamsMap();
}