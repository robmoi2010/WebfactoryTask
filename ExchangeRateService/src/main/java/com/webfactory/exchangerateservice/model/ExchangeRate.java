package com.webfactory.exchangerateservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRate {
	Time time;
	String disclaimer;
	Bpi bpi;

	@JsonProperty("time")
	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	@JsonProperty("disclaimer")
	public String getDisclaimer() {
		return this.disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	@JsonProperty("bpi")
	public Bpi getBpi() {
		return this.bpi;
	}

	public void setBpi(Bpi bpi) {
		this.bpi = bpi;
	}
}
