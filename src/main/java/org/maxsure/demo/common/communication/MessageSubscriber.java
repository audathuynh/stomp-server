package org.maxsure.demo.common.communication;

import java.util.List;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public interface MessageSubscriber {

    void subscribe(String topic, List<MessageListener> listeners);

}
