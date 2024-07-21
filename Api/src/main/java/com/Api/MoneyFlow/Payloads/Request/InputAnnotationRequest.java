package com.Api.MoneyFlow.Payloads.Request;

import com.Api.MoneyFlow.MainCfg.Serializers.CustomJsonAnnotationDeserializer;
import com.Api.MoneyFlow.MainCfg.Serializers.CustomJsonAnnotationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize(using=CustomJsonAnnotationSerializer.class)
@JsonDeserialize(using= CustomJsonAnnotationDeserializer.class)
@Getter
@Setter
public class InputAnnotationRequest {
	
	private String name;
	private Double value;
	private String inputDate;
	private String description;

	public InputAnnotationRequest(String name, Double value, String inputDate, String description) {
		this.name = name;
		this.value = value;
		this.inputDate = inputDate;
		this.description = description;
	}

	public InputAnnotationRequest()
	{

	}
}
