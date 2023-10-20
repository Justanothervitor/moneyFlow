package com.justAnotherVitor.MoneyFlow.config;
//Classe de configuração do Mongo, que por enquanto tem 2 classes
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import com.justAnotherVitor.MoneyFlow.config.converters.ZonedDateTimeReadConverter;
import com.justAnotherVitor.MoneyFlow.config.converters.ZonedDateTimeWriteConverter;

@Configuration
public class MongoConfig  {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
    	
    	return new MongoCustomConversions(
                Arrays.asList(

                        new ZonedDateTimeWriteConverter(),
                        new ZonedDateTimeReadConverter()

                )
        );
    }
}