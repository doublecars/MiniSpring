package com.mini.beans.factory.config;

public interface SingletonBeanRegistry {

    void registrySingleton(String beanName, Object singletonObj);
    Boolean containsSingleton(String beanName);
    Object getSingleton(String beanName);
    String[] getSingletonNames();
}
