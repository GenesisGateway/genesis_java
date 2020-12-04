package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecurringAttributesTest {

    private Sale3DRequest sale3DRequest;
    private Random random;
    private LocalDate today;
    private DateTimeFormatter formatter;
    private static final int MAX_BOUND_NINETEEN = 19;

    @Before
    public void createThreedsV2Attributes() {
        sale3DRequest = new Sale3DRequest();
        random = new Random();
        today = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @Test
    public void buildRecurringAttributes() {

        String expirationDate = today.plusDays(random.nextInt(365)).format(formatter);
        sale3DRequest.set3dsV2RecurringExpirationDate(expirationDate);
        assertEquals(expirationDate, sale3DRequest.get3dsV2RecurringExpirationDate());

        Integer frequency = random.nextInt(MAX_BOUND_NINETEEN) + 1;
        sale3DRequest.set3dsV2RecurringFrequency(frequency);
        assertEquals(frequency, sale3DRequest.get3dsV2RecurringFrequency());

        assertTrue(sale3DRequest.build3DSv2RecurringParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void frequencyError(){

        Integer frequency = 0;
        sale3DRequest.set3dsV2RecurringFrequency(frequency);
        sale3DRequest.build3DSv2RecurringParams();
    }
}