package com.Api.MoneyFlow.payloads.request;

import java.time.ZonedDateTime;

public class InputAnnotationRequest {
	
	private String name;
	private Double value;
	private ZonedDateTime inputDate;
	private String description;
	private String auth;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public ZonedDateTime getInputDate() {
		return inputDate;
	}
	public void setInputDate(ZonedDateTime inputDate) {
		this.inputDate = inputDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAuth()
	{
		return auth;
	}
	
	public void setAuth(String auth)
	{
		this.auth = auth;
	}
	
	
}
