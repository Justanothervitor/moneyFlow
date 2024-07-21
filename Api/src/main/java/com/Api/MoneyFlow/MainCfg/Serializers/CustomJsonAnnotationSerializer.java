package com.Api.MoneyFlow.MainCfg.Serializers;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class CustomJsonAnnotationSerializer extends JsonSerializer<AnnotationDomain>{

	//private static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	

	@Override
	public void serialize(AnnotationDomain value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		
		gen.writeStartObject();
		gen.writeObjectField("name", value.getName());
		gen.writeObjectField("value", value.getValue());
		gen.writeObjectField("dateInput",value.getUserInputData());
		gen.writeObjectField("description", value.getDescription());
		gen.writeEndObject();
	}

}
