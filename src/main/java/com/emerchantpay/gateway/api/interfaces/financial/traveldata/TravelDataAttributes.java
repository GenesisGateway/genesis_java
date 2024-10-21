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
import com.emerchantpay.gateway.api.requests.financial.traveldata.AirlineItineraryLegRequest;
import com.emerchantpay.gateway.api.requests.financial.traveldata.AirlineItineraryTaxRequest;

import java.util.HashMap;
import java.util.List;

public interface TravelDataAttributes extends AirlineItineraryAttributes, CarRentalAttributes, HotelRentalAttributes,
        ReferenceTicketAttributes{

    default TravelDataAttributes addAirlineItineraryTax(AirlineItineraryTaxRequest airlineItineraryTax){
        getAirlineItineraryTaxes().add(airlineItineraryTax);
        return this;
    }

    default TravelDataAttributes addAirlineItineraryLeg(AirlineItineraryLegRequest airlineItineraryLeg){
        getAirlineItineraryLegs().add(airlineItineraryLeg);
        return this;
    }

    default TravelDataAttributes clearAirlineItineraryTaxes(){
        getAirlineItineraryTaxes().clear();
        return this;
    }

    default TravelDataAttributes clearAirlineItineraryLegs (){
        getAirlineItineraryLegs().clear();
        return this;
    }

    default RequestBuilder getAirlineItineraryAttrRequestBuilder(){
        RequestBuilder airlineItineraryAttrRequestBuilder = getTravelDataRequestBuildersMap().get("airlineItinerary");
        if(airlineItineraryAttrRequestBuilder == null){
            airlineItineraryAttrRequestBuilder = new RequestBuilder("");
            getTravelDataRequestBuildersMap().put("airlineItinerary", airlineItineraryAttrRequestBuilder);
        }
        return airlineItineraryAttrRequestBuilder;
    };

    default HashMap<String, String> getAirlineItineraryAttrParamsMap(){
        HashMap<String, String> airlineItineraryAttrParamsMap = getTravelDataAttrParamsMap().get("airlineItinerary");
        if(airlineItineraryAttrParamsMap == null){
            airlineItineraryAttrParamsMap = new HashMap<String, String>();
            getTravelDataAttrParamsMap().put("airlineItinerary", airlineItineraryAttrParamsMap);
        }
        return airlineItineraryAttrParamsMap;
    };

    default RequestBuilder getChargesAttrRequestBuilder(){
        RequestBuilder chargesAttrRequestBuilder = getTravelDataRequestBuildersMap().get("charges");
        if(chargesAttrRequestBuilder == null){
            chargesAttrRequestBuilder = new RequestBuilder("");
            getTravelDataRequestBuildersMap().put("charges", chargesAttrRequestBuilder);
        }
        return chargesAttrRequestBuilder;
    };

    default RequestBuilder getReferenceTicketAttrRequestBuilder(){
        RequestBuilder referenceTicketAttrRequestBuilder = getTravelDataRequestBuildersMap().get("referenceTicket");
        if(referenceTicketAttrRequestBuilder == null){
            referenceTicketAttrRequestBuilder = new RequestBuilder("");
            getTravelDataRequestBuildersMap().put("referenceTicket", referenceTicketAttrRequestBuilder);
        }
        return referenceTicketAttrRequestBuilder;
    };

    default HashMap<String, String> getReferenceTicketAttrParamsMap(){
        HashMap<String, String> referenceTicketAttrParamsMap = getTravelDataAttrParamsMap().get("referenceTicket");
        if(referenceTicketAttrParamsMap == null){
            referenceTicketAttrParamsMap = new HashMap<String, String>();
            getTravelDataAttrParamsMap().put("referenceTicket", referenceTicketAttrParamsMap);
        }
        return referenceTicketAttrParamsMap;
    };

    default RequestBuilder getHotelRentalAttrRequestBuilder(){
        RequestBuilder hotelRentalAttrRequestBuilder = getTravelDataRequestBuildersMap().get("hotelRental");
        if(hotelRentalAttrRequestBuilder == null){
            hotelRentalAttrRequestBuilder = new RequestBuilder("");
            getTravelDataRequestBuildersMap().put("hotelRental", hotelRentalAttrRequestBuilder);
        }
        return hotelRentalAttrRequestBuilder;
    };

    default HashMap<String, String> getHotelRentalAttrParamsMap(){
        HashMap<String, String> hotelRentalAttrParamsMap = getTravelDataAttrParamsMap().get("hotelRental");
        if(hotelRentalAttrParamsMap == null){
            hotelRentalAttrParamsMap = new HashMap<String, String>();
            getTravelDataAttrParamsMap().put("hotelRental", hotelRentalAttrParamsMap);
        }
        return hotelRentalAttrParamsMap;
    };

    default RequestBuilder getCarRentalAttrRequestBuilder(){
        RequestBuilder carRentalAttrRequestBuilder = getTravelDataRequestBuildersMap().get("carRental");
        if(carRentalAttrRequestBuilder == null){
            carRentalAttrRequestBuilder = new RequestBuilder("");
            getTravelDataRequestBuildersMap().put("carRental", carRentalAttrRequestBuilder);
        }
        return carRentalAttrRequestBuilder;
    };

    default HashMap<String, String> getCarRentalAttrParamsMap(){
        HashMap<String, String> carRentalAttrParamsMap = getTravelDataAttrParamsMap().get("carRental");
        if(carRentalAttrParamsMap == null){
            carRentalAttrParamsMap = new HashMap<String, String>();
            getTravelDataAttrParamsMap().put("carRental", carRentalAttrParamsMap);
        }
        return carRentalAttrParamsMap;
    };

    default RequestBuilder buildTravelDataParams() {
        RequestBuilder travelDataAttrRequestBuilder = new RequestBuilder("");

        RequestBuilder taxesRequestBuilder = new RequestBuilder("taxes");
            for(AirlineItineraryTaxRequest airlineItineraryTax : getAirlineItineraryTaxes()){
                taxesRequestBuilder.addElement("tax",
                        airlineItineraryTax.buildAirlineItineraryTaxParams().toXML());
            }

        RequestBuilder legsRequestBuilder = new RequestBuilder("legs");
            for(AirlineItineraryLegRequest airlineItineraryLeg : getAirlineItineraryLegs()){
                legsRequestBuilder.addElement("leg",
                        airlineItineraryLeg.buildAirlineItineraryLegParams().toXML());
            }

        RequestBuilder rentalsRequestBuilder = new RequestBuilder("rentals");
        rentalsRequestBuilder.addElement("car_rental", buildCarRentalParams().toXML())
                .addElement("hotel_rental", buildHotelRentalParams().toXML());

        travelDataAttrRequestBuilder.addElement("ticket",
                buildAirlineItineraryParams().addElement(buildReferenceTicketParams().toXML()).toXML())
                .addElement(rentalsRequestBuilder.toXML())
                .addElement(taxesRequestBuilder.toXML())
                .addElement(legsRequestBuilder.toXML())
                .addElement("charges", buildChargesParams().toXML());
        return travelDataAttrRequestBuilder;
    }

    List<AirlineItineraryTaxRequest> getAirlineItineraryTaxes();

    List<AirlineItineraryLegRequest> getAirlineItineraryLegs();

    HashMap<String, RequestBuilder> getTravelDataRequestBuildersMap();

    HashMap<String, HashMap<String, String>> getTravelDataAttrParamsMap();
}
