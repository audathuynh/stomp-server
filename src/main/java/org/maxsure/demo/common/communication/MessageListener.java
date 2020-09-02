package org.maxsure.demo.common.communication;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
@FunctionalInterface
public interface MessageListener {

    void onMessage(byte[] message);
}
