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
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public interface HotelRentalAttributes {

    //Allowed No show indicators
    ArrayList<String> allowedNoShowIndicator = new ArrayList<String>(Arrays.asList("0", "1"));

    default HotelRentalAttributes setHotelRentalPurchaseIdentifier(String purchaseIdentifier) {
        getHotelRentalAttrParamsMap().put("purchase_identifier", purchaseIdentifier);
        getHotelRentalAttrRequestBuilder().addElement("purchase_identifier", purchaseIdentifier);
        return this;
    }

    default String getHotelRentalPurchaseIdentifier() {
        return getHotelRentalAttrParamsMap().get("purchase_identifier");
    }

    default HotelRentalAttributes setHotelRentalArrivalDate(String arrivalDate) {
        getHotelRentalAttrParamsMap().put("arrival_date", arrivalDate);
        getHotelRentalAttrRequestBuilder().addElement("arrival_date", arrivalDate);
        return this;
    }

    default String getHotelRentalArrivalDate() {
        return getHotelRentalAttrParamsMap().get("arrival_date");
    }

    default HotelRentalAttributes setHotelRentalDepartureDate(String departureDate) {
        getHotelRentalAttrParamsMap().put("departure_date", departureDate);
        getHotelRentalAttrRequestBuilder().addElement("departure_date", departureDate);
        return this;
    }

    default String getHotelRentalDepartureDate() {
        return getHotelRentalAttrParamsMap().get("departure_date");
    }

    default HotelRentalAttributes setHotelRentalCustomerCode(String customerCode) {
        getHotelRentalAttrParamsMap().put("customer_code", customerCode);
        getHotelRentalAttrRequestBuilder().addElement("customer_code", customerCode);
        return this;
    }

    default String getHotelRentalCustomerCode() {
        return getHotelRentalAttrParamsMap().get("customer_code");
    }

    default HotelRentalAttributes setHotelRentalExtraCharges(String extraCharges) {
        getHotelRentalAttrParamsMap().put("extra_charges", extraCharges);
        getHotelRentalAttrRequestBuilder().addElement("extra_charges", extraCharges);
        return this;
    }

    default String getHotelRentalExtraCharges() {
        return getHotelRentalAttrParamsMap().get("extra_charges");
    }

    default HotelRentalAttributes setHotelRentalNoShowIndicator(String noShowIndicator) {
        getHotelRentalAttrParamsMap().put("no_show_indicator", noShowIndicator);
        getHotelRentalAttrRequestBuilder().addElement("no_show_indicator", noShowIndicator);
        return this;
    }

    default String getHotelRentalNoShowIndicator() {
        return getHotelRentalAttrParamsMap().get("no_show_indicator");
    }

    default RequestBuilder buildHotelRentalParams() {

        if (getHotelRentalNoShowIndicator() != null && !allowedNoShowIndicator.contains(getHotelRentalNoShowIndicator())) {
            throw new InvalidParamException("no show indicator", allowedNoShowIndicator);
        }

        if (!getValidator().isValidHotelExtraCharge(getHotelRentalExtraCharges(), "extra charges")) {
            ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }

        return getHotelRentalAttrRequestBuilder();
    }

    RequestBuilder getHotelRentalAttrRequestBuilder();

    HashMap<String, String> getHotelRentalAttrParamsMap();

    GenesisValidator getValidator();
}