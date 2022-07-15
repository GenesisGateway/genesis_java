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

import java.util.ArrayList;
import java.util.Arrays;

public class TransactionTypes {

    // Account Verification
    public static final String ACCOUNT_VERIFICATION = "account_verification";

    // Standard Authorization
    public static final String AUTHORIZE = "authorize";

    // 3D-Secure Authorization
    public static final String AUTHORIZE_3D = "authorize3d";

    // Standard Sale
    public static final String SALE = "sale";

    // 3D-Secure Sale
    public static final String SALE_3D = "sale3d";

    // Capture settles a transaction which has been authorized before
    public static final String CAPTURE = "capture";

    // Refunds allow to return already billed amounts to customers
    public static final String REFUND = "refund";

    // Void transactions undo previous transactions
    public static final String VOID = "void";

    // Credits (also known as Credit Fund Transfer a.k.a. CFT)
    public static final String CREDIT = "credit";

    // Payouts transactions
    public static final String PAYOUT = "payout";

    // Standard initial recurring
    public static final String INIT_RECURRING_SALE = "init_recurring_sale";

    // 3D-based initial recurring
    public static final String INIT_RECURRING_SALE_3D = "init_recurring_sale3d";

    // RecurringSale transaction is a "repeated" transaction which follows and
    // references an initial transaction
    public static final String RECURRING_SALE = "recurring_sale";

    // Bank transfer, popular in Netherlands (via ABN)
    public static final String ABNIDEAL = "abn_ideal";

    // APM used to process Mobile network operator payments (African Mobile Payout, otherwise known as Disbursement)
    public static final String AFRICAN_MOBILE_PAYOUT = "african_mobile_payout";

    // APM used to process Mobile network operator payments
    public static final String AFRICAN_MOBILE_SALE = "african_mobile_sale";

    // Voucher-based payment
    public static final String CASHU = "cashu";

    // Wallet-based payment
    public static final String EZEEWALLET = "ezeewallet";

    // Neteller
    public static final String NETELLER = "neteller";

    // POLi is Australia's most popular online real time debit payment system
    public static final String POLI = "poli";

    // WebMoney is a global settlement system and environment for online
    // business activities
    public static final String WEBMONEY = "webmoney";

    // PayByVouchers via oBeP
    public static final String PAYBYVOUCHER_YEEPAY = "paybyvoucher_yeepay";

    // PayByVouchers via Credit/Debit Cards
    public static final String PAYBYVOUCHER_SALE = "paybyvoucher_sale";

    // Voucher-based payment
    public static final String PAYSAFECARD = "paysafecard";

    // Supports payments via EPS, TeleIngreso, SafetyPay, TrustPay, ELV,
    // Przelewy24, QIWI, and GiroPay
    public static final String PPRO = "ppro";

    public static final String EPS = "eps";
    public static final String GIROPAY = "giropay";
    public static final String SAFETYPAY = "safetypay";
    public static final String TRUSTPAY = "trustpay";

    public static final String POST_FINANCE = "post_finance";

    // Bank transfer payment, popular in Germany
    public static final String SOFORT = "sofort";

    public static final String INPAY = "inpay";

    public static final String P24 = "p24";

    // Sepa Direct Debit
    public static final String SDD_SALE = "sdd_sale";
    public static final String SDD_INIT_RECURRING_SALE = "sdd_init_recurring_sale";
    public static final String SDD_RECURRING_SALE = "sdd_recurring_sale";
    public static final String SDD_REFUND = "sdd_refund";
    public static final String SCT_PAYOUT = "sct_payout";

    //IDebit
    public static final String IDEBIT_PAYIN = "idebit_payin";
    public static final String IDEBIT_PAYOUT = "idebit_payout";

    //InstaDebit
    public static final String INSTADEBIT_PAYIN = "insta_debit_payin";
    public static final String INSTADEBIT_PAYOUT = "insta_debit_payout";

    //Citadel
    public static final String CITADEL_PAYIN = "citadel_payin";
    public static final String CITADEL_PAYOUT = "citadel_payout";

    // PayPal Express Checkout
    public static final String PAYPAL_EXPRESS_CHECKOUT = "paypal_express";

    // Trustly
    public static final String TRUSTLY_SALE = "trustly_sale";
    public static final String TRUSTLY_WITHDRAWAL = "trustly_withdrawal";

    // Earthport
    public static final String EARTHPORT = "earthport";

    // Alipay
    public static final String ALIPAY = "alipay";

    // Wechat
    public static final String WECHAT = "wechat";

    // PaySec
    public static final String PAYSEC = "paysec";

    // PaySec Payout
    public static final String PAYSEC_PAYOUT = "paysec_payout";

    // RPN
    public static final String RPN = "rpn_payment";

    // RPN Payout
    public static final String RPN_PAYOUT = "rpn_payout";

    // Gift Cards
    public static final String FASHIONCHEQUE = "fashioncheque";
    public static final String INTERSOLVE = "intersolve";
    public static final String TCS = "container_store";

    // Klarna
    public static final String KLARNA_AUTHORIZE = "klarna_authorize";
    public static final String KLARNA_CAPTURE = "klarna_capture";
    public static final String KLARNA_REFUND = "klarna refund";

    // Apple Pay
    public static final String APPLE_PAY = "apple_pay";

    // Google Pay
    public static final String GOOGLE_PAY = "google_pay";

    //UPI (Unified Payment Interface)
    public static final String UPI = "upi";

    //PayU
    public static final String PAYU = "payu";

    //Neosurf
    public static final String NEOSURF = "neosurf";

    //South American oBeP
    public static final String BANCO_DO_BRASIL = "banco_do_brasil";
    public static final String BANCOMER = "bancomer";
    public static final String BRADESCO = "bradesco";
    public static final String DAVIVIENDA = "davivienda";
    public static final String ITAU = "itau";
    public static final String PSE = "pse";
    public static final String RAPI_PAGO = "rapi_pago";
    public static final String SANTANDER = "santander";
    public static final String WEBPAY = "webpay";

    //South American Payments
    public static final String TARJETA_SHOPPING = "tarjeta_shopping";
    public static final String ARGENCARD = "argencard";
    public static final String AURA = "aura";
    public static final String CABAL = "cabal";
    public static final String ELO = "elo";
    public static final String CENCOSUD = "cencosud";
    public static final String NATIVA = "nativa";
    public static final String NARANJA = "naranja";

    //South American Cash Payments
    public static final String BALOTO = "baloto";
    public static final String BANCO_DE_OCCIDENTE = "banco_de_occidente";
    public static final String BOLETO = "boleto";
    public static final String EFECTY = "efecty";
    public static final String OXXO = "oxxo";
    public static final String PAGO_FACIL = "pago_facil";
    public static final String REDPAGOS = "redpagos";


    public static final String BANCONTACT = "bcmc";
    public static final String BITPAY_PAYOUT = "bitpay_payout";
    public static final String BITPAY_SALE = "bitpay_sale";
    public static final String IDEAL = "ideal";
    public static final String MULTIBANCO = "multibanco";
    public static final String MY_BANK = "my_bank";
    public static final String ONLINE_BANKING = "online_banking";

    //Preauthorizations
    public static final String INCREMENTAL_AUTHORIZE = "incremental_authorize";
    public static final String PARTIAL_REVERSAL = "partial_reversal";

    //Reconcile
    public static final String RECONCILE = "reconcile";
    public static final String RECONCILE_BY_DATE = "reconcile_by_date";
    public static final String WPF_RECONCILE = "wpf_reconcile";

    public static ArrayList<String> getWPFTransactionTypes() {
        ArrayList<String> transactionTypes = new ArrayList<String>();

        transactionTypes.addAll(Arrays.asList(new String[]{
                ABNIDEAL,
                ACCOUNT_VERIFICATION,
                ALIPAY,
                ARGENCARD,
                AURA,
                AUTHORIZE,
                AUTHORIZE_3D,
                BALOTO,
                BANCO_DE_OCCIDENTE,
                BANCO_DO_BRASIL,
                BANCOMER,
                BANCONTACT,
                BITPAY_PAYOUT,
                BITPAY_SALE,
                BOLETO,
                BRADESCO,
                CABAL,
                CASHU,
                CENCOSUD,
                CITADEL_PAYIN,
                DAVIVIENDA,
                EFECTY,
                ELO,
                EPS,
                EZEEWALLET,
                FASHIONCHEQUE,
                GIROPAY,
                IDEAL,
                IDEBIT_PAYIN,
                INIT_RECURRING_SALE,
                INIT_RECURRING_SALE_3D,
                INPAY,
                INSTADEBIT_PAYIN,
                INTERSOLVE,
                ITAU,
                KLARNA_AUTHORIZE,
                MULTIBANCO,
                MY_BANK,
                NARANJA,
                NATIVA,
                NEOSURF,
                NETELLER,
                ONLINE_BANKING,
                OXXO,
                P24,
                PAGO_FACIL,
                PAYBYVOUCHER_YEEPAY,
                PAYPAL_EXPRESS_CHECKOUT,
                PAYSAFECARD,
                PAYSEC,
                PAYSEC_PAYOUT,
                PAYU,
                POLI,
                POST_FINANCE,
                PPRO,
                PSE,
                RAPI_PAGO,
                REDPAGOS,
                SAFETYPAY,
                SALE,
                SALE_3D,
                SANTANDER,
                SDD_INIT_RECURRING_SALE,
                SDD_SALE,
                SOFORT,
                TARJETA_SHOPPING,
                TCS,
                TRUSTLY_SALE,
                TRUSTPAY,
                UPI,
                WEBMONEY,
                WEBPAY,
                WECHAT,
                APPLE_PAY,
                GOOGLE_PAY
        }));

        return transactionTypes;
    }
}