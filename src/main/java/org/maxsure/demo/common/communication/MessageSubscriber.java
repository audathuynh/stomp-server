package org.maxsure.demo.common.communication;

import java.util.Collection;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public interface MessageSubscriber {

    void subscribe(String topic, Collection<MessageListener> listeners);

}
