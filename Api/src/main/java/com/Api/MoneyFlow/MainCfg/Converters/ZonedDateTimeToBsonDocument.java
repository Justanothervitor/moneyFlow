package com.Api.MoneyFlow.MainCfg.Converters;

import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.Api.MoneyFlow.MainCfg.Converters.TimeZoneUtils.getTimeZoneFromId;

public class ZonedDateTimeToBsonDocument implements Converter<ZonedDateTime, BsonDocument> {

    @Override
    public BsonDocument convert(ZonedDateTime source) {
       if(source == null)
       {
           return null;
       }
       final BsonDocument document = new BsonDocument("userInputDate",new BsonDateTime(
               Date.from(source.toInstant()).getTime()
       ));

       final String offset = getTimeZoneFromId(source.getOffset().toString());

       document.put("offset",new BsonString(offset));

       final LocalTime hour = LocalTime.parse(offset.replaceAll("[-+]",""));

        document.put("virtual",
                new BsonDateTime(offset.startsWith("-") ?
                        Date.from(source.minusHours(hour.getHour())
                                .minusMinutes(hour.getMinute()).toInstant()
                        ).getTime() :
                        Date.from(source.plusHours(hour.getHour()).plusMinutes(hour.getMinute())
                                .toInstant()).getTime())
                );
        return document;
    }
}
