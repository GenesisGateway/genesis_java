package com.emerchantpay.gateway.model.klarna;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;

import java.util.List;
import java.util.Map;

public class MerchantData extends Request {

    private RequestBuilder builder = new RequestBuilder("merchant_data");

    private String marketplaceSellerInfo;

    public MerchantData setMarketSellerInfo(String marketplaceSellerInfo) {
        this.marketplaceSellerInfo = marketplaceSellerInfo;
        return this;
    }

    public MerchantData addMerchantDataParam(String key, String value) {
        builder.addElement(key, value);
        return this;
    }

    public MerchantData addMerchantDataParams(Map<String, String> merchantDataMap) {
        for (Map.Entry<String, String> entry : merchantDataMap.entrySet()) {
            builder.addElement(entry.getKey(), entry.getValue());
        }

        return this;
    }

    @Override
    public String toXML() {
        return buildRequest().toXML();
    }

    @Override
    public String toQueryString() {
        return buildRequest().toQueryString();
    }

    protected RequestBuilder buildRequest() {
        return builder.addElement("marketplace_seller_info", marketplaceSellerInfo);
    }

    public List<Map.Entry<String, Object>> getMerchantDataList() {
        return builder.getElements();
    }
}
