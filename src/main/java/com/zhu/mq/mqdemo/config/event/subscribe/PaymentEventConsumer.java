package com.zhu.mq.mqdemo.config.event.subscribe;

import com.alibaba.fastjson.JSON;
import com.zhu.mq.mqdemo.config.event.EventConsumer;
import com.zhu.mq.mqdemo.config.event.EventFromDomain;

@EventFromDomain(domain = "order")
public class PaymentEventConsumer {

    @EventConsumer(eventName = "payMoney")
    public void payMoney(byte[] obj){
        System.out.println("obj:"+ JSON.toJSONString(new String(obj)));
    }

}
