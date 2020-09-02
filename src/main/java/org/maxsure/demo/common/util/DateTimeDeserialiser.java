package org.maxsure.demo.common.util;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Slf4j
public class DateTimeDeserialiser implements JsonDeserializer<Date> {

    // ISO 8601: https://www.w3.org/TR/NOTE-datetime
    public static final String ISO_8601_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Override
    public Date deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(ISO_8601_DATE_TIME_FORMAT, Locale.ENGLISH);
        try {
            LocalDateTime localDateTime =
                    LocalDateTime.parse(json.getAsString(), formatter);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            log.error("Error when parsing datetime", e);
            throw new JsonParseException(e);
        }
    }

}
