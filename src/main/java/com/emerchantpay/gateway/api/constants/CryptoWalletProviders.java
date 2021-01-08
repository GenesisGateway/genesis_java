package com.emerchantpay.gateway.api.constants;

import java.util.HashMap;
import java.util.Map;

public enum CryptoWalletProviders {
    BITGO("BitGo"), UPHOLD("Uphold"), CIRCLE("Circle"), COINBASE("Coinbase"), GDAX("GDax"),
    GEMINI("Gemini"), ITBIT("ITBit"), KRAKEN("Kraken");

    private final String name;

    private static final Map<String, CryptoWalletProviders> MAP_CRYPTO_PROVIDERS = new HashMap();

    static {
        for (CryptoWalletProviders cwp : values()) {
            MAP_CRYPTO_PROVIDERS.put(cwp.name, cwp);
        }
    }

    CryptoWalletProviders(String name) {
        this.name = name;
    }

    public static CryptoWalletProviders valueOfName(String name) {
        for (CryptoWalletProviders cwp : values()) {
            if (cwp.name.equals(name)) {
                return cwp;
            }
        }
        return null;
    }

    public static String validate(String provider) {
        if (provider == null || "other".equalsIgnoreCase(provider)) {
            return provider;
        }

        for (CryptoWalletProviders cwp : values()) {
            if (cwp.name.equalsIgnoreCase(provider)) {
                return cwp.name;
            }
        }
        return "other";
    }

}
