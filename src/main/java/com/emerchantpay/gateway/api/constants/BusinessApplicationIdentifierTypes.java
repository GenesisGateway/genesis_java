package com.emerchantpay.gateway.api.constants;

public enum BusinessApplicationIdentifierTypes {
    /**
     * Funds disbursement
     */
    FUNDS_DISBURSEMENT("funds_disbursement"),

    /**
     * Pension disbursement
     */
    PENSION_DISBURSEMENT("pension_disbursement"),

    /**
     * Account to account
     */
    ACCOUNT_TO_ACCOUNT("account_to_account"),

    /**
     * Bank initiated
     */
    BANK_INITIATED("bank_initiated"),

    /**
     * Fund transfer
     */
    FUND_TRANSFER("fund_transfer"),

    /**
     * Person to person
     */
    PERSON_TO_PERSON("person_to_person"),

    /**
     * Prepaid Card load
     */
    PREPAID_CARD_LOAD("prepaid_card_load"),

    /**
     * Wallet transfer
     */
    WALLET_TRANSFER("wallet_transfer"),

    /**
     * Liquid assets
     */
    LIQUID_ASSETS("liquid_assets");


    private final String type;

    BusinessApplicationIdentifierTypes(String type) {
        this.type = type;
    }

    public static String validate(String identifierType) {
        for (BusinessApplicationIdentifierTypes it : values()) {
            if (it.type.equalsIgnoreCase(identifierType)) {
                return it.type;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}