package com.emerchantpay.gateway.api.interfaces;

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

public interface RiskParamsAttributes {

    // Risk Params
    default RiskParamsAttributes setRiskSSN(String ssn) {
        getRiskParamsAttrRequestBuilder().addElement("ssn", ssn);
        return this;
    }

    default RiskParamsAttributes setRiskMacAddress(String macAddress) {
        getRiskParamsAttrRequestBuilder().addElement("mac_address", macAddress);
        return this;
    }

    default RiskParamsAttributes setRiskSessionId(String sessionId) {
        getRiskParamsAttrRequestBuilder().addElement("session_id", sessionId);
        return this;
    }

    default RiskParamsAttributes setRiskUserId(String userId) {
        getRiskParamsAttrRequestBuilder().addElement("user_id", userId);
        return this;
    }

    default RiskParamsAttributes setRiskUserLevel(String userLevel) {
        getRiskParamsAttrRequestBuilder().addElement("user_level", userLevel);
        return this;
    }

    default RiskParamsAttributes setRiskEmail(String email) {
        getRiskParamsAttrRequestBuilder().addElement("email", email);
        return this;
    }

    default RiskParamsAttributes setRiskPhone(String phone) {
        getRiskParamsAttrRequestBuilder().addElement("phone", phone);
        return this;
    }

    default RiskParamsAttributes setRiskRemoteIp(String remoteIp) {
        getRiskParamsAttrRequestBuilder().addElement("remote_ip", remoteIp);
        return this;
    }

    default RiskParamsAttributes setRiskSerialNumber(String serialNumber) {
        getRiskParamsAttrRequestBuilder().addElement("serial_number", serialNumber);
        return this;
    }

    default RiskParamsAttributes setRiskPanTail(String panTail) {
        getRiskParamsAttrRequestBuilder().addElement("pan_tail", panTail);
        return this;
    }

    default RiskParamsAttributes setRiskBin(String bin) {
        getRiskParamsAttrRequestBuilder().addElement("bin", bin);
        return this;
    }

    default RiskParamsAttributes setRiskFirstName(String firstname) {
        getRiskParamsAttrRequestBuilder().addElement("first_name", firstname);
        return this;
    }

    default RiskParamsAttributes setRiskLastName(String lastname) {
        getRiskParamsAttrRequestBuilder().addElement("last_name", lastname);
        return this;
    }

    default RiskParamsAttributes setRiskCountry(String country) {
        getRiskParamsAttrRequestBuilder().addElement("country", country);
        return this;
    }

    default RiskParamsAttributes setRiskPan(String pan) {
        getRiskParamsAttrRequestBuilder().addElement("pan", pan);
        return this;
    }

    default RiskParamsAttributes setRiskForwardedIp(String forwardedIp) {
        getRiskParamsAttrRequestBuilder().addElement("forwarded_ip", forwardedIp);
        return this;
    }

    default RiskParamsAttributes setRiskUsername(String username) {
        getRiskParamsAttrRequestBuilder().addElement("username", username);
        return this;
    }

    default RiskParamsAttributes setRiskPassword(String password) {
        getRiskParamsAttrRequestBuilder().addElement("password", password);
        return this;
    }

    default RiskParamsAttributes setRiskBinName(String binName) {
        getRiskParamsAttrRequestBuilder().addElement("bin_name", binName);
        return this;
    }

    default RiskParamsAttributes setRiskBinPhone(String binPhone) {
        getRiskParamsAttrRequestBuilder().addElement("bin_phone", binPhone);
        return this;
    }

    default RequestBuilder buildRiskParams() {
        return getRiskParamsAttrRequestBuilder();
    }

    RequestBuilder getRiskParamsAttrRequestBuilder();
}