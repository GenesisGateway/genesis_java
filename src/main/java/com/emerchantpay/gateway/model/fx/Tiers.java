package com.emerchantpay.gateway.model.fx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.ArrayList;
import java.util.List;

public class Tiers {
    private List<Tier> tiersList = new ArrayList<Tier>();

    public Tiers(JsonNode node) {
        if (node.getNodeType() == JsonNodeType.ARRAY) {
            for (JsonNode treeNode: node) {
                tiersList.add(new Tier(treeNode));
            }
        } else {
            tiersList.add(new Tier(node));
        }
    }

    public List<Tier> getTiersList() {
        return tiersList;
    }
}
