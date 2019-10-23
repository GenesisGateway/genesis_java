package com.emerchantpay.gateway.api.constants;

public class ErrorMessages {
    public static String INVALID_CHANNEL = "Invalid channel value. Allowed are:  ";
    public static String INVALID_REMINDER_AFTER = "After parameter must be between 1 minute and 31 days in minutes.";
    public static String INVALID_REMINDERS_NUMBER = "Maximum number of 3 allowed reminders reached. You can't add more reminders.";
    public static String INVALID_CONSUMER_ID = "Invalid consumer_id. Max. length is 10 digits.";
}
