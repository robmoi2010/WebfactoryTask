package com.webfactory.exchangerateservice.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webfactory.exchangerateservice.model.ExchangeRate;
import com.webfactory.exchangerateservice.service.ExchangeRateService;
import com.webfactory.exchangerateservice.webclient.DataType;
import com.webfactory.exchangerateservice.webclient.RequestType;
import com.webfactory.exchangerateservice.webclient.RestWebClient;

@Component
@EnableAsync
public class Schedule {
	final private static Logger logger = LoggerFactory.getLogger(Schedule.class);

	@Autowired
	ExchangeRateService exchangeRateService;

	@Value("${application.custom.exchange-rate-url}")
	private String url;

	@Async
	@Scheduled(cron = "${cron.expression}")
	public void fetchExchangeRates() {
		try {
			RestWebClient client = new RestWebClient();
			client.setDataType(DataType.JSON);
			client.setRequestType(RequestType.GET);
			client.setUrl(url);
			String data = client.sendRequest();
			ExchangeRate rate = new ObjectMapper().readValue(data, ExchangeRate.class);
			exchangeRateService.addExchangeRate(rate);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
