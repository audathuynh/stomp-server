package org.maxsure.demo.data;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
public enum CommandType {

    SEARCH, PUSH, DELETE;

    public String value() {
        return name();
    }

    public static CommandType fromValue(String v) {
        return valueOf(v);
    }
}
