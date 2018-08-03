package com.emerchantpay.gateway.api.requests.financial.wallets;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.interfaces.financial.AsyncAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.NotificationAttributes;
import com.emerchantpay.gateway.api.interfaces.financial.PaymentAttributes;

public class EzeewalletRequest extends Request implements PaymentAttributes, NotificationAttributes, AsyncAttributes {

	private String transactionType = TransactionTypes.EZEEWALLET;
	private BigDecimal amount;
	private String currency;
	private String sourceWalletId;
	private String sourceWalletPwd;

	public EzeewalletRequest() {
		super();
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

	public EzeewalletRequest setSourceWalletId(String sourceWalletId) {
		this.sourceWalletId = sourceWalletId;
		return this;
	}

	public EzeewalletRequest setSourceWalletPwd(String sourceWalletPwd) {
		this.sourceWalletPwd = sourceWalletPwd;
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

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement(buildBaseParams().toXML())
				.addElement(buildPaymentParams().toXML())
				.addElement(buildNotificationParams().toXML())
				.addElement(buildAsyncParams().toXML())
				.addElement("source_wallet_id", sourceWalletId)
				.addElement("source_wallet_pwd", sourceWalletPwd);
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}
