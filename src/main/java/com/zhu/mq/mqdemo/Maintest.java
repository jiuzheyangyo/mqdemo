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
        String one = getOne(String.class);
        System.out.println(one);

    }

    public static <T> T getOne(Class<T> clz){
        T t = null;
        try {
            t = clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
