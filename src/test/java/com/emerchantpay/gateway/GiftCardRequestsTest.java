package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.constants.ErrorCodes;
import com.emerchantpay.gateway.api.exceptions.ApiException;
import com.emerchantpay.gateway.api.requests.financial.giftcards.FashionchequeRequest;
import com.emerchantpay.gateway.api.requests.financial.giftcards.IntersolveRequest;
import com.emerchantpay.gateway.api.requests.financial.giftcards.TcsRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GiftCardRequestsTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String fashionChequeUId;
    private String intersolveUId;
    private String tcsUId;

    private FashionchequeRequest fashioncheque = new FashionchequeRequest();
    private IntersolveRequest intersolve = new IntersolveRequest();
    private TcsRequest tcs = new TcsRequest();
    @Before
    public void createFashioncheque() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();
        fashionChequeUId = new StringUtils().generateUID();

        // Fashioncheque
        fashioncheque.setTransactionId(fashionChequeUId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        fashioncheque.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        fashioncheque.setCardNumber("6046425114923545647").setCvv("673815");

        fashioncheque.setDynamicMerchantName("John Doe").setDynamicMerchantName("New York");

        mappedParams.put("base_attributes", fashioncheque.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", fashioncheque.buildPaymentParams().getElements());
        mappedParams.put("descriptor_attributes",  fashioncheque.buildDescriptorParams().getElements());
    }

    @Before
    public void createIntersolve() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();
        intersolveUId = new StringUtils().generateUID();

        // Intersolve
        intersolve.setTransactionId(intersolveUId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        intersolve.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        intersolve.setCardNumber("7000001163991388834").setCvv("673815");

        intersolve.setDynamicMerchantName("John Doe").setDynamicMerchantName("New York");

        mappedParams.put("base_attributes", intersolve.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", intersolve.buildPaymentParams().getElements());
        mappedParams.put("descriptor_attributes",  intersolve.buildDescriptorParams().getElements());
    }

    @Before
    public void createTCS() throws MalformedURLException {

        mappedParams = new HashMap<String, Object>();
        tcsUId = new StringUtils().generateUID();

        // TCS
        tcs.setTransactionId(tcsUId).setRemoteIp("82.137.112.202").setUsage("TICKETS");
        tcs.setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"));
        tcs.setCardNumber("6046425117120757123").setCvv("121839");

        tcs.setDynamicMerchantName("John Doe").setDynamicMerchantName("New York");

        mappedParams.put("base_attributes", tcs.buildBaseParams().getElements());
        mappedParams.put("payment_attributes", tcs.buildPaymentParams().getElements());
        mappedParams.put("descriptor_attributes",  tcs.buildDescriptorParams().getElements());
    }

    public void clearRequiredParams() {
        fashioncheque.setCardNumber(null);
        intersolve.setCardNumber(null);
        tcs.setCardNumber(null);

        Integer errorCode = ErrorCodes.INPUT_DATA_ERROR.getCode();

        throw new ApiException(errorCode,
                ErrorCodes.getErrorDescription(errorCode), new Throwable());
    }

    @Test
    public void testFashioncheque() throws MalformedURLException {

        elements = fashioncheque.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), fashioncheque.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), fashioncheque.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), fashioncheque.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("card_number"), "6046425114923545647");
        assertEquals(mappedParams.get("cvv"), "673815");
        assertEquals(mappedParams.get("descriptor_attributes"), fashioncheque.buildDescriptorParams().getElements());
    }

    @Test
    public void testIntersolve() throws MalformedURLException {

        elements = intersolve.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), intersolve.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), intersolve.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), intersolve.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("card_number"), "7000001163991388834");
        assertEquals(mappedParams.get("cvv"), "673815");
        assertEquals(mappedParams.get("descriptor_attributes"), intersolve.buildDescriptorParams().getElements());
    }

    @Test
    public void testTCS() throws MalformedURLException {

        elements = tcs.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), tcs.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("base_attributes"), tcs.buildBaseParams().getElements());
        assertEquals(mappedParams.get("payment_attributes"), tcs.buildPaymentParams().getElements());
        assertEquals(mappedParams.get("card_number"), "6046425117120757123");
        assertEquals(mappedParams.get("cvv"), "121839");
        assertEquals(mappedParams.get("descriptor_attributes"), tcs.buildDescriptorParams().getElements());
    }

    @Test(expected = ApiException.class)
    public void testFashionchequeWithMissingParams() {

        clearRequiredParams();

        elements = fashioncheque.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), fashioncheque.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("card_number"));
    }

    @Test(expected = ApiException.class)
    public void testIntersolveWithMissingParams() {

        clearRequiredParams();

        elements = intersolve.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), intersolve.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("card_number"));
    }

    @Test(expected = ApiException.class)
    public void testTCSWithMissingParams() {

        clearRequiredParams();

        elements = tcs.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), tcs.getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("card_number"));
    }
}