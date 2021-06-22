package com.webfactory.exchangerateservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.webfactory.exchangerateservice.AbstractTest;
import com.webfactory.exchangerateservice.model.ExchangeRate;

public class ExchangeRateControllerTests extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void latestTest() throws Exception {
		String uri = "/api/latest";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		ExchangeRate rate = super.mapFromJson(content, ExchangeRate.class);
		assertTrue(rate != null);
	}
}
