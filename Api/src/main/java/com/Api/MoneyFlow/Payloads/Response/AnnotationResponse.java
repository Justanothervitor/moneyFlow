package com.Api.MoneyFlow.Payloads.Response;

import java.time.ZonedDateTime;

import com.Api.MoneyFlow.MainCfg.Serializers.CustomJsonResponseSerializer;
import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonSerialize(using=CustomJsonResponseSerializer.class)
public class AnnotationResponse {
	
	private String id;
	private String name;
	private Double value;
	private ZonedDateTime dateInput;
	private String description;
	private ZonedDateTime dateOfCreation;

	
	public AnnotationResponse(AnnotationDomain obj)
	{
		this.id = obj.getNoteId();
		this.name = obj.getName();
		this.value = obj.getValue();
		this.dateInput = obj.getUserInputData();
		this.description = obj.getDescription();
		this.dateOfCreation = obj.getDateOfCreation();
	}

	public AnnotationResponse()
	{

	}

}
