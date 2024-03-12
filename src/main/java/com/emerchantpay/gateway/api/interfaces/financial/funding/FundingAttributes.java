package com.emerchantpay.gateway.api.interfaces.financial.funding;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.IdentifierTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

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

public interface FundingAttributes extends ReceiverAttributes {
    default FundingAttributes setIdentifierType(String identifierType) {
        getFundingAttrParamsMap().put("identifier_type", IdentifierTypes.validate(identifierType));
        getFundingAttrRequestBuilder().addElement("identifier_type", IdentifierTypes.validate(identifierType));
        return this;
    }

    default String getIdentifierType() {
        return getFundingAttrParamsMap().get("identifier_type");
    }

    default RequestBuilder buildFundingParams() {
        if (!getFundingAttrParamsMap().isEmpty()){
            getFundingAttrParamsMap().put("receiver", buildReceiverParams().toXML());
            getFundingAttrRequestBuilder().addElement("receiver", buildReceiverParams().toXML());
        }
        if (!getFundingAttrParamsMap().isEmpty() && getIdentifierType() == null) {
            throw new InvalidParamException("Invalid Funding Identifier type.");
        }

        return getFundingAttrRequestBuilder();
    }

    RequestBuilder getFundingAttrRequestBuilder();

    HashMap<String, String> getFundingAttrParamsMap();

    GenesisValidator getValidator();
}