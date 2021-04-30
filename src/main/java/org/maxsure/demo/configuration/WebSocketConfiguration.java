package org.maxsure.demo.configuration;

import org.maxsure.demo.configuration.CommunicationConfigData.StompConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.google.common.base.Preconditions;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final StompConfig stompConfig;

    public WebSocketConfiguration(CommunicationConfigData configData) {
        Preconditions.checkNotNull(configData, "configData");
        this.stompConfig = configData.getStomp();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(stompConfig.getTopicPrefix(), stompConfig.getQueuePrefix());
        registry.setApplicationDestinationPrefixes(stompConfig.getAppPrefix());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String endpoint = stompConfig.getEndpoint();
        String[] allowedOrigins = stompConfig.getAllowedOrigins();
        registry.addEndpoint(endpoint).setAllowedOrigins(allowedOrigins);
        registry.addEndpoint(endpoint).setAllowedOrigins(allowedOrigins).withSockJS();
    }

}
