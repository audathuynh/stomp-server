package org.maxsure.demo.common.encoding.json;

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
 * The Class DateTimeDeserialiser provides an implementation of JsonDeserializer for the data type
 * Date.
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Slf4j
public class DateTimeDeserialiser implements JsonDeserializer<Date> {

    /** The Constant ISO_8601_DATE_TIME_FORMAT. */
    // ISO 8601: https://www.w3.org/TR/NOTE-datetime
    public static final String ISO_8601_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * Deserialises a json object into a Date value.
     *
     * @param json the json
     * @param typeOfT the type of T
     * @param context the context
     * @return the date
     */
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(ISO_8601_DATE_TIME_FORMAT, Locale.ENGLISH);
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(json.getAsString(), formatter);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            log.error("Error when parsing datetime", e);
            throw new JsonParseException(e);
        }
    }

}
