package com.emerchantpay.gateway.model;

import com.fasterxml.jackson.databind.JsonNode;

public class ScaChecker {

    private String card_number;
    private Boolean moto;
    private Boolean mit;
    private Boolean recurring;
    private String transaction_exemption;
    private Integer transaction_amount;
    private String transaction_currency;

    private String rawDocument;
    private String scaRequired;
    private String scaResultReason;
    private String exemption;

    public ScaChecker() {

    }

    public ScaChecker(String card_number,
                      Boolean moto,
                      Boolean mit,
                      Boolean recurring,
                      String transaction_exemption,
                      Integer transaction_amount,
                      String transaction_currency) {
        this.card_number = card_number;
        this.moto = moto;
        this.mit = mit;
        this.recurring = recurring;
        this.transaction_exemption = transaction_exemption;
        this.transaction_amount = transaction_amount;
        this.transaction_currency = transaction_currency;
    }

    public ScaChecker(JsonNode node) {
        this.rawDocument = node.toString();
        this.scaRequired = node.get("sca_required").asText();
        this.scaResultReason = node.get("sca_result_reason").asText();
        this.exemption = node.get("exemption").asText();
    }

    public String getScaRequired() {
        return scaRequired;
    }

    public String getScaResultReason() {
        return scaResultReason;
    }

    public String getExemption() {
        return exemption;
    }

    public String getDocument() {
        return rawDocument;
    }
}
