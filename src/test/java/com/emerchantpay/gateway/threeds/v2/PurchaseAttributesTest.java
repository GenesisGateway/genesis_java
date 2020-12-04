package com.emerchantpay.gateway.threeds.v2;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PurchaseAttributesTest {

    private Sale3DRequest sale3DRequest;

    @Before
    public void createThreedsV2Attributes() {
        sale3DRequest = new Sale3DRequest();
    }

    @Test
    public void buildPurchaseAttributes() {
        String category = "prepaid_activation";
        sale3DRequest.set3dsV2PurchaseCategory(category);
        assertEquals(category, sale3DRequest.get3dsV2PurchaseCategory());
        assertTrue(sale3DRequest.build3DSv2PurchaseParams() instanceof RequestBuilder);
    }

    @Test(expected = InvalidParamException.class)
    public void purchaseCategoryError(){
        sale3DRequest.set3dsV2PurchaseCategory("wrong value");
        sale3DRequest.build3DSv2PurchaseParams();
    }
}