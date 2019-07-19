package com.zhu.mq.mqdemo;

import com.zhu.mq.mqdemo.config.event.EventConsumer;
import com.zhu.mq.mqdemo.config.event.subscribe.EventListenerGenerater;
import org.reflections.Reflections;

import java.io.File;
import java.util.Set;

public class Maintest {
    public static void main(String[] args) {

        EventListenerGenerater eventListenerGenerater = new EventListenerGenerater();
        eventListenerGenerater.generate("com.zhu.mq.mqdemo");
    }
}
