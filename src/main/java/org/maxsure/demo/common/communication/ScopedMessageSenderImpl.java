package org.maxsure.demo.common.communication;

import com.google.common.base.Preconditions;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class ScopedMessageSenderImpl implements ScopedMessageSender {

    private final String topic;
    private final MessageSender messageSender;

    public ScopedMessageSenderImpl(String topic, MessageSender messageSender) {
        this.topic = Preconditions.checkNotNull(topic, "topic");
        this.messageSender = Preconditions.checkNotNull(messageSender, "messageSender");
    }

    @Override
    public void send(byte[] data) {
        messageSender.send(topic, data);
    }

}
