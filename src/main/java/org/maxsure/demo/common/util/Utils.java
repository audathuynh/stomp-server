package org.maxsure.demo.common.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * The Class Utils defines methods for common usage.
 *
 * @author Dat Huynh
 * @since 1.0
 */
public final class Utils {

    /**
     * Instantiates a new utils.
     */
    private Utils() {}

    /**
     * Converts a date into an XMLGregorianCalendar object.
     *
     * @param date the date
     * @return the XMLGregorianCalendar object
     */
    public static XMLGregorianCalendar xmlGregorianCalendar(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        try {
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            return null;
        }
    }

    /**
     * Creates a uuid string.
     *
     * @return the string
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

}
