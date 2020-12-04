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

public interface BrowserAttributes {

    //Static final modifiers are redundant for interface attributes
    //Color depth allowed values: 1, 4, 8, 15, 16, 24, 32, 48
    ArrayList<String> ALLOWED_COLOR_DEPTH_VALUES = new ArrayList<String>(Arrays.asList("1", "4", "8", "15", "16", "24",
            "32", "48"));

    default BrowserAttributes set3dsV2BrowserAcceptHeader(String acceptHeader) {
        get3DSv2BrowserAttrParamsMap().put("accept_header", acceptHeader);
        get3DSv2BrowserAttrRequestBuilder().addElement("accept_header", acceptHeader);
        return this;
    }

    default String get3dsV2BrowserAcceptHeader() {
        return get3DSv2BrowserAttrParamsMap().get("accept_header");
    }

    default BrowserAttributes set3dsV2BrowserJavaEnabled(Boolean javaEnabled) {
        get3DSv2BrowserAttrParamsMap().put("java_enabled", String.valueOf(javaEnabled));
        get3DSv2BrowserAttrRequestBuilder().addElement("java_enabled", javaEnabled);
        return this;
    }

    default Boolean get3dsV2BrowserJavaEnabled() {
        String javaEnabledStr = get3DSv2BrowserAttrParamsMap().get("java_enabled");
        if(javaEnabledStr != null){
            return Boolean.parseBoolean(javaEnabledStr);
        } else{
            return null;
        }
    }

    default BrowserAttributes set3dsV2BrowserLanguage(String language) {
        get3DSv2BrowserAttrParamsMap().put("language", language);
        get3DSv2BrowserAttrRequestBuilder().addElement("language", language);
        return this;
    }

    default String get3dsV2BrowserLanguage() {
        return get3DSv2BrowserAttrParamsMap().get("language");
    }

    default BrowserAttributes set3dsV2BrowserTimeZoneOffset(String timeZoneOffset ) {
        get3DSv2BrowserAttrParamsMap().put("time_zone_offset ", timeZoneOffset );
        get3DSv2BrowserAttrRequestBuilder().addElement("time_zone_offset ", timeZoneOffset );
        return this;
    }

    default String get3dsV2BrowserTimeZoneOffset() {
        return get3DSv2BrowserAttrParamsMap().get("time_zone_offset ");
    }

    default BrowserAttributes set3DSUserAgent (String userAgent ) {
        get3DSv2BrowserAttrParamsMap().put("user_agent ", userAgent );
        get3DSv2BrowserAttrRequestBuilder().addElement("user_agent ", userAgent );
        return this;
    }

    default String get3dsV2BrowserUserAgent() {
        return get3DSv2BrowserAttrParamsMap().get("user_agent ");
    }

    default BrowserAttributes set3dsV2BrowserColorDepth(Integer colorDepth) {
        get3DSv2BrowserAttrParamsMap().put("color_depth", String.valueOf(colorDepth));
        get3DSv2BrowserAttrRequestBuilder().addElement("color_depth", colorDepth);
        return this;
    }

    default Integer get3dsV2BrowserColorDepth() {
        String colorDepthStr = get3DSv2BrowserAttrParamsMap().get("color_depth");
        if(colorDepthStr != null){
            return Integer.parseInt(colorDepthStr);
        } else{
            return null;
        }
    }

    default BrowserAttributes set3dsV2BrowserScreenHeight(Integer screenHeight) {
        get3DSv2BrowserAttrParamsMap().put("screen_height", String.valueOf(screenHeight));
        get3DSv2BrowserAttrRequestBuilder().addElement("screen_height", screenHeight);
        return this;
    }

    default Integer get3dsV2BrowserScreenHeight() {
        String screenHeightStr = get3DSv2BrowserAttrParamsMap().get("screen_height");
        if(screenHeightStr != null){
            return Integer.parseInt(screenHeightStr);
        } else{
            return null;
        }
    }

    default BrowserAttributes set3dsV2BrowserScreenWidth(Integer screenWidth) {
        get3DSv2BrowserAttrParamsMap().put("screen_width", String.valueOf(screenWidth));
        get3DSv2BrowserAttrRequestBuilder().addElement("screen_width", screenWidth);
        return this;
    }

    default Integer get3dsV2BrowserScreenWidth() {
        String screenWidthStr = get3DSv2BrowserAttrParamsMap().get("screen_width");
        if(screenWidthStr != null){
            return Integer.parseInt(screenWidthStr);
        } else{
            return null;
        }
    }

    default RequestBuilder build3DSv2BrowserParams() {
        if (get3dsV2BrowserColorDepth() != null && !ALLOWED_COLOR_DEPTH_VALUES.contains(String.valueOf(get3dsV2BrowserColorDepth()))) {
            throw new InvalidParamException("3DS SDK interface", ALLOWED_COLOR_DEPTH_VALUES);
        }

        return get3DSv2BrowserAttrRequestBuilder();
    }

    RequestBuilder get3DSv2BrowserAttrRequestBuilder();

    HashMap<String, String> get3DSv2BrowserAttrParamsMap();
}
