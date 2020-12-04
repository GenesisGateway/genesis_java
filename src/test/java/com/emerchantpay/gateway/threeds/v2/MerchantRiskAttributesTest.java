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

public class MerchantRiskAttributesTest {

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
    public void buildMerchantRiskAttributes() {

        String shippingIndicator = "digital_goods";
        sale3DRequest.set3dsV2MerchantRiskShippingIndicator(shippingIndicator);
        assertEquals(shippingIndicator, sale3DRequest.get3dsV2MerchantRiskShippingIndicator());

        String deliveryTimeframe = "electronic";
        sale3DRequest.set3dsV2MerchantRiskDeliveryTimeframe(deliveryTimeframe);
        assertEquals(deliveryTimeframe, sale3DRequest.get3dsV2MerchantRiskDeliveryTimeframe());

        String reorderItemIndicator = "first_time";
        sale3DRequest.set3dsV2MerchantRiskReorderItemsIndicator(reorderItemIndicator);
        assertEquals(reorderItemIndicator, sale3DRequest.get3dsV2MerchantRiskReorderItemsIndicator());

        String preorderPurchaseIndicator = "merchandise_available";
        sale3DRequest.set3dsV2MerchantRiskPreOrderPurchaseIndicator(preorderPurchaseIndicator);
        assertEquals(preorderPurchaseIndicator, sale3DRequest.get3dsV2MerchantRiskPreOrderPurchaseIndicator());

        String preorderDate = today.plusDays(random.nextInt(365)).format(formatter);
        sale3DRequest.set3dsV2MerchantRiskPreOrderDate(preorderDate);
        assertEquals(preorderDate, sale3DRequest.get3dsV2MerchantRiskPreOrderDate());

        sale3DRequest.set3dsV2MerchantRiskGiftCard(true);
        assertEquals(true, sale3DRequest.get3dsV2MerchantRiskGiftCard());

        Integer giftCardCount = random.nextInt(MAX_BOUND_NINETEEN);
        sale3DRequest.set3dsV2MerchantRiskGiftCardCount(giftCardCount);
        assertEquals(giftCardCount, sale3DRequest.get3dsV2MerchantRiskGiftCardCount());

        assertTrue(sale3DRequest.build3DSv2MerchantRiskParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void shippingIndicatorError(){
        sale3DRequest.set3dsV2MerchantRiskShippingIndicator("wrong value");
        sale3DRequest.build3DSv2MerchantRiskParams();
    }

    @Test(expected = InvalidParamException.class)
    public void deliveryTimeframeError(){
        sale3DRequest.set3dsV2MerchantRiskDeliveryTimeframe("wrong value");
        sale3DRequest.build3DSv2MerchantRiskParams();
    }

    @Test(expected = InvalidParamException.class)
    public void reorderItemIndicatorError(){
        sale3DRequest.set3dsV2MerchantRiskReorderItemsIndicator("wrong value");
        sale3DRequest.build3DSv2MerchantRiskParams();
    }

    @Test(expected = InvalidParamException.class)
    public void preorderPurchaseIndicatorError(){
        sale3DRequest.set3dsV2MerchantRiskPreOrderPurchaseIndicator("wrong value");
        sale3DRequest.build3DSv2MerchantRiskParams();
    }
}