package com.emerchantpay.gateway.api.interfaces;

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
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.ArrayList;
import java.util.HashMap;

public interface BusinessParamsAttributes  {

    // Business params
    default BusinessParamsAttributes setBusinessEventStartDate(String eventStartDate) {
        if(getValidator().isValidDate(eventStartDate, "event_start_date")){
            getBusinessAttrParamsMap().put("event_start_date", eventStartDate);
            getBusinessParamsAttrRequestBuilder().addElement("event_start_date", eventStartDate);
        }
        return this;
    }

    default String getBusinessEventStartDate() {
        return getBusinessAttrParamsMap().get("event_start_date");
    }

    default BusinessParamsAttributes setBusinessEventEndDate(String eventEndDate) {
        if(getValidator().isValidDate(eventEndDate, "event_end_date")) {
            getBusinessAttrParamsMap().put("event_end_date", eventEndDate);
            getBusinessParamsAttrRequestBuilder().addElement("event_end_date", eventEndDate);
        }
        return this;
    }

    default String getBusinessEventEndDate() {
        return getBusinessAttrParamsMap().get("event_end_date");
    }

    default BusinessParamsAttributes setBusinessEventOrganizerId(String eventOrganizerId) {
        getBusinessAttrParamsMap().put("event_organizer_id", eventOrganizerId);
        getBusinessParamsAttrRequestBuilder().addElement("event_organizer_id", eventOrganizerId);
        return this;
    }

    default String getBusinessEventOrganizerId() {
        return getBusinessAttrParamsMap().get("event_organizer_id");
    }

    default BusinessParamsAttributes setBusinessEventId(String eventId) {
        getBusinessAttrParamsMap().put("event_id", eventId);
        getBusinessParamsAttrRequestBuilder().addElement("event_id", eventId);
        return this;
    }

    default String getBusinessEventId() {
        return getBusinessAttrParamsMap().get("event_id");
    }

    default BusinessParamsAttributes setBusinessDateOfOrder(String dateOfOrder) {
        if(getValidator().isValidDate(dateOfOrder, "date_of_order")) {
            getBusinessAttrParamsMap().put("date_of_order", dateOfOrder);
            getBusinessParamsAttrRequestBuilder().addElement("date_of_order", dateOfOrder);
        }
        return this;
    }

    default String getBusinessDateOfOrder() {
        return getBusinessAttrParamsMap().get("date_of_order");
    }

    default BusinessParamsAttributes setBusinessDeliveryDate(String deliveryDate) {
        if(getValidator().isValidDate(deliveryDate, "delivery_date")) {
            getBusinessAttrParamsMap().put("delivery_date", deliveryDate);
            getBusinessParamsAttrRequestBuilder().addElement("delivery_date", deliveryDate);
        }
        return this;
    }

    default String getBusinessDeliveryDate() {
        return getBusinessAttrParamsMap().get("delivery_date");
    }

    default BusinessParamsAttributes setBusinessNameOfSupplier(String nameOfSupplier) {
        getBusinessAttrParamsMap().put("name_of_the_supplier", nameOfSupplier);
        getBusinessParamsAttrRequestBuilder().addElement("name_of_the_supplier", nameOfSupplier);
        return this;
    }

    default String getBusinessNameOfSupplier() {
        return getBusinessAttrParamsMap().get("name_of_the_supplier");
    }

    default RequestBuilder buildBusinessParams() {
        ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
        if (invalidParams.isEmpty()) {
            return getBusinessParamsAttrRequestBuilder();
        } else {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }
    }

    RequestBuilder getBusinessParamsAttrRequestBuilder();

    HashMap<String, String> getBusinessAttrParamsMap();

    GenesisValidator getValidator();
}