package com.zhu.mq.mqdemo.controller;

import com.alibaba.fastjson.JSON;
import com.zhu.mq.mqdemo.config.event.publish.EventEngine;
import com.zhu.mq.mqdemo.vo.PaymentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    EventEngine eventEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);


    @RequestMapping("getRabbit")
    public void getRabbit(){
        String encoding = rabbitTemplate.getEncoding();
        fireReduceInventory();
        firePayMoney();
        updateUserInfo();
        LOGGER.info(JSON.toJSONString(rabbitTemplate));
    }

    public void fireReduceInventory(){
        eventEngine.fire("reduceInventory","order","扣减库存。。。");
    }

    public void firePayMoney(){
        eventEngine.fire("payMoney","order", PaymentVo.builder().no("1101011").comment("备注提示").money(13.3).build());
    }

    public void updateUserInfo(){
        eventEngine.fire("updateUserInfo","order","更新用户信息。。。");
    }


}
