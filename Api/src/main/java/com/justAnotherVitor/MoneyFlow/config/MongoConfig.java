package com.justAnotherVitor.MoneyFlow.config;

import com.justAnotherVitor.MoneyFlow.config.converters.BsonDocumentToZonedDateTimeConverter;
import com.justAnotherVitor.MoneyFlow.config.converters.ZonedDateTimeToBsonDocumentConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;


@Configuration
public class MongoConfig  {
    /*@Override
    protected String getDatabaseName() {
        return "money-flow";
    }*/

 /*   @Override
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory, MongoCustomConversions customConversions, MongoMappingContext mappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        customConversions.get
        converter.setCustomConversions(customConversions);
        converter.setCodecRegistryProvider(databaseFactory);
        converter.
        return converter;
    }*/

    @Bean
    public MongoCustomConversions mongoCustomConversions() {

        return new MongoCustomConversions(
                Arrays.asList(
                        new ZonedDateTimeToBsonDocumentConverter(),
                        new BsonDocumentToZonedDateTimeConverter()
                )
        );
    }
}