package com.emerchantpay.gateway.api.requests.nonfinancial;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.validation.GenesisValidator;
import com.emerchantpay.gateway.api.validation.RequiredParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class BlacklistRequest extends Request {

	private String cardnumber;
	private String terminalToken;

	// Required params
	private HashMap<String, String> requiredParams = new HashMap<String, String>();

	// GenesisValidator
	private GenesisValidator validator = new GenesisValidator();

	public BlacklistRequest() {
		super();
	}

	public BlacklistRequest setCardNumber(String cardnumber) {
		this.cardnumber = cardnumber;
		return this;
	}

	public BlacklistRequest setTerminalToken(String terminalToken) {
		this.terminalToken = terminalToken;
		return this;
	}

	@Override
	public String getTransactionType() {
		return "blacklist";
	}

	@Override
	public String toXML() {
		return buildRequest("blacklist_request").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		// Set required params
		requiredParams.put(RequiredParameters.cardNumber, cardnumber);

		// Validate request
		validator.isValidRequest(requiredParams);

		return new RequestBuilder(root).addElement("card_number", cardnumber).addElement("terminal_token",
				terminalToken);
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}
