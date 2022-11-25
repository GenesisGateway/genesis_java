package com.emerchantpay.gateway.api.constants;

public class ErrorMessages {
    public static String INVALID_CHANNEL = "Invalid channel value. Allowed are:  ";
    public static String INVALID_REMINDER_AFTER = "After parameter must be between 1 minute and 31 days in minutes.";
    public static String INVALID_REMINDERS_NUMBER = "Maximum number of 3 allowed reminders reached. You can't add more reminders.";
    public static String INVALID_CONSUMER_ID = "Invalid consumer_id. Max. length is 10 digits.";
    public static String INVALID_CONSUMER_API_VERSION = "Invalid Consumer API version, available versions are: ";
    public static String INVALID_FX_API_VERSION = "Invalid FX API version, available versions are: ";
    public static String INVALID_SCA_API_VERSION = "Invalid SCA API version, available versions are: ";
    public static String ONLY_ONE_PARAMETER_ALLOWED = "Only one of following parameters is allowed: ";
    public static String AT_LEAST_ONE_PARAMETER_REQUIRED = "At least one of following parameters must be provided: ";
}
