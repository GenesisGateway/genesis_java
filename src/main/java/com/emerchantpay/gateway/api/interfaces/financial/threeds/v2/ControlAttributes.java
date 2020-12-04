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
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public interface ControlAttributes {

    //Static final modifiers are redundant for interface attributes
    //Device type allowed values: browser, application
    ArrayList<String> ALLOWED_DEVICE_TYPES = new ArrayList<String>(Arrays.asList("browser", "application"));
    //Challenge window size allowed values: 250x400, 390x400, 500x600, 600x400, full_screen
    ArrayList<String> ALLOWED_CHALLENGE_WINDOW_SIZES = new ArrayList<String>(Arrays.asList("250x400", "390x400", "500x600",
            "600x400", "full_screen"));
    //Challenge indicator allowed values: no_preference, no_challenge_requested, preference, mandate
    ArrayList<String> ALLOWED_CHALLENGE_INDICATORS = new ArrayList<String>(Arrays.asList("no_preference", "no_challenge_requested",
            "preference", "mandate"));

    default ControlAttributes set3dsV2ControlDeviceType(String deviceType) {
        get3DSv2ControlAttrParamsMap().put("device_type", deviceType);
        get3DSv2ControlAttrRequestBuilder().addElement("device_type", deviceType);
        return this;
    }

    default String get3dsV2ControlDeviceType() {
        return get3DSv2ControlAttrParamsMap().get("device_type");
    }

    default ControlAttributes set3dsV2ControlChallengeWindowSize(String challengeWindowSize) {
        get3DSv2ControlAttrParamsMap().put("challenge_window_size", challengeWindowSize);
        get3DSv2ControlAttrRequestBuilder().addElement("challenge_window_size", challengeWindowSize);
        return this;
    }

    default String get3dsV2ControlChallengeWindowSize() {
        return get3DSv2ControlAttrParamsMap().get("challenge_window_size");
    }

    default ControlAttributes set3dsV2ControlChallengeIndicator(String challengeIndicator) {
        get3DSv2ControlAttrParamsMap().put("challenge_indicator", challengeIndicator);
        get3DSv2ControlAttrRequestBuilder().addElement("challenge_indicator", challengeIndicator);
        return this;
    }

    default String get3dsV2ControlChallengeIndicator() {
        return get3DSv2ControlAttrParamsMap().get("challenge_indicator");
    }

    default RequestBuilder build3DSv2ControlParams() {

        if (get3dsV2ControlDeviceType() != null && !ALLOWED_DEVICE_TYPES.contains(get3dsV2ControlDeviceType())) {
            throw new InvalidParamException("3DS control device type", ALLOWED_DEVICE_TYPES);
        }
        if (get3dsV2ControlChallengeWindowSize() != null && !ALLOWED_CHALLENGE_WINDOW_SIZES.contains(get3dsV2ControlChallengeWindowSize())) {
            throw new InvalidParamException("3DS control challenge window size", ALLOWED_CHALLENGE_WINDOW_SIZES);
        }
        if (get3dsV2ControlChallengeIndicator() != null && !ALLOWED_CHALLENGE_INDICATORS.contains(get3dsV2ControlChallengeIndicator())) {
            throw new InvalidParamException("3DS control challenge indicator", ALLOWED_CHALLENGE_INDICATORS);
        }
        HashMap<String, String> requiredParams = new HashMap<String, String>();
        if(get3dsV2ControlDeviceType() == "browser"){
            requiredParams.put(RequiredParameters.challengeWindowSize, get3dsV2ControlChallengeWindowSize());
        }
        // Validate request
        getValidator().isValidRequest(requiredParams);

        return get3DSv2ControlAttrRequestBuilder();
    }

    RequestBuilder get3DSv2ControlAttrRequestBuilder();

    HashMap<String, String> get3DSv2ControlAttrParamsMap();

    GenesisValidator getValidator();
}
