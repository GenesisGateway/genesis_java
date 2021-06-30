package com.emerchantpay.gateway.api.requests.financial.apm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.P24BankCodes;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.exceptions.InvalidParamException;
import com.emerchantpay.gateway.api.interfaces.customerinfo.CustomerInfoAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;
import com.emerchantpay.gateway.api.validation.RequiredParameters;
import com.emerchantpay.gateway.util.Currency;

public class P24Request extends Request implements PaymentAttributes, CustomerInfoAttributes, AsyncAttributes {

	private String transactionType = TransactionTypes.P24;
	private BigDecimal amount;
	private String currency;
    private Integer bankCode;

	public P24Request() {
		super();
	}

    public Integer getBankCode() {
        return bankCode;
    }

    public P24Request setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
        return this;
    }

	@Override
	public PaymentAttributes setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public PaymentAttributes setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	@Override
	public String getCurrency() {
		return currency;
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

        //Currencies to validate bank code for
        ArrayList<String> bankCodeCurrencies = new ArrayList<String>();
        bankCodeCurrencies.add(Currency.PLN.getCurrency());
        bankCodeCurrencies.add(Currency.EUR.getCurrency());

        if (bankCode != null && !P24BankCodes.getAllowedP24BankCodes().contains(String.valueOf(bankCode)) &&
                bankCodeCurrencies.contains(getCurrency())) {
            throw new InvalidParamException(RequiredParameters.bankCode, P24BankCodes.getAllowedP24BankCodes());
        }

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement(buildBaseParams().toXML()).addElement(buildPaymentParams().toXML())
				.addElement(buildCustomerInfoParams().toXML()).addElement(buildAsyncParams().toXML())
				.addElement("billing_address", buildBillingAddress().toXML())
				.addElement("shipping_address", buildShippingAddress().toXML())
                .addElement("bank_code", bankCode);
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}
