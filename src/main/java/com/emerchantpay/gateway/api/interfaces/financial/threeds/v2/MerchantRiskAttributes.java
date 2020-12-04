package com.emerchantpay.gateway.api.interfaces.financial.threeds.v2;

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

public interface MerchantRiskAttributes {

    //Static final modifiers are redundant for interface attributes
    //Shipping indicator allowed values: same_as_billing, stored_address, verified_address, pick_up, digital_goods, travel, event_tickets, other
    ArrayList<String> ALLOWED_SHIPPING_INDICATORS = new ArrayList<String>(Arrays.asList("same_as_billing", "stored_address",
            "verified_address", "pick_up", "digital_goods", "travel", "event_tickets", "other"));

    //Delivery timeframe allowed values: electronic, same_day, over_night, another_day
    ArrayList<String> ALLOWED_DELIVERY_TIMEFRAMES = new ArrayList<String>(Arrays.asList("electronic", "same_day", "over_night", "another_day"));

    //Reorder items indicator allowed values: first_time, reordered
    ArrayList<String> ALLOWED_REORDER_ITEMS_INDICATORS = new ArrayList<String>(Arrays.asList("first_time", "reordered"));

    //Pre order purchase indicator allowed values: merchandise_available, future_availability
    ArrayList<String> ALLOWED_PRE_ORDER_PURCHASE_INDICATORS = new ArrayList<String>(Arrays.asList("merchandise_available", "future_availability"));


    default MerchantRiskAttributes set3dsV2MerchantRiskShippingIndicator(String shippingIndicator) {
        get3DSv2MerchantRiskAttrParamsMap().put("shipping_indicator", shippingIndicator);
        get3DSv2MerchantRiskAttrRequestBuilder().addElement("shipping_indicator", shippingIndicator);
        return this;
    }

    default String get3dsV2MerchantRiskShippingIndicator() {
        return get3DSv2MerchantRiskAttrParamsMap().get("shipping_indicator");
    }

    default MerchantRiskAttributes set3dsV2MerchantRiskDeliveryTimeframe(String deliveryTimeframe) {
        get3DSv2MerchantRiskAttrParamsMap().put("delivery_timeframe", deliveryTimeframe);
        get3DSv2MerchantRiskAttrRequestBuilder().addElement("delivery_timeframe", deliveryTimeframe);
        return this;
    }

    default String get3dsV2MerchantRiskDeliveryTimeframe() {
        return get3DSv2MerchantRiskAttrParamsMap().get("delivery_timeframe");
    }

    default MerchantRiskAttributes set3dsV2MerchantRiskReorderItemsIndicator(String reorderItemsIndicator) {
        get3DSv2MerchantRiskAttrParamsMap().put("reorder_items_indicator", reorderItemsIndicator);
        get3DSv2MerchantRiskAttrRequestBuilder().addElement("reorder_items_indicator", reorderItemsIndicator);
        return this;
    }

    default String get3dsV2MerchantRiskReorderItemsIndicator() {
        return get3DSv2MerchantRiskAttrParamsMap().get("reorder_items_indicator");
    }

    default MerchantRiskAttributes set3dsV2MerchantRiskPreOrderPurchaseIndicator(String preOrderPurchaseIndicator) {
        get3DSv2MerchantRiskAttrParamsMap().put("pre_order_purchase_indicator", preOrderPurchaseIndicator);
        get3DSv2MerchantRiskAttrRequestBuilder().addElement("pre_order_purchase_indicator", preOrderPurchaseIndicator);
        return this;
    }

    default String get3dsV2MerchantRiskPreOrderPurchaseIndicator() {
        return get3DSv2MerchantRiskAttrParamsMap().get("pre_order_purchase_indicator");
    }

    default MerchantRiskAttributes set3dsV2MerchantRiskPreOrderDate(String preOrderDate) {
        if(getValidator().isValidDate(preOrderDate, "pre_order_date")) {
            get3DSv2MerchantRiskAttrParamsMap().put("pre_order_date", preOrderDate);
            get3DSv2MerchantRiskAttrRequestBuilder().addElement("pre_order_date", preOrderDate);
        }
        return this;
    }

    default String get3dsV2MerchantRiskPreOrderDate() {
        return get3DSv2MerchantRiskAttrParamsMap().get("pre_order_date");
    }

    default MerchantRiskAttributes set3dsV2MerchantRiskGiftCard(Boolean giftCard) {
        get3DSv2MerchantRiskAttrParamsMap().put("gift_card", String.valueOf(giftCard));
        get3DSv2MerchantRiskAttrRequestBuilder().addElement("gift_card", giftCard);
        return this;
    }

    default Boolean get3dsV2MerchantRiskGiftCard() {
        String giftCardStr = get3DSv2MerchantRiskAttrParamsMap().get("gift_card");
        if(giftCardStr != null){
            return Boolean.parseBoolean(giftCardStr);
        } else{
            return null;
        }
    }

    default MerchantRiskAttributes set3dsV2MerchantRiskGiftCardCount(Integer giftCardCount) {
        get3DSv2MerchantRiskAttrParamsMap().put("gift_card_count", String.valueOf(giftCardCount));
        get3DSv2MerchantRiskAttrRequestBuilder().addElement("gift_card_count", giftCardCount);
        return this;
    }

    default Integer get3dsV2MerchantRiskGiftCardCount() {
        String giftCardCountStr = get3DSv2MerchantRiskAttrParamsMap().get("gift_card_count");
        if(giftCardCountStr != null){
            return Integer.parseInt(giftCardCountStr);
        } else{
            return null;
        }
    }

    default RequestBuilder build3DSv2MerchantRiskParams() {

        if (get3dsV2MerchantRiskShippingIndicator() != null && !ALLOWED_SHIPPING_INDICATORS.contains(get3dsV2MerchantRiskShippingIndicator())) {
            throw new InvalidParamException("Merchant risk shipping indicator", ALLOWED_SHIPPING_INDICATORS);
        }
        if (get3dsV2MerchantRiskDeliveryTimeframe() != null && !ALLOWED_DELIVERY_TIMEFRAMES.contains(get3dsV2MerchantRiskDeliveryTimeframe())) {
            throw new InvalidParamException("Merchant risk delivery timeframe", ALLOWED_DELIVERY_TIMEFRAMES);
        }
        if (get3dsV2MerchantRiskReorderItemsIndicator() != null && !ALLOWED_REORDER_ITEMS_INDICATORS.contains(get3dsV2MerchantRiskReorderItemsIndicator())) {
            throw new InvalidParamException("Merchant risk reorder items indicator", ALLOWED_REORDER_ITEMS_INDICATORS);
        }
        if (get3dsV2MerchantRiskPreOrderPurchaseIndicator() != null && !ALLOWED_PRE_ORDER_PURCHASE_INDICATORS.contains(get3dsV2MerchantRiskPreOrderPurchaseIndicator())) {
            throw new InvalidParamException("Merchant risk pre order purchase indicator", ALLOWED_PRE_ORDER_PURCHASE_INDICATORS);
        }
        ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
        if (invalidParams.isEmpty()) {
            return get3DSv2MerchantRiskAttrRequestBuilder();
        } else {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }
    }

    RequestBuilder get3DSv2MerchantRiskAttrRequestBuilder();

    HashMap<String, String> get3DSv2MerchantRiskAttrParamsMap();

    GenesisValidator getValidator();
}
