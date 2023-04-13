package com.mini.beans.factory.support;

import com.mini.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected List<String> beanNames = new ArrayList<>();
    protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    protected final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    protected final Map<String,Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);
    @Override
    public void registerSingleton(String beanName, Object singletonObj) {
        synchronized (this.singletonObjects){
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null){
                throw new IllegalStateException("Could not register object [" + singletonObj +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            this.singletonObjects.put(beanName, singletonObj);
            beanNames.add(beanName);
            System.out.println(" bean registered............. " + beanName);
        }
    }

    @Override
    public Boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }


    public void removeSingleton(String beanName){
        synchronized (this.singletonObjects){
            this.beanNames.remove(beanName);
            this.singletonObjects.remove(beanName);
        }
    }


    public void registerDependentBean(String beanName, String dependentBeanName){
        Set<String> dependentBeanSet = this.dependentBeanMap.get(beanName);
        if (dependentBeanSet != null && dependentBeanSet.contains(dependentBeanName)){
            return;
        }

        synchronized (this.dependentBeanMap){
            dependentBeanSet = this.dependentBeanMap
                    .computeIfAbsent(beanName, k -> new LinkedHashSet<>(8));
            dependentBeanSet.add(dependentBeanName);
        }

        synchronized (this.dependenciesForBeanMap){
            Set<String> dependenciesForBean = this.dependenciesForBeanMap
                    .computeIfAbsent(beanName, k -> new LinkedHashSet<>(8));
            dependenciesForBean.add(dependentBeanName);
        }
    }
    public String[] getDependentBeans(String beanName){
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (dependentBeans == null) return new String[0];
        return (String[]) dependentBeans.toArray();
    }
    public String[] getDependenciesForBean(String beanName){
        Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
        if (dependenciesForBean == null) return new String[0];
        return (String[]) dependenciesForBean.toArray();
    }

    public boolean hasDependentBean(String beanName) {
        return this.dependentBeanMap.containsKey(beanName);
    }

}
