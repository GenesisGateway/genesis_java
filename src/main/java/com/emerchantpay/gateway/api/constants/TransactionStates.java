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

public class TransactionStates {

	// Transaction was approved by the schemes and is successful
	public static String APPROVED = "approved";

	// Transaction was declined by the schemes or risk management
	public static String DECLINED = "declined";

	// The outcome of the transaction could not be determined, e.g. at a timeout
	// situation.
	// Transaction state will eventually change, so make a reconcile after a
	// certain time frame
	public static String PENDING = "pending";

	// An asynchronous transaction (3-D secure payment) has been initiated and
	// is waiting for user input.
	// Updates of this state will be sent to the notification url specified in
	// request
	public static String PENDING_ASYNC = "pending_async";

	// Transaction is in-progress
	public static String IN_PROGRESS = "in_progress";

	// Once an approved transaction is refunded the state changes to refunded
	public static String REFUNDED = "refunded";

	// Transaction was authorized, but later the merchant canceled it
	public static String VOIDED = "voided";

	// An error has occurred while negotiating with the schemes
	public static String ERROR = "error";

	// Transaction failed, but it was not declined
	public static String UNSUCCESSFUL = "unsuccessful";

	// WPF initial status
	public static String NEW_STATUS = "new_status";

	// WPF timeout has occurred
	public static String TIMEOUT = "timeout";

	// Once an approved transaction is chargeback the state changes to change-
	// backed.
	// Chargeback is the state of rejecting an accepted transaction (with funds
	// transferred)
	// by the cardholder or the issuer
	public static String CHARGEBACKED = "chargebacked";

	// Once a chargebacked transaction is charged, the state changes to charge-
	// back reversed.
	// Chargeback has been canceled
	public static String CHARGEBACKED_REVERSED = "chargebacked_reversed";
}
