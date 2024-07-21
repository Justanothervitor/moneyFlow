package com.Api.MoneyFlow.MainCfg;

import java.util.Arrays;

import com.Api.MoneyFlow.MainCfg.Converters.BsonDocumentToZonedDateTimeConverter;
import com.Api.MoneyFlow.MainCfg.Converters.ZonedDateTimeToBsonDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.Api.MoneyFlow.MainCfg.Converters.ZonedDateTimeReadConverter;
import com.Api.MoneyFlow.MainCfg.Converters.ZonedDateTimeWriteConverter;

@Configuration
public class MongoCfg {

	@Bean
	MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(
				Arrays.asList(
						new ZonedDateTimeWriteConverter(),
						new ZonedDateTimeReadConverter(),
						new BsonDocumentToZonedDateTimeConverter(),
						new ZonedDateTimeToBsonDocument())
		);
	}
	
}
