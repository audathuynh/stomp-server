package org.maxsure.demo.common.communication;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public interface MessageProcessorBuilder {

    MessageProcessorBuilder addBinding(String topic, MessageListener messageListener);

    MessageProcessor build();

}
