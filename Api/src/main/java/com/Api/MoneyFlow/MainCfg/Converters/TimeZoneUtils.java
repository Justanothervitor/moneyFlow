package com.Api.MoneyFlow.MainCfg.Converters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.util.TimeZone.getTimeZone;

public class TimeZoneUtils {

    public static final String TIMEZONE_REGEX = "(([+-]|)([01]?[0-9]|2[0-3]):[0-5][0-9])|(([+-]|)([01]?[0-9]|2[0-3])([0-5][0-9]))";
    public static final Pattern TIMEZONE_PATTERN = Pattern.compile(TIMEZONE_REGEX);

    public static boolean isValidTimeZone(final String timezone)
    {
        return timezone != null ? TIMEZONE_PATTERN.matcher(timezone).matches(): false;
    }

    public static String getTimeZoneFromId(String zoneId)
    {
        if("z".equalsIgnoreCase(zoneId)){
            return "00:00";
        }
        if(isValidTimeZone(zoneId))
        {
            if(zoneId.contains("+")&& !zoneId.contains("-"))
            {
                zoneId= "+" +zoneId;
            }if(!zoneId.contains(":"))
        {
            zoneId = zoneId.substring(0,zoneId.length()-2) + ":" + zoneId.substring(zoneId.length()-2,zoneId.length());
        }
            return zoneId;
        }
        final TimeZone timeZone = getTimeZone(zoneId);

        if(timeZone == null)
        {
            return null;
        }
        final long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
        long minutes = TimeUnit.MINUTES.toMinutes(timeZone.getRawOffset()-TimeUnit.HOURS.toMinutes(hours));
        minutes = Math.abs(minutes);

        if(hours == 0 && minutes ==0)
        {
            return "+00:00";
        }
        final NumberFormat numberFormat = new DecimalFormat("00");
        final String hoursFormatted = numberFormat.format(hours);

        if(hours > 0)
        {
            return "+" + hoursFormatted + ":" + String.format("%02d",minutes);
        } else if(hours == 0 && minutes > 0)
        {
            return "+" + hoursFormatted + ":" + String.format("%02d",minutes);
        }else if(hours < 0)
        {
            return hoursFormatted + ":" + String.format("%02d",minutes);
        }else if(hours == 0 && minutes < 0)
        {
            return hoursFormatted + ":" + String.format("%02d",minutes);
        }
        return null;
    }

    public static String TimeZoneFromId(ZoneOffset offset)
    {
        return getTimeZoneFromId(offset.toString());
    }

}
