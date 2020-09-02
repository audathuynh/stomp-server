package org.maxsure.demo.common.communication.stomp;

import org.maxsure.demo.common.communication.MessageListener;
import org.maxsure.demo.common.communication.ScopedMessageSender;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Slf4j
public class SimpleEchoStompListener implements MessageListener {

    private ScopedMessageSender messageSender;

    public SimpleEchoStompListener(ScopedMessageSender messageSender) {
        this.messageSender = Preconditions.checkNotNull(messageSender, "messageSender");
    }

    @Override
    public void onMessage(byte[] message) {
        String data = new String(message);
        log.debug("Processing a message from STOMP...\n{}", data);
        try {
            messageSender.send(data.getBytes());
        } catch (Exception ex) {
            log.error("Error when sending a message", ex);
        }
    }

}
