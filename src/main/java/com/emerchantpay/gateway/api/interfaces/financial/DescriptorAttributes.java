package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;

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

public interface DescriptorAttributes {

    // Descriptor Params
    default DescriptorAttributes setDynamicMerchantName(String dynamicMerchantName) {
        getDescriptorAttrRequestBuilder().addElement("merchant_name", dynamicMerchantName);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantCity(String dynamicMerchantCity) {
        getDescriptorAttrRequestBuilder().addElement("merchant_city", dynamicMerchantCity);
        return this;
    }

    default DescriptorAttributes setDynamicSubMerchantId(String dynamicSubMerchantId) {
        getDescriptorAttrRequestBuilder().addElement("sub_merchant_id", dynamicSubMerchantId);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantCountry(String dynamicMerchantCountry) {
        getDescriptorAttrRequestBuilder().addElement("merchant_country", dynamicMerchantCountry);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantState(String dynamicMerchantState) {
        getDescriptorAttrRequestBuilder().addElement("merchant_state", dynamicMerchantState);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantZipCode(String dynamicMerchantZipCode) {
        getDescriptorAttrRequestBuilder().addElement("merchant_zip_code", dynamicMerchantZipCode);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantAddress(String dynamicMerchantAddress) {
        getDescriptorAttrRequestBuilder().addElement("merchant_address", dynamicMerchantAddress);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantUrl(String dynamicMerchantUrl) {
        getDescriptorAttrRequestBuilder().addElement("merchant_url", dynamicMerchantUrl);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantPhone(String dynamicMerchantPhone) {
        getDescriptorAttrRequestBuilder().addElement("merchant_phone", dynamicMerchantPhone);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantServiceCity(String dynamicMerchantServiceCity) {
        getDescriptorAttrRequestBuilder().addElement("merchant_service_city", dynamicMerchantServiceCity);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantServiceCountry(String dynamicMerchantServiceCountry) {
        getDescriptorAttrRequestBuilder().addElement("merchant_service_country", dynamicMerchantServiceCountry);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantServiceState(String dynamicMerchantServiceState) {
        getDescriptorAttrRequestBuilder().addElement("merchant_service_state", dynamicMerchantServiceState);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantServiceZipCode(String dynamicMerchantServiceZipCode) {
        getDescriptorAttrRequestBuilder().addElement("merchant_service_zip_code", dynamicMerchantServiceZipCode);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantServicePhone(String dynamicMerchantServicePhone) {
        getDescriptorAttrRequestBuilder().addElement("merchant_service_phone", dynamicMerchantServicePhone);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantGeoCoordinates(String dynamicMerchantGeoCoordinates) {
        getDescriptorAttrRequestBuilder().addElement("merchant_geo_coordinates", dynamicMerchantGeoCoordinates);
        return this;
    }

    default DescriptorAttributes setDynamicMerchantServiceGeoCoordinates(String dynamicMerchantServiceGeoCoordinates) {
        getDescriptorAttrRequestBuilder().addElement("merchant_service_geo_coordinates", dynamicMerchantServiceGeoCoordinates);
        return this;
    }

    default RequestBuilder buildDescriptorParams() {
        return getDescriptorAttrRequestBuilder();
    }

    RequestBuilder getDescriptorAttrRequestBuilder();
}