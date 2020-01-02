package com.emerchantpay.gateway.model.fx;

import com.fasterxml.jackson.databind.JsonNode;

public class Tier {
    private int id;
    private String name;
    private String description;
    private String tierId;
    private Boolean enabled;

    public Tier(JsonNode node) {
        this.id = node.get("id").asInt();
        this.name = node.get("name").asText();
        this.description = node.get("description").asText();
        this.tierId = node.get("description").asText();
        this.enabled = node.get("enabled").asBoolean();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTierId() {
        return tierId;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
