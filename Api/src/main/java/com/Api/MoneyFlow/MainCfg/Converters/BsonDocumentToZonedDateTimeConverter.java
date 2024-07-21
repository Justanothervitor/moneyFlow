package com.Api.MoneyFlow.MainCfg.Converters;

import org.bson.BasicBSONObject;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class BsonDocumentToZonedDateTimeConverter implements Converter<BasicBSONObject, ZonedDateTime> {


    @Override
    public ZonedDateTime convert(final BasicBSONObject source) {
        if(source == null || source.isEmpty())
        {
            return null;
        }else{
            return ZonedDateTime.ofInstant(source.getDate("userInputDate").toInstant(), ZoneOffset.of(source.getString("Offset")));
        }
    }
}
