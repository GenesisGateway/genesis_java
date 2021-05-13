package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;

import java.util.*;

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

/**
 * The COF indicator.
 * The Credential On File indicator can be used for the following transaction types:
 * Account Verification,
 * Authorize, Authorize3D,
 * Sale, Sale3D,
 * InitRecurringSale, InitRecurringSale3D,
 * Payout
 **/
public interface CredentialOnFileAttributes {

    HashSet<String> credentialOnFileSet = new HashSet<String>() {
        {
            add("initial_customer_initiated");
            add("subsequent_customer_initiated");
            add("merchant_unscheduled");
        }
    };

    default CredentialOnFileAttributes setCredentialOnFile(String credentialOnFile) {
        getCredentialOnFileAttrParamsMap().put("credential_on_file", credentialOnFile);
        getCredentialOnFileAttrRequestBuilder().addElement("credential_on_file", credentialOnFile);
        return this;
    }

    default String getCredentialOnFile() {
        return getCredentialOnFileAttrParamsMap().get("credential_on_file");
    }

    default RequestBuilder getCredentialOnFileAttrRequestBuilderValid() {
        if (getCredentialOnFile() != null && !getCredentialOnFile().isEmpty() &&
                !credentialOnFileSet.contains(getCredentialOnFile())) {
            throw new InvalidParamException("credential_on_file", getCredentialOnFile(), new ArrayList<>(credentialOnFileSet));
        }
        return getCredentialOnFileAttrRequestBuilder();
    }

    default RequestBuilder buildCredentialOnFileParam() {
        return getCredentialOnFileAttrRequestBuilderValid();
    }

    RequestBuilder getCredentialOnFileAttrRequestBuilder();

    HashMap<String, String> getCredentialOnFileAttrParamsMap();
}