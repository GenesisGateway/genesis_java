package com.emerchantpay.gateway.api.requests.nonfinancial.reconcile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

public class ReconcileByDateRequest extends Request {

    private String startdate;
    private String enddate;
    private Integer page;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public ReconcileByDateRequest() {
        super();
    }

    public ReconcileByDateRequest setStartDate(String startdate) {
        this.startdate = startdate;
        return this;
    }

    public ReconcileByDateRequest setEndDate(String enddate) {
        this.enddate = enddate;
        return this;
    }

    public ReconcileByDateRequest setPage(Integer page) {
        this.page = page;
        return this;
    }

    @Override
    public String getTransactionType() {
        return TransactionTypes.RECONCILE_BY_DATE;
    }

    @Override
    public String toXML() {
        return buildRequest("reconcile").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        // Set required params
        requiredParams.put(RequiredParameters.startDate, startdate);

        // Validate request
        validator.isValidRequest(requiredParams);

        return new RequestBuilder(root).addElement("start_date", startdate).addElement("end_date", enddate)
                .addElement("page", page);
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }
}
