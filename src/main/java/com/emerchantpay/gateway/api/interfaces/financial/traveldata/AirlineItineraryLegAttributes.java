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

public interface AirlineItineraryLegAttributes {

    //Departure time segment. Allowed values: A, P
    ArrayList<String> allowedDepartureTimeSegments = new ArrayList<String>(Arrays.asList("A", "P"));

    //Stopover code indicating whether there was a direct or a non-direct flight or route on the same ticket number.
    //Allowed values: 0, 1.
    ArrayList<String> allowedStopOverCodes = new ArrayList<String>(Arrays.asList("0", "1"));

    default AirlineItineraryLegAttributes setDepartureDate(String departureDate) {
        getAirlineItineraryLegAttrParamsMap().put("departure_date", departureDate);
        getAirlineItineraryLegAttrRequestBuilder().addElement("departure_date", departureDate);
        return this;
    }

    default String getDepartureDate() {
        return getAirlineItineraryLegAttrParamsMap().get("departure_date");
    }

    default AirlineItineraryLegAttributes setArrivalDate(String arrivalDate) {
        getAirlineItineraryLegAttrParamsMap().put("arrival_date", arrivalDate);
        getAirlineItineraryLegAttrRequestBuilder().addElement("arrival_date", arrivalDate);
        return this;
    }

    default String getArrivalDate() {
        return getAirlineItineraryLegAttrParamsMap().get("arrival_date");
    }

    default AirlineItineraryLegAttributes setCarrierCode(String carrierCode) {
        getAirlineItineraryLegAttrParamsMap().put("carrier_code", carrierCode);
        getAirlineItineraryLegAttrRequestBuilder().addElement("carrier_code", carrierCode);
        return this;
    }

    default String getCarrierCode() {
        return getAirlineItineraryLegAttrParamsMap().get("carrier_code");
    }

    default AirlineItineraryLegAttributes setServiceClass(String serviceClass) {
        getAirlineItineraryLegAttrParamsMap().put("service_class", serviceClass);
        getAirlineItineraryLegAttrRequestBuilder().addElement("service_class", serviceClass);
        return this;
    }

    default String getServiceClass() {
        return getAirlineItineraryLegAttrParamsMap().get("service_class");
    }

    default AirlineItineraryLegAttributes setOriginCity(String originCity) {
        getAirlineItineraryLegAttrParamsMap().put("origin_city", originCity);
        getAirlineItineraryLegAttrRequestBuilder().addElement("origin_city", originCity);
        return this;
    }

    default String getOriginCity() {
        return getAirlineItineraryLegAttrParamsMap().get("origin_city");
    }

    default AirlineItineraryLegAttributes setDestinationCity(String destinationCity) {
        getAirlineItineraryLegAttrParamsMap().put("destination_city", destinationCity);
        getAirlineItineraryLegAttrRequestBuilder().addElement("destination_city", destinationCity);
        return this;
    }

    default String getDestinationCity() {
        return getAirlineItineraryLegAttrParamsMap().get("destination_city");
    }

    default AirlineItineraryLegAttributes setStopoverCode(String stopoverCode) {
        getAirlineItineraryLegAttrParamsMap().put("stopover_code", stopoverCode);
        getAirlineItineraryLegAttrRequestBuilder().addElement("stopover_code", stopoverCode);
        return this;
    }

    default String getStopoverCode() {
        return getAirlineItineraryLegAttrParamsMap().get("stopover_code");
    }

    default AirlineItineraryLegAttributes setFareBasisCode(String fareBasisCode) {
        getAirlineItineraryLegAttrParamsMap().put("fare_basis_code", fareBasisCode);
        getAirlineItineraryLegAttrRequestBuilder().addElement("fare_basis_code", fareBasisCode);
        return this;
    }

    default String getFareBasisCode() {
        return getAirlineItineraryLegAttrParamsMap().get("fare_basis_code");
    }

    default AirlineItineraryLegAttributes setFlightNumber(String flightNumber) {
        getAirlineItineraryLegAttrParamsMap().put("flight_number", flightNumber);
        getAirlineItineraryLegAttrRequestBuilder().addElement("flight_number", flightNumber);
        return this;
    }

    default String getFlightNumber() {
        return getAirlineItineraryLegAttrParamsMap().get("flight_number");
    }

    default AirlineItineraryLegAttributes setDepartureTime(String departureTime) {
        getAirlineItineraryLegAttrParamsMap().put("departure_time", departureTime);
        getAirlineItineraryLegAttrRequestBuilder().addElement("departure_time", departureTime);
        return this;
    }

    default String getDepartureTime() {
        return getAirlineItineraryLegAttrParamsMap().get("departure_time");
    }

    default AirlineItineraryLegAttributes setDepartureTimeSegment(String departureTimeSegment) {
        getAirlineItineraryLegAttrParamsMap().put("departure_time_segment", departureTimeSegment);
        getAirlineItineraryLegAttrRequestBuilder().addElement("departure_time_segment", departureTimeSegment);
        return this;
    }

    default String getDepartureTimeSegment() {
        return getAirlineItineraryLegAttrParamsMap().get("departure_time_segment");
    }

    default RequestBuilder buildAirlineItineraryLegParams() {

        if (getDepartureTimeSegment() != null && !allowedDepartureTimeSegments.contains(getDepartureTimeSegment())) {
            throw new InvalidParamException("departure time segment", allowedDepartureTimeSegments);
        }

        if (getStopoverCode() != null && !allowedStopOverCodes.contains(getStopoverCode())) {
            throw new InvalidParamException("stopover code", allowedStopOverCodes);
        }

        return getAirlineItineraryLegAttrRequestBuilder();
    }

    RequestBuilder getAirlineItineraryLegAttrRequestBuilder();

    HashMap<String, String> getAirlineItineraryLegAttrParamsMap();
}