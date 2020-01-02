package com.emerchantpay.gateway.model;

import com.emerchantpay.gateway.api.constants.ConsumerStates;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ResponseException;
import com.emerchantpay.gateway.util.NodeWrapper;

import java.util.List;

public class Consumer {

    private String rawDocument;
    private String consumerId;
    private String status;
    private Integer code;
    private String message;
    private String technicalMessage;
    private String email;
    private String tokenType;
    private Integer total;
    private List<String> cardsList;

    public Consumer(NodeWrapper node) {

        this.rawDocument = node.toString();
        this.consumerId = node.findString("consumer_id");
        this.status = node.findString("status");
        this.code = node.findInteger("code");
        this.message = node.findString("message");
        this.technicalMessage = node.findString("technical_message");
        this.email = node.findString("email");
        this.tokenType = node.findString("token_type");
        this.total = node.findInteger("total");
        this.cardsList = node.findAllStrings("card");

        if (ConsumerStates.ERROR.equals(getStatus())) {
            if (ErrorCodes.getErrorDescription(getCode()) != null) {
                throw new ResponseException(getCode(), ErrorCodes.getErrorDescription(getCode()));
            } else {
                throw new ResponseException(getCode(), getTechnicalMessage());
            }
        }
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }

    public String getEmail() {
        return email;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getTotal() {
        return total;
    }

    public List<String> getCardsData() {
        return cardsList;
    }

    public String getDocument() {
        return rawDocument;
    }
}
