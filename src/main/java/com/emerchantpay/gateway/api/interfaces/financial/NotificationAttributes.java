package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public interface NotificationAttributes {
    RequestBuilder requestBuilder = new RequestBuilder("");

    // Genesis validator
    GenesisValidator validator = new GenesisValidator();

    HashMap<String, String> paramsMap = new HashMap<String, String>();

    // Notification Params
    default NotificationAttributes setNotificationUrl(URL notificationUrl) {
        if (validator.isValidUrl("notification_url", String.valueOf(notificationUrl))) {
            paramsMap.put("notification_url", String.valueOf(notificationUrl));
            requestBuilder.addElement("notification_url", notificationUrl);
        }

        return this;
    }

    default String getNotificationUrl() {
        return paramsMap.get("notification_url");
    }

    default RequestBuilder buildNotificationParams() {
        return requestBuilder;
    }
}
