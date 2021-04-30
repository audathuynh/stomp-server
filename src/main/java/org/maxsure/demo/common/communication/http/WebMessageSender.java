package org.maxsure.demo.common.communication.http;

import org.apache.logging.log4j.message.FormattedMessage;
import org.maxsure.demo.common.communication.MessageBuilder;
import org.maxsure.demo.common.communication.MessageSender;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * The Class WebMessageSender provides an implementation of
 * {@link org.maxsure.demo.common.communication.MessageSender} to send messages to a server via HTTP
 * PUT method.
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Slf4j
public class WebMessageSender implements MessageSender {

    /** The web client. */
    private final WebClient webClient;

    /** The uri pattern. */
    private final String uriPattern;

    /** The msg builder. */
    private final MessageBuilder msgBuilder;

    /**
     * Instantiates a new web message sender.
     *
     * @param webClient the web client
     * @param uriPattern the uri pattern
     * @param msgBuilder the msg builder
     */
    public WebMessageSender(WebClient webClient, String uriPattern, MessageBuilder msgBuilder) {
        this.webClient = Preconditions.checkNotNull(webClient, "webClient");
        this.uriPattern = Preconditions.checkNotNull(uriPattern, "uriPattern");
        this.msgBuilder = Preconditions.checkNotNull(msgBuilder, "msgBuilder");
    }

    /**
     * Sends data to a topic.
     *
     * @param topic the topic
     * @param data the data
     */
    @Override
    public void send(String topic, byte[] data) {
        String payload = new String(data);
        FormattedMessage formattedURI = new FormattedMessage(uriPattern, topic);
        String uri = formattedURI.getFormattedMessage();
        String msg = msgBuilder.buildWithPayload(payload);
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter =
                BodyInserters.fromPublisher(Mono.just(msg), String.class);
        webClient.put()
                .uri(uri)
                .body(bodyInserter)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(log::debug,
                        e -> log.error("Error when sending: {}\n{}", e.getMessage(), payload));

    }

}
