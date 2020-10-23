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

public interface CarRentalAttributes {

    //Allowed car rental classification.
    ArrayList<String> allowedClassIds = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "9999"));

    //Allowed No show indicators
    ArrayList<String> allowedNoShowIndicator = new ArrayList<String>(Arrays.asList("0", "1"));

    default CarRentalAttributes setCarRentalPurchaseIdentifier(String purchaseIdentifier) {
        getCarRentalAttrParamsMap().put("purchase_identifier", purchaseIdentifier);
        getCarRentalAttrRequestBuilder().addElement("purchase_identifier", purchaseIdentifier);
        return this;
    }

    default String getCarRentalPurchaseIdentifier() {
        return getCarRentalAttrParamsMap().get("purchase_identifier");
    }

    default CarRentalAttributes setCarRentalClassId(String classId) {
        getCarRentalAttrParamsMap().put("class_id", classId);
        getCarRentalAttrRequestBuilder().addElement("class_id", classId);
        return this;
    }

    default String getCarRentalClassId() {
        return getCarRentalAttrParamsMap().get("class_id");
    }

    default CarRentalAttributes setCarRentalPickupDate(String pickupDate) {
        getCarRentalAttrParamsMap().put("pickup_date", pickupDate);
        getCarRentalAttrRequestBuilder().addElement("pickup_date", pickupDate);
        return this;
    }

    default String getCarRentalPickupDate() {
        return getCarRentalAttrParamsMap().get("pickup_date");
    }

    default CarRentalAttributes setCarRentalRenterName(String renterName) {
        getCarRentalAttrParamsMap().put("renter_name", renterName);
        getCarRentalAttrRequestBuilder().addElement("renter_name", renterName);
        return this;
    }

    default String getCarRentalRenterName() {
        return getCarRentalAttrParamsMap().get("renter_name");
    }

    default CarRentalAttributes setCarRentalReturnCity(String returnCity) {
        getCarRentalAttrParamsMap().put("return_city", returnCity);
        getCarRentalAttrRequestBuilder().addElement("return_city", returnCity);
        return this;
    }

    default String getCarRentalReturnCity() {
        return getCarRentalAttrParamsMap().get("return_city");
    }

    default CarRentalAttributes setCarRentalReturnState(String returnState) {
        getCarRentalAttrParamsMap().put("return_state", returnState);
        getCarRentalAttrRequestBuilder().addElement("return_state", returnState);
        return this;
    }

    default String getCarRentalReturnState() {
        return getCarRentalAttrParamsMap().get("return_state");
    }

    default CarRentalAttributes setCarRentalReturnCountry(String returnCountry) {
        getCarRentalAttrParamsMap().put("return_country", returnCountry);
        getCarRentalAttrRequestBuilder().addElement("return_country", returnCountry);
        return this;
    }

    default String getCarRentalReturnCountry() {
        return getCarRentalAttrParamsMap().get("return_country");
    }

    default CarRentalAttributes setCarRentalReturnDate(String returnDate) {
        getCarRentalAttrParamsMap().put("return_date", returnDate);
        getCarRentalAttrRequestBuilder().addElement("return_date", returnDate);
        return this;
    }

    default String getCarRentalReturnDate() {
        return getCarRentalAttrParamsMap().get("return_date");
    }

    default CarRentalAttributes setCarRentalRenterReturnLocationId(String renterReturnLocationId) {
        getCarRentalAttrParamsMap().put("renter_return_location_id", renterReturnLocationId);
        getCarRentalAttrRequestBuilder().addElement("renter_return_location_id", renterReturnLocationId);
        return this;
    }

    default String getCarRentalRenterReturnLocationId() {
        return getCarRentalAttrParamsMap().get("renter_return_location_id");
    }

    default CarRentalAttributes setCarRentalCustomerCode(String customerCode) {
        getCarRentalAttrParamsMap().put("customer_code", customerCode);
        getCarRentalAttrRequestBuilder().addElement("customer_code", customerCode);
        return this;
    }

    default String getCarRentalCustomerCode() {
        return getCarRentalAttrParamsMap().get("customer_code");
    }

    default CarRentalAttributes setCarRentalExtraCharges(String extraCharges) {
        getCarRentalAttrParamsMap().put("extra_charges", extraCharges);
        getCarRentalAttrRequestBuilder().addElement("extra_charges", extraCharges);
        return this;
    }

    default String getCarRentalExtraCharges() {
        return getCarRentalAttrParamsMap().get("extra_charges");
    }

    default CarRentalAttributes setCarRentalNoShowIndicator(String noShowIndicator) {
        getCarRentalAttrParamsMap().put("no_show_indicator", noShowIndicator);
        getCarRentalAttrRequestBuilder().addElement("no_show_indicator", noShowIndicator);
        return this;
    }

    default String getCarRentalNoShowIndicator() {
        return getCarRentalAttrParamsMap().get("no_show_indicator");
    }

    default RequestBuilder buildCarRentalParams() {

        if (getCarRentalClassId() != null && !allowedClassIds.contains(getCarRentalClassId())) {
            throw new InvalidParamException("Invalid value for class id. Check documentation for more information.");
        }

        if (getCarRentalNoShowIndicator() != null && !allowedNoShowIndicator.contains(getCarRentalNoShowIndicator())) {
            throw new InvalidParamException("no show indicator", allowedNoShowIndicator);
        }

        if (!getValidator().isValidCarExtraCharge(getCarRentalExtraCharges(), "extra charges")) {
            ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }

        return getCarRentalAttrRequestBuilder();
    }

    RequestBuilder getCarRentalAttrRequestBuilder();

    HashMap<String, String> getCarRentalAttrParamsMap();

    GenesisValidator getValidator();
}