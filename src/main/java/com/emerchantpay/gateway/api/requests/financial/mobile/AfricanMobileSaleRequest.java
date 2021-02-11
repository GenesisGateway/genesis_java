package com.emerchantpay.gateway.api.requests.financial.mobile;

import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.requests.financial.AfricanMobileRequest;
import com.emerchantpay.gateway.util.Country;
import com.emerchantpay.gateway.util.Currency;

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

public class AfricanMobileSaleRequest extends AfricanMobileRequest {

    private static final String TRANSACTION_TYPE = TransactionTypes.AFRICAN_MOBILE_SALE;

    private static final Set<String> countries = new HashSet<>(Arrays.asList(
            Country.Ghana.getCode(),
            Country.Kenya.getCode(),
            Country.Uganda.getCode()
    ));

    private static final Map<String, String> currenciesMap = new HashMap<String, String>() {
        {
            put(Country.Ghana.getCode(), Currency.GHS.getCurrency());
            put(Country.Kenya.getCode(), Currency.KES.getCurrency());
            put(Country.Uganda.getCode(), Currency.UGX.getCurrency());
        }
    };

    private static final Map<String, Set<String>> operatorsMap = new HashMap<String, Set<String>>() {
        {
            put(Country.Ghana.getCode(), new HashSet<>(Arrays.asList("VODACOM")));
            put(Country.Kenya.getCode(), new HashSet<>(Arrays.asList("SAFARICOM")));
            put(Country.Uganda.getCode(), new HashSet<>(Arrays.asList("AIRTEL", "MTN")));
        }
    };

    public AfricanMobileSaleRequest() {
        super();
    }

    @Override
    public String getTransactionType() {
        return TRANSACTION_TYPE;
    }

    @Override
    protected Set<String> getCounties() {
        return countries;
    }

    @Override
    protected Map<String, String> getCurrenciesMap() {
        return currenciesMap;
    }

    @Override
    protected Map<String, Set<String>> getOperatorsMap() {
        return operatorsMap;
    }
}