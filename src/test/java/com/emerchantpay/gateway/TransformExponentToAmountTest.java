package com.emerchantpay.gateway;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.emerchantpay.gateway.util.Currency;

public class TransformExponentToAmountTest {

	private BigDecimal amount;
	private String currency;

	@Test
	public void transformAmountToExponent() {

		amount = new BigDecimal("1000.00");
		currency = Currency.USD.getCurrency();

		if (amount != null && currency != null) {
			Currency curr = new Currency();

			curr.setExponentToAmount(amount, currency);
			amount = curr.getAmount();
		}

		assertEquals(amount, new BigDecimal("10.00"));
	}
}
