
package com.justAnotherVitor.MoneyFlow.config.converters;

import org.bson.BasicBSONObject;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class BsonDocumentToZonedDateTimeConverter implements Converter<BasicBSONObject, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(final BasicBSONObject source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return ZonedDateTime.ofInstant(source.getDate("date").toInstant(), ZoneOffset.of(source.getString("offset")));
    }
}