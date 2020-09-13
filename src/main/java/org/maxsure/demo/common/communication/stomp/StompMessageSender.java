package org.maxsure.demo.common.communication.stomp;

import org.maxsure.demo.common.communication.MessageSender;
import org.springframework.messaging.simp.stomp.StompSession;
import com.google.common.base.Preconditions;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class StompMessageSender implements MessageSender {

    private final StompSession stompSession;

    public StompMessageSender(StompSession stompSession) {
        this.stompSession = Preconditions.checkNotNull(stompSession, "stompSession");
    }

    @Override
    public void send(String topic, byte[] data) {
        stompSession.send(topic, data);
    }

}
