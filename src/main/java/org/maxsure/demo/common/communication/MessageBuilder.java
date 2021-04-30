package org.maxsure.demo.common.communication;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
public interface MessageBuilder {

    default String buildWithPayload(String payload) {
        return payload;
    }

}
