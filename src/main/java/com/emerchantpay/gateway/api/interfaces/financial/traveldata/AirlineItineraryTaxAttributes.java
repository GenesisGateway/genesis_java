package com.emerchantpay.gateway.api.interfaces.financial.traveldata;

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

public interface AirlineItineraryTaxAttributes {

    default AirlineItineraryTaxAttributes setFeeAmount(int feeAmount) {
        getAirlineItineraryTaxesAttrParamsMap().put("fee_amount", String.valueOf(feeAmount));
        getAirlineItineraryTaxesAttrRequestBuilder().addElement("fee_amount", feeAmount);
        return this;
    }

    default int getFeeAmount() {
        return Integer.parseInt(getAirlineItineraryTaxesAttrParamsMap().get("fee_amount"));
    }

    default AirlineItineraryTaxAttributes setFeeType(String feeType) {
        getAirlineItineraryTaxesAttrParamsMap().put("fee_type", feeType);
        getAirlineItineraryTaxesAttrRequestBuilder().addElement("fee_type", feeType);
        return this;
    }

    default String getFeeType() {
        return getAirlineItineraryTaxesAttrParamsMap().get("fee_type");
    }

    default RequestBuilder buildAirlineItineraryTaxParams() {
        return getAirlineItineraryTaxesAttrRequestBuilder();
    }

    RequestBuilder getAirlineItineraryTaxesAttrRequestBuilder();

    HashMap<String, String> getAirlineItineraryTaxesAttrParamsMap();
}
