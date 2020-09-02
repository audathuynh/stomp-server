package org.maxsure.demo.common.communication.stomp;

import org.maxsure.demo.common.communication.MessageListener;
import org.maxsure.demo.common.communication.MessageProcessor;
import org.maxsure.demo.common.communication.MessageProcessorBuilder;
import org.maxsure.demo.common.communication.MessageProcessorBuilderImpl;
import org.maxsure.demo.common.communication.MessageSubscriber;
import com.google.common.base.Preconditions;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class StompMessageProcessorBuilder implements MessageProcessorBuilder {

    private final MessageProcessorBuilder messageProcessorBuilder;
    private final String topicPrefix;

    public StompMessageProcessorBuilder(String topicPrefix, MessageSubscriber messageSubscriber) {
        this.topicPrefix = Preconditions.checkNotNull(topicPrefix, "topicPrefix");
        this.messageProcessorBuilder = new MessageProcessorBuilderImpl(messageSubscriber);
    }

    @Override
    public MessageProcessorBuilder addBinding(String topic, MessageListener messageListener) {
        String subTopic = String.format("%s/%s", topicPrefix, topic);
        return messageProcessorBuilder.addBinding(subTopic, messageListener);
    }

    @Override
    public MessageProcessor build() {
        return messageProcessorBuilder.build();
    }

}
