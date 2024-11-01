package com.emerchantpay.gateway.api.requests.financial.mobile;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.Arrays;
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

public class GooglePayPaymentTokenRequest extends Request {

    private static final String INTERMEDIATE_SIGNING_KEY = "intermediateSigningKey";

    //Payment Token attributes
    //Version information about the payment token.

    @Getter
    private String tokenSignedKey;

    @Getter
    private String tokenSignedMessage;

    @Getter
    private String tokenProtocolVersion;
    //Signature of the payment and header data.
    @Getter
    private String tokenSignature;

    //Signatures of the payment and header data.
    @Getter
    private String[] tokenSignatures;
    private String tokenTransactionIdentifier;

    @Getter
    private String paymentToken;

    // Required params
    private HashMap<String, String> requiredParams = new HashMap<>();

    // GenesisValidator
    private GenesisValidator validator = new GenesisValidator();

    public GooglePayPaymentTokenRequest() {
        super();
    }

    public GooglePayPaymentTokenRequest setSignedTokenKey(String signedTokenKey) {
        this.tokenSignedKey = signedTokenKey;
        return this;
    }

    public GooglePayPaymentTokenRequest setSignedTokenMessage(String signedTokenMessage) {
        this.tokenSignedMessage = signedTokenMessage;
        return this;
    }

    public GooglePayPaymentTokenRequest setTokenProtocolVersion(String tokenProtocolVersion) {
        this.tokenProtocolVersion = tokenProtocolVersion;
        return this;
    }

    public GooglePayPaymentTokenRequest setTokenSignature(String tokenSignature) {
        this.tokenSignature = tokenSignature;
        return this;
    }

    public GooglePayPaymentTokenRequest setTokenSignatures(String[] tokenSignatures) {
        this.tokenSignatures = tokenSignatures;
        return this;
    }

    public GooglePayPaymentTokenRequest setTokenTransactionIdentifier(String tokenTransactionIdentifier) {
        this.tokenTransactionIdentifier = tokenTransactionIdentifier;
        return this;
    }


    public GooglePayPaymentTokenRequest setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
        return this;
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
                requiredParams.put(RequiredParameters.tokenSignature, tokenSignature);
                requiredParams.put(RequiredParameters.tokenSignatures, Arrays.toString(tokenSignatures));
                requiredParams.put(RequiredParameters.tokenSignedKey, tokenSignedKey);
                requiredParams.put(RequiredParameters.tokenSignedMessage, tokenSignedMessage);
                requiredParams.put(RequiredParameters.tokenProtocolVersion, tokenProtocolVersion);
        }

        // Validate request
        validator.isValidRequest(requiredParams);

        if (getPaymentToken() != null) {

            RequestBuilder requestBuilder = new RequestBuilder("")
                    .addElement(RequiredParameters.tokenSignature, tokenSignature)
                    .addElement(RequiredParameters.tokenProtocolVersion, tokenProtocolVersion)
                    .addElement(RequiredParameters.tokenSignedMessage, tokenSignedMessage);

            RequestBuilder intermediateSigningKeyRequestBuilder = new RequestBuilder(INTERMEDIATE_SIGNING_KEY)
                    .addElement(RequiredParameters.tokenSignedKey, tokenSignedKey)
                    .addElement(RequiredParameters.tokenSignatures, tokenSignatures);

            return new RequestBuilder(root).addElement(RequiredParameters.tokenTransactionIdentifier, tokenTransactionIdentifier)
                    .addElement(requestBuilder.toJSON())
                    .addElement(intermediateSigningKeyRequestBuilder.toJSON());
        } else {
           return new RequestBuilder("payment_token").addElement(getPaymentToken());
        }
    }

    public GooglePayPaymentTokenRequest setPaymentTokenJson(String paymentTokenJson) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        //IOException will be thrown if invalid JSON
        JsonNode root = mapper.readTree(paymentTokenJson);

        this.setTokenTransactionIdentifier(getNodeText(root.get(RequiredParameters.tokenTransactionIdentifier)));

        JsonNode methodName = root.get("");
        if(methodName != null){
            this.setTokenSignature(getNodeText(methodName.get(RequiredParameters.tokenSignature)));
            this.setTokenProtocolVersion(getNodeText(methodName.get(RequiredParameters.tokenProtocolVersion)));
            this.setSignedTokenMessage(getNodeText(methodName.get(RequiredParameters.tokenSignedMessage)));
        }

        JsonNode intermediateSigningKeyNode = root.get(INTERMEDIATE_SIGNING_KEY);
        if(intermediateSigningKeyNode != null){
            this.setSignedTokenKey(getNodeText(intermediateSigningKeyNode.get(RequiredParameters.tokenSignedKey)));
            this.setTokenSignatures(new String[]{getNodeText(intermediateSigningKeyNode.get(RequiredParameters.tokenSignatures))});
        }

        return this;
    }

    private String getNodeText(JsonNode node) {
        if(node == null){
            return null;
        }else{
            return node.asText();
        }
    }
}
