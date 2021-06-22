package com.webfactory.exchangerateservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bpi {
	@JsonProperty("USD")
	public USD getUSD() {
		return this.uSD;
	}

	public void setUSD(USD uSD) {
		this.uSD = uSD;
	}

	USD uSD;
}
