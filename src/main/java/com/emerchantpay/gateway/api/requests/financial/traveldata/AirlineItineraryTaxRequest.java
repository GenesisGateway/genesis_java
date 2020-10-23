package com.emerchantpay.gateway.api.requests.financial.traveldata;

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


import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.interfaces.financial.traveldata.AirlineItineraryTaxAttributes;

import java.util.HashMap;

public class AirlineItineraryTaxRequest extends Request implements AirlineItineraryTaxAttributes {

    //Airline Itinerary Taxes Attributes
    private RequestBuilder airlineItineraryTaxesAttrRequestBuilder;
    private HashMap<String, String> airlineItineraryTaxesAttrParamsMap;

    @Override
    public RequestBuilder getAirlineItineraryTaxesAttrRequestBuilder() {
        if(airlineItineraryTaxesAttrRequestBuilder == null){
            airlineItineraryTaxesAttrRequestBuilder = new RequestBuilder("");
        }
        return airlineItineraryTaxesAttrRequestBuilder;
    }

    @Override
    public HashMap<String, String> getAirlineItineraryTaxesAttrParamsMap() {
        if(airlineItineraryTaxesAttrParamsMap == null){
            airlineItineraryTaxesAttrParamsMap = new HashMap<String, String>();
        }
        return airlineItineraryTaxesAttrParamsMap;
    }
}
