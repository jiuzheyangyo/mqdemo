package com.zhu.mq.mqdemo.config.event;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MqConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = rabbitAdmin().getRabbitTemplate();
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
        System.out.println(JSON.toJSONString((Jackson2JsonMessageConverter)messageConverter));
        return rabbitTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        return cachingConnectionFactory;
    }
}
