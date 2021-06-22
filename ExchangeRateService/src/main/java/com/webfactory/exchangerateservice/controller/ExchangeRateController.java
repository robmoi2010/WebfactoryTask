package com.webfactory.exchangerateservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webfactory.exchangerateservice.exception.ExchangeRateNotFoundException;
import com.webfactory.exchangerateservice.exception.GeneralException;
import com.webfactory.exchangerateservice.model.ExchangeRate;
import com.webfactory.exchangerateservice.service.ExchangeRateService;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {
	@Autowired
	ExchangeRateService exchangeRateService;
	final private static Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/latest", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ExchangeRate getLatestRate() {
		ExchangeRate rate = new ExchangeRate();
		try {
			rate = exchangeRateService.getLatestRate();
		} catch (ExchangeRateNotFoundException e) {
			logger.error(e.toString());
			throw e;
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GeneralException("Server error, try again later");
		}
		return rate;
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/filtered", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ExchangeRate> getRateFilteredRates(
			@RequestParam(name = "from", required = true) Date from,
			@RequestParam(name = "to", required = true) Date to) {
		List<ExchangeRate> rates = new ArrayList<ExchangeRate>();
		try {
			rates = exchangeRateService.getRateByDatePeriod(from, to);
		} catch (ExchangeRateNotFoundException e) {
			logger.error(e.toString());
			throw e;
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GeneralException("Server error, try again later");
		}
		return rates;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody String test() {
		return "Test successful";
	}
}
