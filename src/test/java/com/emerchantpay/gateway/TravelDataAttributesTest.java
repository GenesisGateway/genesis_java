package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;
import com.emerchantpay.gateway.api.requests.financial.traveldata.AirlineItineraryLegRequest;
import com.emerchantpay.gateway.api.requests.financial.traveldata.AirlineItineraryTaxRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.*;

public class TravelDataAttributesTest {

    private SaleRequest saleRequest;
    private Random random;
    private LocalDate today;
    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND_NINETEEN = 19;

    @Before
    public void createTravelDataAttributes() {
        saleRequest = new SaleRequest();
        random = new Random();
        today = LocalDate.now();
    }

    @Test
    public void buildAirlineItineraryAttributes() {

        String aidTicketNumber = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setAidTicketNumber(aidTicketNumber);
        assertEquals(aidTicketNumber, saleRequest.getAidTicketNumber());

        String aidPassengerName = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setAidPassengerName(aidPassengerName);
        assertEquals(aidPassengerName, saleRequest.getAidPassengerName());

        String aidCustomerCode = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setAidCustomerCode(aidCustomerCode);
        assertEquals(aidCustomerCode, saleRequest.getAidCustomerCode());

        String aidAidIssuingCarrier = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setAidIssuingCarrier(aidAidIssuingCarrier);
        assertEquals(aidAidIssuingCarrier, saleRequest.getAidIssuingCarrier());

        int aidTotalFare = random.nextInt(4) + MIN_BOUND;
        saleRequest.setAidTotalFare(aidTotalFare);
        assertEquals(aidTotalFare, saleRequest.getAidTotalFare());

        String aidAgencyName = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setAidAgencyName(aidAgencyName);
        assertEquals(aidAgencyName, saleRequest.getAidAgencyName());

        String aidAgencyCode = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setAidAgencyCode(aidAgencyCode);
        assertEquals(aidAgencyCode, saleRequest.getAidAgencyCode());

        String aidConfirmationInformation = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setAidConfirmationInformation(aidConfirmationInformation);
        assertEquals(aidConfirmationInformation, saleRequest.getAidConfirmationInformation());

        String aidDateOfIssue = today.plusDays(random.nextInt(365)).toString();
        saleRequest.setAidDateOfIssue(aidDateOfIssue);
        assertEquals(aidDateOfIssue, saleRequest.getAidDateOfIssue());

        saleRequest.setAidRestrictedTicketIndicator("1");
        assertEquals("1", saleRequest.getAidRestrictedTicketIndicator());


        assertTrue(saleRequest.buildAirlineItineraryParams() instanceof RequestBuilder);
    }

    @Test
    public void buildAirlineItineraryLegAttributes() {

        AirlineItineraryLegRequest aidLeg = new AirlineItineraryLegRequest();

        String arrivalDate = today.plusDays(random.nextInt(365)).toString();
        aidLeg.setArrivalDate(arrivalDate);
        assertEquals(arrivalDate, aidLeg.getArrivalDate());

        String carrierCode = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        aidLeg.setCarrierCode(carrierCode);
        assertEquals(carrierCode, aidLeg.getCarrierCode());

        String departureDate = today.plusDays(random.nextInt(365)).toString();
        aidLeg.setDepartureDate(departureDate);
        assertEquals(departureDate, aidLeg.getDepartureDate());

        aidLeg.setDepartureTime("11:37");
        assertEquals("11:37", aidLeg.getDepartureTime());

        aidLeg.setDepartureTimeSegment("A");
        assertEquals("A", aidLeg.getDepartureTimeSegment());

        String destinationCity = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        aidLeg.setDestinationCity(destinationCity);
        assertEquals(destinationCity, aidLeg.getDestinationCity());

        aidLeg.setFareBasisCode("1");
        assertEquals("1", aidLeg.getFareBasisCode());

        String flightNumber = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        aidLeg.setFlightNumber(flightNumber);
        assertEquals(flightNumber, aidLeg.getFlightNumber());

        String originCity = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        aidLeg.setOriginCity(originCity);
        assertEquals(originCity, aidLeg.getOriginCity());

        aidLeg.setServiceClass("1");
        assertEquals("1", aidLeg.getServiceClass());

        aidLeg.setStopoverCode("0");
        assertEquals("0", aidLeg.getStopoverCode());

        saleRequest.addAirlineItineraryLeg(aidLeg);
        assertEquals(1, saleRequest.getAirlineItineraryLegs().size());

        assertTrue(saleRequest.buildTravelDataParams() instanceof RequestBuilder);

        saleRequest.clearAirlineItineraryLegs();
        assertEquals(0, saleRequest.getAirlineItineraryLegs().size());
    }

    @Test
    public void buildAirlineTaxAttributes() {

        AirlineItineraryTaxRequest aidTax = new AirlineItineraryTaxRequest();
        int feeAmount = random.nextInt(4) + MIN_BOUND;
        aidTax.setFeeAmount(feeAmount);
        assertEquals(feeAmount, aidTax.getFeeAmount());

        String feeType = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        aidTax.setFeeType(feeType);
        assertEquals(feeType, aidTax.getFeeType());

        saleRequest.addAirlineItineraryTax(aidTax);
        assertEquals(1, saleRequest.getAirlineItineraryTaxes().size());

        assertTrue(saleRequest.buildTravelDataParams() instanceof RequestBuilder);

        saleRequest.clearAirlineItineraryTaxes();
        assertEquals(0, saleRequest.getAirlineItineraryTaxes().size());
    }

    @Test
    public void buildCarRentalAttributes() {

        String carRentalPurchaseIdentifier = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setCarRentalPurchaseIdentifier(carRentalPurchaseIdentifier);
        assertEquals(carRentalPurchaseIdentifier, saleRequest.getCarRentalPurchaseIdentifier());

        saleRequest.setCarRentalClassId("12");
        assertEquals("12", saleRequest.getCarRentalClassId());

        String carRentalPickupDate = today.plusDays(random.nextInt(365)).toString();
        saleRequest.setCarRentalPickupDate(carRentalPickupDate);
        assertEquals(carRentalPickupDate, saleRequest.getCarRentalPickupDate());

        String carRentalRenterName = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setCarRentalRenterName(carRentalRenterName);
        assertEquals(carRentalRenterName, saleRequest.getCarRentalRenterName());

        String carRentalReturnCity = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setCarRentalReturnCity(carRentalReturnCity);
        assertEquals(carRentalReturnCity, saleRequest.getCarRentalReturnCity());

        String carRentalReturnState = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setCarRentalReturnState(carRentalReturnState);
        assertEquals(carRentalReturnState, saleRequest.getCarRentalReturnState());

        String carRentalReturnCountry = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setCarRentalReturnCountry(carRentalReturnCountry);
        assertEquals(carRentalReturnCountry, saleRequest.getCarRentalReturnCountry());

        String carRentalReturnDate = today.plusDays(random.nextInt(365)).toString();
        saleRequest.setCarRentalReturnDate(carRentalReturnDate);
        assertEquals(carRentalReturnDate, saleRequest.getCarRentalReturnDate());

        String carRentalRenterReturnLocationId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setCarRentalRenterReturnLocationId(carRentalRenterReturnLocationId);
        assertEquals(carRentalRenterReturnLocationId, saleRequest.getCarRentalRenterReturnLocationId());

        String carRentalCustomerCode = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setCarRentalCustomerCode(carRentalCustomerCode);
        assertEquals(carRentalCustomerCode, saleRequest.getCarRentalCustomerCode());

        saleRequest.setCarRentalExtraCharges("235");
        assertEquals("235", saleRequest.getCarRentalExtraCharges());

        saleRequest.setCarRentalNoShowIndicator("0");
        assertEquals("0", saleRequest.getCarRentalNoShowIndicator());

        assertTrue(saleRequest.buildCarRentalParams() instanceof RequestBuilder);
    }

    @Test
    public void buildHotelRentalAttributes() {

        String hotelRentalArrivalDate = today.plusDays(random.nextInt(365)).toString();
        saleRequest.setHotelRentalArrivalDate(hotelRentalArrivalDate);
        assertEquals(hotelRentalArrivalDate, saleRequest.getHotelRentalArrivalDate());

        String hotelRentalDepartureDate = today.plusDays(random.nextInt(365)).toString();
        saleRequest.setHotelRentalDepartureDate(hotelRentalDepartureDate);
        assertEquals(hotelRentalDepartureDate, saleRequest.getHotelRentalDepartureDate());

        String hotelRentalCustomerCode = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setHotelRentalCustomerCode(hotelRentalCustomerCode);
        assertEquals(hotelRentalCustomerCode, saleRequest.getHotelRentalCustomerCode());

        String hotelRentalPurchaseIdentifier = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setHotelRentalPurchaseIdentifier(hotelRentalPurchaseIdentifier);
        assertEquals(hotelRentalPurchaseIdentifier, saleRequest.getHotelRentalPurchaseIdentifier());

        saleRequest.setHotelRentalExtraCharges("2457");
        assertEquals("2457", saleRequest.getHotelRentalExtraCharges());

        saleRequest.setHotelRentalNoShowIndicator("1");
        assertEquals("1", saleRequest.getHotelRentalNoShowIndicator());

        assertTrue(saleRequest.buildHotelRentalParams() instanceof RequestBuilder);
    }

    @Test
    public void buildReferenceTicketAttributes() {

        String acIssuedWithTicketNumber = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setACIssuedWithTicketNumber(acIssuedWithTicketNumber);
        assertEquals(acIssuedWithTicketNumber, saleRequest.getACIssuedWithTicketNumber());

        String acTicketDocumentNumber = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setACTicketDocumentNumber(acTicketDocumentNumber);
        assertEquals(acTicketDocumentNumber, saleRequest.getACTicketDocumentNumber());

        String acTicketReferenceId = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setACTicketReferenceId(acTicketReferenceId);
        assertEquals(acTicketReferenceId, saleRequest.getACTicketReferenceId());

        String acType = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setACType(acType);
        assertEquals(acType, saleRequest.getACType());

        String acSubType = RandomStringUtils.randomAlphanumeric(random.nextInt(MAX_BOUND_NINETEEN) + MIN_BOUND);
        saleRequest.setACSubType(acSubType);
        assertEquals(acSubType, saleRequest.getACSubType());

        assertTrue(saleRequest.buildTravelDataParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void aidRestrictedTicketIndicatorError(){
        saleRequest.setAidRestrictedTicketIndicator("2");
        saleRequest.buildAirlineItineraryParams();
    }

    @Test(expected = InvalidParamException.class)
    public void departureTimeSegmentError(){
        AirlineItineraryLegRequest aidLeg = new AirlineItineraryLegRequest();
        aidLeg.setDepartureTimeSegment("B");
        aidLeg.buildAirlineItineraryLegParams();

    }

    @Test(expected = InvalidParamException.class)
    public void stopOverCodeError(){
        AirlineItineraryLegRequest aidLeg = new AirlineItineraryLegRequest();
        aidLeg.setDepartureTimeSegment("P");
        aidLeg.setStopoverCode("2");
        aidLeg.buildAirlineItineraryLegParams();
    }

    @Test(expected = InvalidParamException.class)
    public void carRentalClassIdError(){
        saleRequest.setCarRentalClassId("44");
        saleRequest.buildTravelDataParams();
    }

    @Test(expected = RegexException.class)
    public void carRentalExtraChargesError(){
        saleRequest.setCarRentalClassId("12");
        saleRequest.setCarRentalExtraCharges("1246");
        saleRequest.buildTravelDataParams();
    }

    @Test(expected = InvalidParamException.class)
    public void carRentalNoShowIndicatorError(){
        saleRequest.setCarRentalClassId("12");
        saleRequest.setCarRentalExtraCharges("5");
        saleRequest.setCarRentalNoShowIndicator("3");
        saleRequest.buildTravelDataParams();
    }

    @Test(expected = RegexException.class)
    public void hotelRentalExtraChargesError(){
        saleRequest.setHotelRentalExtraCharges("3679");
        saleRequest.buildTravelDataParams();
    }

    @Test(expected = InvalidParamException.class)
    public void hotelRentalNoShowIndicatorError(){
        saleRequest.setHotelRentalExtraCharges("5");
        saleRequest.setHotelRentalNoShowIndicator("3");
        saleRequest.buildTravelDataParams();
    }
}