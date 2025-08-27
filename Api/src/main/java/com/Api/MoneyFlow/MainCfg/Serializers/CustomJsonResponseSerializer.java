package com.Api.MoneyFlow.MainCfg.Serializers;

import java.io.IOException;

import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class CustomJsonResponseSerializer extends JsonSerializer<AnnotationResponse>{

	@Override
	public void serialize(AnnotationResponse value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id",value.getId());
		gen.writeObjectField("name", value.getName());
		gen.writeObjectField("value",value.getValue());
		gen.writeObjectField("userInputDate", value.getUserInputDate());
		gen.writeObjectField("description", value.getDescription());
		gen.writeEndObject();
		
	}


}
