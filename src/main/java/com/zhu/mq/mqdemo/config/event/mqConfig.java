package com.zhu.mq.mqdemo.config.event;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mqConfig {
    @ConfigurationProperties
    public RabbitAdmin rabbitAdmin(){
        return null;
    }
}
