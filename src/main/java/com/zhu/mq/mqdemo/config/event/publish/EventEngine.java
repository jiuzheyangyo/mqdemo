package com.zhu.mq.mqdemo.config.event.publish;

import com.alibaba.fastjson.JSON;


import com.zhu.mq.mqdemo.config.event.EventSource;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class EventEngine implements InitializingBean {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    public static String prefix = "ee";

    public static String separator = "-";

    public static String queueEnd = "queue";

    public static String exchangeEnd = "exchange";



    /**
     * 初始化事件引擎
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("event/" + "orderEvent.json");
        InputStream inputStream = classPathResource.getInputStream();
        String json = IOUtils.toString(inputStream, "UTF-8");
        List<EventSource> eventSources = JSON.parseArray(json, EventSource.class);
    }

    /**
     * 发送事件
     * @param eventName
     * @param eventParam
     */
    public void fire(String eventName,String domain,Object eventParam){
        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        rabbitTemplate.convertAndSend(getExchangeName(eventName,domain),"",eventParam);
    }

    /**
     * 加载 初始化交换机、队列到服务器
     * @param list
     */
    public void initServer(List<EventSource> list){
       for (EventSource eventSource : list){
           String eventName = eventSource.getEventName();
           String domain = eventSource.getDomain();
           //声明交换机
           Exchange exchange = ExchangeBuilder.fanoutExchange(getExchangeName(eventName,domain)).durable(true).build();
           rabbitAdmin.declareExchange(exchange);
           //声明队列
           Queue queue = QueueBuilder.durable(geQueueName(eventName,domain)).build();
           rabbitAdmin.declareQueue(queue);
           //声明绑定
           Binding binding = BindingBuilder.bind(queue).to(exchange).with("").noargs();
           rabbitAdmin.declareBinding(binding);
       }
    }

    public String getExchangeName(String eventName,String domain){
        return new StringBuilder(domain).append(separator).append(eventName)
                .append(separator).append(exchangeEnd).toString();
    }

    public String geQueueName(String eventName,String domain){
        return new StringBuilder(domain).append(separator).append(eventName)
                .append(separator).append(queueEnd).toString();
    }

}
