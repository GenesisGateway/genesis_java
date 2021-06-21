package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.ArrayList;
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

/**
 * Ucof attribute specific only for Authorize and Sale transactions
 */
public interface UcofAttributes {

    default UcofAttributes setUcofTransactionIdentifier(String cofTransactionIdentifier) {
        getUcofAttrParamsMap().put("credential_on_file_transaction_identifier", cofTransactionIdentifier);
        getUcofAttrRequestBuilder().addElement("credential_on_file_transaction_identifier", cofTransactionIdentifier);
        return this;
    }

    default UcofAttributes setUcofSettlementDate(String cofSettlementDate) {
        getUcofAttrParamsMap().put("credential_on_file_settlement_date", cofSettlementDate);
        getUcofAttrRequestBuilder().addElement("credential_on_file_settlement_date", cofSettlementDate);
        return this;
    }

    default String getUcofTransactionIdentifier() {
        return getUcofAttrParamsMap().get("credential_on_file_transaction_identifier");
    }

    default String getUcofSettlementDate() {
        return getUcofAttrParamsMap().get("credential_on_file_settlement_date");
    }

    default RequestBuilder buildUcofParams(String credentialOnFile) {
        if (getValidator().isValidMonthDate(getUcofSettlementDate(), "credential_on_file_settlement_date")) {
            return getUcofAttrRequestBuilder();
        } else {
            ArrayList<String> invalidParams = new ArrayList<>(getValidator().getInvalidParams());
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }
    }

    RequestBuilder getUcofAttrRequestBuilder();

    HashMap<String, String> getUcofAttrParamsMap();

    GenesisValidator getValidator();
}