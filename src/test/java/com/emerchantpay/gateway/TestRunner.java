package com.emerchantpay.gateway;

import com.emerchantpay.gateway.apm.*;
import com.emerchantpay.gateway.card.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(ConfigurationTest.class, RequestBuilderTest.class, EnvironmentsTest.class,
				GenesisClientTest.class, TransactionGatewayTest.class, AddressTest.class, NotificationTest.class,
				RequestBuilderTest.class, RequestBuilderWithAttributeTest.class, TransformExponentToAmountTest.class,
				TransformAmountToExponentTest.class, WPFNotificationTest.class, SDDRequestsTest.class,
				InstaDebitTest.class, IDebitTest.class, AbnIDealTest.class, CashUTest.class, CitadelTest.class,
				PBVTest.class, POLiTest.class, PProPayTest.class, TrustlyTest.class, WalletsTest.class, P24Test.class,
				SofortTest.class, AuthorizeTest.class, Authorize3DTest.class, SaleTest.class, Sale3DTest.class,
				CreditTest.class, PayoutTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
