package com.zhu.mq.mqdemo.config.event.subscribe;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 事件监听生成器
 */
public class EventListenerGenerater implements Generater{

    @Override
    public void generate(String packageName) {
        Set<Class> set = new HashSet<>();
        getAllClass(packageName, set);
        for(Class clz : set){

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

    public void aa (){

    }

}
