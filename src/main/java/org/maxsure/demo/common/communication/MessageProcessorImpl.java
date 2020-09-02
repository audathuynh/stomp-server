package org.maxsure.demo.common.communication;

import java.util.List;
import java.util.Map;
import com.google.common.base.Preconditions;

/**
 * 
 * @author Dat Huynh
 * @since 1.0
 */
public class MessageProcessorImpl implements MessageProcessor {

    private MessageSubscriber messageSubscriber;
    private Map<String, List<MessageListener>> registers;

    public MessageProcessorImpl(
            MessageSubscriber messageSubscriber,
            Map<String, List<MessageListener>> registers) {
        this.messageSubscriber = Preconditions.checkNotNull(messageSubscriber, "messageSubscriber");
        this.registers = Preconditions.checkNotNull(registers, "register");
    }

    @Override
    public void subscribeAll() {
        registers.forEach(messageSubscriber::subscribe);
    }

}
