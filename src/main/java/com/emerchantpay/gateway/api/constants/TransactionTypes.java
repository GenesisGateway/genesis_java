package com.emerchantpay.gateway.api.constants;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @license     http://opensource.org/licenses/MIT The MIT License
 */

public class TransactionTypes {
	
	// Account Verification
	public static String ACCOUNT_VERIFICATION = "account_verification";

	// Standard Authorization
	public static String AUTHORIZE = "authorize";

	// 3D-Secure Authorization
	public static String AUTHORIZE_3D = "authorize3d";

	// Standard Sale
	public static String SALE = "sale";

	// 3D-Secure Sale
	public static String SALE_3D = "sale3d";

	// Capture settles a transaction which has been authorized before
	public static String CAPTURE = "capture";

	// Refunds allow to return already billed amounts to customers
	public static String REFUND = "refund";

	// Void transactions undo previous transactions
	public static String VOID = "void";

	// Credits (also known as Credit Fund Transfer a.k.a. CFT)
	public static String CREDIT = "credit";

	// Payouts transactions
	public static String PAYOUT = "payout";

	// Standard initial recurring
	public static String INIT_RECURRING_SALE = "init_recurring_sale";

	// 3D-based initial recurring
	public static String INIT_RECURRING_SALE_3D = "init_recurring_sale3d";

	// RecurringSale transaction is a "repeated" transaction which follows and
	// references an initial transaction
	public static String RECURRING_SALE = "recurring_sale";

	// Bank transfer, popular in Netherlands (via ABN)
	public static String ABNIDEAL = "abn_ideal";

	// Voucher-based payment
	public static String CASHU = "cashu";

	// Wallet-based payment
	public static String EZEEWALLET = "ezeewallet";

	// Neteller
	public static String NETELLER = "neteller";

	// POLi is Australia's most popular online real time debit payment system
	public static String POLI = "poli";

	// WebMoney is a global settlement system and environment for online
	// business activities
	public static String WEBMONEY = "webmoney";

	// PayByVouchers via oBeP
	public static String PAYBYVOUCHER_YEEPAY = "paybyvoucher_yeepay";

	// PayByVouchers via Credit/Debit Cards
	public static String PAYBYVOUCHER_SALE = "paybyvoucher_sale";

	// Voucher-based payment
	public static String PAYSAFECARD = "paysafecard";

	// Supports payments via EPS, TeleIngreso, SafetyPay, TrustPay, ELV,
	// Przelewy24, QIWI, and GiroPay
	public static String PPRO = "ppro";

	// Bank transfer payment, popular in Germany
	public static String SOFORT = "sofort";

	public static String INPAY = "inpay";

	public static String P24 = "P24";

	// Sepa Direct Debit
	public static String SDD_SALE = "sdd_sale";
	public static String SDD_INIT_RECURRING_SALE = "sdd_init_recurring_sale";
	public static String SDD_RECURRING_SALE = "sdd_recurring_sale";
	public static String SDD_REFUND = "sdd_refund";
	public static String SCT_PAYOUT = "sct_payout";

	//IDebit
	public static String IDEBIT_PAYIN = "idebit_payin";
	public static String IDEBIT_PAYOUT = "idebit_payout";

	//InstaDebit
	public static String INSTADEBIT_PAYIN = "insta_debit_payin";
	public static String INSTADEBIT_PAYOUT = "insta_debit_payout";
  
	//Citadel
	public static String CITADEL_PAYIN = "citadel_payin";
	public static String CITADEL_PAYOUT = "citadel_payout";

	// PayPal Express Checkout
	public static String PAYPAL_EXPRESS_CHECKOUT = "paypal_express";

	// Trustly
	public static String TRUSTLY_SALE = "trustly_sale";
	public static String TRUSTLY_WITHDRAWAL = "trustly_withdrawal";

	// Earthport
	public static String EARTHPORT = "earthport";

	// Alipay
	public static String ALIPAY = "alipay";

	// Wechat
	public static String WECHAT = "wechat";

	// PaySec
	public static String PAYSEC = "paysec";

	// PaySec Payout
	public static String PAYSEC_PAYOUT = "paysec_payout";

	// RPN
	public static String RPN = "rpn_payment";

	// Gift Cards
	public static String FASHIONCHEQUE = "fashioncheque";
	public static String INTERSOLVE = "intersolve";
	public static String TCS = "container_store";
}
