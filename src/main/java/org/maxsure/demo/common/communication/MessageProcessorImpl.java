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

    private MessageSubscriber subscriber;
    private Map<String, List<MessageListener>> registers;

    public MessageProcessorImpl(
            MessageSubscriber subscriber,
            Map<String, List<MessageListener>> registers) {
        this.subscriber = Preconditions.checkNotNull(subscriber, "subscriber");
        this.registers = Preconditions.checkNotNull(registers, "register");
    }

    @Override
    public void subscribeAll() {
        registers.forEach(subscriber::subscribe);
    }

}
