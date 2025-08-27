package com.Api.MoneyFlow.Payloads.Response;

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
	private String userInputDate;
	private String description;

	
	public AnnotationResponse(AnnotationDomain obj)
	{
		this.id = obj.getNoteId();
		this.name = obj.getName();
		this.value = obj.getValue();
		this.userInputDate = obj.getUserInputData().toString();
		this.description = obj.getDescription();
	}

	public AnnotationResponse() {}
	}
