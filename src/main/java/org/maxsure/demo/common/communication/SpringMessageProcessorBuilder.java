package org.maxsure.demo.common.communication;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public interface SpringMessageProcessorBuilder extends MessageProcessorBuilder {

    MessageProcessorBuilder addBinding(String topic, Class<? extends MessageListener> clazz);

}
