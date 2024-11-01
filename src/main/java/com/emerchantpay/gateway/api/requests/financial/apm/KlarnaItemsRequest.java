package com.emerchantpay.gateway.api.requests.financial.apm;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.model.klarna.KlarnaItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public class KlarnaItemsRequest extends FinancialRequest {

    private ArrayList<KlarnaItem> klarnaItemsList = new ArrayList<KlarnaItem>();

    public KlarnaItemsRequest() {
        super();
    }

    public KlarnaItemsRequest(KlarnaItem klarnaItem) {
        super();

        this.klarnaItemsList.add(klarnaItem);
    }

    public KlarnaItemsRequest(ArrayList<KlarnaItem> klarnaItemsList) {
        super();

        this.klarnaItemsList.addAll(klarnaItemsList);
    }

    public KlarnaItemsRequest addKlarnaItem(KlarnaItem klarnaItem) {
        this.klarnaItemsList.add(klarnaItem);
        return this;
    }

    public KlarnaItemsRequest addKlarnaItems(ArrayList<KlarnaItem> klarnaItems) {
        this.klarnaItemsList.addAll(klarnaItems);
        return this;
    }

    @Override
    public String toXML() {
        return buildRequest("items").toXML();
    }

    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        RequestBuilder builder = new RequestBuilder(root);

        for (KlarnaItem item : klarnaItemsList) {
            builder.addElement(item);
        }

        return builder;
    }

    public BigDecimal getTotalAmounts() {
        BigDecimal totalAmounts = new BigDecimal(0);

        for (KlarnaItem item : klarnaItemsList) {
            totalAmounts = totalAmounts.add(new BigDecimal(item.getTotalAmount().doubleValue()));
        }

        return totalAmounts;
    }

    public BigDecimal getTotalTaxAmounts() {
        BigDecimal totalTaxAmounts = new BigDecimal(0);

        for (KlarnaItem item : klarnaItemsList) {
            totalTaxAmounts = totalTaxAmounts.add(new BigDecimal(item.getTotalTaxAmount().doubleValue()));
        }

        return totalTaxAmounts;
    }

    public ArrayList<KlarnaItem> getItems() {
        return klarnaItemsList;
    }
}
