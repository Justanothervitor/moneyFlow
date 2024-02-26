package com.Api.MoneyFlow.payloads.response;

import java.time.ZonedDateTime;

import com.Api.MoneyFlow.domains.AnnotationDomain;

public class AnnotationResponse {
	
	private String id;
	private String name;
	private Double value;
	private ZonedDateTime dateInputed;
	private String description;
	private ZonedDateTime dateOfCreation;

	
	public AnnotationResponse(AnnotationDomain obj)
	{
		this.name = obj.getName();
		this.value = obj.getValue();
		this.dateInputed = obj.getUserInputData();
		this.description = obj.getDescription();
		this.dateOfCreation = obj.getDateOfCreation();
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
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
	public ZonedDateTime getDateInputed() {
		return dateInputed;
	}
	public void setDateInputed(ZonedDateTime dateInputed) {
		this.dateInputed = dateInputed;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ZonedDateTime getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(ZonedDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
}
