package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.api.validation.RequiredParametersValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

public interface ManagedRecurringAttributes extends ManagedRecurringIndianCardsAttributes {

    // 'mode' attribute is inherited from ManagedRecurringIndianCardsAttributes interface
    enum MRA {interval, first_date, time_of_day, period, amount}

    default ManagedRecurringAttributes setInterval(String interval) {
        if (isIntervalValid(interval)) {
            getManagedRecurringAttrParamsMap().put(MRA.interval.name(), interval);
            getManagedRecurringAttrRequestBuilder().addElement(MRA.interval.name(), interval);
        }
        return this;
    }

    default String getInterval() {
        return getManagedRecurringAttrParamsMap().get(MRA.interval.name());
    }

    default ManagedRecurringAttributes setFirstDate(String firstDate) {
        if (!getValidator().isValidDateUS(firstDate, "first_date")) {
            throw new InvalidParamException("Invalid first_date format. Allowed date format is : YYYY-MM-DD.");
        }

        getManagedRecurringAttrParamsMap().put(MRA.first_date.name(), firstDate);
        getManagedRecurringAttrRequestBuilder().addElement(MRA.first_date.name(), firstDate);
        return this;
    }

    default String getFirstDate() {
        return getManagedRecurringAttrParamsMap().get(MRA.first_date.name());
    }

    default ManagedRecurringAttributes setTimeOfDay(Integer timeOfDay) {

        getManagedRecurringAttrParamsMap().put(MRA.time_of_day.name(), Integer.toString(timeOfDay));
        getManagedRecurringAttrRequestBuilder().addElement(MRA.time_of_day.name(), Integer.toString(timeOfDay));
        return this;
    }

    default Integer getTimeOfDay() {
        return Integer.valueOf(getManagedRecurringAttrParamsMap().get(MRA.time_of_day.name()));
    }

    default ManagedRecurringAttributes setPeriod(Integer period) {
        getManagedRecurringAttrParamsMap().put(MRA.period.name(), Integer.toString(period));
        getManagedRecurringAttrRequestBuilder().addElement(MRA.period.name(), Integer.toString(period));
        return this;
    }

    default Integer getPeriod() {
        String period = getManagedRecurringAttrParamsMap().get(MRA.period.name());
        return Integer.valueOf(period == null || "".equals(period) ? "0" : period);
    }

    default ManagedRecurringAttributes setManagedRecurringAmount(BigDecimal amount) {
        BigDecimal convertedAmount = getConvertedAmount(amount, MRA.amount.name());
        getManagedRecurringAttrParamsMap().put(MRA.amount.name(), convertedAmount.toString());
        getManagedRecurringAttrRequestBuilder().addElement(MRA.amount.name(), convertedAmount.toString());
        return this;
    }

    default BigDecimal getManagedRecurringAmount() {
        String amount = getManagedRecurringAttrParamsMap().get(MRA.amount.name());
        return getExpToAmount(new BigDecimal(amount == null ? "0.0"
                : amount + ".0"));
    }

    default Boolean isIntervalValid(String interval) {
        List<String> requiredIntervals = Arrays.asList("days", "months");

        if (!requiredIntervals.contains(interval)) {
            throw new InvalidParamException("Invalid interval value. Allowed intervals are: "
                    + requiredIntervals);
        }
        return true;
    }

    default RequestBuilder buildManagedRecurring() {
        return buildManagedRecurring(false, "managed_recurring");
    }

    default RequestBuilder buildManagedRecurring(Boolean includeEmptyTags, String parent) {
        String managedRecurringXML = getManagedRecurringAttrRequestBuilder().toXML();
        if (!includeEmptyTags && managedRecurringXML.isEmpty()) {
            return new RequestBuilder("");
        } else {
            return new RequestBuilder(parent).addElement(managedRecurringXML);
        }
    }

    @Override
    default void validateManagedRecurring(String recurringType) {
        if ("managed".equalsIgnoreCase(recurringType)) {
            RequiredParametersValidator requiredParametersValidator = new RequiredParametersValidator(getRequiredManagedRecurringParams());
            requiredParametersValidator.isValidRequiredParams();
        }
    }

    default HashMap<String, String> getRequiredManagedRecurringParams() {
        List<String> allowedModeValues = new ArrayList<>();
        allowedModeValues.add("automatic");
        allowedModeValues.add("manual");

        HashMap<String, String> requiredParams = new HashMap<>();
        requiredParams.put(RequiredParameters.period, (getPeriod() == null || getPeriod().equals(new Integer(0))) ? ""
                : String.valueOf(getPeriod()));

        if (!allowedModeValues.contains(getMode())) {
            throw new InvalidParamException("Invalid managed recurring mode. Allowed modes are: "
                    + allowedModeValues);
        }

        if ("automatic".equalsIgnoreCase(getMode())) {
            return requiredParams;
        }

        return getRequiredIndianCardParams();
    }

    RequestBuilder getManagedRecurringAttrRequestBuilder();

    HashMap<String, String> getManagedRecurringAttrParamsMap();

    GenesisValidator getValidator();
}
