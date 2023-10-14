package com.justAnotherVitor.MoneyFlow.config.converters;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime>{
	
	private static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	

	@Override
	public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String date = p.getText();
		
			return (ZonedDateTime) FMT.parse(date);
		
	}

}
