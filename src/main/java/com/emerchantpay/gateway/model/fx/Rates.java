package com.emerchantpay.gateway.model.fx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class Rates {
    private List<Rate> ratesList = new ArrayList<Rate>();

    public Rates(JsonNode node) {
        if (node.getNodeType() == JsonNodeType.ARRAY) {
            for (JsonNode treeNode : node) {
                ratesList.add(new Rate(treeNode));
            }
        } else {
            ratesList.add(new Rate(node));
        }
    }

    public List<Rate> getRatesList() {
        return ratesList;
    }
}
