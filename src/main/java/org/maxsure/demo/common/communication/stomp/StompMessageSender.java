package org.maxsure.demo.common.communication.stomp;

import org.maxsure.demo.common.communication.MessageSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.google.common.base.Preconditions;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class StompMessageSender implements MessageSender {

    private final SimpMessagingTemplate simpTemplate;

    public StompMessageSender(SimpMessagingTemplate simpTemplate) {
        this.simpTemplate = Preconditions.checkNotNull(simpTemplate, "simpTemplate");
    }

    @Override
    public void send(String topic, byte[] data) {
        simpTemplate.convertAndSend(topic, new String(data));
    }

}
