package com.Api.MoneyFlow.Serializers;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;


public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime,Date>{

	@Override
	public Date convert(ZonedDateTime value) {
		return Date.from(value.toInstant());
	}
}
