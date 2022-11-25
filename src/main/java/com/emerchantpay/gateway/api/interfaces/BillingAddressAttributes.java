package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.api.validation.RequiredParametersValidator;
import com.emerchantpay.gateway.util.Country;

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

public interface BillingAddressAttributes {

    // Billing Address
    default BillingAddressAttributes setBillingFirstname(String firstname) {
        getBillAddrAttrParamsMap().put("first_name", firstname);
        getBillAddrAttrRequestBuilder().addElement("first_name", firstname);
        return this;
    }

    default BillingAddressAttributes setBillingLastname(String lastname) {
        getBillAddrAttrParamsMap().put("last_name", lastname);
        getBillAddrAttrRequestBuilder().addElement("last_name", lastname);
        return this;
    }

    default BillingAddressAttributes setBillingPrimaryAddress(String primaryAddress) {
        getBillAddrAttrParamsMap().put("address1", primaryAddress);
        getBillAddrAttrRequestBuilder().addElement("address1", primaryAddress);
        return this;
    }

    default BillingAddressAttributes setBillingSecondaryAddress(String secondaryAddress) {
        getBillAddrAttrParamsMap().put("address2", secondaryAddress);
        getBillAddrAttrRequestBuilder().addElement("address2", secondaryAddress);
        return this;
    }

    default BillingAddressAttributes setBillingCity(String city) {
        getBillAddrAttrParamsMap().put("city", city);
        getBillAddrAttrRequestBuilder().addElement("city", city);
        return this;
    }

    default BillingAddressAttributes setBillingZipCode(String zipCode) {
        getBillAddrAttrParamsMap().put("zip_code", zipCode);
        getBillAddrAttrRequestBuilder().addElement("zip_code", zipCode);
        return this;
    }

    default BillingAddressAttributes setBillingState(String state) {
        getBillAddrAttrParamsMap().put("state", state);
        getBillAddrAttrRequestBuilder().addElement("state", state);
        return this;
    }

    default BillingAddressAttributes setBillingCountry(String country) {
        Country c = new Country();

        getBillAddrAttrParamsMap().put("country", c.getIsoCode(country));
        getBillAddrAttrRequestBuilder().addElement("country", c.getIsoCode(country));

        return this;
    }

    default String getBillingFirstName() {
        return getBillAddrAttrParamsMap().get("first_name");
    }

    default String getBillingLastName() {
        return getBillAddrAttrParamsMap().get("last_name");
    }

    default String getBillingPrimaryAddress() {
        return getBillAddrAttrParamsMap().get("address1");
    }

    default String getBillingSecondary() {
        return getBillAddrAttrParamsMap().get("address2");
    }

    default String getBillingCity() {
        return getBillAddrAttrParamsMap().get("city");
    }

    default String getBillingZipCode() {
        return getBillAddrAttrParamsMap().get("zip_code");
    }

    default String getBillingState() {
        return getBillAddrAttrParamsMap().get("state");
    }

    default String getBillingCountryCode() {
        return getBillAddrAttrParamsMap().get("country");
    }

    default HashMap<String, String> getBillingAddressParams() {
        return getBillAddrAttrParamsMap();
    }

    default RequestBuilder buildBillingAddress() {
        return buildBillingAddress(true, "");
    }

    default RequestBuilder buildBillingAddress(Boolean includeEmptyTags) {
        return buildBillingAddress(includeEmptyTags, "billing_address");
    }

    default RequestBuilder buildBillingAddress(Boolean includeEmptyTags, String parent) {
        if (!isBillingAddressValid()) {
            return null;
        }
        String billingAddressXML = getBillAddrAttrRequestBuilder().toXML();
        if(!includeEmptyTags && billingAddressXML.isEmpty()){
            //No empty tags included
            return new RequestBuilder("");
        } else {
            return new RequestBuilder(parent).addElement(billingAddressXML);
        }
    }

    default Boolean isBillingAddressValid() {
        // Required params validator
        RequiredParameters requiredParameters = new RequiredParameters();
        RequiredParametersValidator requiredParametersValidator;

        // Init Required Params Validator
        requiredParametersValidator = new RequiredParametersValidator(requiredParameters
                .getRequiredParametersForAddress((AddressAttributes) this));

        return requiredParametersValidator.isValidRequiredParams();
    }

    RequestBuilder getBillAddrAttrRequestBuilder();

    HashMap<String, String> getBillAddrAttrParamsMap();
}