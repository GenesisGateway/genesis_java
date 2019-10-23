package com.emerchantpay.gateway.model;

public class Reminder {
    private String channel;
    private Integer after;

    public Reminder(String channel, Integer after) {
        this.channel = channel;
        this.after = after;
    }

    public String getChannel() {
        return channel;
    }

    public Integer getAfter() {
        return after;
    }
}
