package com.bubbleshop.util;

import com.bubbleshop.exception.InvalidDataException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static com.bubbleshop.constants.ResponseCode.INVALID_PARAMETER;

public class DateTimeUtils {
    public static final String DATE_FORMAT_YYYY_MM_DD_DOT = "yyyy.MM.dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_DASH = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_DASH_HH_MM_SS_DOT = "yyyy-MM-dd HH:mm:ss";

    public static String convertDateTimeToString(String pattern, LocalDateTime dateTime) {
        if(Objects.isNull(dateTime))
            return StringUtils.EMPTY;
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.KOREA));
    }

    public static LocalDateTime convertStringToDateTime(String pattern, String dateTime) {
        if(dateTime.isBlank())
            throw new InvalidDataException(INVALID_PARAMETER);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static LocalDateTime convertStringToDateTime(String pattern, String dateTime, LocalTime time) {
        if(dateTime.isBlank())
            throw new InvalidDataException(INVALID_PARAMETER);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateTime, formatter).atTime(time);
    }
}
