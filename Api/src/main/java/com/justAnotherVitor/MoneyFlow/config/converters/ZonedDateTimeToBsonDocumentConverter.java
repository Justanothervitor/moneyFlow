package com.justAnotherVitor.MoneyFlow.config.converters;

import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.justAnotherVitor.MoneyFlow.config.converters.TimeZoneUtils.getTimezoneFromId;

public class ZonedDateTimeToBsonDocumentConverter implements Converter<ZonedDateTime, BsonDocument> {
    @Override
    public BsonDocument convert(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        final BsonDocument document = new BsonDocument("date", new BsonDateTime(
                Date.from(zonedDateTime.toInstant()).getTime()
        ));

        final String offset = getTimezoneFromId(zonedDateTime.getOffset().toString());

        document.put("offset", new BsonString(offset));

        final LocalTime hour = LocalTime.parse(offset.replaceAll("[-+]", ""));

        document.put("virtual",
                new BsonDateTime(
                        offset.startsWith("-") ?
                                Date.from(
                                        zonedDateTime
                                                .minusHours(hour.getHour())
                                                .minusMinutes(hour.getMinute())
                                                .toInstant()
                                ).getTime() :
                                Date.from(
                                        zonedDateTime
                                                .plusHours(hour.getHour())
                                                .plusMinutes(hour.getMinute())
                                                .toInstant()
                                ).getTime()
                ));
        return document;
    }

    /*@Override
    public <U> Converter<ZonedDateTime, U> andThen(Converter<? super BsonDocument, ? extends U> after) {
        return Converter.super.andThen(after);
    }*/
}