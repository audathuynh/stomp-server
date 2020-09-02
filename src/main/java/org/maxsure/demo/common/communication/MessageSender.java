package org.maxsure.demo.common.communication;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
@FunctionalInterface
public interface MessageSender {

    void send(String topic, byte[] data);

}
