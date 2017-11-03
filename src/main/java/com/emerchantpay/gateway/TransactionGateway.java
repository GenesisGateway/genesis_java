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
import com.emerchantpay.gateway.util.NodeWrapper;

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
	NodeWrapper response;

	public TransactionGateway(Configuration configuration, NodeWrapper response) {

		this.configuration = configuration;
		this.response = response;
	}

	public TransactionResult<Transaction> getRequest() {
		return new TransactionResult<Transaction>(response, Transaction.class);
	}
}
