package com.zhu.mq.mqdemo.config.event.publish;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderEvent {


    @Bean
    public Queue reduceInventory(){
        return new Queue("reduceInventory",true);
    }

    @Bean
    public Exchange  inventory(){
        Exchange inventory = ExchangeBuilder.fanoutExchange("inventory").build();
        return inventory;
    }

    @Bean
    public Binding bing(){
        Binding binding = BindingBuilder.bind(reduceInventory()).to(inventory()).with("").and(null);
        return binding;
    }

    @Bean
    public Queue payMoney(){
        Queue payMoney = QueueBuilder.durable("payMoney").build();
        return payMoney;
    }

    @Bean
    public Exchange money(){
        Exchange money = ExchangeBuilder.fanoutExchange("money").build();
        return money;
    }
    @Bean Binding bingPayMoneyToMoney(){
        Binding and = BindingBuilder.bind(payMoney()).to(money()).with("").and(null);
        return and;
    }


}
