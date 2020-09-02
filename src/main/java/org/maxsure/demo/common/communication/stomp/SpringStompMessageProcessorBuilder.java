package org.maxsure.demo.common.communication.stomp;

import org.maxsure.demo.common.communication.MessageListener;
import org.maxsure.demo.common.communication.MessageProcessor;
import org.maxsure.demo.common.communication.MessageProcessorBuilder;
import org.maxsure.demo.common.communication.MessageSubscriber;
import org.maxsure.demo.common.communication.SpringMessageProcessorBuilder;
import org.springframework.context.ApplicationContext;
import com.google.common.base.Preconditions;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class SpringStompMessageProcessorBuilder implements SpringMessageProcessorBuilder {

    private final MessageProcessorBuilder messageProcessorBuilder;
    private final ApplicationContext appContext;

    public SpringStompMessageProcessorBuilder(
            String topicPrefix,
            MessageSubscriber messageSubscriber,
            ApplicationContext appContext) {
        this.appContext = Preconditions.checkNotNull(appContext, "appContext");
        this.messageProcessorBuilder =
                new StompMessageProcessorBuilder(topicPrefix, messageSubscriber);
    }

    @Override
    public MessageProcessorBuilder addBinding(String topic, MessageListener messageListener) {
        return messageProcessorBuilder.addBinding(topic, messageListener);
    }

    @Override
    public MessageProcessorBuilder addBinding(
            String topic,
            Class<? extends MessageListener> clazz) {
        MessageListener listener = appContext.getBean(clazz);
        return addBinding(topic, listener);
    }

    @Override
    public MessageProcessor build() {
        return messageProcessorBuilder.build();
    }

}
