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
public class SimpleRestTest extends CamelSpringTestSupport {
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("simple-rest.xml");
	}

	@Test
	public void SimpleRestRouteInline() throws InterruptedException, UnsupportedEncodingException {
		MockEndpoint end = getMockEndpoint("mock:finish");
		end.setExpectedMessageCount(1);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("searchHost", "ajax.googleapis.com");
		headers.put("searchPort", "80");
		headers.put("searchVersion", URLEncoder.encode("1.0", "UTF-8"));
		headers.put("searchQuery", URLEncoder.encode("elasticpath", "UTF-8"));

		sendBody("direct:startSearchInline", null, headers);
		end.assertIsSatisfied();

		System.out.println(end.getReceivedExchanges().get(0).getIn().getBody());
	}

	@Test
	public void SimpleRestRouteWithProperties() throws InterruptedException, UnsupportedEncodingException {
		MockEndpoint end = getMockEndpoint("mock:finish");
		end.setExpectedMessageCount(1);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("searchVersion", URLEncoder.encode("1.0", "UTF-8"));
		headers.put("searchQuery", URLEncoder.encode("elasticpath", "UTF-8"));

		sendBody("direct:startSearchExtProperties", null, headers);
		end.assertIsSatisfied();

		System.out.println(end.getReceivedExchanges().get(0).getIn().getBody());
	}
}