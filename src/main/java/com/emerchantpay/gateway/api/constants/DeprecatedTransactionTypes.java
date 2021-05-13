package com.emerchantpay.gateway.api.constants;

import com.emerchantpay.gateway.api.exceptions.DeprecatedMethodException;

import java.util.HashMap;
import java.util.Map;

public class DeprecatedTransactionTypes {

    private static final Map<String, String> deprecatedTypes = new HashMap<String, String>() {
        {
            put("avs", "AVS");
            put("abn_ideal", "ABNiDEAL");
            put("inpay", "InPay");
            put("paybyvoucher_sale", "PayByVoucherSale");
            put("trustly_withdrawal", "TrustlyWithdrawal");
        }
    };

    public static void validate(String requestType) {
        if (deprecatedTypes.containsKey(requestType)) {
            throw new DeprecatedMethodException(deprecatedTypes.get(requestType));
        }
    }
}