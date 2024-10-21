package com.emerchantpay.gateway.api.constants;

public enum IdentifierTypes {
    /**
     * General person to person
     */
    GENERAL_PERSON_TO_PERSON("general_person_to_person"),

    /**
     * Person to person card account
     */
    PERSON_TO_PERSON_CARD_ACCOUNT("person_to_person_card_account"),

    /**
     * Own account
     */
    OWN_ACCOUNT("own_account"),

    /**
     * Own credit card bill
     */
    OWN_CREDIT_CARD_BILL("own_credit_card_bill"),

    /**
     * Business disbursement
     */
    BUSINESS_DISBURSEMENT("business_disbursement"),

    /**
     * Government or non profit disbursement
     */
    GOVERNMENT_OR_NON_PROFIT_DISBURSEMENT("government_or_non_profit_disbursement"),

    /**
     * Rapid merchant settlement
     */
    RAPID_MERCHANT_SETTLEMENT("rapid_merchant_settlement"),

    /**
     * General business to business
     */
    GENERAL_BUSINESS_TO_BUSINESS("general_business_to_business"),

    /**
     * Own staged digital wallet account
     */
    OWN_STAGED_DIGITAL_WALLET_ACCOUNT("own_staged_digital_wallet_account"),

    /**
     * Own debit or prepaid card account
     */
    OWN_DEBIT_OR_PREPAID_CARD_ACCOUNT("own_debit_or_prepaid_card_account");

    private final String type;

    IdentifierTypes(String type) {
        this.type = type;
    }

    public static String validate(String identifierType) {
        for (IdentifierTypes it : values()) {
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