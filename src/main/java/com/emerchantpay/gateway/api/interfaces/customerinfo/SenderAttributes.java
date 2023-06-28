package com.emerchantpay.gateway.api.interfaces.customerinfo;

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
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface SenderAttributes {

    List<String> idTypes = Arrays.asList(
    "PASSPORT", "NATIONAL_ID", "DRIVING_LICENSE", "SOCIAL_SECURITY", "TAX_ID", "SENIOR_CITIZEN_ID", "BIRTH_CERTIFICATE",
     "VILLAGE_ELDER_ID", "RESIDENT_CARD", "ALIEN_REGISTRATION", "PAN_CARD", "VOTERS_ID", "HEALTH_CARD", "EMPLOYER_ID", "OTHER");

    // Sender Params
    default SenderAttributes setSenderDateOfBirth(String dateOfBirth) {
        if (getValidator().isValidDateUS(dateOfBirth, "sender_date_of_birth")) {
            getSenderAttrParamsMap().put("sender_date_of_birth", dateOfBirth);
            getSenderAttrRequestBuilder().addElement("sender_date_of_birth", dateOfBirth);
        } else {
            throw new InvalidParamException("Invalid format for sender_date_of_birth [" + dateOfBirth
                    + "], allowed format YYYY-MM-DD.");
        }

        return this;
    }

    default String getSenderDateOfBirth() {
        return getSenderAttrParamsMap().get("sender_date_of_birth");
    }

    default SenderAttributes setSenderLastName(String lastName) {
            getSenderAttrParamsMap().put("sender_last_name", lastName);
            getSenderAttrRequestBuilder().addElement("sender_last_name", lastName);

        return this;
    }

    default String getSenderLastName() {
        return getSenderAttrParamsMap().get("sender_last_name");
    }

    default SenderAttributes setSenderFirstName(String dateOfBirth) {
            getSenderAttrParamsMap().put("sender_first_name", dateOfBirth);
            getSenderAttrRequestBuilder().addElement("sender_first_name", dateOfBirth);

        return this;
    }

    default String getSenderFirstName() {
        return getSenderAttrParamsMap().get("sender_first_name");
    }

    default SenderAttributes setSenderCountryIsoCode(String countryIsoCode) {
        if (getValidator().isValidLetters(countryIsoCode,"sender_country_iso_code")) {
            getSenderAttrParamsMap().put("sender_country_iso_code", countryIsoCode);
            getSenderAttrRequestBuilder().addElement("sender_country_iso_code", countryIsoCode);
        } else {
            throw new InvalidParamException("Invalid format for sender_country_iso_code [" + countryIsoCode
                    + "], allowed three-letter country code format.");
        }

        return this;
    }

    default String getSenderCountryIsoCode() {
        return getSenderAttrParamsMap().get("sender_country_iso_code");
    }

    default SenderAttributes setSenderIdNumber(String idNumber) {
        getSenderAttrParamsMap().put("sender_id_number", idNumber);
        getSenderAttrRequestBuilder().addElement("sender_id_number", idNumber);

        return this;
    }

    default String getSenderIdNumber() {
        return getSenderAttrParamsMap().get("sender_id_number");
    }

    default SenderAttributes setSenderNationalityCountryIsoCode(String nCountryIsoCode) {
        getSenderAttrParamsMap().put("sender_nationality_country_iso_code", nCountryIsoCode);
        getSenderAttrRequestBuilder().addElement("sender_nationality_country_iso_code", nCountryIsoCode);

        return this;
    }

    default String getSenderNationalityCountryIsoCode() {
        return getSenderAttrParamsMap().get("sender_nationality_country_iso_code");
    }

    default SenderAttributes setSenderAddress(String address) {
        getSenderAttrParamsMap().put("sender_address", address);
        getSenderAttrRequestBuilder().addElement("sender_address", address);

        return this;
    }

    default String getSenderAddress() {
        return getSenderAttrParamsMap().get("sender_address");
    }

    default SenderAttributes setSenderOccupation(String occupation) {
        getSenderAttrParamsMap().put("sender_occupation", occupation);
        getSenderAttrRequestBuilder().addElement("sender_occupation", occupation);

        return this;
    }

    default String getSenderOccupation() {
        return getSenderAttrParamsMap().get("sender_occupation");
    }

    default SenderAttributes setSenderBeneficiaryRelationship(String beneficiaryRelationship) {
        getSenderAttrParamsMap().put("sender_beneficiary_relationship", beneficiaryRelationship);
        getSenderAttrRequestBuilder().addElement("sender_beneficiary_relationship", beneficiaryRelationship);

        return this;
    }

    default String getSenderBeneficiaryRelationship() {
        return getSenderAttrParamsMap().get("sender_beneficiary_relationship");
    }

    default SenderAttributes setSenderPostalCode(String postalCode) {
        getSenderAttrParamsMap().put("sender_postal_code", postalCode);
        getSenderAttrRequestBuilder().addElement("sender_postal_code", postalCode);

        return this;
    }

    default String getSenderPostalCode() {
        return getSenderAttrParamsMap().get("sender_postal_code");
    }

    default SenderAttributes setSenderCity(String city) {
        getSenderAttrParamsMap().put("sender_city", city);
        getSenderAttrRequestBuilder().addElement("sender_city", city);

        return this;
    }

    default String getSenderCity() {
        return getSenderAttrParamsMap().get("sender_city");
    }

    default SenderAttributes setSenderMsisdn(String msisdn) {
            getSenderAttrParamsMap().put("sender_msisdn", msisdn);
            getSenderAttrRequestBuilder().addElement("sender_msisdn", msisdn);
        return this;
    }

    default String getSenderMsisdn() {
        return getSenderAttrParamsMap().get("sender_msisdn");
    }

    default SenderAttributes setSenderGender(String gender) {
        getSenderAttrParamsMap().put("sender_gender", gender);
        getSenderAttrRequestBuilder().addElement("sender_gender", gender);
        return this;
    }

    default String getSenderGender() {
        return getSenderAttrParamsMap().get("sender_gender");
    }

    default SenderAttributes setSenderIdType(String idType) {
        if (isAllowedListOption(idType, "sender_id_type", idTypes)) {
            getSenderAttrParamsMap().put("sender_id_type", idType);
            getSenderAttrRequestBuilder().addElement("sender_id_type", idType);
        }
        return this;
    }

    default String getSenderIdType() {
        return getSenderAttrParamsMap().get("sender_id_type");
    }

    default SenderAttributes setSenderProvinceState(String provinceState) {
        getSenderAttrParamsMap().put("sender_province_state", provinceState);
        getSenderAttrRequestBuilder().addElement("sender_province_state", provinceState);
        return this;
    }

    default String getSenderProvinceState() {
        return getSenderAttrParamsMap().get("sender_province_state");
    }

    default SenderAttributes setSenderSourceOfFunds(String sourceOfFunds) {
        getSenderAttrParamsMap().put("sender_source_of_funds", sourceOfFunds);
        getSenderAttrRequestBuilder().addElement("sender_source_of_funds", sourceOfFunds);
        return this;
    }

    default String getSenderSourceOfFunds() {
        return getSenderAttrParamsMap().get("sender_source_of_funds");
    }

    default SenderAttributes setSenderCountryOfBirthIsoCode(String countryOfBirthIsoCode) {
        if (getValidator().isValidLetters(countryOfBirthIsoCode, "sender_country_of_birth_iso_code")) {
            getSenderAttrParamsMap().put("sender_country_of_birth_iso_code", countryOfBirthIsoCode);
            getSenderAttrRequestBuilder().addElement("sender_country_of_birth_iso_code", countryOfBirthIsoCode);
        } else {
            throw new InvalidParamException("Invalid format for sender_country_of_birth_iso_code [" + countryOfBirthIsoCode
                    + "], allowed three-letter country code format.");
        }
        return this;
    }

    default String getSenderCountryOfBirthIsoCode() {
        return getSenderAttrParamsMap().get("sender_country_of_birth_iso_code");
    }

    default Boolean isAllowedListOption(String value, String paramName, List list) {
        if (!list.contains(value)) {
            throw new InvalidParamException("Invalid " + paramName + " [" + value + "]. Allowed "
                    + paramName + " are: " + list);

        }

        return true;
    }

    default RequestBuilder buildSenderParams() {
        return getSenderAttrRequestBuilder();
    }
    RequestBuilder getSenderAttrRequestBuilder();

    HashMap<String, String> getSenderAttrParamsMap();

    GenesisValidator getValidator();
}
