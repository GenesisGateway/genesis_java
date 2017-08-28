package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
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

    HashMap<String, String> addressMap = new HashMap<String, String>();

    // Billing Address
    default void setBillingFirstname(String firstname) {
        addressMap.put("first_name", firstname);
    }

    default void setBillingLastname(String lastname) {
        addressMap.put("last_name", lastname);
    }

    default void setBillingPrimaryAddress(String primaryAddress) {
        addressMap.put("address1", primaryAddress);
    }

    default void setBillingSecondaryAddress(String secondaryAddress) {
        addressMap.put("address2", secondaryAddress);
    }

    default void setBillingCity(String city) {
        addressMap.put("city", city);
    }

    default void setBillingZipCode(String zipCode) {
        addressMap.put("zip_code", zipCode);
    }

    default void setBillingState(String state) {
        addressMap.put("state", state);
    }

    default void setBillingCountry(String country) {
        Country c = new Country();

        addressMap.put("country", c.getIsoCode(country));
    }

    default RequestBuilder buildBillingAddress() {

        return new RequestBuilder("").addElement("first_name", addressMap.get("first_name"))
                .addElement("last_name", addressMap.get("last_name"))
                .addElement("address1", addressMap.get("address1"))
                .addElement("address2", addressMap.get("address2"))
                .addElement("city", addressMap.get("city"))
                .addElement("zip_code", addressMap.get("zip_code"))
                .addElement("state", addressMap.get("state"))
                .addElement("country", addressMap.get("country"));
    }

    default RequestBuilder getBillingAddress() {
        return buildBillingAddress();
    }
}