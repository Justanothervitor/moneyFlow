package com.Api.MoneyFlow.MainCfg.Serializers;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.Api.MoneyFlow.Domains.UserDomain;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class CustomJsonRegisterSerializer extends JsonSerializer<UserDomain>{


	@Override
	public void serialize(UserDomain value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("username",value.getUsername());
		gen.writeObjectField("email", value.getEmail());
		gen.writeObjectField("password",value.getPassword());
		gen.writeEndObject();
	}

}
