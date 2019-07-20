package com.zhu.mq.mqdemo.config.event.subscribe;

import com.alibaba.fastjson.JSON;
import com.zhu.mq.mqdemo.config.event.EventConsumer;
import com.zhu.mq.mqdemo.config.event.EventFromDomain;
import com.zhu.mq.mqdemo.config.event.EventUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 事件监听生成器
 */
@Component
public class EventListenerGenerater implements Generater,InitializingBean{

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        generate("com.zhu.mq.mqdemo.config.event.subscribe");
    }

    @Override
    public void generate(String packageName) {
        Set<Class> set = new HashSet<>();
        getAllClass(packageName, set);
        for(Class clz : set){
            try {
                registContainer(clz);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(clz.getName()+"regist listner container fail");
            }
        }
    }


    public Set<Class> getAllClass(String packageName, Set<Class> set){
        String classPath = packageName.replace(".","/");
        try {
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources(classPath);
            while (resources.hasMoreElements()){
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if("file".equals(protocol)){
                    File file = new File(url.getPath());
                    File[] files = file.listFiles();
                    for(File f : files){
                        if(f.isDirectory()){
                            getAllClass(packageName+"."+f.getName(),set);
                        }else {
                            String s = packageName + "." + f.getName().substring(0, f.getName().length() - 6);
                            set.add(Class.forName(s));
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("event listener generater load class exception");
        }
        return set;
    }

    public <T> void registContainer (Class<T> clz) throws IllegalAccessException, InstantiationException {
        EventFromDomain annotation = (EventFromDomain)clz.getAnnotation(EventFromDomain.class);
        if(annotation != null) {
            T delegate = clz.newInstance();
            String domain = annotation.domain();
            Method[] declaredMethods = clz.getDeclaredMethods();
            for(Method method : declaredMethods){
                EventConsumer consumerAnno = method.getAnnotation(EventConsumer.class);
                if(consumerAnno != null){
                    String methodName = method.getName();
                    String eventName = consumerAnno.eventName();
                    String queque = EventUtils.geQueueName(eventName, domain);
                    MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(delegate, methodName);
                    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitTemplate.getConnectionFactory());
                    container.setQueueNames(queque);
                    container.setMessageListener(messageListenerAdapter);
                    container.start();
                }
            }
        }
    }


}
