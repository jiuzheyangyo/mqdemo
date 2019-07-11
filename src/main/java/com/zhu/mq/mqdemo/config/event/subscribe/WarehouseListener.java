package com.zhu.mq.mqdemo.config.event.subscribe;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WarehouseListener {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.addQueueNames("reduceInventory");
        simpleMessageListenerContainer.setMessageListener((aa)->{
            System.out.println("aa+"+JSON.toJSONString(new String(aa.getBody())));
        });
        return simpleMessageListenerContainer;
    }

}
