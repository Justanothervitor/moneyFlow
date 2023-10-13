
package com.justAnotherVitor.MoneyFlow.config.converters;

import org.bson.BasicBSONObject;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author Joel Rodrigues Moreira on 10/10/2023.
 * e-mail: <a href="mailto:joel.databox@gmail.com">joel.databox@gmail.com</a>
 * @project Api
 */
public class BsonDocumentToZonedDateTimeConverter implements Converter<BasicBSONObject, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(final BasicBSONObject source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return ZonedDateTime.ofInstant(source.getDate("date").toInstant(), ZoneOffset.of(source.getString("offset")));
    }
}