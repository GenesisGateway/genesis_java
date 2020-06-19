package com.emerchantpay.gateway.api.interfaces;

import com.emerchantpay.gateway.api.RequestBuilder;

import java.util.HashMap;

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

public interface ReminderAttributes {

    // Reminders Params
    default ReminderAttributes setChannel(String channel) {
        getReminderAttrParamsMap().put("channel", channel);
        getReminderAttrRequestBuilder().addElement("channel", channel);
        return this;
    }

    default String getChannel() {
        return getReminderAttrParamsMap().get("channel");
    }

    default ReminderAttributes setAfter(Integer after) {
        getReminderAttrParamsMap().put("after", after.toString());
        getReminderAttrRequestBuilder().addElement("after", after);
        return this;
    }

    default String getAfter() {
        return getReminderAttrParamsMap().get("after");
    }

    default RequestBuilder buildReminders() {
        return getReminderAttrRequestBuilder();
    }

    RequestBuilder getReminderAttrRequestBuilder();

    HashMap<String, String> getReminderAttrParamsMap();
}