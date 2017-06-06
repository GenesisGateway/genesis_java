package com.emerchantpay.gateway;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.requests.financial.CaptureRequest;
import com.emerchantpay.gateway.api.requests.financial.RefundRequest;
import com.emerchantpay.gateway.api.requests.financial.VoidRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.CashURequest;
import com.emerchantpay.gateway.api.requests.financial.apm.InPayRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.P24Request;
import com.emerchantpay.gateway.api.requests.financial.apm.POLiRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.PProRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.PaySafeCardRequest;
import com.emerchantpay.gateway.api.requests.financial.apm.SofortRequest;
import com.emerchantpay.gateway.api.requests.financial.card.Authorize3DRequest;
import com.emerchantpay.gateway.api.requests.financial.card.AuthorizeRequest;
import com.emerchantpay.gateway.api.requests.financial.card.CreditRequest;
import com.emerchantpay.gateway.api.requests.financial.card.PayoutRequest;
import com.emerchantpay.gateway.api.requests.financial.card.Sale3DRequest;
import com.emerchantpay.gateway.api.requests.financial.card.SaleRequest;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSale3DRequest;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.InitRecurringSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.card.recurring.RecurringSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.pbv.PBVYeePayRequest;
import com.emerchantpay.gateway.api.requests.financial.sdd.SDDInitRecurringSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.sdd.SDDRecurringSaleRequest;
import com.emerchantpay.gateway.api.requests.financial.sdd.SDDRefundRequest;
import com.emerchantpay.gateway.api.requests.financial.sdd.SDDSaleRequest;
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
import com.emerchantpay.gateway.api.requests.nonfinancial.retrieve.AbnIDealBanksRetrieveRequest;
import com.emerchantpay.gateway.api.requests.nonfinancial.retrieve.InPayBanksRetrieveRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFCreateRequest;
import com.emerchantpay.gateway.api.requests.wpf.WPFReconcileRequest;
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

public class GenesisClient extends Request {

	private Configuration configuration;

	// Financial Requests
	private AuthorizeRequest authorizeRequest;
	private Authorize3DRequest authorize3dRequest;
	private CaptureRequest captureRequest;
	private RefundRequest refundRequest;
	private VoidRequest voidRequest;
	private CreditRequest creditRequest;
	private PayoutRequest payoutRequest;
	private SaleRequest saleRequest;
	private Sale3DRequest sale3dRequest;
	private InitRecurringSaleRequest initrecurringsaleRequest;
	private InitRecurringSale3DRequest initrecurringsale3dRequest;
	private RecurringSaleRequest recurringsaleRequest;
	private EzeewalletRequest ezeewalletRequest;
	private NetellerRequest netellerRequest;
	private WebMoneyRequest webmoneyRequest;
	private PBVYeePayRequest pbvyeepayRequest;
	private PBVSaleRequest pbvsaleRequest;
	private CashURequest cashuRequest;
	private InPayRequest inpayRequest;
	private PaySafeCardRequest paysafecardRequest;
	private POLiRequest poliRequest;
	private PProRequest pproRequest;
	private SofortRequest sofortRequest;
	private P24Request p24Request;
	private SDDSaleRequest sddsaleRequest;
	private SDDRecurringSaleRequest sddrecurringsaleRequest;
	private SDDInitRecurringSaleRequest sddinitrecurringsaleRequest;
	private SDDRefundRequest sddrefundRequest;

	// Nonfinancial Requests
	private AccountVerificationRequest accountverificationRequest;
	private AVSRequest avsRequest;
	private BlacklistRequest blacklistRequest;
	private ChargebackRequest chargebackRequest;
	private ChargebackByDateRequest chargebackbydateRequest;
	private ReportsRequest reportsRequest;
	private ReportsByDateRequest reportsbydateRequest;
	private RetrievalRequest retrievalRequest;
	private RetrievalByDateRequest retrievalbydateRequest;
	private ReconcileRequest reconcileRequest;
	private ReconcileByDateRequest reconcilebydateRequest;
	private AbnIDealBanksRetrieveRequest abnidealbanksRequest;
	private InPayBanksRetrieveRequest inpaybanksRequest;

	// WPF Requests
	private WPFCreateRequest wpfcreateRequest;
	private WPFReconcileRequest wpfreconcileRequest;

	public GenesisClient(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public AuthorizeRequest setAuthorize() {
		authorizeRequest = new AuthorizeRequest();
		return authorizeRequest;
	}

	public Authorize3DRequest setAuthorize3D() {
		authorize3dRequest = new Authorize3DRequest(configuration);
		return authorize3dRequest;
	}

	public CaptureRequest setCapture() {
		captureRequest = new CaptureRequest(configuration);
		return captureRequest;
	}

	public RefundRequest setRefund() {
		refundRequest = new RefundRequest(configuration);
		return refundRequest;
	}

	public VoidRequest setVoid() {
		voidRequest = new VoidRequest(configuration);
		return voidRequest;
	}

	public CreditRequest setCredit() {
		creditRequest = new CreditRequest(configuration);
		return creditRequest;
	}

	public PayoutRequest setPayout() {
		payoutRequest = new PayoutRequest(configuration);
		return payoutRequest;
	}

	public SaleRequest setSale() {
		saleRequest = new SaleRequest(configuration);
		return saleRequest;
	}

	public Sale3DRequest setSale3D() {
		sale3dRequest = new Sale3DRequest(configuration);
		return sale3dRequest;
	}

	public InitRecurringSaleRequest setInitRecurringSale() {
		initrecurringsaleRequest = new InitRecurringSaleRequest(configuration);
		return initrecurringsaleRequest;
	}

	public InitRecurringSale3DRequest setInitRecurringSale3D() {
		initrecurringsale3dRequest = new InitRecurringSale3DRequest(configuration);
		return initrecurringsale3dRequest;
	}

	public RecurringSaleRequest setRecurringSale() {
		recurringsaleRequest = new RecurringSaleRequest(configuration);
		return recurringsaleRequest;
	}

	public EzeewalletRequest setEzeewallet() {
		ezeewalletRequest = new EzeewalletRequest(configuration);
		return ezeewalletRequest;
	}

	public NetellerRequest setNeteller() {
		netellerRequest = new NetellerRequest(configuration);
		return netellerRequest;
	}

	public WebMoneyRequest setWebmoney() {
		webmoneyRequest = new WebMoneyRequest(configuration);
		return webmoneyRequest;
	}

	public PBVYeePayRequest setPBVYeePay() {
		pbvyeepayRequest = new PBVYeePayRequest(configuration);
		return pbvyeepayRequest;
	}

	public PBVSaleRequest setPBVSale() {
		pbvsaleRequest = new PBVSaleRequest(configuration);
		return pbvsaleRequest;
	}

	public CashURequest setCashU() {
		cashuRequest = new CashURequest(configuration);
		return cashuRequest;
	}

	public InPayRequest setInPay() {
		inpayRequest = new InPayRequest(configuration);
		return inpayRequest;
	}

	public PaySafeCardRequest setPaySafeCard() {
		paysafecardRequest = new PaySafeCardRequest(configuration);
		return paysafecardRequest;
	}

	public PProRequest setPPro() {
		pproRequest = new PProRequest(configuration);
		return pproRequest;
	}

	public POLiRequest setPOLi() {
		poliRequest = new POLiRequest(configuration);
		return poliRequest;
	}

	public SofortRequest setSofort() {
		sofortRequest = new SofortRequest(configuration);
		return sofortRequest;
	}

	public P24Request setP24() {
		p24Request = new P24Request(configuration);
		return p24Request;
	}

	public SDDSaleRequest setSDDSale() {
		sddsaleRequest = new SDDSaleRequest(configuration);
		return sddsaleRequest;
	}

	public SDDRecurringSaleRequest setSDDRecurringSale() {
		sddrecurringsaleRequest = new SDDRecurringSaleRequest(configuration);
		return sddrecurringsaleRequest;
	}

	public SDDInitRecurringSaleRequest setSDDInitRecurringSale() {
		sddinitrecurringsaleRequest = new SDDInitRecurringSaleRequest(configuration);
		return sddinitrecurringsaleRequest;
	}

	public SDDRefundRequest setSDDRefundSale() {
		sddrefundRequest = new SDDRefundRequest(configuration);
		return sddrefundRequest;
	}

	// Nonfinancial Requests
	public AccountVerificationRequest setAccountVerification() {
		accountverificationRequest = new AccountVerificationRequest(configuration);
		return accountverificationRequest;
	}

	public AVSRequest setAVS() {
		avsRequest = new AVSRequest(configuration);
		return avsRequest;
	}

	public BlacklistRequest setBlacklist() {
		blacklistRequest = new BlacklistRequest(configuration);
		return blacklistRequest;
	}

	public ChargebackRequest setChargeback() {
		chargebackRequest = new ChargebackRequest(configuration);
		return chargebackRequest;
	}

	public ChargebackByDateRequest setChargebackByDate() {
		chargebackbydateRequest = new ChargebackByDateRequest(configuration);
		return chargebackbydateRequest;
	}

	public ReportsRequest setReports() {
		reportsRequest = new ReportsRequest(configuration);
		return reportsRequest;
	}

	public ReportsByDateRequest setReportsByDate() {
		reportsbydateRequest = new ReportsByDateRequest(configuration);
		return reportsbydateRequest;
	}

	public RetrievalRequest setRetrieval() {
		retrievalRequest = new RetrievalRequest(configuration);
		return retrievalRequest;
	}

	public RetrievalByDateRequest setRetrievalByDate() {
		retrievalbydateRequest = new RetrievalByDateRequest(configuration);
		return retrievalbydateRequest;
	}

	public ReconcileRequest setReconcile() {
		reconcileRequest = new ReconcileRequest(configuration);
		return reconcileRequest;
	}

	public ReconcileByDateRequest setReconcileByDate() {
		reconcilebydateRequest = new ReconcileByDateRequest(configuration);
		return reconcilebydateRequest;
	}

	public AbnIDealBanksRetrieveRequest setAbnIdealBanks() {
		abnidealbanksRequest = new AbnIDealBanksRetrieveRequest(configuration);
		return abnidealbanksRequest;
	}

	public InPayBanksRetrieveRequest setInPayBanks() {
		inpaybanksRequest = new InPayBanksRetrieveRequest(configuration);
		return inpaybanksRequest;
	}

	// WPF Requests;
	public WPFCreateRequest setWPFCreate() {
		wpfcreateRequest = new WPFCreateRequest(configuration);
		return wpfcreateRequest;
	}

	public WPFReconcileRequest setWPFReconcile() {
		wpfreconcileRequest = new WPFReconcileRequest(configuration);
		return wpfreconcileRequest;
	}

	public TransactionGateway getTransaction() {

		return new TransactionGateway(configuration);
	}
}
