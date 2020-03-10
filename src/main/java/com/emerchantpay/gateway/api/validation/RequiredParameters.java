package com.emerchantpay.gateway.api.validation;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.interfaces.AddressAttributes;

import java.util.HashMap;

public class RequiredParameters {

    public static String transactionId = "transaction_id";
    public static String amount = "amount";
    public static String currency = "currency";
    public static String transactionTypes = "transaction_types";
    public static String transactionTypeName = "transaction_type_name";
    public static String returnSuccessUrl = "return_success_url";
    public static String returnFailureUrl = "return_failure_url";
    public static String returnCancelUrl = "return_cancel_url";
    public static String returnUrl = "return_url";
    public static String customerEmail = "customer_email";
    public static String customerPhone = "customer_phone";
    public static String customerName = "customer_name";
    public static String billingAddress = "billing_address";
    public static String shippingAddress = "shipping_address";
    public static String notificationUrl = "notification_url";
    public static String usage = "usage";
    public static String remoteIp = "remote_ip";
    public static String paymentDescription = "payment_description";
    public static String riskParams = "riskParams";
    public static String dynamicDescriptorParams = "dynamic_descriptor_params";
    public static String lifetime = "lifetime";
    public static String firstName = "firstname";
    public static String lastName = "lastname";
    public static String address1 = "address1";
    public static String address2 = "address2";
    public static String zipCode = "zip_code";
    public static String city = "city";
    public static String country = "country";
    public static String state = "state";
    public static String customerAccount = "customer_account";
    public static String customerAccountId = "customer_account_id";
    public static String customerIdNumber = "customer_id_number";
    public static String customerBankId = "customer_bank_id";
    public static String accountPassword = "account_password";
    public static String sourceWalletId = "source_wallet_id";
    public static String sourceWalletPwd = "source_wallet_pwd";
    public static String merchantCustomerId = "merchant_customer_id";
    public static String productName = "product_name";
    public static String productCategory = "product_category";
    public static String cardType = "card_type";
    public static String redeemType = "redeem_type";
    public static String description = "description";
    public static String referenceId = "reference_id";
    public static String birthDate = "birth_date";
    public static String startDate = "start_date";
    public static String uniqueId = "unique_id";
    public static String iban = "iban";
    public static String bic = "bic";
    public static String bankAccountNumber = "bank_account_number";
    public static String bankName = "bank_name";
    public static String accountName = "account_name";

    // Payment params
    public static String cardHolder = "card_holder";
    public static String cardNumber = "card_number";
    public static String expirationMonth = "expiration_month";
    public static String expirationYear = "expiration_year";

    // Klarna
    public static String orderTaxAmount = "order_tax_amount";
    public static String customerGender = "customer_gender";
    public static String items = "items";
    public static String itemType = "item_type";
    public static String itemName = "name";
    public static String quantity = "quantity";
    public static String unitPrice = "unitPrice";
    public static String totalAmount = "total_amount";
    public static String paymentMethodCategory = "payment_method_category";

    // Consumer API
    public static String consumerId = "consumer_id";
    public static String email = "email";

    // FX Rates
    public static String sourceCurrency = "source_currency";
    public static String targetCurrency = "target_currency";
    public static String tierId = "tier_id";
    public static String rateId = "rate_id";

    // SCA Checker
    public static String transactionAmount = "transaction_amount";
    public static String transactionCurrency = "transaction_currency";

    // Required params
    private HashMap<String, String> requiredParamsMap = new HashMap<String, String>();

    public HashMap<String, String> getRequiredParametersForAddress(AddressAttributes address) {
        if (!address.getBillingAddressParams().isEmpty()) {
            requiredParamsMap.put(country, address.getBillingCountryCode());
        }

        if (!address.getShippingAddressParams().isEmpty()) {
            requiredParamsMap.put(country, address.getShippingCountryCode());
        }

        return requiredParamsMap;
    }

    // Conditional required
    public static String mpiProtocolVersion = "protocol_version";
    public static String mpiDirectoryServerId = "directory_server_id";
    public static String scaVisaMerchantId = "visa_merchant_id";
}
