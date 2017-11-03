package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.util.Country;

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

public interface ShippingAddressAttributes {

    RequestBuilder requestBuilder = new RequestBuilder("");

    // Shipping Address
    default ShippingAddressAttributes setShippingFirstname(String firstname) {
        requestBuilder.addElement("first_name", firstname);
        return this;
    }

    default ShippingAddressAttributes setShippingLastname(String lastname) {
        requestBuilder.addElement("last_name", lastname);
        return this;
    }

    default ShippingAddressAttributes setShippingPrimaryAddress(String primaryAddress) {
        requestBuilder.addElement("address1", primaryAddress);
        return this;
    }

    default ShippingAddressAttributes setShippingSecondaryAddress(String secondaryAddress) {
        requestBuilder.addElement("address2", secondaryAddress);
        return this;
    }

    default ShippingAddressAttributes setShippingCity(String city) {
        requestBuilder.addElement("city", city);
        return this;
    }

    default ShippingAddressAttributes setShippingZipCode(String zipCode) {
        requestBuilder.addElement("zip_code", zipCode);
        return this;
    }

    default ShippingAddressAttributes setShippingState(String state) {
        requestBuilder.addElement("state", state);
        return this;
    }

    default ShippingAddressAttributes setShippingCountry(String country) {
        Country c = new Country();

        requestBuilder.addElement("country", c.getIsoCode(country));

        return this;
    }

    default RequestBuilder buildShippingAddress() {
        return requestBuilder;
    }
}