package com.emerchantpay.gateway.nonfinantial;

import com.emerchantpay.gateway.api.requests.nonfinancial.BlacklistRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class BlacklistTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private BlacklistRequest blacklist = new BlacklistRequest();

    @Before
    public void createBlacklist() {

        // Blacklist
        blacklist.setCardNumber("4200000000000000").setTerminalToken("abd30ed00f88f838c5d233cbb62b6da0b69267b4");
    }

    @Test
    public void testBlacklist() {

        mappedParams = new HashMap<String, Object>();

        elements = blacklist.getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), blacklist.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("card_number"), "4200000000000000");
        assertEquals(mappedParams.get("terminal_token"), "abd30ed00f88f838c5d233cbb62b6da0b69267b4");
    }
}
