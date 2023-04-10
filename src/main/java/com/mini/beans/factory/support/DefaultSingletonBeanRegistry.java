package com.mini.beans.factory.support;

import com.mini.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    List<String> beanNames = new ArrayList<>();
    Map<String, Object> singletons = new ConcurrentHashMap<>(256);


    @Override
    public void registrySingleton(String beanName, Object singletonObj) {
        synchronized (this.singletons){
            this.singletons.put(beanName, singletonObj);
            beanNames.add(beanName);
        }
    }

    @Override
    public Boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }


    public void removeSingleton(String beanName){
        synchronized (this.singletons){
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
