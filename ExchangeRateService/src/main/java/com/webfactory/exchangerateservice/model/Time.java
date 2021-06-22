package com.webfactory.exchangerateservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {
	@JsonProperty("updated")
	public String getUpdated() {
		return this.updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	String updated;

	@JsonProperty("updatedISO")
	public Date getUpdatedISO() {
		return this.updatedISO;
	}

	public void setUpdatedISO(Date updatedISO) {
		this.updatedISO = updatedISO;
	}

	Date updatedISO;

	@JsonProperty("updateduk")
	public String getUpdateduk() {
		return this.updateduk;
	}

	public void setUpdateduk(String updateduk) {
		this.updateduk = updateduk;
	}

	String updateduk;
}
