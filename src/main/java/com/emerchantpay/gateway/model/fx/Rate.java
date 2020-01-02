package com.emerchantpay.gateway.model.fx;

import com.fasterxml.jackson.databind.JsonNode;

public class Rate {
    private int id;
    private String sourceCurrency;
    private String targetCurrency;
    private String tradingRate;

    public Rate(JsonNode node) {
        this.id = node.get("id").asInt();
        this.sourceCurrency = node.get("source_currency").asText();
        this.targetCurrency = node.get("target_currency").asText();
        this.tradingRate = node.get("trading_rate").asText();
    }
    
    public int getId() {
        return id;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public String getTradingRate() {
        return tradingRate;
    }
}
