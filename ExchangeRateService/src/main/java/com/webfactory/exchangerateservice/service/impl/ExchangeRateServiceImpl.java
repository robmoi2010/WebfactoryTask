package com.webfactory.exchangerateservice.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.webfactory.exchangerateservice.ExchangeRateServiceApplication;
import com.webfactory.exchangerateservice.exception.ExchangeRateNotFoundException;
import com.webfactory.exchangerateservice.model.ExchangeRate;
import com.webfactory.exchangerateservice.service.ExchangeRateService;
import com.webfactory.exchangerateservice.util.CacheUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("ExchangeRateService")
public class ExchangeRateServiceImpl implements ExchangeRateService {
	final private static Logger logger = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

	@Override
	public void addExchangeRate(ExchangeRate rate) {
		try {
			Object obj = CacheUtil.getFromCache(ExchangeRateServiceApplication.cacheManager,
					ExchangeRateServiceApplication.cacheKey);
			List<ExchangeRate> lRate = null;
			if (obj == null) {
				lRate = new ArrayList<ExchangeRate>();
			} else {
				lRate = (List<ExchangeRate>) obj;
			}
			lRate.add(rate);
			CacheUtil.addToCache(ExchangeRateServiceApplication.cacheManager, ExchangeRateServiceApplication.cacheKey,
					lRate);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	@Override
	public ExchangeRate getLatestRate() throws Exception {
		Object obj = CacheUtil.getFromCache(ExchangeRateServiceApplication.cacheManager,
				ExchangeRateServiceApplication.cacheKey);
		List<ExchangeRate> lRate = null;
		if (obj == null) {
			throw new ExchangeRateNotFoundException("Exchange rate unavailable at this time");
		}
		lRate = (List<ExchangeRate>) obj;
		if (lRate.isEmpty()) {
			throw new ExchangeRateNotFoundException("Exchange rate unavailable at this time");
		}
		Collections.sort(lRate, Comparator.comparing(s -> s.getTime().getUpdatedISO()));
		return lRate.get(lRate.size() - 1);
	}

	@Override
	public List<ExchangeRate> getRateByDatePeriod(Date from, Date to) throws Exception {
		Object obj = CacheUtil.getFromCache(ExchangeRateServiceApplication.cacheManager,
				ExchangeRateServiceApplication.cacheKey);
		List<ExchangeRate> lRate = null;
		if (obj == null) {
			throw new ExchangeRateNotFoundException("Exchange rate unavailable at this time");
		}
		lRate = (List<ExchangeRate>) obj;
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		fromCal.setTime(from);
		toCal.setTime(to);
		fromCal.add(Calendar.DAY_OF_MONTH, -1);
		toCal.add(Calendar.DAY_OF_MONTH, 1);
		lRate = lRate.stream().filter(s -> s.getTime().getUpdatedISO().after(fromCal.getTime())
				&& s.getTime().getUpdatedISO().before(toCal.getTime())).collect(Collectors.toList());
		if (lRate.isEmpty()) {
			throw new ExchangeRateNotFoundException("Exchange rate for " + from + " to " + to + " unavailable");
		}
		return lRate;
	}

}
