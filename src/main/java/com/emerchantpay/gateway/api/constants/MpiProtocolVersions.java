package com.emerchantpay.gateway.api.constants;

public class MpiProtocolVersions {
    public static String PROTOCOL_VERSION_1 = "1";
    public static String PROTOCOL_VERSION_2 = "2";

    public static String[] getAll() {
        return new String[]{PROTOCOL_VERSION_1, PROTOCOL_VERSION_2};
    }
}
