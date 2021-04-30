package org.maxsure.demo.common.communication;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
public interface MessageParser {

    default String getPayload(String msg) {
        return msg;
    }

}
