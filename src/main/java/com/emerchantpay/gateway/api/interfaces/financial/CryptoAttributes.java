package com.emerchantpay.gateway.api.interfaces.financial;

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
import com.emerchantpay.gateway.api.constants.CryptoWalletProviders;

import java.util.HashMap;

public interface CryptoAttributes {

    default CryptoAttributes setCrypto(Boolean crypto) {
        getCryptoAttrParamsMap().put("crypto", String.valueOf(crypto));
        getCryptoAttrRequestBuilder().addElement("crypto", crypto);
        return this;
    }

    default String getCrypto() {
        return getCryptoAttrParamsMap().get("crypto");
    }

    default CryptoAttributes setCryptoAddress(String cryptoAddress) {
        getCryptoAttrParamsMap().put("crypto_address", cryptoAddress);
        getCryptoAttrRequestBuilder().addElement("crypto_address", cryptoAddress);
        return this;
    }

    default String getCryptoAddress() {
        return getCryptoAttrParamsMap().get("crypto_address");
    }

    default CryptoAttributes setCryptoWalletProvider(String cryptoWalletProvider) {
        getCryptoAttrParamsMap().put("crypto_wallet_provider", CryptoWalletProviders.validate(cryptoWalletProvider));
        getCryptoAttrRequestBuilder().addElement("crypto_wallet_provider", CryptoWalletProviders.validate(cryptoWalletProvider));
        return this;
    }

    default String getCryptoWalletProvider() {
        return getCryptoAttrParamsMap().get("crypto_wallet_provider");
    }

    default RequestBuilder buildCryptoParams() {
        return getCryptoAttrRequestBuilder();
    }

    RequestBuilder getCryptoAttrRequestBuilder();

    HashMap<String, String> getCryptoAttrParamsMap();

}