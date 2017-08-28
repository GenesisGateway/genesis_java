package com.emerchantpay.gateway;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.UUID;

import com.emerchantpay.gateway.api.exceptions.AuthenticationException;
import org.junit.Test;

import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.util.Configuration;

public class TransactionGatewayTest {

	private GenesisClient client;
	private AuthorizeRequest request = new AuthorizeRequest();

	@Test(expected = AuthenticationException.class)
	public void parsePaymentResponse() {

		UUID uniqueId = UUID.randomUUID();

		Configuration configuration = new Configuration(Environments.STAGING, Endpoints.EMERCHANTPAY);

		client = new GenesisClient(configuration);

		request.setTransactionId(uniqueId.toString()).setRemoteIp("192.168.0.1").setGaming(true).setMoto(true)
				.setAmount(new BigDecimal("22.00")).setCurrency("USD").setCardholder("JOHN DOE")
				.setCardNumber("4200000000000000").setExpirationMonth("02").setExpirationYear("2020").setCvv("123")
				.setCustomerEmail("test@example.com").setCustomerPhone("5555555555").setUsage("TICKETS");

		request.setBillingPrimaryAddress("Address1");
		request.setBillingSecondaryAddress("Address2");
		request.setBillingFirstname("John");
		request.setBillingLastname("Doe");
		request.setBillingCity("New York");
		request.setBillingCountry("US");
		request.setBillingZipCode("1000");
		request.setBillingState("NY");

		request.execute(configuration);

		TransactionResult<? extends Transaction> result = this.client.getTransaction().getAuthorize(request);

		assertEquals("approved", result.getTransaction().getStatus());
	}
}