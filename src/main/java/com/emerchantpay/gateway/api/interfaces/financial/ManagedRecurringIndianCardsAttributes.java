package com.emerchantpay.gateway.api.interfaces.financial;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.api.validation.RequiredParametersValidator;
import com.emerchantpay.gateway.util.Currency;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

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

public interface ManagedRecurringIndianCardsAttributes {

    enum MRICA {
        mode, payment_type, amount_type, frequency, registration_reference_number, max_amount,
        max_count, validated
    }

    enum Mode {automatic, manual}

    enum PaymentType {initial, subsequent, modification, cancellation}

    enum AmountType {fixed, max}

    enum FrequencyType {
        daily, twice_weekly, weekly, ten_days, fortnightly,
        monthly, every_two_months, trimester, quarterly, twice_yearly, annually, unscheduled
    }

    default ManagedRecurringIndianCardsAttributes setMode(String mode) {
        if (isModeValid(mode)) {
            getManagedRecurringAttrParamsMap().put(MRICA.mode.name(), mode);
            getManagedRecurringAttrRequestBuilder().addElement(MRICA.mode.name(), mode);
        }
        return this;
    }

    default String getMode() {
        return getManagedRecurringAttrParamsMap().get(MRICA.mode.name());
    }

    default ManagedRecurringIndianCardsAttributes setPaymentType(String paymentType) {
        if (isPaymentTypeValid(paymentType)) {
            getManagedRecurringAttrParamsMap().put(MRICA.payment_type.name(), paymentType);
            getManagedRecurringAttrRequestBuilder().addElement(MRICA.payment_type.name(), paymentType);
        }
        return this;
    }

    default String getPaymentType() {
        return getManagedRecurringAttrParamsMap().get(MRICA.payment_type.name());
    }

    default ManagedRecurringIndianCardsAttributes setAmountType(String amountType) {
        if (isAmountTypeValid(amountType)) {
            getManagedRecurringAttrParamsMap().put(MRICA.amount_type.name(), amountType);
            getManagedRecurringAttrRequestBuilder().addElement(MRICA.amount_type.name(), amountType);
        }
        return this;
    }

    default String getAmountType() {
        return getManagedRecurringAttrParamsMap().get(MRICA.amount_type.name());
    }

    default ManagedRecurringIndianCardsAttributes setFrequency(String frequency) {
        if (isFrequencyValid(frequency)) {
            getManagedRecurringAttrParamsMap().put(MRICA.frequency.name(), frequency);
            getManagedRecurringAttrRequestBuilder().addElement(MRICA.frequency.name(), frequency);
        }
        return this;
    }

    default String getFrequency() {
        return getManagedRecurringAttrParamsMap().get(MRICA.frequency.name());
    }

    default ManagedRecurringIndianCardsAttributes setRegistrationReferenceNumber(String regRefNumber) {
        getManagedRecurringAttrParamsMap().put(MRICA.registration_reference_number.name(), regRefNumber);
        getManagedRecurringAttrRequestBuilder().addElement(MRICA.registration_reference_number.name(), regRefNumber);
        return this;
    }

    default String getRegistrationReferenceNumber() {
        return getManagedRecurringAttrParamsMap().get(MRICA.registration_reference_number.name());
    }

    default ManagedRecurringIndianCardsAttributes setMaxAmount(BigDecimal maxAmount) {
        BigDecimal convertedAmount = getConvertedAmount(maxAmount, MRICA.max_amount.name());
        getManagedRecurringAttrParamsMap().put(MRICA.max_amount.name(), convertedAmount.toString());
        getManagedRecurringAttrRequestBuilder().addElement(MRICA.max_amount.name(), convertedAmount.toString());
        return this;
    }

    default BigDecimal getMaxAmount() {
        String maxAmount = getManagedRecurringAttrParamsMap().get(MRICA.max_amount.name());
        return getExpToAmount(new BigDecimal(maxAmount == null ? "0.0" : maxAmount + ".0"));
    }

    default ManagedRecurringIndianCardsAttributes setMaxCount(Integer maxCount) {
        getManagedRecurringAttrParamsMap().put(MRICA.max_count.name(), Integer.toString(maxCount));
        getManagedRecurringAttrRequestBuilder().addElement(MRICA.max_count.name(), Integer.toString(maxCount));
        return this;
    }

    default Integer getMaxCount() {
        String maxCount = getManagedRecurringAttrParamsMap().get(MRICA.max_count.name());
        return Integer.valueOf(maxCount == null ? "0" : maxCount);
    }

    default ManagedRecurringIndianCardsAttributes setValidated(Boolean validated) {
        getManagedRecurringAttrParamsMap().put(MRICA.validated.name(), Boolean.toString(validated));
        getManagedRecurringAttrRequestBuilder().addElement(MRICA.validated.name(), Boolean.toString(validated));
        return this;
    }

    default Boolean getValidated() {
        return Boolean.valueOf(getManagedRecurringAttrParamsMap().get(MRICA.validated.name()));
    }

    default Boolean isModeValid(String mode) {
        if (Stream.of(Mode.values()).noneMatch(v -> v.name().equals(mode))) {
            throw new InvalidParamException("Invalid managed recurring mode. Allowed modes are: "
                    + Arrays.asList(Mode.values()));
        }
        return true;
    }

    default Boolean isPaymentTypeValid(String paymentType) {
        if (Stream.of(PaymentType.values()).noneMatch(v -> v.name().equals(paymentType))) {
            throw new InvalidParamException("Invalid managed payment type. Allowed payment types are: "
                    + Arrays.asList(PaymentType.values()));
        }
        return true;
    }

    default Boolean isAmountTypeValid(String amountType) {
        if (Stream.of(AmountType.values()).noneMatch(v -> v.name().equals(amountType))) {
            throw new InvalidParamException("Invalid managed amount type. Allowed amount types are: "
                    + Arrays.asList(AmountType.values()));
        }
        return true;
    }

    default Boolean isFrequencyValid(String frequency) {
        if (Stream.of(FrequencyType.values()).noneMatch(v -> v.name().equals(frequency))) {
            throw new InvalidParamException("Invalid managed frequency type. Allowed frequency types are: "
                    + Arrays.asList(FrequencyType.values()));
        }
        return true;
    }

    default BigDecimal getConvertedAmount(BigDecimal amount, String parameter) {
        BigDecimal convertedAmount = null;

        if (amount != null && getCurrency() != null) {
            Currency curr = new Currency();
            curr.setAmountToExponent(amount, getCurrency());
            convertedAmount = curr.getAmount();
        }

        if (!getValidator().isValidAmount(convertedAmount, false)) {
            throw new InvalidParamException("Invalid " + parameter + " value [" + amount.toString() + "].");
        }

        return convertedAmount;
    }

    default BigDecimal getExpToAmount(BigDecimal expAmount) {
        BigDecimal amount = null;

        if (expAmount != null && getCurrency() != null) {
            Currency curr = new Currency();
            curr.setExponentToAmount(expAmount, getCurrency());
            amount = curr.getAmount();
        }

        return amount;
    }

    default HashMap<String, String> getRequiredIndianCardParams() {
        HashMap<String, String> requiredIndianCardParams = new HashMap<>();
        requiredIndianCardParams.put(RequiredParameters.paymentType, getPaymentType());
        requiredIndianCardParams.put(RequiredParameters.amountType, getAmountType());
        requiredIndianCardParams.put(RequiredParameters.frequency, getFrequency());
        requiredIndianCardParams.put(RequiredParameters.registrationReferenceNumber, getRegistrationReferenceNumber());
        requiredIndianCardParams.put(RequiredParameters.maxAmount, getMaxAmount() == null ? null : getMaxAmount().toString());
        requiredIndianCardParams.put(RequiredParameters.maxCount, String.valueOf(getMaxCount().equals("0") ? null : getMaxCount()));
        requiredIndianCardParams.put(RequiredParameters.validated, String.valueOf(getValidated()));

        return requiredIndianCardParams;
    }

    default void validateManagedRecurring(String recurringType) {
        if ("managed".equalsIgnoreCase(recurringType)) {
            RequiredParametersValidator requiredParametersValidator = new RequiredParametersValidator(getRequiredIndianCardParams());
            requiredParametersValidator.isValidRequiredParams();
        }
    }

    RequestBuilder getManagedRecurringAttrRequestBuilder();

    HashMap<String, String> getManagedRecurringAttrParamsMap();

    GenesisValidator getValidator();

    String getCurrency();
}
