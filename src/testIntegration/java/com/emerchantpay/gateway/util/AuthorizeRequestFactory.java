package com.emerchantpay.gateway.util;

import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;

import java.math.BigDecimal;

public final class AuthorizeRequestFactory {

    private AuthorizeRequestFactory() { /* no‑op */ }

    public static AuthorizeRequest create() {
        String uniqueId = new StringUtils().generateUID();

        AuthorizeRequest authorize = new AuthorizeRequest();

        authorize.setTransactionId(uniqueId)
                .setUsage("4242 concert tickets")
                .setRemoteIp("13.37.13.37");
        authorize.setGaming(true);

        authorize.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("50.00"));

        authorize.setCardHolder("Emil Example")
                .setCardNumber("4200000000000000")
                .setExpirationMonth("07")
                .setExpirationYear("2042")
                .setCvv("123");

        authorize.setCustomerEmail("emil.example@abc.com");

        authorize.setBillingFirstname("Travis")
                .setBillingLastname("Pastrana")
                .setBillingPrimaryAddress("Muster Str. 12")
                .setBillingZipCode("10178")
                .setBillingCity("Los Angeles")
                .setBillingState("CA").setBillingCountry(Country.UnitedStates.getCode());

        return authorize;
    }

    public static AuthorizeRequest createWithDynamicDescriptorProps() {
        AuthorizeRequest authorize = AuthorizeRequestFactory.create();

        // Set all descriptor attributes according to specified formats and lengths
        authorize.setDynamicMerchantName("Dynamic MName") // Length <=25
                .setDynamicMerchantCity("Dynamic MCity") // Length <=13
                .setDynamicSubMerchantId("SubMerchantID01") // Length <=15
                .setDynamicMerchantCountry(Country.UnitedStates.getCode()) // ISO 3166 format, Length 2
                .setDynamicMerchantState("CA") // ISO 3166-2 code, Length <=3
                .setDynamicMerchantZipCode("90001-1234") // Length <=10
                .setDynamicMerchantAddress("123 Main Street, Suite 100") // Length <=48
                .setDynamicMerchantUrl("https://merchant.example.com/shop") // Length <=60
                .setDynamicMerchantPhone("+123456789012345") // Length <=16
                .setDynamicMerchantServiceCity("Service City1") // Length <=13
                .setDynamicMerchantServiceCountry(Country.UnitedStates.getCode()) // ISO 3166 format, Length 2
                .setDynamicMerchantServiceState("CA") // ISO 3166-2 code, Length <=3
                .setDynamicMerchantServiceZipCode("90002-5678") // Length <=10
                .setDynamicMerchantServicePhone("+098765432109876") // Length <=16
                .setDynamicMerchantGeoCoordinates("40.73061,-73.93524") // Length between 15-20
                .setDynamicMerchantServiceGeoCoordinates("40.73061,-73.93524"); // Length between 15-20

        return authorize;
    }
}
