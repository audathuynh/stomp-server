package org.maxsure.demo.common.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class MessageProcessorBuilderImpl implements MessageProcessorBuilder {

    private final Map<String, List<MessageListener>> registers;
    private final MessageSubscriber messageSubscriber;

    public MessageProcessorBuilderImpl(MessageSubscriber messageSubscriber) {
        this.messageSubscriber = Preconditions.checkNotNull(messageSubscriber, "messageSubscriber");
        this.registers = Maps.newConcurrentMap();
    }

    @Override
    public MessageProcessorBuilder addBinding(String topic, MessageListener messageListener) {
        if (registers.containsKey(topic)) {
            List<MessageListener> listeners = registers.get(topic);
            listeners.add(messageListener);
        } else {
            List<MessageListener> listeners = new ArrayList<>();
            listeners.add(messageListener);
            registers.put(topic, listeners);
        }
        return this;
    }

    @Override
    public MessageProcessor build() {
        return new MessageProcessorImpl(messageSubscriber, registers);
    }

}
