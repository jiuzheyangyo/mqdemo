package com.zhu.mq.mqdemo.config.event;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventFromDomain {
    String domain();
}
