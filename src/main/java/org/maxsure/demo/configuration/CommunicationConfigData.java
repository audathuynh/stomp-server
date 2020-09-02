package org.maxsure.demo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "org.maxsure.demo.communication")
@Data
public class CommunicationConfigData {

    private StompConfig stomp = new StompConfig();

    @Data
    public class StompConfig {
        private String baseURL;
        private String endpoint;
        private String appPrefix;
        private String topicPrefix;
        private String queuePrefix;
        private String[] allowedOrigins;
    }

}

