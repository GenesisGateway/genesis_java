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

public interface SdkAttributes {

    //Static final modifiers are redundant for interface attributes
    //Sdk interface allowed values: native, html, both
    ArrayList<String> ALLOWED_SDK_INTERFACES = new ArrayList<String>(Arrays.asList("native", "html", "both"));

    //UI type allowed values: text, single_select, multi_select, out_of_bag, other_html
    ArrayList<String> ALLOWED_UI_TYPES = new ArrayList<String>(Arrays.asList("text", "single_select", "multi_select",
            "out_of_bag", "other_html"));

    String UI_TYPE_DELIMITER = ", ";

    default SdkAttributes set3dsV2SDKInterface(String sdkInterface) {
        get3DSv2SdkAttrParamsMap().put("interface", sdkInterface);
        get3DSv2SdkAttrRequestBuilder().addElement("interface", sdkInterface);
        return this;
    }

    default String get3dsV2SDKInterface() {
        return get3DSv2SdkAttrParamsMap().get("interface");
    }

    default SdkAttributes set3dsV2SDKUITypes(ArrayList<String> uiTypes) {
        if(uiTypes != null && uiTypes.size() > 0){
            //Values in map keeping params are Strings so we have to convert list
            get3DSv2SdkAttrParamsMap().put("ui_types", String.join(UI_TYPE_DELIMITER, uiTypes));
        }
        return this;
    }

    default ArrayList<String> get3dsV2SDKUITypes() {
        String uiTypesStr = get3DSv2SdkAttrParamsMap().get("ui_types");
        if(uiTypesStr != null && uiTypesStr != ""){
            return new ArrayList<String>(Arrays.asList(uiTypesStr.split(UI_TYPE_DELIMITER)));
        } else{
            return null;
        }
    }

    default SdkAttributes set3dsV2SDKApplicationId(String applicationId) {
        get3DSv2SdkAttrParamsMap().put("application_id", applicationId);
        get3DSv2SdkAttrRequestBuilder().addElement("application_id", applicationId);
        return this;
    }

    default String get3dsV2SDKApplicationId() {
        return get3DSv2SdkAttrParamsMap().get("application_id");
    }

    default SdkAttributes set3dsV2SDKEncryptedData(String encryptedData) {
        get3DSv2SdkAttrParamsMap().put("encrypted_data", encryptedData);
        get3DSv2SdkAttrRequestBuilder().addElement("encrypted_data", encryptedData);
        return this;
    }

    default String get3dsV2SDKEncryptedData() {
        return get3DSv2SdkAttrParamsMap().get("encrypted_data");
    }

    default SdkAttributes set3dsV2SDKEphemeralPublicKeyPair(String ephemeralPublicKeyPair) {
        get3DSv2SdkAttrParamsMap().put("ephemeral_public_key_pair", ephemeralPublicKeyPair);
        get3DSv2SdkAttrRequestBuilder().addElement("ephemeral_public_key_pair", ephemeralPublicKeyPair);
        return this;
    }

    default String get3dsV2SDKEphemeralPublicKeyPair() {
        return get3DSv2SdkAttrParamsMap().get("ephemeral_public_key_pair");
    }

    default SdkAttributes set3dsV2SDKMaxTimeout(Integer maxTimeout) {
        get3DSv2SdkAttrParamsMap().put("max_timeout", String.valueOf(maxTimeout));
        get3DSv2SdkAttrRequestBuilder().addElement("max_timeout", maxTimeout);
        return this;
    }

    default Integer get3dsV2SDKMaxTimeout() {
        String maxTimeoutStr = get3DSv2SdkAttrParamsMap().get("max_timeout");
        if(maxTimeoutStr != null){
            return Integer.parseInt(maxTimeoutStr);
        } else{
            return null;
        }
    }

    default SdkAttributes set3dsV2SDKReferenceNumber(String referenceNumber) {
        get3DSv2SdkAttrParamsMap().put("reference_number", referenceNumber);
        get3DSv2SdkAttrRequestBuilder().addElement("reference_number", referenceNumber);
        return this;
    }

    default String get3dsV2SDKReferenceNumber() {
        return get3DSv2SdkAttrParamsMap().get("reference_number");
    }

    default RequestBuilder build3DSv2SdkParams() {

        if (get3dsV2SDKMaxTimeout() != null && get3dsV2SDKMaxTimeout() < 5) {
            throw new InvalidParamException("Invalid value for 3DS max timeout. Values greater or equal to 5 are accepted");
        }

        if (get3dsV2SDKInterface() != null && !ALLOWED_SDK_INTERFACES.contains(get3dsV2SDKInterface())) {
            throw new InvalidParamException("3DS Sdk interface", ALLOWED_SDK_INTERFACES);
        }

        RequestBuilder uiTypesRequestBuilder = new RequestBuilder("ui_types");
        ArrayList<String> uiTypes = get3dsV2SDKUITypes();
        if(uiTypes != null){
            for (String uiType : uiTypes){
                if (uiType != null && !ALLOWED_UI_TYPES.contains(uiType)) {
                    throw new InvalidParamException("3DS Sdk ui type", ALLOWED_UI_TYPES);
                } else{
                    //If we add named elements to request builder they overwrite each other
                    String uiTypeXML = new RequestBuilder("").addElement("ui_type", uiType).toXML();
                    uiTypesRequestBuilder.addElement(uiTypeXML);
                }
            }
        }
        return get3DSv2SdkAttrRequestBuilder()
                .addElement(uiTypesRequestBuilder.toXML());
    }

    RequestBuilder get3DSv2SdkAttrRequestBuilder();

    HashMap<String, String> get3DSv2SdkAttrParamsMap();
}
