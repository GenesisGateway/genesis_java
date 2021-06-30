package com.emerchantpay.gateway;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.exceptions.RegexException;
import com.emerchantpay.gateway.api.requests.financial.oBeP.PayURequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PendingPaymentAttributesTest {

    @Test(expected = RegexException.class)
    public void testInvalidURL() {
        PayURequest payURequest = new PayURequest();
        payURequest.setReturnPendingUrl("invalid URL");
        payURequest.buildPendingPaymentParams();
    }

    @Test
    public void testValidURL() {
        PayURequest payURequest = new PayURequest();
        String returnPendingUrl = "http://www.example.com/pending";
        payURequest.setReturnPendingUrl(returnPendingUrl);
        assertEquals(returnPendingUrl, payURequest.getReturnPendingUrl());
        assertTrue(payURequest.buildPendingPaymentParams() instanceof RequestBuilder);

        //Should work without URL as it's not required
        payURequest.setReturnPendingUrl("");
        assertEquals("", payURequest.getReturnPendingUrl());
        assertTrue(payURequest.buildPendingPaymentParams() instanceof RequestBuilder);
    }
}
