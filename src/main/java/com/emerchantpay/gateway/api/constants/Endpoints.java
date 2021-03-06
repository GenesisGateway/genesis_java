package com.emerchantpay.gateway.api.constants;

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

import java.io.Serializable;

public class Endpoints implements Serializable {

    private String endpointName;

    // Domain for E-ComProcessing's Genesis instance
    public static Endpoints ECOMPROCESSING = new Endpoints("e-comprocessing.net");

    // Domain for Emerchantpay's Genesis instance
    public static Endpoints EMERCHANTPAY = new Endpoints("emerchantpay.net");

    // SCA Checker endpoint
    public static Endpoints SCA_CHECKER = new Endpoints("sca/checker");
    public static String SCA_CHECKER_API_VERSION = "v1";

    //3DS continue method endpoint
    public static Endpoints THREEDS_METHOD = new Endpoints("threeds/threeds_method");

    public Endpoints(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getEndpointName() {
        return this.endpointName;
    }

    public String toString() {
        return getEndpointName();
    }
}
