package com.emerchantpay.gateway.api.requests.nonfinancial.fraud;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;

import java.util.List;
import java.util.Map;

public class ChargebackByDateRequest extends Request {

	private String startdate;
	private String enddate;
	private Integer page;

	public ChargebackByDateRequest() {
		super();
	}

	public ChargebackByDateRequest setStartDate(String startdate) {
		this.startdate = startdate;
		return this;
	}

	public ChargebackByDateRequest setEndDate(String enddate) {
		this.enddate = enddate;
		return this;
	}

	public ChargebackByDateRequest setPage(Integer page) {
		this.page = page;
		return this;
	}

	@Override
	public String getTransactionType() {
		return "chargeback_by_date";
	}

	@Override
	public String toXML() {
		return buildRequest("chargeback_request").toXML();
	}

	@Override
	public String toQueryString(String root) {
		return buildRequest(root).toQueryString();
	}

	protected RequestBuilder buildRequest(String root) {

		return new RequestBuilder(root).addElement("start_date", startdate).addElement("end_date", enddate)
				.addElement("page", page);
	}

	public List<Map.Entry<String, Object>> getElements() {
		return buildRequest("payment_transaction").getElements();
	}
}
