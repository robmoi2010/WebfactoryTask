package com.webfactory.exchangerateservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class USD {
	@JsonProperty("code") 
	 public String getCode() { 
			 return this.code; } 
	 public void setCode(String code) { 
			 this.code = code; } 
	 String code;
	 @JsonProperty("rate") 
	 public String getRate() { 
			 return this.rate; } 
	 public void setRate(String rate) { 
			 this.rate = rate; } 
	 String rate;
	 @JsonProperty("description") 
	 public String getDescription() { 
			 return this.description; } 
	 public void setDescription(String description) { 
			 this.description = description; } 
	 String description;
	 @JsonProperty("rate_float") 
	 public double getRate_float() { 
			 return this.rate_float; } 
	 public void setRate_float(double rate_float) { 
			 this.rate_float = rate_float; } 
	 double rate_float;
}
