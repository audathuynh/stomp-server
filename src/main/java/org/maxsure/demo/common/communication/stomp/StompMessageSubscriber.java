package org.maxsure.demo.common.communication.stomp;

import java.lang.reflect.Type;
import java.util.List;
import org.maxsure.demo.common.communication.MessageListener;
import org.maxsure.demo.common.communication.MessageSubscriber;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
@Slf4j
public class StompMessageSubscriber implements MessageSubscriber {

    private final StompSession stompSession;
    private final TaskExecutor taskExecutor;

    public StompMessageSubscriber(StompSession stompSession, TaskExecutor taskExecutor) {
        this.stompSession = Preconditions.checkNotNull(stompSession, "stompSession");
        this.taskExecutor = Preconditions.checkNotNull(taskExecutor, "taskExecutor");
    }

    @Override
    public void subscribe(String topic, List<MessageListener> listeners) {
        stompSession.subscribe(topic, new StompFrameHandler() {
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                byte[] dataBytes = (byte[]) payload;
                for (MessageListener listener : listeners) {
                    taskExecutor.execute(() -> {
                        try {
                            listener.onMessage(dataBytes);
                        } catch (Exception ex) {
                            log.error("Error when handling message from Stomp", ex);
                        }
                    });
                }
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return byte[].class;
            }
        });
        log.info("Subscribed the Stomp topic: [{}]", topic);
    }

}
