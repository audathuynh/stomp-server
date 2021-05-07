package org.maxsure.demo.web;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@RestController
@RequestMapping("/")
public class HomeController {

    private final int interval;

    public HomeController(
            @Value("${org.maxsure.demo.heartbeat.interval:5}") int interval) {
        this.interval = interval;
    }

    @GetMapping("/heartbeat")
    public Flux<ServerSentEvent<byte[]>> streamHeartbeatEvents() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault());
        return Flux.interval(Duration.ofSeconds(interval))
                .map(num -> ServerSentEvent.<byte[]>builder()
                        .id(String.valueOf(num))
                        .event("heatbeat")
                        .data("OK".getBytes())
                        .comment(formatter.format(Instant.now()))
                        .build());
    }

}
