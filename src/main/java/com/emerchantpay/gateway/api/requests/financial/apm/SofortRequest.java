package com.emerchantpay.gateway.api.requests.financial.apm;

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.RequiredParamsException;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PendingPaymentAttributes;
import com.emerchantpay.gateway.api.requests.financial.FinancialRequest;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class SofortRequest extends FinancialRequest implements CustomerInfoAttributes, AsyncAttributes, PendingPaymentAttributes {

	private String transactionType = TransactionTypes.SOFORT;

	private String customerBankId;
	private String bankAccountNumber;

	// Required params
	private HashMap<String, String> requiredParams = new HashMap<String, String>();

	// GenesisValidator
	private GenesisValidator validator = new GenesisValidator();

	public SofortRequest() {
		super();
	}

	public SofortRequest setCustomerBankId(String customerBankId) {
		this.customerBankId = customerBankId;
		return this;
	}

	public SofortRequest setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
		return this;
	}

	@Override
	public String getTransactionType() {
		return transactionType;
	}

	@Override
	public String toXML() {
		return buildRequest("payment_transaction").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		// Set required params
		requiredParams.put(RequiredParameters.transactionId, getTransactionId());
		requiredParams.put(RequiredParameters.amount, getAmount().toString());
		requiredParams.put(RequiredParameters.currency, getCurrency());
		requiredParams.put(RequiredParameters.returnSuccessUrl, getReturnSuccessUrl());
		requiredParams.put(RequiredParameters.returnFailureUrl, getReturnFailureUrl());
		requiredParams.put(RequiredParameters.customerEmail, getCustomerEmail());

		setRequiredCountries();

		// Validate request
		validator.isValidRequest(requiredParams);

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement(buildBaseParams().toXML()).addElement(buildPaymentParams().toXML())
				.addElement(buildCustomerInfoParams().toXML()).addElement(buildAsyncParams().toXML())
                .addElement(buildPendingPaymentParams().toXML())
				.addElement("customer_bank_id", customerBankId)
				.addElement("ban_account_number", bankAccountNumber)
				.addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML());
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}

	protected void setRequiredCountries() {
		// Allowed Countries
		ArrayList<String> requiredCountries = new ArrayList<String>();

		requiredCountries.add(Country.Austria.getCode());
		requiredCountries.add(Country.Belgium.getCode());
		requiredCountries.add(Country.Germany.getCode());
		requiredCountries.add(Country.Denmark.getCode());
		requiredCountries.add(Country.Spain.getCode());
		requiredCountries.add(Country.France.getCode());
		requiredCountries.add(Country.UnitedKingdom.getCode());
		requiredCountries.add(Country.Netherlands.getCode());

		if (!requiredCountries.contains(getBillingCountryCode())) {
			throw new RequiredParamsException("Invalid country. Allowed countries are: "
					+ requiredCountries.toString());
		}
	}
}