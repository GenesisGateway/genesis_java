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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public interface AirlineItineraryAttributes {

    //Restricted ticket indicator allowed values: Empty String, 0, 1; Empty String or 0 = No restriction, 1 = Restriction
    ArrayList<String> allowedRestrTicketIndicators = new ArrayList<String>(Arrays.asList("", "0", "1"));

    default AirlineItineraryAttributes setAidTicketNumber(String ticketNumber) {
        getAirlineItineraryAttrParamsMap().put("ticket_number", ticketNumber);
        getAirlineItineraryAttrRequestBuilder().addElement("ticket_number", ticketNumber);
        return this;
    }

    default String getAidTicketNumber() {
        return getAirlineItineraryAttrParamsMap().get("ticket_number");
    }

    default AirlineItineraryAttributes setAidPassengerName(String passengerName) {
        getAirlineItineraryAttrParamsMap().put("passenger_name", passengerName);
        getAirlineItineraryAttrRequestBuilder().addElement("passenger_name", passengerName);
        return this;
    }

    default String getAidPassengerName() {
        return getAirlineItineraryAttrParamsMap().get("passenger_name");
    }

    default AirlineItineraryAttributes setAidCustomerCode(String customerCode) {
        getAirlineItineraryAttrParamsMap().put("customer_code", customerCode);
        getAirlineItineraryAttrRequestBuilder().addElement("customer_code", customerCode);
        return this;
    }

    default String getAidCustomerCode() {
        return getAirlineItineraryAttrParamsMap().get("customer_code");
    }

    default AirlineItineraryAttributes setAidIssuingCarrier(String issuingCarrier) {
        getAirlineItineraryAttrParamsMap().put("issuing_carrier", issuingCarrier);
        getAirlineItineraryAttrRequestBuilder().addElement("issuing_carrier", issuingCarrier);
        return this;
    }

    default String getAidIssuingCarrier() {
        return getAirlineItineraryAttrParamsMap().get("issuing_carrier");
    }

    default AirlineItineraryAttributes setAidTotalFare(int totalFare) {
        getAirlineItineraryAttrParamsMap().put("total_fare", String.valueOf(totalFare));
        getAirlineItineraryAttrRequestBuilder().addElement("total_fare", totalFare);
        return this;
    }

    default int getAidTotalFare() {
        return Integer.parseInt(getAirlineItineraryAttrParamsMap().get("total_fare"));
    }

    default AirlineItineraryAttributes setAidAgencyName(String agencyName) {
        getAirlineItineraryAttrParamsMap().put("agency_name", agencyName);
        getAirlineItineraryAttrRequestBuilder().addElement("agency_name", agencyName);
        return this;
    }

    default String getAidAgencyName() {
        return getAirlineItineraryAttrParamsMap().get("agency_name");
    }

    default AirlineItineraryAttributes setAidAgencyCode(String agencyCode) {
        getAirlineItineraryAttrParamsMap().put("agency_code", agencyCode);
        getAirlineItineraryAttrRequestBuilder().addElement("agency_code", agencyCode);
        return this;
    }

    default String getAidAgencyCode() {
        return getAirlineItineraryAttrParamsMap().get("agency_code");
    }

    default AirlineItineraryAttributes setAidConfirmationInformation(String confirmationInformation) {
        getAirlineItineraryAttrParamsMap().put("confirmation_information", confirmationInformation);
        getAirlineItineraryAttrRequestBuilder().addElement("confirmation_information", confirmationInformation);
        return this;
    }

    default String getAidConfirmationInformation() {
        return getAirlineItineraryAttrParamsMap().get("confirmation_information");
    }

    default AirlineItineraryAttributes setAidDateOfIssue(String dateOfIssue) {
        getAirlineItineraryAttrParamsMap().put("date_of_issue", dateOfIssue);
        getAirlineItineraryAttrRequestBuilder().addElement("date_of_issue", dateOfIssue);
        return this;
    }

    default String getAidDateOfIssue() {
        return getAirlineItineraryAttrParamsMap().get("date_of_issue");
    }

    default AirlineItineraryAttributes setAidRestrictedTicketIndicator(String restrictedTicketIndicator) {
        getAirlineItineraryAttrParamsMap().put("restricted_ticket_indicator", restrictedTicketIndicator);
        getAirlineItineraryAttrRequestBuilder().addElement("restricted_ticket_indicator", restrictedTicketIndicator);
        return this;
    }

    default String getAidRestrictedTicketIndicator() {
        return getAirlineItineraryAttrParamsMap().get("restricted_ticket_indicator");
    }


    default RequestBuilder buildAirlineItineraryParams() {

        if (getAidRestrictedTicketIndicator() != null && !allowedRestrTicketIndicators.contains(getAidRestrictedTicketIndicator())) {
            throw new InvalidParamException("restricted ticket indicator", allowedRestrTicketIndicators);
        }

        return getAirlineItineraryAttrRequestBuilder();
    }

    RequestBuilder getAirlineItineraryAttrRequestBuilder();

    HashMap<String, String> getAirlineItineraryAttrParamsMap();
}