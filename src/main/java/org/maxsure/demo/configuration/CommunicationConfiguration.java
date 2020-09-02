package org.maxsure.demo.configuration;

import org.maxsure.demo.common.communication.MessageListener;
import org.maxsure.demo.common.communication.MessageProcessor;
import org.maxsure.demo.common.communication.MessageSender;
import org.maxsure.demo.common.communication.MessageSubscriber;
import org.maxsure.demo.common.communication.ScopedMessageSender;
import org.maxsure.demo.common.communication.stomp.SimpleEchoStompListener;
import org.maxsure.demo.common.communication.stomp.StompMessageProcessorBuilder;
import org.maxsure.demo.common.communication.stomp.StompMessageSender;
import org.maxsure.demo.common.communication.stomp.StompMessageSubscriber;
import org.maxsure.demo.common.communication.stomp.StompScopedMessageSender;
import org.maxsure.demo.configuration.CommunicationConfigData.StompConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Configuration
public class CommunicationConfiguration {

    @Bean("stompMessageSender")
    public MessageSender stompMessageSender(SimpMessagingTemplate simpTemplate) {
        return new StompMessageSender(simpTemplate);
    }

    @Bean("stompResponse")
    public ScopedMessageSender stompScopedMessageSender(
            @Value("${org.maxsure.demo.communication.stomp.topic.pub.response:response}") String topic,
            CommunicationConfigData configData,
            @Qualifier("stompMessageSender") MessageSender messageSender) {
        return new StompScopedMessageSender(
                messageSender,
                configData.getStomp().getTopicPrefix(),
                topic);
    }

    @Bean("webSocketClient")
    WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean("stompMessageSubscriber")
    public MessageSubscriber stompMessageSubscriber(
            CommunicationConfigData configData,
            @Qualifier("webSocketClient") WebSocketClient webSocketClient,
            @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        StompConfig stompConfig = configData.getStomp();
        String baseURL = stompConfig.getBaseURL();
        String endpoint = stompConfig.getEndpoint();
        String subscribeURL = String.format("%s%s", baseURL, endpoint);
        return new StompMessageSubscriber(webSocketClient, subscribeURL, taskExecutor);
    }

    @Bean("stomEchoListener")
    public MessageListener stompMessageListener(
            @Qualifier("stompResponse") ScopedMessageSender messageSender) {
        return new SimpleEchoStompListener(messageSender);
    }

    @Bean("stompMessageProcessor")
    public MessageProcessor stompMessageProcessor(
            CommunicationConfigData configData,
            @Qualifier("stompMessageSubscriber") MessageSubscriber messageSubscriber,
            @Value("${org.maxsure.demo.communication.stomp.topic.sub.request:request}") String requestTopic,
            @Qualifier("stompEchoListener") MessageListener stompEchoListener) {
        String topicPrefix = configData.getStomp().getTopicPrefix();
        return new StompMessageProcessorBuilder(topicPrefix, messageSubscriber)
                .addBinding(requestTopic, stompEchoListener)
                .build();
    }

}
