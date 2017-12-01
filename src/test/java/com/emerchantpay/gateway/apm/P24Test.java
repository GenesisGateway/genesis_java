package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.GenesisClient;
import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.apm.P24Request;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class P24Test {

	private String uniqueId;

	private GenesisClient client;
	private P24Request p24Request;

	@Before
	public void createP24() throws MalformedURLException {
		uniqueId = new StringUtils().generateUID();

		client = mock(GenesisClient.class);
		p24Request = mock(P24Request.class);
	}

	public void clearRequiredParams() {
		Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();
		ApiException exception = new ApiException(errorCode, ErrorCodes.getErrorDescription(errorCode),
				new Throwable());

		when(p24Request.setBillingCountry(null)).thenThrow(exception);
	}

	public void verifyExecute() {
		when(client.execute()).thenReturn(p24Request);
		assertEquals(client.execute(), p24Request);
		verify(client).execute();
		verifyNoMoreInteractions(client);
	}

	@Test
	public void testP24() throws MalformedURLException {
		// P24
		when(p24Request.setTransactionId(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setRemoteIp((isA(String.class)))).thenReturn(p24Request);
		when(p24Request.setUsage(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setCurrency(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setAmount(isA(BigDecimal.class))).thenReturn(p24Request);
		when(p24Request.setCustomerEmail(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setCustomerPhone(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setReturnSuccessUrl(isA(URL.class))).thenReturn(p24Request);
		when(p24Request.setReturnFailureUrl(isA(URL.class))).thenReturn(p24Request);
		when(p24Request.setBillingPrimaryAddress(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setBillingSecondaryAddress(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setBillingFirstname(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setBillingLastname(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setBillingCity(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setBillingCountry(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setBillingZipCode(isA(String.class))).thenReturn(p24Request);
		when(p24Request.setBillingState(isA(String.class))).thenReturn(p24Request);

		assertEquals(p24Request.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS"),
				p24Request);
		assertEquals(p24Request.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00")),
				p24Request);
		assertEquals(p24Request.setCustomerEmail("john@example.com").setCustomerPhone("+55555555"), p24Request);
		assertEquals(p24Request.setReturnSuccessUrl(new URL("https://example.com/return_success_url"))
				.setReturnFailureUrl(new URL("https://example.com/return_failure_url")), p24Request);
		assertEquals(p24Request.setBillingPrimaryAddress("Berlin").setBillingSecondaryAddress("Berlin")
				.setBillingFirstname("Plamen").setBillingLastname("Petrov")
				.setBillingCity("Berlin").setBillingCountry("DE")
				.setBillingZipCode("M4B1B3").setBillingState("BE"), p24Request);

		verify(p24Request).setTransactionId(uniqueId);
		verify(p24Request).setRemoteIp("82.137.112.202");
		verify(p24Request).setUsage("TICKETS");
		verify(p24Request).setCurrency(Currency.EUR.getCurrency());
		verify(p24Request).setAmount(new BigDecimal("2.00"));
		verify(p24Request).setCustomerEmail("john@example.com");
		verify(p24Request).setCustomerPhone("+55555555");
		verify(p24Request).setReturnSuccessUrl(new URL("https://example.com/return_success_url"));
		verify(p24Request).setReturnFailureUrl(new URL("https://example.com/return_failure_url"));
		verify(p24Request).setBillingPrimaryAddress("Berlin");
		verify(p24Request).setBillingSecondaryAddress("Berlin");
		verify(p24Request).setBillingFirstname("Plamen");
		verify(p24Request).setBillingLastname("Petrov");
		verify(p24Request).setBillingCity("Berlin");
		verify(p24Request).setBillingCountry("DE");
		verify(p24Request).setBillingZipCode("M4B1B3");
		verify(p24Request).setBillingState("BE");
		verifyNoMoreInteractions(p24Request);

		verifyExecute();
	}

	@Test(expected = ApiException.class)
	public void testP24WithMissingParams() {

		clearRequiredParams();

		assertNull(p24Request.setBillingCountry(null));
		verify(p24Request).setBillingCountry(null);
		verifyNoMoreInteractions(p24Request);

		verifyExecute();
	}
}