/*
 * 
 */
package org.maxsure.demo.common.communication.stomp;

import org.maxsure.demo.common.communication.MessageSubscriber;
import org.maxsure.demo.common.communication.SpringMessageProcessorBuilderImpl;
import org.springframework.context.ApplicationContext;

/**
 * The Class SpringStompMessageProcessorBuilder.
 *
 * @author Dat Huynh
 * @since 1.0
 */
public class SpringStompMessageProcessorBuilder extends SpringMessageProcessorBuilderImpl {

    /**
     * Instantiates a new spring stomp message processor builder.
     *
     * @param topicPrefix the topic prefix
     * @param subscriber the message subscriber
     * @param appContext the app context
     */
    public SpringStompMessageProcessorBuilder(
            String topicPrefix,
            MessageSubscriber subscriber,
            ApplicationContext appContext) {
        super(new StompMessageProcessorBuilder(topicPrefix, subscriber), appContext);
    }

}
