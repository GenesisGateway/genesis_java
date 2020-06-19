package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public interface NotificationAttributes {

    // Notification Params
    default NotificationAttributes setNotificationUrl(URL notificationUrl) {
        if (getValidator().isValidUrl("notification_url", String.valueOf(notificationUrl))) {
            getNotificationAttrParamsMap().put("notification_url", String.valueOf(notificationUrl));
            getNotificationAttrRequestBuilder().addElement("notification_url", notificationUrl);
        }

        return this;
    }

    default String getNotificationUrl() {
        return getNotificationAttrParamsMap().get("notification_url");
    }

    default RequestBuilder buildNotificationParams() {
        return getNotificationAttrRequestBuilder();
    }

    RequestBuilder getNotificationAttrRequestBuilder();

    HashMap<String, String> getNotificationAttrParamsMap();

    GenesisValidator getValidator();
}
