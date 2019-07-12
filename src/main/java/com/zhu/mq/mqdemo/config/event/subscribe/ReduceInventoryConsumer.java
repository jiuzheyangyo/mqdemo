package com.zhu.mq.mqdemo.config.event.subscribe;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class ReduceInventoryConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("cc:"+new String(message.getBody()));
    }
}
