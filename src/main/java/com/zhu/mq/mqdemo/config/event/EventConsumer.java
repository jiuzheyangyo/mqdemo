package com.zhu.mq.mqdemo.config.event;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventConsumer {
    String eventName();
}
