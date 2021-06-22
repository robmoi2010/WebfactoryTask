package com.webfactory.exchangerateservice.service;

import java.util.Date;
import java.util.List;

import com.webfactory.exchangerateservice.model.ExchangeRate;

public interface ExchangeRateService {
	public void addExchangeRate(ExchangeRate rate);

	public ExchangeRate getLatestRate() throws Exception;

	public List<ExchangeRate> getRateByDatePeriod(Date from, Date to) throws Exception;
}
