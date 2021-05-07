package org.maxsure.demo.common.communication;

import org.springframework.context.ApplicationContext;
import com.google.common.base.Preconditions;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
public class SpringMessageProcessorBuilderImpl implements SpringMessageProcessorBuilder {

    /** The message processor builder. */
    private final MessageProcessorBuilder messageProcessorBuilder;

    /** The app context. */
    private final ApplicationContext appContext;

    /**
     * Instantiates a new spring stomp message processor builder.
     *
     * @param topicPrefix the topic prefix
     * @param messageSubscriber the message subscriber
     * @param appContext the app context
     */
    public SpringMessageProcessorBuilderImpl(
            MessageSubscriber subscriber,
            ApplicationContext appContext) {
        this.appContext = Preconditions.checkNotNull(appContext, "appContext");
        this.messageProcessorBuilder = new MessageProcessorBuilderImpl(subscriber);
    }

    public SpringMessageProcessorBuilderImpl(
            MessageProcessorBuilder messageProcessorBuilder,
            ApplicationContext appContext) {
        this.messageProcessorBuilder =
                Preconditions.checkNotNull(messageProcessorBuilder, "messageProcessorBuilder");
        this.appContext = Preconditions.checkNotNull(appContext, "appContext");

    }

    /**
     * Adds the binding between a topic and a listener.
     *
     * @param topic the topic
     * @param listener the message listener
     * @return the current message processor builder
     */
    @Override
    public MessageProcessorBuilder addBinding(String topic, MessageListener listener) {
        return messageProcessorBuilder.addBinding(topic, listener);
    }

    /**
     * Adds the binding between a topic and a class of a listener.
     *
     * @param topic the topic
     * @param clazz the class
     * @return the current message processor builder
     */
    @Override
    public MessageProcessorBuilder addBinding(
            String topic,
            Class<? extends MessageListener> clazz) {
        MessageListener listener = appContext.getBean(clazz);
        return addBinding(topic, listener);
    }

    /**
     * Builds a message processor.
     *
     * @return the message processor
     */
    @Override
    public MessageProcessor build() {
        return messageProcessorBuilder.build();
    }

}
