package com.emerchantpay.gateway.api.interfaces.financial.threeds.v2;

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

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public interface CardHolderAccountAttributes {

    //Static final modifiers are redundant for interface attributes
    //Update Indicator allowed values: current_transaction, less_than_30days, 30_to_60_days, more_than_60days
    ArrayList<String> ALLOWED_UPDATE_INDICATORS = new ArrayList<String>(Arrays.asList("current_transaction", "less_than_30days",
            "30_to_60_days", "more_than_60days"));

    //Password change indicator allowed values: no_change, during_transaction, less_than_30days, 30_to_60_days, more_than_60days
    ArrayList<String> ALLOWED_PASSWORD_CHANGE_INDICATORS = new ArrayList<String>(Arrays.asList("no_change", "during_transaction", "less_than_30days",
            "30_to_60_days", "more_than_60days"));

    //Shipping address usage indicator allowed values: current_transaction, less_than_30days, 30_to_60_days, more_than_60days
    ArrayList<String> ALLOWED_SHIPPING_ADDRESS_USAGE_INDICATORS = new ArrayList<String>(Arrays.asList("current_transaction", "less_than_30days",
            "30_to_60_days", "more_than_60days"));

    //Suspicious activity indicator allowed values: no_suspicious_observed, suspicious_observed
    ArrayList<String> ALLOWED_SUSPICIOUS_ACTIVITY_INDICATORS = new ArrayList<String>(Arrays.asList("no_suspicious_observed", "suspicious_observed"));

    //Registration indicator allowed values: guest_checkout, current_transaction, less_than_30days, 30_to_60_days, more_than_60days
    ArrayList<String> ALLOWED_REGISTRATION_INDICATORS = new ArrayList<String>(Arrays.asList("guest_checkout", "current_transaction",
            "less_than_30days", "30_to_60_days", "more_than_60days"));

    default CardHolderAccountAttributes set3dsV2CardHolderAccountCreationDate(String creationDate) {
        if(getValidator().isValidDate(creationDate, "creation_date")) {
            get3DSv2CardHolderAttrParamsMap().put("creation_date", creationDate);
            get3DSv2CardHolderAttrRequestBuilder().addElement("creation_date", creationDate);
        }
        return this;
    }

    default String get3dsV2CardHolderAccountCreationDate() {
        return get3DSv2CardHolderAttrParamsMap().get("creation_date");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountLastChangeDate(String lastChangeDate) {
        if(getValidator().isValidDate(lastChangeDate, "last_change_date")) {
            get3DSv2CardHolderAttrParamsMap().put("last_change_date", lastChangeDate);
            get3DSv2CardHolderAttrRequestBuilder().addElement("last_change_date", lastChangeDate);
        }
        return this;
    }

    default String get3dsV2CardHolderAccountLastChangeDate() {
        return get3DSv2CardHolderAttrParamsMap().get("last_change_date");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountPasswordChangeDate(String passwordChangeDate) {
        if(getValidator().isValidDate(passwordChangeDate, "password_change_date")) {
            get3DSv2CardHolderAttrParamsMap().put("password_change_date", passwordChangeDate);
            get3DSv2CardHolderAttrRequestBuilder().addElement("password_change_date", passwordChangeDate);
        }
        return this;
    }

    default String get3dsV2CardHolderAccountPasswordChangeDate() {
        return get3DSv2CardHolderAttrParamsMap().get("password_change_date");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountShippingAddressDateFirstUsed(String shippingAddressDateFirstUsed) {
        if(getValidator().isValidDate(shippingAddressDateFirstUsed, "shipping_address_date_first_used")) {
            get3DSv2CardHolderAttrParamsMap().put("shipping_address_date_first_used", shippingAddressDateFirstUsed);
            get3DSv2CardHolderAttrRequestBuilder().addElement("shipping_address_date_first_used", shippingAddressDateFirstUsed);
        }
        return this;
    }

    default String get3dsV2CardHolderAccountShippingAddressDateFirstUsed() {
        return get3DSv2CardHolderAttrParamsMap().get("shipping_address_date_first_used");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountRegistrationDate(String registrationDate) {
        if(getValidator().isValidDate(registrationDate, "registration_date")) {
            get3DSv2CardHolderAttrParamsMap().put("registration_date", registrationDate);
            get3DSv2CardHolderAttrRequestBuilder().addElement("registration_date", registrationDate);
        }
        return this;
    }

    default String get3dsV2CardHolderAccountRegistrationDate() {
        return get3DSv2CardHolderAttrParamsMap().get("registration_date");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountUpdateIndicator(String updateIndicator) {
        get3DSv2CardHolderAttrParamsMap().put("update_indicator", updateIndicator);
        get3DSv2CardHolderAttrRequestBuilder().addElement("update_indicator", updateIndicator);
        return this;
    }

    default String get3dsV2CardHolderAccountUpdateIndicator() {
        return get3DSv2CardHolderAttrParamsMap().get("update_indicator");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountPasswordChangeIndicator(String passwordChangeIndicator) {
        get3DSv2CardHolderAttrParamsMap().put("password_change_indicator", passwordChangeIndicator);
        get3DSv2CardHolderAttrRequestBuilder().addElement("password_change_indicator", passwordChangeIndicator);
        return this;
    }

    default String get3dsV2CardHolderAccountPasswordChangeIndicator() {
        return get3DSv2CardHolderAttrParamsMap().get("password_change_indicator");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountShippingAddressUsageIndicator(String shippingAddressUsageIndicator) {
        get3DSv2CardHolderAttrParamsMap().put("shipping_address_usage_indicator", shippingAddressUsageIndicator);
        get3DSv2CardHolderAttrRequestBuilder().addElement("shipping_address_usage_indicator", shippingAddressUsageIndicator);
        return this;
    }

    default String get3dsV2CardHolderAccountShippingAddressUsageIndicator() {
        return get3DSv2CardHolderAttrParamsMap().get("shipping_address_usage_indicator");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountSuspiciousActivityIndicator(String suspiciousActivityIndicator) {
        get3DSv2CardHolderAttrParamsMap().put("suspicious_activity_indicator", suspiciousActivityIndicator);
        get3DSv2CardHolderAttrRequestBuilder().addElement("suspicious_activity_indicator", suspiciousActivityIndicator);
        return this;
    }

    default String get3dsV2CardHolderAccountSuspiciousActivityIndicator() {
        return get3DSv2CardHolderAttrParamsMap().get("suspicious_activity_indicator");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountRegistrationIndicator(String registrationIndicator) {
        get3DSv2CardHolderAttrParamsMap().put("registration_indicator", registrationIndicator);
        get3DSv2CardHolderAttrRequestBuilder().addElement("registration_indicator", registrationIndicator);
        return this;
    }

    default String get3dsV2CardHolderAccountRegistrationIndicator() {
        return get3DSv2CardHolderAttrParamsMap().get("registration_indicator");
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountTransactionsActivityLast24Hours(Integer transactionsActivityLast24Hours) {
        get3DSv2CardHolderAttrParamsMap().put("transactions_activity_last_24_hours", String.valueOf(transactionsActivityLast24Hours));
        get3DSv2CardHolderAttrRequestBuilder().addElement("transactions_activity_last_24_hours", transactionsActivityLast24Hours);
        return this;
    }

    default Integer get3dsV2CardHolderAccountTransactionsActivityLast24Hours() {
        String transactionsActivityLast24HoursStr = get3DSv2CardHolderAttrParamsMap().get("transactions_activity_last_24_hours");
        if(transactionsActivityLast24HoursStr != null){
            return Integer.parseInt(transactionsActivityLast24HoursStr);
        } else{
            return null;
        }
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountTransactionsActivityPreviousYear(Integer transactionsActivityPreviousYear) {
        get3DSv2CardHolderAttrParamsMap().put("transactions_activity_previous_year", String.valueOf(transactionsActivityPreviousYear));
        get3DSv2CardHolderAttrRequestBuilder().addElement("transactions_activity_previous_year", transactionsActivityPreviousYear);
        return this;
    }

    default Integer get3dsV2CardHolderAccountTransactionsActivityPreviousYear() {
        String transactionsActivityPreviousYearStr = get3DSv2CardHolderAttrParamsMap().get("transactions_activity_previous_year");
        if(transactionsActivityPreviousYearStr != null){
            return Integer.parseInt(transactionsActivityPreviousYearStr);
        } else{
            return null;
        }
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountProvisionAttemptsLast24Hours(Integer provisionAttemptsLast24Hours) {
        get3DSv2CardHolderAttrParamsMap().put("provision_attempts_last_24_hours", String.valueOf(provisionAttemptsLast24Hours));
        get3DSv2CardHolderAttrRequestBuilder().addElement("provision_attempts_last_24_hours", provisionAttemptsLast24Hours);
        return this;
    }

    default Integer get3dsV2CardHolderAccountProvisionAttemptsLast24Hours() {
        String provisionAttemptsLast24HoursStr = get3DSv2CardHolderAttrParamsMap().get("provision_attempts_last_24_hours");
        if(provisionAttemptsLast24HoursStr != null){
            return Integer.parseInt(provisionAttemptsLast24HoursStr);
        } else{
            return null;
        }
    }

    default CardHolderAccountAttributes set3dsV2CardHolderAccountPurchasesCountLast6Months(Integer purchasesCountLast6Months) {
        get3DSv2CardHolderAttrParamsMap().put("purchases_count_last_6_months", String.valueOf(purchasesCountLast6Months));
        get3DSv2CardHolderAttrRequestBuilder().addElement("purchases_count_last_6_months", purchasesCountLast6Months);
        return this;
    }

    default Integer get3dsV2CardHolderAccountPurchasesCountLast6Months() {
        String purchasesCountLast6MonthsStr = get3DSv2CardHolderAttrParamsMap().get("purchases_count_last_6_months");
        if(purchasesCountLast6MonthsStr != null){
            return Integer.parseInt(purchasesCountLast6MonthsStr);
        } else{
            return null;
        }
    }

    default RequestBuilder build3DSv2CardHolderParams() {

        if (get3dsV2CardHolderAccountUpdateIndicator() != null && !ALLOWED_UPDATE_INDICATORS.contains(get3dsV2CardHolderAccountUpdateIndicator())) {
            throw new InvalidParamException("3DS card holder account update indicator", ALLOWED_UPDATE_INDICATORS);
        }
        if (get3dsV2CardHolderAccountPasswordChangeIndicator() != null && !ALLOWED_PASSWORD_CHANGE_INDICATORS.contains(get3dsV2CardHolderAccountPasswordChangeIndicator())) {
            throw new InvalidParamException("3DS card holder password change indicator", ALLOWED_PASSWORD_CHANGE_INDICATORS);
        }
        if (get3dsV2CardHolderAccountShippingAddressUsageIndicator() != null && !ALLOWED_SHIPPING_ADDRESS_USAGE_INDICATORS.contains(get3dsV2CardHolderAccountShippingAddressUsageIndicator())) {
            throw new InvalidParamException("3DS card holder shipping address usage indicator", ALLOWED_SHIPPING_ADDRESS_USAGE_INDICATORS);
        }
        if (get3dsV2CardHolderAccountSuspiciousActivityIndicator() != null && !ALLOWED_SUSPICIOUS_ACTIVITY_INDICATORS.contains(get3dsV2CardHolderAccountSuspiciousActivityIndicator())) {
            throw new InvalidParamException("3DS card holder suspicious activity indicator", ALLOWED_SUSPICIOUS_ACTIVITY_INDICATORS);
        }
        if (get3dsV2CardHolderAccountRegistrationIndicator() != null && !ALLOWED_REGISTRATION_INDICATORS.contains(get3dsV2CardHolderAccountRegistrationIndicator())) {
            throw new InvalidParamException("3DS card holder registration indicator", ALLOWED_REGISTRATION_INDICATORS);
        }

        ArrayList<String> invalidParams = new ArrayList<String>(getValidator().getInvalidParams());
        if (invalidParams.isEmpty()) {
            return get3DSv2CardHolderAttrRequestBuilder();
        } else {
            getValidator().clearInvalidParams();
            throw new RegexException(invalidParams);
        }
    }

    RequestBuilder get3DSv2CardHolderAttrRequestBuilder();

    HashMap<String, String> get3DSv2CardHolderAttrParamsMap();

    GenesisValidator getValidator();
}