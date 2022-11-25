package com.emerchantpay.gateway;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import com.emerchantpay.gateway.api.interfaces.AddressAttributes;
import org.junit.Before;
import org.junit.Test;

public class AddressTest {

	AddressAttributes addresses;

	@Before
	public void createAddress() {
		addresses = mock(AddressAttributes.class);
	}

	@Test
	public void buildAddress() {
		// Billing address
		when(addresses.setBillingFirstname(isA(String.class))).thenReturn(addresses);
		when(addresses.setBillingLastname(isA(String.class))).thenReturn(addresses);
		when(addresses.setBillingPrimaryAddress(isA(String.class))).thenReturn(addresses);
		when(addresses.setBillingSecondaryAddress(isA(String.class))).thenReturn(addresses);
		when(addresses.setBillingCity(isA(String.class))).thenReturn(addresses);
		when(addresses.setBillingZipCode(isA(String.class))).thenReturn(addresses);
		when(addresses.setBillingCountry(isA(String.class))).thenReturn(addresses);

		assertEquals(addresses.setBillingFirstname("John"), addresses);
		assertEquals(addresses.setBillingLastname("Doe"), addresses);
		assertEquals(addresses.setBillingPrimaryAddress("Fifth avenue"), addresses);
		assertEquals(addresses.setBillingSecondaryAddress("Fourth avenue"), addresses);
		assertEquals(addresses.setBillingCity("New York"), addresses);
		assertEquals(addresses.setBillingZipCode("16000"), addresses);
		assertEquals(addresses.setBillingCountry("US"), addresses);
		assertEquals(addresses.buildBillingAddress(), addresses.getBillingAddress());
		assertEquals(addresses.buildBillingAddress(true), addresses.getBillingAddress());
		assertEquals(addresses.buildBillingAddress(true, ""), addresses.getBillingAddress());

		verify(addresses).setBillingFirstname("John");
		verify(addresses).setBillingLastname("Doe");
		verify(addresses).setBillingPrimaryAddress("Fifth avenue");
		verify(addresses).setBillingSecondaryAddress("Fourth avenue");
		verify(addresses).setBillingCity("New York");
		verify(addresses).setBillingZipCode("16000");
		verify(addresses).setBillingCountry("US");
		verify(addresses).buildBillingAddress();
		verify(addresses).buildBillingAddress(true);
		verify(addresses).buildBillingAddress(true, "");
		verify(addresses, times(3)).getBillingAddress();

		// Shipping address
		when(addresses.setShippingFirstname(isA(String.class))).thenReturn(addresses);
		when(addresses.setShippingLastname(isA(String.class))).thenReturn(addresses);
		when(addresses.setShippingPrimaryAddress(isA(String.class))).thenReturn(addresses);
		when(addresses.setShippingSecondaryAddress(isA(String.class))).thenReturn(addresses);
		when(addresses.setShippingCity(isA(String.class))).thenReturn(addresses);
		when(addresses.setShippingZipCode(isA(String.class))).thenReturn(addresses);
		when(addresses.setShippingCountry(isA(String.class))).thenReturn(addresses);

		assertEquals(addresses.setShippingFirstname("John"), addresses);
		assertEquals(addresses.setShippingLastname("Doe"), addresses);
		assertEquals(addresses.setShippingPrimaryAddress("Fifth avenue"), addresses);
		assertEquals(addresses.setShippingSecondaryAddress("Fourth avenue"), addresses);
		assertEquals(addresses.setShippingCity("New York"), addresses);
		assertEquals(addresses.setShippingZipCode("16000"), addresses);
		assertEquals(addresses.setShippingCountry("US"), addresses);
		assertEquals(addresses.buildShippingAddress(), addresses.getShippingAddress());
		assertEquals(addresses.buildShippingAddress(true), addresses.getShippingAddress());
		assertEquals(addresses.buildShippingAddress(true, ""), addresses.getShippingAddress());

		verify(addresses).setShippingFirstname("John");
		verify(addresses).setShippingLastname("Doe");
		verify(addresses).setShippingPrimaryAddress("Fifth avenue");
		verify(addresses).setShippingSecondaryAddress("Fourth avenue");
		verify(addresses).setShippingCity("New York");
		verify(addresses).setShippingZipCode("16000");
		verify(addresses).setShippingCountry("US");
		verify(addresses).buildShippingAddress();
		verify(addresses).buildShippingAddress(true);
		verify(addresses).buildShippingAddress(true, "");
		verify(addresses, times(3)).getShippingAddress();

		verifyNoMoreInteractions(addresses);
	}
}