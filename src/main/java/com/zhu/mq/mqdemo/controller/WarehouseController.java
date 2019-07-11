package com.zhu.mq.mqdemo.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("warehouse")
public class WarehouseController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseController.class);


    @RequestMapping("getRabbit")
    public void getRabbit(){
        String encoding = rabbitTemplate.getEncoding();

        LOGGER.info(JSON.toJSONString(rabbitTemplate));
    }
}
