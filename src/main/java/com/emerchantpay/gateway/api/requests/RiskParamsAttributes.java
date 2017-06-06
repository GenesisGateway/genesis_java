package com.emerchantpay.gateway.api.requests;

import com.emerchantpay.gateway.api.RequestBuilder;

import java.util.HashMap;


public interface RiskParamsAttributes {

    HashMap<String, String> paramMap = new HashMap<String, String>();

    // Risk Params
    default void setRiskSSN(String ssn) {
        paramMap.put("ssn", ssn);
    }

    default void setRiskMacAddress(String macAddress) {
        paramMap.put("mac_address", macAddress);
    }

    default void setRiskSessionId(String sessionId) {
        paramMap.put("session_id", sessionId);
    }

    default void setRiskUserId(String userId) {
        paramMap.put("user_id", userId);
    }

    default void setRiskUserLevel(String userLevel) {
        paramMap.put("user_level", userLevel);
    }

    default void setRiskEmail(String email) {
        paramMap.put("email", email);
    }

    default void setRiskPhone(String phone) {
        paramMap.put("phone", phone);
    }

    default void setRiskRemoteIp(String remoteIp) {
       paramMap.put("remote_ip", remoteIp);
    }

    default void setRiskSerialNumber(String serialNumber) {
        paramMap.put("serial_number", serialNumber);
    }

    default void setRiskPanTail(String panTail) {
        paramMap.put("pan_tail", panTail);
    }
    default void setRiskBin(String bin) {
        paramMap.put("bin", bin);
    }

    default void setRiskFirstName(String firstname) {
        paramMap.put("first_name", firstname);
    }

    default void setRiskLastName(String lastname) {
        paramMap.put("last_name", lastname);
    }

    default void setRiskCounry(String country) {
        paramMap.put("country", country);
    }

    default void setRiskPan(String pan) {
        paramMap.put("pan", pan);
    }

    default void setRiskForwardedIp(String forwardedIp) {
        paramMap.put("forwarded_ip", forwardedIp);
    }

    default void setRiskUsername(String username) {
        paramMap.put("username", username);
    }

    default void setRiskPassword(String password) {
        paramMap.put("password", password);
    }

    default void setRiskBinName(String binName) {
        paramMap.put("bin_name", binName);
    }

    default void setRiskBinPhone(String binPhone) {
        paramMap.put("bin_phone", binPhone);
    }

    default RequestBuilder buildRiskParams(String root) {

        return new RequestBuilder(root).addElement("ssn", paramMap.get("ssn"))
                .addElement("mac_address", paramMap.get("mac_address"))
                .addElement("session_id", paramMap.get("session_id"))
                .addElement("user_id", paramMap.get("user_id"))
                .addElement("user_level", paramMap.get("user_level"))
                .addElement("email", "").addElement("phone", "")
                .addElement("remote_ip", paramMap.get("remote_ip"))
                .addElement("serial_number", paramMap.get("serial_number"))
                .addElement("pan_tail", paramMap.get("pan_tail"))
                .addElement("bin", paramMap.get("bin"))
                .addElement("first_name", paramMap.get("first_name"))
                .addElement("last_name", paramMap.get("last_name"))
                .addElement("country", paramMap.get("country"))
                .addElement("pan", paramMap.get("pan"))
                .addElement("forwarded_ip", paramMap.get("forwarded_ip"))
                .addElement("username", paramMap.get("username"))
                .addElement("password", paramMap.get("password"))
                .addElement("bin_name", paramMap.get("bin_name"))
                .addElement("bin_phone", paramMap.get("bin_phone"));
    }
}
