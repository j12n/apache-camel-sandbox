package com.elasticpath.camel.test.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test camel rest routes
 */
public class BranchRouteTest extends CamelSpringTestSupport {
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("branch-route.xml");
	}

	@Test
	public void BranchRouteOption1() throws InterruptedException, UnsupportedEncodingException {
		MockEndpoint end = getMockEndpoint("mock:finish");
		end.setExpectedMessageCount(1);

		MockEndpoint branch1 = getMockEndpoint("mock:branch1");
		branch1.setExpectedMessageCount(1);

		MockEndpoint branch2 = getMockEndpoint("mock:branch2");
		branch2.setExpectedMessageCount(0);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("dealerCode", "1000");

		sendBody("direct:startBranchRoute", null, headers);
		end.assertIsSatisfied();
		branch1.assertIsSatisfied();
		branch2.assertIsSatisfied();

		System.out.println(end.getReceivedExchanges().get(0).getIn().getBody());
	}

	@Test
	public void BranchRouteOption2() throws InterruptedException, UnsupportedEncodingException {
		MockEndpoint end = getMockEndpoint("mock:finish");
		end.setExpectedMessageCount(1);

		MockEndpoint branch1 = getMockEndpoint("mock:branch1");
		branch1.setExpectedMessageCount(0);

		MockEndpoint branch2 = getMockEndpoint("mock:branch2");
		branch2.setExpectedMessageCount(1);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("dealerCode", "-1");

		sendBody("direct:startBranchRoute", null, headers);
		end.assertIsSatisfied();
		branch1.assertIsSatisfied();
		branch2.assertIsSatisfied();

		System.out.println(end.getReceivedExchanges().get(0).getIn().getBody());
	}
}