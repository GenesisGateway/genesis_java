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

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

@Getter
public class Environments implements Serializable {

	private final String environmentName;

	// Production Environment (empty subdomain or "prod" when necessary)
	public static final Environments PRODUCTION = new Environments("prod");

	// Staging Environment
	public static final Environments STAGING = new Environments("staging");

	// Define services that require "prod" subdomain in production
	public static final Set<EndpointApiTypes> SERVICES_WITH_PROD_SUBDOMAIN = EnumSet.of(EndpointApiTypes.API);

	public Environments(String environmentName) {
		this.environmentName = environmentName;
	}

	public String toString() {
		return getEnvironmentName();
	}
}
