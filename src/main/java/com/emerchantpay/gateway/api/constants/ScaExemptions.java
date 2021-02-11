package com.emerchantpay.gateway.api.constants;

public class ScaExemptions {
    public static String EXEMPTION_LOW_VALUE = "low_value";
    public static String EXEMPTION_LOW_RISK = "low_risk";
    public static String EXEMPTION_TRUSTED_MERCHANT = "trusted_merchant";
    public static String EXEMPTION_CORPORATE_PAYMENT = "corporate_payment";
    public static String EXEMPTION_DELEGATED_AUTHENTICATION = "delegated_authentication";
    public static final String EXEMPTION_RECURRING_PAYMENT = "recurring_payment";
    public static final String EXEMPTION_MERCHANT_UNSCHEDULED = "merchant_unscheduled";
    public static final String EXEMPTION_AUTH_NETWORK_OUTAGE = "auth_network_outage";

    public static String[] getAll() {
        return new String[]{EXEMPTION_LOW_VALUE,
                EXEMPTION_LOW_RISK,
                EXEMPTION_TRUSTED_MERCHANT,
                EXEMPTION_CORPORATE_PAYMENT,
                EXEMPTION_DELEGATED_AUTHENTICATION,
                EXEMPTION_RECURRING_PAYMENT,
                EXEMPTION_MERCHANT_UNSCHEDULED,
                EXEMPTION_AUTH_NETWORK_OUTAGE};
    }
}