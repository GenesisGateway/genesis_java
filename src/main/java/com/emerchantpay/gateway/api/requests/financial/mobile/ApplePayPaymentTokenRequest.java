package com.emerchantpay.gateway.api.requests.financial.mobile;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.HashMap;


/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

public class ApplePayPaymentTokenRequest extends Request {

    private static final String PAYMENT_METHOD = "paymentMethod";
    private static final String PAYMENT_DATA = "paymentData";
    private static final String PAYMENT_DATA_HEADER = "header";

    //Payment Token attributes
    //Version information about the payment token.
    private String tokenVersion;
    //Encrypted payment data.
    private String tokenData;
    //Signature of the payment and header data.
    private String tokenSignature;
    //Ephemeral public key bytes.
    private String tokenEphemeralPublicKey;
    //Hash of encoded public key bytes of the merchant’s certificate.
    private String tokenPublicKeyHash;
    //Transaction identifier, generated on the device
    private String tokenTransactionId;
    //describes the card
    private String tokenDisplayName;
    //Describes the payment network backing the card
    private String tokenNetwork;
    //Representing the card’s type
    private String tokenType;
    //A unique identifier for this payment.
    private String tokenTransactionIdentifier;

    private String paymentToken;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<String, String>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public ApplePayPaymentTokenRequest() {
        super();
    }

    public String getTokenVersion() {
        return tokenVersion;
    }

    public ApplePayPaymentTokenRequest setTokenVersion(String tokenVersion) {
        this.tokenVersion = tokenVersion;
        return this;
    }

    public String getTokenData() {
        return tokenData;
    }

    public ApplePayPaymentTokenRequest setTokenData(String tokenData) {
        this.tokenData = tokenData;
        return this;
    }

    public String getTokenSignature() {
        return tokenSignature;
    }

    public ApplePayPaymentTokenRequest setTokenSignature(String tokenSignature) {
        this.tokenSignature = tokenSignature;
        return this;
    }

    public String getTokenEphemeralPublicKey() {
        return tokenEphemeralPublicKey;
    }

    public ApplePayPaymentTokenRequest setTokenEphemeralPublicKey(String tokenEphemeralPublicKey) {
        this.tokenEphemeralPublicKey = tokenEphemeralPublicKey;
        return this;
    }

    public String getTokenPublicKeyHash() {
        return tokenPublicKeyHash;
    }

    public ApplePayPaymentTokenRequest setTokenPublicKeyHash(String tokenPublicKeyHash) {
        this.tokenPublicKeyHash = tokenPublicKeyHash;
        return this;
    }

    public String getTokenTransactionId() {
        return tokenTransactionId;
    }

    public ApplePayPaymentTokenRequest setTokenTransactionId(String tokenTransactionId) {
        this.tokenTransactionId = tokenTransactionId;
        return this;
    }

    public String getTokenDisplayName() {
        return tokenDisplayName;
    }

    public ApplePayPaymentTokenRequest setTokenDisplayName(String tokenDisplayName) {
        this.tokenDisplayName = tokenDisplayName;
        return this;
    }

    public String getTokenNetwork() {
        return tokenNetwork;
    }

    public ApplePayPaymentTokenRequest setTokenNetwork(String tokenNetwork) {
        this.tokenNetwork = tokenNetwork;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public ApplePayPaymentTokenRequest setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getTokenTransactionIdentifier() {
        return tokenTransactionIdentifier;
    }

    public ApplePayPaymentTokenRequest setTokenTransactionIdentifier(String tokenTransactionIdentifier) {
        this.tokenTransactionIdentifier = tokenTransactionIdentifier;
        return this;
    }

    public ApplePayPaymentTokenRequest setPaymentTokenJson(String paymentTokenJson) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        //IOException will be thrown if invalid JSON
        JsonNode root = mapper.readTree(paymentTokenJson);

        this.setTokenTransactionIdentifier(getNodeText(root.get(RequiredParameters.tokenTransactionIdentifier)));

        JsonNode paymentMethodNode = root.get(PAYMENT_METHOD);
        if(paymentMethodNode != null){
            this.setTokenDisplayName(getNodeText(paymentMethodNode.get(RequiredParameters.tokenDisplayName)));
            this.setTokenNetwork(getNodeText(paymentMethodNode.get(RequiredParameters.tokenNetwork)));
            this.setTokenType(getNodeText(paymentMethodNode.get(RequiredParameters.tokenType)));
        }

        JsonNode paymentDataNode = root.get(PAYMENT_DATA);
        if(paymentDataNode != null){
            this.setTokenVersion(getNodeText(paymentDataNode.get(RequiredParameters.tokenVersion)));
            this.setTokenData(getNodeText(paymentDataNode.get(RequiredParameters.tokenData)));
            this.setTokenSignature(getNodeText(paymentDataNode.get(RequiredParameters.tokenSignature)));
        }

        JsonNode paymentDataHeaderNode = root.get(PAYMENT_DATA).get(PAYMENT_DATA_HEADER);
        if(paymentDataHeaderNode != null){
            this.setTokenEphemeralPublicKey(getNodeText(paymentDataHeaderNode.get(RequiredParameters.tokenEphemeralPublicKey)));
            this.setTokenPublicKeyHash(getNodeText(paymentDataHeaderNode.get(RequiredParameters.tokenPublicKeyHash)));
            this.setTokenTransactionId(getNodeText(paymentDataHeaderNode.get(RequiredParameters.tokenTransactionId)));
        }

        return this;
    }

    public ApplePayPaymentTokenRequest setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
        return this;
    }

    public String getPaymentToken() {
        return this.paymentToken;
    }

    @Override
    public String toXML() {
        return buildRequest("payment_token").toXML();
    }

    @Override
    public String toJSON(){
        return StringEscapeUtils.unescapeJava(buildRequest("payment_token").toJSON());
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    public RequestBuilder buildRequest(String root) {
        // Set required params
        if (getPaymentToken() != null) {
            requiredParams.put(RequiredParameters.paymentToken, paymentToken);
        } else {
            requiredParams.put(RequiredParameters.tokenVersion, tokenVersion);
            requiredParams.put(RequiredParameters.tokenData, tokenData);
            requiredParams.put(RequiredParameters.tokenSignature, tokenSignature);
            requiredParams.put(RequiredParameters.tokenEphemeralPublicKey, tokenEphemeralPublicKey);
            requiredParams.put(RequiredParameters.tokenPublicKeyHash, tokenPublicKeyHash);
            requiredParams.put(RequiredParameters.tokenTransactionId, tokenTransactionId);
            requiredParams.put(RequiredParameters.tokenDisplayName, tokenDisplayName);
            requiredParams.put(RequiredParameters.tokenNetwork, tokenNetwork);
            requiredParams.put(RequiredParameters.tokenType, tokenType);
            requiredParams.put(RequiredParameters.tokenTransactionIdentifier, tokenTransactionIdentifier);
        }


        // Validate request
        validator.isValidRequest(requiredParams);

        RequestBuilder paymentDataHeaderRequestBuilder = new RequestBuilder(PAYMENT_DATA_HEADER)
                .addElement(RequiredParameters.tokenEphemeralPublicKey, tokenEphemeralPublicKey)
                .addElement(RequiredParameters.tokenPublicKeyHash, tokenPublicKeyHash)
                .addElement(RequiredParameters.tokenTransactionId, tokenTransactionId);

        RequestBuilder paymentDataRequestBuilder = new RequestBuilder(PAYMENT_DATA)
                .addElement(RequiredParameters.tokenVersion, tokenVersion)
                .addElement(RequiredParameters.tokenData, tokenData)
                .addElement(RequiredParameters.tokenSignature, tokenSignature)
                .addElement(paymentDataHeaderRequestBuilder.toXML());

        RequestBuilder paymentMethodRequestBuilder = new RequestBuilder(PAYMENT_METHOD)
                .addElement(RequiredParameters.tokenDisplayName, tokenDisplayName)
                .addElement(RequiredParameters.tokenNetwork, tokenNetwork)
                .addElement(RequiredParameters.tokenType, tokenType);

        return new RequestBuilder(root).addElement(RequiredParameters.tokenTransactionIdentifier, tokenTransactionIdentifier)
                .addElement(paymentDataRequestBuilder.toXML())
                .addElement(paymentMethodRequestBuilder.toXML());
    }

    private String getNodeText(JsonNode node){
        if(node == null){
            return null;
        }else{
            return node.asText();
        }
    }
}
