package org.maxsure.demo.common.communication.stomp;

import org.maxsure.demo.common.communication.MessageSender;
import org.maxsure.demo.common.communication.ScopedMessageSender;
import com.google.common.base.Preconditions;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class StompScopedMessageSender implements ScopedMessageSender {

    private final MessageSender messageSender;
    private final String topic;

    public StompScopedMessageSender(MessageSender messageSender, String topicPrefix, String topic) {
        this.messageSender = Preconditions.checkNotNull(messageSender, "messageSender");
        Preconditions.checkNotNull(topicPrefix, "topicPrefix");
        Preconditions.checkNotNull(topic, "topic");
        this.topic = String.format("%s/%s", topicPrefix, topic);
    }

    @Override
    public void send(byte[] data) {
        messageSender.send(topic, data);
    }

}
