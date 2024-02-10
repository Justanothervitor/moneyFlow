package com.Api.MoneyFlow.MainCfg;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.Api.MoneyFlow.Serializers.ZonedDateTimeReadConverter;
import com.Api.MoneyFlow.Serializers.ZonedDateTimeWriteConverter;

@Configuration
public class MongoCfg {

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(
				Arrays.asList(
						new ZonedDateTimeWriteConverter(),
						new ZonedDateTimeReadConverter())
				);
	}
	
}
