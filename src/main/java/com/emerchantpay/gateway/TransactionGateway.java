package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.NotificationResult;
import com.emerchantpay.gateway.api.TransactionResult;
import com.emerchantpay.gateway.api.requests.financial.CaptureRequest;
import com.emerchantpay.gateway.api.requests.financial.RefundRequest;
import com.emerchantpay.gateway.api.requests.financial.VoidRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.*;
import com.emerchantpay.gateway.api.requests.financial.card.*;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSale3DRequest;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.RecurringSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.IDebitPayOutRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.InstaDebitPayInRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.InstaDebitPayOutRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.AlipayRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.WechatRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.PaySecRequest;
import com.emerchantpay.gateway.api.requests.financial.oBeP.PaySecPayoutRequest;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVYeePayRequest;
import com.emerchantpay.gateway.api.requests.financial.sct.SCTPayoutRequest;
import com.emerchantpay.gateway.api.requests.financial.sdd.*;
import com.emerchantpay.gateway.api.requests.financial.wallets.EzeewalletRequest;
import com.emerchantpay.gateway.api.requests.financial.wallets.NetellerRequest;
import com.emerchantpay.gateway.api.requests.financial.wallets.WebMoneyRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.AVSRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.AccountVerificationRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.BlacklistRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ChargebackByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ChargebackRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ReportsByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.ReportsRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.RetrievalByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.fraud.RetrievalRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileByDateRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.reconcile.ReconcileRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFCreateRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFReconcileRequest;
import com.emerchantpay.gateway.model.Notification;
import com.emerchantpay.gateway.model.Transaction;
import com.emerchantpay.gateway.model.WPFNotification;
import com.emerchantpay.gateway.util.Configuration;

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

public class TransactionGateway {
	Configuration configuration;

	public TransactionGateway(Configuration configuration) {

		this.configuration = configuration;
	}

	public TransactionResult<Transaction> getAuthorize(AuthorizeRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getAuthorize3D(Authorize3DRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getCredit(CreditRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getSale(SaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getSale3D(Sale3DRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getCapture(CaptureRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getRefund(RefundRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getVoid(VoidRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getPayout(PayoutRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getInitRecurringSale(InitRecurringSaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getInitRecurringSale3D(InitRecurringSale3DRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getRecurringSale(RecurringSaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getEzeewallet(EzeewalletRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getWebMoney(WebMoneyRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getNeteller(NetellerRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getWPFCreate(WPFCreateRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getWPFReconcile(WPFReconcileRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getPBVYeePay(PBVYeePayRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getPBVSale(PBVSaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getAbnIDeal(AbnIDealRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getCashU(CashURequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getInPay(InPayRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getPaySafeCard(PaySafeCardRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getP24(P24Request request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getPOLi(POLiRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getPPro(PProRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getSofort(SofortRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getAccountVerification(AccountVerificationRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getAVS(AVSRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getBlacklist(BlacklistRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getReconcile(ReconcileRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getReconcileByDate(ReconcileByDateRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getChargeback(ChargebackRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getChargebackByDate(ChargebackByDateRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getReports(ReportsRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getReportsByDate(ReportsByDateRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getRetrieval(RetrievalRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getRetrievalByDate(RetrievalByDateRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public NotificationResult<Notification> getNotification(NotificationGateway request) {

		return new NotificationResult<Notification>(request.getResponse(), Notification.class);
	}

	public NotificationResult<WPFNotification> getWPFNotification(WPFNotificationGateway request) {

		return new NotificationResult<WPFNotification>(request.getResponse(), WPFNotification.class);
	}

	// SDD
	public TransactionResult<Transaction> getSDDSale(SDDSaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getSDDInitRecurring(SDDInitRecurringSaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getSDDRecurring(SDDRecurringSaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getSDDRefund(SDDRefundRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// SCT
	public TransactionResult<Transaction> getSCTPayout(SCTPayoutRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// IDebit
	public TransactionResult<Transaction> getIDebitPayIn(IDebitPayInRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getIDebitPayOut(IDebitPayOutRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// InstaDebit
	public TransactionResult<Transaction> getInstaDebitPayIn(InstaDebitPayInRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getInstaDebitPayOut(InstaDebitPayOutRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// Citadel
	public TransactionResult<Transaction> getCitadelPayIn(CitadelPayInRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getCitadelPayOut(CitadelPayOutRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// PayPal Express Checkout
	public TransactionResult<Transaction> getPayPalExpress(PayPalExpressRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// Trustly
	public TransactionResult<Transaction> getTrustlySale(TrustlySaleRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	public TransactionResult<Transaction> getTrustlyWithdrawal(TrustlyWithdrawalRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// Earthport
	public TransactionResult<Transaction> getEarthport(EarthportRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// Alipay
	public TransactionResult<Transaction> getAliPay(AlipayRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// Wechat
	public TransactionResult<Transaction> getWechat(WechatRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// PaySec
	public TransactionResult<Transaction> getPaySec(PaySecRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}

	// PaySec Payout
	public TransactionResult<Transaction> getPaySecPayout(PaySecPayoutRequest request) {

		return new TransactionResult<Transaction>(request.getResponse(), Transaction.class);
	}
}
