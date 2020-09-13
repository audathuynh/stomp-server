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
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Configuration
@Slf4j
public class CommunicationConfiguration {

    @Bean("stompSession")
    public StompSession stompSession(CommunicationConfigData configData) {
        StompConfig stompConfig = configData.getStomp();
        String baseURL = stompConfig.getBaseURL();
        String endpoint = stompConfig.getEndpoint();
        String subscribeURL = String.format("%s%s", baseURL, endpoint);

        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {};
        try {
            return stompClient.connect(subscribeURL, sessionHandler).get();
        } catch (Exception e) {
            log.error("Error when connecting to Stomp", e);
        }
        return null;
    }

    @Bean("stompMessageSender")
    public MessageSender stompMessageSender(StompSession stompSession) {
        return new StompMessageSender(stompSession);
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

    @Bean("stompMessageSubscriber")
    public MessageSubscriber stompMessageSubscriber(
            @Qualifier("stompSession") StompSession stompSession,
            @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        return new StompMessageSubscriber(stompSession, taskExecutor);
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
