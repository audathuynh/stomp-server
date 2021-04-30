package org.maxsure.demo.configuration;

import java.util.Date;
import org.maxsure.demo.common.communication.MessageProcessor;
import org.maxsure.demo.common.encoding.json.DateTimeDeserialiser;
import org.maxsure.demo.encoding.BaseCommandTypeAdapterFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Configuration
public class DemoConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new BaseCommandTypeAdapterFactory())
                .registerTypeAdapter(Date.class, new DateTimeDeserialiser())
                .setDateFormat(DateTimeDeserialiser.ISO_8601_DATE_TIME_FORMAT)
                .create();
    }

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(
            @Value("${org.maxsure.demo.threadpool.coreSize:7}") int coreSize,
            @Value("${org.maxsure.demo.threadpool.maxSize:20}") int maxSize,
            @Value("${org.maxsure.demo.threadpool.queueCapacity:100}") int queueCapacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(
            @Qualifier("taskExecutor") ThreadPoolTaskExecutor executor) {
        return new WebMvcConfigurer() {
            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                configurer.setTaskExecutor(executor);
            }
        };
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady(ApplicationReadyEvent event) {
        ApplicationContext appContext = event.getApplicationContext();
        MessageProcessor processor =
                appContext.getBean("stompMessageProcessor", MessageProcessor.class);
        processor.subscribeAll();
    }

}
