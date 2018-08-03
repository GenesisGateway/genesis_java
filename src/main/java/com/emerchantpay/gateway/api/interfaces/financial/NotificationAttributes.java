package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;

import java.net.URL;

public interface NotificationAttributes {
    RequestBuilder requestBuilder = new RequestBuilder("");

    // Genesis validator
    GenesisValidator validator = new GenesisValidator();

    // Notification Params
    default NotificationAttributes setNotificationUrl(URL notificationUrl) {
        if (validator.isValidUrl("notification_url", String.valueOf(notificationUrl))) {
            requestBuilder.addElement("notification_url", notificationUrl);
        }

        return this;
    }

    default RequestBuilder buildNotificationParams() {
        return requestBuilder;
    }
}
