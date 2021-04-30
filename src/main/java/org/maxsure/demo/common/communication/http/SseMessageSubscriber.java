package org.maxsure.demo.common.communication.http;

import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.message.FormattedMessage;
import org.maxsure.demo.common.communication.MessageListener;
import org.maxsure.demo.common.communication.MessageSubscriber;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * The Class SseMessageSubscriber provides an implementation of
 * {@link org.maxsure.demo.common.communication.MessageSubscriber} to process messages via SSE.
 * 
 * @author Dat Huynh
 * @since 1.0
 */
@Slf4j
public class SseMessageSubscriber implements MessageSubscriber {

    /** The web client. */
    private final WebClient webClient;

    /** The uri pattern. */
    private final String uriPattern;

    /** The task executor. */
    private final TaskExecutor executor;

    /**
     * Instantiates a new sse message subscriber.
     *
     * @param webClient the web client
     * @param uriPattern the uri pattern
     * @param executor the executor
     */
    public SseMessageSubscriber(WebClient webClient, String uriPattern, TaskExecutor executor) {
        this.webClient = Preconditions.checkNotNull(webClient, "webClient");
        this.uriPattern = Preconditions.checkNotNull(uriPattern, "uriPattern");
        this.executor = Preconditions.checkNotNull(executor, "executor");
    }

    /**
     * Subscribes a topic and processes the messages by using a list of listeners.
     *
     * @param topic the topic
     * @param listeners the listeners
     */
    @Override
    public void subscribe(String topic, List<MessageListener> listeners) {
        FormattedMessage subscribedURI = new FormattedMessage(uriPattern, topic);
        String uri = subscribedURI.getFormattedMessage();
        ParameterizedTypeReference<ServerSentEvent<String>> elementType =
                new ParameterizedTypeReference<>() {};
        Flux<ServerSentEvent<String>> elementStream = webClient.get()
                .uri(uri)
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToFlux(elementType);
        elementStream.filter(Objects::nonNull)
                .subscribe(
                        event -> {
                            String id = event.id();
                            String name = event.event();
                            String data = event.data();
                            String comment = event.comment();
                            if (Strings.isNullOrEmpty(data)) {
                                return;
                            }
                            listeners.parallelStream()
                                    .forEach(listener -> executor.execute(() -> {
                                        try {
                                            listener.onMessage(data.getBytes());
                                        } catch (Exception e) {
                                            log.error("Error when handling a message from SSE", e);
                                        }
                                    }));
                        },
                        error -> log.error("Error when processing messages from SSE:\n{}", error),
                        () -> log.info("Processing messages from SSE completed."));
        log.info("Subscribed the SSE topic: [{}]", topic);
    }

}
