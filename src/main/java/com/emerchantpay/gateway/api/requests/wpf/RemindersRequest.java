package com.emerchantpay.gateway.api.requests.wpf;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.interfaces.ReminderAttributes;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.model.Reminder;

import java.util.ArrayList;

public class RemindersRequest extends Request implements ReminderAttributes {
    private WPFCreateRequest parent;

    // Genesis Validator
    private GenesisValidator validator = new GenesisValidator();

    private ArrayList<Reminder> remindersList = new ArrayList<Reminder>();

    public RemindersRequest() {
        super();
    }

    public RemindersRequest(WPFCreateRequest parent) {
        super();
        this.parent = parent;
    }

    public RemindersRequest(Reminder reminder) {
        if (Boolean.TRUE.equals(parent.getPayLater())
                && validator.validateReminder(reminder.getChannel(), reminder.getAfter())
                && validator.validateRemindersNumber(remindersList) ) {
            this.remindersList.add(reminder);
        }
    }

    public RemindersRequest addReminder(String channel, Integer after) {
        if (Boolean.TRUE.equals(parent.getPayLater())
                && validator.validateReminder(channel, after)
                && validator.validateRemindersNumber(remindersList)) {
            this.remindersList.add(new Reminder(channel, after));
        }
        return this;
    }

    @Override
    public String toXML() {
        return buildRequest("reminders").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {
        RequestBuilder builder = new RequestBuilder(root);

        for (Reminder reminder : remindersList) {
            builder.addElement("reminder", setChannel(reminder.getChannel())
                    .setAfter(reminder.getAfter())
                    .buildReminders().toXML());
        }
        return builder;
    }

    public WPFCreateRequest done() {
        return parent;
    }

    public ArrayList<Reminder> getList() {
        return remindersList;
    }
}
