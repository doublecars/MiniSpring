package com.mini.beans.factory;

import com.mini.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;
    Boolean containsBean(String beanName);

    boolean isSingleton(String name);
    boolean isPrototype(String name);

    Class<?> getType(String name);

}
