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

public interface ThreedsV2Attributes extends ThreedsV2CommonAttributes, MethodAttributes, BrowserAttributes, SdkAttributes{

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

        RequestBuilder threedsV2AttrRequestBuilder = ThreedsV2CommonAttributes.super.buildThreedsV2Params();
        threedsV2AttrRequestBuilder.addElement("threeds_method", build3DSv2MethodParams().toXML())
                .addElement("browser", build3DSv2BrowserParams().toXML())
                .addElement("sdk", build3DSv2SdkParams().toXML());

        return threedsV2AttrRequestBuilder;
    }
}