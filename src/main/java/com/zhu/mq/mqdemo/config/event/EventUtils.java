package com.zhu.mq.mqdemo.config.event;

public class EventUtils {

    public static String prefix = "ee";

    public static String separator = "-";

    public static String queueEnd = "queue";

    public static String exchangeEnd = "exchange";

    public static String getExchangeName(String eventName,String domain){
        return new StringBuilder(domain).append(separator).append(eventName)
                .append(separator).append(exchangeEnd).toString();
    }

    public static String geQueueName(String eventName,String domain){
        return new StringBuilder(domain).append(separator).append(eventName)
                .append(separator).append(queueEnd).toString();
    }
}
